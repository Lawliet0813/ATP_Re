// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import com.MiTAC.TRA.ATP.Tools.SwingWorker;

class LogDataCollector$1 extends SwingWorker {
    public Object construct() {
        return new DecodeTask();
    }
}