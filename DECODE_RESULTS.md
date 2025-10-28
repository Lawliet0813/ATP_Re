# 解碼實際RU與MMI檔 - 結果報告
# Decode Real RU and MMI Files - Results Report

## 概述 (Overview)

This report documents the decoding results of real RU and MMI files from the ATP system.

## 檔案清單 (Files Decoded)

### 1. RU File (Recording Unit)
- **檔案路徑 (File Path)**: `tests/RU_file/024423.RU`
- **檔案大小 (File Size)**: 2,038 bytes
- **解碼封包數 (Packets Decoded)**: 56 packets
- **錯誤數 (Errors)**: 0

#### 封包類型統計 (Packet Type Statistics):
| 封包類型 (Type) | 數量 (Count) | 說明 (Description) |
|----------------|--------------|-------------------|
| Type 1         | 2           | ATP/MMI packets   |
| Type 31        | 7           | Unknown type 31   |
| Type 42        | 6           | BTM Status        |
| Type 43-47     | 4 each      | BTM Telegram fragments (5 fragments per complete telegram) |
| Type 62-64     | 4 each      | Unknown types     |
| Type 91        | 2           | Unknown type 91   |
| Type 211       | 7           | Unknown type 211  |

### 2. MMI File 1 (Man-Machine Interface)
- **檔案路徑 (File Path)**: `tests/MMI_file/00000267_001170--_775495--_-9042/114011.MMI`
- **檔案大小 (File Size)**: 74,718 bytes
- **解碼封包數 (Packets Decoded)**: 1,972 packets
- **錯誤數 (Errors)**: 0

#### 封包類型統計 (Packet Type Statistics):
| 封包類型 (Type) | 數量 (Count) | 說明 (Description) |
|----------------|--------------|-------------------|
| Type 1         | 456         | ATP/MMI packets (includes MMI_DYNAMIC and MMI_STATUS) |
| Type 31        | 223         | Unknown type 31   |
| Type 33        | 8           | Unknown type 33   |
| Type 42        | 194         | BTM Status        |
| Type 43-47     | 105 each    | BTM Telegram fragments |
| Type 51        | 1           | Unknown type 51   |
| Type 52        | 3           | Unknown type 52   |
| Type 61        | 10          | Unknown type 61   |
| Type 62-64     | 106-114     | Unknown types     |
| Type 71        | 2           | Unknown type 71   |
| Type 211       | 224         | Unknown type 211  |

### 3. MMI File 2 (Man-Machine Interface)
- **檔案路徑 (File Path)**: `tests/MMI_file/00000267_001170--_775495--_-9042/120001.MMI`
- **檔案大小 (File Size)**: 125,011 bytes
- **解碼封包數 (Packets Decoded)**: 3,438 packets
- **錯誤數 (Errors)**: 0

#### 封包類型統計 (Packet Type Statistics):
| 封包類型 (Type) | 數量 (Count) | 說明 (Description) |
|----------------|--------------|-------------------|
| Type 1         | 759         | ATP/MMI packets (includes MMI_DYNAMIC and MMI_STATUS) |
| Type 21        | 1           | VDX packet        |
| Type 31        | 492         | Unknown type 31   |
| Type 33        | 20          | Unknown type 33   |
| Type 42        | 286         | BTM Status        |
| Type 43-47     | 164 each    | BTM Telegram fragments |
| Type 51        | 7           | Unknown type 51   |
| Type 52        | 6           | Unknown type 52   |
| Type 61        | 23          | Unknown type 61   |
| Type 62-64     | 168-192     | Unknown types     |
| Type 71        | 2           | Unknown type 71   |
| Type 211       | 494         | Unknown type 211  |

## 解碼數據類型 (Decoded Data Types)

### MMI_DYNAMIC (動態列車狀態)
包含以下數據欄位:
- **v_train**: 列車速度 (Train Speed in km/h)
- **a_train**: 列車加速度 (Train Acceleration in cm/s²)
- **o_train**: 列車位置 (Train Position in meters)
- **o_brake_target**: 煞車目標位置 (Brake Target Position in meters)
- **v_target**: 目標速度 (Target Speed in km/h)
- **t_interven_war**: 干預警告時間 (Intervention Warning Time in seconds)
- **v_permitted**: 允許速度 (Permitted Speed in km/h)
- **v_release**: 釋放速度 (Release Speed in km/h)
- **v_intervention**: 干預速度 (Intervention Speed in km/h)
- **m_warning**: 警告模式 (Warning Mode, 0-15)
- **m_slip**: 打滑指示 (Slip Indication, 0-1)
- **m_slide**: 滑動指示 (Slide Indication, 0-1)
- **o_bcsp**: BCSP位置 (BCSP Position in meters)

#### 範例數據 (Example Data):
```
Description: MMI_DYNAMIC
Header:
  Packet Number/Type: 1
  Recording Timestamp: 2024-11-15T11:41:35
  Train Location (meters): 617
  Train Speed (km/h): 177
Decoded Data:
  Train Speed (km/h): 177
  Train Acceleration (cm/s²): 19
  Train Position (meters): 617
  Brake Target Position (meters): 3294967295
  Target Speed (km/h): 65535
  Intervention Warning Time (seconds): 65535
  Permitted Speed (km/h): 694
  Release Speed (km/h): 65535
  Intervention Speed (km/h): 65535
  Warning Mode (0-15): 0
  Slip Indication (0-1): 0
  Slide Indication (0-1): 0
  BCSP Position (meters): 2147468038
```

### MMI_STATUS (列車狀態)
包含以下數據欄位:
- **m_adhesion**: 黏著模式 (Adhesion Mode)
- **m_mode**: 操作模式 (Operating Mode)
- **m_level**: ATP等級 (ATP Level)
- **m_emer_brake**: 緊急煞車狀態 (Emergency Brake Status)
- **m_service_brake**: 常用煞車狀態 (Service Brake Status)
- **m_override_eoa**: 超越EOA狀態 (Override EOA Status)
- **m_trip**: 跳閘狀態 (Trip Status)

#### 範例數據 (Example Data):
```
Description: MMI_STATUS
Header:
  Packet Number/Type: 1
  Recording Timestamp: 2024-11-15T11:41:55
  Train Location (meters): 5981
  Train Speed (km/h): 327
Decoded Data:
  Adhesion Mode: 100
  Operating Mode: 0
  ATP Level: 2
  Emergency Brake Status: 0
```

### BTM Telegrams (軌旁信標傳輸模組)
BTM telegrams are transmitted in 5 fragments and reassembled into complete 104-byte telegrams containing:
- **sequence_number**: 電報序列號 (Telegram Sequence Number)
- **data**: 完整104位元組電報數據 (Complete 104-byte telegram data)
- **nid_bg**: 信標組識別碼 (Balise Group Identifier)
- **m_count**: 訊息計數 (Message Count)

### Packet Header (封包標頭)
所有封包都包含標頭資訊:
- **packet_no**: 封包編號/類型 (Packet Number/Type)
- **timestamp**: 記錄時間戳 (Recording Timestamp)
- **location**: 列車位置 (Train Location in meters)
- **speed**: 列車速度 (Train Speed in km/h)

## 輸出檔案 (Output Files)

每個輸入檔案都產生兩種格式的輸出:

### 1. 文字格式 (Text Format) - `*_decoded.txt`
- 人類可讀的格式 (Human-readable format)
- 包含欄位說明 (Includes field descriptions)
- 適合直接閱讀和分析 (Suitable for direct reading and analysis)

### 2. JSON格式 (JSON Format) - `*_decoded.json`
- 機器可讀的格式 (Machine-readable format)
- 完整的結構化數據 (Complete structured data)
- 適合程式處理和進一步分析 (Suitable for programmatic processing and further analysis)

## 輸出檔案位置 (Output File Locations)

1. **RU File**:
   - `tests/RU_file/024423_decoded.txt`
   - `tests/RU_file/024423_decoded.json`

2. **MMI File 1**:
   - `tests/MMI_file/00000267_001170--_775495--_-9042/114011_decoded.txt`
   - `tests/MMI_file/00000267_001170--_775495--_-9042/114011_decoded.json`

3. **MMI File 2**:
   - `tests/MMI_file/00000267_001170--_775495--_-9042/120001_decoded.txt`
   - `tests/MMI_file/00000267_001170--_775495--_-9042/120001_decoded.json`

## 使用方法 (Usage)

### 重新解碼檔案 (Re-decode Files)
```bash
python3 decode_test_files.py
```

### 解碼其他檔案 (Decode Other Files)
```bash
# 文字格式輸出 (Text format output)
python3 decode_packets.py path/to/file.RU -o output.txt

# JSON格式輸出 (JSON format output)
python3 decode_packets.py path/to/file.MMI -f json -o output.json

# 只解碼前N個封包 (Decode first N packets only)
python3 decode_packets.py path/to/file.RU -n 100
```

## 備註 (Notes)

1. **大數值處理 (Large Values)**: 
   - 65535 和 4294967295 通常表示「不可用」或「無限」(Values like 65535 and 4294967295 often indicate "not available" or "infinite")

2. **位置調整 (Position Adjustment)**:
   - 位置值 >= 10億會自動減去10億 (Position values >= 1 billion are automatically adjusted by subtracting 1 billion)

3. **BTM電報組裝 (BTM Telegram Assembly)**:
   - BTM電報需要5個片段才能組成完整的104位元組電報 (BTM telegrams require all 5 fragments to be reassembled into complete 104-byte telegrams)

4. **未實作的封包類型 (Unimplemented Packet Types)**:
   - 某些封包類型尚未實作詳細解碼器，只顯示封包標頭 (Some packet types don't have detailed decoders yet and only show packet headers)

## 結論 (Conclusion)

成功解碼了所有指定的RU和MMI檔案，總共解碼了5,466個封包，沒有錯誤。解碼結果包含完整的封包標頭和可用的封包數據，以兩種格式（文字和JSON）輸出，方便人類閱讀和機器處理。

All specified RU and MMI files were successfully decoded, with a total of 5,466 packets decoded with zero errors. The decoded results include complete packet headers and available packet data, output in both text and JSON formats for both human reading and machine processing.
