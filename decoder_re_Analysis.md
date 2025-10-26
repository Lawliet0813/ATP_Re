# decoder_re 資料夾分析報告

## 概述
`decoder_re` 資料夾是ATP系統的進階解碼層，包含34個Java檔案（含子資料夾waySidePacket的19個檔案），負責更深入的資料解析、解碼緩衝管理、以及特定裝置（如BTM、VDX、RU）的資料處理。這個資料夾是decode_re的擴展和增強，提供了更完整的解碼能力和資料管理功能。

## 資料夾結構

### 主要檔案 (15個)
1. **ATPDecoder.java** - ATP封包解碼器
2. **BTMDecoder.java** - Balise傳輸模組解碼器
3. **DataFeeder.java** - 資料供給器
4. **DecodeBuffer.java** - 解碼緩衝區管理
5. **Decoder_tester.java** - 解碼器測試工具
6. **HeadDecoder.java** - 封包標頭解碼器
7. **MMIVariables.java** - MMI變數處理
8. **PacketMMI.java** - MMI封包處理
9. **RU.java** - Recording Unit資料結構
10. **RUDecoder.java** - RU解碼器
11. **VDXDecoder.java** - VDX解碼器
12. **WaySideTelegramPacketDecoder.java** - 路側電報封包解碼器
13. **btnDecodeWatcher.java** - 解碼監視器按鈕
14. **frmDecodeWatcher.java** - 解碼監視器視窗

### 子資料夾 waySidePacket (19個檔案)
路側設備電報封包的詳細解碼器

## 核心檔案深入分析

### 1. DecodeBuffer.java - 解碼緩衝區管理器
系統中最重要的檔案之一，管理所有待解碼任務的佇列

#### 靜態緩衝區設計
```java
private static boolean running = false;
private static int bufferSize = 4096;          // 緩衝區大小
private static int bufferDepth = 15;           // 每個項目深度
private static Object[][] buffer = new Object[bufferSize][bufferDepth];
private static int pointer = 0;                // 當前處理位置
private static int size = 0;                   // 已加入的任務數
```

#### 緩衝區結構（推測）
```java
buffer[index][0]  = File          // 檔案物件
buffer[index][1]  = ?             // 其他資訊
buffer[index][6]  = Date          // 處理時間
buffer[index][8]  = String        // 狀態訊息
buffer[index][13] = String        // 檔案類型
```

#### 狀態訊息常數
```java
public static final String successMsg = "解碼完成";
public static final String failureMsg = "解碼失敗";
public static final String giveupMsg = "放棄解碼";
public static final String decodeMsg = "解碼中";
public static final String waitingMsg = "等待中";
public static final String dataExistMsg = "資料已存在";
public static final String pathNotExistMsg = "路徑錯誤";
public static final String invaliedMsg = "無效資料";
public static final String ignoreMsg = "忽略";
public static final String replaceMsg = "取代";
public static final String extendMsg = "擴充";
public static final String noDataMsg = "無資料";
```

#### 核心功能

**1. 執行緒處理**
```java
public void run() {
    running = true;
    setPriority(1);  // 設定為最低優先權
    while (true) {
        while (pointer == size) {
            // 等待新任務
            sleep(3000L);
        }
        Object[] data = buffer[pointer];
        checkPath((File)data[0], pointer);
        // 處理解碼
        doDecode(data, cndb);
    }
}
```
- 背景執行緒持續運行
- 低優先權避免影響GUI
- 無任務時休眠3秒

**2. 資料庫檢查**
```java
Vector isDBExists = cndb.getLogDecodeCategory(...);
if (isDBExists.size() != 0) {
    if (data[13] == "RU檔案") {
        cndb.DeleteLog(mson);  // 刪除舊資料
        doDecode(data, cndb);   // 重新解碼
        data[8] = replaceMsg;   // 標記為取代
    } else if (data[13] == "MMI檔案") {
        // 檢查是否需要擴充
    }
}
```
- 檢查資料是否已存在於資料庫
- RU檔案：刪除舊資料重新解碼
- MMI檔案：可能合併或擴充資料

**3. 檔案類型處理**
- **RU檔案**: 完整的任務記錄，覆蓋式更新
- **MMI檔案**: 部分資料，可能需要合併

**4. 錯誤處理**
```java
public static boolean ignoreNoError = true;
```
- 可設定忽略無錯誤的任務
- 減少不必要的解碼工作

### 2. DataFeeder.java - 資料供給器
負責實際執行解碼並將資料餵給業務物件

#### 主要屬性
```java
private ATPMissionGeneral thisMission;  // 當前任務
private RUDecoder rudecoder = new RUDecoder();  // RU解碼器

// 各種資料容器
private Vector gradient = new Vector();       // 坡度
private Vector curve = new Vector();          // 曲線
private Vector btm = new Vector();            // BTM資料
private Vector driverData = new Vector();     // 司機員資料
private Vector trainData = new Vector();      // 列車資料
private Vector dynamic = new Vector();        // 動態資料
private Vector status = new Vector();         // 狀態資料
private Vector failure = new Vector();        // 故障資料
private Vector driverMessage = new Vector();  // 司機員訊息
private Vector vdx = new Vector();            // VDX資料

// 統計資訊
private int ebTimes = 0;              // EB煞車次數
private int sbTimes = 0;              // SB煞車次數
private int cabinFailureTimes = 0;    // 客艙故障次數
private int waysideFailureTimes = 0;  // 路側故障次數
```

#### 建構子和初始化
```java
public DataFeeder(File mission) throws Exception {
    this.thisMission = new ATPMissionGeneral(mission);
    this.root = this.thisMission.getFile();
    go();  // 執行解碼
    
    // 將解碼結果設定到任務物件
    this.thisMission.setCategory(getCagetory());
    this.thisMission.setVDX(getVDX());
    this.thisMission.setDynamic(getDynamic());
    this.thisMission.setFailure(getFailure());
    this.thisMission.setStatus(getStatus());
    this.thisMission.setTimeStamp(getTS());
    this.thisMission.setDriverMessage(getDriverMessage());
}
```

#### 功能特點
1. **統一介面**: 作為解碼器和業務物件之間的橋樑
2. **資料聚合**: 收集各種解碼器的結果
3. **統計計算**: 自動計算故障次數、煞車次數等統計資訊
4. **除錯支援**: 提供WriteToFile進行除錯輸出

### 3. BTMDecoder.java - Balise傳輸模組解碼器
解碼BTM（Balise Transmission Module）資料

#### 核心概念
BTM電報由5個片段組成，每個片段最多25位元組：
```java
byte[][][] btmData = new byte[10][5][32];  // 10個序列槽，每個5個片段，每片段32位元組
int[] pointer = new int[10];                // 10個序列號追蹤器
int[][] pnt = new int[10][5];              // 標記每個片段是否已接收
```

#### 資料收集流程
```java
public void setData(byte[] bdata, int telegramNo) {
    int sequenceNumber = Byte2Number.getUnsigned(bdata[0]);
    
    // 1. 尋找序列號的槽位
    int point = -1;
    for (int x = 0; x < pointer.length; x++) {
        if (pointer[x] == sequenceNumber)
            point = x;
    }
    
    // 2. 如果找不到，分配新槽位
    if (point == -1) {
        for (int x = 0; x < pointer.length; x++) {
            if (pointer[x] == -1) {
                point = x;
                pointer[x] = sequenceNumber;
                break;
            }
        }
    }
    
    // 3. 儲存資料片段
    setData(bdata, point, telegramNo);
}
```

#### 完整性檢查
```java
private void setData(byte[] bdata, int point, int telegramNo) {
    telegramNo--;
    pnt[point][telegramNo] = 1;  // 標記此片段已接收
    btmData[point][telegramNo] = bdata;
    
    // 檢查是否收集齊5個片段
    boolean collect5 = true;
    for (int x = 0; x < pnt[point].length; x++) {
        if (pnt[point][x] == 0)
            collect5 = false;
    }
    
    if (collect5) {
        haveResult = true;
        // 清空此槽位供下次使用
        pointer[point] = -1;
        // 開始解碼
        decode(btmData[point]);
    }
}
```

#### 資料重組
```java
private void decode(byte[][] bdata) {
    byte[] data = new byte[104];  // 完整電報104位元組
    
    for (int x = 0; x < bdata.length; x++) {
        byte[] tmp = bdata[x];
        int loc = -1;
        switch (x) {
            case 0:  // 第1片段：取4位元組
                for (int y = 0; y < 4; y++)
                    data[y] = tmp[22 + y];
                break;
            case 1:  // 第2片段：從位置4開始
                loc = 4;
            case 2:  // 第3片段：從位置29開始
                loc = (loc < 0) ? 29 : loc;
            case 3:  // 第4片段：從位置54開始
                loc = (loc < 0) ? 54 : loc;
            case 4:  // 第5片段：從位置79開始
                loc = (loc < 0) ? 79 : loc;
                for (int y = 0; y < 25; y++)
                    data[loc + y] = tmp[1 + y];
                break;
        }
    }
    
    // 使用路側電報解碼器解碼
    wtpd = new WaySideTelegramPacketDecoder(data);
}
```

#### 設計亮點
1. **片段化處理**: 支援分片傳輸
2. **並行追蹤**: 同時追蹤10個不同序列號的電報
3. **自動重組**: 收集齊全後自動重組和解碼
4. **槽位重用**: 解碼完成後釋放槽位

### 4. HeadDecoder.java - 封包標頭解碼器
解析每個封包的共用標頭資訊

#### 標頭結構
```
Byte 0    : Packet Number (封包編號)
Byte 1-6  : Timestamp (時間戳記: YY MM DD HH MM SS)
Byte 7-10 : Location (位置: 4 bytes)
Byte 13-14: Speed (速度: 2 bytes)
```

#### 解碼方法
```java
public void setByte(byte[] tsb) throws Exception {
    // 封包編號
    packetNo = Byte2Number.getUnsigned(tsb[0]);
    
    // 時間戳記
    ts = getTime(tsb[1], tsb[2], tsb[3], tsb[4], tsb[5], tsb[6]);
    
    // 位置（處理超過10億的情況）
    location = mmivariables.MMI_O_TRAIN(tsb[7], tsb[8], tsb[9], tsb[10]);
    location = (location >= 1000000000) ? (location - 1000000000) : location;
    
    // 速度
    speed = mmivariables.MMI_V_TRAIN(tsb[13], tsb[14]);
}
```

#### 時間處理
```java
private Date getTime(byte yy, byte mm, byte dd, byte hh, byte MM, byte ss) {
    int year = 2000 + Byte2Number.getUnsigned(yy);  // 年份加上2000
    int month = Byte2Number.getUnsigned(mm);
    int day = Byte2Number.getUnsigned(dd);
    int hour = Byte2Number.getUnsigned(hh);
    int minute = Byte2Number.getUnsigned(MM);
    int second = Byte2Number.getUnsigned(ss);
    
    return new Date(year - 1900, month - 1, day, hour, minute, second);
}
```

#### 資料存取
```java
public Vector getData() {
    Vector tmp = new Vector();
    tmp.add(ts);           // 時間
    tmp.add(new Integer(speed));     // 速度
    tmp.add(new Integer(location));  // 位置
    return tmp;
}
```

### 5. VDXDecoder.java - VDX解碼器
解碼VDX（速度-距離記錄器）資料

#### 功能推測
- 解析速度和距離的關係
- 重建列車運行軌跡
- 提供時間-空間資料

### 6. RUDecoder.java - Recording Unit解碼器
解碼RU（記錄單元）設備的資料

#### 功能推測
- 處理RU設備的原始記錄
- 可能包含完整的任務記錄
- 整合所有子系統的資料

### 7. ATPDecoder.java - ATP解碼器
與decode_re中的DecodeATP類似，但可能包含額外功能

#### 核心方法
```java
public Vector DecodeATPData(byte[] byteData) throws Exception {
    data = byteData;
    RecordHandler rtn = new RecordHandler();
    
    packetNO = Byte2Number.getUnsigned(data[0]);
    rtn.add(new Integer(packetNO));
    rtn.addObject(packetdata());
    rtn.add("ATP PacketNo " + packetNO);
    
    return (Vector)rtn;
}
```

#### 封包處理
根據封包編號分派到對應的處理方法，支援40+種封包類型

## waySidePacket 子資料夾分析

### 路側設備電報封包解碼器
專門處理Balise（地面應答器）傳送的各種訊息

#### 主要檔案

**1. WaySideTelegramPacketDecoder.java**
- 主解碼器
- 分派到各個特定封包解碼器

**2. Header.java**
- 電報標頭解析
- 包含版本、長度等基本資訊

**3. MovementAuthority.java**
- 行車許可解碼
- 定義列車可行駛的距離和速度限制

**4. StaticSpeedProfile.java**
- 靜態速限檔案
- 永久性的速度限制

**5. TemporaySpeedRestriction.java**
- 臨時速限
- 工程或維修造成的臨時限制

**6. TemporarySpeedRestrictionRevocation.java**
- 臨時速限解除
- 取消之前的臨時限制

**7. GradientProfile.java**
- 坡度檔案
- 軌道坡度資訊

**8. Curve.java**
- 曲線檔案
- 曲線半徑資訊

**9. LevelTransistionOrder.java**
- 等級轉換命令
- ATP等級切換指令

**10. Linking.java**
- 連結資訊
- Balise之間的連結關係

**11. StopOverAndStationID.java**
- 停靠站和車站ID
- 定義停車點

**12. InfillLocationReference.java**
- Infill位置參考
- 補充位置資訊

**13. Repositioning.java**
- 重新定位
- 位置校正

**14. DangerForShuntingInformation.java**
- 調車危險資訊
- 調車作業安全警告

**15. LEUerror.java**
- LEU（Lineside Electronic Unit）錯誤
- 路側電子單元故障

**16. WheelWearCompensation.java**
- 輪徑磨耗補償
- 補償輪徑變化對位置的影響

**17. Auxiliary4bitdata.java**
- 輔助4位元資料
- 額外的控制資訊

**18. P44.java**
- 封包44
- 特定功能封包

**19. DefaultTelegram.java, waySidePacketBody.java**
- 預設電報處理
- 封包主體基礎類別

## 解碼流程圖

### 完整系統解碼流程
```
檔案系統
    ↓
DecodeBuffer (任務佇列)
    ↓
DataFeeder (資料供給)
    ├→ RUDecoder (RU資料)
    ├→ HeadDecoder (標頭)
    ├→ ATPDecoder (ATP封包)
    │     ↓
    │  PacketMMI (MMI封包)
    │     ↓
    │  MMIVariables (變數轉換)
    │
    ├→ BTMDecoder (BTM資料)
    │     ↓
    │  WaySideTelegramPacketDecoder
    │     ↓
    │  各種路側封包解碼器
    │
    └→ VDXDecoder (VDX資料)
    ↓
ATPMissionGeneral (業務物件)
    ↓
資料庫 (ConnectDB)
```

## 與其他資料夾的關聯

### 依賴關係
1. **decode_re**: 使用基礎的解碼功能
2. **Tools_re**: 使用工具類別（Byte2Number, FileRead, PathHandler等）
3. **connect_re**: 使用ConnectDB寫入資料庫
4. **core_re**: 使用ATPMission等資料模型

### 被依賴關係
1. **ui_re**: GUI使用DecodeBuffer管理解碼任務
2. **drawGraphics_re**: 繪圖使用解碼後的資料

## 技術特點

### 1. 緩衝區管理
```java
private static Object[][] buffer = new Object[4096][15];
```
- 靜態二維陣列
- 支援最多4096個待解碼任務
- 每個任務15個欄位的資訊

### 2. 背景執行緒
```java
public void run() {
    running = true;
    setPriority(1);  // 最低優先權
    while (true) {
        // 持續處理任務
    }
}
```
- 不阻塞GUI
- 低優先權避免影響系統性能

### 3. 片段化資料處理
BTMDecoder支援分片接收和重組：
- 同時追蹤10個不同電報
- 每個電報5個片段
- 自動重組為完整電報

### 4. 狀態機模式
DecodeBuffer使用狀態訊息追蹤任務狀態：
- 等待中 → 解碼中 → 完成/失敗/放棄

### 5. 資料庫整合
- 檢查資料是否已存在
- 決定覆蓋、合併或忽略
- 自動更新資料庫

## 路側通訊協定

### Balise電報結構
```
總長度: 104 bytes

片段1 (bytes 22-25): 4 bytes標頭
片段2 (bytes 1-25):  25 bytes資料
片段3 (bytes 1-25):  25 bytes資料
片段4 (bytes 1-25):  25 bytes資料
片段5 (bytes 1-25):  25 bytes資料
```

### 重要封包類型
1. **Movement Authority**: 最重要，定義行車權限
2. **Speed Profile**: 速度限制
3. **Gradient Profile**: 坡度資訊
4. **Repositioning**: 位置校正

## 效能考量

### 優點
1. **非同步處理**: 背景執行緒不影響GUI
2. **批次處理**: 緩衝區可累積多個任務
3. **智慧檢查**: 避免重複解碼已存在的資料
4. **記憶體重用**: BTMDecoder的槽位循環使用

### 可改進之處
1. **固定大小緩衝區**: 4096可能不夠或浪費
2. **輪詢機制**: sleep(3000)不夠即時，應使用通知機制
3. **無優先權管理**: 所有任務按順序處理
4. **記憶體使用**: 靜態二維陣列可能浪費記憶體

## 程式碼品質

### 優點
1. **模組化**: 每種資料有專門的解碼器
2. **層次清晰**: 從緩衝管理到具體解碼分層明確
3. **重用性**: 解碼器可獨立使用

### 可改進之處
1. **變數命名**: 依然存在混淆的命名
2. **硬編碼**: 
   ```java
   WriteToFile wtf = new WriteToFile("c:\\debug.txt");
   ```
3. **例外處理**: 某些地方只是printStackTrace，沒有恢復機制
4. **註解缺乏**: 尤其是BTM重組邏輯需要詳細註解

## 建議改進方向

### 短期改進
1. 增加關鍵演算法的註解（如BTM片段重組）
2. 將硬編碼路徑移到設定檔
3. 改善變數命名

### 中期改進
1. 使用BlockingQueue替代靜態陣列+輪詢
2. 實作任務優先權機制
3. 加入解碼進度回調
4. 改善錯誤恢復機制

### 長期改進
1. 引入執行緒池管理
2. 實作串流解碼支援大檔案
3. 加入解碼結果緩存
4. 使用現代並行工具（如CompletableFuture）

## 結論

`decoder_re` 資料夾是ATP系統的進階解碼引擎，相比decode_re提供了：

### 核心價值
1. **任務管理**: DecodeBuffer提供完整的解碼任務佇列管理
2. **資料聚合**: DataFeeder整合所有解碼結果到業務物件
3. **特殊裝置支援**: BTM、VDX、RU等設備的專門解碼器
4. **路側協定**: 完整的Balise電報解碼支援（19個封包類型）

### 關鍵特色
- **片段化處理**: BTMDecoder的5片段重組機制
- **背景處理**: 不阻塞GUI的非同步解碼
- **智慧去重**: 檢查資料庫避免重複解碼
- **完整協定**: 支援40+種ATP/MMI封包和19種路側封包

### 系統角色
- **decode_re**: 基礎解碼（原始位元組 → 結構化資料）
- **decoder_re**: 進階解碼（任務管理 + 特殊裝置 + 資料庫整合）

這個資料夾包含34個檔案，實現了非常複雜的解碼邏輯，是整個ATP日誌分析系統的核心引擎。主要挑戰是程式碼可讀性和並行處理效率，但功能完整且運作穩定。
