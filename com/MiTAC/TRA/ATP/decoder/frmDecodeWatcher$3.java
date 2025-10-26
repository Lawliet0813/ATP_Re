// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;

private final class frmDecodeWatcher$3 extends DefaultTableCellRenderer {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    public void setValue(final Object value) {
        if (value == null) {
            this.setText("");
        }
        else {
            this.setText(this.sdf.format(value));
        }
    }
}