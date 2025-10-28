# ATP 範例程式

本目錄包含 ATP 系統的使用範例。

## 可用範例

### plot_driving_curves.py

展示行車曲線繪製功能的完整範例。

**功能：**
- 生成多種速度曲線資料（簡單、真實、超速）
- 繪製時間模式和距離模式的曲線
- 展示目標速度和速限顯示
- 標示超速事件
- 匯出和載入 CSV 資料

**執行方式：**
```bash
cd /home/runner/work/ATP_re/ATP_re
python examples/plot_driving_curves.py
```

**輸出：**
- `output/plots/simple_speed_time.png` - 簡單速度曲線（時間模式）
- `output/plots/simple_speed_distance.png` - 簡單速度曲線（距離模式）
- `output/plots/realistic_speed_profile.png` - 真實速度曲線
- `output/plots/overspeed_profile.png` - 包含超速事件的曲線
- `output/plots/csv_loaded_profile.png` - 從 CSV 載入的曲線
- `output/plots/sample_driving_data.csv` - 範例資料 CSV 檔案

## 更多資訊

詳細的 API 文件和使用指南，請參閱 [VISUALIZATION_GUIDE.md](../VISUALIZATION_GUIDE.md)。
