// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import java.io.File;

public class Decoder_tester
{
    public Decoder_tester() {
        System.out.println("dd");
    }
    
    public static void main(final String[] args) {
        try {
            final String path = "K:\\\u5de5\u4f5c\u8cc7\u6599\\\u884c\u8eca\u7d00\u9304\\\u554f\u984c\u8cc7\u6599\\20070124_EMC582_\u5609\u7fa9_\u884c\u8eca\u7d00\u9304\u7570\u5e38\\20070119\\00000732_002645--_764094--_C582-\\";
            final File f = new File(path);
            final DataFeeder df = new DataFeeder(f);
            System.out.println(df.getCagetory());
        }
        catch (final Exception ex) {}
    }
}
