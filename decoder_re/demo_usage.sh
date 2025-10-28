#!/bin/bash
# 
# 示範如何使用 RU 和 MMI 檔案解碼列表工具
# Demonstration script for RU and MMI file decode list tools
#

echo "=========================================="
echo "RU檔與MMI檔解碼列表工具 - 示範腳本"
echo "RU and MMI File Decode List Tools - Demo"
echo "=========================================="
echo ""

# 檢查 Java 是否安裝
if ! command -v javac &> /dev/null; then
    echo "錯誤：找不到 Java 編譯器 (javac)"
    echo "Error: Java compiler (javac) not found"
    echo "請先安裝 Java JDK"
    echo "Please install Java JDK first"
    exit 1
fi

echo "步驟 1: 編譯工具"
echo "Step 1: Compiling tools"
echo ""

# 編譯工具
cd "$(dirname "$0")"
javac RUFileDecodeListTool.java
javac MMIFileDecodeListTool.java

if [ $? -eq 0 ]; then
    echo "✓ 編譯成功"
    echo "✓ Compilation successful"
else
    echo "✗ 編譯失敗"
    echo "✗ Compilation failed"
    exit 1
fi

echo ""
echo "=========================================="
echo "示範 1: RU檔案解碼"
echo "Demo 1: RU File Decoding"
echo "=========================================="
echo ""

echo "使用方式："
echo "Usage:"
echo "  java decoder.RUFileDecodeListTool <RU檔案路徑>"
echo "  java decoder.RUFileDecodeListTool <path/to/ru/file>"
echo ""

# 如果有提供測試檔案路徑作為第一個參數
if [ -n "$1" ] && [ -f "$1" ]; then
    echo "解碼檔案：$1"
    echo "Decoding file: $1"
    echo ""
    java decoder.RUFileDecodeListTool "$1"
else
    echo "提示：請提供 RU 檔案路徑作為參數來測試"
    echo "Hint: Provide RU file path as argument to test"
    echo "範例：./demo_usage.sh /path/to/sample.ru"
    echo "Example: ./demo_usage.sh /path/to/sample.ru"
fi

echo ""
echo "=========================================="
echo "示範 2: 單一MMI檔案解碼"
echo "Demo 2: Single MMI File Decoding"
echo "=========================================="
echo ""

echo "使用方式："
echo "Usage:"
echo "  java decoder.MMIFileDecodeListTool <MMI檔案路徑>"
echo "  java decoder.MMIFileDecodeListTool <path/to/mmi/file>"
echo ""

# 如果有提供測試檔案路徑作為第二個參數
if [ -n "$2" ] && [ -f "$2" ]; then
    echo "解碼檔案：$2"
    echo "Decoding file: $2"
    echo ""
    java decoder.MMIFileDecodeListTool "$2"
else
    echo "提示：請提供 MMI 檔案路徑作為第二個參數來測試"
    echo "Hint: Provide MMI file path as 2nd argument to test"
    echo "範例：./demo_usage.sh ru_file.ru mmi_file.mmi"
    echo "Example: ./demo_usage.sh ru_file.ru mmi_file.mmi"
fi

echo ""
echo "=========================================="
echo "示範 3: MMI目錄模式解碼（Issue需求）"
echo "Demo 3: MMI Directory Mode (Issue Requirement)"
echo "=========================================="
echo ""

echo "使用方式（符合 issue 要求：測試區選一個資料夾，內含2個檔案以上）："
echo "Usage (meets issue requirement: select a test folder with 2+ files):"
echo "  java decoder.MMIFileDecodeListTool <目錄路徑>"
echo "  java decoder.MMIFileDecodeListTool <directory/path>"
echo ""

# 如果有提供測試目錄路徑作為第三個參數
if [ -n "$3" ] && [ -d "$3" ]; then
    echo "解碼目錄：$3"
    echo "Decoding directory: $3"
    echo ""
    java decoder.MMIFileDecodeListTool "$3"
else
    echo "提示：請提供包含MMI檔案的目錄路徑作為第三個參數來測試"
    echo "Hint: Provide directory path containing MMI files as 3rd argument"
    echo "範例：./demo_usage.sh ru_file.ru mmi_file.mmi /path/to/mmi/folder"
    echo "Example: ./demo_usage.sh ru_file.ru mmi_file.mmi /path/to/mmi/folder"
fi

echo ""
echo "=========================================="
echo "說明"
echo "Description"
echo "=========================================="
echo ""
echo "這些工具實現了以下 issue 需求："
echo "These tools implement the following issue requirements:"
echo ""
echo "✓ 列出RU檔解碼後的數據"
echo "✓ List decoded data from RU files"
echo ""
echo "✓ 列出MMI檔解碼後的數據"
echo "✓ List decoded data from MMI files"
echo ""
echo "✓ MMI檔選擇：測試區選一個資料夾，內含2個檔案以上"
echo "✓ MMI file selection: Choose a test folder with 2+ files"
echo ""
echo "詳細說明請參閱：README_DecodeListTools.md"
echo "For detailed documentation, see: README_DecodeListTools.md"
echo ""
