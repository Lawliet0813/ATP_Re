# Tools_re 資料夾分析報告

## 概述
`Tools_re` 資料夾是ATP系統的工具集合，包含50個檔案，提供系統運作所需的各種工具類別和輔助功能。此資料夾是整個ATP系統的基礎支援層，負責處理檔案操作、資料備份、使用者驗證、任務管理等核心功能。

## 資料夾結構

### 主要檔案 (32個Java檔案)
位於 `Tools_re` 根目錄

### 子資料夾
1. **DataBackup** - 資料備份相關 (2個檔案)
2. **DiskTools** - 磁碟操作工具 (7個檔案)
3. **FileFilter** - 檔案過濾器 (5個檔案)
4. **SortTable** - 表格排序功能 (8個檔案)

## 主要功能分類

### 1. 備份與資料管理
#### BackupHandler.java
- **功能**: 管理日誌檔案的備份流程
- **主要職責**:
  - 過濾需要備份的檔案列表
  - 區分待刪除和待備份的檔案
  - 與資料庫連接查詢備份狀態
  - 將Vector列表轉換為File陣列
- **關鍵方法**:
  - `filterBackupList()`: 根據資料庫記錄過濾檔案
  - `transferToFile()`: 將路徑列表轉換為File物件
  - `getBackupFile()`, `getDeleteFile()`: 取得處理後的檔案陣列

#### BackupDeleteFrame.java
- **功能**: 提供備份刪除的GUI介面
- **特點**: 視覺化管理備份檔案的刪除操作

#### DataBackup/ExportTableToText.java
- **功能**: 將資料庫表格資料匯出為文字檔案
- **用途**: 資料備份和匯出功能

#### DataBackup/TableList.java
- **功能**: 管理需要備份的資料表清單
- **用途**: 定義和管理備份範圍

### 2. 任務管理
#### CopyTask.java
- **功能**: 處理檔案複製任務的核心類別
- **複製模式**:
  - `CopyFromUSBtoHD` (1): 從USB複製到硬碟
  - `CopyFromHDtoMO` (2): 從硬碟複製到MO光碟
  - `CopyFromFTPtoHD` (3): 從FTP下載到硬碟
  - `CopyFromHDtoFTP` (4): 從硬碟上傳到FTP
  - `CopyFromUSBtoAUTO` (5): 從USB自動複製
- **檔案類型識別**:
  - RU檔案 (1)
  - MMI檔案 (2)
  - 兩者都有 (3)
  - ETC其他檔案 (4)
  - 無檔案 (5)
- **主要功能**:
  - 檔案複製與驗證
  - 進度監控
  - 資料庫記錄更新
  - 解碼緩衝區管理
  - MO光碟格式化檢查
- **關鍵方法**:
  - `go()`: 根據複製模式執行對應的複製流程
  - `checkFileType()`: 檢查目錄中的檔案類型
  - `getCurrent()`, `getLengthOfTask()`: 進度追蹤

#### DecodeTask.java
- **功能**: 管理解碼任務的執行與顯示
- **主要職責**:
  - 顯示解碼進度表格
  - 使用JSortTable展示任務狀態
  - 管理多個解碼任務的並行執行
- **GUI元件**:
  - JPanel顯示區域
  - JSortTable顯示任務列表
  - 包含日期、工作班、車次、司機員代號、動力車號碼等欄位
- **關鍵方法**:
  - `go()`: 啟動解碼任務
  - `getMessage()`: 取得顯示面板
  - `getCurrent()`, `getLengthOfTask()`: 進度追蹤

#### FormatTask.java
- **功能**: 處理磁碟格式化任務
- **用途**: MO光碟和其他可移除媒體的格式化

### 3. 使用者驗證與權限管理
#### CheckUser.java
- **功能**: 使用者身份驗證和權限檢查
- **主要職責**:
  - 驗證使用者帳號密碼
  - 管理使用者權限等級 (0-3級)
  - 檢查使用者對特定功能的存取權限
- **權限等級**:
  - Level 0: 最低權限 (11個功能代碼)
  - Level 1: 一般權限 (41個功能代碼)
  - Level 2: 進階權限 (47個功能代碼)
  - Level 3: 最高權限 (68個功能代碼)
- **安全機制**:
  - 使用MD5加密密碼
  - 內建管理員帳號 "MiTACManager"
  - 從資料庫讀取使用者資訊
- **關鍵方法**:
  - `isPasswordRight()`: 驗證密碼正確性
  - `isEnable(int priority, int functionCode)`: 檢查功能權限
  - `getPriority()`: 取得使用者權限等級

#### CheckUserPassword.java
- **功能**: 密碼驗證輔助類別
- **用途**: 提供密碼檢查的額外功能

#### PasswordDialog.java & PasswordInputDialog.java
- **功能**: 密碼輸入對話框
- **用途**: 提供GUI密碼輸入介面

### 4. 資料處理與轉換
#### CheckDataCount.java
- **功能**: 檢查資料筆數
- **用途**: 資料完整性驗證

#### CheckMissionDate.java
- **功能**: 檢查任務日期的有效性
- **用途**: 確保任務日期格式正確且在有效範圍內

#### ConvertTrainRunningNo.java
- **功能**: 轉換列車運行編號
- **用途**: 統一列車編號格式

#### DateTimeTransfer.java
- **功能**: 日期時間轉換工具
- **用途**: 處理不同格式的日期時間轉換

#### WorkDateTransfer.java & WorkDatetimeTransfer.java
- **功能**: 工作日期時間的專用轉換工具
- **用途**: 處理工作班次相關的日期時間格式

#### StringHandler.java
- **功能**: 字串處理工具
- **用途**: 提供字串操作的輔助方法

#### PathHandler.java
- **功能**: 路徑處理工具
- **主要職責**:
  - 編碼和解碼檔案路徑
  - 從路徑中提取任務資訊
  - 路徑格式轉換
- **用途**: 統一管理檔案路徑的編碼格式

### 5. FTP相關功能
#### FtpBuffer.java
- **功能**: FTP傳輸緩衝管理
- **主要職責**:
  - 管理FTP上傳下載的佇列
  - 批次處理FTP傳輸任務
  - 監控傳輸進度
- **用途**: 優化FTP傳輸效能

#### MWFileWatcher.java
- **功能**: 檔案監視器
- **用途**: 監控特定目錄的檔案變化

#### frmFTPWatcher_btn_Adapter.java
- **功能**: FTP監視器按鈕適配器
- **用途**: GUI事件處理

### 6. 檔案操作
#### FileRead.java
- **功能**: 檔案讀取工具
- **用途**: 提供統一的檔案讀取介面

#### RecordHandler.java
- **功能**: 記錄處理器
- **用途**: 管理和處理系統記錄

### 7. GUI相關工具
#### DateRenderer.java & TimeRenderer.java
- **功能**: 日期時間的表格渲染器
- **用途**: 在表格中美化顯示日期時間

#### UnknowProgressMonitor.java
- **功能**: 不確定進度監視器
- **主要職責**:
  - 顯示無法預估時間的進度條
  - 適用於格式化、長時間等待等操作
- **用途**: 提供使用者回饋

#### UpperCaseAndMaxLenghtDocument.java
- **功能**: 文字輸入限制器
- **用途**: 限制文字欄位為大寫且限制長度

### 8. 列車相關功能
#### TrainTypeRunnable.java
- **功能**: 列車類型執行緒
- **用途**: 非同步處理列車類型相關操作

#### WaysideFailureGroup.java
- **功能**: 路側設備故障群組
- **用途**: 管理和分類路側設備故障

### 9. DiskTools 子資料夾 (7個檔案)
處理磁碟和檔案系統操作：

#### CopyDir.java
- **功能**: 目錄複製工具
- **用途**: 遞迴複製整個目錄結構

#### CopyFile.java
- **功能**: 檔案複製工具
- **用途**: 單一檔案複製操作

#### CopySmbDir.java
- **功能**: SMB網路目錄複製
- **用途**: 跨網路的目錄複製

#### DeleteDir.java
- **功能**: 目錄刪除工具
- **用途**: 遞迴刪除目錄及其內容

#### DiskName.java
- **功能**: 磁碟名稱管理
- **主要職責**:
  - 檢查磁碟標籤格式
  - 驗證MO光碟是否超過2年保用期限
- **用途**: MO光碟管理

#### DiskStatus.java
- **功能**: 磁碟狀態檢查
- **主要職責**:
  - 取得磁碟標籤
  - 檢查磁碟可用空間
  - 監控磁碟狀態
- **用途**: 磁碟管理和監控

#### FormatDisk.java
- **功能**: 磁碟格式化工具
- **用途**: 格式化MO光碟等可移除媒體

### 10. FileFilter 子資料夾 (5個檔案)
檔案過濾器集合：

#### FilterRU.java
- **功能**: RU檔案過濾器
- **用途**: 篩選RU類型的日誌檔案

#### FilterMMI.java
- **功能**: MMI檔案過濾器
- **用途**: 篩選MMI類型的日誌檔案

#### FilterDIR.java
- **功能**: 目錄過濾器
- **用途**: 只顯示目錄，過濾檔案

#### FilterVID.java
- **功能**: VID檔案過濾器
- **用途**: 篩選特定車輛ID的檔案

#### FilterATP.java
- **功能**: ATP檔案過濾器
- **用途**: 篩選ATP系統相關檔案

### 11. SortTable 子資料夾 (8個檔案)
可排序表格元件：

#### JSortTable.java
- **功能**: 可排序的JTable擴充類別
- **用途**: 提供點擊欄位標題即可排序的表格

#### SortTableModel.java & DefaultSortTableModel.java
- **功能**: 表格資料模型
- **用途**: 管理可排序表格的資料結構

#### ColumnComparator.java
- **功能**: 欄位比較器
- **用途**: 定義不同資料類型的排序規則

#### SortHeaderRenderer.java
- **功能**: 排序標題渲染器
- **用途**: 顯示排序方向的視覺標示

#### SortArrowIcon.java
- **功能**: 排序箭頭圖示
- **用途**: 繪製上下箭頭表示排序方向

#### MultiLineCellRenderer.java & MultiLineHeaderRenderer.java
- **功能**: 多行文字渲染器
- **用途**: 支援表格儲存格和標題的多行文字顯示

## 與其他資料夾的關聯

### 依賴關係
1. **connect_re**: 使用ConnectDB進行資料庫操作，使用ConnectFTP進行FTP傳輸
2. **core_re**: 使用ATPMission等核心資料結構
3. **decoder_re**: DecodeTask需要將檔案加入解碼緩衝區
4. **ui_re**: 提供GUI元件使用的工具類別

### 被依賴關係
- 幾乎所有其他資料夾都需要使用Tools_re中的工具類別
- 特別是路徑處理、檔案操作、使用者驗證等基礎功能

## 技術特點

### 1. 設計模式
- **Adapter Pattern**: 大量使用Adapter處理GUI事件
- **Singleton Pattern**: 部分工具類別使用靜態方法
- **Observer Pattern**: 進度監控使用觀察者模式

### 2. 執行緒安全
- CopyTask使用Thread處理長時間操作
- 避免阻塞GUI執行緒

### 3. 資料庫整合
- 透過ConnectDB進行資料庫操作
- 使用PreparedStatement和CallableStatement

### 4. 錯誤處理
- 大量使用try-catch處理例外
- 提供使用者友善的錯誤訊息
- JOptionPane顯示錯誤對話框

### 5. 國際化支援
- 使用ATPMessages進行訊息國際化
- 支援多語言切換

## 系統角色
`Tools_re` 資料夾在ATP系統中扮演**基礎工具層**的角色：

1. **檔案管理**: 提供檔案複製、備份、過濾的完整解決方案
2. **使用者管理**: 實現使用者驗證和權限控制
3. **資料處理**: 提供資料轉換和格式化工具
4. **GUI支援**: 提供可重用的GUI元件和工具
5. **任務管理**: 協調複雜的檔案處理流程

## 主要特色

### 優點
1. **模組化設計**: 功能明確分類，易於維護
2. **完整的工具集**: 涵蓋檔案、資料庫、GUI等各方面
3. **進度監控**: 提供良好的使用者體驗
4. **權限管理**: 完善的使用者權限控制機制
5. **多種複製模式**: 支援USB、MO、FTP等多種媒體

### 可改進之處
1. 部分類別使用混淆的變數名稱（如`_$10090`），降低可讀性
2. 硬編碼的路徑（如`C:\\TEMP_ATP_FILE\\`）可能影響跨平台相容性
3. 部分類別職責過重（如CopyTask超過550行）

## 結論
`Tools_re` 是ATP系統的核心支援資料夾，提供了系統運作所需的所有基礎工具和輔助功能。它的設計考慮到了實際應用場景，包括檔案備份、使用者管理、進度監控等關鍵需求。雖然有些程式碼可讀性有待提升，但整體架構合理，功能完整，是系統不可或缺的一部分。
