# ATP_Re 單機版安裝指南
# ATP_Re Standalone Installation Guide

## 目錄 (Table of Contents)

1. [系統需求 (System Requirements)](#系統需求-system-requirements)
2. [Windows 安裝 (Windows Installation)](#windows-安裝-windows-installation)
3. [macOS 安裝 (macOS Installation)](#macos-安裝-macos-installation)
4. [首次啟動 (First Launch)](#首次啟動-first-launch)
5. [驗證安裝 (Verify Installation)](#驗證安裝-verify-installation)
6. [疑難排解 (Troubleshooting)](#疑難排解-troubleshooting)

---

## 系統需求 (System Requirements)

### Windows

- **作業系統**: Windows 10 (64-bit) 或更新版本
- **處理器**: Intel Core i3 或以上 / AMD 同等處理器
- **記憶體**: 最低 4GB RAM，建議 8GB 或以上
- **儲存空間**: 至少 500MB 可用空間（資料檔案需額外空間）
- **螢幕解析度**: 1280x720 或更高
- **網路**: 不需要網際網路連線（本地運作）

### macOS

- **作業系統**: macOS 11 Big Sur 或更新版本
- **處理器**: Intel 或 Apple Silicon (M1/M2/M3)
- **記憶體**: 最低 4GB RAM，建議 8GB 或以上
- **儲存空間**: 至少 500MB 可用空間（資料檔案需額外空間）
- **螢幕解析度**: 1280x720 或更高
- **網路**: 不需要網際網路連線（本地運作）

### 瀏覽器需求

ATP_Re 使用 Web 介面，支援以下瀏覽器：

- Google Chrome 90+
- Microsoft Edge 90+
- Mozilla Firefox 88+
- Safari 14+ (macOS)

---

## Windows 安裝 (Windows Installation)

### 下載

1. 前往發佈頁面下載最新版本
2. 下載檔案: `ATP_Re_v1.0.0_Windows.zip`
3. 下載校驗檔: `ATP_Re_v1.0.0_Windows.zip.sha256`

### 驗證下載檔案（可選）

開啟 PowerShell 並執行：

```powershell
Get-FileHash ATP_Re_v1.0.0_Windows.zip -Algorithm SHA256
```

比對輸出的 Hash 值與 `.sha256` 檔案中的值是否相同。

### 安裝步驟

#### 方法 1: 安裝到程式目錄（建議）

1. **解壓縮檔案**
   - 右鍵點擊 `ATP_Re_v1.0.0_Windows.zip`
   - 選擇「解壓縮全部」
   - 建議解壓縮到: `C:\Program Files\ATP_Re`

2. **設定權限（如需要）**
   - 如果解壓縮到 `Program Files`，可能需要管理員權限
   - 右鍵點擊資料夾 → 內容 → 安全性
   - 確保您的使用者帳戶有「讀取和執行」權限

3. **建立桌面捷徑（可選）**
   - 找到 `ATP_Re.exe`
   - 右鍵 → 傳送到 → 桌面（建立捷徑）

#### 方法 2: 便攜式安裝

1. **解壓縮到任意位置**
   - 可以解壓縮到桌面、文件資料夾或外接硬碟
   - 整個資料夾都可以搬移

2. **注意事項**
   - 資料庫和上傳的檔案會儲存在應用程式目錄中
   - 搬移整個資料夾會一起搬移所有資料

### 目錄結構

安裝後的目錄結構：

```
ATP_Re/
├── ATP_Re.exe              # 主啟動程式
├── ATP_API/                # API 後端（不要刪除）
│   └── ATP_API.exe
├── ATP_UI/                 # Web UI（不要刪除）
│   └── ATP_UI.exe
├── data/                   # 資料庫檔案目錄
│   └── atp_re.db          # SQLite 資料庫（自動建立）
├── config/                 # 配置檔案
│   └── .env.example       # 配置範本
├── logs/                   # 日誌檔案
├── uploads/                # 上傳檔案
├── reports/                # 報表輸出
├── README.txt             # 英文說明
└── README_zh.txt          # 中文說明
```

---

## macOS 安裝 (macOS Installation)

### 下載

1. 前往發佈頁面下載最新版本
2. 下載檔案: `ATP_Re_v1.0.0_macOS.dmg`
3. 下載校驗檔: `ATP_Re_v1.0.0_macOS.dmg.sha256`

### 驗證下載檔案（可選）

開啟終端機並執行：

```bash
shasum -a 256 ATP_Re_v1.0.0_macOS.dmg
```

比對輸出的 Hash 值與 `.sha256` 檔案中的值是否相同。

### 安裝步驟

1. **掛載 DMG 檔案**
   - 雙擊 `ATP_Re_v1.0.0_macOS.dmg`
   - DMG 檔案會自動掛載並開啟 Finder 視窗

2. **安裝應用程式**
   - 將 `ATP_Re.app` 拖曳到「應用程式」資料夾
   - 等待複製完成

3. **首次開啟（重要）**
   - 前往「應用程式」資料夾
   - 找到 `ATP_Re.app`
   - **不要直接雙擊開啟**
   - **右鍵點擊** → 選擇「開啟」
   - 在安全性警告對話框中點擊「開啟」

   > **為什麼需要這樣做？**  
   > macOS Gatekeeper 會阻止未經 Apple 認證的應用程式。  
   > 使用右鍵 → 開啟可以繞過這個限制。  
   > 這只需要在第一次啟動時執行。

4. **後續啟動**
   - 首次開啟後，之後就可以直接雙擊啟動

### 替代安裝方法：命令列

如果您熟悉命令列，也可以使用以下方式：

```bash
# 掛載 DMG
hdiutil attach ATP_Re_v1.0.0_macOS.dmg

# 複製到應用程式
cp -R /Volumes/ATP_Re/ATP_Re.app /Applications/

# 卸載 DMG
hdiutil detach /Volumes/ATP_Re

# 移除隔離屬性（跳過 Gatekeeper 警告）
xattr -cr /Applications/ATP_Re.app

# 啟動應用程式
open /Applications/ATP_Re.app
```

### 應用程式位置

- **系統安裝**: `/Applications/ATP_Re.app`
- **使用者安裝**: `~/Applications/ATP_Re.app`
- **資料位置**: 應用程式內部的 `Contents/Resources/` 目錄

---

## 首次啟動 (First Launch)

### Windows

1. **啟動應用程式**
   - 雙擊 `ATP_Re.exe`
   - 或使用桌面捷徑

2. **Windows 安全性警告**
   - 如果出現「Windows 已保護您的電腦」警告
   - 點擊「更多資訊」
   - 點擊「仍要執行」

3. **防火牆警告**
   - 如果出現 Windows 防火牆警告
   - 選擇「允許存取」
   - 這是因為應用程式需要開啟本地網路埠

4. **等待啟動**
   - 控制台視窗會顯示啟動進度
   - 等待訊息「✅ ATP_Re 系統已成功啟動！」

5. **自動開啟瀏覽器**
   - 瀏覽器會自動開啟並載入 Web UI
   - 預設網址: http://localhost:8501

### macOS

1. **啟動應用程式**
   - 在「應用程式」中雙擊 `ATP_Re.app`
   - 或使用 Spotlight 搜尋 "ATP_Re"

2. **授予權限**
   - 如果系統要求授予網路權限
   - 點擊「允許」

3. **等待啟動**
   - 終端機視窗會顯示啟動進度
   - 等待訊息「✅ ATP_Re 系統已成功啟動！」

4. **自動開啟瀏覽器**
   - 瀏覽器會自動開啟並載入 Web UI
   - 預設網址: http://localhost:8501

### 首次啟動畫面

成功啟動後，您會看到：

```
============================================
✅ ATP_Re 系統已成功啟動！
============================================

📊 Web UI:        http://localhost:8501
🔧 API 文檔:      http://localhost:8000/docs
📋 API 健康檢查:  http://localhost:8000/health

============================================
按 Ctrl+C 或關閉此視窗以停止系統
============================================
```

### Web 介面

瀏覽器會自動開啟 ATP_Re Web UI：

- **首頁**: 顯示系統儀表板
- **導航側邊欄**: 
  - Dashboard (儀表板)
  - File Upload (檔案上傳)
  - Task Management (任務管理)
  - Data Analysis (資料分析)
  - Event Monitoring (事件監控)
  - Reports (報表)

---

## 驗證安裝 (Verify Installation)

### 檢查系統狀態

1. **Web UI 可訪問**
   - 開啟瀏覽器，訪問: http://localhost:8501
   - 應該看到 ATP_Re 主畫面

2. **API 正常運作**
   - 訪問: http://localhost:8000/health
   - 應該看到: `{"status": "healthy"}`

3. **API 文檔**
   - 訪問: http://localhost:8000/docs
   - 應該看到 Swagger UI API 文檔

### 測試基本功能

1. **上傳測試檔案**
   - 在 Web UI 中選擇 "File Upload"
   - 選擇一個測試檔案
   - 點擊「Upload」按鈕
   - 驗證上傳成功

2. **查看儀表板**
   - 返回 "Dashboard"
   - 應該看到剛上傳的任務

3. **檢查資料庫**
   - 查看 `data/atp_re.db` 檔案是否已建立
   - 檔案大小應該大於 0 bytes

---

## 疑難排解 (Troubleshooting)

### Windows 常見問題

#### 問題 1: 無法啟動 - 缺少 DLL

**症狀**: 錯誤訊息「無法啟動此程式，因為您的電腦遺失 xxx.dll」

**解決方案**:
1. 安裝 Visual C++ Redistributable:
   - 下載: https://aka.ms/vs/17/release/vc_redist.x64.exe
   - 執行安裝程式
2. 重新啟動應用程式

#### 問題 2: 防毒軟體阻擋

**症狀**: 防毒軟體將 ATP_Re.exe 識別為威脅

**解決方案**:
1. 這是誤報（False Positive）
2. 將 ATP_Re 資料夾加入防毒軟體的白名單
3. 或暫時停用防毒軟體進行測試

#### 問題 3: 埠號被佔用

**症狀**: 錯誤訊息「端口 8000 或 8501 已被佔用」

**解決方案**:
1. 檢查是否有其他 ATP_Re 實例正在運行
2. 關閉佔用埠號的程式:
   ```cmd
   netstat -ano | findstr "8000"
   netstat -ano | findstr "8501"
   taskkill /PID [PID號碼] /F
   ```

#### 問題 4: 瀏覽器未自動開啟

**症狀**: 系統啟動成功但瀏覽器未開啟

**解決方案**:
1. 手動開啟瀏覽器
2. 訪問: http://localhost:8501
3. 檢查預設瀏覽器設定

### macOS 常見問題

#### 問題 1: 「無法開啟 ATP_Re，因為它來自未識別的開發者」

**症狀**: macOS 阻止開啟應用程式

**解決方案**:
1. 不要直接雙擊開啟
2. 右鍵點擊 `ATP_Re.app` → 「開啟」
3. 在對話框中點擊「開啟」

**替代方案**:
```bash
xattr -cr /Applications/ATP_Re.app
```

#### 問題 2: 「ATP_Re 已損毀，無法開啟」

**症狀**: macOS 報告應用程式已損毀

**解決方案**:
```bash
# 移除隔離屬性
xattr -cr /Applications/ATP_Re.app

# 如果仍然無法開啟，暫時允許任何來源的應用程式（不建議）
sudo spctl --master-disable
# 開啟應用程式後，記得恢復設定
sudo spctl --master-enable
```

#### 問題 3: Python 相關錯誤

**症狀**: 錯誤訊息提到 Python 或找不到模組

**解決方案**:
1. 應用程式應該是完全獨立的，不需要系統 Python
2. 嘗試重新下載並安裝
3. 確認完整複製了整個 .app

#### 問題 4: 埠號被佔用

**症狀**: 錯誤訊息「端口 8000 或 8501 已被佔用」

**解決方案**:
```bash
# 檢查埠號使用情況
lsof -i :8000
lsof -i :8501

# 終止佔用埠號的程序
kill -9 [PID]
```

### 通用問題

#### 問題 1: 記憶體不足

**症狀**: 系統運行緩慢或崩潰

**解決方案**:
1. 關閉其他不必要的應用程式
2. 增加系統 RAM
3. 處理較小的資料檔案

#### 問題 2: 資料庫錯誤

**症狀**: 無法存取或寫入資料庫

**解決方案**:
1. 檢查 `data/` 目錄權限
2. 確保有足夠的磁碟空間
3. 如果資料庫損壞，刪除 `atp_re.db` 重新開始
4. 備份重要資料

#### 問題 3: Web UI 無法載入

**症狀**: 瀏覽器顯示連線錯誤

**解決方案**:
1. 確認應用程式正在運行
2. 檢查防火牆設定
3. 嘗試不同的瀏覽器
4. 清除瀏覽器快取

#### 問題 4: 檔案上傳失敗

**症狀**: 無法上傳檔案或上傳後出錯

**解決方案**:
1. 檢查 `uploads/` 目錄權限
2. 確保有足夠的磁碟空間
3. 檢查檔案大小限制（預設 100MB）
4. 檢查檔案格式是否支援

---

## 取得協助 (Getting Help)

### 日誌檔案

遇到問題時，請檢查日誌檔案：

**Windows**:
- `logs\api.log` - API 後端日誌
- `logs\ui.log` - UI 日誌
- `atp_re_launcher.log` - 啟動器日誌

**macOS**:
- `ATP_Re.app/Contents/Resources/logs/` 目錄中的日誌檔案

### 回報問題

如果問題無法解決，請提供以下資訊：

1. **系統資訊**
   - 作業系統版本
   - ATP_Re 版本

2. **錯誤訊息**
   - 完整的錯誤訊息
   - 截圖（如有）

3. **日誌檔案**
   - 相關的日誌內容

4. **重現步驟**
   - 如何重現問題

### 聯絡方式

- GitHub Issues: https://github.com/Lawliet0813/ATP_re/issues
- 專案 Wiki: https://github.com/Lawliet0813/ATP_re/wiki

---

## 解除安裝 (Uninstallation)

### Windows

1. 關閉 ATP_Re 應用程式
2. 刪除 ATP_Re 資料夾
3. 刪除桌面捷徑（如有）

**注意**: 這會刪除所有資料，請先備份重要檔案！

### macOS

1. 關閉 ATP_Re 應用程式
2. 將 `ATP_Re.app` 拖曳到垃圾桶
3. 清空垃圾桶

**注意**: 這會刪除所有資料，請先備份重要檔案！

### 備份重要資料

在解除安裝前，請備份：
- `data/atp_re.db` - 資料庫檔案
- `uploads/` - 上傳的檔案
- `reports/` - 生成的報表
- `config/` - 自訂配置

---

## 更新 (Updates)

### 檢查更新

1. 訪問發佈頁面查看最新版本
2. 下載新版本

### 更新步驟

1. **備份資料**
   - 複製 `data/`, `uploads/`, `reports/` 目錄

2. **關閉舊版本**
   - 完全關閉 ATP_Re

3. **安裝新版本**
   - 解壓縮/安裝新版本到新位置
   - 或覆蓋舊版本（Windows）

4. **還原資料**
   - 將備份的資料複製回新版本目錄

5. **測試**
   - 啟動新版本
   - 驗證資料完整性

---

## 授權資訊 (License)

ATP_Re 使用多個開源軟體。詳細授權資訊請參閱:
- `LICENSE.txt` - 應用程式授權
- `LICENSES/` - 依賴套件授權（如有）

---

## 版本歷史 (Version History)

### v1.0.0 (2024-xx-xx)
- 初始發佈
- 支援 Windows 10+ 和 macOS 11+
- 完整的資料分析功能
- SQLite 資料庫後端
- Web 介面

---

**最後更新**: 2024年
**文檔版本**: 1.0.0
