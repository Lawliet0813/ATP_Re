# ATP 互動式圖表與自動分析 - 實作路線圖

## 文件資訊
- **版本**: 1.0
- **日期**: 2024-06-10
- **目的**: 詳細的實作步驟與交付標準

## 總覽

### 專案時程
- **總時程**: 11 週
- **開始日期**: 2024-06-15
- **預計完成**: 2024-08-31

### 團隊配置
- **前端開發**: 1 人
- **後端開發**: 1 人
- **測試工程師**: 0.5 人
- **UI/UX 設計師**: 0.5 人

---

## 階段 1: 基礎互動圖表 (第 1-2 週)

### Week 1: 環境設置與基礎架構

#### Day 1-2: 開發環境準備
**任務**:
- [ ] 建立開發分支 `feature/interactive-charts`
- [ ] 安裝必要依賴
  ```bash
  pip install plotly==5.18.0
  pip install streamlit==1.28.2
  pip install pandas==2.1.3
  pip install numpy==1.26.2
  ```
- [ ] 設置 pre-commit hooks
- [ ] 配置 linter (black, flake8, mypy)

**交付物**:
- 開發環境配置文檔
- requirements.txt 更新

#### Day 3-5: 基礎組件實作
**任務**:
- [ ] 創建 `streamlit_ui/components/` 目錄結構
  ```
  streamlit_ui/components/
  ├── __init__.py
  ├── interactive_chart.py
  ├── chart_toolbar.py
  └── chart_legend.py
  ```
- [ ] 實作 `InteractiveChartPanel` 基礎類別
  ```python
  class InteractiveChartPanel:
      def __init__(self, mission_data):
          self.data = mission_data
          self.fig = None
          self.selected_range = None
      
      def render(self):
          """渲染圖表"""
          pass
      
      def update_layout(self):
          """更新圖表佈局"""
          pass
  ```
- [ ] 整合 Plotly 圖表庫
- [ ] 實作基礎速度曲線顯示

**驗收標準**:
- ✅ 可顯示基本速度曲線
- ✅ 圖表載入時間 < 2 秒 (1000 點)
- ✅ 通過單元測試

### Week 2: 互動功能實作

#### Day 1-2: 縮放與平移
**任務**:
- [ ] 實作滑鼠滾輪縮放
  ```python
  def handle_zoom(event):
      zoom_factor = 1.1 if event.delta > 0 else 0.9
      update_axis_range(zoom_factor, event.x, event.y)
  ```
- [ ] 實作拖曳平移
- [ ] 加入縮放限制 (0.1x ~ 100x)
- [ ] 實作平滑動畫效果

**驗收標準**:
- ✅ 縮放流暢 (無卡頓)
- ✅ 平移範圍受限於數據範圍
- ✅ 動畫時長 200-300ms

#### Day 3-5: 懸停提示與工具欄
**任務**:
- [ ] 實作 Tooltip 組件
  ```python
  class ChartTooltip:
      def __init__(self):
          self.visible = False
          self.position = (0, 0)
          self.content = {}
      
      def show(self, x, y, data):
          """顯示提示"""
          pass
      
      def hide(self):
          """隱藏提示"""
          pass
  ```
- [ ] 實作工具欄按鈕
  - 放大/縮小
  - 重置視圖
  - 十字準線切換
- [ ] 加入鍵盤快捷鍵支援

**交付物**:
- `streamlit_ui/components/interactive_chart.py` (完整)
- 單元測試檔案
- 使用說明文檔

**驗收標準**:
- ✅ 懸停提示顯示正確數據
- ✅ 工具欄所有按鈕正常運作
- ✅ 快捷鍵響應正確

---

## 階段 2: 進階互動功能 (第 3-4 週)

### Week 3: 區段選取與數據面板

#### Day 1-3: 區段選取功能
**任務**:
- [ ] 實作框選區域功能
  ```python
  def handle_selection(start_x, end_x):
      selected_data = filter_data(start_x, end_x)
      update_data_panel(selected_data)
      highlight_selection_zone(start_x, end_x)
  ```
- [ ] 視覺化選取框 (半透明藍色)
- [ ] 實作選取後動作
  - 更新統計數據
  - 顯示選取範圍
  - 提供「放大到選取區域」按鈕

**驗收標準**:
- ✅ 選取精準 (誤差 < 1%)
- ✅ 選取框視覺效果清晰
- ✅ 選取後數據正確更新

#### Day 4-5: 數據面板實作
**任務**:
- [ ] 創建 `DataPanel` 組件
  ```python
  class DataPanel:
      def __init__(self):
          self.statistics = {}
      
      def update(self, selected_data):
          """更新統計數據"""
          self.statistics = {
              'avg_speed': calculate_avg(selected_data.speeds),
              'max_speed': max(selected_data.speeds),
              'event_count': len(selected_data.events)
          }
      
      def render(self):
          """渲染數據面板"""
          pass
  ```
- [ ] 顯示選定區域統計
  - 平均速度
  - 最高/最低速度
  - 事件數量
  - 距離/時間範圍

**交付物**:
- `streamlit_ui/components/data_panel.py`
- 整合測試報告

### Week 4: 圖層控制與事件時間軸

#### Day 1-2: 圖層控制
**任務**:
- [ ] 實作 `LayerControl` 組件
  ```python
  class LayerControl:
      def __init__(self):
          self.layers = {
              'speed': {'visible': True, 'color': '#00ff00'},
              'target_speed': {'visible': True, 'color': '#ffff00'},
              'events': {'visible': True, 'color': None},
          }
      
      def toggle_layer(self, layer_name):
          """切換圖層顯示"""
          pass
      
      def set_layer_color(self, layer_name, color):
          """設置圖層顏色"""
          pass
  ```
- [ ] 實作多選框控制
- [ ] 加入顏色選擇器
- [ ] 實作不透明度滑桿

**驗收標準**:
- ✅ 圖層切換即時反應 (< 100ms)
- ✅ 顏色修改正確應用
- ✅ 至少保留一個圖層可見

#### Day 3-5: 事件時間軸
**任務**:
- [ ] 創建 `EventTimeline` 組件
  ```python
  class EventTimeline:
      EVENT_ICONS = {
          'brake': '🚦',
          'overspeed': '⚠️',
          'station': '🚉',
          'failure': '❌'
      }
      
      def render(self, events):
          """渲染事件時間軸"""
          pass
      
      def on_event_click(self, event_id):
          """處理事件點擊"""
          pass
  ```
- [ ] 顯示事件圖標與標籤
- [ ] 實作點擊跳轉功能
- [ ] 加入事件篩選器

**交付物**:
- `streamlit_ui/components/layer_control.py`
- `streamlit_ui/components/event_timeline.py`
- 整合測試報告

---

## 階段 3: 異常偵測模組 (第 5-6 週)

### Week 5: 後端異常偵測實作

#### Day 1-2: 超速偵測器
**任務**:
- [ ] 創建 `api/app/services/anomaly_detector.py`
- [ ] 實作 `OverspeedDetector` 類別
  ```python
  class OverspeedDetector:
      def __init__(self, threshold_kmh=5):
          self.threshold = threshold_kmh
      
      def detect(self, timestamps, speeds, target_speeds):
          """偵測超速事件"""
          events = []
          # 實作偵測邏輯
          return events
  ```
- [ ] 撰寫單元測試
  ```python
  def test_overspeed_detection():
      detector = OverspeedDetector(threshold_kmh=5)
      result = detector.detect(
          timestamps=[...],
          speeds=[80, 85, 90, 88, 85],
          target_speeds=[80, 80, 80, 80, 80]
      )
      assert len(result) == 1
      assert result[0].max_excess_speed == 10
  ```

**驗收標準**:
- ✅ 偵測準確率 > 90%
- ✅ 無假陽性 > 5%
- ✅ 單元測試通過率 100%

#### Day 3-5: 異常煞車與速度波動偵測
**任務**:
- [ ] 實作 `BrakingAnomalyDetector`
  ```python
  def detect_harsh_braking(self, timestamps, speeds):
      """偵測急煞車"""
      harsh_brakes = []
      for i in range(1, len(speeds)):
          deceleration = calculate_deceleration(
              speeds[i-1], speeds[i],
              time_diff(timestamps[i-1], timestamps[i])
          )
          if deceleration < -2.0:  # m/s²
              harsh_brakes.append({
                  'timestamp': timestamps[i],
                  'deceleration': deceleration
              })
      return harsh_brakes
  ```
- [ ] 實作 `SpeedFluctuationDetector`
- [ ] 加入異常嚴重度評級 (low, medium, high)

**交付物**:
- `api/app/services/anomaly_detector.py` (完整)
- 測試報告
- API 使用文檔

### Week 6: API 整合與前端展示

#### Day 1-3: API 端點實作
**任務**:
- [ ] 創建 API 路由 `api/app/routers/analysis.py`
  ```python
  @router.get("/analysis/anomalies/{mission_id}")
  async def detect_anomalies(
      mission_id: str,
      anomaly_types: List[str] = Query(default=None)
  ):
      # 載入任務數據
      mission_data = await get_mission_data(mission_id)
      
      # 執行異常偵測
      detector = AnomalyDetector()
      anomalies = detector.detect_all(mission_data)
      
      return {
          "mission_id": mission_id,
          "total_anomalies": len(anomalies),
          "anomalies": anomalies
      }
  ```
- [ ] 實作快取機制 (Redis)
  ```python
  @lru_cache(maxsize=100)
  def get_cached_anomalies(mission_id):
      cache_key = f"anomalies:{mission_id}"
      cached = redis_client.get(cache_key)
      if cached:
          return json.loads(cached)
      return None
  ```
- [ ] 加入錯誤處理

**驗收標準**:
- ✅ API 響應時間 < 1 秒
- ✅ 快取命中率 > 70%
- ✅ 錯誤處理完善

#### Day 4-5: 前端異常展示
**任務**:
- [ ] 創建異常展示組件
  ```python
  def show_anomalies(anomalies):
      st.subheader("🚨 異常偵測結果")
      
      # 異常摘要
      col1, col2, col3 = st.columns(3)
      col1.metric("超速事件", anomalies['overspeed_count'])
      col2.metric("急煞車", anomalies['harsh_brake_count'])
      col3.metric("速度波動", anomalies['fluctuation_count'])
      
      # 異常列表
      for anomaly in anomalies['items']:
          with st.expander(f"{anomaly['type']} - {anomaly['timestamp']}"):
              st.write(f"嚴重度: {anomaly['severity']}")
              st.write(f"描述: {anomaly['description']}")
              st.write(f"建議: {anomaly['recommendation']}")
  ```
- [ ] 在圖表上標記異常區域
- [ ] 實作異常篩選功能

**交付物**:
- 完整的異常偵測功能
- 前後端整合測試報告

---

## 階段 4: 趨勢分析模組 (第 7-8 週)

### Week 7: 趨勢分析算法

#### Day 1-3: 速度趨勢分析
**任務**:
- [ ] 實作 `TrendAnalyzer` 類別
  ```python
  class SpeedTrendAnalyzer:
      def analyze(self, timestamps, speeds):
          # 移動平均
          ma_short = self.moving_average(speeds, window=5)
          ma_long = self.moving_average(speeds, window=20)
          
          # 線性回歸
          trend_line = self.linear_regression(timestamps, speeds)
          
          # 預測
          prediction = self.predict_next(speeds, steps=10)
          
          return {
              'moving_avg_short': ma_short,
              'moving_avg_long': ma_long,
              'trend_line': trend_line,
              'prediction': prediction
          }
      
      @staticmethod
      def moving_average(data, window):
          return np.convolve(data, np.ones(window)/window, mode='valid')
  ```
- [ ] 實作線性回歸
- [ ] 實作簡單預測模型

**驗收標準**:
- ✅ 移動平均計算正確
- ✅ 趨勢線擬合度 R² > 0.7
- ✅ 預測誤差 < 10%

#### Day 4-5: 耗時與停車精度分析
**任務**:
- [ ] 實作耗時分析
  ```python
  def analyze_time_consumption(stations, arrival_times):
      consumptions = []
      for i in range(len(stations) - 1):
          actual = (arrival_times[i+1] - arrival_times[i]).seconds
          consumptions.append({
              'from': stations[i],
              'to': stations[i+1],
              'actual_time': actual
          })
      return consumptions
  ```
- [ ] 實作停車精度趨勢分析

**交付物**:
- `api/app/services/trend_analyzer.py`
- 單元測試與文檔

### Week 8: 趨勢視覺化

#### Day 1-3: 趨勢圖表組件
**任務**:
- [ ] 創建趨勢圖表
  ```python
  def plot_speed_trend(data, trend):
      fig = go.Figure()
      
      # 實際速度
      fig.add_trace(go.Scatter(
          x=data['timestamps'],
          y=data['speeds'],
          name='實際速度',
          mode='lines'
      ))
      
      # 移動平均
      fig.add_trace(go.Scatter(
          x=data['timestamps'],
          y=trend['moving_avg_short'],
          name='短期趨勢',
          line=dict(dash='dash')
      ))
      
      return fig
  ```
- [ ] 實作耗時比較圖
- [ ] 實作停車精度趨勢圖

#### Day 4-5: API 整合
**任務**:
- [ ] 創建趨勢分析 API 端點
- [ ] 整合到前端展示

**交付物**:
- 完整趨勢分析功能
- 整合測試報告

---

## 階段 5: 自動報告生成 (第 9-10 週)

### Week 9: 報告生成引擎

#### Day 1-3: 報告模板設計
**任務**:
- [ ] 設計 Markdown 報告模板
  ```markdown
  # ATP 行車任務分析報告
  
  ## 任務資訊
  - 任務日期: {{ mission_date }}
  - 班次: {{ work_shift }}
  - 車次: {{ train_running }}
  
  ## 運行摘要
  - 總距離: {{ total_distance }} km
  - 總時間: {{ total_time }} 分鐘
  - 平均速度: {{ avg_speed }} km/h
  
  ## 異常事件
  {% for anomaly in anomalies %}
  ### {{ anomaly.type }}
  - 時間: {{ anomaly.timestamp }}
  - 描述: {{ anomaly.description }}
  {% endfor %}
  ```
- [ ] 設計 HTML 報告模板
- [ ] 設計 PDF 樣式

**驗收標準**:
- ✅ 報告格式美觀專業
- ✅ 數據呈現清晰
- ✅ 支援多語言

#### Day 4-5: 報告生成器實作
**任務**:
- [ ] 實作 `ReportGenerator` 類別
  ```python
  class AutoReportGenerator:
      def generate(self, mission_data, format='pdf'):
          # 執行所有分析
          analyses = self.run_all_analyses(mission_data)
          
          # 生成建議
          recommendations = self.generate_recommendations(analyses)
          
          # 填充模板
          report_content = self.fill_template(analyses, recommendations)
          
          # 導出
          if format == 'pdf':
              return self.export_pdf(report_content)
          elif format == 'html':
              return self.export_html(report_content)
  ```
- [ ] 實作 PDF 生成 (使用 ReportLab 或 WeasyPrint)
- [ ] 實作建議引擎

**交付物**:
- `api/app/services/report_generator.py`
- 報告模板檔案

### Week 10: 報告系統整合

#### Day 1-3: API 與任務佇列
**任務**:
- [ ] 實作報告生成 API
  ```python
  @router.post("/reports/generate")
  async def generate_report(request: ReportRequest):
      # 創建報告任務
      task = create_report_task(request)
      
      # 加入佇列 (使用 Celery 或 asyncio)
      task_id = queue.enqueue(task)
      
      return {
          "report_id": task_id,
          "status": "generating"
      }
  
  @router.get("/reports/status/{report_id}")
  async def get_report_status(report_id: str):
      status = check_task_status(report_id)
      return status
  ```
- [ ] 實作非同步任務處理
- [ ] 實作報告下載端點

**驗收標準**:
- ✅ 報告生成時間 < 5 秒
- ✅ 支援並發生成
- ✅ 錯誤處理完善

#### Day 4-5: 前端報告介面
**任務**:
- [ ] 創建報告生成介面
  ```python
  def report_generation_page():
      st.title("📊 自動報告生成")
      
      # 選擇任務
      mission = st.selectbox("選擇任務", missions)
      
      # 選擇格式
      format = st.radio("報告格式", ["PDF", "HTML", "Markdown"])
      
      # 選擇章節
      sections = st.multiselect(
          "包含章節",
          ["摘要", "速度分析", "異常偵測", "趨勢分析", "建議"]
      )
      
      # 生成按鈕
      if st.button("生成報告"):
          with st.spinner("生成中..."):
              result = generate_report_api(mission, format, sections)
              st.success(f"報告已生成: {result['report_id']}")
              st.download_button("下載報告", result['url'])
  ```
- [ ] 實作進度顯示
- [ ] 實作報告預覽

**交付物**:
- 完整報告生成功能
- 使用手冊

---

## 階段 6: 效能優化與測試 (第 11 週)

### Week 11: 系統優化

#### Day 1-2: 效能優化
**任務**:
- [ ] 實作數據降採樣
  ```python
  def downsample_data(data, target_points=1000):
      if len(data) <= target_points:
          return data
      
      step = len(data) // target_points
      return data[::step]
  ```
- [ ] 優化資料庫查詢
  ```sql
  -- 建立索引
  CREATE INDEX idx_mission_date ON atp_missions(mission_date);
  CREATE INDEX idx_record_timestamp ON records(timestamp);
  ```
- [ ] 實作 Redis 快取
- [ ] 前端資源壓縮

**驗收標準**:
- ✅ 首次載入時間 < 2 秒
- ✅ 快取命中率 > 80%
- ✅ 資料庫查詢優化 > 50%

#### Day 3-4: 測試
**任務**:
- [ ] 單元測試補充 (覆蓋率 > 80%)
- [ ] 整合測試
- [ ] 壓力測試 (1000 並發)
  ```bash
  locust -f locustfile.py --users 1000 --spawn-rate 50
  ```
- [ ] 安全性測試
  - SQL injection
  - XSS
  - CSRF

**交付物**:
- 測試報告
- 效能評估報告
- 安全性檢查報告

#### Day 5: 文檔與部署
**任務**:
- [ ] 完善技術文檔
- [ ] 撰寫使用手冊
- [ ] 準備部署腳本
- [ ] 創建 Docker 映像

**交付物**:
- 完整技術文檔包
- 部署指南
- Docker Compose 配置

---

## 驗收標準總覽

### 功能性標準

| 功能 | 驗收標準 |
|------|----------|
| 互動圖表 | ✅ 支援縮放、平移、選取<br>✅ 懸停提示正確<br>✅ 圖層控制正常 |
| 異常偵測 | ✅ 偵測準確率 > 90%<br>✅ 假陽性 < 5%<br>✅ 嚴重度分級正確 |
| 趨勢分析 | ✅ 移動平均正確<br>✅ 趨勢線擬合 R² > 0.7<br>✅ 預測誤差 < 10% |
| 報告生成 | ✅ 支援 PDF/HTML/Markdown<br>✅ 生成時間 < 5 秒<br>✅ 格式美觀專業 |

### 非功能性標準

| 指標 | 目標值 | 測量方法 |
|------|--------|----------|
| 載入時間 | < 2 秒 | Chrome DevTools |
| API 響應時間 | < 1 秒 | Postman / curl |
| 並發能力 | 1000 用戶 | Locust 壓力測試 |
| 測試覆蓋率 | > 80% | pytest-cov |
| 程式碼品質 | A 級 | SonarQube |

---

## 風險管理

### 高風險項目

| 風險 | 緩解措施 | 應變計畫 |
|------|----------|----------|
| Plotly 效能不足 | 提前進行效能測試 | 準備 Canvas 替代方案 |
| 大數據處理緩慢 | 實作降採樣 | 使用分頁載入 |
| 時程延誤 | 敏捷迭代，MVP 優先 | 調整範圍，砍掉非必要功能 |

---

## 附錄

### A. 開發規範

#### 程式碼風格
```python
# 使用 black 格式化
black --line-length 100 .

# 使用 flake8 檢查
flake8 --max-line-length 100 --ignore E203,W503 .

# 使用 mypy 型別檢查
mypy --strict .
```

#### Git Commit 規範
```
feat: 新功能
fix: 修復 bug
docs: 文檔更新
style: 程式碼格式
refactor: 重構
test: 測試
chore: 其他
```

#### Pull Request 檢查清單
- [ ] 程式碼符合風格規範
- [ ] 通過所有單元測試
- [ ] 測試覆蓋率 > 80%
- [ ] 更新相關文檔
- [ ] 經過 Code Review

### B. 參考資源

- Plotly 文檔: https://plotly.com/python/
- Streamlit 文檔: https://docs.streamlit.io/
- FastAPI 文檔: https://fastapi.tiangolo.com/
- PostgreSQL 文檔: https://www.postgresql.org/docs/

---

**文件結束**
