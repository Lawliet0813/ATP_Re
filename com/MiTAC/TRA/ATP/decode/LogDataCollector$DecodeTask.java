// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import java.awt.Component;
import javax.swing.JOptionPane;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import java.util.Vector;
import java.io.File;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;

class DecodeTask
{
    DecodeTask() {
        try {
            LogDataCollector.access$002(LogDataCollector.this, new RecordHandler(0, 1));
            LogDataCollector.access$102(LogDataCollector.this, new RecordHandler(0, 1));
            LogDataCollector.access$202(LogDataCollector.this, new RecordHandler(0, 1));
            LogDataCollector.access$302(LogDataCollector.this, new RecordHandler(0, 1000));
            LogDataCollector.access$402(LogDataCollector.this, new RecordHandler(0, 1000));
            LogDataCollector.access$502(LogDataCollector.this, new RecordHandler(0, 100));
            LogDataCollector.access$602(LogDataCollector.this, new RecordHandler(0, 100));
            LogDataCollector.access$702(LogDataCollector.this, new RecordHandler(0, 1000));
            LogDataCollector.access$802(LogDataCollector.this, new RecordHandler(0, 1000));
            LogDataCollector.access$902(LogDataCollector.this, new RecordHandler(0, 100));
            LogDataCollector.access$1002(LogDataCollector.this, new RecordHandler(0, 10));
            final File file = new File(LogDataCollector.access$1100(LogDataCollector.this));
            if (file.isDirectory()) {
                final File[] listFiles = file.listFiles();
                for (int i = 0; i < listFiles.length; ++i) {
                    if (listFiles[i].getName().endsWith(".MMI") || listFiles[i].getName().endsWith(".RU")) {
                        LogDataCollector.access$1200(LogDataCollector.this, listFiles[i].getPath());
                    }
                }
            }
            else {
                if (!LogDataCollector.access$1100(LogDataCollector.this).endsWith(".MMI") && !LogDataCollector.access$1100(LogDataCollector.this).endsWith(".RU")) {
                    throw new Exception("Not a ATP file, please select a ATP Log file.");
                }
                LogDataCollector.access$1200(LogDataCollector.this, LogDataCollector.access$1100(LogDataCollector.this));
            }
            LogDataCollector.access$1300(LogDataCollector.this, LogDataCollector.access$1100(LogDataCollector.this)).get(0).booleanValue();
            if (LogDataCollector.access$000(LogDataCollector.this).size() == 0) {
                throw new Exception("\u884c\u8eca\u7d00\u9304\u4e0d\u9f4a\u5168 -- \u7121\u73ed\u6b21, \u8eca\u6b21, \u53ca\u53f8\u6a5f\u54e1\u4ee3\u865f.");
            }
            LogDataCollector.access$200(LogDataCollector.this).addVector((Vector)LogDataCollector.access$000(LogDataCollector.this).get(0));
            if (LogDataCollector.access$100(LogDataCollector.this).size() != 0) {
                final Vector vector = (Vector)LogDataCollector.access$100(LogDataCollector.this).get(0);
                vector.remove(0);
                vector.remove(10);
                vector.insertElementAt(PathHandler.getDecodePath(LogDataCollector.access$1100(LogDataCollector.this)).get(4), 10);
                LogDataCollector.access$200(LogDataCollector.this).addVector(vector);
            }
            else {
                final Vector<String> vector2 = new Vector<String>();
                vector2.add("");
                vector2.add("");
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Double(0.0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add("");
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.add((String)new Integer(0));
                vector2.trimToSize();
                LogDataCollector.access$200(LogDataCollector.this).addVector((Vector)vector2);
            }
            LogDataCollector.access$1402(LogDataCollector.this, true);
        }
        catch (final Exception ex) {
            LogDataCollector.access$1502(LogDataCollector.this, true);
            JOptionPane.showMessageDialog(null, "\u89e3\u78bc\u53d6\u6d88\n\u539f\u56e0: " + ex.getMessage(), "\u932f\u8aa4", 0);
            ex.printStackTrace();
        }
    }
}
