# ATP_Re 授權資訊
# ATP_Re Licensing Information

## 目錄 (Table of Contents)

1. [概述 (Overview)](#概述-overview)
2. [ATP_Re 應用程式授權 (Application License)](#atp_re-應用程式授權-application-license)
3. [第三方依賴套件授權 (Third-Party Dependencies)](#第三方依賴套件授權-third-party-dependencies)
4. [授權相容性 (License Compatibility)](#授權相容性-license-compatibility)
5. [商業使用指南 (Commercial Use Guide)](#商業使用指南-commercial-use-guide)
6. [重新分發指南 (Redistribution Guide)](#重新分發指南-redistribution-guide)
7. [授權聲明範本 (License Notice Template)](#授權聲明範本-license-notice-template)

---

## 概述 (Overview)

ATP_Re 是基於多個開源軟體建構的應用程式。本文檔詳細列出所有依賴套件的授權資訊，以確保合規使用。

### 授權原則

- ✅ **尊重開源**: 遵守所有依賴套件的授權條款
- ✅ **透明公開**: 完整揭露所有授權資訊
- ✅ **保持相容**: 確保授權之間的相容性
- ✅ **方便使用**: 提供清晰的使用指南

---

## ATP_Re 應用程式授權 (Application License)

### 主要授權

**ATP_Re** 本身採用 [待定義] 授權。

```
Copyright (c) 2024 ATP_Re Contributors

[在此處加入您選擇的授權條款]

建議選項:
1. MIT License - 最寬鬆
2. Apache License 2.0 - 包含專利授權
3. GPL v3 - Copyleft 授權
```

### 授權選擇考量

| 授權類型 | 優點 | 缺點 | 適用場景 |
|----------|------|------|----------|
| MIT | 最寬鬆，易於理解 | 無專利保護 | 商業友好 |
| Apache 2.0 | 包含專利授權 | 稍微複雜 | 企業使用 |
| GPL v3 | 保護開源 | 限制商業使用 | 社群專案 |

---

## 第三方依賴套件授權 (Third-Party Dependencies)

### 核心框架 (Core Frameworks)

#### 1. Python
- **版本**: 3.11+
- **授權**: Python Software Foundation License (PSF)
- **類型**: 寬鬆型開源授權
- **商業使用**: ✅ 允許
- **修改**: ✅ 允許
- **重新分發**: ✅ 允許
- **網站**: https://www.python.org/psf/license/

**授權摘要**:
```
Python 是在 PSF 授權下發佈的開源軟體。
PSF 授權與 GPL 相容，允許在商業產品中使用 Python。
```

#### 2. FastAPI
- **版本**: 0.104.1
- **授權**: MIT License
- **授權文件**: https://github.com/tiangolo/fastapi/blob/master/LICENSE
- **商業使用**: ✅ 允許
- **說明**: 最寬鬆的開源授權之一

```
MIT License

Copyright (c) 2018 Sebastián Ramírez

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
```

#### 3. Streamlit
- **版本**: 1.28.2
- **授權**: Apache License 2.0
- **授權文件**: https://github.com/streamlit/streamlit/blob/develop/LICENSE
- **商業使用**: ✅ 允許
- **專利授權**: ✅ 包含

```
Apache License 2.0

優點:
- 允許商業使用
- 提供專利授權
- 要求保留版權聲明
```

#### 4. Uvicorn
- **版本**: 0.24.0
- **授權**: BSD 3-Clause License
- **商業使用**: ✅ 允許

### 資料處理 (Data Processing)

#### 5. Pandas
- **版本**: 2.1.3
- **授權**: BSD 3-Clause License
- **授權文件**: https://github.com/pandas-dev/pandas/blob/main/LICENSE
- **商業使用**: ✅ 允許

#### 6. NumPy
- **版本**: 1.26.2
- **授權**: BSD 3-Clause License
- **授權文件**: https://github.com/numpy/numpy/blob/main/LICENSE.txt
- **商業使用**: ✅ 允許

### 視覺化 (Visualization)

#### 7. Plotly
- **版本**: 5.18.0
- **授權**: MIT License
- **授權文件**: https://github.com/plotly/plotly.py/blob/master/LICENSE.txt
- **商業使用**: ✅ 允許

#### 8. Altair
- **版本**: 5.1.2
- **授權**: BSD 3-Clause License
- **商業使用**: ✅ 允許

### 資料庫 (Database)

#### 9. SQLAlchemy
- **版本**: 2.0.23
- **授權**: MIT License
- **授權文件**: https://github.com/sqlalchemy/sqlalchemy/blob/main/LICENSE
- **商業使用**: ✅ 允許

**注意**: SQLite 是公有領域（Public Domain），無授權限制。

### 工具庫 (Utilities)

#### 10. Pydantic
- **版本**: 2.5.0
- **授權**: MIT License
- **商業使用**: ✅ 允許

#### 11. python-dotenv
- **版本**: 1.0.0
- **授權**: BSD 3-Clause License
- **商業使用**: ✅ 允許

### 打包工具 (Packaging)

#### 12. PyInstaller
- **版本**: 6.3.0
- **授權**: GPL v2 with exception
- **特殊說明**: 
  - PyInstaller 本身是 GPL 授權
  - **但**打包出的應用程式**不受** GPL 約束
  - 這是 PyInstaller 的授權例外條款

**PyInstaller 例外條款**:
```
PyInstaller is licensed under the GPL v2 license.

HOWEVER, as a special exception, the copyright holders of PyInstaller 
give you permission to combine PyInstaller with other programs...
and distribute the resulting application without the resulting work 
being covered by the GNU General Public License.
```

**意義**:
- ✅ 可以打包專有軟體
- ✅ 打包的應用程式可以使用任何授權
- ✅ 不需要開源您的應用程式

**要求**:
- ⚠️ 必須保留 PyInstaller 的版權聲明
- ⚠️ 建議在文檔中說明使用了 PyInstaller

---

## 授權相容性 (License Compatibility)

### 授權類型分類

#### 寬鬆型授權 (Permissive Licenses)
- ✅ MIT License
- ✅ BSD 3-Clause License
- ✅ Apache License 2.0
- ✅ PSF License

**特性**:
- 允許自由使用、修改、分發
- 可以用於專有軟體
- 可以改變授權

#### Copyleft 授權
- PyInstaller (GPL with exception)

**特性**:
- 通常要求衍生作品也使用相同授權
- PyInstaller 有特殊例外，不影響打包的應用程式

### 相容性矩陣

| 您的授權選擇 | MIT | Apache 2.0 | GPL v3 | 專有軟體 |
|-------------|-----|------------|--------|----------|
| ATP_Re 依賴 | ✅ | ✅ | ✅ | ✅ |
| 商業使用 | ✅ | ✅ | ⚠️ | ✅ |

**結論**: 
- ✅ ATP_Re 的所有依賴都允許商業使用
- ✅ 可以用於專有/商業軟體
- ✅ 不需要開源您的應用程式（如果基於 ATP_Re 開發）

---

## 商業使用指南 (Commercial Use Guide)

### 可以做的事情 ✅

1. **內部使用**
   - 在公司內部使用 ATP_Re
   - 不需要特殊授權或付費

2. **客製化**
   - 修改 ATP_Re 以符合需求
   - 不需要公開修改內容

3. **分發**
   - 分發給客戶或合作夥伴
   - 作為產品的一部分

4. **整合**
   - 整合到您的商業產品中
   - 透過 API 或其他方式

5. **品牌**
   - 使用自己的商標和品牌
   - 不需要提及 ATP_Re（但建議）

### 必須做的事情 ⚠️

1. **保留版權聲明**
   ```
   必須在分發時包含:
   - 原始版權聲明
   - 授權條款全文
   - 第三方依賴的授權資訊
   ```

2. **免責聲明**
   ```
   必須包含軟體的免責聲明
   （通常已包含在授權文件中）
   ```

3. **歸屬**
   ```
   建議（非必須）在文檔中說明:
   - 基於 ATP_Re 建構
   - 使用的開源套件
   ```

### 不能做的事情 ❌

1. **移除版權**
   - 不能移除原始的版權聲明
   - 不能聲稱是完全原創

2. **專利聲索**
   - 不能對原始開源部分聲索專利
   - Apache 2.0 提供專利授權保護

3. **誤導性聲明**
   - 不能暗示原作者背書
   - 不能使用原始專案的商標（未經許可）

### 商業使用範例

#### 範例 1: SaaS 服務
```
情境: 使用 ATP_Re 建立雲端分析服務

允許: ✅
- 部署 ATP_Re 到伺服器
- 向客戶收費
- 不公開修改內容

要求:
- 保留授權資訊在服務中（關於頁面）
- 內部文檔記錄使用的開源軟體
```

#### 範例 2: 桌面軟體
```
情境: 將 ATP_Re 打包為商業桌面軟體

允許: ✅
- 使用 PyInstaller 打包
- 銷售給客戶
- 使用自己的授權

要求:
- 在安裝程式/關於對話框中包含授權資訊
- 提供 LICENSES 檔案
- 歸屬使用的開源軟體
```

#### 範例 3: OEM 整合
```
情境: 預裝在硬體設備中

允許: ✅
- 預裝 ATP_Re
- 作為設備功能的一部分
- 不額外收費或收費

要求:
- 在設備文檔中包含授權資訊
- 提供開源聲明
```

---

## 重新分發指南 (Redistribution Guide)

### 必須包含的檔案

如果您重新分發 ATP_Re（無論是否修改），必須包含：

1. **LICENSE.txt**
   - ATP_Re 的授權條款

2. **LICENSES/** 目錄
   - 所有第三方依賴的授權文件

3. **NOTICE.txt** (建議)
   - 版權聲明
   - 使用的開源軟體列表

### NOTICE.txt 範本

```
ATP_Re - Automatic Train Protection Record Analysis System
Copyright (c) 2024 ATP_Re Contributors

本軟體包含以下開源軟體:

Python (PSF License)
- Copyright (c) 2001-2024 Python Software Foundation

FastAPI (MIT License)
- Copyright (c) 2018 Sebastián Ramírez

Streamlit (Apache License 2.0)
- Copyright (c) 2018-2024 Snowflake Inc.

Pandas (BSD 3-Clause License)
- Copyright (c) 2008-2024, AQR Capital Management, LLC, Lambda Foundry, Inc. and PyData Development Team

NumPy (BSD 3-Clause License)
- Copyright (c) 2005-2024, NumPy Developers

Plotly (MIT License)
- Copyright (c) 2016-2024 Plotly, Inc

[... 其他依賴套件 ...]

完整的授權條款請參閱 LICENSES/ 目錄。
```

### 分發檢查清單

在分發前，確認：

- [ ] 包含 LICENSE.txt
- [ ] 包含 LICENSES/ 目錄（所有依賴授權）
- [ ] 包含 NOTICE.txt（版權聲明）
- [ ] 保留所有版權聲明
- [ ] 未移除授權資訊
- [ ] 文檔中提及使用的開源軟體
- [ ] 如有修改，在適當位置註明

---

## 授權聲明範本 (License Notice Template)

### 應用程式內聲明

在「關於」對話框或頁面中：

```
ATP_Re v1.0.0
Automatic Train Protection Record Analysis System

Copyright (c) 2024 ATP_Re Contributors
Licensed under [您的授權]

本軟體基於以下開源專案建構:
- Python (PSF License)
- FastAPI (MIT License)
- Streamlit (Apache License 2.0)
- Pandas, NumPy, Plotly 等

完整的授權資訊請參閱 LICENSE.txt 及 LICENSES/ 目錄。

本軟體"按現狀"提供，不含任何明示或暗示的保證。
```

### 文檔中的聲明

在使用手冊或技術文檔中：

```markdown
## 授權與致謝

ATP_Re 使用多個優秀的開源軟體。我們感謝以下專案:

- **Python** - 程式語言 (PSF License)
- **FastAPI** - Web 框架 (MIT License)
- **Streamlit** - UI 框架 (Apache 2.0)
- **Pandas** - 資料處理 (BSD 3-Clause)
- **NumPy** - 數值計算 (BSD 3-Clause)
- **Plotly** - 視覺化 (MIT License)
- **SQLAlchemy** - ORM (MIT License)

完整的授權資訊和版權聲明請參閱軟體附帶的 LICENSE.txt 檔案。
```

### 原始碼檔案標頭

如果您修改或添加原始碼檔案:

```python
# Copyright (c) 2024 ATP_Re Contributors
# Copyright (c) 2024 [您的名字或公司]
#
# Licensed under [授權名稱]
# [授權簡短說明或連結]
```

---

## 常見問題 (License FAQ)

### Q: 我可以在商業產品中使用 ATP_Re 嗎？
**A**: ✅ 可以。所有依賴都允許商業使用。

### Q: 我需要開源我的修改嗎？
**A**: 取決於您選擇的授權。如果基於 ATP_Re 開發:
- MIT/BSD/Apache: 不需要開源
- GPL: 需要開源衍生作品

但 ATP_Re 的依賴都是寬鬆型授權，不要求開源。

### Q: 我可以改變 ATP_Re 的授權嗎？
**A**: 如果您是完整重寫，可以使用任何授權。如果基於現有代碼，必須遵守原始授權。

### Q: PyInstaller 的 GPL 授權會影響我嗎？
**A**: ❌ 不會。PyInstaller 有特殊例外條款，打包的應用程式不受 GPL 約束。

### Q: 我需要付費嗎？
**A**: ❌ 不需要。所有使用的開源軟體都是免費的。

### Q: 我可以移除「基於 ATP_Re」的聲明嗎？
**A**: 法律上可能可以（取決於授權），但道德上不建議。保留歸屬是對開源社群的尊重。

---

## 取得更多資訊

### 授權資源

- **Open Source Initiative**: https://opensource.org/licenses
- **Choose a License**: https://choosealicense.com/
- **SPDX License List**: https://spdx.org/licenses/

### 授權比較

- **TL;DR Legal**: https://tldrlegal.com/
- **GitHub License Comparison**: https://choosealicense.com/licenses/

### 法律諮詢

本文檔提供一般性指導，不構成法律建議。如有特定的授權問題，請諮詢法律專業人士。

---

**最後更新**: 2024年
**文檔版本**: 1.0.0

**注意**: 授權條款可能隨版本更新而變化，請以隨軟體分發的最新 LICENSE.txt 為準。
