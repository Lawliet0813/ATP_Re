package ui.frames;

import com.MiTAC.TRA.ATP.ATPMessages;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

public class frmWarn extends JDialog {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  JButton jButton1 = new JButton();
  
  JTextArea txtWarn = new JTextArea();
  
  public frmWarn(String paramString1, Frame paramFrame, String paramString2, boolean paramBoolean) {
    super(paramFrame, paramString2, paramBoolean);
    try {
      jbInit();
      pack();
      this.txtWarn.setText(paramString1);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void jbInit() throws Exception {
    this.txtWarn.setMinimumSize(new Dimension(60, 19));
    this.txtWarn.setOpaque(false);
    this.txtWarn.setText("jTextArea1");
    this.txtWarn.setLineWrap(true);
    getContentPane().setLayout(this.gridBagLayout1);
    setLocale(Locale.getDefault());
    setSize(400, 150);
    this.jButton1.setText(ATPMessages.getString("MW.Confirm"));
    getContentPane().add(this.txtWarn, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(17, 49, 0, 11), 0, 67));
    getContentPane().add(this.jButton1, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 15, 0, new Insets(6, 297, 10, 46), 0, 0));
    setSize(new Dimension(550, 450));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
}
