# ATP_re - 列車自動防護系統分析平台

[![Project Status](https://img.shields.io/badge/status-planning-yellow.svg)](https://github.com/Lawliet0813/ATP_re)
[![Java](https://img.shields.io/badge/java-8+-blue.svg)](https://www.java.com/)
[![Python](https://img.shields.io/badge/python-3.11+-green.svg)](https://www.python.org/)
[![License](https://img.shields.io/badge/license-proprietary-red.svg)]()

ATP (Automatic Train Protection) 行車紀錄分析系統 - 用於解析、分析和視覺化鐵路 ATP 系統產生的行車記錄資料。

---

## 🎯 專案概述

ATP_re 是一個完整的鐵路 ATP 系統資料分析與管理工作站軟體，提供：

- 📁 **資料收集與解碼**：解析 RU（Recording Unit）和 MMI（Man-Machine Interface）檔案
- 📊 **多維度分析**：速度曲線、事件分析、停車精準度、組員考核
- 📈 **視覺化展示**：互動式圖表、時間/距離雙軸顯示、事件標記
- 📋 **報表產出**：組員考核報表、異常事件清單、統計摘要
- 🔍 **即時監控**：FTP 監控、解碼進度、系統狀態

### 專案規模

- **程式碼檔案：** 514 個 Java 檔案
- **程式碼行數：** 約 50,000 行
- **開發時間：** 2004-2012 年（8年）
- **開發廠商：** MiTAC Inc（神通電腦）

---

## 📚 文件導覽

根據你的角色選擇適合的文件：

### 🏢 管理層 / 決策者
**從這裡開始：** [EXECUTIVE_SUMMARY.md](./EXECUTIVE_SUMMARY.md)
- 投資回報分析（ROI）
- 風險評估
- 資源需求
- 時程規劃概覽
- 決策建議

### 👨‍💻 開發者 / 技術人員
**從這裡開始：** [QUICK_START.md](./QUICK_START.md)
- 快速入門指南
- 如何參與開發
- 環境設定
- 常見問題

**深入了解：** [ROADMAP.md](./ROADMAP.md)
- 完整的 18 個月開發路線圖
- 8 個階段詳細規劃
- 技術選型與架構設計
- 測試策略與品質標準

### 🔍 系統分析師 / 架構師
**從這裡開始：** [00_Project_Summary.md](./00_Project_Summary.md)
- 系統架構總覽
- 各模組功能說明
- 技術棧分析
- 改進建議

**深入了解：** 
- [ATP 行車紀錄分析系統 - 完整技術規格書 v2 0.md](./ATP%20行車紀錄分析系統%20-%20完整技術規格書%20v2%200%2029855714413f81b1b980eedb85bea559.md) - 完整技術規格
- [decoder_re_Analysis.md](./decoder_re_Analysis.md) - 解碼器分析
- [RU_DECODER_UPDATE_NOTES.md](./RU_DECODER_UPDATE_NOTES.md) - 解碼器更新說明

---

## 🚀 專案狀態

### 當前階段：規劃完成，準備開始重構

```
✅ 已完成
├─ 完整的 Java 程式碼分析（7份分析報告）
├─ RU 解碼器規格修正（符合 v1.8 規格）
├─ 完整技術規格書（Python 重構版）
└─ 18 個月路線圖規劃

🔄 進行中
└─ 等待專案批准，準備啟動 Phase 1

⏳ 待開始
├─ Phase 1: 程式碼品質改善（2-3週）
├─ Phase 2: Python 遷移規劃（1-2週）
├─ Phase 3: 核心解碼器實作（4-6週）
├─ Phase 4: 測試框架建立（2-3週）
├─ Phase 5: 資料模型與 API 設計（3-4週）
├─ Phase 6: 視覺化層（2-3週）
├─ Phase 7: Web UI 開發（3-4週）
└─ Phase 8: 部署與文件（2週）
```

### 關鍵里程碑

| 時間點 | 里程碑 | 狀態 |
|--------|--------|------|
| 2025-10-29 | 規劃階段完成 | ✅ 完成 |
| Month 1 | 程式碼品質改善完成 | ⏳ 待開始 |
| Month 3 | 核心解碼器完成 | ⏳ 待開始 |
| Month 6 | 資料層與視覺化完成 | ⏳ 待開始 |
| Month 10 | Web UI 完成 | ⏳ 待開始 |
| **Month 12** | **系統上線** | ⏳ 待開始 |
| Month 18 | 進階功能完成 | ⏳ 待開始 |

---

## 🏗️ 系統架構

### 現有架構（Java）

```
┌─────────────────────────────────────────────┐
│  ui_re (使用者介面層) - 310個檔案           │
│  Swing GUI, 視窗、面板、對話框              │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  drawGraphics_re (圖形繪製層) - 16個檔案    │
│  曲線、直方圖、故障訊息視覺化                │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  decoder_re (進階解碼層) - 34個檔案         │
│  任務佇列、BTM/VDX/RU解碼、路側協定          │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  decode_re (基礎解碼層) - 9個檔案           │
│  MMI封包解析、資料分類                       │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  core_re (核心資料模型層) - 12個檔案        │
│  ATP任務、車站、使用者等資料結構             │
└─────────────────────────────────────────────┘
```

### 目標架構（Python）

```
┌─────────────────────────────────────────────┐
│  Web UI (FastAPI + React / Streamlit)      │
│  現代化 Web 介面，跨平台存取                 │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  API Layer (FastAPI)                        │
│  RESTful API + WebSocket                    │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  Business Logic (Python)                    │
│  資料分析、事件偵測、統計計算                │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  Decoder Engine (Python + struct)          │
│  RU/MMI 解碼器、BTM 重組、ETCS 封包解析     │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│  Data Layer (SQLAlchemy + PostgreSQL)      │
│  資料模型、DAO、快取管理                     │
└─────────────────────────────────────────────┘
```

---

## 📊 為什麼要重構？

### 技術債務
- ❌ 老舊技術棧（Java Swing, 2004年）
- ❌ 程式碼可讀性差（反編譯的混淆變數）
- ❌ 缺乏自動化測試
- ❌ 維護困難，新功能開發成本高

### 重構效益
- ✅ 程式碼量減少 80-90%（50,000 → 5,000-10,000 行）
- ✅ 跨平台 Web 存取（不再限於 Windows 桌面）
- ✅ 開發速度提升 3x（現代化框架）
- ✅ 維護成本降低 60-70%
- ✅ 更強大的資料分析能力（pandas, numpy）

---

## 🛠️ 技術棧

### 現有系統
- **語言：** Java 7-8
- **GUI：** Swing
- **資料庫：** JDBC + SQL Server
- **網路：** 自訂 FTP 客戶端

### 目標系統
- **語言：** Python 3.11+
- **Web 框架：** FastAPI
- **前端：** Streamlit / React（待決定）
- **資料庫：** PostgreSQL + SQLAlchemy
- **資料處理：** pandas, numpy
- **視覺化：** Plotly, Matplotlib
- **測試：** pytest
- **部署：** Docker + Docker Compose

---

## 👥 如何參與

### 開發者

1. **閱讀文件**
   - [QUICK_START.md](./QUICK_START.md) - 快速入門
   - [ROADMAP.md](./ROADMAP.md) - 開發路線圖

2. **選擇階段**
   - Phase 1: 改善 Java 程式碼（需要 Java 經驗）
   - Phase 3: Python 解碼器（需要 Python + 二進位處理）
   - Phase 7: Web UI（需要前端經驗）

3. **開始貢獻**
   - Fork 專案
   - 選擇一個 Issue
   - 提交 Pull Request

### 管理者

1. **審閱規劃**
   - [EXECUTIVE_SUMMARY.md](./EXECUTIVE_SUMMARY.md) - 執行摘要
   - [ROADMAP.md](./ROADMAP.md) - 詳細路線圖

2. **決策批准**
   - 確認資源配置
   - 批准預算
   - 指派團隊

3. **監控進度**
   - 定期審查里程碑
   - 風險管理
   - 品質把關

---

## 📈 成功指標（KPIs）

### 技術指標
- 程式碼覆蓋率 ≥ 80%（核心模組 ≥ 95%）
- 對照驗證通過率 = 100%
- API 回應時間 < 200ms（P95）
- 解碼速度 ≥ Java 版本

### 品質指標
- Bug 密度 < 0.5 bugs/KLOC
- 程式碼重複率 < 3%
- 循環複雜度 < 10

### 業務指標
- 使用者滿意度 ≥ 4.0/5.0
- 系統可用性 ≥ 99.5%
- 平均故障修復時間 < 2 小時

---

## 📞 聯絡資訊

### 專案負責人
- **GitHub：** [@Lawliet0813](https://github.com/Lawliet0813)
- **專案：** [ATP_re](https://github.com/Lawliet0813/ATP_re)

### 如何提問
1. 建立 [GitHub Issue](https://github.com/Lawliet0813/ATP_re/issues)
2. 使用適當的標籤（bug / feature / question）
3. 提供詳細的描述和上下文

### 如何貢獻
1. Fork 專案
2. 建立功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交變更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 建立 Pull Request

---

## 📄 授權

本專案為專有軟體，版權所有。

---

## 🙏 致謝

- **原始開發團隊：** MiTAC Inc（神通電腦，2004-2012）
- **專案分析與重構規劃：** Lawliet (2025)
- **技術支援：** GitHub Copilot

---

## 📚 相關資源

### 規格文件
- ATPRU-LOGF-001 RU Log File Data Format v1.8
- Interface Specification, ATPCU - MMI 3NSS003791D0106 v3.2
- Recording Unit (RU) Requirement Specification 3NSS004830D0200 v1.2

### 技術參考
- [Python 官方文件](https://docs.python.org/3/)
- [FastAPI 文件](https://fastapi.tiangolo.com/)
- [SQLAlchemy 文件](https://docs.sqlalchemy.org/)
- [Plotly 文件](https://plotly.com/python/)

---

**Last Updated:** 2025-10-29  
**Version:** 1.0  
**Status:** Planning Complete, Ready to Start 🚀

---

<p align="center">
  <strong>讓我們一起打造更好的 ATP 分析系統！</strong>
</p>
