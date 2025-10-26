// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.File;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import javax.swing.table.TableColumn;
import java.awt.Color;
import javax.swing.table.TableCellRenderer;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.JScrollPane;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import com.MiTAC.TRA.ATP.ATPMessages;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class frmDecodeWatcher extends JFrame implements ActionListener
{
    JCheckBox chxIgnoreNoError;
    DefaultSortTableModel jstm;
    JLabel lblInformation;
    JButton btnCleanSuccess;
    JButton btnCleanExists;
    JButton btnSaveErrorLog;
    JButton btnCloseFrame;
    JSortTable jst;
    Object[] dColumn;
    
    public frmDecodeWatcher() {
        this.chxIgnoreNoError = new JCheckBox();
        this.dColumn = new Object[16];
        this.init();
        final Timer watcher = new Timer(500, new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                frmDecodeWatcher.this.refresh();
            }
        });
        watcher.setInitialDelay(0);
        watcher.start();
    }
    
    private void init() {
        this.setTitle(ATPMessages.getString("MW.DECODE.WATCHER"));
        this.setSize(new Dimension(800, 350));
        this.getContentPane().setLayout(new BorderLayout());
        this.dColumn[0] = ATPMessages.getString("MW.DECODE.NO");
        this.dColumn[1] = ATPMessages.getString("MW.DECODE.SOURCE");
        this.dColumn[2] = ATPMessages.getString("MW.LOG.RUNNINGDATE");
        this.dColumn[3] = ATPMessages.getString("MW.WS.ID");
        this.dColumn[4] = ATPMessages.getString("MW.TR.ID");
        this.dColumn[5] = ATPMessages.getString("MW.DRIVER.ID");
        this.dColumn[6] = ATPMessages.getString("MW.VEHICLE.ID");
        this.dColumn[7] = ATPMessages.getString("MW.DECODE.START_DECODE");
        this.dColumn[8] = ATPMessages.getString("MW.DECODE.END_DECODE");
        this.dColumn[9] = ATPMessages.getString("MW.DECODE.TIME_USED");
        this.dColumn[10] = ATPMessages.getString("MW.DECODE.STATUS");
        this.dColumn[11] = ATPMessages.getString("MW.LA.SB.TIMES");
        this.dColumn[12] = ATPMessages.getString("MW.LA.EB.TIMES");
        this.dColumn[13] = ATPMessages.getString("MW.LA.CABIN_FAILURE.TIMES");
        this.dColumn[14] = ATPMessages.getString("MW.LA.WAYSIDE_FAILURE.TIMES");
        this.dColumn[15] = ATPMessages.getString("MW.DECODE.FILETYPE");
        this.jstm = new DefaultSortTableModel();
        this.jst = new JSortTable((SortTableModel)this.jstm);
        final JScrollPane jsp = new JScrollPane((Component)this.jst);
        this.getContentPane().add(jsp, "Center");
        final JPanel pnlControl = new JPanel(new GridBagLayout());
        pnlControl.add(this.lblInformation = new JLabel("---"), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        this.chxIgnoreNoError.setText(ATPMessages.getString("MW.LD.IGNORE_NO_ERROR_LOG"));
        this.chxIgnoreNoError.setSelected(DecodeBuffer.ignoreNoError);
        this.chxIgnoreNoError.addActionListener(this);
        (this.btnCleanSuccess = new JButton(ATPMessages.getString("MW.DECODE.CLEAR.SUCCESS"))).setActionCommand("CleanSuccess");
        this.btnCleanSuccess.addActionListener(this);
        (this.btnCleanExists = new JButton(ATPMessages.getString("MW.DECODE.CLEAR.EXIST"))).setActionCommand("CleanExists");
        this.btnCleanExists.addActionListener(this);
        (this.btnSaveErrorLog = new JButton(ATPMessages.getString("MW.DECODE.ERROR.LOG.SAVE"))).addActionListener(this);
        (this.btnCloseFrame = new JButton(ATPMessages.getString("MW.DECODE.CLOSE"))).setActionCommand("Close");
        this.btnCloseFrame.addActionListener(this);
        pnlControl.add(this.btnSaveErrorLog, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 12, 0, new Insets(0, 0, 0, 0), 0, 0));
        pnlControl.add(this.chxIgnoreNoError, new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
        pnlControl.add(this.btnCleanExists, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, 12, 0, new Insets(0, 0, 0, 0), 0, 0));
        pnlControl.add(this.btnCleanSuccess, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, 12, 0, new Insets(0, 0, 0, 0), 0, 0));
        pnlControl.add(this.btnCloseFrame, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, 12, 0, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(pnlControl, "South");
    }
    
    private void refresh() {
        this.jstm.setDataVector(DecodeBuffer.getBufferStatus2(), this.dColumn);
        final TableColumn clum8 = this.jst.getColumnModel().getColumn(7);
        clum8.setCellRenderer(new DefaultTableCellRenderer() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            
            public void setValue(final Object value) {
                if (value == null) {
                    this.setText("");
                }
                else {
                    this.setText(this.sdf.format(value));
                }
            }
        });
        final TableColumn clum9 = this.jst.getColumnModel().getColumn(8);
        clum9.setCellRenderer(new DefaultTableCellRenderer() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            
            public void setValue(final Object value) {
                if (value == null) {
                    this.setText("");
                }
                else {
                    this.setText(this.sdf.format(value));
                }
            }
        });
        final TableColumn clum10 = this.jst.getColumnModel().getColumn(9);
        clum10.setCellRenderer(new DefaultTableCellRenderer() {
            public void setValue(final Object value) {
                if (value == null) {
                    this.setText("");
                }
                else {
                    this.setText(String.valueOf((long)value / 1000.0f) + " " + ATPMessages.getString("MW.DECODE.SECOND"));
                }
            }
        });
        final TableColumn clum11 = this.jst.getColumnModel().getColumn(10);
        clum11.setCellRenderer(new DefaultTableCellRenderer() {
            public void setValue(final Object value) {
                this.setText((String)value);
                this.setOpaque(true);
                if (((String)value).startsWith(DecodeBuffer.failureMsg)) {
                    this.setBackground(Color.red);
                }
                else if (((String)value).startsWith(DecodeBuffer.waitingMsg)) {
                    this.setBackground(Color.yellow);
                }
                else if (((String)value).startsWith(DecodeBuffer.successMsg)) {
                    this.setBackground(Color.green);
                }
                else if (((String)value).startsWith(DecodeBuffer.giveupMsg)) {
                    this.setBackground(Color.red.darker());
                }
                else if (((String)value).startsWith(DecodeBuffer.dataExistMsg)) {
                    this.setBackground(Color.pink);
                }
                else {
                    this.setOpaque(false);
                }
                if (((String)value).endsWith(DecodeBuffer.ignoreMsg)) {
                    this.setBackground(Color.green.darker());
                }
            }
        });
        final StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(ATPMessages.getString("MW.DECODE.BUFFER")) + ": ");
        sb.append(String.valueOf(DecodeBuffer.getBufferSize()) + ".");
        sb.append("  " + ATPMessages.getString("MW.DECODE.STATUS.COMPLETED") + ": ");
        sb.append(String.valueOf(DecodeBuffer.getBufferFinished()) + ".");
        sb.append("  " + ATPMessages.getString("MW.DECODE.STATUS.WAIT") + ": ");
        sb.append(String.valueOf(DecodeBuffer.getBufferWaiting()) + ".");
        this.lblInformation.setText(sb.toString());
        this.repaint();
    }
    
    public static void main(final String[] args) {
        try {
            InitParameters.start();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        final DecodeBuffer db = new DecodeBuffer();
        db.start();
        final frmDecodeWatcher de = new frmDecodeWatcher();
        de.show();
        DecodeBuffer.addTask(new File("D:\\MWdisk\\20060207\\00000716_002487--_753590--_M578-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000556_002463--_750352--_C569-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000563_002471B-_753730--_C585-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000566_002654--_372620--_C574-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000573_002454--_367906--_C574-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000573_002581--_645894--_C563-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000574_002425--_754674--_C585-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000716_002648--_753425--_C565-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000723_002483--_751894--_C584-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000723_002483--_751894--_C5d4-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20051001\\00000723_002483--_751894--_C5de-\\"));
        DecodeBuffer.addTask(new File("D:\\MWDisk\\20061314\\00000730_002665--_304075--C569-\\"));
    }
    
    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals(this.btnCloseFrame.getActionCommand())) {
            this.setVisible(false);
        }
        else if (e.getActionCommand().equals(this.btnCleanSuccess.getActionCommand())) {
            DecodeBuffer.clearDecodeFinished();
        }
        else if (e.getActionCommand().equals(this.btnCleanExists.getActionCommand())) {
            DecodeBuffer.cleanExists();
        }
        else if (e.getActionCommand().equals(this.btnSaveErrorLog.getActionCommand())) {
            try {
                final Object[][] status = DecodeBuffer.getBufferStatus();
                final StringBuffer sb = new StringBuffer();
                for (int x = 0; x < status.length; ++x) {
                    if (status[x][14] != null) {
                        sb.append(status[x][14]);
                    }
                }
                if (sb.length() != 0) {
                    final File fileListPath = new File("C:\\ATPMW\\ERROR_LOG\\");
                    if (!fileListPath.exists()) {
                        fileListPath.mkdirs();
                    }
                    final FileWriter fw = new FileWriter(new File("C:\\ATPMW\\ERROR_LOG\\DECODE_ERROR_" + System.currentTimeMillis() + ".LOG"));
                    fw.write(sb.toString());
                    fw.close();
                    JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DECODE.ERROR.LOG.SAVE.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
                }
                DecodeBuffer.clearFailure();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DECODE.ERROR.LOG.SAVE.OK"), ATPMessages.getString("MW.GNL.ERROR"), 0);
            }
        }
        else if (e.getActionCommand().equals(this.chxIgnoreNoError.getActionCommand())) {
            DecodeBuffer.ignoreNoError = this.chxIgnoreNoError.isSelected();
        }
    }
}
