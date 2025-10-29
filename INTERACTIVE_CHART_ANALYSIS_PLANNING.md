# ATP 行車紀錄分析系統 - 互動式圖表與自動分析功能規劃書

## 文件資訊
- **文件版本**: 1.0
- **建立日期**: 2024-10-28
- **專案**: ATP_Re (Automatic Train Protection - Reimplementation)
- **目的**: 規劃互動式行車曲線圖表與自動分析功能的詳細技術規格

## 目錄
1. [專案背景與現況分析](#1-專案背景與現況分析)
2. [需求分析](#2-需求分析)
3. [互動式圖表規劃](#3-互動式圖表規劃)
4. [自動分析功能規劃](#4-自動分析功能規劃)
5. [技術選型](#5-技術選型)
6. [系統整合方式](#6-系統整合方式)
7. [實作階段規劃](#7-實作階段規劃)
8. [效能評估](#8-效能評估)
9. [風險評估與緩解策略](#9-風險評估與緩解策略)

---

## 1. 專案背景與現況分析

### 1.1 現有系統架構

ATP_Re 系統目前包含：

#### 後端架構
- **Python FastAPI**: RESTful API 服務 (api/)
- **PostgreSQL 資料庫**: 資料持久化層
- **資料模型**: 完整的 ATP 任務、記錄、事件資料結構 (src/atp_re/models/)

#### 前端架構
- **Streamlit UI**: Web 介面 (streamlit_ui/app.py)
- **基本視覺化**: 使用 Plotly 進行圖表展示

#### 舊有 Java 系統
- **drawGraphics_re**: 18 個 Java 繪圖類別
  - DrawCurve, DrawHistogram, SpeedCurveDrawer, EventDrawer 等
  - 支援時間模式/距離模式雙軸繪圖
- **analysis_re**: 既有分析模組
  - SpeedAnalyzer, EventDetector, ParkingAccuracyAnalyzer
  - StatisticalSummary, AnalysisAPI

### 1.2 現有功能盤點

#### ✅ 已實現功能
1. **資料解碼**: ATP/MMI 協定完整解析
2. **基礎視覺化**: 速度曲線、事件標記
3. **基礎分析**: 速度分析、事件偵測、停車精度分析
4. **API 端點**: 任務管理、資料查詢、報表生成
5. **Web 界面**: Streamlit 基礎展示

#### ❌ 缺少功能
1. **互動性不足**:
   - 無法縮放(zoom)、平移(pan)特定區段
   - 無法選取時間/距離範圍查看詳細數據
   - 無滑鼠懸停(hover)顯示即時數據
   - 無法動態切換圖層顯示

2. **自動分析有限**:
   - 缺乏智能異常偵測
   - 無趨勢分析與預測
   - 缺少自動化報告生成
   - 無數據品質自動評估

3. **效能問題**:
   - 大數據量載入緩慢
   - 無資料分頁或虛擬化
   - 圖表渲染效能待優化

---

## 2. 需求分析

### 2.1 互動式圖表需求

#### 2.1.1 基礎互動功能
| 功能 | 優先級 | 描述 |
|------|--------|------|
| 縮放 (Zoom) | 高 | 滑鼠滾輪或觸控板縮放圖表 |
| 平移 (Pan) | 高 | 拖曳移動圖表視窗 |
| 區段選取 | 高 | 框選特定時間/距離範圍 |
| 重置視圖 | 中 | 一鍵恢復預設視圖 |
| 十字準線 | 中 | 顯示當前滑鼠位置的精確座標 |

#### 2.1.2 進階互動功能
| 功能 | 優先級 | 描述 |
|------|--------|------|
| 數據懸停提示 | 高 | 滑鼠停留顯示詳細數據點資訊 |
| 圖層控制 | 高 | 動態開關速度、目標速度、事件等圖層 |
| 多曲線比較 | 中 | 同時顯示多次任務的曲線對比 |
| 標記功能 | 中 | 用戶自訂標記特定點或區域 |
| 導出功能 | 中 | 匯出圖表為 PNG/SVG/PDF |
| 時間/距離切換 | 高 | X 軸在時間模式與距離模式間切換 |

#### 2.1.3 數據展示需求
| 功能 | 優先級 | 描述 |
|------|--------|------|
| 即時數據面板 | 高 | 顯示選定區域的統計數據 |
| 事件列表 | 高 | 對應圖表區間的事件清單 |
| 速度分布直方圖 | 中 | 顯示速度分布統計 |
| 數據品質指標 | 低 | 顯示資料完整度、準確度 |

### 2.2 自動分析需求

#### 2.2.1 異常偵測
| 分析項目 | 偵測條件 | 輸出 |
|----------|----------|------|
| 超速事件 | 實際速度 > 目標速度 + 閥值 | 事件列表、嚴重度分級 |
| 異常煞車 | 減速度 > 閥值 | 煞車點、減速率 |
| 速度異常波動 | 速度變化率超過正常範圍 | 異常區段、波動程度 |
| 長時間怠速 | 速度 = 0 且時間 > 閥值 | 怠速區段、持續時間 |
| ATP 故障 | 系統故障記錄 | 故障類型、頻率、影響 |

#### 2.2.2 趨勢分析
| 分析項目 | 方法 | 輸出 |
|----------|------|------|
| 速度趨勢 | 移動平均、線性回歸 | 趨勢線、預測值 |
| 耗時分析 | 各區段耗時統計 | 平均值、標準差、異常值 |
| 停車精度趨勢 | 歷史停車偏差分析 | 精度改善/惡化趨勢 |
| 司機行為模式 | 操作序列分析 | 行為特徵、習慣模式 |

#### 2.2.3 數據摘要
| 摘要項目 | 內容 |
|----------|------|
| 任務概要 | 路線、車次、司機、車輛、時間 |
| 運行指標 | 總距離、總時間、平均速度、最高速度 |
| 事件統計 | 各類事件數量、分布、嚴重度 |
| 停車分析 | 停車次數、平均偏差、精度評級 |
| 異常報告 | 偵測到的所有異常及建議 |

---

## 3. 互動式圖表規劃

### 3.1 前端圖表庫選擇

#### 方案比較

| 圖表庫 | 優點 | 缺點 | 適用場景 | 推薦度 |
|--------|------|------|----------|--------|
| **Plotly** | • 功能完整<br>• Python 整合好<br>• 互動性強<br>• 支援 3D | • 檔案較大<br>• 客製化較複雜 | 科學數據視覺化 | ★★★★★ |
| **Chart.js** | • 輕量快速<br>• 文檔豐富<br>• 易於客製化 | • 功能較陽春<br>• 大數據效能差 | 簡單商業圖表 | ★★★☆☆ |
| **D3.js** | • 極高客製化<br>• 強大靈活<br>• 動畫效果好 | • 學習曲線陡峭<br>• 開發時間長 | 複雜特殊視覺化 | ★★★☆☆ |
| **ECharts** | • 功能強大<br>• 中文文檔<br>• 效能優秀 | • 體積較大<br>• API 繁瑣 | 大數據複雜圖表 | ★★★★☆ |

#### 最終選擇: **Plotly**
**理由**:
1. 與現有 Streamlit 整合最佳
2. Python 原生支援，減少前後端溝通成本
3. 內建豐富的互動功能 (zoom, pan, hover)
4. 支援時間序列數據視覺化
5. 可擴展性強，支援自訂組件

### 3.2 圖表組件設計

#### 3.2.1 主圖表組件 (MainChartComponent)

```python
class InteractiveChartPanel:
    """
    互動式圖表主組件
    
    功能:
    - 速度曲線顯示 (實際速度 vs 目標速度)
    - 事件標記 (煞車、超速、故障等)
    - 區域選取與詳細數據展示
    - 多圖層管理
    """
    
    def __init__(self, data):
        self.data = data
        self.selected_range = None
        self.visible_layers = {
            'speed': True,
            'target_speed': True,
            'events': True,
            'stations': True
        }
    
    def render(self):
        """渲染主圖表"""
        pass
    
    def on_range_select(self, start, end):
        """處理區域選取事件"""
        pass
    
    def toggle_layer(self, layer_name):
        """切換圖層顯示"""
        pass
```

**視覺設計**:
- **X 軸**: 時間或距離 (可切換)
- **左 Y 軸**: 速度 (km/h)
- **右 Y 軸**: 其他指標 (坡度、質量等)
- **配色方案**:
  - 實際速度: 綠色實線
  - 目標速度: 黃色虛線
  - 超速區域: 半透明紅色填充
  - 事件標記: 依類型不同顏色垂直線

#### 3.2.2 事件時間軸組件 (EventTimelineComponent)

```python
class EventTimeline:
    """
    事件時間軸組件
    
    顯示所有事件在時間軸上的分布
    可點擊跳轉到主圖表對應位置
    """
    
    EVENT_COLORS = {
        'brake': 'orange',
        'overspeed': 'red',
        'failure': 'magenta',
        'driver_message': 'cyan',
        'balise': 'yellow',
        'station': 'green'
    }
```

#### 3.2.3 數據面板組件 (DataPanelComponent)

顯示選定區域的詳細統計:
- 時間範圍
- 距離範圍
- 平均速度
- 最高/最低速度
- 事件數量
- 加速度統計

#### 3.2.4 圖層控制組件 (LayerControlComponent)

使用 checkbox 群組控制圖層顯示:
```
☑ 速度曲線
☑ 目標速度
☑ 事件標記
☐ 煞車點
☑ 車站位置
☐ 坡度曲線
```

### 3.3 互動功能實作規格

#### 3.3.1 縮放功能 (Zoom)
- **觸發方式**: 
  - 滑鼠滾輪: 以滑鼠位置為中心縮放
  - 工具列按鈕: 放大/縮小按鈕
  - 觸控: 雙指捏合手勢
- **範圍限制**: 0.1x ~ 100x
- **平滑動畫**: 200ms 緩動效果

#### 3.3.2 平移功能 (Pan)
- **觸發方式**:
  - 滑鼠拖曳: 按住左鍵拖動
  - 觸控: 單指滑動
  - 鍵盤: 方向鍵移動
- **邊界限制**: 不超出數據範圍

#### 3.3.3 區段選取 (Range Selection)
- **觸發方式**: 按住 Shift + 拖曳
- **視覺回饋**: 半透明藍色選取框
- **選取後動作**:
  1. 顯示選定區域統計數據
  2. 更新事件列表
  3. 提供「放大到選取區域」按鈕

#### 3.3.4 懸停提示 (Hover Tooltip)
- **顯示內容**:
  ```
  時間: 2024-10-28 10:30:45
  距離: 5.240 km
  速度: 75 km/h
  目標速度: 80 km/h
  加速度: 0.5 m/s²
  最近事件: 通過車站 (5.200 km)
  ```
- **樣式**: 半透明白底、圓角、陰影

### 3.4 效能優化策略

#### 3.4.1 數據降採樣 (Downsampling)
對於大量數據點 (>10,000)，依據縮放層級動態降採樣:

```python
def downsample_data(data, zoom_level):
    """
    根據縮放層級降採樣數據
    
    zoom_level: 1.0 = 原始, 0.1 = 每10個點取1個
    """
    if len(data) < 1000:
        return data
    
    sample_rate = max(1, int(1 / zoom_level))
    return data[::sample_rate]
```

#### 3.4.2 虛擬化渲染
只渲染可視區域的數據點，視窗外的數據不繪製。

#### 3.4.3 Canvas 渲染
對於超大數據集 (>50,000 點)，使用 Canvas 代替 SVG 渲染。

#### 3.4.4 Web Worker
將數據處理移至 Web Worker，避免阻塞主執行緒。

---

## 4. 自動分析功能規劃

### 4.1 異常偵測模組

#### 4.1.1 超速偵測 (Overspeed Detection)

```python
class OverspeedDetector:
    """超速事件偵測器"""
    
    def __init__(self, threshold_kmh=5):
        """
        threshold_kmh: 超速閥值 (km/h)
        實際速度 > 目標速度 + threshold 即視為超速
        """
        self.threshold = threshold_kmh
    
    def detect(self, timestamps, speeds, target_speeds):
        """
        偵測超速事件
        
        Returns:
            List[OverspeedEvent]: 超速事件列表
            每個事件包含: 開始時間、結束時間、最大超速值、持續時間
        """
        overspeed_events = []
        in_overspeed = False
        event_start = None
        max_excess = 0
        
        for i, (t, speed, target) in enumerate(zip(timestamps, speeds, target_speeds)):
            excess = speed - target
            
            if excess > self.threshold:
                if not in_overspeed:
                    # 開始超速
                    in_overspeed = True
                    event_start = i
                    max_excess = excess
                else:
                    # 持續超速，更新最大超速值
                    max_excess = max(max_excess, excess)
            else:
                if in_overspeed:
                    # 超速結束
                    overspeed_events.append(OverspeedEvent(
                        start_time=timestamps[event_start],
                        end_time=timestamps[i-1],
                        max_excess_speed=max_excess,
                        duration=(timestamps[i-1] - timestamps[event_start]).total_seconds()
                    ))
                    in_overspeed = False
        
        return overspeed_events
```

#### 4.1.2 異常煞車偵測 (Abnormal Braking Detection)

```python
class BrakingAnomalyDetector:
    """異常煞車偵測器"""
    
    def __init__(self, harsh_brake_threshold=-2.0):
        """
        harsh_brake_threshold: 急煞車閥值 (m/s²)
        減速度超過此值視為急煞車
        """
        self.threshold = harsh_brake_threshold
    
    def detect(self, timestamps, speeds, locations):
        """
        偵測異常煞車事件
        
        Returns:
            List[BrakingEvent]: 煞車事件列表
            包含: 時間、位置、減速度、煞車類型 (正常/急煞)
        """
        braking_events = []
        
        for i in range(1, len(speeds)):
            # 計算減速度 (m/s²)
            v1 = speeds[i-1] / 3.6  # km/h -> m/s
            v2 = speeds[i] / 3.6
            dt = (timestamps[i] - timestamps[i-1]).total_seconds()
            
            if dt > 0:
                deceleration = (v2 - v1) / dt
                
                if deceleration < self.threshold:
                    braking_events.append(BrakingEvent(
                        time=timestamps[i],
                        location=locations[i],
                        deceleration=deceleration,
                        brake_type='harsh' if deceleration < -3.0 else 'normal'
                    ))
        
        return braking_events
```

#### 4.1.3 速度異常波動偵測 (Speed Fluctuation Detection)

使用移動標準差偵測速度異常波動:

```python
def detect_speed_fluctuations(speeds, window_size=10, std_threshold=2.0):
    """
    偵測速度異常波動
    
    使用滾動視窗計算標準差，超過閥值倍數視為異常波動
    """
    fluctuations = []
    
    for i in range(window_size, len(speeds)):
        window = speeds[i-window_size:i]
        mean = np.mean(window)
        std = np.std(window)
        current = speeds[i]
        
        if abs(current - mean) > std_threshold * std:
            fluctuations.append({
                'index': i,
                'value': current,
                'expected': mean,
                'deviation': abs(current - mean) / std
            })
    
    return fluctuations
```

### 4.2 趨勢分析模組

#### 4.2.1 速度趨勢分析 (Speed Trend Analysis)

```python
class SpeedTrendAnalyzer:
    """速度趨勢分析器"""
    
    def analyze(self, timestamps, speeds):
        """
        分析速度趨勢
        
        Returns:
            SpeedTrend: 包含趨勢線、預測值、季節性等資訊
        """
        # 1. 移動平均
        ma_short = self.moving_average(speeds, window=5)
        ma_long = self.moving_average(speeds, window=20)
        
        # 2. 線性回歸趨勢線
        trend_line = self.linear_regression(timestamps, speeds)
        
        # 3. 季節性分解 (如果數據足夠長)
        if len(speeds) > 100:
            seasonal, trend, residual = self.seasonal_decompose(speeds)
        
        return SpeedTrend(
            moving_avg_short=ma_short,
            moving_avg_long=ma_long,
            trend_line=trend_line,
            prediction=self.predict_next(speeds, steps=10)
        )
    
    @staticmethod
    def moving_average(data, window):
        """計算移動平均"""
        return np.convolve(data, np.ones(window)/window, mode='valid')
    
    @staticmethod
    def linear_regression(x, y):
        """線性回歸擬合"""
        x_numeric = np.array([(t - x[0]).total_seconds() for t in x])
        slope, intercept = np.polyfit(x_numeric, y, 1)
        return slope, intercept
```

#### 4.2.2 耗時分析 (Time Consumption Analysis)

分析各路段耗時，找出延誤區段:

```python
def analyze_time_consumption(stations, arrival_times, scheduled_times):
    """
    耗時分析
    
    Returns:
        List[TimeConsumption]: 各區段耗時分析
        包含: 實際耗時、計畫耗時、延誤時間、延誤百分比
    """
    consumptions = []
    
    for i in range(len(stations) - 1):
        actual = (arrival_times[i+1] - arrival_times[i]).total_seconds()
        scheduled = (scheduled_times[i+1] - scheduled_times[i]).total_seconds()
        delay = actual - scheduled
        delay_percent = (delay / scheduled) * 100
        
        consumptions.append(TimeConsumption(
            from_station=stations[i],
            to_station=stations[i+1],
            actual_time=actual,
            scheduled_time=scheduled,
            delay=delay,
            delay_percent=delay_percent,
            status='delayed' if delay > 60 else 'on_time'
        ))
    
    return consumptions
```

### 4.3 數據摘要生成

#### 4.3.1 自動報告生成器 (Auto Report Generator)

```python
class AutoReportGenerator:
    """自動報告生成器"""
    
    def generate_comprehensive_report(self, mission_data):
        """
        生成完整分析報告
        
        Returns:
            Dict: 包含所有分析結果的結構化報告
        """
        report = {
            'mission_info': self.extract_mission_info(mission_data),
            'summary': self.generate_summary(mission_data),
            'speed_analysis': self.analyze_speed(mission_data),
            'event_analysis': self.analyze_events(mission_data),
            'anomaly_detection': self.detect_anomalies(mission_data),
            'trend_analysis': self.analyze_trends(mission_data),
            'recommendations': self.generate_recommendations(mission_data)
        }
        
        return report
    
    def export_report(self, report, format='pdf'):
        """
        匯出報告
        
        支援格式: pdf, html, json, markdown
        """
        if format == 'pdf':
            return self.export_pdf(report)
        elif format == 'html':
            return self.export_html(report)
        elif format == 'json':
            return json.dumps(report, indent=2)
        elif format == 'markdown':
            return self.export_markdown(report)
```

**報告結構範例**:

```markdown
# ATP 行車任務分析報告

## 任務資訊
- 任務日期: 2024-10-28
- 班次: A001
- 車次: T123
- 司機: D456
- 車輛: V789

## 運行摘要
- 總距離: 45.2 km
- 總時間: 52 分鐘
- 平均速度: 52.1 km/h
- 最高速度: 90 km/h
- 停車次數: 8 次

## 異常事件
### 超速事件 (2 件)
1. [10:15:23 - 10:16:05] 最大超速 8 km/h，持續 42 秒
2. [10:42:11 - 10:42:45] 最大超速 5 km/h，持續 34 秒

### 急煞車事件 (1 件)
1. [10:28:33] 減速度 -2.5 m/s²，位置 25.8 km

## 趨勢分析
- 平均速度呈上升趨勢 (+5%)
- 後半段停車精度改善 (平均偏差 -30cm)

## 建議
1. 注意 10:15-10:16 路段速度控制
2. 第 25.8 km 位置建議提前減速
3. 整體運行良好，繼續保持
```

### 4.4 智能建議系統

基於分析結果提供智能建議:

```python
class RecommendationEngine:
    """建議引擎"""
    
    def generate_recommendations(self, analysis_results):
        """
        根據分析結果生成建議
        
        Returns:
            List[Recommendation]: 建議列表
            包含: 類型、嚴重度、描述、建議動作
        """
        recommendations = []
        
        # 超速建議
        if analysis_results['overspeed_count'] > 3:
            recommendations.append(Recommendation(
                type='safety',
                severity='high',
                description='偵測到多次超速事件',
                action='建議加強速度控制訓練'
            ))
        
        # 急煞車建議
        if analysis_results['harsh_brake_count'] > 0:
            recommendations.append(Recommendation(
                type='comfort',
                severity='medium',
                description='偵測到急煞車事件',
                action='建議提前預判，平滑減速'
            ))
        
        # 停車精度建議
        if analysis_results['avg_parking_deviation'] > 1.0:  # > 1m
            recommendations.append(Recommendation(
                type='precision',
                severity='low',
                description='停車精度有待提升',
                action='建議參考標誌物，提前調整速度'
            ))
        
        return recommendations
```

---

## 5. 技術選型

### 5.1 前端技術棧

| 技術 | 用途 | 版本 | 理由 |
|------|------|------|------|
| **Streamlit** | Web 框架 | 1.28+ | 快速開發、Python 原生 |
| **Plotly** | 圖表庫 | 5.18+ | 互動性強、功能完整 |
| **Pandas** | 數據處理 | 2.1+ | 高效數據操作 |
| **NumPy** | 數值計算 | 1.26+ | 科學計算基礎 |

### 5.2 後端技術棧

| 技術 | 用途 | 版本 | 理由 |
|------|------|------|------|
| **FastAPI** | API 框架 | 0.104+ | 異步、高效能、自動文檔 |
| **SQLAlchemy** | ORM | 2.0+ | 資料庫抽象層 |
| **PostgreSQL** | 資料庫 | 14+ | 成熟穩定、地理空間支援 |
| **Redis** | 快取 | 7.0+ | 加速數據查詢 |

### 5.3 分析算法庫

| 庫 | 用途 | 版本 |
|------|------|------|
| **SciPy** | 科學計算 | 1.11+ |
| **scikit-learn** | 機器學習 | 1.3+ |
| **statsmodels** | 統計分析 | 0.14+ |

### 5.4 開發工具

| 工具 | 用途 |
|------|------|
| **pytest** | 單元測試 |
| **black** | 程式碼格式化 |
| **mypy** | 型別檢查 |
| **flake8** | 程式碼檢查 |

---

## 6. 系統整合方式

### 6.1 架構設計

```
┌─────────────────────────────────────────────────────┐
│                  Web Browser                        │
│  ┌──────────────────────────────────────────┐      │
│  │  Streamlit UI (streamlit_ui/app.py)      │      │
│  │  - InteractiveChartPanel                 │      │
│  │  - EventTimeline                          │      │
│  │  - DataPanel                              │      │
│  │  - LayerControl                           │      │
│  └──────────────────────────────────────────┘      │
└─────────────────────────────────────────────────────┘
                     ↕ HTTP/WebSocket
┌─────────────────────────────────────────────────────┐
│               FastAPI Backend (api/)                │
│  ┌──────────────────────────────────────────┐      │
│  │  REST API Endpoints                      │      │
│  │  - /api/v1/analysis/speed                │      │
│  │  - /api/v1/analysis/events               │      │
│  │  - /api/v1/analysis/anomalies            │      │
│  │  - /api/v1/analysis/trends               │      │
│  │  - /api/v1/reports/generate              │      │
│  └──────────────────────────────────────────┘      │
│  ┌──────────────────────────────────────────┐      │
│  │  Analysis Modules (新增)                 │      │
│  │  - anomaly_detector.py                   │      │
│  │  - trend_analyzer.py                     │      │
│  │  - report_generator.py                   │      │
│  │  - recommendation_engine.py              │      │
│  └──────────────────────────────────────────┘      │
└─────────────────────────────────────────────────────┘
                     ↕
┌─────────────────────────────────────────────────────┐
│         Data Layer (src/atp_re/models/)            │
│  - ATPMission                                       │
│  - Record (Dynamic, Status, VDX)                   │
│  - Event                                            │
│  - Station, Balise                                  │
└─────────────────────────────────────────────────────┘
                     ↕
┌─────────────────────────────────────────────────────┐
│              PostgreSQL Database                    │
└─────────────────────────────────────────────────────┘
```

### 6.2 新增模組結構

```
ATP_Re/
├── api/
│   └── app/
│       ├── routers/
│       │   ├── analysis.py  # 新增：分析 API
│       │   └── reports.py   # 已存在：報表 API
│       └── services/
│           ├── anomaly_detector.py  # 新增
│           ├── trend_analyzer.py    # 新增
│           └── report_generator.py  # 新增
│
├── streamlit_ui/
│   ├── app.py  # 主應用 (擴充)
│   ├── components/
│   │   ├── interactive_chart.py  # 新增
│   │   ├── event_timeline.py     # 新增
│   │   ├── data_panel.py          # 新增
│   │   └── layer_control.py       # 新增
│   └── utils/
│       └── chart_helpers.py       # 新增
│
└── src/
    └── atp_re/
        └── analysis/  # 新增分析模組
            ├── __init__.py
            ├── anomaly_detection.py
            ├── trend_analysis.py
            └── report_generation.py
```

### 6.3 API 端點設計

#### 6.3.1 分析 API

```python
# GET /api/v1/analysis/speed/{mission_id}
# Response:
{
  "mission_id": "uuid",
  "max_speed": 120,
  "min_speed": 0,
  "avg_speed": 65.5,
  "speed_trend": {
    "moving_avg_short": [...],
    "moving_avg_long": [...],
    "trend_line": {"slope": 0.5, "intercept": 60}
  }
}

# GET /api/v1/analysis/anomalies/{mission_id}
# Response:
{
  "mission_id": "uuid",
  "overspeed_events": [
    {
      "start_time": "2024-10-28T10:15:23Z",
      "end_time": "2024-10-28T10:16:05Z",
      "max_excess_speed": 8.0,
      "duration": 42
    }
  ],
  "braking_events": [...],
  "speed_fluctuations": [...]
}

# GET /api/v1/analysis/trends/{mission_id}
# Response:
{
  "mission_id": "uuid",
  "speed_trend": {...},
  "time_consumption": [...],
  "parking_accuracy_trend": {...}
}

# POST /api/v1/reports/generate
# Request:
{
  "mission_id": "uuid",
  "format": "pdf",  // pdf, html, json, markdown
  "include_sections": [
    "summary",
    "speed_analysis",
    "anomaly_detection",
    "trends",
    "recommendations"
  ]
}
# Response:
{
  "report_id": "uuid",
  "download_url": "/api/v1/reports/download/{report_id}",
  "expires_at": "2024-10-29T10:00:00Z"
}
```

### 6.4 資料流程

```
1. 使用者選擇任務
   ↓
2. Streamlit UI 發送 API 請求
   GET /api/v1/analysis/speed/{mission_id}
   GET /api/v1/analysis/anomalies/{mission_id}
   ↓
3. FastAPI 接收請求
   ↓
4. 從資料庫載入任務數據
   ↓
5. 執行分析 (anomaly_detector, trend_analyzer)
   ↓
6. 返回分析結果 (JSON)
   ↓
7. Streamlit UI 渲染圖表
   - InteractiveChartPanel 顯示速度曲線
   - EventTimeline 顯示事件時間軸
   - DataPanel 顯示統計數據
```

### 6.5 快取策略

使用 Redis 快取分析結果，減少重複計算:

```python
from functools import lru_cache
import redis

redis_client = redis.Redis(host='localhost', port=6379, db=0)

def get_cached_analysis(mission_id, analysis_type):
    """從快取取得分析結果"""
    cache_key = f"analysis:{analysis_type}:{mission_id}"
    cached = redis_client.get(cache_key)
    
    if cached:
        return json.loads(cached)
    
    return None

def set_cached_analysis(mission_id, analysis_type, result, ttl=3600):
    """將分析結果存入快取 (1小時過期)"""
    cache_key = f"analysis:{analysis_type}:{mission_id}"
    redis_client.setex(cache_key, ttl, json.dumps(result))
```

---

## 7. 實作階段規劃

### 階段 1: 基礎互動圖表 (2 週)

#### 目標
實作基本的互動式圖表，支援縮放、平移、懸停提示。

#### 任務清單
- [x] 選擇並設定 Plotly 圖表庫
- [ ] 實作 InteractiveChartPanel 組件
- [ ] 整合現有速度數據到互動圖表
- [ ] 實作縮放 (zoom) 功能
- [ ] 實作平移 (pan) 功能
- [ ] 實作懸停提示 (hover tooltip)
- [ ] 單元測試：圖表渲染、互動功能
- [ ] 使用者測試：收集回饋

#### 交付物
- `streamlit_ui/components/interactive_chart.py`
- 單元測試檔案
- 使用說明文件

#### 驗收標準
- 圖表載入時間 < 2 秒 (1000 數據點)
- 縮放/平移流暢 (無卡頓)
- 懸停提示顯示正確數據

### 階段 2: 進階互動功能 (2 週)

#### 目標
加入區段選取、圖層控制、多曲線比較等進階功能。

#### 任務清單
- [ ] 實作區段選取功能
- [ ] 實作 DataPanel 組件 (顯示選定區域統計)
- [ ] 實作 LayerControl 組件 (圖層開關)
- [ ] 實作 EventTimeline 組件
- [ ] 加入時間/距離模式切換
- [ ] 實作圖表導出功能 (PNG/SVG)
- [ ] 單元測試
- [ ] 整合測試

#### 交付物
- `streamlit_ui/components/data_panel.py`
- `streamlit_ui/components/layer_control.py`
- `streamlit_ui/components/event_timeline.py`
- 整合測試報告

#### 驗收標準
- 區段選取精準 (誤差 < 1%)
- 圖層切換即時反應 (< 100ms)
- 導出圖表清晰度符合需求

### 階段 3: 異常偵測模組 (2 週)

#### 目標
實作超速、異常煞車、速度波動等異常偵測算法。

#### 任務清單
- [ ] 設計異常偵測 API 端點
- [ ] 實作 OverspeedDetector
- [ ] 實作 BrakingAnomalyDetector
- [ ] 實作 SpeedFluctuationDetector
- [ ] 整合到 FastAPI 後端
- [ ] 前端展示異常事件
- [ ] 單元測試：各偵測器
- [ ] 整合測試：API 端到端

#### 交付物
- `api/app/services/anomaly_detector.py`
- `api/app/routers/analysis.py`
- API 文檔
- 測試報告

#### 驗收標準
- 異常偵測準確率 > 90%
- API 響應時間 < 1 秒 (1000 數據點)
- 無假陽性 (false positive) 超過 5%

### 階段 4: 趨勢分析模組 (2 週)

#### 目標
實作速度趨勢、耗時分析、停車精度趨勢等分析功能。

#### 任務清單
- [ ] 實作 SpeedTrendAnalyzer
- [ ] 實作 TimeConsumptionAnalyzer
- [ ] 實作 ParkingAccuracyTrendAnalyzer
- [ ] 整合到 API
- [ ] 前端展示趨勢圖表
- [ ] 單元測試
- [ ] 整合測試

#### 交付物
- `api/app/services/trend_analyzer.py`
- 趨勢圖表組件
- API 文檔更新
- 測試報告

#### 驗收標準
- 趨勢預測誤差 < 10%
- 移動平均計算正確
- 圖表展示清晰易懂

### 階段 5: 自動報告生成 (2 週)

#### 目標
實作自動報告生成器，支援多種格式匯出。

#### 任務清單
- [ ] 實作 AutoReportGenerator
- [ ] 設計報告模板 (PDF/HTML/Markdown)
- [ ] 實作 RecommendationEngine
- [ ] 整合到 API
- [ ] 前端報告生成介面
- [ ] 單元測試
- [ ] 端到端測試

#### 交付物
- `api/app/services/report_generator.py`
- 報告模板檔案
- 前端報告頁面
- 使用手冊

#### 驗收標準
- 報告生成時間 < 5 秒
- PDF 格式正確、美觀
- 建議內容準確、實用

### 階段 6: 效能優化與測試 (1 週)

#### 目標
優化系統效能，進行壓力測試，確保穩定性。

#### 任務清單
- [ ] 實作數據降採樣
- [ ] 加入 Redis 快取
- [ ] 優化資料庫查詢
- [ ] 壓力測試 (1000 並發)
- [ ] 效能調校
- [ ] 安全性檢查
- [ ] 完整文檔撰寫

#### 交付物
- 效能測試報告
- 壓力測試報告
- 安全性檢查報告
- 完整技術文檔

#### 驗收標準
- 系統可承受 1000 並發用戶
- 99 percentile 響應時間 < 3 秒
- 無嚴重安全漏洞

---

## 8. 效能評估

### 8.1 效能指標

| 指標 | 目標值 | 測量方法 |
|------|--------|----------|
| 圖表初始載入時間 | < 2 秒 | 從 API 請求到圖表渲染完成 |
| 縮放/平移響應時間 | < 100 ms | 用戶操作到視圖更新 |
| 懸停提示延遲 | < 50 ms | 滑鼠移動到提示顯示 |
| API 響應時間 | < 1 秒 | 請求發送到接收完整響應 |
| 報告生成時間 | < 5 秒 | 請求報告到下載連結生成 |
| 系統並發能力 | 1000 用戶 | 同時在線用戶數 |

### 8.2 效能測試計畫

#### 8.2.1 單元效能測試

測試各組件的效能:

```python
import time

def test_chart_render_performance():
    """測試圖表渲染效能"""
    data = generate_sample_data(n=1000)
    
    start = time.time()
    chart = InteractiveChartPanel(data)
    chart.render()
    end = time.time()
    
    render_time = end - start
    assert render_time < 2.0, f"圖表渲染過慢: {render_time:.2f}s"

def test_anomaly_detection_performance():
    """測試異常偵測效能"""
    data = generate_sample_data(n=10000)
    
    start = time.time()
    detector = OverspeedDetector()
    events = detector.detect(data['timestamps'], data['speeds'], data['target_speeds'])
    end = time.time()
    
    detect_time = end - start
    assert detect_time < 1.0, f"異常偵測過慢: {detect_time:.2f}s"
```

#### 8.2.2 壓力測試

使用 Locust 進行壓力測試:

```python
from locust import HttpUser, task, between

class ATPUser(HttpUser):
    wait_time = between(1, 3)
    
    @task
    def get_speed_analysis(self):
        self.client.get("/api/v1/analysis/speed/test-mission-id")
    
    @task
    def get_anomaly_detection(self):
        self.client.get("/api/v1/analysis/anomalies/test-mission-id")
    
    @task
    def generate_report(self):
        self.client.post("/api/v1/reports/generate", json={
            "mission_id": "test-mission-id",
            "format": "pdf"
        })
```

執行壓力測試:
```bash
locust -f locustfile.py --host=http://localhost:8000 --users 1000 --spawn-rate 50
```

### 8.3 效能優化策略

#### 8.3.1 前端優化
1. **延遲載入**: 只載入可視區域的數據
2. **虛擬化渲染**: 使用虛擬滾動技術
3. **Web Worker**: 將計算密集任務移至 Worker
4. **圖表降採樣**: 根據縮放層級動態調整數據密度

#### 8.3.2 後端優化
1. **資料庫索引**: 為常查詢欄位建立索引
2. **查詢優化**: 減少 N+1 查詢問題
3. **Redis 快取**: 快取分析結果
4. **異步處理**: 長時間任務改為異步

#### 8.3.3 資料庫優化
```sql
-- 為常查詢欄位建立索引
CREATE INDEX idx_mission_date ON atp_missions(mission_date);
CREATE INDEX idx_record_mission_id ON records(mission_id);
CREATE INDEX idx_event_timestamp ON events(timestamp);

-- 分割大表
CREATE TABLE records_2024_01 PARTITION OF records
FOR VALUES FROM ('2024-01-01') TO ('2024-02-01');
```

### 8.4 監控與告警

使用 Prometheus + Grafana 監控系統:

```python
from prometheus_client import Counter, Histogram, generate_latest

# 定義指標
request_count = Counter('http_requests_total', 'Total HTTP requests')
request_latency = Histogram('http_request_duration_seconds', 'HTTP request latency')

# 記錄指標
@app.middleware("http")
async def add_metrics(request: Request, call_next):
    request_count.inc()
    
    start = time.time()
    response = await call_next(request)
    duration = time.time() - start
    
    request_latency.observe(duration)
    
    return response

# 暴露指標端點
@app.get("/metrics")
def metrics():
    return Response(generate_latest(), media_type="text/plain")
```

---

## 9. 風險評估與緩解策略

### 9.1 技術風險

| 風險 | 機率 | 影響 | 緩解策略 |
|------|------|------|----------|
| Plotly 效能不足 | 中 | 高 | 實作降採樣、Canvas 渲染備案 |
| 大數據量處理緩慢 | 高 | 高 | 分頁載入、異步處理、快取 |
| 異常偵測誤報率高 | 中 | 中 | 調整閥值、加入人工審核 |
| 資料庫查詢瓶頸 | 中 | 高 | 建立索引、查詢優化、讀寫分離 |

### 9.2 開發風險

| 風險 | 機率 | 影響 | 緩解策略 |
|------|------|------|----------|
| 開發時程延遲 | 中 | 中 | 敏捷迭代、MVP 優先 |
| 需求變更 | 高 | 中 | 保持架構彈性、模組化設計 |
| 測試不足 | 低 | 高 | 自動化測試、持續整合 |
| 技術債累積 | 中 | 中 | Code Review、定期重構 |

### 9.3 運營風險

| 風險 | 機率 | 影響 | 緩解策略 |
|------|------|------|----------|
| 系統故障 | 低 | 高 | 多備份、故障轉移、監控告警 |
| 效能下降 | 中 | 高 | 效能監控、自動擴展 |
| 資料遺失 | 低 | 極高 | 定期備份、異地容災 |
| 安全性漏洞 | 低 | 極高 | 安全審計、滲透測試 |

---

## 附錄

### A. 專業術語

| 術語 | 英文 | 說明 |
|------|------|------|
| 互動式圖表 | Interactive Chart | 支援用戶操作 (縮放、平移等) 的圖表 |
| 異常偵測 | Anomaly Detection | 自動識別數據中的異常模式 |
| 趨勢分析 | Trend Analysis | 分析數據隨時間的變化趨勢 |
| 降採樣 | Downsampling | 減少數據點數量以提升效能 |
| 移動平均 | Moving Average | 平滑數據的統計方法 |

### B. 參考資料

1. **Plotly 官方文檔**: https://plotly.com/python/
2. **Streamlit 文檔**: https://docs.streamlit.io/
3. **FastAPI 文檔**: https://fastapi.tiangolo.com/
4. **時間序列分析**: https://otexts.com/fpp2/
5. **異常偵測算法**: https://scikit-learn.org/stable/modules/outlier_detection.html

### C. 版本歷史

| 版本 | 日期 | 作者 | 變更內容 |
|------|------|------|----------|
| 1.0 | 2025-10-28 | Copilot | 初版完成 |

---

**文件結束**
