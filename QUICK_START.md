# ATP 專案快速入門指南

本文件提供給想要快速了解並開始參與 ATP 專案的開發者。

---

## 📖 專案簡介

ATP_re 是一個鐵路自動列車防護（Automatic Train Protection）系統的資料分析與管理工作站軟體。

- **原始系統：** Java + Swing (2004-2012)
- **規劃重構：** Python + FastAPI + Web UI
- **程式碼規模：** 514 個 Java 檔案，約 50,000 行程式碼

---

## 🎯 快速開始

### 第一步：閱讀核心文件

按照以下順序閱讀專案文件：

1. **[ROADMAP.md](./ROADMAP.md)** - 完整的開發路線圖（必讀）
2. **[00_Project_Summary.md](./00_Project_Summary.md)** - 專案總覽
3. **[ATP 行車紀錄分析系統 - 完整技術規格書 v2 0.md](./ATP%20行車紀錄分析系統%20-%20完整技術規格書%20v2%200%2029855714413f81b1b980eedb85bea559.md)** - 詳細技術規格

### 第二步：了解系統架構

閱讀各模組分析文件：

- **核心模組（優先）：**
  - [decoder_re_Analysis.md](./decoder_re_Analysis.md) - 進階解碼引擎
  - [decode_re_Analysis.md](./decode_re_Analysis.md) - 基礎解碼層
  - [core_re_Analysis.md](./core_re_Analysis.md) - 核心資料模型

- **其他模組：**
  - [ui_re_Analysis.md](./ui_re_Analysis.md) - 使用者介面
  - [drawGraphics_re_Analysis.md](./drawGraphics_re_Analysis.md) - 圖形繪製
  - [Tools_re_Analysis.md](./Tools_re_Analysis.md) - 工具集
  - [connect_re_Analysis.md](./connect_re_Analysis.md) - 資料存取

### 第三步：了解最近更新

- [RU_DECODER_UPDATE_NOTES.md](./RU_DECODER_UPDATE_NOTES.md) - RU 解碼器重要修正

---

## 🚀 參與開發

### 如果你要參與 Phase 1：程式碼品質改善

**目標：** 改善 Java 程式碼的可讀性

**你需要：**
- Java 開發經驗
- IntelliJ IDEA
- 了解反編譯程式碼的特性

**開始任務：**
1. 選擇一個核心 Java 檔案
2. 識別混淆的變數名稱
3. 根據上下文重新命名
4. 添加必要的註解
5. 提交 Pull Request

**參考：** ROADMAP.md > Phase 1

### 如果你要參與 Phase 2-3：Python 重構

**目標：** 實作 Python 核心解碼器

**你需要：**
- Python 3.11+
- 熟悉二進位資料處理
- struct 模組經驗
- pytest 測試框架

**開始任務：**
1. 設定 Python 開發環境
2. 閱讀 Java 原始碼了解邏輯
3. 選擇一個模組開始實作
4. 撰寫對照驗證測試
5. 確保測試通過

**參考：** ROADMAP.md > Phase 2-3

### 如果你要參與 Phase 7：Web UI 開發

**目標：** 建立現代化 Web 介面

**你需要：**
- React 或 Streamlit 經驗
- Web 前端技能
- UI/UX 基礎概念

**開始任務：**
1. 了解現有 Swing UI 的功能
2. 設計新的 UI 原型
3. 實作基礎頁面
4. 整合後端 API
5. 使用者測試

**參考：** ROADMAP.md > Phase 7

---

## 📋 目前進度

### 已完成
- ✅ 完整的 Java 程式碼分析（7份分析報告）
- ✅ RU 解碼器規格修正（符合 v1.8 規格）
- ✅ 完整技術規格書撰寫
- ✅ 18 個月路線圖規劃

### 進行中
- 🔄 Phase 1: 程式碼品質改善（準備開始）

### 待開始
- ⏳ Phase 2: Python 遷移規劃
- ⏳ Phase 3: 核心解碼器實作
- ⏳ Phase 4-8: 後續階段

---

## 🛠️ 開發環境設定

### Java 開發環境（現階段）

```bash
# 需要的工具
- JDK 8+
- IntelliJ IDEA
- Git

# 專案結構
ATP_re/
├── decoder_re/     # 進階解碼器
├── decode_re/      # 基礎解碼器
├── core_re/        # 核心模型
├── ui_re/          # 使用者介面
├── drawGraphics_re/ # 圖形繪製
├── Tools_re/       # 工具集
└── connect_re/     # 資料存取
```

### Python 開發環境（未來）

```bash
# 安裝 Python
python --version  # 需要 3.11+

# 建立虛擬環境
python -m venv venv
source venv/bin/activate  # macOS/Linux
# venv\Scripts\activate  # Windows

# 安裝相依套件（待建立）
pip install -r requirements.txt

# 執行測試
pytest tests/
```

---

## 📞 聯絡資訊

### 專案負責人
- **GitHub：** @Lawliet0813
- **專案：** https://github.com/Lawliet0813/ATP_re

### 如何提問
1. 建立 GitHub Issue
2. 使用適當的標籤（bug / feature / question）
3. 提供詳細的描述和上下文

### 如何貢獻
1. Fork 專案
2. 建立功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交變更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 建立 Pull Request

---

## 📚 學習資源

### ATP 系統相關
- ETCS（歐洲列車控制系統）規範
- 鐵路信號系統基礎
- Balise 應答器原理

### 技術相關
- **Java：** Effective Java, Clean Code
- **Python：** Python Cookbook, Fluent Python
- **二進位處理：** struct 模組文件
- **測試：** pytest 文件

---

## ⚡ 常見問題

### Q: 我沒有鐵路背景，可以參與嗎？
A: 可以！只要有軟體開發經驗，閱讀技術規格書和分析文件即可理解系統邏輯。

### Q: Java 程式碼為何有這麼多混淆的變數名稱？
A: 這些是反編譯後的程式碼，原始變數名稱已遺失。Phase 1 的目標就是改善這點。

### Q: Python 重構會改變解碼邏輯嗎？
A: 不會！所有解碼邏輯都必須 100% 對照 Java 系統，並通過驗證測試。

### Q: 為什麼要從 Java 遷移到 Python？
A: 
- 降低程式碼複雜度（預計減少 80% 程式碼量）
- 使用現代化框架和工具
- 更好的資料處理能力（pandas, numpy）
- 更容易維護和擴展

### Q: 可以只參與特定階段嗎？
A: 當然！每個階段都是相對獨立的，可以根據你的專長選擇參與。

---

## 🎯 下一步建議

### 對於新加入的開發者：
1. ⭐ Star 這個專案
2. 📖 閱讀 ROADMAP.md 了解全貌
3. 🔍 選擇一個感興趣的階段
4. 💬 在 Issue 中提出問題或表達參與意願
5. 🚀 開始貢獻！

### 對於專案負責人：
1. 📅 規劃 Phase 1 的詳細時程
2. 🏷️ 建立 GitHub Issues 追蹤各個任務
3. 📝 撰寫 TECH_STACK.md 確定技術選型
4. 🧪 準備測試資料和參考輸出
5. 👥 尋找協作者

---

**歡迎加入 ATP 專案！讓我們一起打造更好的系統！** 🎉
