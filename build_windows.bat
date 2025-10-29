@echo off
REM ============================================
REM ATP_Re Windows Build Script
REM ATP_Re Windows 建置腳本
REM ============================================

echo ============================================
echo Building ATP_Re Standalone for Windows
echo 建置 ATP_Re Windows 單機版
echo ============================================
echo.

REM 檢查 Python
python --version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Python is not installed or not in PATH
    echo [錯誤] 未安裝 Python 或 Python 不在 PATH 中
    exit /b 1
)

echo [1/6] Checking Python version...
python --version

REM 安裝/更新依賴
echo.
echo [2/6] Installing dependencies...
echo [2/6] 安裝依賴套件...
pip install -r requirements-standalone.txt
if errorlevel 1 (
    echo [ERROR] Failed to install dependencies
    echo [錯誤] 安裝依賴失敗
    exit /b 1
)

REM 清理舊的建置
echo.
echo [3/6] Cleaning old builds...
echo [3/6] 清理舊的建置...
if exist build rmdir /s /q build
if exist dist rmdir /s /q dist

REM 建置 API 後端
echo.
echo [4/6] Building API Backend...
echo [4/6] 建置 API 後端...
pyinstaller api_backend.spec --clean --noconfirm
if errorlevel 1 (
    echo [ERROR] Failed to build API backend
    echo [錯誤] 建置 API 後端失敗
    exit /b 1
)

REM 建置 Streamlit UI
echo.
echo [5/6] Building Streamlit UI...
echo [5/6] 建置 Streamlit UI...
pyinstaller streamlit_ui.spec --clean --noconfirm
if errorlevel 1 (
    echo [ERROR] Failed to build Streamlit UI
    echo [錯誤] 建置 Streamlit UI 失敗
    exit /b 1
)

REM 建置啟動器
echo.
echo [6/6] Building Launcher...
echo [6/6] 建置啟動器...
pyinstaller launcher.spec --clean --noconfirm
if errorlevel 1 (
    echo [ERROR] Failed to build launcher
    echo [錯誤] 建置啟動器失敗
    exit /b 1
)

REM 建立發佈目錄結構
echo.
echo Creating distribution package...
echo 建立發佈套件...

set DIST_DIR=dist\ATP_Re_Windows
if exist "%DIST_DIR%" rmdir /s /q "%DIST_DIR%"
mkdir "%DIST_DIR%"

REM 複製執行檔
echo Copying executables...
xcopy /E /I /Y dist\ATP_API "%DIST_DIR%\ATP_API"
xcopy /E /I /Y dist\ATP_UI "%DIST_DIR%\ATP_UI"
copy /Y dist\ATP_Re.exe "%DIST_DIR%\"

REM 建立必要的目錄
echo Creating directories...
mkdir "%DIST_DIR%\data"
mkdir "%DIST_DIR%\config"
mkdir "%DIST_DIR%\logs"
mkdir "%DIST_DIR%\uploads"
mkdir "%DIST_DIR%\reports"

REM 複製配置檔案範本
if exist .env.example copy .env.example "%DIST_DIR%\config\.env.example"

REM 建立 README
echo Creating README...
(
echo ATP_Re - Automatic Train Protection Record Analysis System
echo.
echo Installation and Usage:
echo 1. Extract this package to a folder
echo 2. Double-click ATP_Re.exe to start the system
echo 3. The system will automatically open in your default browser
echo.
echo Web UI: http://localhost:8501
echo API Documentation: http://localhost:8000/docs
echo.
echo For more information, please visit:
echo https://github.com/Lawliet0813/ATP_re
) > "%DIST_DIR%\README.txt"

REM 建立中文說明
(
echo ATP_Re - 自動列車防護行車紀錄分析系統
echo.
echo 安裝與使用:
echo 1. 解壓縮此套件到任意資料夾
echo 2. 雙擊 ATP_Re.exe 啟動系統
echo 3. 系統將自動在預設瀏覽器中開啟
echo.
echo Web 介面: http://localhost:8501
echo API 文檔: http://localhost:8000/docs
echo.
echo 更多資訊請訪問:
echo https://github.com/Lawliet0813/ATP_re
) > "%DIST_DIR%\README_zh.txt"

REM 計算版本號 (從 git 或使用預設值)
for /f "tokens=*" %%a in ('git describe --tags --always 2^>nul') do set VERSION=%%a
if "%VERSION%"=="" set VERSION=v1.0.0

REM 壓縮打包
echo.
echo Packaging...
echo 正在打包...
cd dist
if exist ATP_Re_%VERSION%_Windows.zip del ATP_Re_%VERSION%_Windows.zip

REM 使用 PowerShell 壓縮 (Windows 內建)
powershell -Command "Compress-Archive -Path 'ATP_Re_Windows\*' -DestinationPath 'ATP_Re_%VERSION%_Windows.zip' -Force"

REM 計算 SHA256
echo Calculating SHA256...
powershell -Command "Get-FileHash 'ATP_Re_%VERSION%_Windows.zip' -Algorithm SHA256 | Select-Object Hash | Format-List" > ATP_Re_%VERSION%_Windows.zip.sha256

cd ..

echo.
echo ============================================
echo Build completed successfully!
echo 建置完成！
echo ============================================
echo.
echo Output files:
echo   dist\ATP_Re_%VERSION%_Windows.zip
echo   dist\ATP_Re_%VERSION%_Windows.zip.sha256
echo.
echo You can now distribute these files.
echo 現在可以發佈這些檔案了。
echo ============================================

pause
