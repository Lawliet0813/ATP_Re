package ui.dialogs;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.adapters.dlgVehicleIDEdit_btnCancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.dlgVehicleIDEdit_btnConfirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

public class dlgVehicleIDEdit extends JDialog {
  JButton btnCancel = new JButton();
  
  JButton btnConfirm = new JButton();
  
  ConnectDB conn = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  JLabel lblVehicleFormat = new JLabel();
  
  JLabel lblVehicleID = new JLabel();
  
  JLabel lblVehicleIDFormat = new JLabel();
  
  JLabel lblVehicleType = new JLabel();
  
  JLabel lblVehicleTypeID = new JLabel();
  
  MaskFormatter mf;
  
  boolean newVehicleID = false;
  
  JPanel panel1 = new JPanel();
  
  JFormattedTextField txtVehicleID;
  
  String vehicleID = "";
  
  String vehicleType = "";
  
  public dlgVehicleIDEdit(Frame paramFrame, String paramString1, String paramString2) {
    this(paramFrame, paramString1);
    this.vehicleID = paramString2;
    this.txtVehicleID.setText(paramString2);
    this.newVehicleID = false;
    setTitle(ATPMessages.getString("MW.VEHICLE.ID.MODIFY"));
  }
  
  public dlgVehicleIDEdit(Frame paramFrame, String paramString) {
    super(paramFrame, "", true);
    this.newVehicleID = true;
    setTitle(ATPMessages.getString("MW.VEHICLE.ID.NEW"));
    try {
      this.vehicleType = paramString;
      _$3120();
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
      setResizable(false);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnCancel_actionPerformed(ActionEvent paramActionEvent) {
    if (!this.newVehicleID) {
      if (JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.GNL.CANCEL.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0, 3) == 0)
        dispose(); 
    } else {
      dispose();
    } 
  }
  
  void btnConfirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (_$21018()) {
        if (this.newVehicleID) {
          if (_$21017()) {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.ID.EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0);
          } else {
            _$21015();
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.ID.NEW.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
            dispose();
          } 
        } else {
          _$21016();
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.ID.MODIFY.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
          dispose();
        } 
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.ID.LENGTH<5") + "\n" + ATPMessages.getString("MW.VEHICLE.ID.START_WITH_EN"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private boolean _$21018() {
    if (this.txtVehicleID.getText().length() > 5)
      return false; 
    for (byte b = 0; b < this.txtVehicleID.getText().length(); b++) {
      char c = this.txtVehicleID.getText().charAt(b);
      switch (b) {
        case 0:
          if (!Character.isLetter(c))
            return false; 
          break;
        case 1:
        case 2:
        case 3:
        case 4:
          if (!Character.isDigit(c))
            return false; 
          break;
      } 
    } 
    return true;
  }
  
  private void _$3120() throws Exception {
    this.panel1.setLayout(new GridBagLayout());
    this.btnConfirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnConfirm.addActionListener((ActionListener)new dlgVehicleIDEdit_btnConfirm_actionAdapter(this));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new dlgVehicleIDEdit_btnCancel_actionAdapter(this));
    this.lblVehicleID.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.lblVehicleTypeID.setText(ATPMessages.getString("MW.VEHICLE.TYPE"));
    this.lblVehicleIDFormat.setText(ATPMessages.getString("MW.VEHICLE.ID.FORMAT"));
    this.lblVehicleType.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    getContentPane().add(this.panel1, "Center");
    Vector vector = this.conn.getData("select * from vehicleType where VehicleType ='" + this.vehicleType + "'");
    vector = vector.get(0);
    this.lblVehicleType.setText((String)vector.get(0));
    this.lblVehicleFormat.setText((String)vector.get(1));
    String str = (String)vector.get(1);
    str = str.replaceAll("U", "'U");
    str = str.replaceAll("L", "'L");
    str = str.replaceAll("A", "'A");
    str = str.replaceAll("H", "'H");
    this.mf = new MaskFormatter(str);
    this.mf.setPlaceholderCharacter('_');
    this.txtVehicleID = new JFormattedTextField(this.mf);
    this.txtVehicleID.setFocusLostBehavior(0);
    this.panel1.add(this.lblVehicleTypeID, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.lblVehicleType, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.lblVehicleIDFormat, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.lblVehicleFormat, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.lblVehicleID, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.txtVehicleID, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.btnConfirm, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.btnCancel, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
  }
  
  private void _$21015() throws Exception {
    this.conn.Insert("insert into VehicleID(VehicleType, VehicleID) values('" + this.vehicleType + "', '" + this.txtVehicleID.getText() + "')");
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "動力車頭號碼", "新增", this.lblVehicleTypeID.getText() + ":" + this.lblVehicleType.getText() + "," + this.lblVehicleIDFormat.getText() + ":" + this.txtVehicleID.getText());
  }
  
  private boolean _$21017() throws Exception {
    Vector vector = this.conn.getData("select * from VehicleID where VehicleID ='" + this.txtVehicleID.getText() + "'");
    return (vector.size() > 0);
  }
  
  private void _$21016() throws Exception {
    this.conn.Update("update VehicleID set VehicleID = '" + this.txtVehicleID.getText() + "' WHERE VehicleID ='" + this.vehicleID + "'");
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "動力車頭號碼", "修改", this.lblVehicleTypeID.getText() + ":" + this.lblVehicleType.getText() + "," + this.lblVehicleIDFormat.getText() + ":" + this.txtVehicleID.getText());
  }
}
