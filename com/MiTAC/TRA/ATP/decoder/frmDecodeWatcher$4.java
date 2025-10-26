// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import com.MiTAC.TRA.ATP.ATPMessages;
import javax.swing.table.DefaultTableCellRenderer;

private final class frmDecodeWatcher$4 extends DefaultTableCellRenderer {
    public void setValue(final Object value) {
        if (value == null) {
            this.setText("");
        }
        else {
            this.setText(String.valueOf((long)value / 1000.0f) + " " + ATPMessages.getString("MW.DECODE.SECOND"));
        }
    }
}