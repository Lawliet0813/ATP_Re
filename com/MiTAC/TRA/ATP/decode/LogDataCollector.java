// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.File;
import com.MiTAC.TRA.ATP.Tools.SwingWorker;
import java.util.Date;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import java.util.Vector;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;

public class LogDataCollector
{
    private RecordHandler _$22276;
    private RecordHandler _$22277;
    private RecordHandler _$22278;
    private RecordHandler _$22270;
    private RecordHandler _$22268;
    private RecordHandler _$22275;
    private RecordHandler _$22272;
    private RecordHandler _$22274;
    private RecordHandler _$22273;
    private RecordHandler _$22271;
    private RecordHandler _$22269;
    private String _$22279;
    private boolean _$16641;
    private boolean _$1639;
    
    public LogDataCollector(final String path) throws Exception {
        this.setPath(path);
    }
    
    public LogDataCollector() throws Exception {
    }
    
    private Vector _$22285(String s) throws Exception {
        boolean value = true;
        String s2 = "";
        final Vector vector = new Vector(2);
        if (s.endsWith("\\")) {
            s = s.substring(s.length() - 27, s.length() - 1);
        }
        else {
            s = s.substring(s.length() - 26, s.length());
        }
        final Vector decodePath = PathHandler.getDecodePath(this._$22279);
        final Date e = decodePath.get(0);
        final String s3 = decodePath.get(1);
        final String s4 = decodePath.get(2);
        final String s5 = decodePath.get(3);
        final String e2 = decodePath.get(4);
        String s6 = "";
        String s7 = "";
        String s8 = "";
        if (this._$22268.size() == 0) {
            final Vector<Date> vector2 = new Vector<Date>();
            vector2.add(e);
            vector2.add((Date)s5);
            vector2.add((Date)s4);
            vector2.add((Date)s3);
            vector2.add((Date)e2);
            this._$22268.add((Object)vector2);
        }
        else {
            final Vector vector3 = (Vector)this._$22268.get(0);
            s6 = (String)vector3.get(1);
            s7 = (String)vector3.get(3);
            s8 = (String)vector3.get(2);
        }
        if (!s5.equals(s6)) {
            value = false;
            s2 = s2 + "Driver ID(" + s5 + ") in path is different from Driver ID(" + s6 + ")  in log.";
        }
        if (!s3.equals(s7)) {
            value = false;
            s2 = s2 + "\nWorkShift Number(" + s3 + ")  in path is different from WorkShift Number(" + s7 + ")  in log.";
        }
        if (!s4.equals(s8)) {
            value = false;
            s2 = s2 + "\nTrainRunning Number(" + s4 + ")  in path is different from TrainRunning Number(" + s8 + ")  in log.";
        }
        vector.add(new Boolean(value));
        vector.add(s2);
        return vector;
    }
    
    public void clear() {
        this._$22268.clear();
        this._$22268.trimToSize();
        this._$22269.clear();
        this._$22269.trimToSize();
        this._$22270.clear();
        this._$22270.trimToSize();
        this._$22271.clear();
        this._$22271.trimToSize();
        this._$22272.clear();
        this._$22272.trimToSize();
        this._$22273.clear();
        this._$22273.trimToSize();
        this._$22274.clear();
        this._$22274.trimToSize();
        this._$22275.clear();
        this._$22276.clear();
        this._$22276.trimToSize();
        this._$22277.clear();
        this._$22277.trimToSize();
        this._$22278.clear();
        this._$22278.trimToSize();
        System.gc();
    }
    
    private void _$22283(final String s) throws Exception {
        final Decoder decoder = new Decoder(s);
        this._$22268.addVector(decoder.getLogDriverData());
        this._$22269.addVector(decoder.getLogTrainData());
        this._$22271.addVector(decoder.getLogTS());
        this._$22272.addVector(decoder.getLogDynamic());
        this._$22273.addVector(decoder.getLogStatus());
        this._$22274.addVector(decoder.getLogFailure());
        this._$22275.addVector(decoder.getLogDriverMessage());
        this._$22276.addVector(decoder.getAll());
        this._$22277.addVector(decoder.getATP());
        this._$22278.addVector(decoder.getErr());
    }
    
    public Vector getATPEventdata() {
        return (Vector)this._$22277;
    }
    
    public Vector getAllEventdata() {
        return (Vector)this._$22276;
    }
    
    public Vector getErrorEventdata() {
        return (Vector)this._$22278;
    }
    
    public Vector getLogCategory() {
        return (Vector)this._$22270;
    }
    
    public Vector getLogDriverMessage() {
        return (Vector)this._$22275;
    }
    
    public Vector getLogDynamic() {
        return (Vector)this._$22272;
    }
    
    public Vector getLogFailure() {
        return (Vector)this._$22274;
    }
    
    public Vector getLogStatus() {
        return (Vector)this._$22273;
    }
    
    public Vector getLogTS() {
        return (Vector)this._$22271;
    }
    
    public boolean isCanceled() {
        return this._$16641;
    }
    
    public boolean isDone() {
        return this._$1639;
    }
    
    public void setPath(final String $22279) {
        this._$22279 = $22279;
        new SwingWorker() {
            public Object construct() {
                return new DecodeTask();
            }
        }.start();
    }
    
    class DecodeTask
    {
        DecodeTask() {
            try {
                LogDataCollector.this._$22268 = new RecordHandler(0, 1);
                LogDataCollector.this._$22269 = new RecordHandler(0, 1);
                LogDataCollector.this._$22270 = new RecordHandler(0, 1);
                LogDataCollector.this._$22271 = new RecordHandler(0, 1000);
                LogDataCollector.this._$22272 = new RecordHandler(0, 1000);
                LogDataCollector.this._$22273 = new RecordHandler(0, 100);
                LogDataCollector.this._$22274 = new RecordHandler(0, 100);
                LogDataCollector.this._$22276 = new RecordHandler(0, 1000);
                LogDataCollector.this._$22277 = new RecordHandler(0, 1000);
                LogDataCollector.this._$22278 = new RecordHandler(0, 100);
                LogDataCollector.this._$22275 = new RecordHandler(0, 10);
                final File file = new File(LogDataCollector.this._$22279);
                if (file.isDirectory()) {
                    final File[] listFiles = file.listFiles();
                    for (int i = 0; i < listFiles.length; ++i) {
                        if (listFiles[i].getName().endsWith(".MMI") || listFiles[i].getName().endsWith(".RU")) {
                            LogDataCollector.this._$22283(listFiles[i].getPath());
                        }
                    }
                }
                else {
                    if (!LogDataCollector.this._$22279.endsWith(".MMI") && !LogDataCollector.this._$22279.endsWith(".RU")) {
                        throw new Exception("Not a ATP file, please select a ATP Log file.");
                    }
                    LogDataCollector.this._$22283(LogDataCollector.this._$22279);
                }
                LogDataCollector.this._$22285(LogDataCollector.this._$22279).get(0).booleanValue();
                if (LogDataCollector.this._$22268.size() == 0) {
                    throw new Exception("\u884c\u8eca\u7d00\u9304\u4e0d\u9f4a\u5168 -- \u7121\u73ed\u6b21, \u8eca\u6b21, \u53ca\u53f8\u6a5f\u54e1\u4ee3\u865f.");
                }
                LogDataCollector.this._$22270.addVector((Vector)LogDataCollector.this._$22268.get(0));
                if (LogDataCollector.this._$22269.size() != 0) {
                    final Vector vector = (Vector)LogDataCollector.this._$22269.get(0);
                    vector.remove(0);
                    vector.remove(10);
                    vector.insertElementAt(PathHandler.getDecodePath(LogDataCollector.this._$22279).get(4), 10);
                    LogDataCollector.this._$22270.addVector(vector);
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
                    LogDataCollector.this._$22270.addVector((Vector)vector2);
                }
                LogDataCollector.this._$1639 = true;
            }
            catch (final Exception ex) {
                LogDataCollector.this._$16641 = true;
                JOptionPane.showMessageDialog(null, "\u89e3\u78bc\u53d6\u6d88\n\u539f\u56e0: " + ex.getMessage(), "\u932f\u8aa4", 0);
                ex.printStackTrace();
            }
        }
    }
}
