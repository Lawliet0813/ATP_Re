# drawGraphics_re 資料夾分析報告

## 概述
`drawGraphics_re` 資料夾是ATP系統的圖形繪製層，包含16個Java檔案，負責將解碼後的ATP資料以視覺化的方式呈現。這個資料夾實現了各種圖表和曲線的繪製，包括速度曲線、煞車曲線、坡度曲線、故障訊息等，是系統資料視覺化的核心模組。

## 資料夾結構

### 檔案清單
1. **drawATP.java** (介面) - ATP繪圖介面定義
2. **DrawBase.java** (抽象類別) - 繪圖基礎類別
3. **DrawBaliseInfo.java** - 繪製Balise資訊
4. **DrawBreakOff.java** - 繪製中斷點
5. **DrawContinuous.java** - 繪製連續資料
6. **DrawCurve.java** - 繪製曲線
7. **DrawDescription.java** - 繪製描述資訊
8. **DrawDriverMessage.java** - 繪製司機員訊息
9. **DrawFailureMsg.java** - 繪製故障訊息
10. **DrawGradient.java** - 繪製坡度
11. **DrawHistogram.java** - 繪製直方圖
12. **DrawMass.java** - 繪製質量資料
13. **DrawRightAngle.java** - 繪製直角圖形
14. **DrawTrainData.java** - 繪製列車資料
15. **commonParaSetting.java** - 共用參數設定
16. **drawParameters.java** - 繪圖參數

## 核心類別深入分析

### 1. drawATP.java - ATP繪圖介面
定義所有繪圖類別必須實作的介面

#### 介面方法
```java
public interface drawATP {
    void paintBody(Graphics g) throws Exception;     // 繪製主體
    void paintHeader(Graphics g) throws Exception;   // 繪製標題
    void setData(Vector data) throws Exception;      // 設定資料
    void setDataLineColor(Color color) throws Exception;  // 設定資料線顏色
    void setDrawCurve(boolean draw) throws Exception;     // 設定是否繪製曲線
    void setStroke(Stroke stroke);                   // 設定畫筆樣式
}
```

#### 設計模式
- **策略模式**: 定義統一的繪圖介面
- **模板方法模式**: paintBody和paintHeader定義繪圖流程

### 2. DrawBase.java - 繪圖基礎抽象類別
所有繪圖類別的父類別，提供共用功能

#### 主要屬性
```java
protected Vector BaseData;              // 基礎資料
protected commonParaSetting cps;        // 共用參數設定
protected drawParameters para;          // 繪圖參數
protected Graphics2D g2;                // 主體繪圖物件
protected Graphics2D g2h;               // 標題繪圖物件

// 繪圖模式
public static final int drawByDistance = 0;  // 按距離繪製
public static final int drawByTime = 1;      // 按時間繪製
protected static int drawType = -1;

// 座標範圍
protected static int startLocation;     // 起始位置
protected static int endLocation;       // 結束位置
protected static long startTime;        // 起始時間
protected static long endTime;          // 結束時間

// 資料陣列
protected static int[] location;        // 位置陣列
protected static Date[] time;           // 時間陣列

// 滑鼠位置
protected static int mouseX;
protected static int mouseY;
```

#### 建構子
```java
public DrawBase(commonParaSetting cps, drawParameters para, Vector data) {
    this.cps = cps;
    this.para = para;
    this.BaseData = data;
    drawType = this.cps.drawByDist ? 0 : 1;  // 根據設定決定繪圖模式
    setScaleData();  // 設定比例尺資料
}
```

#### 核心方法

**1. 座標轉換 - 按距離**
```java
protected int arrangesoldierPosition(int distance) {
    // 將實際距離轉換為螢幕X座標
    return (int)(this.cps.basePointX + (distance - _$18160) / this.cps.dpiDistX);
}
```
- 公式: `螢幕X = 基準點X + (實際距離 - 起始距離) / X軸比例尺`

**2. 座標轉換 - 按時間**
```java
protected int arrangesoldierPosition(long time) {
    // 將時間轉換為螢幕X座標
    return this.cps.basePointX + (int)(((time - _$18158) / 1000L) / this.cps.dpiX);
}
```
- 公式: `螢幕X = 基準點X + (時間差(秒) / X軸比例尺)`

**3. 繪製基本訊息**
```java
protected void drawBasicMessage() {
    // 繪製車站、時間、距離等基本資訊
}
```

#### 設計亮點
1. **雙模式支援**: 同時支援按時間和按距離繪製
2. **座標抽象**: 提供統一的座標轉換方法
3. **靜態資料共享**: location和time陣列為所有繪圖物件共享

### 3. commonParaSetting.java - 共用參數設定
定義所有繪圖類別的共用參數

#### 繪圖區域設定
```java
public Graphics BodyG;              // 主體繪圖區域
public Graphics HeaderG;            // 標題繪圖區域
public int headerHeight = 20;       // 標題高度
public int headerWidth = 60;        // 標題寬度
```

#### 顏色設定
```java
public Color backgroundColor = Color.black;      // 背景色
public Color mainLineColor = Color.white;        // 主線顏色
public Color baseLineColor = Color.darkGray;     // 基準線顏色
public Color baseLineColor_light = Color.gray;   // 淺基準線顏色
public Color charColor = Color.white;            // 文字顏色
public Color stationColor = Color.pink;          // 車站顏色
public Color mouse = Color.BLUE;                 // 滑鼠指標顏色
public int BrightLine = 3;                       // 亮線寬度
```

#### 比例尺設定
```java
public double dpiX = 1.0D;          // X軸比例尺（時間模式）
public double dpiDistX = 1.0D;      // X軸比例尺（距離模式）
public int intervalX = 20;          // X軸間隔（時間模式）
public int intervalDistX = 20;      // X軸間隔（距離模式）
```

#### 繪圖範圍
```java
public int startLocation = 0;       // 起始位置
public int endLocation = 100;       // 結束位置
public long startTime = 0L;         // 起始時間
public long endTime = 1000000L;     // 結束時間
public boolean drawByDist = true;   // 是否按距離繪製
```

#### 基準點
```java
public int basePointX = -1;         // X軸基準點
```

### 4. drawParameters.java - 繪圖參數
定義單一圖表的參數

#### 主要屬性
```java
public int MaxNum = 130;            // 最大值
public int MinNum = 0;              // 最小值
public int UpperBound = 0;          // 上邊界
public double dpiY = 1.0D;          // Y軸比例尺
public int intervalY = 20;          // Y軸間隔
public String message = "";         // 圖表訊息/標題
public int sequence = 0;            // 圖表順序
public boolean drawBody = true;     // 是否繪製主體
public boolean drawValues = true;   // 是否繪製數值
```

#### 計算方法
```java
public int basePointY() {
    // 計算Y軸基準點
    return (int)(this.UpperBound + (this.MaxNum - this.MinNum) / this.dpiY);
}
```
- 公式: `基準點Y = 上邊界 + (最大值 - 最小值) / Y軸比例尺`

#### 複製方法
```java
public Object clone() {
    drawParameters newPara = new drawParameters();
    // 複製所有屬性
    newPara.dpiY = this.dpiY;
    newPara.MaxNum = this.MaxNum;
    // ... 其他屬性
    return newPara;
}
```
- 實作Cloneable介面
- 支援深拷貝

### 5. DrawCurve.java - 曲線繪製器
繪製各種曲線資料（速度曲線、煞車曲線等）

#### 主要屬性
```java
private Color _$18217 = Color.blue.brighter();  // 曲線顏色
private boolean _$18218 = true;                 // 是否繪製曲線
private Vector _$18214;                         // 曲線資料
private Vector _$26716 = new Vector();          // 曲線集合
private Stroke _$18036 = new BasicStroke();     // 畫筆樣式
private Vector _$26656 = new Vector();          // X座標集合
private Vector _$26657 = new Vector();          // Y座標集合
```

#### 繪製主體
```java
public void paintBody(Graphics g) throws Exception {
    super.paintBody(g);
    
    // 繪製半透明背景
    Color color1 = Color.gray.brighter();
    Color color2 = new Color(color1.getRed(), color1.getGreen(), 
                            color1.getBlue(), 50);  // 透明度50
    g.setColor(color2);
    g.fillRect(1, showsoldierRangeToDefence(0), 
              showBattleLingDepth(), showBattleLineRange() / 2 + 1);
    
    // 繪製曲線
    if (this._$18218)
        _$18223();  // 繪製曲線的私有方法
}
```

#### 比例尺重設
```java
public void resetScale() {
    this._$26656 = new Vector();  // 清空X座標
    this._$26657 = new Vector();  // 清空Y座標
    
    // 遍歷曲線資料
    for (int i = 0; i < this._$18214.size(); i++) {
        Vector curveData = this._$18214.get(i);
        
        // 使用二分搜尋找到對應時間點
        int j = Arrays.binarySearch((Object[])DrawBase.time, curveData.get(0));
        j = (j < 0) ? (Math.abs(j) - 1) : j;
        
        // 計算螢幕座標
        int x = showsoldierWhereToStand(DrawBase.time[j].getTime());
        int loc = DrawBase.location[j] / 100;
        
        // 處理曲線片段
        for (int k = 0; k < 9; k++) {
            int offset = 3 + 3 * k;
            int start = curveData.get(offset);
            int length = curveData.get(offset + 1);
            int radius = curveData.get(offset + 2);
            
            if (start == -1) break;  // 無更多曲線片段
            
            // 計算曲線起點和終點的螢幕座標
            // ... 複雜的座標計算邏輯
        }
    }
}
```

#### 特點
- 支援多段曲線
- 使用GeneralPath繪製平滑曲線
- 自動縮放和座標轉換

### 6-13. 其他繪圖類別

#### DrawBaliseInfo.java - Balise資訊繪製
- 繪製地面應答器（Balise）資訊
- 顯示Balise位置和ID
- 標示電報內容

#### DrawBreakOff.java - 中斷點繪製
- 繪製資料中斷點
- 標示資料缺失區域
- 視覺化資料完整性

#### DrawContinuous.java - 連續資料繪製
- 繪製連續的時間序列資料
- 如速度、位置等連續變化的參數
- 支援平滑曲線和折線兩種模式

#### DrawDescription.java - 描述資訊繪製
- 繪製文字描述
- 圖例說明
- 註解資訊

#### DrawDriverMessage.java - 司機員訊息繪製
- 繪製司機員操作訊息
- 顯示警告和提示
- 標示訊息時間和位置

#### DrawFailureMsg.java - 故障訊息繪製
- 繪製ATP系統故障
- 標示故障類型和時間
- 使用不同顏色區分嚴重程度

#### DrawGradient.java - 坡度繪製
- 繪製軌道坡度曲線
- 顯示上坡/下坡
- 標示坡度數值

#### DrawHistogram.java - 直方圖繪製
- 繪製統計直方圖
- 如速度分布、故障次數等
- 支援多種樣式

#### DrawMass.java - 質量資料繪製
- 繪製列車質量變化
- 標示質量設定點
- 影響煞車曲線計算

#### DrawRightAngle.java - 直角圖形繪製
- 繪製矩形和直角圖形
- 標示區域
- 用於突顯特定範圍

#### DrawTrainData.java - 列車資料繪製
- 繪製列車基本資料
- 如車型、車號等
- 顯示在圖表標題區

## 繪圖系統架構

### 類別繼承關係
```
drawATP (介面)
    ↑
DrawBase (抽象類別) ← commonParaSetting
    ↑                  ← drawParameters
    ├─ DrawBaliseInfo
    ├─ DrawBreakOff
    ├─ DrawContinuous
    ├─ DrawCurve
    ├─ DrawDescription
    ├─ DrawDriverMessage
    ├─ DrawFailureMsg
    ├─ DrawGradient
    ├─ DrawHistogram
    ├─ DrawMass
    ├─ DrawRightAngle
    └─ DrawTrainData
```

### 資料流程
```
ATPMissionDetail (業務資料)
    ↓
drawParameters (繪圖參數)
    ↓
DrawXXX (具體繪圖類別)
    ↓
Graphics2D (Java繪圖API)
    ↓
螢幕/列印/檔案
```

### 座標系統

#### X軸 - 時間或距離
```
實際時間/距離 → 螢幕X座標

按時間: X = basePointX + (time - startTime) / dpiX
按距離: X = basePointX + (location - startLocation) / dpiDistX
```

#### Y軸 - 數值
```
實際數值 → 螢幕Y座標

Y = basePointY - (value - MinNum) / dpiY
```

### 雙模式設計

**時間模式**
- X軸表示時間流逝
- 適合分析時間序列事件
- 使用dpiX和intervalX

**距離模式**
- X軸表示位置變化
- 適合分析空間分布
- 使用dpiDistX和intervalDistX

## 與其他資料夾的關聯

### 依賴關係
1. **core_re**: 
   - 使用ATPMissionDetail取得資料
   - 使用Station取得車站資訊

2. **decoder_re**: 
   - 使用解碼後的資料

3. **Tools_re**: 
   - 使用SortTable進行資料排序
   - 使用ColumnComparator

4. **Java AWT/Swing**: 
   - Graphics, Graphics2D
   - Color, Stroke, BasicStroke
   - 標準繪圖API

### 被依賴關係
1. **ui_re**: 
   - GUI介面使用這些繪圖類別
   - 在JPanel或JFrame中顯示圖表
   - 列印和匯出功能

## 技術特點

### 1. 抽象基礎類別設計
```java
public abstract class DrawBase implements drawATP
```
- 實作通用功能
- 子類別只需實作特定邏輯
- 遵循DRY原則

### 2. 參數物件模式
```java
commonParaSetting cps   // 共用參數
drawParameters para     // 個別參數
```
- 避免參數過多
- 便於參數傳遞和修改
- 支援參數複製

### 3. 雙緩衝繪圖
```java
protected Graphics2D g2;   // 主體繪圖
protected Graphics2D g2h;  // 標題繪圖
```
- 分離標題和主體
- 減少閃爍
- 提升繪圖效能

### 4. 座標轉換抽象
```java
protected int arrangesoldierPosition(int distance)
protected int arrangesoldierPosition(long time)
```
- 隱藏座標計算細節
- 統一座標系統
- 支援不同比例尺

### 5. 策略模式
```java
drawType = this.cps.drawByDist ? 0 : 1;
```
- 執行時期選擇繪圖策略
- 時間模式vs距離模式
- 不需修改程式碼

### 6. 顏色透明度
```java
Color color2 = new Color(r, g, b, 50);  // alpha = 50
```
- 支援半透明效果
- 圖層疊加
- 視覺美化

### 7. 使用GeneralPath
```java
GeneralPath path = new GeneralPath();
path.moveTo(x1, y1);
path.lineTo(x2, y2);
path.curveTo(...);  // 貝茲曲線
g2.draw(path);
```
- 繪製複雜路徑
- 支援平滑曲線
- 高品質輸出

## 效能考量

### 優點
1. **資料共享**: location和time陣列為靜態，所有繪圖物件共享
2. **延遲繪製**: 只在需要時才繪製
3. **座標快取**: resetScale()預先計算座標

### 可改進之處
1. **重複計算**: 每次paintBody可能重新計算座標
2. **記憶體使用**: 每個繪圖物件都儲存完整資料Vector
3. **二分搜尋**: Arrays.binarySearch在大資料集上可能較慢

## 程式碼品質

### 優點
1. **介面導向**: 定義drawATP介面
2. **繼承層次清晰**: DrawBase提供通用功能
3. **參數封裝**: 使用參數物件
4. **支援克隆**: drawParameters實作Cloneable

### 可改進之處
1. **變數命名**: 
   ```java
   private Vector _$26656;  // 應命名為 xCoordinates
   private boolean _$18218; // 應命名為 shouldDrawCurve
   ```
2. **方法命名**: 
   ```java
   protected int arrangesoldierPosition(int distance)
   // 應該命名為 convertDistanceToScreenX(int distance)
   ```
3. **註解缺乏**: 幾乎沒有註解說明座標轉換邏輯
4. **魔術數字**: 
   ```java
   for (byte b1 = 0; b1 < 9; b1++)  // 9的意義？
   int i2 = 3 + 3 * b1;             // 為何是3？
   ```

## 建議改進方向

### 短期改進
1. 重新命名變數和方法
2. 增加座標轉換的註解
3. 定義魔術數字為常數

### 中期改進
1. 實作座標快取機制
2. 使用更現代的繪圖API（如JavaFX）
3. 支援圖表匯出（PNG, SVG等）
4. 加入縮放和平移互動

### 長期改進
1. 考慮使用繪圖函式庫（如JFreeChart）
2. 實作硬體加速繪圖
3. 支援3D視覺化
4. 加入動畫效果

## 應用場景

### 1. 運行分析
- 速度-時間曲線
- 速度-距離曲線
- 分析列車運行特性

### 2. 故障分析
- 故障分布圖
- 故障時間線
- 與速度、位置的關聯

### 3. 司機員行為分析
- 操作時間線
- 反應時間統計
- 訊息確認延遲

### 4. 軌道條件分析
- 坡度曲線
- 曲線半徑
- 臨時速限分布

### 5. 報告生成
- 列印圖表
- 匯出到報告
- 多圖表組合

## 結論

`drawGraphics_re` 資料夾是ATP系統的視覺化引擎，提供了：

### 核心價值
1. **資料視覺化**: 將複雜的ATP資料轉換為直觀的圖表
2. **雙模式支援**: 時間模式和距離模式滿足不同分析需求
3. **多樣化圖表**: 曲線、直方圖、訊息標記等多種圖表類型
4. **統一介面**: 所有繪圖類別實作相同介面，易於擴充

### 關鍵特色
- **抽象基礎類別**: DrawBase提供通用功能，減少重複程式碼
- **參數物件**: commonParaSetting和drawParameters簡化參數管理
- **座標轉換**: 自動處理實際值到螢幕座標的轉換
- **美觀輸出**: 支援顏色、透明度、平滑曲線等視覺效果

### 系統角色
- 將decoder_re解碼的資料視覺化
- 為ui_re提供圖表元件
- 支援報告生成和列印
- 協助資料分析和決策

這個資料夾包含16個檔案，實現了完整的ATP資料視覺化系統。主要挑戰是變數命名和註解，但整體架構合理，功能完整。建議優先改善程式碼可讀性，然後考慮引入現代繪圖技術提升使用者體驗。
