package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class pnlIndex extends JPanel {
  JLabel lblAnalyse = new JLabel();
  
  JLabel lblAnalyse_srt = new JLabel();
  
  JLabel lblDesignate = new JLabel();
  
  JLabel lblDesignate_srt = new JLabel();
  
  JLabel lblQuery = new JLabel();
  
  JLabel lblQuery_srt = new JLabel();
  
  JLabel lblSeparator = new JLabel();
  
  JLabel lblSetting = new JLabel();
  
  JLabel lblSetting_srt = new JLabel();
  
  JLabel lblTitle = new JLabel();
  
  JLabel lblTitle_srt = new JLabel();
  
  JTextArea txtAnalyse_dtl = new JTextArea();
  
  JTextArea txtDesignate_dtl = new JTextArea();
  
  JTextArea txtQuery_dtl = new JTextArea();
  
  JTextArea txtSetting_dtl = new JTextArea();
  
  JTextArea txtTitle_dtl = new JTextArea();
  
  public pnlIndex() {
    init();
  }
  
  public void init() {
    setLayout(new GridBagLayout());
    this.lblSeparator.setText("--------------------------------------------------------");
    this.lblTitle.setText("台灣鐵路管理局");
    this.lblTitle_srt.setText("列車自動防護系統");
    this.txtTitle_dtl.setText("==================================");
    this.txtTitle_dtl.setOpaque(false);
    this.txtTitle_dtl.setEditable(false);
    this.lblSetting.setText("設定");
    this.lblSetting_srt.setText("設定管理電腦的行為");
    this.txtSetting_dtl.setText("==================================");
    this.txtSetting_dtl.setOpaque(false);
    this.txtSetting_dtl.setEditable(false);
    this.lblDesignate.setText("指派");
    this.lblDesignate_srt.setText("編派工作及執行內容");
    this.txtDesignate_dtl.setText("==================================");
    this.txtDesignate_dtl.setOpaque(false);
    this.txtDesignate_dtl.setEditable(false);
    this.lblAnalyse.setText("分析");
    this.lblAnalyse_srt.setText("考核執行內容");
    this.txtAnalyse_dtl.setText("==================================");
    this.txtAnalyse_dtl.setOpaque(false);
    this.txtAnalyse_dtl.setEditable(false);
    this.lblQuery.setText("記錄");
    this.lblQuery_srt.setText("查詢使用者對系統的操作");
    this.txtQuery_dtl.setText("==================================");
    this.txtQuery_dtl.setOpaque(false);
    this.txtQuery_dtl.setEditable(false);
    add(this.lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblTitle_srt, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.txtTitle_dtl, new GridBagConstraints(1, 0, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblSetting, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblSetting_srt, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.txtSetting_dtl, new GridBagConstraints(1, 3, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblDesignate, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblDesignate_srt, new GridBagConstraints(0, 7, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.txtDesignate_dtl, new GridBagConstraints(1, 6, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblAnalyse, new GridBagConstraints(0, 9, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblAnalyse_srt, new GridBagConstraints(0, 10, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.txtAnalyse_dtl, new GridBagConstraints(1, 9, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblQuery, new GridBagConstraints(0, 12, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.lblQuery_srt, new GridBagConstraints(0, 13, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    add(this.txtQuery_dtl, new GridBagConstraints(1, 12, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
  }
}
