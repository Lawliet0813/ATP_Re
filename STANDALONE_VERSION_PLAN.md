# ATP_Re 單機版規劃文檔
# ATP_Re Standalone Version Planning Document

## 專案概述 (Project Overview)

本文檔規劃 ATP 行車紀錄分析系統的單機版本，支援 Windows 及 macOS 作業系統，讓使用者無需架設伺服器即可在本地電腦執行完整系統。

This document outlines the plan for creating standalone versions of the ATP Train Record Analysis System for Windows and macOS, allowing users to run the complete system on their local computers without server setup.

## 系統架構分析 (System Architecture Analysis)

### 當前架構 (Current Architecture)

- **後端 (Backend)**: FastAPI (Python) - REST API 服務
- **前端 (Frontend)**: Streamlit (Python) - Web UI 介面
- **資料庫 (Database)**: PostgreSQL - 關聯式資料庫
- **快取 (Cache)**: Redis - 記憶體快取
- **監控 (Monitoring)**: Prometheus + Grafana

### 依賴套件分析 (Dependency Analysis)

#### 核心依賴 (Core Dependencies)
```
fastapi==0.104.1          # Web framework
uvicorn==0.24.0           # ASGI server
streamlit==1.28.2         # UI framework
pandas==2.1.3             # Data processing
numpy==1.26.2             # Numerical computing
plotly==5.18.0            # Visualization
sqlalchemy==2.0.23        # Database ORM
psycopg2-binary>=2.9.9    # PostgreSQL driver
```

#### 可選依賴 (Optional Dependencies)
```
redis>=5.0.0              # Caching (可用檔案快取替代)
prometheus-client>=0.19.0 # Monitoring (單機版可選)
```

## 可移植性評估 (Portability Assessment)

### ✅ 可直接移植 (Directly Portable)

1. **Python 核心邏輯**: 所有資料處理、分析邏輯都是純 Python 代碼
2. **Web 框架**: FastAPI 和 Streamlit 都支援跨平台
3. **資料處理**: pandas、numpy 等套件完全跨平台
4. **視覺化**: plotly 圖表引擎跨平台

### ⚠️ 需要調整 (Requires Adaptation)

1. **資料庫**: PostgreSQL → SQLite (輕量級、無需安裝)
2. **快取**: Redis → 檔案系統快取或記憶體快取
3. **監控**: 在單機版中設為可選功能

### 🔧 平台特定處理 (Platform-Specific Handling)

1. **Windows**: 
   - 使用 `.exe` 執行檔
   - Windows 服務註冊 (可選)
   - 檔案路徑處理 (反斜線)

2. **macOS**: 
   - 建立 `.app` 應用程式包
   - 程式碼簽章需求
   - 檔案權限處理

## 跨平台架構規劃 (Cross-Platform Architecture)

### 架構圖 (Architecture Diagram)

```
┌─────────────────────────────────────────────────┐
│         ATP_Re Standalone Application            │
├─────────────────────────────────────────────────┤
│                                                  │
│  ┌──────────────┐        ┌──────────────┐      │
│  │  Streamlit   │◄───────┤  FastAPI     │      │
│  │  Web UI      │        │  Backend API │      │
│  │  (Port 8501) │        │  (Port 8000) │      │
│  └──────────────┘        └──────┬───────┘      │
│                                  │               │
│                          ┌───────▼────────┐     │
│                          │  SQLite DB     │     │
│                          │  (Local File)  │     │
│                          └────────────────┘     │
│                                                  │
│  ┌──────────────────────────────────────────┐  │
│  │  Browser (自動開啟 / Auto-launch)        │  │
│  └──────────────────────────────────────────┘  │
│                                                  │
└─────────────────────────────────────────────────┘
```

### 單機版特性 (Standalone Features)

1. **一鍵啟動**: 雙擊執行檔即可啟動完整系統
2. **自動開啟瀏覽器**: 啟動後自動在預設瀏覽器開啟 UI
3. **內嵌資料庫**: SQLite 資料庫檔案存放在應用程式資料夾
4. **便攜性**: 所有資料存放在應用程式目錄，可整個資料夾搬移
5. **不需安裝**: 解壓縮即可使用

## 打包工具選擇 (Packaging Tool Selection)

### 評估方案 (Evaluation)

| 工具 | Windows | macOS | 優點 | 缺點 |
|------|---------|-------|------|------|
| **PyInstaller** | ✅ | ✅ | 成熟穩定、文檔完善、支援多平台 | 打包檔案較大 |
| cx_Freeze | ✅ | ✅ | 較小的打包體積 | 文檔較少、設定複雜 |
| py2exe | ✅ | ❌ | 僅 Windows、簡單易用 | 不支援 macOS |
| py2app | ❌ | ✅ | 僅 macOS、官方推薦 | 不支援 Windows |

### 選定方案: PyInstaller

**理由**:
1. ✅ 同時支援 Windows 和 macOS
2. ✅ 社群活躍，文檔完整
3. ✅ 支援複雜的依賴關係
4. ✅ 可以打包為單一執行檔或目錄
5. ✅ 支援資料檔案嵌入

## 資料庫遷移方案 (Database Migration)

### PostgreSQL → SQLite

#### 差異分析 (Differences)

| 功能 | PostgreSQL | SQLite | 影響 |
|------|-----------|--------|------|
| 資料類型 | 豐富 | 基本 | 需要調整 schema |
| 並發控制 | 完整 | 有限 | 單機版影響小 |
| 交易支援 | 完整 | 完整 | 無影響 |
| JSON 支援 | 原生 | 部分 | 需要調整查詢 |
| 全文搜尋 | 原生 | FTS5 擴展 | 需要調整 |

#### 遷移策略 (Migration Strategy)

1. **抽象化資料庫層**: 使用 SQLAlchemy ORM 統一介面
2. **相容性處理**: 建立資料庫適配器處理特定語法
3. **Schema 轉換**: 建立轉換腳本將 PostgreSQL schema 轉為 SQLite
4. **資料匯入匯出**: 提供工具在不同資料庫間遷移資料

### 快取方案 (Caching Solution)

**Redis → 檔案系統/記憶體快取**

```python
# 抽象快取介面
class CacheAdapter:
    def get(self, key): pass
    def set(self, key, value, ttl): pass
    def delete(self, key): pass

# 檔案快取實作
class FileCache(CacheAdapter):
    def __init__(self, cache_dir):
        self.cache_dir = cache_dir
    # ... 實作

# 記憶體快取實作  
class MemoryCache(CacheAdapter):
    def __init__(self):
        self.cache = {}
    # ... 實作
```

## 打包配置 (Packaging Configuration)

### PyInstaller Spec 檔案

#### API 後端 (API Backend)

```python
# api_backend.spec
a = Analysis(
    ['api/main.py'],
    pathex=[],
    binaries=[],
    datas=[
        ('api', 'api'),
        ('config', 'config'),
        ('core', 'core'),
        ('src', 'src'),
    ],
    hiddenimports=[
        'uvicorn.logging',
        'uvicorn.loops',
        'uvicorn.protocols',
        'fastapi',
        'sqlalchemy.dialects.sqlite',
    ],
    hookspath=[],
    runtime_hooks=[],
    excludes=['tkinter'],
    win_no_prefer_redirects=False,
    win_private_assemblies=False,
    noarchive=False,
)

pyz = PYZ(a.pure, a.zipped_data)

exe = EXE(
    pyz,
    a.scripts,
    [],
    exclude_binaries=True,
    name='ATP_API',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    console=True,
)

coll = COLLECT(
    exe,
    a.binaries,
    a.zipfiles,
    a.datas,
    strip=False,
    upx=True,
    name='ATP_API',
)
```

#### Streamlit UI

```python
# streamlit_ui.spec
a = Analysis(
    ['streamlit_ui/app.py'],
    pathex=[],
    binaries=[],
    datas=[
        ('streamlit_ui', 'streamlit_ui'),
    ],
    hiddenimports=[
        'streamlit',
        'altair',
        'plotly',
    ],
    hookspath=[],
    runtime_hooks=[],
    excludes=['tkinter'],
    win_no_prefer_redirects=False,
    win_private_assemblies=False,
    noarchive=False,
)

pyz = PYZ(a.pure, a.zipped_data)

exe = EXE(
    pyz,
    a.scripts,
    [],
    exclude_binaries=True,
    name='ATP_UI',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    console=True,
)

coll = COLLECT(
    exe,
    a.binaries,
    a.zipfiles,
    a.datas,
    strip=False,
    upx=True,
    name='ATP_UI',
)
```

### 啟動器腳本

#### Windows (launcher.py)
```python
import os
import sys
import subprocess
import webbrowser
import time
from pathlib import Path

def main():
    print("🚆 啟動 ATP_Re 系統...")
    
    # 取得應用程式目錄
    if getattr(sys, 'frozen', False):
        app_dir = Path(sys._MEIPASS)
    else:
        app_dir = Path(__file__).parent
    
    # 啟動 API
    api_exe = app_dir / 'ATP_API' / 'ATP_API.exe'
    api_process = subprocess.Popen([str(api_exe)])
    
    # 等待 API 啟動
    time.sleep(3)
    
    # 啟動 UI
    ui_exe = app_dir / 'ATP_UI' / 'ATP_UI.exe'
    ui_process = subprocess.Popen([str(ui_exe)])
    
    # 等待 UI 啟動
    time.sleep(5)
    
    # 開啟瀏覽器
    webbrowser.open('http://localhost:8501')
    
    print("✅ 系統啟動完成！")
    print("📊 Web UI: http://localhost:8501")
    print("🔧 API 文檔: http://localhost:8000/docs")
    
    # 等待使用者關閉
    input("按 Enter 鍵關閉系統...")
    
    # 終止程序
    api_process.terminate()
    ui_process.terminate()

if __name__ == '__main__':
    main()
```

## 安裝流程規劃 (Installation Process)

### Windows 安裝

```
ATP_Re_v1.0.0_Windows.zip
├── ATP_Re.exe              # 啟動器
├── ATP_API/                # API 後端
├── ATP_UI/                 # Streamlit UI
├── data/                   # 資料目錄
│   └── atp_re.db          # SQLite 資料庫
├── config/                 # 配置檔案
│   └── settings.yaml
├── logs/                   # 日誌目錄
├── README.txt             # 說明文件
└── LICENSE.txt            # 授權資訊
```

**安裝步驟**:
1. 下載 `ATP_Re_v1.0.0_Windows.zip`
2. 解壓縮至任意目錄（建議：`C:\Program Files\ATP_Re`）
3. 執行 `ATP_Re.exe` 啟動系統
4. 系統將自動開啟瀏覽器

### macOS 安裝

```
ATP_Re_v1.0.0_macOS.dmg
└── ATP_Re.app              # macOS 應用程式包
    └── Contents/
        ├── MacOS/
        │   └── ATP_Re      # 啟動器
        ├── Resources/
        │   ├── ATP_API/    # API 後端
        │   ├── ATP_UI/     # Streamlit UI
        │   ├── data/       # 資料目錄
        │   ├── config/     # 配置檔案
        │   └── logs/       # 日誌目錄
        └── Info.plist      # 應用程式資訊
```

**安裝步驟**:
1. 下載 `ATP_Re_v1.0.0_macOS.dmg`
2. 雙擊掛載 DMG 檔案
3. 拖曳 `ATP_Re.app` 至「應用程式」資料夾
4. 首次執行需右鍵點擊 → 開啟（macOS 安全性設定）

## 相依套件處理 (Dependency Management)

### 核心依賴清單 (Core Dependencies)

```requirements-standalone.txt
# Web Framework
fastapi==0.104.1
uvicorn[standard]==0.24.0
streamlit==1.28.2

# Data Processing
pandas==2.1.3
numpy==1.26.2

# Visualization
plotly==5.18.0
altair==5.1.2

# Database (SQLite - Python內建)
sqlalchemy==2.0.23

# Utilities
python-dotenv==1.0.0
pydantic==2.5.0
pydantic-settings==2.1.0

# PyInstaller
pyinstaller==6.3.0
```

### 排除的依賴 (Excluded Dependencies)

```
# 不需要的依賴（用於單機版）
psycopg2-binary      # PostgreSQL 驅動
pymssql              # MSSQL 驅動
redis                # Redis 快取
prometheus-client    # 監控
```

## 授權需求分析 (Licensing Requirements)

### 開源軟體授權 (Open Source Licenses)

#### 主要依賴授權
- **Python**: PSF License (允許商業使用)
- **FastAPI**: MIT License (允許商業使用)
- **Streamlit**: Apache License 2.0 (允許商業使用)
- **Pandas**: BSD License (允許商業使用)
- **NumPy**: BSD License (允許商業使用)
- **Plotly**: MIT License (允許商業使用)
- **SQLAlchemy**: MIT License (允許商業使用)

#### PyInstaller 授權
- **License**: GPL with exception
- **允許**: 打包的應用程式不受 GPL 約束
- **要求**: 需保留 PyInstaller 版權聲明

### 應用程式授權建議

```txt
ATP_Re - Automatic Train Protection Record Analysis System
Copyright (c) 2024 ATP_Re Contributors

本軟體基於以下開源軟體建構：
- Python (PSF License)
- FastAPI (MIT License)
- Streamlit (Apache License 2.0)
- 其他依賴套件（詳見 LICENSES/ 目錄）

[在此加入您的授權條款]
```

## 效能測試計畫 (Performance Testing Plan)

### 測試環境 (Test Environments)

#### Windows
- **作業系統**: Windows 10/11 (64-bit)
- **CPU**: Intel i5 或以上
- **記憶體**: 8GB RAM
- **儲存空間**: 1GB 可用空間

#### macOS
- **作業系統**: macOS 11 Big Sur 或以上
- **CPU**: Intel 或 Apple Silicon (M1/M2)
- **記憶體**: 8GB RAM
- **儲存空間**: 1GB 可用空間

### 測試項目 (Test Items)

#### 1. 功能測試 (Functional Tests)
- [ ] 系統啟動測試
- [ ] API 端點測試
- [ ] UI 介面測試
- [ ] 資料匯入測試
- [ ] 資料分析測試
- [ ] 報表生成測試
- [ ] 資料匯出測試

#### 2. 效能測試 (Performance Tests)
- [ ] 啟動時間（目標：< 10 秒）
- [ ] 記憶體使用（目標：< 500MB 待機）
- [ ] CPU 使用率（目標：< 5% 待機）
- [ ] 資料處理速度（1000 筆記錄 < 1 秒）
- [ ] 大檔案處理（100MB < 30 秒）

#### 3. 穩定性測試 (Stability Tests)
- [ ] 長時間運行測試（8 小時）
- [ ] 重複啟動關閉測試（100 次）
- [ ] 異常處理測試
- [ ] 記憶體洩漏測試

#### 4. 相容性測試 (Compatibility Tests)
- [ ] Windows 10 (21H2)
- [ ] Windows 11 (22H2)
- [ ] macOS 11 Big Sur
- [ ] macOS 12 Monterey
- [ ] macOS 13 Ventura
- [ ] macOS 14 Sonoma

### 測試腳本 (Test Scripts)

```python
# tests/standalone/test_startup.py
import subprocess
import time
import requests
import pytest

def test_system_startup():
    """測試系統啟動"""
    # 啟動應用程式
    start_time = time.time()
    process = subprocess.Popen(['./ATP_Re.exe'])
    
    # 等待啟動
    time.sleep(10)
    
    # 檢查 API 是否可用
    response = requests.get('http://localhost:8000/health')
    assert response.status_code == 200
    
    # 檢查 UI 是否可用
    response = requests.get('http://localhost:8501')
    assert response.status_code == 200
    
    # 檢查啟動時間
    startup_time = time.time() - start_time
    assert startup_time < 10, f"啟動時間過長: {startup_time}秒"
    
    # 清理
    process.terminate()
```

## 建置流程 (Build Process)

### Windows 建置

```batch
REM build_windows.bat
@echo off
echo 建置 ATP_Re Windows 版本...

REM 安裝依賴
pip install -r requirements-standalone.txt

REM 建置 API
pyinstaller api_backend.spec --clean

REM 建置 UI
pyinstaller streamlit_ui.spec --clean

REM 建置啟動器
pyinstaller launcher.spec --clean

REM 建立發佈目錄
mkdir dist\ATP_Re_Windows
xcopy /E /I dist\ATP_API dist\ATP_Re_Windows\ATP_API
xcopy /E /I dist\ATP_UI dist\ATP_Re_Windows\ATP_UI
copy dist\ATP_Re.exe dist\ATP_Re_Windows\

REM 複製資料目錄
mkdir dist\ATP_Re_Windows\data
mkdir dist\ATP_Re_Windows\config
mkdir dist\ATP_Re_Windows\logs

REM 複製文檔
copy README.txt dist\ATP_Re_Windows\
copy LICENSE.txt dist\ATP_Re_Windows\

REM 打包
cd dist
7z a -tzip ATP_Re_v1.0.0_Windows.zip ATP_Re_Windows\*
cd ..

echo 建置完成！
```

### macOS 建置

```bash
#!/bin/bash
# build_macos.sh
echo "建置 ATP_Re macOS 版本..."

# 安裝依賴
pip install -r requirements-standalone.txt

# 建置 API
pyinstaller api_backend.spec --clean

# 建置 UI
pyinstaller streamlit_ui.spec --clean

# 建置啟動器
pyinstaller launcher_macos.spec --clean --windowed

# 建立 .app 結構
mkdir -p dist/ATP_Re.app/Contents/{MacOS,Resources}

# 複製執行檔
cp dist/ATP_Re dist/ATP_Re.app/Contents/MacOS/

# 複製資源
cp -r dist/ATP_API dist/ATP_Re.app/Contents/Resources/
cp -r dist/ATP_UI dist/ATP_Re.app/Contents/Resources/

# 建立資料目錄
mkdir -p dist/ATP_Re.app/Contents/Resources/{data,config,logs}

# 複製 Info.plist
cp Info.plist dist/ATP_Re.app/Contents/

# 複製圖示
cp icon.icns dist/ATP_Re.app/Contents/Resources/

# 建立 DMG
hdiutil create -volname "ATP_Re" -srcfolder dist/ATP_Re.app \
    -ov -format UDZO dist/ATP_Re_v1.0.0_macOS.dmg

echo "建置完成！"
```

## 部署檢查清單 (Deployment Checklist)

### 建置前 (Pre-Build)
- [ ] 更新版本號
- [ ] 更新相依套件
- [ ] 執行所有測試
- [ ] 更新文檔
- [ ] 準備授權檔案

### Windows 部署
- [ ] 在 Windows 10 上建置
- [ ] 在 Windows 11 上測試
- [ ] 檢查防毒軟體誤報
- [ ] 測試安裝流程
- [ ] 驗證所有功能
- [ ] 產生校驗碼 (SHA256)

### macOS 部署
- [ ] 在 macOS 上建置
- [ ] 程式碼簽章（如需要）
- [ ] 公證 (Notarization)（如需要）
- [ ] 測試 Intel 和 Apple Silicon
- [ ] 測試安裝流程
- [ ] 驗證所有功能
- [ ] 產生校驗碼 (SHA256)

### 文檔
- [ ] 更新 README
- [ ] 更新安裝指南
- [ ] 更新使用手冊
- [ ] 更新 FAQ
- [ ] 準備版本說明

### 發佈
- [ ] 上傳至發佈平台
- [ ] 更新下載連結
- [ ] 發佈公告
- [ ] 更新網站

## 預期成果 (Expected Deliverables)

### 軟體交付物 (Software Deliverables)

1. **Windows 版本**
   - `ATP_Re_v1.0.0_Windows.zip` (約 150-200MB)
   - SHA256 校驗檔

2. **macOS 版本**
   - `ATP_Re_v1.0.0_macOS.dmg` (約 150-200MB)
   - SHA256 校驗檔

### 文檔交付物 (Documentation Deliverables)

1. **STANDALONE_INSTALLATION_GUIDE.md** - 安裝指南
2. **STANDALONE_USER_MANUAL.md** - 使用手冊
3. **STANDALONE_FAQ.md** - 常見問題
4. **STANDALONE_TROUBLESHOOTING.md** - 疑難排解

### 測試報告 (Test Reports)

1. **功能測試報告**
2. **效能測試報告**
3. **相容性測試報告**

## 時程規劃 (Timeline)

### Phase 1: 準備階段 (1 週)
- 建立打包配置
- 資料庫適配器實作
- 啟動器腳本開發

### Phase 2: 建置階段 (1 週)
- Windows 版本建置與測試
- macOS 版本建置與測試
- 問題修復

### Phase 3: 測試階段 (1 週)
- 功能測試
- 效能測試
- 相容性測試

### Phase 4: 文檔階段 (3 天)
- 撰寫安裝指南
- 撰寫使用手冊
- 準備 FAQ

### Phase 5: 發佈階段 (2 天)
- 最終測試
- 打包發佈
- 公告推廣

**總計**: 約 3-4 週

## 維護計畫 (Maintenance Plan)

### 版本更新策略
- **主要版本 (Major)**: 重大功能更新、架構變更
- **次要版本 (Minor)**: 新功能、改進
- **修訂版本 (Patch)**: 錯誤修復、安全更新

### 支援計畫
- 提供線上文檔
- GitHub Issues 追蹤問題
- 定期發佈更新

## 風險評估 (Risk Assessment)

### 技術風險

| 風險 | 影響 | 機率 | 緩解措施 |
|------|------|------|----------|
| 打包失敗 | 高 | 中 | 充分測試、準備備用方案 |
| 相容性問題 | 中 | 高 | 多平台測試 |
| 效能問題 | 中 | 低 | 效能測試、優化 |
| 資料庫遷移 | 高 | 中 | 完整測試、提供遷移工具 |

### 非技術風險

| 風險 | 影響 | 機率 | 緩解措施 |
|------|------|------|----------|
| 授權問題 | 高 | 低 | 仔細審查所有依賴授權 |
| 使用者接受度 | 中 | 中 | 詳細文檔、簡化安裝 |
| 維護負擔 | 中 | 高 | 自動化建置、完善文檔 |

## 結論 (Conclusion)

本規劃提供了完整的單機版實作路徑，涵蓋：
- ✅ 系統架構適配
- ✅ 打包工具與配置
- ✅ 跨平台部署方案
- ✅ 測試驗證計畫
- ✅ 文檔與維護策略

透過 PyInstaller 打包技術，結合 SQLite 輕量級資料庫，可以實現完整的單機版 ATP 分析系統，讓使用者在 Windows 和 macOS 上輕鬆使用，無需複雜的伺服器設定。
