package decoder;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.WaySideTelegramPacketDecoder;
import java.util.Vector;

/**
 * BTMDecoder is responsible for reassembling BTM (Balise Transmission Module) telegrams
 * from 5 data fragments received over MVB (Multifunction Vehicle Bus).
 * 
 * <p>Each complete BTM telegram is split into 5 fragments that arrive separately.
 * This class tracks multiple telegram sequences simultaneously and reassembles them.</p>
 * 
 * <h3>Data Structure:</h3>
 * <ul>
 *   <li><b>pointer</b>: Array tracking which sequence numbers are being collected (index = slot, value = sequence number)</li>
 *   <li><b>pnt</b>: 2D array tracking which fragments have been received (pnt[slot][fragmentNo] = 1 if received)</li>
 *   <li><b>btmData</b>: 3D array storing the actual fragment data (btmData[slot][fragmentNo][bytes])</li>
 * </ul>
 * 
 * <h3>Reassembly Process:</h3>
 * <ol>
 *   <li>When a fragment arrives (via setData), extract its sequence number from byte[0]</li>
 *   <li>Find or allocate a slot in the pointer array for this sequence number</li>
 *   <li>Store the fragment data in btmData[slot][fragmentNo]</li>
 *   <li>Mark the fragment as received in pnt[slot][fragmentNo] = 1</li>
 *   <li>When all 5 fragments are received (all pnt[slot][0-4] == 1), decode the complete telegram</li>
 *   <li>Reset the slot for reuse with the next sequence</li>
 * </ol>
 * 
 * <h3>Fragment Layout:</h3>
 * <pre>
 * Fragment 0: bytes 22-25 (4 bytes) -> output bytes 0-3
 * Fragment 1: bytes 1-25  (25 bytes) -> output bytes 4-28
 * Fragment 2: bytes 1-25  (25 bytes) -> output bytes 29-53
 * Fragment 3: bytes 1-25  (25 bytes) -> output bytes 54-78
 * Fragment 4: bytes 1-25  (25 bytes) -> output bytes 79-103
 * Total: 104 bytes
 * </pre>
 */
public class BTMDecoder {
  /**
   * Array of 10 slots to track up to 10 concurrent telegram sequences.
   * Each element stores the sequence number being collected in that slot,
   * or -1 if the slot is available.
   */
  int[] pointer = new int[10];
  
  /**
   * 2D array tracking which of the 5 fragments have been received for each slot.
   * pnt[slot][fragmentNo] = 1 when that fragment has been received.
   * pnt[slot][fragmentNo] = 0 when that fragment is still missing.
   */
  int[][] pnt = new int[10][5];
  
  /**
   * 3D array storing the actual fragment data.
   * btmData[slot][fragmentNo][bytes] contains up to 32 bytes per fragment.
   */
  byte[][][] btmData = new byte[10][5][32];
  
  private WaySideTelegramPacketDecoder wtpd;
  
  private boolean haveResult = false;
  
  public BTMDecoder() {
    init();
  }
  
  private void init() {
    for (int x = 0; x < this.pointer.length; x++) {
      this.pointer[x] = -1;
      for (int y = 0; y < (this.pnt[x]).length; y++)
        this.pnt[x][y] = 0; 
    } 
  }
  
  /**
   * Receives a BTM telegram fragment and stores it for reassembly.
   * 
   * @param bdata the fragment data (32 bytes), where byte[0] contains the sequence number
   * @param telegramNo the fragment number (1-5)
   */
  public void setData(byte[] bdata, int telegramNo) {
    int sequenceNumber = Byte2Number.getUnsigned(bdata[0]);
    int point = -1;
    int x;
    for (x = 0; x < this.pointer.length; x++) {
      if (this.pointer[x] == sequenceNumber)
        point = x; 
    } 
    if (point == -1)
      for (x = 0; x < this.pointer.length; x++) {
        if (this.pointer[x] == -1) {
          point = x;
          this.pointer[x] = sequenceNumber;
          break;
        } 
      }  
    if (point != -1)
      setData(bdata, point, telegramNo); 
  }
  
  private void setData(byte[] bdata, int point, int telegramNo) {
    this.haveResult = false;
    telegramNo--;
    this.pnt[point][telegramNo] = 1;
    this.btmData[point][telegramNo] = bdata;
    boolean collect5 = true;
    int x;
    for (x = 0; x < (this.pnt[point]).length; x++) {
      if (this.pnt[point][x] == 0)
        collect5 = false; 
    } 
    if (collect5) {
      this.haveResult = true;
      this.pointer[point] = -1;
      for (x = 0; x < (this.pnt[point]).length; x++)
        this.pnt[point][x] = 0; 
      decode(this.btmData[point]);
    } 
  }
  
  /**
   * Decodes a complete BTM telegram by extracting and concatenating data from all 5 fragments.
   * 
   * <p>The 5 fragments are reassembled into a single 104-byte array according to the
   * ERTMS/ETCS specification for BTM telegrams:</p>
   * <ul>
   *   <li>Fragment 0 (bytes 22-25): 4 bytes of header data</li>
   *   <li>Fragments 1-4 (bytes 1-25 each): 100 bytes of telegram payload</li>
   * </ul>
   * 
   * @param bdata 2D array containing the 5 fragments [fragment][bytes]
   */
  private void decode(byte[][] bdata) {
    try {
      byte[] data = new byte[104];
      for (int x = 0; x < bdata.length; x++) {
        int y;
        byte[] tmp = bdata[x];
        int loc = -1;
        switch (x) {
          case 0:
            for (y = 0; y < 4; y++)
              data[y] = tmp[22 + y]; 
            break;
          case 1:
            loc = (loc < 0) ? 4 : loc;
          case 2:
            loc = (loc < 0) ? 29 : loc;
          case 3:
            loc = (loc < 0) ? 54 : loc;
          case 4:
            loc = (loc < 0) ? 79 : loc;
            for (y = 0; y < 25; y++)
              data[loc + y] = tmp[1 + y]; 
            break;
        } 
      } 
      if (this.wtpd == null) {
        this.wtpd = new WaySideTelegramPacketDecoder(data);
      } else {
        this.wtpd.set(data);
        this.wtpd.go();
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public String toString() {
    return this.wtpd.getTelegramStream();
  }
  
  public int[] getBTMData() {
    return this.wtpd.get();
  }
  
  public int getNID_BG() {
    return this.wtpd.getNID_BG();
  }
  
  public Vector getGradient() {
    int[][] gradient = this.wtpd.getGradient();
    Vector rtn = new Vector();
    for (int x = 0; x < gradient.length; x++) {
      int[] zz = gradient[x];
      rtn.add(new Integer(zz[0]));
      rtn.add(new Integer(zz[1]));
      rtn.add(new Integer(zz[2]));
    } 
    while (rtn.size() < 27)
      rtn.add(new Integer(-1)); 
    return rtn;
  }
  
  public Vector getCurve() {
    int[][] curve = this.wtpd.getCurve();
    Vector rtn = new Vector();
    for (int x = 0; x < curve.length; x++) {
      int[] zz = curve[x];
      rtn.add(new Integer(zz[0]));
      rtn.add(new Integer(zz[1]));
      rtn.add(new Integer(zz[2]));
    } 
    while (rtn.size() < 27)
      rtn.add(new Integer(-1)); 
    return rtn;
  }
  
  public int[] getDetail() {
    return new int[] { this.wtpd.getNID_BG(), this.wtpd.getMCOUNT() };
  }
  
  public boolean isDataCollectFinished() {
    return this.haveResult;
  }
}
