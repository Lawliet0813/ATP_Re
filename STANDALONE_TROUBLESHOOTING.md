# ATP_Re 單機版疑難排解指南
# ATP_Re Standalone Troubleshooting Guide

## 目錄 (Table of Contents)

1. [診斷工具 (Diagnostic Tools)](#診斷工具-diagnostic-tools)
2. [啟動問題 (Startup Issues)](#啟動問題-startup-issues)
3. [連線問題 (Connection Issues)](#連線問題-connection-issues)
4. [檔案與任務問題 (File & Task Issues)](#檔案與任務問題-file--task-issues)
5. [效能問題 (Performance Issues)](#效能問題-performance-issues)
6. [資料庫問題 (Database Issues)](#資料庫問題-database-issues)
7. [介面問題 (UI Issues)](#介面問題-ui-issues)
8. [錯誤代碼參考 (Error Code Reference)](#錯誤代碼參考-error-code-reference)

---

## 診斷工具 (Diagnostic Tools)

### 檢查系統狀態

#### 1. 檢查服務是否運行

**Windows**:
```cmd
netstat -ano | findstr "8000"
netstat -ano | findstr "8501"
```

**macOS/Linux**:
```bash
lsof -i :8000
lsof -i :8501
```

**預期輸出**: 應該看到 ATP_API 和 ATP_UI 正在監聽這些埠號

#### 2. 檢查 API 健康狀態

**瀏覽器**:
```
http://localhost:8000/health
```

**cURL**:
```bash
curl http://localhost:8000/health
```

**預期輸出**: `{"status": "healthy"}`

#### 3. 查看日誌檔案

**位置**:
- Windows: `[安裝目錄]\logs\`
- macOS: `ATP_Re.app/Contents/Resources/logs/`

**重要日誌**:
- `atp_re_launcher.log` - 啟動器日誌
- `api.log` - API 後端日誌
- `ui.log` - UI 前端日誌

### 系統資訊收集

建立診斷報告腳本：

**Windows (診斷.bat)**:
```batch
@echo off
echo ATP_Re 診斷報告 > diagnosis.txt
echo ================ >> diagnosis.txt
echo. >> diagnosis.txt
echo [1] 系統資訊 >> diagnosis.txt
systeminfo | findstr /C:"OS" /C:"Memory" >> diagnosis.txt
echo. >> diagnosis.txt
echo [2] 埠號檢查 >> diagnosis.txt
netstat -ano | findstr "8000 8501" >> diagnosis.txt
echo. >> diagnosis.txt
echo [3] 程序檢查 >> diagnosis.txt
tasklist | findstr "ATP" >> diagnosis.txt
echo. >> diagnosis.txt
echo [4] 磁碟空間 >> diagnosis.txt
wmic logicaldisk get caption,freespace,size >> diagnosis.txt
echo. >> diagnosis.txt
echo 診斷報告已儲存至 diagnosis.txt
pause
```

**macOS (診斷.sh)**:
```bash
#!/bin/bash
echo "ATP_Re 診斷報告" > diagnosis.txt
echo "================" >> diagnosis.txt
echo "" >> diagnosis.txt
echo "[1] 系統資訊" >> diagnosis.txt
sw_vers >> diagnosis.txt
sysctl -n hw.memsize | awk '{print $1/1024/1024/1024 " GB"}' >> diagnosis.txt
echo "" >> diagnosis.txt
echo "[2] 埠號檢查" >> diagnosis.txt
lsof -i :8000 >> diagnosis.txt
lsof -i :8501 >> diagnosis.txt
echo "" >> diagnosis.txt
echo "[3] 程序檢查" >> diagnosis.txt
ps aux | grep ATP >> diagnosis.txt
echo "" >> diagnosis.txt
echo "[4] 磁碟空間" >> diagnosis.txt
df -h >> diagnosis.txt
echo "" >> diagnosis.txt
echo "診斷報告已儲存至 diagnosis.txt"
```

---

## 啟動問題 (Startup Issues)

### 問題 1: 無法啟動 - 缺少 DLL (Windows)

**症狀**:
```
無法啟動此程式，因為您的電腦遺失 VCRUNTIME140.dll
```

**原因**: 缺少 Visual C++ Redistributable

**解決方案**:
1. 下載並安裝 Visual C++ Redistributable:
   - https://aka.ms/vs/17/release/vc_redist.x64.exe
2. 重新啟動電腦
3. 再次嘗試啟動 ATP_Re

### 問題 2: 啟動後立即關閉

**症狀**: 
- 控制台視窗閃現後立即關閉
- 沒有錯誤訊息

**診斷步驟**:

1. **使用命令列啟動**（保留輸出）:
   
   **Windows**:
   ```cmd
   cd [ATP_Re 安裝目錄]
   ATP_Re.exe > startup.log 2>&1
   type startup.log
   ```
   
   **macOS**:
   ```bash
   cd /Applications
   ./ATP_Re.app/Contents/MacOS/ATP_Re 2>&1 | tee startup.log
   cat startup.log
   ```

2. **檢查日誌檔案**:
   - 查看 `atp_re_launcher.log`
   - 尋找錯誤訊息

**常見原因與解決方案**:

| 原因 | 解決方案 |
|------|----------|
| 權限不足 | 以管理員身分執行 |
| 埠號被佔用 | 終止佔用埠號的程序 |
| 磁碟空間不足 | 清理磁碟空間 |
| 防毒軟體阻擋 | 將 ATP_Re 加入白名單 |

### 問題 3: Python 錯誤訊息

**症狀**:
```
ImportError: No module named 'xxx'
ModuleNotFoundError: No module named 'xxx'
```

**原因**: PyInstaller 打包遺漏某些模組

**解決方案**:
1. 確認下載的是完整版本
2. 重新下載並解壓縮
3. 檢查檔案完整性（SHA256）
4. 如果仍有問題，回報 Issue

### 問題 4: 埠號衝突

**症狀**:
```
[ERROR] 端口 8000 已被佔用
[ERROR] 端口 8501 已被佔用
```

**診斷**:

**Windows**:
```cmd
netstat -ano | findstr "8000"
netstat -ano | findstr "8501"
```

記下 PID（最後一欄的數字）

**解決方案**:

**方法 1: 終止佔用程序**:
```cmd
taskkill /PID [PID號碼] /F
```

**方法 2: 修改埠號**:
1. 編輯 `config/.env`
2. 修改埠號設定:
   ```
   API_PORT=8001
   UI_PORT=8502
   ```
3. 重新啟動

---

## 連線問題 (Connection Issues)

### 問題 5: 瀏覽器無法連線

**症狀**:
- "無法連線到此網站"
- "ERR_CONNECTION_REFUSED"

**診斷步驟**:

1. **確認服務正在運行**:
   ```bash
   # API
   curl http://localhost:8000/health
   
   # UI
   curl http://localhost:8501
   ```

2. **檢查防火牆**:
   
   **Windows**:
   - 控制台 → 系統及安全性 → Windows Defender 防火牆
   - 允許應用程式通過防火牆
   - 確認 ATP_Re 已被允許
   
   **macOS**:
   - 系統偏好設定 → 安全性與隱私 → 防火牆
   - 允許 ATP_Re

3. **嘗試不同瀏覽器**:
   - Chrome
   - Firefox
   - Edge
   - Safari (macOS)

**解決方案**:

如果 API 健康檢查失敗:
1. 重新啟動 ATP_Re
2. 檢查日誌檔案
3. 確認沒有埠號衝突

如果 UI 無法連線:
1. 確認 API 已啟動
2. 等待 UI 完全啟動（10-15秒）
3. 手動訪問 http://localhost:8501

### 問題 6: CORS 錯誤

**症狀**:
```
Access to XMLHttpRequest blocked by CORS policy
```

**原因**: 跨域請求被阻擋

**解決方案**:
1. 確認使用 `localhost` 而非 `127.0.0.1` 或 IP 位址
2. 不要從檔案系統直接開啟 HTML（必須通過 Web 伺服器）
3. 檢查 `config/.env` 中的 CORS 設定

### 問題 7: WebSocket 連線失敗

**症狀**:
- Streamlit 顯示 "Disconnected"
- 頁面不會即時更新

**解決方案**:
1. 重新整理頁面（F5）
2. 清除瀏覽器快取
3. 檢查防火牆 WebSocket 設定
4. 重新啟動 ATP_Re

---

## 檔案與任務問題 (File & Task Issues)

### 問題 8: 檔案上傳失敗

**症狀**:
- 上傳進度條停止
- 顯示上傳失敗錯誤

**可能原因與解決方案**:

#### 原因 1: 檔案太大
```
Solution: 
- 檢查檔案大小 (< 100MB)
- 壓縮後再上傳
- 分割大檔案
```

#### 原因 2: 磁碟空間不足
```
檢查:
Windows: wmic logicaldisk get caption,freespace,size
macOS: df -h

Solution: 清理磁碟空間
```

#### 原因 3: 權限問題
```
檢查: uploads/ 目錄權限

Windows: 右鍵 → 內容 → 安全性
macOS: ls -la uploads/

Solution: 修正權限
Windows: 允許完全控制
macOS: chmod 755 uploads/
```

#### 原因 4: 網路逾時
```
Solution:
- 重新嘗試上傳
- 檢查本地網路狀態
- 增加上傳逾時時間（需修改配置）
```

### 問題 9: 任務處理失敗

**症狀**:
- 任務狀態變為 "failed"
- 錯誤訊息出現

**診斷步驟**:

1. **查看任務詳情**:
   - 在 Task Management 中展開任務
   - 查看錯誤訊息

2. **檢查日誌**:
   ```bash
   # 查看最新 API 日誌
   tail -n 100 logs/api.log
   ```

3. **檢查檔案**:
   - 確認檔案未損壞
   - 驗證檔案格式正確

**常見錯誤與解決方案**:

| 錯誤 | 原因 | 解決方案 |
|------|------|----------|
| "Invalid file format" | 檔案格式不正確 | 確認檔案類型 |
| "Memory error" | 記憶體不足 | 關閉其他應用程式 |
| "Database error" | 資料庫問題 | 檢查資料庫狀態 |
| "Timeout" | 處理時間過長 | 增加逾時或處理較小檔案 |

### 問題 10: 無法查看分析結果

**症狀**:
- Data Analysis 頁面空白
- 沒有資料顯示

**檢查清單**:

1. ✅ 任務已完成處理
2. ✅ 選擇了正確的任務
3. ✅ 點擊了「Load Data」
4. ✅ 資料庫中有資料

**解決方案**:

```sql
-- 檢查資料庫中的資料（需要 SQLite 工具）
sqlite3 data/atp_re.db "SELECT COUNT(*) FROM data_records;"
```

如果沒有資料:
1. 檢查任務是否真的成功處理
2. 重新處理任務
3. 檢查日誌檔案

---

## 效能問題 (Performance Issues)

### 問題 11: 系統運行緩慢

**症狀**:
- 介面反應慢
- 處理時間長
- 系統卡頓

**診斷**:

1. **檢查系統資源**:
   
   **Windows**:
   ```
   工作管理員 (Ctrl+Shift+Esc)
   → 效能標籤
   → 查看 CPU、記憶體、磁碟使用率
   ```
   
   **macOS**:
   ```
   活動監視器
   → 查看 CPU、記憶體使用情況
   ```

2. **檢查 ATP_Re 資源使用**:
   - 尋找 ATP_API 和 ATP_UI 程序
   - 查看各自的資源使用

**解決方案**:

#### 如果 CPU 使用率高 (> 80%)
```
原因: 正在處理大量資料
Solution:
- 等待處理完成
- 處理較小的資料集
- 升級 CPU
```

#### 如果記憶體使用率高 (> 90%)
```
Solution:
- 關閉其他應用程式
- 重新啟動 ATP_Re
- 增加系統 RAM
- 處理較小的資料集
```

#### 如果磁碟使用率高 (> 90%)
```
Solution:
- 等待 I/O 操作完成
- 使用 SSD
- 清理磁碟空間
- 關閉磁碟密集型應用程式
```

### 問題 12: 記憶體洩漏

**症狀**:
- 記憶體使用持續增長
- 最終導致系統變慢或崩潰

**診斷**:
```
監控記憶體使用隨時間變化:
- 啟動時: ~300MB
- 使用中: ~500MB-1GB
- 如果持續增長超過 2GB → 可能有洩漏
```

**解決方案**:
1. **短期**: 定期重新啟動 ATP_Re
2. **長期**: 回報問題，提供:
   - 操作步驟
   - 記憶體使用記錄
   - 日誌檔案

### 問題 13: 大檔案處理慢

**症狀**:
- 上傳或處理大檔案時系統無回應
- 長時間顯示「處理中」

**優化建議**:

1. **分批處理**:
   ```
   大檔案 (> 100MB)
   → 分割成多個小檔案
   → 逐一處理
   ```

2. **壓縮檔案**:
   ```
   壓縮比率通常可達 50-70%
   → 減少上傳時間
   → 減少儲存空間
   ```

3. **硬體升級**:
   - 增加 RAM (最有效)
   - 使用 SSD
   - 升級 CPU

---

## 資料庫問題 (Database Issues)

### 問題 14: 資料庫鎖定

**症狀**:
```
database is locked
database is busy
```

**原因**:
- 多個程序同時存取資料庫
- 前一次操作未正確關閉
- 資料庫檔案權限問題

**解決方案**:

1. **檢查是否有多個 ATP_Re 實例**:
   ```bash
   # Windows
   tasklist | findstr "ATP"
   
   # macOS
   ps aux | grep ATP
   ```

2. **終止所有 ATP_Re 程序**:
   ```bash
   # Windows
   taskkill /F /IM ATP_API.exe
   taskkill /F /IM ATP_UI.exe
   
   # macOS
   killall ATP_API
   killall ATP_UI
   ```

3. **檢查並刪除鎖定檔案**:
   ```bash
   # 刪除 SQLite 鎖定檔案（如果存在）
   rm data/atp_re.db-journal
   rm data/atp_re.db-wal
   rm data/atp_re.db-shm
   ```

4. **重新啟動 ATP_Re**

### 問題 15: 資料庫損壞

**症狀**:
```
database disk image is malformed
database corruption
```

**診斷**:
```bash
# 使用 SQLite 工具檢查
sqlite3 data/atp_re.db "PRAGMA integrity_check;"
```

**解決方案**:

**方法 1: 嘗試修復**
```bash
# 備份
cp data/atp_re.db data/atp_re.db.corrupt

# 匯出資料
sqlite3 data/atp_re.db ".dump" > dump.sql

# 建立新資料庫
rm data/atp_re.db
sqlite3 data/atp_re.db < dump.sql
```

**方法 2: 從備份還原**
```bash
cp data/atp_re.db.backup data/atp_re.db
```

**方法 3: 重新開始**
```bash
# 警告: 這會刪除所有資料
rm data/atp_re.db
# 重新啟動 ATP_Re 會建立新資料庫
```

### 問題 16: 資料遺失

**症狀**:
- 之前的資料不見了
- 任務列表空白

**可能原因**:

1. **錯誤的資料庫路徑**:
   - 檢查是否在正確的目錄啟動
   - 確認 `data/atp_re.db` 存在

2. **資料庫被重置**:
   - 檢查是否誤刪資料庫
   - 查看備份

3. **配置錯誤**:
   - 檢查 `config/.env`
   - 確認 `DATABASE_PATH` 設定正確

**恢復資料**:

如果有備份:
```bash
cp [備份位置]/atp_re.db data/atp_re.db
```

如果沒有備份:
- 無法恢復
- 建議建立定期備份策略

---

## 介面問題 (UI Issues)

### 問題 17: 頁面顯示異常

**症狀**:
- 佈局錯亂
- 元素重疊
- 圖表不顯示

**解決方案**:

1. **清除瀏覽器快取**:
   ```
   Chrome: Ctrl+Shift+Delete
   Firefox: Ctrl+Shift+Delete
   Edge: Ctrl+Shift+Delete
   Safari: Cmd+Option+E
   ```

2. **強制重新整理**:
   ```
   Windows: Ctrl+F5
   macOS: Cmd+Shift+R
   ```

3. **嘗試無痕模式**:
   ```
   Chrome: Ctrl+Shift+N
   Firefox: Ctrl+Shift+P
   ```

4. **檢查瀏覽器控制台**:
   ```
   F12 → Console 標籤
   查看是否有 JavaScript 錯誤
   ```

### 問題 18: 圖表無法互動

**症狀**:
- 無法縮放圖表
- 懸停提示不顯示
- 圖例點擊無效

**檢查**:

1. **瀏覽器相容性**:
   - 確認使用支援的瀏覽器
   - 更新到最新版本

2. **JavaScript 是否啟用**:
   - 檢查瀏覽器設定
   - 確認沒有擴充套件阻擋

3. **網路連線**:
   - 圖表庫可能需要載入資源
   - 檢查是否有連線錯誤

**解決方案**:
1. 更新瀏覽器到最新版本
2. 停用可能衝突的擴充套件
3. 嘗試不同瀏覽器

### 問題 19: 上傳介面卡住

**症狀**:
- 選擇檔案後無反應
- 上傳按鈕變灰無法點擊

**解決方案**:

1. **檢查檔案大小**:
   - 確認 < 100MB

2. **檢查檔案類型**:
   - 確認是支援的格式

3. **重新整理頁面**:
   - F5 重新載入
   - 重新選擇檔案

4. **檢查瀏覽器控制台**:
   - 查看錯誤訊息

---

## 錯誤代碼參考 (Error Code Reference)

### HTTP 錯誤代碼

| 代碼 | 說明 | 常見原因 | 解決方案 |
|------|------|----------|----------|
| 400 | Bad Request | 請求格式錯誤 | 檢查請求資料 |
| 404 | Not Found | 資源不存在 | 檢查 URL 或資源 ID |
| 413 | Payload Too Large | 檔案太大 | 減小檔案或提高限制 |
| 500 | Internal Server Error | 伺服器內部錯誤 | 查看日誌檔案 |
| 503 | Service Unavailable | 服務無法使用 | 檢查服務狀態 |

### 應用程式錯誤代碼

| 代碼 | 類別 | 說明 | 解決方案 |
|------|------|------|----------|
| E001 | 啟動 | 埠號被佔用 | 釋放埠號或修改配置 |
| E002 | 啟動 | 資料庫初始化失敗 | 檢查磁碟空間和權限 |
| E003 | 檔案 | 檔案格式不支援 | 確認檔案格式 |
| E004 | 檔案 | 檔案損壞 | 重新取得檔案 |
| E005 | 資料庫 | 資料庫鎖定 | 終止其他實例 |
| E006 | 資料庫 | 資料庫損壞 | 修復或還原資料庫 |
| E007 | 記憶體 | 記憶體不足 | 增加 RAM 或減少資料量 |
| E008 | 處理 | 處理逾時 | 增加逾時或減少資料量 |

---

## 取得協助 (Getting Help)

如果以上方法都無法解決問題:

### 1. 收集診斷資訊

- 系統資訊（作業系統、版本）
- ATP_Re 版本
- 錯誤訊息（完整內容）
- 日誌檔案（`logs/` 目錄）
- 重現步驟

### 2. 搜尋已知問題

- GitHub Issues: https://github.com/Lawliet0813/ATP_re/issues
- 搜尋類似問題

### 3. 建立新 Issue

如果找不到解決方案:
1. 前往 GitHub Issues
2. 點擊「New Issue」
3. 使用問題模板
4. 提供所有診斷資訊

### 4. 社群協助

- GitHub Discussions
- 相關論壇或社群

---

**持續更新中...**

如有新的疑難排解方案，會持續更新本文檔。

**最後更新**: 2024年
**文檔版本**: 1.0.0
