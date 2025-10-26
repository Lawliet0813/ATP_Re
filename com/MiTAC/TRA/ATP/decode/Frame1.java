// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import java.awt.event.WindowEvent;
import java.awt.Component;
import com.MiTAC.TRA.ATP.ui.pnlViewCode;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Frame1 extends JFrame
{
    public Frame1() {
        this.enableEvents(64L);
        try {
            this._$12828();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$12828() throws Exception {
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(new Dimension(800, 600));
        this.setTitle("MMI-RU Decoder");
        this.getContentPane().add((Component)new pnlViewCode(), "Center");
    }
    
    protected void processWindowEvent(final WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == 201) {
            System.exit(0);
        }
    }
}
