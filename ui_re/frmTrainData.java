package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmMain;
import com.MiTAC.TRA.ATP.ui.frmTrainData_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_commit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtEB_deceleration_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtEB_delay_time_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtLength_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtMax_Acceleration_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtMax_Deceleration_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtSB_deceleration_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtSB_delay_time_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtSpeed_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtTrain_Kind_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtWeight_Empty_keyAdapter;
import com.MiTAC.TRA.ATP.ui.frmTrainData_txtWeight_Full_keyAdapter;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class frmTrainData extends JDialog {
  JButton cancel = new JButton();
  
  Vector category;
  
  JComboBox cmbTrainType = new JComboBox();
  
  JButton commit = new JButton();
  
  ConnectDB conDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  boolean insert;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel10 = new JLabel();
  
  JLabel jLabel11 = new JLabel();
  
  JLabel jLabel12 = new JLabel();
  
  JLabel jLabel13 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JLabel jLabel6 = new JLabel();
  
  JLabel jLabel7 = new JLabel();
  
  JLabel jLabel8 = new JLabel();
  
  JLabel jLabel9 = new JLabel();
  
  JLabel lblTitle = new JLabel("--");
  
  JPanel panel1 = new JPanel();
  
  Vector trainType;
  
  JTextField txtEB_deceleration = new JTextField();
  
  JTextField txtEB_delay_time = new JTextField();
  
  JTextField txtLength = new JTextField();
  
  JTextField txtMax_Acceleration = new JTextField();
  
  JTextField txtMax_Deceleration = new JTextField();
  
  JTextField txtSB_deceleration = new JTextField();
  
  JTextField txtSB_delay_time = new JTextField();
  
  JTextField txtSpeed = new JTextField();
  
  JTextField txtTrain_Kind = new JTextField();
  
  JTextField txtTrain_SN = new JTextField();
  
  JTextField txtWeight_Empty = new JTextField();
  
  JTextField txtWeight_Full = new JTextField();
  
  boolean update;
  
  public frmTrainData(Vector paramVector1, Vector paramVector2, Vector paramVector3, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      _$3120();
      this.lblTitle.setText(ATPMessages.getString("MW.TD.MODIFY"));
      this.update = true;
      this.trainType = paramVector1;
      this.category = paramVector2;
      this.txtTrain_SN.setEditable(false);
      this.txtTrain_Kind.setEditable(false);
      this.txtTrain_SN.setText(paramVector3.elementAt(0).toString());
      this.txtTrain_Kind.setText(paramVector3.elementAt(1).toString());
      this.txtSpeed.setText(paramVector3.elementAt(2).toString());
      this.txtLength.setText(paramVector3.elementAt(3).toString());
      this.txtWeight_Empty.setText(paramVector3.elementAt(4).toString());
      this.txtWeight_Full.setText(paramVector3.elementAt(5).toString());
      this.txtMax_Acceleration.setText(paramVector3.elementAt(6).toString());
      this.txtSB_delay_time.setText(paramVector3.elementAt(7).toString());
      this.txtSB_deceleration.setText(paramVector3.elementAt(8).toString());
      this.txtEB_delay_time.setText(paramVector3.elementAt(9).toString());
      this.txtEB_deceleration.setText(paramVector3.elementAt(10).toString());
      this.txtMax_Deceleration.setText(paramVector3.elementAt(11).toString());
      pack();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      exception.printStackTrace();
    } 
  }
  
  public frmTrainData(Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      _$3120();
      this.lblTitle.setText(ATPMessages.getString("MW.TD.NEW"));
      this.insert = true;
      this.trainType = paramVector;
      pack();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      exception.printStackTrace();
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    if (this.update) {
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.TD.CANCEL.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0)
        dispose(); 
    } else {
      dispose();
    } 
  }
  
  private void _$21646() {
    for (byte b = 0; b < this.trainType.size(); b++)
      this.cmbTrainType.addItem(((Vector)this.trainType.get(b)).get(0)); 
  }
  
  void commit_actionPerformed(ActionEvent paramActionEvent) {
    Object object1 = null;
    Object object2 = null;
    try {
      if (this.insert) {
        String str1 = "INSERT INTO Train_Data VALUES('" + this.txtTrain_SN.getText() + "','" + this.txtTrain_Kind.getText() + "'," + _$931(this.txtSpeed.getText().length(), this.txtSpeed.getText()) + "," + _$931(this.txtLength.getText().length(), this.txtLength.getText()) + "," + _$931(this.txtWeight_Empty.getText().length(), this.txtWeight_Empty.getText()) + "," + _$931(this.txtWeight_Full.getText().length(), this.txtWeight_Full.getText()) + "," + _$931(this.txtMax_Acceleration.getText().length(), this.txtMax_Acceleration.getText()) + "," + _$931(this.txtSB_delay_time.getText().length(), this.txtSB_delay_time.getText()) + "," + _$931(this.txtSB_deceleration.getText().length(), this.txtSB_deceleration.getText()) + "," + _$931(this.txtEB_delay_time.getText().length(), this.txtEB_delay_time.getText()) + "," + _$931(this.txtEB_deceleration.getText().length(), this.txtEB_deceleration.getText()) + "," + _$931(this.txtMax_Deceleration.getText().length(), this.txtMax_Deceleration.getText()) + ")";
        this.conDB.Insert(str1);
        String str2 = ATPMessages.getString("MW.TrainTypeID") + ":" + this.txtTrain_SN.getText() + "," + ATPMessages.getString("MW.TrainTypeName") + ":" + this.txtTrain_Kind.getText() + "," + ATPMessages.getString("MW.MaxSpeed(Km/h)") + ":" + _$931(this.txtSpeed.getText().length(), this.txtSpeed.getText()) + "," + ATPMessages.getString("MW.TrainLength(m)") + ":" + _$931(this.txtLength.getText().length(), this.txtLength.getText()) + "," + ATPMessages.getString("MW.TrainWeight(Empty)") + ":" + _$931(this.txtWeight_Empty.getText().length(), this.txtWeight_Empty.getText()) + "," + ATPMessages.getString("MW.TrainWeight(Full)") + ":" + _$931(this.txtWeight_Full.getText().length(), this.txtWeight_Full.getText()) + "," + ATPMessages.getString("MW.8") + ":" + _$931(this.txtMax_Acceleration.getText().length(), this.txtMax_Acceleration.getText()) + "," + ATPMessages.getString("MW.9") + ":" + _$931(this.txtSB_delay_time.getText().length(), this.txtSB_delay_time.getText()) + "," + ATPMessages.getString("MW.10") + ":" + _$931(this.txtSB_deceleration.getText().length(), this.txtSB_deceleration.getText()) + "," + ATPMessages.getString("MW.11") + ":" + _$931(this.txtEB_delay_time.getText().length(), this.txtEB_delay_time.getText()) + "," + ATPMessages.getString("MW.EmergencyBrakeDeceleration(m/s^2)") + ":" + _$931(this.txtEB_deceleration.getText().length(), this.txtEB_deceleration.getText()) + "," + "列車最大減速率(m/s^2)" + ":" + _$931(this.txtMax_Deceleration.getText().length(), this.txtMax_Deceleration.getText());
        this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "列車編組", "新增", str2);
        dispose();
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.NEW.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
      } 
      if (this.update) {
        String str1 = "UPDATE Train_Data SET Train_SN ='" + this.txtTrain_SN.getText() + "',Train_Kind = '" + this.txtTrain_Kind.getText() + "',Speed = " + _$931(this.txtSpeed.getText().length(), this.txtSpeed.getText()) + ",Length = " + _$931(this.txtLength.getText().length(), this.txtLength.getText()) + ",Weight_Empty = " + _$931(this.txtWeight_Empty.getText().length(), this.txtWeight_Empty.getText()) + ",Weight_Full = " + _$931(this.txtWeight_Full.getText().length(), this.txtWeight_Full.getText()) + ",Max_Acceleration =" + _$931(this.txtMax_Acceleration.getText().length(), this.txtMax_Acceleration.getText()) + ",SB_delay_time =" + _$931(this.txtSB_delay_time.getText().length(), this.txtSB_delay_time.getText()) + ",SB_deceleration = " + _$931(this.txtSB_deceleration.getText().length(), this.txtSB_deceleration.getText()) + ",EB_delay_time =" + _$931(this.txtEB_delay_time.getText().length(), this.txtEB_delay_time.getText()) + ",EB_deceleration = " + _$931(this.txtEB_deceleration.getText().length(), this.txtEB_deceleration.getText()) + ",Max_deceleration = " + _$931(this.txtMax_Deceleration.getText().length(), this.txtMax_Deceleration.getText()) + " WHERE Train_SN='" + this.txtTrain_SN.getText() + "'";
        this.conDB.Update(str1);
        String str2 = ATPMessages.getString("MW.TrainTypeID") + ":" + this.txtTrain_SN.getText() + "," + ATPMessages.getString("MW.TrainTypeName") + ":" + this.txtTrain_Kind.getText() + "," + ATPMessages.getString("MW.MaxSpeed(Km/h)") + ":" + _$931(this.txtSpeed.getText().length(), this.txtSpeed.getText()) + "," + ATPMessages.getString("MW.TrainLength(m)") + ":" + _$931(this.txtLength.getText().length(), this.txtLength.getText()) + "," + ATPMessages.getString("MW.TrainWeight(Empty)") + ":" + _$931(this.txtWeight_Empty.getText().length(), this.txtWeight_Empty.getText()) + "," + ATPMessages.getString("MW.TrainWeight(Full)") + ":" + _$931(this.txtWeight_Full.getText().length(), this.txtWeight_Full.getText()) + "," + ATPMessages.getString("MW.8") + ":" + _$931(this.txtMax_Acceleration.getText().length(), this.txtMax_Acceleration.getText()) + "," + ATPMessages.getString("MW.9") + ":" + _$931(this.txtSB_delay_time.getText().length(), this.txtSB_delay_time.getText()) + "," + ATPMessages.getString("MW.10") + ":" + _$931(this.txtSB_deceleration.getText().length(), this.txtSB_deceleration.getText()) + "," + ATPMessages.getString("MW.11") + ":" + _$931(this.txtEB_delay_time.getText().length(), this.txtEB_delay_time.getText()) + "," + ATPMessages.getString("MW.EmergencyBrakeDeceleration(m/s^2)") + ":" + _$931(this.txtEB_deceleration.getText().length(), this.txtEB_deceleration.getText()) + "," + "列車最大減速率(m/s^2)" + ":" + _$931(this.txtMax_Deceleration.getText().length(), this.txtMax_Deceleration.getText());
        this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "列車編組", "修改", str2);
        dispose();
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.MODIFY.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
      } 
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      if (sQLException.getErrorCode() == 2627)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
      JOptionPane.showMessageDialog(this, sQLException.getErrorCode() + sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      exception.printStackTrace();
    } 
  }
  
  private String _$931(int paramInt, String paramString) {
    null = "null";
    return (paramInt == 0) ? null : paramString;
  }
  
  private void _$3120() throws Exception {
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setFont(new Font("Dialog", 1, 16));
    this.panel1.setLayout(new GridBagLayout());
    this.jLabel1.setText(ATPMessages.getString("MW.TD.ID"));
    this.jLabel2.setText(ATPMessages.getString("MW.TD.NAME"));
    this.jLabel3.setText(ATPMessages.getString("MW.TD.SPEED(Km/hr)"));
    this.jLabel4.setText(ATPMessages.getString("MW.TD.LENGTH(m)"));
    this.jLabel5.setText(ATPMessages.getString("MW.TD.WEIGHT_EMPTY(ton)"));
    this.jLabel6.setRequestFocusEnabled(true);
    this.jLabel6.setText(ATPMessages.getString("MW.TD.WEIGHT_FULL(ton)"));
    this.jLabel7.setText(ATPMessages.getString("MW.TD.MAX_ACCELERATION(m/s/s)"));
    this.jLabel8.setText(ATPMessages.getString("MW.TD.SB_DELAY_TIME(s)"));
    this.jLabel9.setText(ATPMessages.getString("MW.TD.SB_DECELERATION(m/s/s)"));
    this.jLabel10.setText(ATPMessages.getString("MW.TD.EB_DELAY_TIME(s)"));
    this.jLabel11.setText(ATPMessages.getString("MW.TD.EB_DECELERATION(m/s/s)"));
    this.jLabel12.setText(ATPMessages.getString("MW.TD.MAX_DECELERATION(m/s/s)"));
    this.txtTrain_SN.setText("");
    this.txtTrain_SN.setPreferredSize(new Dimension(50, 20));
    this.txtTrain_SN.addKeyListener((KeyListener)new frmTrainData_txtSpeed_keyAdapter(this));
    this.txtTrain_Kind.setSelectionStart(0);
    this.txtTrain_Kind.setPreferredSize(new Dimension(300, 20));
    this.txtTrain_Kind.addActionListener((ActionListener)new frmTrainData_txtTrain_Kind_actionAdapter(this));
    this.txtSpeed.setText("");
    this.txtSpeed.addKeyListener((KeyListener)new frmTrainData_txtSpeed_keyAdapter(this));
    this.txtLength.setText("");
    this.txtLength.addKeyListener((KeyListener)new frmTrainData_txtLength_keyAdapter(this));
    this.txtWeight_Empty.setText("");
    this.txtWeight_Empty.addKeyListener((KeyListener)new frmTrainData_txtWeight_Empty_keyAdapter(this));
    this.txtWeight_Full.setText("");
    this.txtWeight_Full.addKeyListener((KeyListener)new frmTrainData_txtWeight_Full_keyAdapter(this));
    this.txtMax_Acceleration.setText("");
    this.txtMax_Acceleration.addKeyListener((KeyListener)new frmTrainData_txtMax_Acceleration_keyAdapter(this));
    this.txtSB_delay_time.setText("");
    this.txtSB_delay_time.addKeyListener((KeyListener)new frmTrainData_txtSB_delay_time_keyAdapter(this));
    this.txtSB_deceleration.setText("");
    this.txtSB_deceleration.addKeyListener((KeyListener)new frmTrainData_txtSB_deceleration_keyAdapter(this));
    this.txtEB_delay_time.setText("");
    this.txtEB_delay_time.addKeyListener((KeyListener)new frmTrainData_txtEB_delay_time_keyAdapter(this));
    this.txtEB_deceleration.setText("");
    this.txtEB_deceleration.addKeyListener((KeyListener)new frmTrainData_txtEB_deceleration_keyAdapter(this));
    this.txtMax_Deceleration.setText("");
    this.txtMax_Deceleration.addKeyListener((KeyListener)new frmTrainData_txtMax_Deceleration_keyAdapter(this));
    this.commit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.commit.addActionListener((ActionListener)new frmTrainData_commit_actionAdapter(this));
    this.cancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.cancel.addActionListener((ActionListener)new frmTrainData_cancel_actionAdapter(this));
    this.panel1.add(this.jLabel1, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtTrain_SN, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel2, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtTrain_Kind, new GridBagConstraints(2, 2, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel3, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtSpeed, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel4, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtLength, new GridBagConstraints(2, 4, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel5, new GridBagConstraints(1, 5, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtWeight_Empty, new GridBagConstraints(2, 5, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel6, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtWeight_Full, new GridBagConstraints(2, 6, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel7, new GridBagConstraints(1, 7, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtMax_Acceleration, new GridBagConstraints(2, 7, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel8, new GridBagConstraints(1, 8, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtSB_delay_time, new GridBagConstraints(2, 8, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel9, new GridBagConstraints(1, 9, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtSB_deceleration, new GridBagConstraints(2, 9, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel10, new GridBagConstraints(1, 10, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtEB_delay_time, new GridBagConstraints(2, 10, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel11, new GridBagConstraints(1, 11, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtEB_deceleration, new GridBagConstraints(2, 11, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.jLabel12, new GridBagConstraints(1, 12, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.panel1.add(this.txtMax_Deceleration, new GridBagConstraints(2, 12, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.commit, new GridBagConstraints(3, 13, 1, 1, 1.0D, 0.0D, 13, 0, new Insets(0, 0, 2, 2), 0, 0));
    this.panel1.add(this.cancel, new GridBagConstraints(4, 13, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 2, 2), 0, 0));
    getContentPane().add(this.lblTitle, "North");
    getContentPane().add(this.panel1, "Center");
    pack();
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  void txtEB_deceleration_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtEB_deceleration_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtEB_deceleration.select(this.txtEB_deceleration.getText().length() - 1, this.txtEB_deceleration.getText().length());
      }  
  }
  
  void txtEB_delay_time_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtEB_delay_time_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtEB_delay_time.select(this.txtEB_delay_time.getText().length() - 1, this.txtEB_delay_time.getText().length());
      }  
  }
  
  void txtLength_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtLength_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtLength.select(this.txtLength.getText().length() - 1, this.txtLength.getText().length());
      }  
  }
  
  void txtMax_Acceleration_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtMax_Acceleration_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtMax_Acceleration.select(this.txtMax_Acceleration.getText().length() - 1, this.txtMax_Acceleration.getText().length());
      }  
  }
  
  void txtMax_Deceleration_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtMax_Deceleration.select(this.txtMax_Deceleration.getText().length() - 1, this.txtMax_Deceleration.getText().length());
      }  
  }
  
  void txtSB_deceleration_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtSB_deceleration_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtSB_deceleration.select(this.txtSB_deceleration.getText().length() - 1, this.txtSB_deceleration.getText().length());
      }  
  }
  
  void txtSB_delay_time_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtSB_delay_time_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtSB_delay_time.select(this.txtSB_delay_time.getText().length() - 1, this.txtSB_delay_time.getText().length());
      }  
  }
  
  void txtSpeed_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtSpeed_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtSpeed.select(this.txtSpeed.getText().length() - 1, this.txtSpeed.getText().length());
      }  
  }
  
  void txtTrain_Kind_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtTrain_SN_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtWeight_Empty_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtWeight_Empty_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtWeight_Empty.select(this.txtWeight_Empty.getText().length() - 1, this.txtWeight_Empty.getText().length());
      }  
  }
  
  void txtWeight_Full_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtWeight_Full_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() != '0' && paramKeyEvent.getKeyChar() != '1' && paramKeyEvent.getKeyChar() != '2' && paramKeyEvent.getKeyChar() != '3' && paramKeyEvent.getKeyChar() != '4' && paramKeyEvent.getKeyChar() != '5' && paramKeyEvent.getKeyChar() != '6' && paramKeyEvent.getKeyChar() != '7' && paramKeyEvent.getKeyChar() != '8' && paramKeyEvent.getKeyChar() != '9' && paramKeyEvent.getKeyChar() != '.')
      if (paramKeyEvent.getKeyCode() == 10) {
        ((Component)paramKeyEvent.getSource()).transferFocus();
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TD.DIGITAL_ONLY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtWeight_Full.select(this.txtWeight_Full.getText().length() - 1, this.txtWeight_Full.getText().length());
      }  
  }
}
