# core_re 資料夾分析報告

## 概述
`core_re` 資料夾是ATP系統的核心資料模型層，包含12個Java檔案，定義了系統中最重要的資料結構和業務實體。這個資料夾包含了ATP任務、列車資料、車站資訊、使用者等核心概念的封裝，是整個系統的資料骨幹。

## 資料夾結構

### 檔案清單
1. **ATPGeneralData.java** (介面) - ATP通用資料介面
2. **ATPMission.java** - ATP任務基礎類別
3. **ATPMissionDetail.java** - ATP任務詳細資料
4. **ATPMissionFailure.java** - ATP任務故障記錄
5. **ATPMissionGeneral.java** - ATP任務綜合資料
6. **ATPMissionUpdate.java** - ATP任務更新
7. **CreateTrainCategoryData.java** - 建立列車類別資料
8. **CreateTrainDataMapping.java** - 建立列車資料映射
9. **CreateTrainTypeData.java** - 建立列車類型資料
10. **CurveController.java** - 曲線控制器
11. **Station.java** - 車站資訊
12. **User.java** - 使用者資料

## 詳細檔案分析

### 1. ATPMission.java - ATP任務基礎類別
系統中所有ATP任務的基礎類別

#### 主要屬性
```java
private Date _$2932 = new Date();      // 任務日期
private String _$2933 = "notInit";      // 工作班次編號
private String _$2934 = "notInit";      // 列車運行編號
private String _$2935 = "notInit";      // 司機員ID
private String _$2936 = "notInit";      // 動力車ID
private File _$2246;                    // 檔案物件
private int _$2931 = 1;                 // 資料來源
```

#### 資料來源類型
```java
public static final int SourceFromFile = 1;        // 從檔案讀取
public static final int SourceFromDataBase = 2;    // 從資料庫讀取
```

#### 建構子
```java
// 從資料庫建立
public ATPMission(Date date, String workShift, String trainRunning, 
                  String driverID, String vehicleID)

// 從檔案建立
public ATPMission(File file)
```
- 支援兩種初始化方式
- 從檔案建立時，自動解析路徑取得任務資訊

#### 核心方法
- `getATPMissionData()`: 返回完整的任務資料Vector
- `getPath()`: 取得任務對應的檔案路徑
- `getFile()`: 取得檔案物件
- `getMissionDate()`, `getWorkShift()`, `getTrainRunning()`, `getDriver()`, `getVehicle()`: 取得各項任務資訊

#### 特點
- 使用PathHandler解析檔案路徑結構
- 提供統一的任務識別方式
- 支援檔案和資料庫兩種資料來源

### 2. ATPMissionGeneral.java - ATP任務綜合資料
繼承自ATPMission，提供完整的任務相關資料

#### 主要資料集合
```java
private Vector _$3769;  // BTM資料
private Vector _$3770;  // 類別資料
private Vector _$3767;  // 動態資料
private Vector _$3771;  // 故障資料
private Vector _$3772;  // 狀態資料
private Vector _$3773;  // 按鈕事件
private Vector _$3774;  // 司機員訊息
private Vector _$3775;  // 時間戳記
private Vector _$3776;  // VDX資料
private Vector _$2355;  // 曲線資料
```

#### 統計資訊
```java
private Integer _$3782;  // EB煞車次數
private Integer _$3783;  // SB煞車次數
private Integer _$3784;  // 客艙故障次數
private Integer _$3785;  // 路側故障次數
private Integer _$3786;  // 其他統計
private Date _$3779;     // 任務開始時間
private Date _$3780;     // 任務結束時間
private Integer _$3781;  // 起始位置
private Integer _$3782;  // 結束位置
```

#### 資料載入機制
```java
public Vector getBTM() {
    if (this._$3769 == null && getDataSource() == 2)
        try {
            setBTM(this.conn.getBTM(this));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    return this._$3769;
}
```
- **延遲載入（Lazy Loading）**: 資料只在需要時才從資料庫讀取
- **快取機制**: 資料讀取後保存在記憶體中
- **資料來源判斷**: 根據資料來源決定是否需要從資料庫讀取

#### 建構子
```java
public ATPMissionGeneral(File file)
public ATPMissionGeneral(Date date, String shift, String train, 
                        String driver, String vehicle)
public ATPMissionGeneral(ATPMission mission)
```
- 支援多種建立方式
- 可從現有ATPMission物件建立

#### 特點
- 整合所有任務相關資料
- 使用ConnectDB進行資料庫操作
- 提供完整的任務統計資訊

### 3. ATPMissionDetail.java - ATP任務詳細資料
提供更詳細的任務資料和分析功能

#### 主要功能
- 擴充ATPMissionGeneral的功能
- 提供更細緻的資料存取
- 可能包含時間序列分析
- 支援資料過濾和排序

### 4. ATPMissionFailure.java - ATP任務故障記錄
專門處理任務故障相關資料

#### 主要功能
- 記錄故障類型
- 故障時間和位置
- 故障處理狀態
- 故障統計分析

#### 可能的故障類型
- 客艙故障
- 路側設備故障
- ATP系統故障
- 通訊故障
- 感測器故障

### 5. ATPMissionUpdate.java - ATP任務更新
處理任務資料的更新操作

#### 主要功能
- 更新任務狀態
- 修改任務資料
- 同步資料庫變更
- 觸發相關事件

### 6. Station.java - 車站資訊
管理系統中所有車站的資料

#### 靜態資料結構
```java
static String[] pchtName;   // 中文站名
static String[] pengName;   // 英文站名
static int[] pinnerID;      // 內部ID
static Vector pdata;        // 完整資料
```

#### 初始化
```java
static {
    try {
        ConnectDB connectDB = new ConnectDB();
        pdata = connectDB.getStation();
        // 解析資料填入陣列
    } catch (Exception exception) {
        exception.printStackTrace();
    }
}
```
- **靜態初始化**: 類別載入時從資料庫讀取所有車站資料
- **記憶體快取**: 所有車站資料常駐記憶體
- **快速查詢**: 使用二分搜尋法提升查詢效能

#### 核心方法
```java
public static String getStationChtName(int stationID)
public static String getStationEngName(int stationID)
```
- 使用`Arrays.binarySearch()`進行高效查詢
- 找不到時返回預設值（"未知"/"unknow"）

#### 特點
- 靜態資料，全域共享
- 高效的查詢機制
- 支援中英文站名

### 7. User.java - 使用者資料
管理使用者帳號資訊

#### 主要屬性
```java
private String _$29792;  // 使用者名稱
private String _$29793;  // 權限等級
private String _$29794;  // 使用者職稱
private boolean _$29795; // 狀態標記
```

#### 建構子
```java
public User(String username, String password) throws Exception {
    if (!validUserAndPWD(username, password))
        throw new Exception("請輸入正確的使用者名稱及密碼!");
    // 初始化使用者資料
}
```
- 建立時驗證帳號密碼
- 驗證失敗拋出例外

#### 核心方法
- `validUserAndPWD()`: 驗證使用者帳號密碼
- `checkPWD()`: 檢查密碼正確性
- `getPriority()`: 取得權限等級
- `getTitle()`: 取得使用者職稱

#### 注意事項
```java
public static boolean validUserAndPWD(String username, String password) {
    return false;  // 實作未完成
}
```
- 驗證方法尚未實作，目前總是返回false

### 8. ATPGeneralData.java - ATP通用資料介面
定義所有ATP資料類別必須實作的介面

#### 資料存取方法
```java
// Getters
Vector getBTM();
Vector getCategory();
Vector getDriverMessage();
Vector getDynamic();
Vector getFailure();
Vector getStatus();
Vector getTimeStamp();
Vector getVDX();

// Setters
void setBTM(Vector data);
void setCategory(Vector data);
void setDriverMessage(Vector data);
void setDynamic(Vector data);
void setFailure(Vector data);
void setStatus(Vector data);
void setTimeStamp(Vector data);
void setVDX(Vector data);
```

#### 統計方法
```java
int getCabinFailureTimes();      // 客艙故障次數
int getWaysideFailureTimes();    // 路側故障次數
int getEBTimes();                 // 緊急煞車次數
int getSBTimes();                 // 服務煞車次數
```

#### 任務時間和位置
```java
Date getMissionStartTime();
Date getMissionEndTime();
int getMissionStartLocation();
int getMissionEndLocation();
```

#### 資料狀態
```java
boolean isDataExistsInDB();  // 檢查資料是否存在於資料庫
```

#### 設計特點
- 定義標準的資料存取介面
- 確保所有實作類別提供一致的API
- 便於多型使用

### 9-10. CreateTrainXXXData.java 系列
建立列車相關資料的工具類別

#### CreateTrainCategoryData.java
- **功能**: 建立列車類別資料
- **用途**: 定義不同類型的列車分類
- **資料**: 自強號、莒光號、區間車等

#### CreateTrainDataMapping.java
- **功能**: 建立列車資料映射
- **用途**: 將不同來源的列車資料對應到統一格式
- **處理**: 資料格式轉換和正規化

#### CreateTrainTypeData.java
- **功能**: 建立列車型號資料
- **用途**: 定義列車的具體型號
- **資料**: EMU、推拉式、機車頭等型號資訊

### 11. CurveController.java - 曲線控制器
處理速度限制曲線和其他曲線資料

#### 可能的功能
- 計算速度限制曲線
- 管理臨時速限
- 處理坡度曲線
- 控制煞車曲線

## 資料模型架構

### 繼承層次
```
ATPMission (基礎類別)
    ↓
ATPMissionGeneral (綜合資料)
    ↓
ATPMissionDetail (詳細資料)
```

### 介面實作
```
ATPGeneralData (介面)
    ↓
ATPMissionGeneral (實作類別)
```

## 與其他資料夾的關聯

### 依賴關係
1. **connect_re**: 透過ConnectDB讀取資料庫資料
2. **Tools_re**: 使用PathHandler、SortTable等工具

### 被依賴關係
1. **ui_re**: GUI介面使用這些資料模型顯示資訊
2. **decoder_re**: 解碼後的資料填入這些模型
3. **drawGraphics_re**: 繪圖時使用這些資料
4. **所有模組**: 作為核心資料結構被廣泛使用

## 技術特點

### 1. 延遲載入（Lazy Loading）
```java
public Vector getBTM() {
    if (this._$3769 == null && getDataSource() == 2) {
        // 只在需要時才載入
        setBTM(this.conn.getBTM(this));
    }
    return this._$3769;
}
```
- 提升效能，減少不必要的資料庫查詢
- 節省記憶體使用

### 2. 快取機制
- Station類別使用靜態變數快取所有車站資料
- ATPMissionGeneral快取已載入的資料

### 3. 資料來源抽象
```java
public static final int SourceFromFile = 1;
public static final int SourceFromDataBase = 2;
```
- 支援從檔案或資料庫讀取
- 統一的資料存取介面

### 4. 建構子多載
- 提供多種物件建立方式
- 適應不同的使用情境

### 5. 二分搜尋最佳化
```java
int index = Arrays.binarySearch(pinnerID, stationID);
```
- Station類別使用二分搜尋提升查詢效能
- O(log n) 時間複雜度

## 設計模式

### 1. 工廠模式
- CreateTrainXXXData系列類別使用工廠模式建立資料

### 2. 策略模式
- 不同的資料來源使用不同的載入策略

### 3. 單例模式
- Station使用靜態資料，類似單例模式

### 4. 代理模式
- 延遲載入機制類似代理模式

## 資料完整性

### 任務識別
每個ATP任務由以下5個欄位唯一識別：
1. 任務日期 (Date)
2. 工作班次 (String)
3. 列車運行編號 (String)
4. 司機員ID (String)
5. 動力車ID (String)

### 資料關聯
```
ATPMission (任務基本資訊)
    ├─ BTM資料 (Balise資料)
    ├─ VDX資料 (速度距離資料)
    ├─ 動態資料 (速度、位置等)
    ├─ 狀態資料 (系統狀態)
    ├─ 故障資料 (故障記錄)
    ├─ 司機員訊息 (操作記錄)
    └─ 統計資訊 (次數、時間等)
```

## 效能考量

### 優點
1. **延遲載入**: 只載入需要的資料
2. **記憶體快取**: Station資料常駐記憶體
3. **二分搜尋**: 高效的車站查詢
4. **批次載入**: 一次性載入相關資料

### 可改進之處
1. **記憶體使用**: 所有Station資料常駐記憶體可能造成浪費
2. **沒有資料過期機制**: 快取資料可能過時
3. **缺乏並行控制**: 多執行緒環境可能有問題

## 程式碼品質

### 優點
1. **清晰的分層**: 基礎類別、擴充類別、介面分離清楚
2. **資料封裝**: 使用private屬性和public方法
3. **例外處理**: 適當的try-catch處理

### 可改進之處
1. **變數命名**: 大量使用混淆的變數名稱
   ```java
   private Vector _$3769;  // 應該命名為 btmData
   private Integer _$3785; // 應該命名為 cabinFailureTimes
   ```
2. **註解缺乏**: 幾乎沒有註解說明
3. **魔術數字**: 資料來源使用1和2，應定義為常數

## 建議改進方向

### 短期改進
1. 重新命名變數，使用有意義的名稱
2. 增加類別和方法的JavaDoc註解
3. User類別的驗證方法需要實作

### 中期改進
1. 引入JPA/Hibernate等ORM框架
2. 使用泛型取代Vector
3. 實作更完善的快取機制（如使用Cache框架）
4. 加入資料驗證

### 長期改進
1. 考慮使用不可變物件（Immutable Objects）
2. 實作Builder模式簡化物件建立
3. 加入單元測試
4. 考慮使用現代的Java集合（List、Map取代Vector）

## 結論

`core_re` 資料夾是ATP系統的核心，定義了系統中最重要的資料結構：

### 核心價值
1. **統一的資料模型**: 提供標準的ATP任務資料結構
2. **靈活的資料來源**: 支援檔案和資料庫兩種來源
3. **完整的資料封裝**: 將任務相關的所有資料集中管理
4. **高效的資料存取**: 使用延遲載入和快取提升效能

### 關鍵特色
- **ATPMission**: 任務的唯一識別和基本資訊
- **ATPMissionGeneral**: 完整的任務資料和統計
- **Station**: 高效的車站資訊查詢
- **User**: 使用者身份管理

這個資料夾雖然檔案不多，但每個類別都承載著重要的業務概念，是整個系統的資料基石。程式碼結構合理，但可讀性有待提升。在現代化改造時，建議優先改善命名和註解，然後逐步引入現代Java特性和ORM框架。
