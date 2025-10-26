package connect;

import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.ShowTimeNow;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class ConnectDB {
  public static final int checkBackup = 2;
  
  public static final int checkDecode = 1;
  
  public static final int checkUpload = 0;
  
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  
  public boolean Delete(String paramString) throws Exception {
    Connection connection = getNewConn();
    Statement statement = connection.createStatement();
    statement.executeUpdate(paramString);
    statement.close();
    connection.close();
    return true;
  }
  
  public String Delete(Vector paramVector) throws Exception {
    Connection connection = getNewConn();
    String str = Delete(connection, paramVector);
    connection.close();
    return str;
  }
  
  public String Delete(Connection paramConnection, Vector paramVector) throws Exception {
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_DeleteStation(?)}");
    for (byte b1 = 0; b1 < paramVector.size(); b1++) {
      callableStatement.setString(1, paramVector.get(b1));
      callableStatement.addBatch();
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    byte b2 = 0;
    byte b3 = 0;
    for (byte b4 = 0; b4 < arrayOfInt.length; b4++) {
      if (arrayOfInt[b4] == 1) {
        b2++;
      } else {
        b3++;
      } 
    } 
    return "總刪除筆數: " + arrayOfInt.length + "\n 刪除成功: " + b2 + "\n 刪除失敗: " + b3;
  }
  
  public boolean DeleteLog(ATPMission paramATPMission) throws Exception {
    boolean bool = true;
    Connection connection = getTransactionConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_DeleteRecordLog(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    callableStatement.executeUpdate();
    connection.commit();
    connection.close();
    return bool;
  }
  
  public String[] Insert(Vector paramVector) throws Exception {
    Connection connection = getNewConn();
    String[] arrayOfString = { "", "" };
    arrayOfString = Insert(connection, paramVector);
    connection.close();
    return arrayOfString;
  }
  
  public String[] Insert(Connection paramConnection, Vector paramVector) throws Exception {
    String[] arrayOfString = { "", "" };
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertStation(?,?,?,?,?,?)}");
    callableStatement.setString(1, paramVector.get(0));
    callableStatement.setFloat(2, ((Float)paramVector.get(1)).floatValue());
    callableStatement.setString(3, paramVector.get(2));
    callableStatement.setString(4, paramVector.get(3));
    callableStatement.setString(5, paramVector.get(4));
    callableStatement.setString(6, paramVector.get(5));
    callableStatement.execute();
    arrayOfString[0] = "Insert Completed";
    arrayOfString[1] = "1";
    return arrayOfString;
  }
  
  public boolean Insert(String paramString) throws Exception {
    Connection connection = getNewConn();
    Statement statement = connection.createStatement();
    statement.executeUpdate(paramString);
    statement.close();
    connection.close();
    return true;
  }
  
  private boolean _$3923(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getBTM();
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertBTM(?,?,?,?,?, ?,?,?,?)}");
    for (byte b = 0; b < vector.size(); b++) {
      Vector vector1 = vector.get(b);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Integer)vector1.get(6)).intValue());
      callableStatement.setInt(8, ((Integer)vector1.get(7)).intValue());
      callableStatement.setInt(9, ((Integer)vector1.get(8)).intValue());
      callableStatement.addBatch();
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    return true;
  }
  
  private boolean _$4009(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getBtnEvent();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertBtnEvent(?,?,?,?,?, ?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("Button Event size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, 0);
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3921(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    null = true;
    Vector vector = paramATPMissionGeneral.getCategory();
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertCategory(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)}");
    byte b1 = 1;
    byte b2 = 4;
    callableStatement.setString(b1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
    callableStatement.setString(++b1, paramATPMissionGeneral.getWorkShift());
    callableStatement.setString(++b1, paramATPMissionGeneral.getTrainRunning());
    callableStatement.setString(++b1, paramATPMissionGeneral.getDriver());
    callableStatement.setString(++b1, paramATPMissionGeneral.getVehicle());
    callableStatement.setString(++b1, vector.get(b2));
    callableStatement.setString(++b1, vector.get(++b2));
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setFloat(++b1, ((Double)vector.get(++b2)).floatValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    b2++;
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setInt(++b1, ((Integer)vector.get(++b2)).intValue());
    callableStatement.setString(++b1, this.sdf.format(paramATPMissionGeneral.getMissionStartTime()));
    callableStatement.setString(++b1, this.sdf.format(paramATPMissionGeneral.getMissionEndTime()));
    return callableStatement.execute();
  }
  
  private boolean _$3932(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getCurve();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertCurve(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("Curve size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      byte b3 = 6;
      byte b4 = 5;
      callableStatement.setInt(b3, ((Integer)vector1.get(b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3927(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getDriverMessage();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertDriverMessage(?,?,?,?,?, ?,?,?,?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("DriverMessage size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Integer)vector1.get(6)).intValue());
      callableStatement.setInt(8, ((Integer)vector1.get(7)).intValue());
      callableStatement.setInt(9, ((Integer)vector1.get(8)).intValue());
      callableStatement.setInt(10, ((Integer)vector1.get(9)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3929(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getDriverMessage_Ack();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertDriverMessage_Ack(?,?,?,?,?, ?,?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("DriverMessage Ack size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Integer)vector1.get(6)).intValue());
      callableStatement.setInt(8, ((Integer)vector1.get(7)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3924(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getDynamic();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertDynamic(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("Dynamic size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Integer)vector1.get(6)).intValue());
      callableStatement.setInt(8, ((Integer)vector1.get(7)).intValue());
      callableStatement.setInt(9, ((Integer)vector1.get(8)).intValue());
      callableStatement.setInt(10, ((Integer)vector1.get(9)).intValue());
      callableStatement.setInt(11, ((Integer)vector1.get(10)).intValue());
      callableStatement.setInt(12, ((Integer)vector1.get(11)).intValue());
      callableStatement.setInt(13, ((Integer)vector1.get(12)).intValue());
      callableStatement.setInt(14, ((Integer)vector1.get(13)).intValue());
      callableStatement.setInt(15, ((Integer)vector1.get(14)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3926(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getFailure();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertFailure(?,?,?,?,?, ?,?,?,?,?, ?)}");
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Integer)vector1.get(6)).intValue());
      callableStatement.setInt(8, ((Integer)vector1.get(7)).intValue());
      callableStatement.setInt(9, ((Integer)vector1.get(8)).intValue());
      callableStatement.setInt(10, ((Integer)vector1.get(9)).intValue());
      callableStatement.setInt(11, ((Integer)vector1.get(10)).intValue());
      callableStatement.addBatch();
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3931(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getGradient();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertGradient(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("Gradient size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      byte b3 = 6;
      byte b4 = 5;
      callableStatement.setInt(b3, ((Integer)vector1.get(b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.setInt(++b3, ((Integer)vector1.get(++b4)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  public boolean InsertLogBackupLibrary(Vector paramVector, String[][] paramArrayOfString) throws Exception {
    boolean bool = true;
    Connection connection = getTransactionConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_InsertLogBackupLibrary(?,?,?,?,?, ?,?)}");
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      callableStatement.setString(1, vector.get(1));
      callableStatement.setString(2, vector.get(2));
      callableStatement.setString(3, this.sdf.format((Date)vector.get(0)));
      callableStatement.setString(4, vector.get(3));
      callableStatement.setString(5, vector.get(4));
      callableStatement.setString(6, paramArrayOfString[b][1]);
      callableStatement.setString(7, ShowTimeNow.TimeNow());
      callableStatement.addBatch();
    } 
    callableStatement.executeBatch();
    connection.commit();
    connection.close();
    return bool;
  }
  
  public boolean InsertLogDeleteLibrary(Vector paramVector, String paramString) throws Exception {
    boolean bool = true;
    Connection connection = getTransactionConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_InsertLogDeleteLibrary(?,?,?,?,?, ?,?)}");
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      callableStatement.setString(1, vector.get(1));
      callableStatement.setString(2, vector.get(2));
      callableStatement.setString(3, this.sdf.format((Date)vector.get(0)));
      callableStatement.setString(4, vector.get(3));
      callableStatement.setString(5, vector.get(4));
      callableStatement.setString(6, paramString);
      callableStatement.setString(7, ShowTimeNow.TimeNow());
      callableStatement.addBatch();
    } 
    callableStatement.executeBatch();
    connection.commit();
    connection.close();
    return bool;
  }
  
  public boolean InsertLogUploadLibrary(Vector paramVector, String paramString1, String paramString2) throws Exception {
    boolean bool = true;
    Connection connection = getTransactionConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_InsertLogUploadLibrary(?,?,?,?,?, ?,?,?)}");
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      callableStatement.setString(1, vector.get(1));
      callableStatement.setString(2, vector.get(2));
      callableStatement.setString(3, this.sdf.format((Date)vector.get(0)));
      callableStatement.setString(4, vector.get(3));
      callableStatement.setString(5, vector.get(4));
      callableStatement.setString(6, paramString1);
      callableStatement.setString(7, paramString2);
      callableStatement.setString(8, ShowTimeNow.TimeNow());
      callableStatement.addBatch();
    } 
    callableStatement.executeBatch();
    connection.commit();
    connection.close();
    return bool;
  }
  
  public boolean InsertMWUser(Vector paramVector) {
    try {
      Connection connection = getTransactionConn();
      CallableStatement callableStatement = connection.prepareCall("{call sp_InsertMWUser(?,?,?,?)}");
      callableStatement.setString(1, paramVector.get(0));
      callableStatement.setString(2, paramVector.get(1));
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])paramVector.get(2));
      callableStatement.setBinaryStream(3, byteArrayInputStream, 16);
      callableStatement.setString(4, paramVector.get(3));
      callableStatement.execute();
      connection.commit();
      connection.close();
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  public boolean InsertMissionLog(ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    boolean bool = true;
    Connection connection = getTransactionConn();
    try {
      bool = _$3921(connection, paramATPMissionGeneral);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    bool = _$3922(connection, paramATPMissionGeneral);
    bool = _$3923(connection, paramATPMissionGeneral);
    bool = _$3924(connection, paramATPMissionGeneral);
    bool = _$3925(connection, paramATPMissionGeneral);
    bool = _$3926(connection, paramATPMissionGeneral);
    bool = _$3927(connection, paramATPMissionGeneral);
    bool = _$3928(connection, paramATPMissionGeneral);
    bool = _$3929(connection, paramATPMissionGeneral);
    bool = _$3930(connection, paramATPMissionGeneral);
    bool = _$3931(connection, paramATPMissionGeneral);
    bool = _$3932(connection, paramATPMissionGeneral);
    if (bool) {
      connection.commit();
    } else {
      connection.rollback();
    } 
    connection.close();
    return bool;
  }
  
  private boolean _$3925(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getStatus();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertStatus(?,?,?,?,?, ?,?,?,?,?, ?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("Status size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Integer)vector1.get(6)).intValue());
      callableStatement.setInt(8, ((Integer)vector1.get(7)).intValue());
      callableStatement.setInt(9, ((Integer)vector1.get(8)).intValue());
      callableStatement.setInt(10, ((Integer)vector1.get(9)).intValue());
      callableStatement.setInt(11, ((Integer)vector1.get(10)).intValue());
      callableStatement.setInt(12, ((Integer)vector1.get(11)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3922(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getTimeStamp();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertTS(?,?,?,?,?, ?,?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("TS size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Integer)vector1.get(6)).intValue());
      callableStatement.setInt(8, ((Integer)vector1.get(7)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3930(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getTrainData();
    boolean bool = true;
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertTrainData(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)}");
    long l = System.currentTimeMillis();
    System.out.println("TrainData size: " + vector.size());
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      byte b = 1;
      callableStatement.setString(b, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(++b, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(++b, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(++b, paramATPMissionGeneral.getDriver());
      callableStatement.setString(++b, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(++b, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(++b, (new Integer(vector1.get(22).toString().substring(vector1.get(22).toString().length() - 3, vector1.get(22).toString().length()).trim())).intValue());
      callableStatement.setString(++b, vector1.get(21).toString());
      callableStatement.setString(++b, vector1.get(20).toString());
      callableStatement.setInt(++b, ((Integer)vector1.get(19)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(18)).intValue());
      callableStatement.setFloat(++b, ((Double)vector1.get(17)).floatValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(16)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(15)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(14)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(13)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(12)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(10)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(9)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(8)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(7)).intValue());
      callableStatement.setInt(++b, ((Integer)vector1.get(6)).intValue());
      callableStatement.addBatch();
      if (b1 % 5000 == 0) {
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to init");
        l = System.currentTimeMillis();
        callableStatement.executeBatch();
        System.out.println("batch " + b1 + " >> " + (System.currentTimeMillis() - l) + "ms to execute batch");
        l = System.currentTimeMillis();
      } 
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      if (arrayOfInt[b2] == 0) {
        bool = false;
        break;
      } 
    } 
    return bool;
  }
  
  private boolean _$3928(Connection paramConnection, ATPMissionGeneral paramATPMissionGeneral) throws Exception {
    Vector vector = paramATPMissionGeneral.getVDX();
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_InsertVDX(?,?,?,?,?, ?,?)}");
    for (byte b = 0; b < vector.size(); b++) {
      Vector vector1 = vector.get(b);
      callableStatement.setString(1, this.sdf.format(paramATPMissionGeneral.getMissionDate()));
      callableStatement.setString(2, paramATPMissionGeneral.getWorkShift());
      callableStatement.setString(3, paramATPMissionGeneral.getTrainRunning());
      callableStatement.setString(4, paramATPMissionGeneral.getDriver());
      callableStatement.setString(5, paramATPMissionGeneral.getVehicle());
      callableStatement.setString(6, this.sdf.format(vector1.get(5)));
      callableStatement.setInt(7, ((Boolean)vector1.get(6)).booleanValue() ? 1 : 0);
      callableStatement.addBatch();
    } 
    int[] arrayOfInt = callableStatement.executeBatch();
    return true;
  }
  
  public String[] Update(Vector paramVector) throws Exception {
    Connection connection = getNewConn();
    String[] arrayOfString = { "", "" };
    arrayOfString = Update(connection, paramVector);
    connection.close();
    return arrayOfString;
  }
  
  public String[] Update(Connection paramConnection, Vector paramVector) throws Exception {
    String[] arrayOfString = { "", "" };
    CallableStatement callableStatement = paramConnection.prepareCall("{call sp_UpdateStation(?,?,?,?,?,?,?)}");
    callableStatement.setString(1, paramVector.get(0));
    callableStatement.setFloat(2, ((Float)paramVector.get(1)).floatValue());
    callableStatement.setString(3, paramVector.get(2));
    callableStatement.setString(4, paramVector.get(3));
    callableStatement.setString(5, paramVector.get(4));
    callableStatement.setString(6, paramVector.get(5));
    callableStatement.setString(7, paramVector.get(6));
    if (callableStatement.executeUpdate() == 1) {
      arrayOfString[0] = "Update Completed";
      arrayOfString[1] = "1";
      return arrayOfString;
    } 
    arrayOfString[0] = "Update Completed";
    arrayOfString[1] = "0";
    return arrayOfString;
  }
  
  public boolean Update(String paramString) throws Exception {
    Connection connection = getNewConn();
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate(paramString);
      statement.close();
      connection.close();
    } catch (Exception exception) {
      throw exception;
    } finally {
      if (!connection.isClosed())
        connection.close(); 
    } 
    return true;
  }
  
  public boolean UpdateMWUser(Vector paramVector) {
    try {
      Connection connection = getTransactionConn();
      CallableStatement callableStatement = connection.prepareCall("{call sp_UpdateMWUser(?,?,?,?)}");
      callableStatement.setString(1, paramVector.get(0));
      callableStatement.setString(2, paramVector.get(1));
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])paramVector.get(2));
      callableStatement.setBinaryStream(3, byteArrayInputStream, 16);
      callableStatement.setString(4, paramVector.get(3));
      callableStatement.execute();
      connection.commit();
      connection.close();
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  public boolean UpdateMWUserWithPWDChange(Vector paramVector) {
    try {
      Connection connection = getTransactionConn();
      CallableStatement callableStatement = connection.prepareCall("{call sp_UpdateMWUserWithPWDChange(?,?,?)}");
      callableStatement.setString(1, paramVector.get(0));
      callableStatement.setString(2, paramVector.get(1));
      callableStatement.setString(3, paramVector.get(2));
      callableStatement.execute();
      connection.commit();
      connection.close();
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  public boolean UpdateMission(Vector paramVector) {
    boolean bool = false;
    try {
      Connection connection = getTransactionConn();
      CallableStatement callableStatement = connection.prepareCall("{call sp_UpdateMission_Commit(?,?,?,?,?, ?)}");
      for (byte b = 0; b < paramVector.size(); b++) {
        Vector vector = paramVector.get(b);
        callableStatement.setString(1, vector.get(3));
        callableStatement.setString(2, this.sdf.format((Date)vector.get(0)));
        callableStatement.setString(3, vector.get(1));
        callableStatement.setString(4, vector.get(2));
        callableStatement.setString(5, vector.get(4));
        callableStatement.setString(6, vector.get(5));
        callableStatement.addBatch();
      } 
      callableStatement.executeBatch();
      connection.commit();
      connection.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return bool;
  }
  
  public boolean UpdateMission(Vector paramVector, int paramInt) {
    boolean bool = false;
    try {
      Connection connection = getTransactionConn();
      CallableStatement callableStatement = connection.prepareCall("{call sp_UpdateMission_Commit(?,?,?,?,?, ?)}");
      for (byte b = 0; b < paramVector.size(); b++) {
        Vector vector = paramVector.get(b);
        String str1;
        for (str1 = vector.get(3); str1.startsWith("0"); str1 = str1.substring(1, str1.length()));
        callableStatement.setString(1, str1);
        callableStatement.setString(2, this.sdf.format((Date)vector.get(0)));
        String str2;
        for (str2 = vector.get(1); str2.startsWith("0"); str2 = str2.substring(1, str2.length()));
        callableStatement.setString(3, str2);
        String str3;
        for (str3 = vector.get(2); str3.startsWith("0"); str3 = str3.substring(1, str3.length()));
        callableStatement.setString(4, str3);
        String str4 = vector.get(4);
        callableStatement.setString(5, str4);
        callableStatement.setString(6, "1");
        callableStatement.addBatch();
        System.out.println("DID: " + str1 + ", WS: " + str2 + ", TR: " + str3 + ", VID: " + str4);
      } 
      callableStatement.executeBatch();
      connection.commit();
      connection.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return bool;
  }
  
  public boolean UpdateMission_Backup(Vector paramVector, int paramInt) {
    boolean bool = false;
    try {
      Connection connection = getTransactionConn();
      CallableStatement callableStatement = connection.prepareCall("{call sp_UpdateMission_Backup(?,?,?,?,?)}");
      for (byte b = 0; b < paramVector.size(); b++) {
        Vector vector = paramVector.get(b);
        String str1;
        for (str1 = vector.get(3); str1.startsWith("0"); str1 = str1.substring(1, str1.length()));
        callableStatement.setString(1, str1);
        callableStatement.setString(2, this.sdf.format((Date)vector.get(0)));
        String str2;
        for (str2 = vector.get(1); str2.startsWith("0"); str2 = str2.substring(1, str2.length()));
        callableStatement.setString(3, str2);
        String str3;
        for (str3 = vector.get(2); str3.startsWith("0"); str3 = str3.substring(1, str3.length()));
        callableStatement.setString(4, str3);
        callableStatement.setString(5, "1");
        callableStatement.addBatch();
      } 
      callableStatement.executeBatch();
      connection.commit();
      connection.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return bool;
  }
  
  public Vector checkLogDataExist(Vector paramVector, int paramInt) throws Exception {
    Vector vector = new Vector();
    String str = "";
    System.err.println("?" + paramVector);
    switch (paramInt) {
      case 0:
        str = "{call sp_getLogUploadLibrary(?,?,?,?)}";
        break;
      case 1:
        str = "{call sp_getLogCategory(?,?,?,?)}";
        break;
      case 2:
        str = "{call sp_getLogBackupLibrary(?,?,?,?)}";
        break;
    } 
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector1 = paramVector.get(b);
      Connection connection = getNewConn();
      CallableStatement callableStatement = connection.prepareCall(str);
      callableStatement.setString(1, vector1.get(0));
      callableStatement.setString(2, vector1.get(1));
      callableStatement.setString(3, vector1.get(3));
      callableStatement.setString(4, vector1.get(4));
      ResultSet resultSet = callableStatement.executeQuery();
    } 
    return vector;
  }
  
  public Vector getBTM(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getBTM(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getBtnEvent(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getBtnEvent(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getCategory(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getCategory(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getColumnName(String paramString) throws Exception {
    Vector vector = new Vector();
    Connection connection = null;
    try {
      connection = getNewConn();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(paramString);
      int i = resultSet.getMetaData().getColumnCount();
      for (byte b = 1; b <= i; b++)
        vector.addElement(resultSet.getMetaData().getColumnName(b).toString()); 
    } catch (Exception exception) {
      throw exception;
    } finally {
      try {
        connection.close();
      } catch (Exception exception) {
        throw exception;
      } 
    } 
    return vector;
  }
  
  public Vector getCurve(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getCurve(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getCurveToDraw(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getCurveToDraw(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getData(String paramString) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(paramString);
      int i = resultSet.getMetaData().getColumnCount();
      vector = _$3968(resultSet);
    } catch (Exception exception) {
      throw exception;
    } finally {
      try {
        connection.close();
      } catch (Exception exception) {
        throw exception;
      } 
    } 
    return vector;
  }
  
  public Vector getDrawDistToTarget(String paramString1, String paramString2, String paramString3) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    String str = paramString3.substring(0, 4) + "/";
    str = str + paramString3.substring(5, 7) + "/";
    str = str + paramString3.substring(8, 10);
    CallableStatement callableStatement = connection.prepareCall("{call sp_getDynamic(?,?,?)}");
    callableStatement.setString(1, paramString1);
    callableStatement.setString(2, paramString2);
    callableStatement.setString(3, str);
    ResultSet resultSet = callableStatement.executeQuery();
    while (resultSet.next()) {
      Vector vector1 = new Vector();
      vector1.add(new Long(resultSet.getTimestamp(1).getTime()));
      int i = resultSet.getInt(3);
      if (i < 0) {
        vector1.add(new Integer(-1));
      } else {
        vector1.add(new Integer((i - 1000000000) / 100));
      } 
      vector.addElement(vector1);
    } 
    connection.close();
    return vector;
  }
  
  public Vector getDrawLocation(String paramString1, String paramString2, String paramString3) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    String str = paramString3.substring(0, 4) + "/";
    str = str + paramString3.substring(5, 7) + "/";
    str = str + paramString3.substring(8, 10);
    CallableStatement callableStatement = connection.prepareCall("{call sp_getTS(?,?,?)}");
    callableStatement.setString(1, paramString1);
    callableStatement.setString(2, paramString2);
    callableStatement.setString(3, str);
    ResultSet resultSet = callableStatement.executeQuery();
    while (resultSet.next()) {
      Vector vector1 = new Vector();
      vector1.add(new Long(resultSet.getTimestamp(1).getTime()));
      int i = resultSet.getInt(3) - 1000000000;
      i /= 10000;
      vector1.add(new Integer(i));
      vector.add(vector1);
    } 
    connection.close();
    return vector;
  }
  
  public Vector getDriverMessage(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getDriverMessage(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getDriverMessage_Ack(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getDriverMessage_Ack(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getDynamic(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getDynamic(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getFailure(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getFailure(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getGradient(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getGradient(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getGradientToDraw(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getGradientToDraw(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getLineNames() throws Exception {
    return getColumnName("sp_getLineInfo");
  }
  
  public Vector getLines() throws Exception {
    return getData("sp_getLineInfo");
  }
  
  public Vector getLogBackupSaveDate(Vector paramVector) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    System.err.println(paramVector);
    CallableStatement callableStatement = connection.prepareCall("{call sp_getLogBackupSaveDate(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramVector.get(0)));
    callableStatement.setString(2, (String)paramVector.get(1));
    callableStatement.setString(3, (String)paramVector.get(2));
    callableStatement.setString(4, (String)paramVector.get(3));
    callableStatement.setString(5, (String)paramVector.get(4));
    ResultSet resultSet = callableStatement.executeQuery();
    int i = resultSet.getMetaData().getColumnCount();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getLogDecodeCatagory(Vector paramVector) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getCategory(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramVector.get(0)));
    callableStatement.setString(2, (String)paramVector.get(1));
    callableStatement.setString(3, (String)paramVector.get(2));
    callableStatement.setString(4, (String)paramVector.get(3));
    callableStatement.setString(5, (String)paramVector.get(4));
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getLogDecodeCategory(Object[] paramArrayOfObject) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getCategory(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format((Date)paramArrayOfObject[0]));
    callableStatement.setString(2, (String)paramArrayOfObject[1]);
    callableStatement.setString(3, (String)paramArrayOfObject[2]);
    callableStatement.setString(4, (String)paramArrayOfObject[3]);
    callableStatement.setString(5, (String)paramArrayOfObject[4]);
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getLogList() throws Exception {
    Vector vector;
    Connection connection = getNewConn();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("sp_getLogList");
      vector = _$3968(resultSet);
    } catch (Exception exception) {
      throw exception;
    } finally {
      try {
        connection.close();
      } catch (Exception exception) {
        throw exception;
      } 
    } 
    return vector;
  }
  
  public Vector getLogUploadSaveDate(Vector paramVector) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getLogUploadSaveDate(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramVector.get(0)));
    callableStatement.setString(2, (String)paramVector.get(1));
    callableStatement.setString(3, (String)paramVector.get(2));
    callableStatement.setString(4, (String)paramVector.get(3));
    callableStatement.setString(5, (String)paramVector.get(4));
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Connection getNewConn() throws Exception {
    null = null;
    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
    return DriverManager.getConnection("jdbc:microsoft:sqlserver://" + InitParameters.SQLName + ":1433", InitParameters.SQLUserName, InitParameters.SQLUserPWD);
  }
  
  public Vector getStation() throws Exception {
    Connection connection = getNewConn();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT StationCName, StationEName, InnerID FROM Station ORDER BY InnerID");
    return _$3968(resultSet);
  }
  
  public Vector getStationNames() throws Exception {
    return getColumnName("sp_getStationInfo");
  }
  
  public Vector getStations() throws Exception {
    return getData("sp_getStationInfo");
  }
  
  public Vector getStatus(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getStatus(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getTRMapping() throws Exception {
    null = new Vector();
    Connection connection = null;
    connection = getNewConn();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT train_type, train_sn FROM Train_Category ORDER BY train_type");
    return _$3968(resultSet);
  }
  
  public Vector getTS(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getTS(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Vector getTrainData(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getTrainData(?, ?, ?, ?, ?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  public Connection getTransactionConn() throws Exception {
    null = null;
    return getNewConn();
  }
  
  public Vector getVDX(ATPMission paramATPMission) throws Exception {
    Vector vector = new Vector();
    Connection connection = getNewConn();
    CallableStatement callableStatement = connection.prepareCall("{call sp_getVDX(?,?,?,?,?)}");
    callableStatement.setString(1, this.sdf.format(paramATPMission.getMissionDate()));
    callableStatement.setString(2, paramATPMission.getWorkShift());
    callableStatement.setString(3, paramATPMission.getTrainRunning());
    callableStatement.setString(4, paramATPMission.getDriver());
    callableStatement.setString(5, paramATPMission.getVehicle());
    ResultSet resultSet = callableStatement.executeQuery();
    vector = _$3968(resultSet);
    connection.close();
    return vector;
  }
  
  private Vector _$3968(ResultSet paramResultSet) throws SQLException {
    Vector vector = new Vector();
    int i = paramResultSet.getMetaData().getColumnCount();
    while (paramResultSet.next()) {
      Vector vector1 = new Vector();
      for (byte b = 1; b <= i; b++) {
        if (paramResultSet.getMetaData().getColumnClassName(b) == "java.sql.Timestamp") {
          try {
            vector1.add(new Date(paramResultSet.getTimestamp(b).getTime()));
          } catch (NullPointerException nullPointerException) {
            vector1.add(null);
          } 
        } else if (paramResultSet.getMetaData().getColumnClassName(b) == "java.lang.Integer") {
          vector1.add(new Integer(paramResultSet.getInt(b)));
        } else if (paramResultSet.getMetaData().getColumnClassName(b) == "java.lang.Double") {
          vector1.add(new Double(paramResultSet.getDouble(b)));
        } else if (paramResultSet.getMetaData().getColumnClassName(b) == "java.lang.Boolean") {
          vector1.add(new Boolean(paramResultSet.getBoolean(b)));
        } else if (paramResultSet.getMetaData().getColumnClassName(b) == "java.lang.String") {
          try {
            vector1.add(paramResultSet.getString(b));
          } catch (SQLException sQLException) {
            vector1.add(null);
          } 
        } else {
          vector1.add(paramResultSet.getObject(b));
        } 
      } 
      vector.addElement(vector1);
    } 
    return vector;
  }
}
