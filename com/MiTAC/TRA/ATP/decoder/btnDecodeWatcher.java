// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import java.awt.Color;
import com.MiTAC.TRA.ATP.ATPMessages;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class btnDecodeWatcher extends JButton
{
    private int btnRed;
    
    public btnDecodeWatcher() {
        this.btnRed = this.getBackground().getRed();
        final Timer watcher = new Timer(3000, new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                btnDecodeWatcher.this.refresh();
            }
        });
        watcher.start();
    }
    
    private void refresh() {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(ATPMessages.getString("MW.DECODE.BUFFER")) + ": ");
        sb.append(String.valueOf(DecodeBuffer.getBufferSize()) + ".");
        sb.append("  " + ATPMessages.getString("MW.DECODE.STATUS.COMPLETED") + ": ");
        sb.append(String.valueOf(DecodeBuffer.getBufferFinished()) + ".");
        sb.append("  " + ATPMessages.getString("MW.DECODE.STATUS.WAIT") + ": ");
        sb.append(String.valueOf(DecodeBuffer.getBufferWaiting()) + ".");
        int red = this.btnRed;
        if (DecodeBuffer.getBufferWaiting() > 0) {
            red = 255;
        }
        final Color newColor = new Color(red, this.getBackground().getGreen(), this.getBackground().getBlue());
        this.setBackground(newColor);
        this.setText(sb.toString());
    }
}
