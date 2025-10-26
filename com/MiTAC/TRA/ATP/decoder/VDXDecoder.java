// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import com.MiTAC.TRA.ATP.Tools.HexCode;

public class VDXDecoder
{
    public boolean getBrakePressure(final byte a) throws Exception {
        return HexCode.getBit(a, 2, 3) != 0 || HexCode.getBit(a, 3, 4) != 1;
    }
}
