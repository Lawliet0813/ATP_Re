package ui;

import com.MiTAC.TRA.ATP.ui.frmError_btnConfirm_actionAdapter;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

public class frmError extends JDialog {
  public static int MESSAGE_ONLY = 0;
  
  JButton btnConfirm = new JButton();
  
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  JTextArea txtMsg = new JTextArea();
  
  public frmError(String paramString1, Frame paramFrame, String paramString2, boolean paramBoolean) {
    super(paramFrame, paramString2, paramBoolean);
    try {
      jbInit();
      this.txtMsg.setText(paramString1);
      pack();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmError(String paramString1, Frame paramFrame, String paramString2, boolean paramBoolean, int paramInt) {
    super(paramFrame, paramString2, paramBoolean);
    try {
      jbInit();
      pack();
      this.txtMsg.setText(paramString1);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    if (paramInt == MESSAGE_ONLY)
      this.btnConfirm.setVisible(false); 
  }
  
  void btnConfirm_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  void jbInit() throws Exception {
    getContentPane().setLayout(this.gridBagLayout1);
    this.btnConfirm.setText("確認");
    this.btnConfirm.addActionListener((ActionListener)new frmError_btnConfirm_actionAdapter(this));
    this.txtMsg.setOpaque(false);
    this.txtMsg.setText("jTextArea124245454654564545645456454875645246----------------------------------------------------------------------------------------");
    this.txtMsg.setLineWrap(true);
    getContentPane().add(this.txtMsg, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(26, 62, 0, 11), 0, 5));
    getContentPane().add(this.btnConfirm, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(8, 297, 10, 39), 0, 0));
    setSize(new Dimension(400, 150));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  public void setConfirmVisiable(boolean paramBoolean) {
    this.btnConfirm.setVisible(paramBoolean);
    repaint();
  }
  
  public void setMessage(String paramString) {
    this.txtMsg.setText(paramString);
  }
}
