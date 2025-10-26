package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.decoder.waySidePacket.P44;

public class Curve extends P44 {
  private int N_ITER = 0;
  
  private int[][] iter;
  
  public Curve(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial getCode : ()[B
    //   4: bipush #70
    //   6: bipush #75
    //   8: invokestatic getBit : ([BII)[B
    //   11: astore_1
    //   12: aload_0
    //   13: aload_1
    //   14: iconst_0
    //   15: baload
    //   16: invokestatic getUnsigned : (B)I
    //   19: putfield N_ITER : I
    //   22: aload_0
    //   23: aload_0
    //   24: invokevirtual get_N_ITER : ()I
    //   27: iconst_1
    //   28: iadd
    //   29: iconst_3
    //   30: multianewarray[[I 2
    //   34: putfield iter : [[I
    //   37: bipush #40
    //   39: istore_2
    //   40: aload_0
    //   41: invokespecial getCode : ()[B
    //   44: iload_2
    //   45: iinc #2, 13
    //   48: iload_2
    //   49: invokestatic getBit : ([BII)[B
    //   52: astore_1
    //   53: aload_1
    //   54: iconst_0
    //   55: baload
    //   56: aload_1
    //   57: iconst_1
    //   58: baload
    //   59: invokestatic getUnsigned : (BB)I
    //   62: istore_3
    //   63: aload_0
    //   64: invokespecial getCode : ()[B
    //   67: iload_2
    //   68: iinc #2, 12
    //   71: iload_2
    //   72: invokestatic getBit : ([BII)[B
    //   75: astore_1
    //   76: aload_1
    //   77: iconst_0
    //   78: baload
    //   79: aload_1
    //   80: iconst_1
    //   81: baload
    //   82: invokestatic getUnsigned : (BB)I
    //   85: istore #4
    //   87: aload_0
    //   88: invokespecial getCode : ()[B
    //   91: iload_2
    //   92: iinc #2, 5
    //   95: iload_2
    //   96: invokestatic getBit : ([BII)[B
    //   99: astore_1
    //   100: aload_1
    //   101: iconst_0
    //   102: baload
    //   103: invokestatic getUnsigned : (B)I
    //   106: istore #5
    //   108: aload_0
    //   109: getfield iter : [[I
    //   112: iconst_0
    //   113: iconst_3
    //   114: newarray int
    //   116: dup
    //   117: iconst_0
    //   118: iload_3
    //   119: iastore
    //   120: dup
    //   121: iconst_1
    //   122: iload #4
    //   124: iastore
    //   125: dup
    //   126: iconst_2
    //   127: iload #5
    //   129: iastore
    //   130: aastore
    //   131: iconst_1
    //   132: istore #6
    //   134: goto -> 243
    //   137: bipush #75
    //   139: iload #6
    //   141: iconst_1
    //   142: isub
    //   143: bipush #30
    //   145: imul
    //   146: iadd
    //   147: istore_2
    //   148: aload_0
    //   149: invokespecial getCode : ()[B
    //   152: iload_2
    //   153: iinc #2, 13
    //   156: iload_2
    //   157: invokestatic getBit : ([BII)[B
    //   160: astore_1
    //   161: aload_1
    //   162: iconst_0
    //   163: baload
    //   164: aload_1
    //   165: iconst_1
    //   166: baload
    //   167: invokestatic getUnsigned : (BB)I
    //   170: istore_3
    //   171: aload_0
    //   172: invokespecial getCode : ()[B
    //   175: iload_2
    //   176: iinc #2, 12
    //   179: iload_2
    //   180: invokestatic getBit : ([BII)[B
    //   183: astore_1
    //   184: aload_1
    //   185: iconst_0
    //   186: baload
    //   187: aload_1
    //   188: iconst_1
    //   189: baload
    //   190: invokestatic getUnsigned : (BB)I
    //   193: istore #4
    //   195: aload_0
    //   196: invokespecial getCode : ()[B
    //   199: iload_2
    //   200: iinc #2, 5
    //   203: iload_2
    //   204: invokestatic getBit : ([BII)[B
    //   207: astore_1
    //   208: aload_1
    //   209: iconst_0
    //   210: baload
    //   211: invokestatic getUnsigned : (B)I
    //   214: istore #5
    //   216: aload_0
    //   217: getfield iter : [[I
    //   220: iload #6
    //   222: iconst_3
    //   223: newarray int
    //   225: dup
    //   226: iconst_0
    //   227: iload_3
    //   228: iastore
    //   229: dup
    //   230: iconst_1
    //   231: iload #4
    //   233: iastore
    //   234: dup
    //   235: iconst_2
    //   236: iload #5
    //   238: iastore
    //   239: aastore
    //   240: iinc #6, 1
    //   243: iload #6
    //   245: aload_0
    //   246: invokevirtual get_N_ITER : ()I
    //   249: if_icmple -> 137
    //   252: goto -> 260
    //   255: astore_1
    //   256: aload_1
    //   257: invokevirtual printStackTrace : ()V
    //   260: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #33	-> 0
    //   #34	-> 12
    //   #36	-> 22
    //   #38	-> 37
    //   #39	-> 40
    //   #40	-> 53
    //   #42	-> 63
    //   #43	-> 76
    //   #45	-> 87
    //   #46	-> 100
    //   #48	-> 108
    //   #50	-> 131
    //   #51	-> 137
    //   #53	-> 148
    //   #54	-> 161
    //   #56	-> 171
    //   #57	-> 184
    //   #59	-> 195
    //   #60	-> 208
    //   #62	-> 216
    //   #50	-> 240
    //   #64	-> 255
    //   #65	-> 256
    //   #68	-> 260
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	261	0	this	Lcom/MiTAC/TRA/ATP/decoder/waySidePacket/Curve;
    //   12	243	1	tmpdata	[B
    //   40	215	2	loc	I
    //   63	192	3	D_CURVEDISTANCE	I
    //   87	168	4	L_CURVELENGTH	I
    //   108	147	5	M_CURVERADIUS	I
    //   134	118	6	x	I
    //   256	4	1	ex	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   0	255	255	java/lang/Exception
  }
  
  public int get_N_ITER() {
    return this.N_ITER;
  }
  
  public int[][] get_Curve() {
    return this.iter;
  }
  
  private int[] get_Curve(int x) {
    if (x >= 0 && x <= get_N_ITER())
      return this.iter[x]; 
    return new int[] { -1, -1, -1 };
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("== P44:0, Curve ==");
    tmp.append("\r\n");
    tmp.append(super.toString());
    tmp.append("\r\n");
    int[] iter = get_Curve(0);
    tmp.append("D_CURVEDISTANCE:\t" + iter[0]);
    tmp.append("\r\n");
    tmp.append("L_CURVELENGTH:\t" + iter[1]);
    tmp.append("\r\n");
    tmp.append("M_CURVERADIUS:\t" + iter[2]);
    tmp.append("\r\n");
    tmp.append("N_ITER:\t" + get_N_ITER());
    tmp.append("\r\n");
    for (int x = 1; x <= get_N_ITER(); x++) {
      iter = get_Curve(x);
      tmp.append("\t");
      tmp.append("D_CURVEDISTANCE(" + x + "):\t" + iter[0]);
      tmp.append("\r\n");
      tmp.append("\t");
      tmp.append("L_CURVELENGTH(" + x + "):\t" + iter[1]);
      tmp.append("\r\n");
      tmp.append("\t");
      tmp.append("M_CURVERADIUS(" + x + "):\t" + iter[2]);
      tmp.append("\r\n");
    } 
    return tmp.toString();
  }
}
