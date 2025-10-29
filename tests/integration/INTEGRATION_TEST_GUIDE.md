# ATP 行車紀錄分析系統：MMI 與 RU 檔案整合測試文件

本文件說明 ATP 行車紀錄分析系統的 MMI 與 RU 檔案整合測試架構。

## 測試目標

依照 Issue #[原議題編號] 的規劃，本測試框架旨在：

1. ✅ 驗證系統解析真實 MMI 檔案能力
2. ✅ 驗證系統解析真實 RU 檔案能力
3. ✅ 檢查資料整合與比對精確性
4. ⏳ 對照原 Java 系統輸出結果（待實作）
5. ✅ 效能與大檔案處理能力

## 測試架構

### 目錄結構

```
tests/
├── integration/
│   ├── test_ru_file_parsing.py      # RU 檔案解析測試
│   ├── test_mmi_file_parsing.py     # MMI 檔案解析測試
│   └── run_integration_tests.py     # 整合測試執行器
├── RU_file/                         # RU 測試檔案目錄
│   └── 024423.RU                    # 範例 RU 檔案
└── MMI_file/                        # MMI 測試檔案目錄
    ├── 00000207_000426--_653102--_-3202/
    ├── 00000267_001170--_775495--_-9042/
    ├── 00000267_004209--_775495--_-9041/
    ├── 00000267_004218--_775495--_-9041/
    ├── 00000267_004218--_775495--_-9042/
    ├── 00000270_001132B-_088605--_-9172/
    └── 2T5-----_008252--_775495--_M0573/
```

## 測試內容

### 1. RU 檔案解析測試 (`test_ru_file_parsing.py`)

#### 基本功能測試
- **test_ru_files_exist**: 驗證測試檔案存在
- **test_parse_single_ru_file**: 解析單一 RU 檔案並輸出詳細資訊
- **test_parse_all_ru_files**: 批次解析所有 RU 檔案
- **test_ru_packet_types_coverage**: 分析封包類型覆蓋率

#### 測試結果範例
```
Testing RU file: 024423.RU
File size: 2038 bytes
Total packets decoded: 56

Packet types found:
- Type 1 (MMI_DYNAMIC): 2 packets
- Type 31: 11 packets
- Type 42 (MVB_LOG_BTM_STATUS_1): 5 packets
- Type 43-47 (MVB_LOG_BTM_TGM_1-5): 25 packets
- Type 62-64: 8 packets
- Type 91: 2 packets
- Type 211: 11 packets
```

#### 錯誤處理測試
- **test_ru_error_handling_truncated_file**: 測試截斷檔案處理
- **test_ru_error_handling_invalid_data**: 測試無效資料處理

#### 效能測試
- **test_large_file_performance**: 測試大檔案效能（1000+ 封包）
- 目標效能: > 1000 packets/sec

### 2. MMI 檔案解析測試 (`test_mmi_file_parsing.py`)

#### 基本功能測試
- **test_mmi_files_exist**: 驗證測試檔案存在（18 個 MMI 檔案）
- **test_parse_single_mmi_file**: 解析單一 MMI 檔案
- **test_parse_all_mmi_files**: 批次解析所有 MMI 檔案

#### 資料分析測試
- **test_mmi_file_sizes_distribution**: 分析檔案大小分佈
  - 最小檔案: ~12 KB
  - 最大檔案: ~310 KB
  - 平均檔案: ~100 KB
  
- **test_mmi_directory_structure**: 分析目錄結構
  - 8 個子目錄
  - 每個目錄代表不同的行車任務

#### 封包解碼測試
- **test_mmi_dynamic_packet_decoding**: 測試 MMI_DYNAMIC 封包解碼
  - 速度資訊 (v_train, v_target, v_permitted)
  - 加速度資訊 (a_train)
  - 位置資訊 (o_train, o_brake_target)
  
- **test_mmi_status_packet_decoding**: 測試 MMI_STATUS 封包解碼
  - 黏著模式 (m_adhesion)
  - 運行模式 (m_mode)
  - 防護等級 (m_level)

#### 效能測試
- **test_large_mmi_file_performance**: 測試大檔案效能（10000+ 封包）
- 目標效能: > 10000 packets/sec

## 執行測試

### 方法 1: 使用 pytest 直接執行

執行所有整合測試：
```bash
pytest tests/integration/ -v
```

執行 RU 檔案測試：
```bash
pytest tests/integration/test_ru_file_parsing.py -v
```

執行 MMI 檔案測試：
```bash
pytest tests/integration/test_mmi_file_parsing.py -v
```

執行特定測試：
```bash
pytest tests/integration/test_ru_file_parsing.py::TestRUFileParsing::test_parse_single_ru_file -v -s
```

### 方法 2: 使用整合測試執行器（推薦）

使用整合測試執行器可以自動生成完整報告：

```bash
python tests/integration/run_integration_tests.py
```

執行器會：
1. 執行所有整合測試
2. 生成測試報告（文字、JSON、Markdown 格式）
3. 輸出測試結果到 `test_results_[timestamp]/` 目錄

### 生成的報告

執行後會在 `test_results_[timestamp]/` 目錄下生成：

1. **test_summary.txt**: 文字格式摘要報告
2. **test_results.json**: JSON 格式結果（供自動化使用）
3. **TEST_REPORT.md**: Markdown 格式詳細報告
4. **test_ru_file_parsing.xml**: RU 測試 JUnit XML 報告
5. **test_mmi_file_parsing.xml**: MMI 測試 JUnit XML 報告

## 測試結果

### 最新測試結果摘要

**測試日期**: 2025-10-28

**測試狀態**: ✅ 全部通過

| 測試套件 | 測試數量 | 通過 | 失敗 | 執行時間 |
|---------|---------|------|------|---------|
| RU 檔案解析 | 7 | 7 | 0 | 0.02s |
| MMI 檔案解析 | 8 | 8 | 0 | 7.09s |
| **總計** | **15** | **15** | **0** | **7.55s** |

### RU 檔案測試結果

- ✅ 成功解析 024423.RU 檔案（2038 bytes）
- ✅ 解碼 56 個封包，0 錯誤
- ✅ 識別 10+ 種封包類型
- ✅ 錯誤處理正常
- ✅ 效能測試通過（> 1000 packets/sec）

### MMI 檔案測試結果

- ✅ 找到 18 個 MMI 測試檔案
- ✅ 檔案大小範圍: 12 KB - 310 KB
- ✅ 8 個不同任務目錄
- ✅ MMI_DYNAMIC 封包解碼正常
- ✅ MMI_STATUS 封包解碼正常
- ✅ 效能測試通過（> 10000 packets/sec）

## 新增測試資料

### 新增 RU 測試檔案

將新的 RU 檔案放入 `tests/RU_file/` 目錄：

```bash
cp /path/to/new_file.RU tests/RU_file/
```

測試會自動偵測並處理新檔案。

### 新增 MMI 測試檔案

建議依照原有結構組織 MMI 檔案：

```bash
# 建立新的任務目錄
mkdir tests/MMI_file/[任務識別碼]/

# 將 MMI 檔案複製進去
cp /path/to/*.MMI tests/MMI_file/[任務識別碼]/
```

## 已知問題與限制

### 目前狀態

1. ✅ **基本解析功能**: 完整實作
2. ✅ **錯誤處理**: 已實作基本錯誤處理
3. ✅ **效能測試**: 已實作並通過
4. ⏳ **Java 系統比對**: 待實作
5. ⏳ **更多測試資料**: 需要更多樣化的測試檔案

### 待改進項目

1. **Java 輸出比對**
   - 需要 Java 系統的參考輸出資料
   - 實作自動比對工具

2. **測試資料擴充**
   - 目前只有 1 個 RU 檔案
   - 需要更多不同場景的檔案

3. **異常狀況測試**
   - 檔案損毀測試
   - 格式異常測試
   - 資料缺漏測試

4. **效能基準**
   - 建立效能基準線
   - 監控效能退化

## 特殊狀況測試

### 測試損毀檔案

可以手動創建損毀檔案進行測試：

```python
# 建立截斷的檔案
with open('tests/RU_file/024423.RU', 'rb') as f:
    data = f.read(100)  # 只讀前 100 bytes
    
with open('/tmp/corrupted.RU', 'wb') as f:
    f.write(data)

# 測試損毀檔案處理
pytest tests/integration/test_ru_file_parsing.py::TestRUFileParsing::test_ru_error_handling_truncated_file
```

## 持續整合（CI/CD）

### GitHub Actions 整合

建議在 `.github/workflows/` 加入測試工作流程：

```yaml
name: Integration Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Python
        uses: actions/setup-python@v2
        with:
          python-version: '3.12'
      - name: Install dependencies
        run: |
          pip install -e .
          pip install -r requirements.txt
      - name: Run integration tests
        run: |
          python tests/integration/run_integration_tests.py
      - name: Upload test reports
        uses: actions/upload-artifact@v2
        with:
          name: test-reports
          path: test_results_*/
```

## 維護與更新

### 定期檢查

建議定期執行以下檢查：

1. **每週**: 執行完整測試套件
2. **新增檔案時**: 執行相關測試
3. **程式碼變更後**: 執行回歸測試
4. **發布前**: 執行完整測試並生成報告

### 更新測試

當解碼器功能更新時，需要同步更新測試：

1. 更新測試用例
2. 更新預期結果
3. 執行回歸測試
4. 更新文件

## 疑難排解

### 常見問題

**Q: 測試找不到檔案？**
```
A: 確認工作目錄正確，測試應該從專案根目錄執行：
   cd /path/to/ATP_re
   python tests/integration/run_integration_tests.py
```

**Q: 測試執行很慢？**
```
A: MMI 檔案掃描測試較耗時，可以只執行特定測試：
   pytest tests/integration/test_ru_file_parsing.py -v
```

**Q: 如何查看詳細輸出？**
```
A: 使用 -s 參數顯示標準輸出：
   pytest tests/integration/test_ru_file_parsing.py -v -s
```

## 相關文件

- [解碼器驗證文件](../../DECODER_VALIDATION.md)
- [專案總結](../../00_Project_Summary.md)
- [Stage 5 快速開始](../../STAGE5_QUICKSTART.md)
- [故障排除指南](../../TROUBLESHOOTING.md)

## 聯絡資訊

如有問題或建議，請在 GitHub Issue 中提出：
- Repository: Lawliet0813/ATP_re
- Issue Tracking: #12 (總覽議題)

---

**最後更新**: 2025-10-28  
**版本**: 1.0  
**維護者**: Lawliet Chen
