// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Application1
{
    boolean packFrame;
    
    public Application1() {
        this.packFrame = false;
        final Frame1 frame1 = new Frame1();
        if (this.packFrame) {
            frame1.pack();
        }
        else {
            frame1.validate();
        }
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension size = frame1.getSize();
        if (size.height > screenSize.height) {
            size.height = screenSize.height;
        }
        if (size.width > screenSize.width) {
            size.width = screenSize.width;
        }
        frame1.setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
        frame1.setTitle("TRA ATP RU-MMI Decoder    Version: 2.6");
        frame1.setVisible(true);
    }
    
    public static void main(final String[] array) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        new Application1();
    }
}
