# connect_re 資料夾分析報告

## 概述
`connect_re` 資料夾是ATP系統的連線層，包含2個核心Java檔案，負責處理所有外部連線相關的功能，包括資料庫連線和FTP伺服器連線。這個資料夾是系統與外部資源溝通的橋樑。

## 資料夾結構

### 檔案清單
1. **ConnectDB.java** - 資料庫連線管理
2. **ConnectFTP.java** - FTP伺服器連線管理

## 詳細檔案分析

### 1. ConnectDB.java
資料庫連線和操作的核心類別

#### 主要功能
- **資料庫連線管理**
  - 建立新連線: `getNewConn()`
  - 交易連線: `getTransactionConn()`
  - 連線池管理

- **CRUD操作**
  - **Create (新增)**:
    - `Insert(String sql)`: 執行SQL插入語句
    - `Insert(Vector data)`: 插入車站資料
    - `InsertLogUploadLibrary()`: 記錄上傳日誌
    - `InsertLogBackupLibrary()`: 記錄備份日誌
  
  - **Read (查詢)**:
    - `getData(String sql)`: 執行SQL查詢，返回Vector結果
    - `getLogBackupSaveDate()`: 查詢備份日期
    - 各種特定查詢方法

  - **Update (更新)**:
    - `UpdateMission()`: 更新任務狀態
    - `UpdateMission_Backup()`: 更新備份狀態
  
  - **Delete (刪除)**:
    - `Delete(String sql)`: 執行SQL刪除語句
    - `Delete(Vector stations)`: 批次刪除車站
    - `DeleteLog(ATPMission mission)`: 刪除任務記錄

#### 特色功能

**1. 批次處理**
```java
CallableStatement callableStatement = paramConnection.prepareCall("{call sp_DeleteStation(?)}");
for (byte b1 = 0; b1 < paramVector.size(); b1++) {
    callableStatement.setString(1, paramVector.get(b1));
    callableStatement.addBatch();
}
int[] arrayOfInt = callableStatement.executeBatch();
```
- 使用PreparedStatement批次執行
- 提高資料庫操作效能
- 返回執行結果統計

**2. 預存程序調用**
- `sp_DeleteStation`: 刪除車站
- `sp_InsertStation`: 新增車站
- `sp_DeleteRecordLog`: 刪除記錄日誌
- 利用資料庫預存程序提升效能和安全性

**3. 交易管理**
```java
Connection connection = getTransactionConn();
// 執行操作
connection.commit();
connection.close();
```
- 支援ACID交易
- 確保資料一致性

**4. 日期格式處理**
```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
```
- 統一日期時間格式
- 確保資料庫相容性

#### 檢查模式常數
```java
public static final int checkUpload = 0;  // 檢查上傳
public static final int checkDecode = 1;  // 檢查解碼
public static final int checkBackup = 2;  // 檢查備份
```

#### 關鍵方法
- `getNewConn()`: 建立新的資料庫連線
- `getData(String sql)`: 執行查詢並返回結果集
- `Insert/Update/Delete`: 標準CRUD操作
- `InsertLogUploadLibrary()`: 記錄檔案傳輸日誌
- `InsertLogBackupLibrary()`: 記錄檔案備份日誌

#### 技術特點
- 使用JDBC進行資料庫操作
- 支援Connection、Statement、PreparedStatement、CallableStatement
- 完善的例外處理機制
- 資源自動關閉（連線、語句）

### 2. ConnectFTP.java
FTP伺服器連線和檔案傳輸管理類別

#### 主要功能

**1. FTP連線管理**
```java
public boolean ftpconnect() throws Exception {
    this.ftp = new ATPFTP(_$2707, _$2710, _$2708, _$2709, "");
    this.ftp.connect(_$2707, _$2710);
    if (!this.ftp.login(_$2708, _$2709))
        throw new Exception("使用者名稱/密碼錯誤.");
    this.ftp.setFileType(2);  // Binary模式
    return true;
}
```
- 建立FTP連線
- 使用者身份驗證
- 設定傳輸模式（Binary）
- 錯誤處理與訊息回報

**2. 建構子多載**
```java
// 使用自訂參數
public ConnectFTP(String host, int port, String user, String pwd, String path)

// 從InitParameters讀取設定
public ConnectFTP() throws Exception {
    _$2707 = InitParameters.FTPHostIP;
    _$2710 = InitParameters.FTPPort;
    _$2708 = InitParameters.FTPUserName;
    _$2709 = InitParameters.FTPUserPWD;
    _$2711 = InitParameters.FTPLogPath;
}
```
- 支援自訂和預設兩種初始化方式
- 彈性的設定管理

**3. 檔案操作**
- **上傳功能**: 將本地檔案上傳至FTP伺服器
- **下載功能**: 從FTP伺服器下載檔案到本地
- **目錄列表**: `getATPDirList()` 取得FTP目錄結構
- **檔案管理**: 檔案刪除、重新命名等操作

**4. 目錄管理**
```java
public Vector getATPDirList() throws Exception {
    this.ftp.changeWorkingDirectory(_$2711);
    BufferedInputStream bufferedInputStream = 
        new BufferedInputStream(this.ftp.retrieveFileStream("ftpFileList.log"));
    // 處理目錄列表
}
```
- 切換工作目錄
- 讀取目錄列表檔案
- 解析目錄結構

**5. 連線控制**
```java
public boolean closeServer() {
    try {
        if (this.ftp.isConnected())
            this.ftp.disconnect();
        return true;
    } catch (Exception exception) {
        exception.printStackTrace();
        return false;
    }
}
```
- 安全關閉FTP連線
- 檢查連線狀態
- 例外處理

**6. 檔案壓縮支援**
- 使用ATPZIP進行壓縮
- 使用ATPUnZIP進行解壓縮
- 減少傳輸檔案大小

#### 靜態設定參數
```java
private static String _$2707 = "";      // FTP主機IP
private static String _$2711;           // FTP路徑
private static int _$2710;              // FTP埠號
private static String _$2708 = "";      // 使用者名稱
private static String _$2709 = "";      // 密碼
private static final int _$2712 = 58;   // 常數（用途待確認）
private static final int _$2714 = 44;   // 常數（用途待確認）
private static final int _$2713 = 29;   // 常數（用途待確認）
```

#### 輔助方法
```java
private boolean _$2779(File paramFile) {
    if (paramFile.isDirectory()) {
        File[] arrayOfFile = paramFile.listFiles();
        for (byte b = 0; b < arrayOfFile.length; b++)
            _$2779(arrayOfFile[b]);
    }
    System.out.print("deleting: " + paramFile.toString());
    System.out.println(" - " + paramFile.delete());
    return true;
}
```
- 遞迴刪除目錄和檔案
- 提供刪除進度輸出

#### ATPFTP物件
```java
ATPFTP ftp;
```
- 使用自訂的ATPFTP類別
- 封裝FTP操作細節
- 提供ATP系統專用的FTP功能

## 與其他資料夾的關聯

### 依賴關係
1. **core_re**: 使用ATPMission等核心資料模型
2. **Tools_re**: 使用InitParameters取得設定參數
3. **外部函式庫**: 
   - ATPFTP (FTP客戶端)
   - ATPZIP/ATPUnZIP (壓縮工具)
   - JDBC驅動程式

### 被依賴關係
1. **Tools_re**: 
   - BackupHandler使用ConnectDB查詢備份狀態
   - CopyTask使用ConnectDB記錄傳輸日誌
   - CheckUser使用ConnectDB驗證使用者
   - FtpBuffer使用ConnectFTP進行檔案傳輸

2. **ui_re**: GUI介面需要透過這層存取資料庫和FTP

3. **decoder_re**: 解碼後的資料需要寫入資料庫

4. **所有資料夾**: 幾乎所有模組都需要資料庫連線

## 技術特點

### 1. 連線管理
- **資料庫連線池**: 雖然程式碼未直接顯示，但getNewConn()暗示可能有連線池機制
- **FTP連線重用**: 透過物件保持FTP連線狀態
- **自動資源釋放**: 使用try-catch-finally確保資源關閉

### 2. 錯誤處理
```java
try {
    // FTP操作
} catch (Exception exception) {
    exception.printStackTrace();
    throw new Exception("資訊中心FTP連線失敗\n訊息: " + exception.getMessage());
}
```
- 捕獲並重新包裝例外
- 提供中文錯誤訊息
- 堆疊追蹤記錄

### 3. 資料格式
- 使用Vector作為資料容器（較舊的Java集合）
- SimpleDateFormat統一日期格式
- 支援多種資料類型（String, Date, Float等）

### 4. SQL操作
- 直接SQL語句執行
- 預存程序調用
- 參數化查詢（防止SQL注入）
- 批次處理提升效能

### 5. 傳輸最佳化
- Binary模式傳輸
- 檔案壓縮
- 批次傳輸支援

## 系統角色

`connect_re` 資料夾在ATP系統中扮演**資料存取層（Data Access Layer）**的角色：

### 職責
1. **資料持久化**: 將業務資料儲存到資料庫
2. **資料檢索**: 從資料庫讀取資料供系統使用
3. **檔案傳輸**: 與遠端FTP伺服器交換檔案
4. **連線抽象**: 隱藏底層連線細節，提供簡單的API

### 設計理念
- **關注點分離**: 將資料存取邏輯與業務邏輯分離
- **可重用性**: 提供通用的連線管理介面
- **錯誤隔離**: 統一處理連線相關錯誤

## 安全性考量

### 優點
1. **參數化查詢**: 使用PreparedStatement防止SQL注入
2. **密碼保護**: FTP密碼不直接暴露在程式碼中
3. **連線驗證**: FTP連線需要帳號密碼驗證

### 可改進之處
1. **密碼明文**: FTP密碼以明文形式儲存在記憶體中
2. **連線加密**: 未使用SFTP或FTPS加密傳輸
3. **SQL注入風險**: 部分直接字串拼接SQL語句的地方存在風險
   ```java
   String str = "SELECT User_ID, UserName, Password, Priority FROM MWUser WHERE User_ID ='" + this._$524 + "'";
   ```

## 效能考量

### 優點
1. **批次處理**: 使用executeBatch()提升批次操作效能
2. **預存程序**: 減少網路往返次數
3. **Binary傳輸**: FTP使用二進位模式，提升傳輸速度
4. **檔案壓縮**: 減少網路傳輸量

### 可改進之處
1. **連線池**: 未見明確的連線池實作（可能在getNewConn()中）
2. **緩存機制**: 未見查詢結果緩存
3. **非同步傳輸**: FTP傳輸為同步操作，可能阻塞

## 程式碼品質

### 優點
1. **例外處理**: 完整的try-catch錯誤處理
2. **資源管理**: 正確關閉連線和語句
3. **常數定義**: 使用final常數增加可讀性

### 可改進之處
1. **變數命名**: 大量使用混淆的變數名稱（如`_$2707`）
2. **程式碼註解**: 缺乏必要的註解說明
3. **硬編碼**: 
   ```java
   File file = new File("C:\\ATPMW\\FTPLSIT.log");
   ```
   硬編碼的路徑降低跨平台相容性

## 建議改進方向

### 短期改進
1. 增加程式碼註解，說明關鍵方法用途
2. 使用有意義的變數名稱
3. 將硬編碼路徑移到設定檔

### 中期改進
1. 實作連線池機制
2. 加入查詢結果緩存
3. 支援SFTP加密傳輸
4. 使用更現代的集合類別（List取代Vector）

### 長期改進
1. 引入ORM框架（如Hibernate或MyBatis）
2. 使用依賴注入管理連線物件
3. 實作非同步FTP傳輸
4. 加入連線監控和日誌記錄

## 結論

`connect_re` 資料夾雖然只有2個檔案，但它們是整個ATP系統的基礎設施，提供了：
- **穩定的資料庫連線**: 支援系統所有資料持久化需求
- **可靠的FTP傳輸**: 實現與資訊中心的檔案交換
- **簡潔的API**: 隱藏複雜的連線細節

這兩個類別的設計相對簡單直接，符合資料存取層的職責定位。雖然有些程式碼可讀性和安全性有待提升，但基本功能完整且運作穩定。在現代化改造時，建議優先考慮安全性（加密傳輸、參數化查詢）和可維護性（變數命名、註解）的改進。
