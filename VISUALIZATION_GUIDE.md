# ATP 行車曲線繪製功能文件

## 概述

ATP 行車紀錄分析系統的行車曲線繪製模組提供了完整的列車運行資料視覺化功能。本模組支援多種繪圖模式，能夠繪製速度曲線、目標速度曲線、速限曲線，並可標示超速事件。

## 功能特性

### 核心功能
- ✅ **雙模式繪圖**：支援時間為橫軸（BY_TIME）和距離為橫軸（BY_DISTANCE）兩種模式
- ✅ **速度曲線繪製**：繪製列車實際速度曲線
- ✅ **目標速度顯示**：顯示 ATP 系統的目標速度
- ✅ **速限顯示**：顯示軌道速限
- ✅ **超速事件標示**：自動標示並突顯超速區域
- ✅ **資料匯入匯出**：支援 CSV 格式的資料儲存與載入
- ✅ **高解析度輸出**：支援 PNG、JPG、PDF、SVG 等多種格式

### 視覺化特性
- 清晰的網格線和座標軸
- 顏色編碼（綠色=當前速度，黃色=目標速度，橙色=速限）
- 超速區域紅色半透明標示
- 自動縮放和格式化
- 可自訂圖表標題和任務資訊

## 安裝

模組已整合至 ATP_re 套件中，安裝方式：

```bash
# 從專案根目錄安裝
cd /home/runner/work/ATP_re/ATP_re
pip install -e .

# 安裝視覺化相關依賴
pip install matplotlib
```

## 快速開始

### 基本使用範例

```python
from datetime import datetime
from atp_re.visualization import SpeedCurvePlotter, PlotMode

# 準備資料
time = [datetime(2024, 1, 1, 10, 0, i) for i in range(100)]
distance = [i * 100 for i in range(100)]  # 米
speed = [i * 1.2 for i in range(100)]     # km/h

# 建立繪圖器（時間模式）
plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)

# 繪製曲線
plotter.plot(
    time=time,
    distance=distance,
    speed=speed
)

# 儲存圖表
plotter.save('speed_curve.png')
plotter.close()
```

### 使用記錄格式

```python
from atp_re.visualization import SpeedCurvePlotter, PlotMode

# 準備記錄資料
records = [
    {
        'timestamp': datetime(2024, 1, 1, 10, 0, i),
        'position': i * 100,
        'speed': i * 1.2,
        'target_speed': 100.0,
        'speed_limit': 120.0
    }
    for i in range(100)
]

# 繪製
plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
plotter.plot_from_records(records)
plotter.save('speed_curve.png')
plotter.close()
```

## 使用範例

### 範例 1：時間模式速度曲線

```python
from atp_re.visualization import SpeedCurvePlotter, PlotMode
from atp_re.visualization.sample_data import SampleDataGenerator

# 生成範例資料
records = SampleDataGenerator.generate_simple_speed_profile(
    num_points=100,
    max_speed=100.0
)

# 繪製曲線
plotter = SpeedCurvePlotter(
    mode=PlotMode.BY_TIME,
    title="列車速度曲線（時間模式）"
)
plotter.plot_from_records(records)
plotter.save('speed_by_time.png')
plotter.close()
```

### 範例 2：距離模式速度曲線

```python
# 繪製距離模式曲線
plotter = SpeedCurvePlotter(
    mode=PlotMode.BY_DISTANCE,
    title="列車速度曲線（距離模式）"
)
plotter.plot_from_records(records)
plotter.save('speed_by_distance.png')
plotter.close()
```

### 範例 3：包含目標速度和速限

```python
# 生成完整資料
records = SampleDataGenerator.generate_speed_profile(
    duration_minutes=30,
    interval_seconds=1.0,
    max_speed=130.0
)

# 繪製完整曲線
plotter = SpeedCurvePlotter(
    mode=PlotMode.BY_TIME,
    title="完整速度曲線"
)
plotter.plot_from_records(
    records,
    mission_info="車次：T123 | 駕駛：D456 | 日期：2024-01-01"
)
plotter.save('complete_curve.png', dpi=300)
plotter.close()
```

### 範例 4：超速事件標示

```python
# 生成包含超速的資料
records = SampleDataGenerator.generate_speed_profile(
    duration_minutes=20,
    include_overspeed=True
)

# 繪製並標示超速
plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
plotter.plot_from_records(
    records,
    overspeed_threshold=130.0  # 超過 130 km/h 標示為紅色
)
plotter.save('overspeed_detection.png')
plotter.close()
```

### 範例 5：資料匯入匯出

```python
from atp_re.visualization.sample_data import SampleDataGenerator

# 生成資料並儲存
records = SampleDataGenerator.generate_speed_profile(duration_minutes=30)
SampleDataGenerator.save_to_csv(records, 'driving_data.csv')

# 載入並繪圖
loaded_records = SampleDataGenerator.load_from_csv('driving_data.csv')
plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
plotter.plot_from_records(loaded_records)
plotter.save('loaded_curve.png')
plotter.close()
```

## API 參考

### SpeedCurvePlotter

速度曲線繪圖器類別。

#### 建構函式

```python
SpeedCurvePlotter(
    mode: PlotMode = PlotMode.BY_TIME,
    figsize: Tuple[int, int] = (12, 6),
    title: str = "Train Speed Curve"
)
```

**參數：**
- `mode`: 繪圖模式（`PlotMode.BY_TIME` 或 `PlotMode.BY_DISTANCE`）
- `figsize`: 圖表大小（寬度, 高度），單位為英寸
- `title`: 圖表標題

#### plot() 方法

```python
plot(
    time: Optional[List[datetime]] = None,
    distance: Optional[List[float]] = None,
    speed: List[float] = None,
    target_speed: Optional[List[float]] = None,
    speed_limit: Optional[List[float]] = None,
    overspeed_threshold: Optional[float] = None,
    **kwargs
)
```

**參數：**
- `time`: 時間列表（BY_TIME 模式必須）
- `distance`: 距離列表，單位：公尺（BY_DISTANCE 模式必須）
- `speed`: 速度列表，單位：km/h（必須）
- `target_speed`: 目標速度列表，單位：km/h（可選）
- `speed_limit`: 速限列表，單位：km/h（可選）
- `overspeed_threshold`: 超速閾值，單位：km/h（可選）
- `**kwargs`: 額外參數
  - `mission_info`: 任務資訊字串
  - `y_min`: Y 軸最小值
  - `y_max`: Y 軸最大值

**返回：**
- `(figure, axes)`: Matplotlib 圖表物件

#### plot_from_records() 方法

```python
plot_from_records(
    records: List[Dict[str, Any]],
    **kwargs
)
```

從記錄字典列表繪製曲線。

**記錄格式：**
```python
{
    'timestamp': datetime,      # 時間戳記
    'position': float,          # 位置（公尺）
    'speed': float,             # 速度（km/h）
    'target_speed': float,      # 目標速度（可選）
    'speed_limit': float        # 速限（可選）
}
```

#### save() 方法

```python
save(filename: str, dpi: int = 300)
```

儲存圖表到檔案。

**參數：**
- `filename`: 輸出檔案名稱（支援 .png, .jpg, .pdf, .svg）
- `dpi`: 解析度（每英寸點數）

#### close() 方法

```python
close()
```

關閉圖表並釋放資源。

### SampleDataGenerator

範例資料生成器。

#### generate_speed_profile()

```python
@staticmethod
generate_speed_profile(
    duration_minutes: int = 30,
    interval_seconds: float = 1.0,
    max_speed: float = 130.0,
    include_acceleration: bool = True,
    include_braking: bool = True,
    include_overspeed: bool = False
) -> List[Dict[str, Any]]
```

生成真實的速度曲線資料。

**參數：**
- `duration_minutes`: 持續時間（分鐘）
- `interval_seconds`: 資料點間隔（秒）
- `max_speed`: 最大速度（km/h）
- `include_acceleration`: 包含加速階段
- `include_braking`: 包含制動階段
- `include_overspeed`: 包含超速事件

**返回：**
- 記錄字典列表

#### generate_simple_speed_profile()

```python
@staticmethod
generate_simple_speed_profile(
    num_points: int = 100,
    max_speed: float = 100.0
) -> List[Dict[str, Any]]
```

生成簡單的三角形速度曲線（加速-巡航-減速）。

#### save_to_csv() / load_from_csv()

```python
@staticmethod
save_to_csv(records: List[Dict[str, Any]], filename: str)

@staticmethod
load_from_csv(filename: str) -> List[Dict[str, Any]]
```

儲存/載入 CSV 格式的資料。

## 完整範例程式

專案提供完整的範例程式：

```bash
cd /home/runner/work/ATP_re/ATP_re
python examples/plot_driving_curves.py
```

此程式將：
1. 生成多種速度曲線資料
2. 繪製時間模式和距離模式的曲線
3. 展示超速事件標示
4. 匯出 CSV 資料
5. 從 CSV 載入並繪圖

輸出結果儲存在 `output/plots/` 目錄中。

## 測試

執行單元測試：

```bash
# 執行視覺化模組測試
pytest tests/unit/visualization/ -v

# 執行所有測試
pytest tests/ -v

# 執行測試並顯示覆蓋率
pytest tests/unit/visualization/ --cov=atp_re.visualization
```

## 技術規格

### 支援的資料格式

1. **時間序列資料**
   - 時間戳記：Python datetime 物件
   - 間隔：可變，通常 0.1-1.0 秒

2. **位置資料**
   - 單位：公尺（m）
   - 精度：浮點數

3. **速度資料**
   - 單位：公里/小時（km/h）
   - 精度：浮點數

### 輸出格式

支援的圖片格式：
- PNG（推薦，預設 300 DPI）
- JPEG
- PDF（向量圖形）
- SVG（向量圖形）

### 效能

- 能夠處理數萬個資料點
- 繪圖時間：通常 < 1 秒（1000 點）
- 記憶體佔用：合理（取決於資料量）

## 架構設計

### 類別層次結構

```
CurvePlotter (基礎類別)
    ├─ 座標軸管理
    ├─ 圖表儲存
    └─ 資源管理
    
SpeedCurvePlotter (速度曲線)
    ├─ 速度資料處理
    ├─ 目標速度繪製
    ├─ 速限繪製
    └─ 超速事件標示
```

### 設計模式

1. **策略模式**：支援 BY_TIME 和 BY_DISTANCE 兩種繪圖策略
2. **工廠模式**：SampleDataGenerator 提供資料生成
3. **建造者模式**：逐步建構複雜的圖表

## 整合指南

### 與現有系統整合

```python
from atp_re.models import Record
from atp_re.visualization import SpeedCurvePlotter, PlotMode

# 從資料庫讀取記錄
# records = db.query(Record).filter(...).all()

# 轉換為繪圖格式
plot_records = [
    {
        'timestamp': r.timestamp,
        'position': r.data.get('position', 0),
        'speed': r.data.get('speed', 0),
        'target_speed': r.data.get('target_speed'),
        'speed_limit': r.data.get('speed_limit')
    }
    for r in records
]

# 繪圖
plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
plotter.plot_from_records(plot_records)
plotter.save('mission_curve.png')
plotter.close()
```

### Web API 整合

可以整合到 FastAPI 端點：

```python
from fastapi import FastAPI
from fastapi.responses import FileResponse

@app.get("/api/missions/{mission_id}/speed_curve")
async def get_speed_curve(mission_id: int):
    # 取得資料
    records = get_mission_records(mission_id)
    
    # 繪圖
    plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
    plotter.plot_from_records(records)
    
    # 儲存暫存檔
    temp_file = f"/tmp/curve_{mission_id}.png"
    plotter.save(temp_file)
    plotter.close()
    
    return FileResponse(temp_file)
```

## 故障排除

### 常見問題

**Q: 圖表顯示為空白**
A: 確認資料列表不為空，且 time/distance 與 speed 長度相同。

**Q: 時間軸格式不正確**
A: 確保使用 Python datetime 物件，而非字串。

**Q: 記憶體不足**
A: 對於大量資料，考慮資料降採樣或分段繪製。

**Q: 中文顯示亂碼**
A: 需要配置 matplotlib 的中文字型。

## 未來規劃

- [ ] 支援更多曲線類型（加速度、位移等）
- [ ] 互動式圖表（Plotly 整合）
- [ ] 多列車對比分析
- [ ] 3D 視覺化
- [ ] 即時資料串流繪圖
- [ ] 匯出為動畫（GIF/MP4）

## 參考資料

- [Matplotlib 官方文件](https://matplotlib.org/)
- [ATP 系統技術規格](./ATP%20行車紀錄分析系統%20-%20完整技術規格書%20v2%200%2029855714413f81b1b980eedb85bea559.md)
- [專案 README](./README.md)

## 授權

與 ATP_re 專案相同授權。

## 聯絡資訊

如有問題或建議，請在 GitHub Issue 中提出。
