#!/bin/bash
# ============================================
# ATP_Re macOS Build Script
# ATP_Re macOS 建置腳本
# ============================================

set -e  # Exit on error

echo "============================================"
echo "Building ATP_Re Standalone for macOS"
echo "建置 ATP_Re macOS 單機版"
echo "============================================"
echo ""

# Check Python
if ! command -v python3 &> /dev/null; then
    echo "[ERROR] Python 3 is not installed"
    echo "[錯誤] 未安裝 Python 3"
    exit 1
fi

echo "[1/7] Checking Python version..."
python3 --version

# Install/Update dependencies
echo ""
echo "[2/7] Installing dependencies..."
echo "[2/7] 安裝依賴套件..."
pip3 install -r requirements-standalone.txt

# Clean old builds
echo ""
echo "[3/7] Cleaning old builds..."
echo "[3/7] 清理舊的建置..."
rm -rf build dist

# Build API Backend
echo ""
echo "[4/7] Building API Backend..."
echo "[4/7] 建置 API 後端..."
pyinstaller api_backend.spec --clean --noconfirm

# Build Streamlit UI
echo ""
echo "[5/7] Building Streamlit UI..."
echo "[5/7] 建置 Streamlit UI..."
pyinstaller streamlit_ui.spec --clean --noconfirm

# Build Launcher
echo ""
echo "[6/7] Building Launcher..."
echo "[6/7] 建置啟動器..."
pyinstaller launcher.spec --clean --noconfirm

# Create .app bundle
echo ""
echo "[7/7] Creating .app bundle..."
echo "[7/7] 建立 .app 應用程式包..."

APP_NAME="ATP_Re"
APP_DIR="dist/${APP_NAME}.app"
CONTENTS_DIR="${APP_DIR}/Contents"
MACOS_DIR="${CONTENTS_DIR}/MacOS"
RESOURCES_DIR="${CONTENTS_DIR}/Resources"

# Create directory structure
mkdir -p "${MACOS_DIR}"
mkdir -p "${RESOURCES_DIR}"

# Copy launcher
cp "dist/${APP_NAME}" "${MACOS_DIR}/"
chmod +x "${MACOS_DIR}/${APP_NAME}"

# Copy API and UI
cp -r "dist/ATP_API" "${RESOURCES_DIR}/"
cp -r "dist/ATP_UI" "${RESOURCES_DIR}/"

# Create necessary directories
mkdir -p "${RESOURCES_DIR}/data"
mkdir -p "${RESOURCES_DIR}/config"
mkdir -p "${RESOURCES_DIR}/logs"
mkdir -p "${RESOURCES_DIR}/uploads"
mkdir -p "${RESOURCES_DIR}/reports"

# Copy config example
if [ -f ".env.example" ]; then
    cp ".env.example" "${RESOURCES_DIR}/config/.env.example"
fi

# Create Info.plist
cat > "${CONTENTS_DIR}/Info.plist" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>CFBundleDevelopmentRegion</key>
    <string>en</string>
    <key>CFBundleDisplayName</key>
    <string>ATP_Re</string>
    <key>CFBundleExecutable</key>
    <string>${APP_NAME}</string>
    <key>CFBundleIdentifier</key>
    <string>com.atpre.app</string>
    <key>CFBundleInfoDictionaryVersion</key>
    <string>6.0</string>
    <key>CFBundleName</key>
    <string>${APP_NAME}</string>
    <key>CFBundlePackageType</key>
    <string>APPL</string>
    <key>CFBundleShortVersionString</key>
    <string>1.0.0</string>
    <key>CFBundleVersion</key>
    <string>1</string>
    <key>LSMinimumSystemVersion</key>
    <string>10.13</string>
    <key>NSHighResolutionCapable</key>
    <true/>
    <key>NSAppleScriptEnabled</key>
    <false/>
    <key>CFBundleDocumentTypes</key>
    <array>
        <dict>
            <key>CFBundleTypeName</key>
            <string>ATP Data File</string>
            <key>CFBundleTypeExtensions</key>
            <array>
                <string>dat</string>
                <string>log</string>
            </array>
            <key>CFBundleTypeRole</key>
            <string>Viewer</string>
        </dict>
    </array>
</dict>
</plist>
EOF

# Create README files
cat > "${APP_DIR}/../README.txt" << EOF
ATP_Re - Automatic Train Protection Record Analysis System

Installation and Usage:
1. Mount the DMG file
2. Drag ATP_Re.app to Applications folder
3. First launch: Right-click ATP_Re.app -> Open (for macOS security)
4. The system will automatically open in your default browser

Web UI: http://localhost:8501
API Documentation: http://localhost:8000/docs

For more information, please visit:
https://github.com/Lawliet0813/ATP_re
EOF

cat > "${APP_DIR}/../README_zh.txt" << EOF
ATP_Re - 自動列車防護行車紀錄分析系統

安裝與使用:
1. 掛載 DMG 檔案
2. 拖曳 ATP_Re.app 到「應用程式」資料夾
3. 首次啟動: 右鍵點擊 ATP_Re.app -> 開啟（macOS 安全性設定）
4. 系統將自動在預設瀏覽器中開啟

Web 介面: http://localhost:8501
API 文檔: http://localhost:8000/docs

更多資訊請訪問:
https://github.com/Lawliet0813/ATP_re
EOF

# Get version from git or use default
VERSION=$(git describe --tags --always 2>/dev/null || echo "v1.0.0")

# Create DMG
echo ""
echo "Creating DMG..."
echo "建立 DMG..."

DMG_NAME="ATP_Re_${VERSION}_macOS.dmg"
DMG_PATH="dist/${DMG_NAME}"

# Remove old DMG if exists
rm -f "${DMG_PATH}"

# Create DMG
hdiutil create -volname "ATP_Re" \
    -srcfolder "${APP_DIR}" \
    -ov -format UDZO \
    "${DMG_PATH}"

# Calculate SHA256
echo ""
echo "Calculating SHA256..."
shasum -a 256 "${DMG_PATH}" > "${DMG_PATH}.sha256"

echo ""
echo "============================================"
echo "Build completed successfully!"
echo "建置完成！"
echo "============================================"
echo ""
echo "Output files:"
echo "  ${DMG_PATH}"
echo "  ${DMG_PATH}.sha256"
echo ""
echo "You can now distribute these files."
echo "現在可以發佈這些檔案了。"
echo ""
echo "Note: For distribution, you may want to:"
echo "  1. Code sign the app: codesign --deep --force --sign <identity> ${APP_DIR}"
echo "  2. Notarize the app: xcrun altool --notarize-app ..."
echo "============================================"
