package Tools;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class PasswordDialog extends JOptionPane {
  public static void main(String[] paramArrayOfString) {
    System.err.println(showPasswordInputDialog(null, "passwd", "input pwd", 3));
  }
  
  public static String showPasswordInputDialog(Component paramComponent, String paramString1, String paramString2, int paramInt) {
    JPanel jPanel = new JPanel();
    JPasswordField jPasswordField = new JPasswordField("");
    jPasswordField.setEchoChar('*');
    jPasswordField.setPreferredSize(new Dimension(100, 20));
    JLabel jLabel = new JLabel();
    jLabel.setText(paramString1);
    jPanel.setLayout(new GridLayout(0, 1));
    jPanel.add(jLabel);
    jPanel.add(jPasswordField);
    JOptionPane.showMessageDialog(paramComponent, jPanel, paramString2, paramInt);
    return new String(jPasswordField.getPassword());
  }
}
