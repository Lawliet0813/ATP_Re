package Tools;

import com.MiTAC.TRA.ATP.Tools.BackupHandler;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.ui.frmMain;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class BackupDeleteFrame extends JDialog implements ActionListener {
  String rootPath;
  
  Vector orgiList;
  
  Vector userList;
  
  Vector selectList;
  
  File[] deleteFiles;
  
  Vector columns;
  
  private backupHandlerModel dtm;
  
  private JSortTable table;
  
  JButton btnDelete;
  
  JButton btnCancel;
  
  JButton btnSelectAll;
  
  JButton btnSelectNone;
  
  ProgressMonitor monitor;
  
  com.MiTAC.TRA.ATP.Tools.BackupDeleteFrame thisFrame;
  
  public BackupDeleteFrame(Frame owner, String path, Vector list) {
    super(owner, true);
    this.thisFrame = this;
    setTitle("已備份的檔案刪除.");
    init();
    this.rootPath = path;
    setData(list);
  }
  
  public void show() {
    if (this.userList.size() == 0) {
      JOptionPane.showMessageDialog(this, "找不到可以刪除的行車記錄. \n管理電腦現存的行車記錄都尚未被備份到MO光碟.", "訊息", 2);
      dispose();
    } else {
      super.show();
    } 
  }
  
  public void setData(Vector data) {
    this.orgiList = data;
    BackupHandler bh = new BackupHandler(this.rootPath, this.orgiList);
    Vector filterList = bh.getDeleteList();
    Vector filterList_G = bh.getDeleteList_G();
    this.userList = new Vector();
    try {
      for (int x = 0; x < filterList.size(); x++) {
        Vector vData = filterList.get(x);
        Vector tmp = new Vector();
        tmp.add(new Boolean(true));
        tmp.add(vData.get(0));
        tmp.add(vData.get(1));
        tmp.add(vData.get(2));
        tmp.add(vData.get(3));
        tmp.add(vData.get(4));
        tmp.add(vData.get(5));
        tmp.add(vData.get(6));
        tmp.add(filterList_G.get(x));
        this.userList.add(tmp);
      } 
    } catch (NullPointerException ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, ex.getMessage(), "錯誤", 0);
    } 
    refresh();
  }
  
  private void init() {
    Container container = getContentPane();
    container.setLayout(new BorderLayout());
    this.columns = new Vector();
    this.columns.add("刪除");
    this.columns.add("工作日期");
    this.columns.add("工作班");
    this.columns.add("車次");
    this.columns.add("司機員");
    this.columns.add("動力車號碼");
    this.columns.add("備份到MO");
    this.columns.add("備份日期");
    this.columns.add("hide");
    this.dtm = new backupHandlerModel(this);
    this.dtm.setDataVector(null, this.columns);
    this.table = new JSortTable((SortTableModel)this.dtm);
    JScrollPane scroll = new JScrollPane((Component)this.table);
    this.btnDelete = new JButton("刪除");
    this.btnDelete.setActionCommand("delete");
    this.btnDelete.addActionListener(this);
    this.btnCancel = new JButton("取消");
    this.btnCancel.setActionCommand("cancel");
    this.btnCancel.addActionListener(this);
    this.btnSelectAll = new JButton("選擇全部");
    this.btnSelectAll.setActionCommand("selectAll");
    this.btnSelectAll.addActionListener(this);
    this.btnSelectNone = new JButton("全部不選");
    this.btnSelectNone.setActionCommand("selectNone");
    this.btnSelectNone.addActionListener(this);
    JPanel pnlCmd = new JPanel();
    pnlCmd.setLayout(new GridBagLayout());
    pnlCmd.add(
        this.btnSelectAll, 
        new GridBagConstraints(
          0, 0, 1, 1, 0.0D, 0.0D, 
          10, 
          17, 
          new Insets(3, 3, 3, 3), 
          0, 0));
    pnlCmd.add(
        this.btnSelectNone, 
        new GridBagConstraints(
          1, 0, 1, 1, 1.0D, 0.0D, 
          17, 
          17, 
          new Insets(3, 3, 3, 3), 
          0, 0));
    pnlCmd.add(
        this.btnDelete, 
        new GridBagConstraints(
          2, 0, 1, 1, 0.0D, 0.0D, 
          10, 
          13, 
          new Insets(3, 3, 3, 3), 
          0, 0));
    pnlCmd.add(
        this.btnCancel, 
        new GridBagConstraints(
          3, 0, 1, 1, 0.0D, 0.0D, 
          10, 
          13, 
          new Insets(3, 3, 3, 3), 
          0, 0));
    container.add(scroll, "Center");
    container.add(pnlCmd, "South");
    setSize(new Dimension(800, 400));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screenSize.width - getWidth()) / 2, (
        screenSize.height - getHeight()) / 2);
  }
  
  private void refresh() {
    this.dtm.setDataVector(this.userList, this.columns);
    repaint();
    TableColumn missionDate = this.table.getColumnModel().getColumn(1);
    missionDate.setCellRenderer((TableCellRenderer)new Object(this));
    TableColumn backupDate = this.table.getColumnModel().getColumn(7);
    backupDate.setCellRenderer((TableCellRenderer)new Object(this));
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(this.btnCancel.getActionCommand())) {
      dispose();
    } else if (e.getActionCommand().equals(this.btnDelete.getActionCommand())) {
      doDelete();
    } else if (e.getActionCommand().equals(this.btnSelectAll.getActionCommand())) {
      doSelect(true);
    } else if (e.getActionCommand().equals(this.btnSelectNone.getActionCommand())) {
      doSelect(false);
    } 
  }
  
  private Vector filterSelect() {
    Vector returnSet = new Vector();
    Vector selectResult = this.dtm.getDataVector();
    for (int x = 0; x < selectResult.size(); x++) {
      Vector tmp = selectResult.get(x);
      if (((Boolean)tmp.get(0)).booleanValue())
        returnSet.add((Vector)tmp.get(8)); 
    } 
    return returnSet;
  }
  
  private void doDelete() {
    Vector deleteList = new Vector();
    deleteList = filterSelect();
    if (deleteList.size() == 0) {
      JOptionPane.showMessageDialog(this, "您至少需選擇一筆要刪除的行車記錄.", "錯誤", 0);
    } else {
      this.deleteFiles = BackupHandler.transferToFile(deleteList);
      for (int x = 0; x < this.deleteFiles.length; x++)
        System.out.println(
            "delete this: " + 
            deleteList.get(x) + " > " + 
            this.deleteFiles[x].getAbsolutePath()); 
      int cfm = JOptionPane.showConfirmDialog(this, createDeleteListPanel(), "詢問", 0, 3);
      if (cfm == 0) {
        PasswordInputDialog pwdInputDialog = new PasswordInputDialog(this, "密碼確認", true);
        pwdInputDialog.show();
        try {
          if (pwdInputDialog.getPassword().length() != 0) {
            CheckUser checkUser = new CheckUser(frmMain.getUserID(), pwdInputDialog.getPassword());
            if (checkUser.isPasswordRight()) {
              this.monitor = new ProgressMonitor(this, "刪除行車記錄", "list", 0, this.deleteFiles.length - 1);
              this.monitor.setMillisToDecideToPopup(0);
              this.monitor.setMillisToPopup(0);
              Object object = new Object(this);
              object.start();
            } 
          } 
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(this, "密碼輸入錯誤", "錯誤", 0);
        } 
      } 
    } 
  }
  
  public JPanel createDeleteListPanel() {
    JPanel deleteListPanel = new JPanel();
    deleteListPanel.setLayout(new BorderLayout());
    JLabel title = new JLabel("您選擇要刪除的行車記錄清單:");
    JTextArea txt = new JTextArea();
    txt.setEditable(false);
    JScrollPane scroll = new JScrollPane(txt);
    scroll.setPreferredSize(new Dimension(500, 200));
    JLabel confirm = new JLabel("確認要將這些行車記錄刪除?");
    deleteListPanel.add(title, "North");
    deleteListPanel.add(scroll, "Center");
    deleteListPanel.add(confirm, "South");
    StringBuffer sb = new StringBuffer();
    sb.append(
        String.valueOf(fillString("日期")) + "\t" + 
        fillString("工作班") + "\t" + 
        fillString("車次") + "\t" + 
        fillString("司機員") + "\t" + 
        fillString("動力車") + "\n");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    for (int x = 0; x < this.deleteFiles.length; x++) {
      try {
        Vector data = PathHandler.getDecodePath(this.deleteFiles[x].getAbsolutePath());
        sb.append(String.valueOf(fillString(sdf.format(data.get(0)))) + "\t" + 
            fillString(data.get(1).toString()) + "\t" + 
            fillString(data.get(2).toString()) + "\t" + 
            fillString(data.get(3).toString()) + "\t" + 
            fillString(data.get(4).toString()) + "\n");
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
    txt.setText(sb.toString());
    return deleteListPanel;
  }
  
  private String fillString(String str) {
    StringBuffer sb = new StringBuffer(str);
    for (int x = str.length(); x < 10; x++)
      sb.append(" "); 
    return sb.toString();
  }
  
  private void doSelect(boolean select) {
    Vector selectTmp = this.dtm.getDataVector();
    for (int x = 0; x < selectTmp.size(); x++) {
      Vector tmp = selectTmp.get(x);
      tmp.setElementAt(new Boolean(select), 0);
    } 
    repaint();
  }
  
  public static void main(String[] args) {
    try {
      InitParameters.start();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    Vector fake = new Vector();
    Vector tmp1 = new Vector();
    tmp1.add(new Date(105, 8, 9));
    tmp1.add("55555555");
    tmp1.add("006666BB");
    tmp1.add("000002");
    tmp1.add("M568");
    fake.add(tmp1);
    Vector tmp2 = new Vector();
    tmp2.add(new Date(105, 8, 9));
    tmp2.add("55555555");
    tmp2.add("006666BB");
    tmp2.add("000002");
    tmp2.add("C568");
    fake.add(tmp2);
    Vector tmp3 = new Vector();
    tmp3.add(new Date(105, 8, 9));
    tmp3.add("4598");
    tmp3.add("001515B");
    tmp3.add("6789");
    tmp3.add("M568");
    fake.add(tmp3);
    Vector tmp4 = new Vector();
    tmp4.add(new Date(105, 8, 9));
    tmp4.add("22222222");
    tmp4.add("003333AA");
    tmp4.add("000001");
    tmp4.add("C568");
    fake.add(tmp4);
    Vector tmp5 = new Vector();
    tmp5.add(new Date(105, 8, 9));
    tmp5.add("2222");
    tmp5.add("001515");
    tmp5.add("1111");
    tmp5.add("M568");
    fake.add(tmp5);
    Vector tmp6 = new Vector();
    tmp6.add(new Date(105, 8, 9));
    tmp6.add("1111");
    tmp6.add("001515");
    tmp6.add("1111");
    tmp6.add("M568");
    fake.add(tmp6);
    Vector tmp7 = new Vector();
    tmp7.add(new Date(105, 8, 9));
    tmp7.add("1111");
    tmp7.add("001515");
    tmp7.add("111");
    tmp7.add("M568");
    fake.add(tmp7);
    Vector tmp8 = new Vector();
    tmp8.add(new Date(105, 8, 9));
    tmp8.add("0000R882");
    tmp8.add("004205");
    tmp8.add("762754");
    tmp8.add("M568");
    fake.add(tmp8);
    com.MiTAC.TRA.ATP.Tools.BackupDeleteFrame bdf = new com.MiTAC.TRA.ATP.Tools.BackupDeleteFrame(null, InitParameters.MWLogPath, fake);
    bdf.show();
  }
}
