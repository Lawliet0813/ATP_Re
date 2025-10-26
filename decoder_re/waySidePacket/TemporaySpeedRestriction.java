package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.decoder.waySidePacket.P44;

public class TemporaySpeedRestriction extends P44 {
  private int NID_TSR = -1;
  
  private int D_TSR = -1;
  
  private int L_TSR = -1;
  
  private int Q_FRONT_DRIVER = -1;
  
  private int V_TSR = -1;
  
  private int N_ITER = 0;
  
  private int[][] iter;
  
  public TemporaySpeedRestriction(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial getCode : ()[B
    //   4: bipush #40
    //   6: bipush #48
    //   8: invokestatic getBit : ([BII)[B
    //   11: astore_1
    //   12: aload_0
    //   13: aload_1
    //   14: iconst_0
    //   15: baload
    //   16: invokestatic getUnsigned : (B)I
    //   19: putfield NID_TSR : I
    //   22: aload_0
    //   23: invokespecial getCode : ()[B
    //   26: bipush #48
    //   28: bipush #63
    //   30: invokestatic getBit : ([BII)[B
    //   33: astore_1
    //   34: aload_0
    //   35: aload_1
    //   36: iconst_0
    //   37: baload
    //   38: aload_1
    //   39: iconst_1
    //   40: baload
    //   41: invokestatic getUnsigned : (BB)I
    //   44: putfield D_TSR : I
    //   47: aload_0
    //   48: invokespecial getCode : ()[B
    //   51: bipush #63
    //   53: bipush #78
    //   55: invokestatic getBit : ([BII)[B
    //   58: astore_1
    //   59: aload_0
    //   60: aload_1
    //   61: iconst_0
    //   62: baload
    //   63: aload_1
    //   64: iconst_1
    //   65: baload
    //   66: invokestatic getUnsigned : (BB)I
    //   69: putfield L_TSR : I
    //   72: aload_0
    //   73: invokespecial getCode : ()[B
    //   76: bipush #78
    //   78: bipush #80
    //   80: invokestatic getBit : ([BII)[B
    //   83: astore_1
    //   84: aload_0
    //   85: aload_1
    //   86: iconst_0
    //   87: baload
    //   88: invokestatic getUnsigned : (B)I
    //   91: putfield Q_FRONT_DRIVER : I
    //   94: aload_0
    //   95: invokespecial getCode : ()[B
    //   98: bipush #80
    //   100: bipush #87
    //   102: invokestatic getBit : ([BII)[B
    //   105: astore_1
    //   106: aload_0
    //   107: aload_1
    //   108: iconst_0
    //   109: baload
    //   110: invokestatic getUnsigned : (B)I
    //   113: putfield V_TSR : I
    //   116: aload_0
    //   117: invokespecial getCode : ()[B
    //   120: bipush #87
    //   122: bipush #92
    //   124: invokestatic getBit : ([BII)[B
    //   127: astore_1
    //   128: aload_0
    //   129: aload_1
    //   130: iconst_0
    //   131: baload
    //   132: invokestatic getUnsigned : (B)I
    //   135: putfield N_ITER : I
    //   138: aload_0
    //   139: aload_0
    //   140: invokevirtual get_N_ITER : ()I
    //   143: iconst_3
    //   144: multianewarray[[I 2
    //   148: putfield iter : [[I
    //   151: iconst_1
    //   152: istore_2
    //   153: goto -> 256
    //   156: bipush #92
    //   158: iload_2
    //   159: iadd
    //   160: bipush #13
    //   162: isub
    //   163: istore_3
    //   164: aload_0
    //   165: invokespecial getCode : ()[B
    //   168: iload_3
    //   169: iinc #3, 4
    //   172: iload_3
    //   173: invokestatic getBit : ([BII)[B
    //   176: astore_1
    //   177: aload_1
    //   178: iconst_0
    //   179: baload
    //   180: invokestatic getUnsigned : (B)I
    //   183: istore #4
    //   185: aload_0
    //   186: invokespecial getCode : ()[B
    //   189: iload_3
    //   190: iinc #3, 2
    //   193: iload_3
    //   194: invokestatic getBit : ([BII)[B
    //   197: astore_1
    //   198: aload_1
    //   199: iconst_0
    //   200: baload
    //   201: invokestatic getUnsigned : (B)I
    //   204: istore #5
    //   206: aload_0
    //   207: invokespecial getCode : ()[B
    //   210: iload_3
    //   211: iinc #3, 7
    //   214: iload_3
    //   215: invokestatic getBit : ([BII)[B
    //   218: astore_1
    //   219: aload_1
    //   220: iconst_0
    //   221: baload
    //   222: invokestatic getUnsigned : (B)I
    //   225: istore #6
    //   227: aload_0
    //   228: getfield iter : [[I
    //   231: iload_2
    //   232: iconst_1
    //   233: isub
    //   234: iconst_3
    //   235: newarray int
    //   237: dup
    //   238: iconst_0
    //   239: iload #4
    //   241: iastore
    //   242: dup
    //   243: iconst_1
    //   244: iload #5
    //   246: iastore
    //   247: dup
    //   248: iconst_2
    //   249: iload #6
    //   251: iastore
    //   252: aastore
    //   253: iinc #2, 1
    //   256: iload_2
    //   257: aload_0
    //   258: invokevirtual get_N_ITER : ()I
    //   261: if_icmple -> 156
    //   264: goto -> 272
    //   267: astore_1
    //   268: aload_1
    //   269: invokevirtual printStackTrace : ()V
    //   272: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #35	-> 0
    //   #36	-> 12
    //   #38	-> 22
    //   #39	-> 34
    //   #41	-> 47
    //   #42	-> 59
    //   #44	-> 72
    //   #45	-> 84
    //   #47	-> 94
    //   #48	-> 106
    //   #50	-> 116
    //   #51	-> 128
    //   #53	-> 138
    //   #54	-> 151
    //   #55	-> 156
    //   #56	-> 164
    //   #57	-> 177
    //   #59	-> 185
    //   #60	-> 198
    //   #62	-> 206
    //   #63	-> 219
    //   #65	-> 227
    //   #54	-> 253
    //   #67	-> 267
    //   #68	-> 268
    //   #70	-> 272
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	273	0	this	Lcom/MiTAC/TRA/ATP/decoder/waySidePacket/TemporaySpeedRestriction;
    //   12	255	1	tmpdata	[B
    //   153	111	2	x	I
    //   164	89	3	loc	I
    //   185	68	4	NC_DIFF	I
    //   206	47	5	Q_FRONT_DRIVER	I
    //   227	26	6	V_DIFF	I
    //   268	4	1	ex	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   0	267	267	java/lang/Exception
  }
  
  public int get_NID_TSR() {
    return this.NID_TSR;
  }
  
  public int get_D_TSR() {
    return this.D_TSR;
  }
  
  public int get_L_TSR() {
    return this.L_TSR;
  }
  
  public int get_Q_FRONT_DRIVER() {
    return this.Q_FRONT_DRIVER;
  }
  
  public int get_V_TSR() {
    return this.V_TSR;
  }
  
  public int get_N_ITER() {
    return this.N_ITER;
  }
  
  public int[] get_ITER(int no) {
    if (no > 0 && no <= get_N_ITER())
      return this.iter[no]; 
    return new int[] { -1, -1, -1 };
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("== P44:3, Temporay Speed Restriction. ==");
    tmp.append("\r\n");
    tmp.append(super.toString());
    tmp.append("NID_TSR:\t" + get_NID_TSR());
    tmp.append("\r\n");
    tmp.append("D_TSR:\t" + get_D_TSR());
    tmp.append("\r\n");
    tmp.append("L_TSR:\t" + get_L_TSR());
    tmp.append("\r\n");
    tmp.append("Q_FRONT_DRIVER:\t" + get_Q_FRONT_DRIVER());
    tmp.append("\r\n");
    tmp.append("V_TSR:\t" + get_V_TSR());
    tmp.append("\r\n");
    tmp.append("N_ITER:\t" + get_N_ITER());
    tmp.append("\r\n");
    for (int x = 1; x <= get_N_ITER(); x++) {
      int[] iter = get_ITER(x);
      tmp.append("\t");
      tmp.append("NC_DIFF(" + x + "):\t" + iter[0]);
      tmp.append("\r\n");
      tmp.append("\t");
      tmp.append("Q_FRONT_DRIVER(" + x + "):\t" + iter[1]);
      tmp.append("\r\n");
      tmp.append("\t");
      tmp.append("V_DIFF(" + x + "):\t" + iter[2]);
      tmp.append("\r\n");
    } 
    return tmp.toString();
  }
}
