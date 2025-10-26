// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;

private final class frmDecodeWatcher$5 extends DefaultTableCellRenderer {
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
}