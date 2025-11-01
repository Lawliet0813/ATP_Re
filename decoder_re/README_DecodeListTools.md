# RU檔與MMI檔解碼列表工具

## 概述

本文件說明如何使用 RU 檔案和 MMI 檔案的解碼列表工具。這些工具實現了 issue 的要求：列出 RU 檔跟 MMI 檔解碼後的數據。

## 工具說明

### 1. RUFileDecodeListTool（RU檔案解碼列表工具）

此工具用於解碼並列出 Recording Unit (RU) 檔案的內容。

#### 功能特點

- 解析 RU 檔案結構
- 顯示封包編號和類型
- 解碼時間戳記
- 顯示位置和速度資訊
- 列出原始封包資料（十六進位格式）
- 識別特殊封包類型（ATP/MMI、BTM、VDX等）

#### 使用方式

```bash
# 編譯
javac decoder_re/RUFileDecodeListTool.java

# 執行
java decoder.RUFileDecodeListTool <RU檔案路徑>
```

#### 範例

```bash
java decoder.RUFileDecodeListTool /path/to/ru/file
```

#### 輸出範例

```
========================================
RU檔案解碼內容
檔案名稱：sample.ru
檔案路徑：/path/to/sample.ru
檔案大小：102400 bytes
解碼時間：Mon Oct 28 08:00:00 UTC 2025
========================================

=== 封包 #1 ===
封包編號：1 (ATP_MMI_CH1)
時間戳記：2025/10/28 08:00:00
位置：1234567 cm (12345.67 m)
速度：2778 cm/s (100.0 km/h)
資料長度：64 bytes
封包內容（前32位元組）：01 19 0A 1C 08 00 00 ...
→ ATP/MMI 通訊封包

...

========================================
解碼完成
總共封包數：128
========================================
```

#### 支援的封包類型

- ATP/MMI 通訊封包 (1, 4)
- BTM 電報封包 (43-47)
- VDX 資料封包 (21-24)
- 狀態資訊封包 (2, 3)
- ATP 下載資料封包 (201)
- 週期性速度距離資料 (211)
- 其他系統狀態封包 (221-228)

### 2. MMIFileDecodeListTool（MMI檔案解碼列表工具）

此工具用於解碼並列出 Man-Machine Interface (MMI) 檔案的內容。

#### 功能特點

- 解析 MMI 檔案結構
- 顯示封包編號和類型（中文說明）
- 解碼動態資料（速度、加速度、位置、煞車目標）
- 解碼司機員訊息、故障報告等
- 列出原始封包資料（十六進位格式）
- 統計各類封包的數量
- **支援目錄模式**：可處理包含多個 MMI 檔案的目錄（符合 issue 要求）

#### 使用方式

```bash
# 編譯
javac decoder_re/MMIFileDecodeListTool.java

# 執行（單一檔案模式）
java decoder.MMIFileDecodeListTool <MMI檔案路徑>

# 執行（目錄模式）- 符合 issue 要求：測試區選一個資料夾，內含2個檔案以上
java decoder.MMIFileDecodeListTool <包含MMI檔案的目錄路徑>
```

#### 範例

```bash
# 單一檔案
java decoder.MMIFileDecodeListTool /path/to/mmi/file

# 目錄模式（issue 要求：處理包含2個以上檔案的資料夾）
java decoder.MMIFileDecodeListTool /path/to/test/directory
```

#### 輸出範例（單一檔案模式）

```
========================================
MMI檔案解碼內容
檔案名稱：sample.mmi
檔案路徑：/path/to/sample.mmi
檔案大小：51200 bytes
解碼時間：Mon Oct 28 08:00:00 UTC 2025
========================================

=== 封包 #1 ===
封包編號：1 (0x01)
封包類型：MMI_DYNAMIC（動態資料）
資料長度：64 bytes
→ 動態資料（速度、加速度、位置、煞車目標）
  速度：2778 cm/s (100.0 km/h)
  加速度：50 cm/s²
  位置：1234567 cm (12345.67 m)
封包內容（前48位元組）：
  01 00 0A DA 00 32 00 12 D6 87 ...

=== 封包 #2 ===
封包編號：8 (0x08)
封包類型：MMI_DRIVER_MESSAGE（司機員訊息）
資料長度：32 bytes
→ 司機員訊息
  訊息ID：42
封包內容（前32位元組）：
  08 00 2A ...

...

========================================
解碼完成
總共封包數：256

封包類型統計：
  [  1] MMI_DYNAMIC（動態資料）: 128 次
  [  2] MMI_STATUS（狀態）: 64 次
  [  8] MMI_DRIVER_MESSAGE（司機員訊息）: 32 次
  [  9] MMI_FAILURE_REPORT_ATP（ATP故障報告）: 16 次
  [102] MMI_STATUS_REPORT（狀態報告）: 16 次
========================================
```

#### 輸出範例（目錄模式）

```
========================================
目錄中的MMI檔案解碼
目錄路徑：/path/to/test/directory
找到檔案總數：5
MMI檔案數：3
解碼時間：Mon Oct 28 08:00:00 UTC 2025
========================================

✓ 符合要求：找到 3 個MMI檔案（>=2個檔案）

╔════════════════════════════════════════
║ MMI檔案 #1 / 3
╚════════════════════════════════════════

========================================
MMI檔案解碼內容
檔案名稱：file1.mmi
...
========================================

╔════════════════════════════════════════
║ MMI檔案 #2 / 3
╚════════════════════════════════════════

========================================
MMI檔案解碼內容
檔案名稱：file2.mmi
...
========================================

╔════════════════════════════════════════
║ MMI檔案 #3 / 3
╚════════════════════════════════════════

========================================
MMI檔案解碼內容
檔案名稱：file3.mmi
...
========================================

╔════════════════════════════════════════
║ 目錄解碼總結
╚════════════════════════════════════════
總共處理MMI檔案數：3
完成時間：Mon Oct 28 08:00:00 UTC 2025
```

#### 支援的 MMI 封包類型

**ATP 發送的封包 (0-19):**
- MMI_START_ATP (0) - 啟動ATP
- MMI_DYNAMIC (1) - 動態資料
- MMI_STATUS (2) - 狀態
- MMI_SET_TIME_ATP (3) - 設定時間
- MMI_TRACK_DESCRIPTION (4) - 軌道描述
- MMI_GEO_POSITION (5) - 地理位置
- MMI_CURRENT_TRAIN_DATA (6) - 目前列車資料
- MMI_FORCED_DRIVER_REQUEST (7) - 強制司機員請求
- MMI_DRIVER_MESSAGE (8) - 司機員訊息
- MMI_FAILURE_REPORT_ATP (9) - ATP故障報告
- MMI_ECHOED_TRAIN_DATA (10) - 回應列車資料
- MMI_CURRENT_SR_RULES (11) - 目前SR規則
- MMI_ECHOED_SR_RULES (12) - 回應SR規則
- MMI_CURRENT_DRIVER_DATA (14) - 目前司機員資料
- MMI_TEST_REQUEST (15) - 測試請求
- MMI_SELECT_STM_REQUEST (16) - 選擇STM請求
- MMI_STM_DATA_TO_CAB_1 (17) - STM資料至駕駛室1
- MMI_STM_DATA_TO_CAB_2 (18) - STM資料至駕駛室2
- MMI_RU_DATA (19) - RU資料

**MMI 發送的封包 (100-120):**
- MMI_START_MMI (100) - 啟動MMI
- MMI_DRIVER_REQUEST (101) - 司機員請求
- MMI_STATUS_REPORT (102) - 狀態報告
- MMI_CONFIRMED_SR_RULES (103) - 確認SR規則
- MMI_NEW_DRIVER_DATA (104) - 新司機員資料
- MMI_NEW_SR_RULES (106) - 新SR規則
- MMI_NEW_TRAIN_DATA (107) - 新列車資料
- MMI_SET_TIME_MMI (109) - 設定時間MMI
- MMI_CONFIRMED_TRAIN_DATA (110) - 確認列車資料
- MMI_DRIVER_MESSAGE_ACK (111) - 司機員訊息確認
- MMI_NEW_RBC_DATA (112) - 新RBC資料
- MMI_FAILURE_REPORT_MMI (113) - MMI故障報告
- MMI_TEST_RESULT (114) - 測試結果
- MMI_FAILURE_REPORT_ACK (115) - 故障報告確認
- MMI_SELECT_STM (116) - 選擇STM
- MMI_STM_DATA_FROM_CAB_1 (117) - 來自駕駛室1的STM資料
- MMI_STM_DATA_FROM_CAB_2 (118) - 來自駕駛室2的STM資料
- MMI_RU_DATA_FROM_CAB_1 (119) - 來自駕駛室1的RU資料
- MMI_RU_DATA_FROM_CAB_2 (120) - 來自駕駛室2的RU資料

## Issue 需求對應

### ✅ RU檔解碼內容
- **實現方式**: RUFileDecodeListTool.java
- **功能**: 解析並列出 RU 檔案的所有封包資訊
- **包含**: 封包類型、時間戳記、位置、速度、原始資料

### ✅ MMI檔解碼內容
- **實現方式**: MMIFileDecodeListTool.java
- **功能**: 解析並列出 MMI 檔案的所有封包資訊
- **包含**: 封包類型、動態資料、訊息內容、統計資訊

### ✅ MMI檔選擇：測試區選一個資料夾，內含2個檔案以上
- **實現方式**: MMIFileDecodeListTool 的目錄模式
- **功能**: 
  - 支援處理整個目錄
  - 自動偵測並處理目錄中的所有 MMI 檔案
  - 檢查是否符合「2個檔案以上」的要求
  - 提供友善的提示訊息
  - 逐一解碼每個檔案並顯示結果

## 程式碼結構

### RUFileDecodeListTool.java

```
package decoder;

主要方法：
- listDecodedData(File ruFile): Vector<String>
  解碼RU檔案並回傳結果列表
  
- printDecodedData(File ruFile): void
  解碼並直接列印到控制台
  
輔助方法：
- getUnsignedByte(byte b): int
  將位元組轉換為無符號整數
  
- bytesToInt(byte[] bytes, int offset, int length): int
  將多個位元組轉換為整數
  
- toHexString(byte[] bytes): String
  將位元組陣列轉換為十六進位字串
  
- getPacketTypeName(int packetNo): String
  根據封包編號取得封包類型名稱
```

### MMIFileDecodeListTool.java

```
package decoder;

主要方法：
- listDecodedData(File mmiFile): Vector<String>
  解碼單一MMI檔案並回傳結果列表
  
- listDecodedDataFromDirectory(File directory): Vector<String>
  解碼目錄中所有MMI檔案並回傳結果列表（符合issue要求）
  
- printDecodedData(File mmiFile): void
  解碼並直接列印單一檔案到控制台
  
- printDecodedDataFromDirectory(File directory): void
  解碼並直接列印目錄中所有檔案到控制台
  
輔助方法：
- decodePacketContent(int packetNo, byte[] packetData, Vector<String> results): void
  根據封包類型解碼封包內容
  
- getEstimatedPacketSize(int packetNo): int
  根據封包編號取得估計的封包大小
  
- getPacketTypeName(int packetNo): String
  根據封包編號取得封包類型名稱（中文）
  
- getUnsignedByte(byte b): int
  將位元組轉換為無符號整數
  
- bytesToInt(byte[] bytes, int offset, int length): int
  將多個位元組轉換為整數
  
- toHexString(byte[] bytes): String
  將位元組陣列轉換為十六進位字串
```

## 技術細節

### RU 檔案格式

```
每個封包結構：
- 標頭 (16 bytes)
  - Byte 0: 封包編號
  - Byte 1-6: 時間戳記 (YY MM DD HH MM SS)
  - Byte 7-10: 位置 (4 bytes, cm)
  - Byte 13-14: 速度 (2 bytes, cm/s)
  - Byte 15: 資料長度
- 資料主體 (可變長度)
```

### MMI 檔案格式

```
每個封包結構：
- Byte 0: 封包編號
- 資料主體 (長度依封包類型而定)

典型封包大小：
- 小型封包 (0, 100): 20 bytes
- 中型封包 (1, 2, 8): 32-64 bytes
- 大型封包 (4): 128 bytes
```

## 限制與注意事項

1. **獨立工具**: 這些工具設計為獨立運作，不依賴其他解碼器類別
2. **簡化解碼**: 使用直接的位元組解析，而非完整的協定解碼器
3. **檔案格式假設**: 基於常見的 RU/MMI 檔案格式，實際格式可能有所差異
4. **十六進位顯示**: 限制顯示前 32-48 位元組，避免輸出過長
5. **目錄模式檔案偵測**: MMI 檔案透過副檔名或檔名偵測，可能需要調整過濾條件

## 除錯建議

如果工具無法正確解碼檔案：

1. 檢查檔案格式是否符合預期
2. 查看十六進位輸出，手動分析資料結構
3. 調整 `getEstimatedPacketSize()` 方法中的封包大小估計
4. 修改檔案偵測邏輯（針對目錄模式）

## 維護與擴展

如需擴展功能：

1. **新增封包類型**: 在 `getPacketTypeName()` 和 `decodePacketContent()` 中新增 case
2. **改善解碼邏輯**: 在 `decodePacketContent()` 中新增更詳細的解析
3. **支援其他檔案格式**: 新增類似的解碼工具類別
4. **整合現有解碼器**: 如果 Tools 類別可用，可替換為使用 RUDecoder 和 ATPDecoder

## 結論

這兩個工具實現了 issue 的所有需求：

- ✅ 列出 RU 檔解碼後的數據
- ✅ 列出 MMI 檔解碼後的數據
- ✅ MMI 檔選擇：測試區選一個資料夾，內含2個檔案以上

工具提供清晰、易讀的輸出格式，包含中文說明，方便使用者理解解碼結果。
