package ui.dialogs;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.ui.adapters.dlgAboutManagementWorkstation_jButton1_actionAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class dlgAboutManagementWorkstation extends JDialog {
  JButton jButton1 = new JButton();
  
  JLabel jLabel10 = new JLabel();
  
  JLabel jLabel11 = new JLabel();
  
  JLabel jLabel12 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JLabel jLabel6 = new JLabel();
  
  JLabel jLabel7 = new JLabel();
  
  JLabel jLabel8 = new JLabel();
  
  JLabel jLabel9 = new JLabel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel panel1 = new JPanel();
  
  public dlgAboutManagementWorkstation() {
    this(null, "", false);
  }
  
  public dlgAboutManagementWorkstation(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      _$4300();
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$4300() throws Exception {
    this.panel1.setLayout(new GridBagLayout());
    this.jLabel4.setText(ATPMessages.getString("MW.ATP"));
    this.jLabel4.setForeground(SystemColor.desktop);
    this.jLabel4.setFont(new Font("標楷體", 1, 20));
    this.jLabel3.setFont(new Font("標楷體", 1, 20));
    this.jLabel3.setForeground(SystemColor.desktop);
    this.jLabel3.setText(ATPMessages.getString("MW.MW"));
    this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel2.setLayout(new GridBagLayout());
    this.jLabel5.setText(ATPMessages.getString("MW.TRA"));
    this.jLabel5.setForeground(SystemColor.desktop);
    this.jLabel5.setFont(new Font("標楷體", 1, 20));
    this.jLabel6.setText("Version: 3.5.4 Build: 100802");
    this.jLabel6.setForeground(new Color(58, 110, 175));
    this.jLabel6.setToolTipText("");
    this.jLabel6.setEnabled(true);
    this.jLabel6.setFont(new Font("新細明體", 1, 12));
    this.jLabel7.setEnabled(true);
    this.jLabel7.setFont(new Font("Dialog", 1, 18));
    this.jLabel7.setForeground(new Color(58, 60, 175));
    this.jLabel7.setText(ATPMessages.getString("MW.MITAC"));
    this.jLabel8.setEnabled(true);
    this.jLabel8.setFont(new Font("Dialog", 1, 14));
    this.jLabel8.setForeground(new Color(58, 60, 175));
    this.jLabel8.setHorizontalAlignment(10);
    this.jLabel8.setText(ATPMessages.getString("MW.MITAC.ATP.GROUP"));
    this.jLabel9.setText(ATPMessages.getString("MW.MITAC.ADDRESS"));
    this.jLabel9.setForeground(new Color(58, 60, 175));
    this.jLabel9.setFont(new Font("Dialog", 1, 12));
    this.jLabel10.setText(ATPMessages.getString("MW.MITAC.TEL"));
    this.jLabel10.setForeground(new Color(58, 60, 175));
    this.jLabel10.setFont(new Font("Dialog", 1, 12));
    this.jLabel11.setText(ATPMessages.getString("MW.MITAC"));
    this.jLabel11.setForeground(new Color(58, 60, 175));
    this.jLabel11.setFont(new Font("Times New Roman", 1, 12));
    this.jLabel12.setFont(new Font("Dialog", 1, 12));
    this.jLabel12.setForeground(new Color(58, 60, 175));
    this.jLabel12.setText(ATPMessages.getString("MW.MITAC.FAX"));
    this.jButton1.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.jButton1.addActionListener((ActionListener)new dlgAboutManagementWorkstation_jButton1_actionAdapter(this));
    this.jPanel2.add(this.jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(7, 7, 0, 196), 0, 0));
    this.jPanel2.add(this.jLabel4, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 87, 0, 95), 0, 0));
    this.jPanel2.add(this.jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(1, 175, 3, 28), 0, 0));
    this.jPanel2.add(this.jLabel6, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.panel1.add(this.jButton1, new GridBagConstraints(1, 7, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 53, 24, 42), 0, 0));
    this.panel1.add(this.jLabel8, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 42, 0, 16), 0, 0));
    this.panel1.add(this.jLabel9, new GridBagConstraints(0, 3, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 69, 0, 75), 0, 0));
    this.panel1.add(this.jLabel10, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 69, 0, 0), 0, 0));
    this.panel1.add(this.jLabel12, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 69, 0, 47), 0, 0));
    this.panel1.add(this.jLabel11, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 69, 0, 47), 0, 0));
    this.panel1.add(this.jLabel7, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(7, 20, 0, 32), 0, 0));
    getContentPane().add(this.panel1, "Center");
    this.panel1.add(this.jPanel2, new GridBagConstraints(0, 0, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(18, 20, 0, 19), 7, 5));
  }
  
  void jButton1_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
}
