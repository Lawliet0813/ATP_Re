package Tools;

import com.MiTAC.TRA.ATP.Tools.WaysideFailureGroupCases;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class WaysideFailureGroup extends WaysideFailureGroupCases {
  Vector data;
  
  Vector wsGroupData = new Vector();
  
  Vector wsGroupData_2 = new Vector();
  
  Vector wsGroupData_3 = new Vector();
  
  Vector wsGroupData_4 = new Vector();
  
  public WaysideFailureGroup() throws Exception {
    initCase();
  }
  
  public WaysideFailureGroup(Vector data) throws Exception {
    this();
    this.data = data;
    groupErrors();
  }
  
  public WaysideFailureGroup(String date, String wsno, String trno, String did, String vid) throws Exception {
    this();
    ConnectDB cndb = new ConnectDB();
    this.data = cndb




      
      .getData("SELECT MissionDate, WSNo, TRNo, DID, VID, RunningTime, Q_Failure_Class, I_Failure_Number FROM LogFailure WHERE Q_FAILURE_CLASS IN (8, 15, 16, 21, 34) " + ((date.length() != 0) ? ("AND MissionDate = '" + date + "' ") : "") + ((wsno.length() != 0) ? ("AND WSNo = '" + wsno + "' ") : "") + ((trno.length() != 0) ? ("AND TRNo = '" + trno + "' ") : "") + ((did.length() != 0) ? ("AND DID = '" + did + "' ") : "") + ((vid.length() != 0) ? ("AND VID = '" + vid + "'") : ""));
    groupErrors();
  }
  
  public Vector getGroupList() {
    return this.wsGroupData;
  }
  
  public Vector getRDTList() {
    return this.wsGroupData_2;
  }
  
  public Vector getRDTList_With_Pervious_balise() {
    return this.wsGroupData_3;
  }
  
  public Vector getRDTList_With_Pervious_balise_And_Remove_cycA5() {
    return ignoreFailureCases(this.wsGroupData_3);
  }
  
  public Vector greRDTList_With_Pervious_balise_And_Remove_CycA5_And_Reverse_balise() {
    return ignoreFailureCases(this.wsGroupData_4);
  }
  
  private void initCase() {
    for (int x = 0; x < eFlag.length; x++) {
      boolean[] tmp = (boolean[])eFlag[x];
      for (int y = 0; y < tmp.length; y++)
        tmp[y] = false; 
    } 
  }
  
  private void groupErrors() throws IOException {
    separiteData(this.data);
  }
  
  private void separiteData(Vector data) throws IOException {
    if (data == null)
      return; 
    int chksum = 0;
    for (int x = 0; x < data.size(); x++) {
      Vector tmp = data.get(x);
      chksum++;
      int ecase = ((Integer)tmp.get(4)).intValue();
      int eNumber = ((Integer)tmp.get(5)).intValue();
      this.wsGroupData.add(tmp);
      for (int y = 0; y < eCase.length; y++) {
        int[][] lcase = (int[][])eCase[y];
        for (int z = 0; z < lcase.length; z++) {
          int[] llcase = lcase[z];
          if (!((boolean[])eFlag[y])[z])
            if (llcase.length == 1) {
              if (ecase == llcase[0] && eNumber < 16380)
                ((boolean[])eFlag[y])[z] = true; 
            } else if (llcase.length == 2 && 
              ecase == llcase[0] && eNumber == llcase[1]) {
              ((boolean[])eFlag[y])[z] = true;
            }  
        } 
      } 
      int chknum = -1;
      for (int a = 0; a < eFlag.length; a++) {
        boolean[] bf = (boolean[])eFlag[a];
        boolean check = true;
        for (int b = 0; b < bf.length; b++)
          check &= bf[b]; 
        if (check) {
          chknum = a;
          if (!checkCaseElse(chknum, (x == data.size() - 1) ? null : data.get(x + 1))) {
            String findCase = caseName[a];
            String findCaseDescrip = caseDescrip[a];
            int baliseNO = 0;
            StringBuffer showBalise = new StringBuffer();
            for (int i = chksum; i > 0; i--) {
              ((Vector)this.wsGroupData.get(this.wsGroupData.size() - i)).add(String.valueOf(findCase) + "[" + findCaseDescrip + "]");
              if (((Integer)((Vector)this.wsGroupData.get(this.wsGroupData.size() - i)).get(4)).intValue() == 34 && (
                baliseNO == 0 || baliseNO >= 16380))
                baliseNO = ((Integer)((Vector)this.wsGroupData.get(this.wsGroupData.size() - i)).get(5)).intValue(); 
            } 
            Vector tmp2 = (Vector)((Vector)this.wsGroupData.get(this.wsGroupData.size() - chksum)).clone();
            tmp2.setElementAt(findCase, 4);
            tmp2.setElementAt(new Integer(baliseNO), 5);
            tmp2.setElementAt(findCaseDescrip, 6);
            this.wsGroupData_2.add(tmp2);
            if (baliseNO >= 16380 || baliseNO == 0) {
              ConnectDB cndb = new ConnectDB();
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
              try {
                Vector data2 = cndb.getData("SELECT TOP 1 NID_BG FROM LOGBTMDATA WHERE NID_BG < 16380 AND TRNO='" + 



                    
                    tmp2.get(1) + "'" + 
                    " AND DID='" + tmp2.get(0) + "'" + 
                    " AND VID='" + tmp2.get(2) + "'" + 
                    " AND RUNNINGTIME<'" + sdf.format((Date)tmp2.get(3)) + "'" + 
                    " ORDER BY RUNNINGTIME DESC");
                if (data2 == null) {
                  showBalise.append("X");
                } else {
                  showBalise.append(((Integer)((Vector)data2.get(0)).get(0)).intValue());
                } 
                showBalise.append(" * ");
                data2 = cndb.getData("SELECT TOP 1 NID_BG FROM LOGBTMDATA WHERE NID_BG < 16380 AND TRNO='" + 



                    
                    tmp2.get(1) + "'" + 
                    " AND DID='" + tmp2.get(0) + "'" + 
                    " AND VID='" + tmp2.get(2) + "'" + 
                    " AND RUNNINGTIME>'" + sdf.format((Date)tmp2.get(3)) + "'");
                if (data2 == null) {
                  showBalise.append("X");
                } else {
                  showBalise.append(((Integer)((Vector)data2.get(0)).get(0)).intValue());
                } 
              } catch (Exception ex) {
                ex.printStackTrace();
              } 
            } else {
              showBalise.append(baliseNO);
            } 
            Vector tmp3 = (Vector)((Vector)this.wsGroupData.get(this.wsGroupData.size() - chksum)).clone();
            tmp3.setElementAt(findCase, 4);
            tmp3.setElementAt(showBalise.toString(), 5);
            tmp3.setElementAt(findCaseDescrip, 6);
            this.wsGroupData_3.add(tmp3);
            if (!caseName_Detail[a].equals(caseName_Detail[2]) && 
              !caseName_Detail[a].equals(caseName_Detail[3]) && 
              !caseName_Detail[a].equals(caseName_Detail[6]) && 
              !caseName_Detail[a].equals(caseName_Detail[7]) && 
              !caseName_Detail[a].equals(caseName_Detail[9]) && 
              !caseName_Detail[a].equals(caseName_Detail[10]) && 
              !caseName_Detail[a].equals(caseName_Detail[18]) && 
              !caseName_Detail[a].equals(caseName_Detail[22]) && 
              !caseName_Detail[a].equals(caseName_Detail[23]) && 
              !caseName_Detail[a].equals(caseName_Detail[25]))
              this.wsGroupData_4.add(tmp3); 
            chksum = 0;
            initCase();
            break;
          } 
        } 
      } 
    } 
    initCase();
  }
  
  private Vector ignoreFailureCases(Vector source) {
    Vector rtn = (Vector)source.clone();
    int chkNum = 0;
    boolean ignore = false;
    for (int x = rtn.size() - 1; x >= 0; x--) {
      Vector tmp = rtn.get(x);
      if (tmp.get(4).toString().equals("A5")) {
        if (ignore)
          rtn.remove(x); 
        if (chkNum == 2) {
          rtn.remove(x + 2);
          rtn.remove(x + 1);
          rtn.remove(x);
          ignore = true;
        } 
        chkNum++;
      } else {
        chkNum = 0;
        ignore = false;
      } 
    } 
    return rtn;
  }
  
  private Vector ignoreReverseFailure(Vector data) {
    Vector rtn = new Vector();
    return rtn;
  }
  
  private boolean checkCaseElse(int chk, Vector next) {
    boolean rtn = false;
    if (eFlag[chk] == this.caseA4_2_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA4_2_c.length; x++)
          this.caseA4_2_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA1_5_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA1_4_c.length; x++)
          this.caseA1_4_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA1_6_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA1_6_c.length; x++)
          this.caseA1_6_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA1_7_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA1_7_c.length; x++)
          this.caseA1_7_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA1_8_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA1_8_c.length; x++)
          this.caseA1_8_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA5_2_c) {
      if (this.caseA1_1_c[0] || this.caseA1_1_c[1] || this.caseA1_1_c[3] || this.caseA1_1_c[4] || 
        this.caseA1_2_c[0] || this.caseA1_2_c[1] || this.caseA1_2_c[3] || this.caseA1_2_c[4] || 
        this.caseA1_3_c[0] || this.caseA1_3_c[1] || this.caseA1_3_c[3] || this.caseA1_3_c[4] || 
        this.caseA1_4_c[0] || this.caseA1_4_c[1] || this.caseA1_4_c[3] || this.caseA1_4_c[4] || 
        this.caseA3_1_c[0] || this.caseA3_1_c[1] || this.caseA3_1_c[2] || 
        this.caseA3_2_c[0] || this.caseA3_2_c[1] || this.caseA3_2_c[2] || 
        this.caseA3_5_c[0] || this.caseA3_5_c[1] || this.caseA3_5_c[2] || 
        this.caseA3_6_c[0] || this.caseA3_6_c[1] || this.caseA3_6_c[2] || 
        this.caseA4_1_c[0] || this.caseA4_1_c[1] || 
        this.caseA6_1_c[0] || this.caseA6_1_c[2] || this.caseA6_1_c[4] || 
        this.caseA7_1_c[0] || this.caseA7_1_c[1] || 
        this.caseA7_2_c[0] || this.caseA7_2_c[1] || 
        this.caseA7_5_c[0]) {
        for (int x = 0; x < this.caseA5_2_c.length; x++)
          this.caseA5_2_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA3_3_c && next != null) {
      if ((((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) || ((
        (Integer)next.get(4)).intValue() == 8 && ((Integer)next.get(5)).intValue() == 12420)) {
        for (int x = 0; x < this.caseA3_3_c.length; x++)
          this.caseA3_3_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA3_4_c && next != null) {
      if ((((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) || ((
        (Integer)next.get(4)).intValue() == 8 && ((Integer)next.get(5)).intValue() == 12420)) {
        for (int x = 0; x < this.caseA3_4_c.length; x++)
          this.caseA3_4_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA7_3_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA7_3_c.length; x++)
          this.caseA7_3_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA7_4_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA7_4_c.length; x++)
          this.caseA7_4_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA7_6_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA7_6_c.length; x++)
          this.caseA7_6_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA6_1_1_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA6_1_1_c.length; x++)
          this.caseA6_1_1_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA2_1_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA2_1_c.length; x++)
          this.caseA2_1_c[x] = false; 
        rtn = true;
      } 
    } else if (eFlag[chk] == this.caseA2_2_c && next != null) {
      if (((Integer)next.get(4)).intValue() == 15 && ((Integer)next.get(5)).intValue() == 4098) {
        for (int x = 0; x < this.caseA2_2_c.length; x++)
          this.caseA2_2_c[x] = false; 
        rtn = true;
      } 
    } 
    return rtn;
  }
}
