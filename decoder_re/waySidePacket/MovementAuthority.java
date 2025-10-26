package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;

public class MovementAuthority extends DefaultTelegram {
  private int Q_SCALE = -1;
  
  private int V_MAIN = -1;
  
  private int V_LOA = -1;
  
  private int T_LOA = -1;
  
  private int N_ITER = 0;
  
  private int Q_SECTIONTIMER = -1;
  
  private int T_SECTIONTIMER = -1;
  
  private int D_SECTIONTIMERSTOPLOC = -1;
  
  private int Q_ENDTIMER = -1;
  
  private int Q_DANGERPOINT = -1;
  
  private int D_DP = -1;
  
  private int V_RELEASEDP = -1;
  
  private int Q_OVERLAP = -1;
  
  private int[][] iter;
  
  public MovementAuthority(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    // Byte code:
    //   0: bipush #23
    //   2: istore_1
    //   3: aload_0
    //   4: invokespecial getCode : ()[B
    //   7: iload_1
    //   8: iinc #1, 2
    //   11: iload_1
    //   12: invokestatic getBit : ([BII)[B
    //   15: astore_2
    //   16: aload_0
    //   17: aload_2
    //   18: iconst_0
    //   19: baload
    //   20: invokestatic getUnsigned : (B)I
    //   23: putfield Q_SCALE : I
    //   26: aload_0
    //   27: invokespecial getCode : ()[B
    //   30: iload_1
    //   31: iinc #1, 7
    //   34: iload_1
    //   35: invokestatic getBit : ([BII)[B
    //   38: astore_2
    //   39: aload_0
    //   40: aload_2
    //   41: iconst_0
    //   42: baload
    //   43: invokestatic getUnsigned : (B)I
    //   46: putfield V_MAIN : I
    //   49: aload_0
    //   50: invokespecial getCode : ()[B
    //   53: iload_1
    //   54: iinc #1, 7
    //   57: iload_1
    //   58: invokestatic getBit : ([BII)[B
    //   61: astore_2
    //   62: aload_0
    //   63: aload_2
    //   64: iconst_0
    //   65: baload
    //   66: invokestatic getUnsigned : (B)I
    //   69: putfield V_LOA : I
    //   72: aload_0
    //   73: invokespecial getCode : ()[B
    //   76: iload_1
    //   77: iinc #1, 10
    //   80: iload_1
    //   81: invokestatic getBit : ([BII)[B
    //   84: astore_2
    //   85: aload_0
    //   86: aload_2
    //   87: iconst_0
    //   88: baload
    //   89: aload_2
    //   90: iconst_1
    //   91: baload
    //   92: invokestatic getUnsigned : (BB)I
    //   95: putfield T_LOA : I
    //   98: aload_0
    //   99: invokespecial getCode : ()[B
    //   102: iload_1
    //   103: iinc #1, 5
    //   106: iload_1
    //   107: invokestatic getBit : ([BII)[B
    //   110: astore_2
    //   111: aload_0
    //   112: aload_2
    //   113: iconst_0
    //   114: baload
    //   115: invokestatic getUnsigned : (B)I
    //   118: putfield N_ITER : I
    //   121: aload_0
    //   122: aload_0
    //   123: invokespecial get_N_ITER : ()I
    //   126: iconst_5
    //   127: multianewarray[[I 2
    //   131: putfield iter : [[I
    //   134: iconst_1
    //   135: istore_3
    //   136: goto -> 494
    //   139: iload_1
    //   140: iload_3
    //   141: iconst_1
    //   142: isub
    //   143: bipush #107
    //   145: imul
    //   146: iadd
    //   147: istore_1
    //   148: bipush #13
    //   150: newarray int
    //   152: astore #4
    //   154: aload_0
    //   155: invokespecial getCode : ()[B
    //   158: iload_1
    //   159: iinc #1, 15
    //   162: iload_1
    //   163: invokestatic getBit : ([BII)[B
    //   166: astore_2
    //   167: aload #4
    //   169: iconst_0
    //   170: aload_2
    //   171: iconst_0
    //   172: baload
    //   173: aload_2
    //   174: iconst_1
    //   175: baload
    //   176: invokestatic getUnsigned : (BB)I
    //   179: iastore
    //   180: aload_0
    //   181: invokespecial getCode : ()[B
    //   184: iload_1
    //   185: iinc #1, 1
    //   188: iload_1
    //   189: invokestatic getBit : ([BII)[B
    //   192: astore_2
    //   193: aload #4
    //   195: iconst_1
    //   196: aload_2
    //   197: iconst_0
    //   198: baload
    //   199: invokestatic getUnsigned : (B)I
    //   202: iastore
    //   203: aload_0
    //   204: invokespecial getCode : ()[B
    //   207: iload_1
    //   208: iinc #1, 10
    //   211: iload_1
    //   212: invokestatic getBit : ([BII)[B
    //   215: astore_2
    //   216: aload #4
    //   218: iconst_2
    //   219: aload_2
    //   220: iconst_0
    //   221: baload
    //   222: aload_2
    //   223: iconst_1
    //   224: baload
    //   225: invokestatic getUnsigned : (BB)I
    //   228: iastore
    //   229: aload_0
    //   230: invokespecial getCode : ()[B
    //   233: iload_1
    //   234: iinc #1, 15
    //   237: iload_1
    //   238: invokestatic getBit : ([BII)[B
    //   241: astore_2
    //   242: aload #4
    //   244: iconst_3
    //   245: aload_2
    //   246: iconst_0
    //   247: baload
    //   248: aload_2
    //   249: iconst_1
    //   250: baload
    //   251: invokestatic getUnsigned : (BB)I
    //   254: iastore
    //   255: aload_0
    //   256: invokespecial getCode : ()[B
    //   259: iload_1
    //   260: iinc #1, 15
    //   263: iload_1
    //   264: invokestatic getBit : ([BII)[B
    //   267: astore_2
    //   268: aload #4
    //   270: iconst_4
    //   271: aload_2
    //   272: iconst_0
    //   273: baload
    //   274: aload_2
    //   275: iconst_1
    //   276: baload
    //   277: invokestatic getUnsigned : (BB)I
    //   280: iastore
    //   281: aload_0
    //   282: invokespecial getCode : ()[B
    //   285: iload_1
    //   286: iinc #1, 1
    //   289: iload_1
    //   290: invokestatic getBit : ([BII)[B
    //   293: astore_2
    //   294: aload #4
    //   296: iconst_5
    //   297: aload_2
    //   298: iconst_0
    //   299: baload
    //   300: invokestatic getUnsigned : (B)I
    //   303: iastore
    //   304: aload_0
    //   305: invokespecial getCode : ()[B
    //   308: iload_1
    //   309: iinc #1, 10
    //   312: iload_1
    //   313: invokestatic getBit : ([BII)[B
    //   316: astore_2
    //   317: aload #4
    //   319: bipush #6
    //   321: aload_2
    //   322: iconst_0
    //   323: baload
    //   324: aload_2
    //   325: iconst_1
    //   326: baload
    //   327: invokestatic getUnsigned : (BB)I
    //   330: iastore
    //   331: aload_0
    //   332: invokespecial getCode : ()[B
    //   335: iload_1
    //   336: iinc #1, 15
    //   339: iload_1
    //   340: invokestatic getBit : ([BII)[B
    //   343: astore_2
    //   344: aload #4
    //   346: bipush #7
    //   348: aload_2
    //   349: iconst_0
    //   350: baload
    //   351: aload_2
    //   352: iconst_1
    //   353: baload
    //   354: invokestatic getUnsigned : (BB)I
    //   357: iastore
    //   358: aload_0
    //   359: invokespecial getCode : ()[B
    //   362: iload_1
    //   363: iinc #1, 1
    //   366: iload_1
    //   367: invokestatic getBit : ([BII)[B
    //   370: astore_2
    //   371: aload #4
    //   373: bipush #8
    //   375: aload_2
    //   376: iconst_0
    //   377: baload
    //   378: invokestatic getUnsigned : (B)I
    //   381: iastore
    //   382: aload_0
    //   383: invokespecial getCode : ()[B
    //   386: iload_1
    //   387: iinc #1, 1
    //   390: iload_1
    //   391: invokestatic getBit : ([BII)[B
    //   394: astore_2
    //   395: aload #4
    //   397: bipush #9
    //   399: aload_2
    //   400: iconst_0
    //   401: baload
    //   402: invokestatic getUnsigned : (B)I
    //   405: iastore
    //   406: aload_0
    //   407: invokespecial getCode : ()[B
    //   410: iload_1
    //   411: iinc #1, 15
    //   414: iload_1
    //   415: invokestatic getBit : ([BII)[B
    //   418: astore_2
    //   419: aload #4
    //   421: bipush #10
    //   423: aload_2
    //   424: iconst_0
    //   425: baload
    //   426: aload_2
    //   427: iconst_1
    //   428: baload
    //   429: invokestatic getUnsigned : (BB)I
    //   432: iastore
    //   433: aload_0
    //   434: invokespecial getCode : ()[B
    //   437: iload_1
    //   438: iinc #1, 7
    //   441: iload_1
    //   442: invokestatic getBit : ([BII)[B
    //   445: astore_2
    //   446: aload #4
    //   448: bipush #11
    //   450: aload_2
    //   451: iconst_0
    //   452: baload
    //   453: invokestatic getUnsigned : (B)I
    //   456: iastore
    //   457: aload_0
    //   458: invokespecial getCode : ()[B
    //   461: iload_1
    //   462: iinc #1, 1
    //   465: iload_1
    //   466: invokestatic getBit : ([BII)[B
    //   469: astore_2
    //   470: aload #4
    //   472: bipush #12
    //   474: aload_2
    //   475: iconst_0
    //   476: baload
    //   477: invokestatic getUnsigned : (B)I
    //   480: iastore
    //   481: aload_0
    //   482: getfield iter : [[I
    //   485: iload_3
    //   486: iconst_1
    //   487: isub
    //   488: aload #4
    //   490: aastore
    //   491: iinc #3, 1
    //   494: iload_3
    //   495: aload_0
    //   496: invokespecial get_N_ITER : ()I
    //   499: if_icmple -> 139
    //   502: goto -> 506
    //   505: astore_1
    //   506: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #42	-> 0
    //   #43	-> 3
    //   #44	-> 16
    //   #46	-> 26
    //   #47	-> 39
    //   #49	-> 49
    //   #50	-> 62
    //   #52	-> 72
    //   #53	-> 85
    //   #55	-> 98
    //   #56	-> 111
    //   #58	-> 121
    //   #59	-> 134
    //   #60	-> 139
    //   #62	-> 148
    //   #65	-> 154
    //   #66	-> 167
    //   #69	-> 180
    //   #70	-> 193
    //   #73	-> 203
    //   #74	-> 216
    //   #77	-> 229
    //   #78	-> 242
    //   #81	-> 255
    //   #82	-> 268
    //   #85	-> 281
    //   #86	-> 294
    //   #91	-> 304
    //   #93	-> 317
    //   #97	-> 331
    //   #99	-> 344
    //   #103	-> 358
    //   #105	-> 371
    //   #109	-> 382
    //   #111	-> 395
    //   #115	-> 406
    //   #117	-> 419
    //   #121	-> 433
    //   #123	-> 446
    //   #127	-> 457
    //   #129	-> 470
    //   #131	-> 481
    //   #59	-> 491
    //   #135	-> 505
    //   #138	-> 506
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	507	0	this	Lcom/MiTAC/TRA/ATP/decoder/waySidePacket/MovementAuthority;
    //   3	502	1	loc	I
    //   16	489	2	tmpdata	[B
    //   136	366	3	x	I
    //   154	337	4	rtn	[I
    // Exception table:
    //   from	to	target	type
    //   0	505	505	java/lang/Exception
  }
  
  public int get_Q_SCALE() {
    return this.Q_SCALE;
  }
  
  public int get_V_MAIN() {
    return this.V_MAIN;
  }
  
  public int get_V_LOA() {
    return this.V_LOA;
  }
  
  public int get_T_LOA() {
    return this.T_LOA;
  }
  
  private int get_N_ITER() {
    return this.N_ITER;
  }
  
  public int[] get_ITER(int no) {
    if (no > 0 && no <= get_N_ITER())
      return this.iter[no - 1]; 
    return new int[] { -1, -1, -1, -1, -1 };
  }
  
  public int get_Q_SECTIONTIMER() {
    return this.Q_SECTIONTIMER;
  }
  
  public int get_T_SECTIONTIMER() {
    return this.T_SECTIONTIMER;
  }
  
  public int get_D_SECTIONTIMERSTOPLOC() {
    return this.D_SECTIONTIMERSTOPLOC;
  }
  
  public int get_Q_ENDTIMER() {
    return this.Q_ENDTIMER;
  }
  
  public int get_Q_DANGERPOINT() {
    return this.Q_DANGERPOINT;
  }
  
  public int get_D_DP() {
    return this.D_DP;
  }
  
  public int get_V_RELEASEDP() {
    return this.V_RELEASEDP;
  }
  
  public int get_Q_OVERLAP() {
    return this.Q_OVERLAP;
  }
  
  public String toString() {
    try {
      StringBuffer tmp = new StringBuffer();
      tmp.append("== P12, MovementAuthority. ==");
      tmp.append("\r\n");
      tmp.append(super.toString());
      tmp.append("\r\n");
      tmp.append("Q_SCALE:\t" + get_Q_SCALE());
      tmp.append("\r\n");
      tmp.append("V_MAIN:\t" + get_V_MAIN());
      tmp.append("\r\n");
      tmp.append("V_LOA:\t" + get_V_LOA());
      tmp.append("\r\n");
      tmp.append("T_LOA:\t" + get_T_LOA());
      tmp.append("\r\n");
      tmp.append("N_ITER:\t" + get_N_ITER());
      tmp.append("\r\n");
      for (int x = 1; x <= get_N_ITER(); x++) {
        int[] tmpdata = get_ITER(x);
        tmp.append("\t");
        tmp.append("L_SECTION:\t" + tmpdata[0]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("Q_SECTIONTIMER:\t" + tmpdata[1]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("T_SECTIONTIMER:\t" + tmpdata[2]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("D_SECTIONTIMERSTOPLOC:\t" + tmpdata[3]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("L_ENDSECTION:\t" + tmpdata[4]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("Q_SECTIONTIMER:\t" + tmpdata[5]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("T_SECTIONTIMER:\t" + tmpdata[6]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("D_SECTIONTIMERSTOPLOC:\t" + tmpdata[7]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("Q_ENDTIMER:\t" + tmpdata[8]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("Q_DANGERPOINT:\t" + tmpdata[9]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("D_DP:\t" + tmpdata[10]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("V_RELEASEDP:\t" + tmpdata[11]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("Q_OVERLAP:\t" + tmpdata[12]);
        tmp.append("\r\n");
      } 
      return tmp.toString();
    } catch (Exception ex) {
      return ">>Exception<<\r\n" + getClass() + "\r\n" + super.toString();
    } 
  }
}
