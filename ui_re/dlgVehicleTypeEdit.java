package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.dlgVehicleTypeEdit_btnCancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.dlgVehicleTypeEdit_btnConfirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMain;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class dlgVehicleTypeEdit extends JDialog {
  String VehicleTypeEdit = "";
  
  JButton btnCancel = new JButton();
  
  JButton btnConfirm = new JButton();
  
  ConnectDB cndb = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  JLabel lblVehicleTypeExample = new JLabel();
  
  JLabel lblVehicleTypeFormat = new JLabel();
  
  JLabel lblVehicleTypeNo = new JLabel();
  
  boolean newVehicleID = false;
  
  JPanel panel1 = new JPanel();
  
  JTextField txtVehicleFormat = new JTextField();
  
  JTextField txtVehicleType = new JTextField();
  
  public dlgVehicleTypeEdit(Frame paramFrame, String paramString) {
    this(paramFrame);
    this.newVehicleID = false;
    setTitle(ATPMessages.getString("MW.VEHICLE.TYPE.MODIFY"));
    try {
      Vector vector = this.cndb.getData("Select * from VehicleType where VehicleType ='" + paramString + "'");
      vector = vector.get(0);
      this.txtVehicleType.setText((String)vector.get(0));
      this.txtVehicleFormat.setText((String)vector.get(1));
      this.VehicleTypeEdit = paramString;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public dlgVehicleTypeEdit(Frame paramFrame) {
    super(paramFrame, "", true);
    this.newVehicleID = true;
    setTitle(ATPMessages.getString("MW.VEHICLE.TYPE.NEW"));
    try {
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
      if (JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.GNL.CANCEL.Q.CONFIRM"), ATPMessages.getString("MW.GNL.CANCEL"), 0, 3) == 0)
        dispose(); 
    } else {
      dispose();
    } 
  }
  
  void btnConfirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (_$21000() && _$21001()) {
        if (this.newVehicleID) {
          if (_$21002()) {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.ID.EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0);
          } else {
            _$21003();
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.TYPE.NEW.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
            dispose();
          } 
        } else {
          _$21004();
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.TYPE.MODIFY.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
          dispose();
        } 
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.VEHICLE.ID.LENGTH<5") + "\n" + ATPMessages.getString("MW.VEHICLE.ID.START_WITH_EN"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private boolean _$21001() {
    if (this.txtVehicleFormat.getText().length() > 5)
      return false; 
    if (this.txtVehicleFormat.getText().indexOf("#") < 0)
      return false; 
    boolean bool = false;
    for (byte b = 0; b < this.txtVehicleFormat.getText().length(); b++) {
      char c = this.txtVehicleFormat.getText().charAt(b);
      if (bool) {
        if (c != '#')
          return false; 
      } else {
        switch (b) {
          case 0:
            if (c == '#') {
              bool = true;
              break;
            } 
            if (!Character.isLetter(c))
              return false; 
            break;
          case 4:
            if (c != '#')
              return false; 
          case 1:
          case 2:
          case 3:
            if (c == '#') {
              bool = true;
              break;
            } 
            if (!Character.isDigit(c))
              return false; 
            break;
        } 
      } 
    } 
    return true;
  }
  
  private boolean _$21000() {
    if (this.txtVehicleType.getText().length() > 5)
      return false; 
    for (byte b = 0; b < this.txtVehicleType.getText().length(); b++) {
      char c = this.txtVehicleType.getText().charAt(b);
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
    this.txtVehicleType.setPreferredSize(new Dimension(57, 23));
    this.txtVehicleType.setText("");
    this.txtVehicleFormat.setPreferredSize(new Dimension(57, 23));
    this.txtVehicleFormat.setText("");
    this.btnConfirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnConfirm.addActionListener((ActionListener)new dlgVehicleTypeEdit_btnConfirm_actionAdapter(this));
    this.lblVehicleTypeNo.setText(ATPMessages.getString("MW.VEHICLE.TYPE"));
    this.lblVehicleTypeFormat.setText(ATPMessages.getString("MW.VEHICLE.ID.FORMAT"));
    this.lblVehicleTypeExample.setText(ATPMessages.getString("MW.VEHICLE.TYPE.EXAMPLE"));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new dlgVehicleTypeEdit_btnCancel_actionAdapter(this));
    this.panel1.add(this.lblVehicleTypeNo, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.txtVehicleType, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.lblVehicleTypeFormat, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.txtVehicleFormat, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.lblVehicleTypeExample, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.btnConfirm, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    this.panel1.add(this.btnCancel, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(3, 3, 3, 3), 0, 0));
    getContentPane().add(this.panel1, "North");
  }
  
  private void _$21003() throws Exception {
    this.cndb.Insert("insert into VehicleType(VehicleType, VehicleFormat) values('" + this.txtVehicleType.getText() + "', '" + this.txtVehicleFormat.getText() + "')");
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "ATP動力車型", "新增", this.lblVehicleTypeNo.getText() + ":" + this.txtVehicleType.getText() + "," + this.lblVehicleTypeFormat.getText() + ":" + this.txtVehicleFormat.getText());
  }
  
  private boolean _$21002() throws Exception {
    Vector vector = this.cndb.getData("select * from VehicleType where VehicleType ='" + this.txtVehicleType.getText() + "'");
    return (vector.size() > 0);
  }
  
  private void _$21004() throws Exception {
    this.cndb.Update("update VehicleType set VehicleType = '" + this.txtVehicleType.getText() + "', VehicleFormat = '" + this.txtVehicleFormat.getText() + "' WHERE VehicleType ='" + this.VehicleTypeEdit + "'");
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "ATP動力車型", "修改", this.lblVehicleTypeNo.getText() + ":" + this.txtVehicleType.getText() + "," + this.lblVehicleTypeFormat.getText() + ":" + this.txtVehicleFormat.getText());
  }
}
