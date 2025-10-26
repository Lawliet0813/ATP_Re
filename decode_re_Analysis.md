# decode_re 資料夾分析報告

## 概述
`decode_re` 資料夾是ATP系統的解碼層，包含9個Java檔案，負責解析ATP和MMI（Man-Machine Interface，人機介面）之間的通訊封包。這個資料夾將原始的二進位位元組資料轉換成可讀的結構化資料，是系統資料處理的關鍵環節。

## 資料夾結構

### 檔案清單
1. **Application1.java** - 應用程式主類別
2. **DecodeATP.java** - ATP封包解碼器
3. **DecodeTS.java** - TS (Train Speed/Time Series?) 解碼器
4. **Decoder.java** - 主解碼器類別
5. **Frame1.java** - GUI框架類別
6. **LogDataCollector.java** - 日誌資料收集器
7. **MMIVariables.java** - MMI變數處理
8. **PacketMMI.java** - MMI封包定義和處理
9. **RU.java** - Recording Unit 記錄單元

## 詳細檔案分析

### 1. Decoder.java - 主解碼器類別
統籌所有解碼工作的核心類別

#### 主要屬性
```java
private DecodeATP _$25797 = new DecodeATP();  // ATP解碼器
private Vector _$25801;  // 所有資料
private Vector _$25799;  // ATP資料
private Vector _$25800;  // 錯誤資料
private Vector _$25802;  // 其他資料
private Vector _$25798;  // TS資料
```

#### 日誌資料集合
```java
private Vector _$22268;  // 司機員資料日誌
private Vector _$22275;  // 司機員訊息日誌
private Vector _$22272;  // 動態資料日誌
private Vector _$22274;  // 故障資料日誌
private Vector _$22273;  // 狀態資料日誌
private Vector _$22271;  // TS資料日誌
private Vector _$22269;  // 列車資料日誌
```

#### 建構子
```java
public Decoder(String path) throws Exception {
    setPath(path);
}
```
- 接收檔案路徑
- 初始化所有資料容器
- 開始解碼流程

#### 核心方法
```java
public void setPath(String path) throws Exception
```
- 設定要解碼的檔案路徑
- 初始化所有Vector容器
- 準備解碼流程

#### 資料存取方法
- `getAll()`: 取得所有解碼資料
- `getATP()`: 取得ATP封包資料
- `getTS()`: 取得TS資料
- `getErr()`: 取得錯誤資料
- `getLogDriverData()`: 取得司機員資料日誌
- `getLogDriverMessage()`: 取得司機員訊息日誌
- `getLogDynamic()`: 取得動態資料日誌
- `getLogFailure()`: 取得故障資料日誌
- `getLogStatus()`: 取得狀態資料日誌
- `getLogTS()`: 取得時間序列日誌
- `getLogTrainData()`: 取得列車資料日誌

#### 工作流程
1. 讀取二進位檔案
2. 使用FileRead讀取位元組資料
3. 使用DecodeATP和DecodeTS解碼
4. 將資料分類存入不同Vector
5. 提供存取介面

### 2. DecodeATP.java - ATP封包解碼器
專門處理ATP系統封包的解碼

#### 主要屬性
```java
private Vector _$25799;  // ATP資料
private Vector _$25887;  // 司機員資料
private Vector _$22275;  // 司機員訊息
private Vector _$22272;  // 動態資料
private Vector _$22274;  // 故障資料
private Vector _$22273;  // 狀態資料
private Vector _$22271;  // TS資料
private Vector _$22269;  // 列車資料
```

#### 核心方法
```java
public void setData(byte[] data) throws Exception
```
解碼流程：
1. 取得封包編號 `Byte2Number.getUnsigned(data[1])`
2. 根據封包編號分派到對應的解碼方法
3. 將解碼結果存入對應的Vector

#### 支援的MMI封包類型
```java
switch (Byte2Number.getUnsigned(data[1])) {
    case 1:  // MMI_DYNAMIC
        vector = PacketMMI.MMI_DYMANIC(data);
        // 處理動態資料和TS資料
        break;
    case 2:  // MMI_STATUS
        this._$22273 = PacketMMI.MMI_STATUS(data);
        break;
    case 6:  // MMI_CURRENT_TRAIN_DATA
        this._$22269 = PacketMMI.MMI_CURRENT_TRAIN_DATA(data);
        break;
    case 8:  // MMI_DRIVER_MESSAGE
        this._$22275 = PacketMMI.MMI_DRIVER_MESSAGE(data);
        break;
    case 9:  // MMI_FAILURE_REPORT_ATP
        this._$22274 = PacketMMI.MMI_FAILURE_REPORT_ATP(data);
        break;
    case 14: // MMI_CURRENT_DRIVER_DATA
        this._$25887 = PacketMMI.MMI_CURRENT_DRIVER_DATA(data);
        break;
}
```

#### 資料存取方法
- `getData()`: 取得所有解碼資料
- `getLogDriver()`: 取得司機員資料
- `getLogDriverMessage()`: 取得司機員訊息
- `getLogDynamic()`: 取得動態資料
- `getLogFailure()`: 取得故障記錄
- `getLogStatus()`: 取得狀態資料
- `getLogTS()`: 取得時間序列資料
- `getLogTrainData()`: 取得列車資料

### 3. PacketMMI.java - MMI封包定義
定義所有MMI封包類型和處理方法

#### 封包編號常數定義

**ATP到MMI的封包 (0-19)**
```java
public static final int MMI_START_ATP = 0;              // ATP啟動
public static final int MMI_DYNAMIC = 1;                // 動態資料
public static final int MMI_STATUS = 2;                 // 狀態資料
public static final int MMI_SET_TIME_ATP = 3;           // 設定時間
public static final int MMI_TRACK_DESCRIPTION = 4;      // 軌道描述
public static final int MMI_GEO_POSITION = 5;           // 地理位置
public static final int MMI_CURRENT_TRAIN_DATA = 6;     // 當前列車資料
public static final int MMI_FORCED_DRIVER_REQUEST = 7;  // 強制司機員請求
public static final int MMI_DRIVER_MESSAGE = 8;         // 司機員訊息
public static final int MMI_FAILURE_REPORT_ATP = 9;     // ATP故障報告
public static final int MMI_ECHOED_TRAIN_DATA = 10;     // 回應列車資料
public static final int MMI_CURRENT_SR_RULES = 11;      // 當前SR規則
public static final int MMI_ECHOED_SR_RULES = 12;       // 回應SR規則
public static final int MMI_CURRENT_DRIVER_DATA = 14;   // 當前司機員資料
public static final int MMI_TEST_REQUEST = 15;          // 測試請求
public static final int MMI_SELECT_STM_REQUEST = 16;    // 選擇STM請求
public static final int MMI_RU_DATA = 19;               // RU資料
```

**MMI到ATP的封包 (100-120)**
```java
public static final int MMI_START_MMI = 100;            // MMI啟動
public static final int MMI_DRIVER_REQUEST = 101;       // 司機員請求
public static final int MMI_STATUS_REPORT = 102;        // 狀態報告
public static final int MMI_CONFIRMED_SR_RULES = 103;   // 確認SR規則
public static final int MMI_NEW_DRIVER_DATA = 104;      // 新司機員資料
public static final int MMI_NEW_SR_RULES = 106;         // 新SR規則
public static final int MMI_NEW_TRAIN_DATA = 107;       // 新列車資料
public static final int MMI_SET_TIME_MMI = 109;         // MMI設定時間
public static final int MMI_CONFIRMED_TRAIN_DATA = 110; // 確認列車資料
public static final int MMI_DRIVER_MESSAGE_ACK = 111;   // 司機員訊息確認
public static final int MMI_NEW_RBC_DATA = 112;         // 新RBC資料
public static final int MMI_FAILURE_REPORT_MMI = 113;   // MMI故障報告
public static final int MMI_TEST_RESULT = 114;          // 測試結果
public static final int MMI_FAILURE_REPORT_ACK = 115;   // 故障報告確認
public static final int MMI_SELECT_STM = 116;           // 選擇STM
public static final int MMI_RU_DATA_FROM_CAB_1 = 119;   // 駕駛室1的RU資料
public static final int MMI_RU_DATA_FROM_CAB_2 = 120;   // 駕駛室2的RU資料
```

#### 封包處理方法
```java
public static Vector MMI_CURRENT_DRIVER_DATA(byte[] data) {
    Vector vector = new Vector();
    // 提取司機員ID (8 bytes)
    byte[] driverID = {data[4], data[5], ..., data[11]};
    vector.add(MMIVariables.MMI_NID_DRIVER(driverID));
    
    // 提取操作編號 (4 bytes)
    byte[] operationID = {data[12], data[13], data[14], data[15]};
    vector.add(MMIVariables.MMI_NID_OPERATION(operationID));
    
    // 提取工作班次 (8 bytes)
    byte[] workshift = {data[16], data[17], ..., data[23]};
    vector.add(MMIVariables.MMI_NID_WORKSHIFT(workshift));
    
    return vector;
}
```
- 從位元組陣列中提取特定欄位
- 使用MMIVariables進行資料轉換
- 返回結構化的Vector資料

### 4. MMIVariables.java - MMI變數處理
提供MMI封包中各種變數的解析方法

#### 加速度相關
```java
public static int MMI_A_TRAIN(byte b1, byte b2)        // 列車加速度
public static int MMI_A_MAX_ACC(byte b1, byte b2)      // 最大加速度
public static int MMI_A_MAX_DEC(byte b1, byte b2)      // 最大減速度
public static int MMI_A_DEGUARANTEED(byte b1, byte b2) // 保證減速度
public static int MMI_A_DSGUARANTEED(byte b1, byte b2) // DS保證加速度
```

#### 坡度相關
```java
public static int MMI_G_GRADIENT(byte b1, byte b2)      // 坡度
public static int MMI_G_GRADIENT_CURR(byte b1, byte b2) // 當前坡度
```

#### 其他變數
```java
public static int MMI_I_FAILURE_NUMBER(byte b1, byte b2) // 故障編號
public static int MMI_I_TEXT(byte b)                     // 文字代碼
public static int MMI_I_UNIT(int i)                      // 單位
public static int MMI_L_PACKET(byte b1, byte b2)         // 封包長度
```

#### 資料轉換
- 使用`Byte2Number.getUnsigned()` 進行無符號轉換
- 使用`Byte2Number.getSigned()` 進行有符號轉換
- 處理Big-Endian或Little-Endian位元組順序

### 5. LogDataCollector.java - 日誌資料收集器
收集並整理解碼後的日誌資料

#### 主要屬性
```java
private RecordHandler _$22276;  // 記錄處理器1
private RecordHandler _$22277;  // 記錄處理器2
private RecordHandler _$22278;  // 記錄處理器3
private RecordHandler _$22270;  // 記錄處理器4
private RecordHandler _$22268;  // 司機員資料
private RecordHandler _$22275;  // 司機員訊息
private RecordHandler _$22272;  // 動態資料
private RecordHandler _$22274;  // 故障資料
private RecordHandler _$22273;  // 狀態資料
private RecordHandler _$22271;  // TS資料
private RecordHandler _$22269;  // 列車資料
private String _$22279;         // 檔案路徑
```

#### 建構子
```java
public LogDataCollector(String path) throws Exception {
    setPath(path);
}
```

#### 核心功能

**1. 資料驗證**
```java
private Vector _$22285(String path) throws Exception
```
驗證檔案路徑中的任務資訊是否與日誌內容一致：
- 比對司機員ID
- 比對工作班次編號
- 比對列車運行編號
- 返回驗證結果和錯誤訊息

驗證邏輯：
```java
if (!driverIDFromPath.equals(driverIDFromLog)) {
    valid = false;
    errorMsg += "Driver ID in path is different from Driver ID in log.";
}
```

**2. 資料收集**
- 從Decoder取得解碼資料
- 使用RecordHandler整理資料
- 分類存入不同的記錄器

**3. 記憶體管理**
```java
public void clear() {
    this._$22268.clear();
    this._$22268.trimToSize();
    this._$22269.clear();
    this._$22269.trimToSize();
    // ... 清理所有記錄器
}
```
- 清除所有資料
- 釋放記憶體空間
- 避免記憶體洩漏

### 6. DecodeTS.java - TS解碼器
處理時間序列（Time Series）或列車速度（Train Speed）資料的解碼

#### 功能推測
- 解碼速度時間序列資料
- 處理位置時間序列資料
- 可能包含軌跡重建功能

### 7. RU.java - Recording Unit
記錄單元，可能處理RU設備的資料

#### 功能推測
- RU設備資料格式定義
- RU封包解析
- RU資料記錄

### 8. Application1.java & Frame1.java
應用程式和GUI框架類別

#### 功能推測
- 提供解碼工具的GUI介面
- 檔案選擇和載入
- 解碼進度顯示
- 結果展示

## 解碼流程

### 完整解碼流程
```
原始檔案 (Binary)
    ↓
FileRead (讀取位元組)
    ↓
Decoder (主解碼器)
    ├→ DecodeATP (ATP封包)
    │     ↓
    │  PacketMMI (封包處理)
    │     ↓
    │  MMIVariables (變數轉換)
    │
    └→ DecodeTS (TS資料)
    ↓
LogDataCollector (資料收集)
    ↓
RecordHandler (記錄處理)
    ↓
結構化資料 (Vector)
```

### 資料分類
解碼後的資料被分類為：
1. **司機員資料**: 司機員ID、工作班次、操作記錄
2. **列車資料**: 列車編號、車型、配置
3. **動態資料**: 速度、位置、加速度
4. **狀態資料**: ATP模式、系統狀態
5. **故障資料**: 故障類型、時間、位置
6. **司機員訊息**: 顯示訊息、警告資訊
7. **TS資料**: 時間序列資料
8. **錯誤資料**: 解碼錯誤記錄

## 與其他資料夾的關聯

### 依賴關係
1. **Tools_re**: 
   - 使用Byte2Number進行位元組轉換
   - 使用FileRead讀取檔案
   - 使用HexCode進行十六進位轉換
   - 使用RecordHandler處理記錄
   - 使用PathHandler解析路徑

2. **無外部依賴**: 
   - 專注於資料解碼
   - 不依賴資料庫或網路

### 被依賴關係
1. **decoder_re**: 使用decode_re的解碼結果
2. **core_re**: ATPMissionDetail等類別需要解碼後的資料
3. **drawGraphics_re**: 繪圖需要解碼後的資料
4. **ui_re**: GUI顯示解碼結果

## 技術特點

### 1. 封包導向設計
```java
public static final int MMI_DYNAMIC = 1;
public static final int MMI_STATUS = 2;
// ... 所有封包類型都有對應的常數
```
- 清晰的封包類型定義
- 便於維護和擴充

### 2. 位元組級操作
```java
byte[] driverID = {data[4], data[5], data[6], data[7], 
                   data[8], data[9], data[10], data[11]};
```
- 精確控制位元組提取
- 處理原始二進位資料

### 3. 資料轉換層次
```
原始位元組 → MMIVariables → 基本類型 → Vector → 業務物件
```
- 清晰的轉換層次
- 每層有明確職責

### 4. 延遲處理
- Decoder只負責初步分類
- LogDataCollector負責詳細整理
- 避免一次性處理大量資料

### 5. 資料驗證
```java
private Vector _$22285(String path)
```
- 驗證檔案路徑與內容一致性
- 及早發現資料錯誤
- 提供詳細錯誤訊息

## MMI通訊協定

### 封包結構（推測）
```
[Header][Packet Number][Length][Data...][Checksum?]
  0-3        4            5-6     7-...      ...
```

### 通訊流程
```
ATP System ←→ MMI System
    ↓              ↓
封包0-19      封包100-120
    ↓              ↓
記錄在RU設備中
    ↓
檔案傳輸到MW
    ↓
decode_re解碼
```

### 重要封包解釋

**MMI_DYNAMIC (1)**
- 最重要的動態資料封包
- 包含速度、位置、加速度
- 高頻率傳送（可能每秒或更頻繁）

**MMI_STATUS (2)**
- ATP系統狀態
- 模式切換記錄
- 系統健康狀態

**MMI_CURRENT_DRIVER_DATA (14)**
- 司機員身份資訊
- 工作班次資訊
- 登入時傳送

**MMI_DRIVER_MESSAGE (8)**
- 顯示給司機員的訊息
- 警告、提示資訊
- 需要確認的訊息

**MMI_FAILURE_REPORT_ATP (9)**
- ATP系統故障報告
- 故障類型和編號
- 故障時間和位置

## 效能考量

### 優點
1. **分類儲存**: 不同類型資料分開存放，查詢快速
2. **Vector使用**: 動態陣列，適合不確定大小的資料
3. **記憶體管理**: 提供clear()方法釋放資源

### 可改進之處
1. **重複解碼**: 如果多次存取相同資料，可能重複解碼
2. **記憶體使用**: 所有資料保存在記憶體中，大檔案可能有問題
3. **無串流處理**: 不支援串流解碼，必須載入整個檔案

## 程式碼品質

### 優點
1. **清晰的職責劃分**: 每個類別有明確職責
2. **常數定義完整**: 所有封包類型都有對應常數
3. **例外處理**: 適當的throws Exception

### 可改進之處
1. **變數命名**: 大量混淆的變數名稱
   ```java
   private Vector _$25799;  // 應該命名為 atpPackets
   private Vector _$22268;  // 應該命名為 driverDataLog
   ```
2. **註解缺乏**: 幾乎沒有說明封包格式的註解
3. **魔術數字**: 位元組陣列索引硬編碼
   ```java
   byte[] driverID = {data[4], data[5], ...};  // 應該定義常數 DRIVER_ID_OFFSET = 4
   ```

## 建議改進方向

### 短期改進
1. 增加封包格式的詳細註解
2. 使用有意義的變數名稱
3. 定義位元組偏移量常數

### 中期改進
1. 實作串流解碼，支援大檔案
2. 加入單元測試，驗證解碼正確性
3. 使用enum定義封包類型
4. 考慮使用ByteBuffer替代byte[]

### 長期改進
1. 引入Protobuf或類似的序列化框架
2. 實作解碼快取機制
3. 支援並行解碼
4. 使用現代集合類別（List取代Vector）

## 結論

`decode_re` 資料夾是ATP系統資料處理的第一關，負責將原始二進位資料轉換成可用的結構化資料：

### 核心價值
1. **協定解析**: 實作完整的MMI通訊協定解析
2. **資料分類**: 將混合資料分類為不同類型
3. **格式轉換**: 位元組到業務物件的轉換
4. **資料驗證**: 確保資料完整性和一致性

### 關鍵特色
- **完整的封包支援**: 涵蓋所有MMI封包類型（40+種）
- **清晰的解碼流程**: Decoder → PacketMMI → MMIVariables → Data
- **資料完整性檢查**: LogDataCollector驗證資料一致性
- **靈活的資料存取**: 提供多種方式存取不同類型的資料

這個資料夾雖然只有9個檔案，但實現了複雜的通訊協定解析功能，是整個ATP日誌分析系統的基礎。程式碼的主要問題是可讀性，建議在保持功能的前提下，優先改善命名和註解。
