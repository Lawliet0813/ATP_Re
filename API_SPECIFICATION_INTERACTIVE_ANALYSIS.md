# ATP 互動式圖表與自動分析 API 規格書

## 文件資訊
- **版本**: 1.0
- **日期**: 2025-10-28
- **目的**: 定義互動式圖表與自動分析功能的 REST API 規格

## 目錄
1. [API 概述](#1-api-概述)
2. [認證與授權](#2-認證與授權)
3. [分析 API](#3-分析-api)
4. [報告 API](#4-報告-api)
5. [圖表數據 API](#5-圖表數據-api)
6. [錯誤處理](#6-錯誤處理)
7. [範例](#7-範例)

---

## 1. API 概述

### 1.1 基礎資訊
- **Base URL**: `http://localhost:8000/api/v1`
- **協定**: HTTP/HTTPS
- **數據格式**: JSON
- **字元編碼**: UTF-8
- **API 版本**: v1

### 1.2 通用請求標頭
```
Content-Type: application/json
Accept: application/json
Authorization: Bearer <token>
```

### 1.3 通用響應格式
```json
{
  "success": true,
  "data": {},
  "message": "操作成功",
  "timestamp": "2024-10-28T10:00:00Z"
}
```

---

## 2. 認證與授權

### 2.1 獲取 Token
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password123"
}
```

**響應**:
```json
{
  "success": true,
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "token_type": "bearer",
    "expires_in": 3600
  }
}
```

---

## 3. 分析 API

### 3.1 速度分析

#### 端點
```http
GET /api/v1/analysis/speed/{mission_id}
```

#### 參數
| 參數 | 類型 | 必填 | 說明 |
|------|------|------|------|
| mission_id | string | 是 | 任務 ID (UUID) |
| start_time | datetime | 否 | 開始時間 (ISO 8601) |
| end_time | datetime | 否 | 結束時間 (ISO 8601) |
| include_trend | boolean | 否 | 是否包含趨勢分析 (預設: false) |

#### 響應
```json
{
  "success": true,
  "data": {
    "mission_id": "123e4567-e89b-12d3-a456-426614174000",
    "analysis_time": "2024-10-28T10:00:00Z",
    "speed_statistics": {
      "max_speed": 120,
      "min_speed": 0,
      "avg_speed": 65.5,
      "median_speed": 68.0,
      "std_deviation": 15.3
    },
    "overspeed_analysis": {
      "count": 5,
      "total_duration": 180,
      "max_excess_speed": 8.0,
      "events": [
        {
          "start_time": "2024-10-28T10:15:23Z",
          "end_time": "2024-10-28T10:16:05Z",
          "start_location": 5240,
          "end_location": 5580,
          "max_excess_speed": 8.0,
          "duration": 42,
          "severity": "medium"
        }
      ]
    },
    "braking_analysis": {
      "total_braking_points": 12,
      "harsh_braking_count": 1,
      "avg_deceleration": -1.2
    },
    "speed_trend": {
      "moving_avg_short": [60, 62, 64, 66, 68],
      "moving_avg_long": [58, 59, 60, 61, 62],
      "trend_line": {
        "slope": 0.5,
        "intercept": 60.0,
        "r_squared": 0.85
      },
      "prediction": {
        "next_10_points": [70, 71, 72, 73, 74, 75, 76, 77, 78, 79],
        "confidence_interval": [5, 5, 5, 6, 6, 6, 7, 7, 8, 8]
      }
    }
  }
}
```

### 3.2 異常偵測

#### 端點
```http
GET /api/v1/analysis/anomalies/{mission_id}
```

#### 參數
| 參數 | 類型 | 必填 | 說明 |
|------|------|------|------|
| mission_id | string | 是 | 任務 ID |
| anomaly_types | array | 否 | 偵測類型 (overspeed, harsh_brake, speed_fluctuation) |
| severity_threshold | string | 否 | 嚴重度閥值 (low, medium, high) |

#### 響應
```json
{
  "success": true,
  "data": {
    "mission_id": "123e4567-e89b-12d3-a456-426614174000",
    "total_anomalies": 8,
    "anomaly_summary": {
      "overspeed": 5,
      "harsh_braking": 1,
      "speed_fluctuation": 2
    },
    "anomalies": [
      {
        "id": "anomaly-001",
        "type": "overspeed",
        "severity": "medium",
        "timestamp": "2024-10-28T10:15:23Z",
        "location": 5240,
        "description": "速度超過目標速度 8 km/h",
        "details": {
          "actual_speed": 88,
          "target_speed": 80,
          "excess_speed": 8,
          "duration": 42
        },
        "recommendation": "建議加強速度控制，提前關注限速標誌"
      },
      {
        "id": "anomaly-002",
        "type": "harsh_braking",
        "severity": "high",
        "timestamp": "2024-10-28T10:28:33Z",
        "location": 25800,
        "description": "急煞車，減速度 -2.5 m/s²",
        "details": {
          "deceleration": -2.5,
          "speed_before": 70,
          "speed_after": 45,
          "time_span": 2.8
        },
        "recommendation": "建議提前預判路況，平滑減速"
      }
    ],
    "anomaly_distribution": {
      "by_time": [
        {"hour": 10, "count": 5},
        {"hour": 11, "count": 3}
      ],
      "by_location": [
        {"location_range": "0-10km", "count": 2},
        {"location_range": "10-20km", "count": 3},
        {"location_range": "20-30km", "count": 3}
      ]
    }
  }
}
```

### 3.3 趨勢分析

#### 端點
```http
GET /api/v1/analysis/trends/{mission_id}
```

#### 響應
```json
{
  "success": true,
  "data": {
    "mission_id": "123e4567-e89b-12d3-a456-426614174000",
    "speed_trend": {
      "overall_direction": "increasing",
      "change_rate": "+5%",
      "moving_averages": {
        "short_term": [60, 62, 64, 66, 68],
        "long_term": [58, 59, 60, 61, 62]
      },
      "trend_line": {
        "equation": "y = 0.5x + 60",
        "slope": 0.5,
        "intercept": 60.0
      }
    },
    "time_consumption_analysis": {
      "total_segments": 7,
      "segments": [
        {
          "from_station": "台北",
          "to_station": "板橋",
          "scheduled_time": 300,
          "actual_time": 320,
          "delay": 20,
          "delay_percent": 6.67,
          "status": "delayed"
        }
      ],
      "overall_punctuality": 93.3
    },
    "parking_accuracy_trend": {
      "total_stops": 8,
      "accuracy_improvement": true,
      "avg_deviation_trend": [-50, -40, -35, -30, -25],
      "stops": [
        {
          "station": "板橋",
          "target_location": 10000,
          "actual_location": 9970,
          "deviation": -30,
          "accuracy": "accurate"
        }
      ]
    }
  }
}
```

### 3.4 事件分析

#### 端點
```http
GET /api/v1/analysis/events/{mission_id}
```

#### 參數
| 參數 | 類型 | 必填 | 說明 |
|------|------|------|------|
| mission_id | string | 是 | 任務 ID |
| event_types | array | 否 | 事件類型過濾 |
| start_time | datetime | 否 | 開始時間 |
| end_time | datetime | 否 | 結束時間 |

#### 響應
```json
{
  "success": true,
  "data": {
    "mission_id": "123e4567-e89b-12d3-a456-426614174000",
    "total_events": 45,
    "event_summary": {
      "brake": 12,
      "overspeed": 5,
      "failure": 0,
      "driver_message": 8,
      "balise": 15,
      "station": 8
    },
    "events": [
      {
        "id": "event-001",
        "type": "brake",
        "timestamp": "2024-10-28T10:15:00Z",
        "location": 5000,
        "description": "煞車啟動",
        "details": {
          "brake_type": "normal",
          "deceleration": -1.2
        }
      }
    ],
    "event_timeline": [
      {
        "time": "2024-10-28T10:00:00Z",
        "events": ["departure", "balise_signal"]
      }
    ],
    "critical_events": [
      {
        "id": "event-025",
        "type": "overspeed",
        "severity": "high",
        "timestamp": "2024-10-28T10:30:00Z",
        "requires_attention": true
      }
    ]
  }
}
```

### 3.5 綜合統計

#### 端點
```http
GET /api/v1/analysis/statistics/{mission_id}
```

#### 響應
```json
{
  "success": true,
  "data": {
    "mission_id": "123e4567-e89b-12d3-a456-426614174000",
    "mission_info": {
      "date": "2024-10-28",
      "work_shift": "A001",
      "train_running": "T123",
      "driver_id": "D456",
      "vehicle_id": "V789"
    },
    "operational_statistics": {
      "total_distance": 45200,
      "total_time": 3120,
      "avg_speed": 52.1,
      "max_speed": 90,
      "min_speed": 0,
      "data_points": 3120,
      "data_quality": 99.8
    },
    "event_statistics": {
      "total_events": 45,
      "event_breakdown": {
        "brake": 12,
        "overspeed": 5,
        "failure": 0,
        "driver_message": 8,
        "balise": 15,
        "station": 8
      }
    },
    "parking_statistics": {
      "total_stops": 8,
      "accurate_stops": 6,
      "acceptable_stops": 2,
      "accuracy_rate": 75.0,
      "avg_deviation": 35,
      "max_deviation": 120
    },
    "performance_score": {
      "overall": 85,
      "speed_control": 88,
      "parking_accuracy": 75,
      "punctuality": 93,
      "safety": 90
    }
  }
}
```

---

## 4. 報告 API

### 4.1 生成報告

#### 端點
```http
POST /api/v1/reports/generate
```

#### 請求體
```json
{
  "mission_id": "123e4567-e89b-12d3-a456-426614174000",
  "format": "pdf",
  "include_sections": [
    "summary",
    "speed_analysis",
    "anomaly_detection",
    "event_analysis",
    "trends",
    "recommendations"
  ],
  "language": "zh_TW",
  "include_charts": true
}
```

#### 參數說明
| 參數 | 類型 | 必填 | 說明 |
|------|------|------|------|
| mission_id | string | 是 | 任務 ID |
| format | string | 是 | 報告格式 (pdf, html, json, markdown) |
| include_sections | array | 否 | 包含的章節 |
| language | string | 否 | 語言 (zh_TW, en_US) |
| include_charts | boolean | 否 | 是否包含圖表 (預設: true) |

#### 響應
```json
{
  "success": true,
  "data": {
    "report_id": "report-20241028-001",
    "status": "generating",
    "download_url": null,
    "progress": 0,
    "estimated_time": 30
  }
}
```

### 4.2 檢查報告狀態

#### 端點
```http
GET /api/v1/reports/status/{report_id}
```

#### 響應
```json
{
  "success": true,
  "data": {
    "report_id": "report-20241028-001",
    "status": "completed",
    "download_url": "/api/v1/reports/download/report-20241028-001",
    "expires_at": "2024-10-29T10:00:00Z",
    "file_size": 2048576,
    "format": "pdf"
  }
}
```

### 4.3 下載報告

#### 端點
```http
GET /api/v1/reports/download/{report_id}
```

#### 響應
二進制文件流（PDF、HTML 等）

---

## 5. 圖表數據 API

### 5.1 獲取圖表數據

#### 端點
```http
GET /api/v1/chart/data/{mission_id}
```

#### 參數
| 參數 | 類型 | 必填 | 說明 |
|------|------|------|------|
| mission_id | string | 是 | 任務 ID |
| chart_type | string | 是 | 圖表類型 (speed_curve, event_timeline, histogram) |
| mode | string | 否 | 模式 (time, distance) 預設: time |
| start_range | number | 否 | 開始範圍 |
| end_range | number | 否 | 結束範圍 |
| downsample | boolean | 否 | 是否降採樣 (預設: false) |
| sample_rate | number | 否 | 採樣率 (1-100) |

#### 響應 (速度曲線)
```json
{
  "success": true,
  "data": {
    "chart_type": "speed_curve",
    "mode": "time",
    "data_points": 1000,
    "x_axis": {
      "label": "時間",
      "unit": "HH:mm:ss",
      "values": ["10:00:00", "10:00:01", "10:00:02"]
    },
    "y_axis": {
      "label": "速度",
      "unit": "km/h",
      "min": 0,
      "max": 120
    },
    "series": [
      {
        "name": "實際速度",
        "type": "line",
        "color": "#00ff00",
        "data": [0, 5, 10, 15, 20]
      },
      {
        "name": "目標速度",
        "type": "line",
        "color": "#ffff00",
        "line_style": "dashed",
        "data": [0, 10, 20, 30, 40]
      }
    ],
    "annotations": [
      {
        "type": "overspeed_zone",
        "start": "10:15:23",
        "end": "10:16:05",
        "color": "#ff000033"
      }
    ],
    "events": [
      {
        "timestamp": "10:05:00",
        "type": "station",
        "label": "板橋站"
      }
    ]
  }
}
```

### 5.2 獲取事件時間軸數據

#### 端點
```http
GET /api/v1/chart/events/{mission_id}
```

#### 響應
```json
{
  "success": true,
  "data": {
    "chart_type": "event_timeline",
    "events": [
      {
        "id": "event-001",
        "timestamp": "2024-10-28T10:00:00Z",
        "location": 0,
        "type": "departure",
        "color": "#00ff00",
        "icon": "🚆",
        "label": "發車"
      },
      {
        "id": "event-002",
        "timestamp": "2024-10-28T10:05:00Z",
        "location": 5000,
        "type": "station",
        "color": "#0000ff",
        "icon": "🏢",
        "label": "板橋站"
      }
    ],
    "event_layers": {
      "brake": true,
      "overspeed": true,
      "failure": false,
      "station": true
    }
  }
}
```

---

## 6. 錯誤處理

### 6.1 錯誤響應格式
```json
{
  "success": false,
  "error": {
    "code": "MISSION_NOT_FOUND",
    "message": "找不到指定的任務",
    "details": "Mission ID: 123e4567-e89b-12d3-a456-426614174000 does not exist",
    "timestamp": "2024-10-28T10:00:00Z"
  }
}
```

### 6.2 HTTP 狀態碼

| 狀態碼 | 說明 |
|--------|------|
| 200 | 成功 |
| 201 | 創建成功 |
| 400 | 請求參數錯誤 |
| 401 | 未授權 |
| 403 | 禁止訪問 |
| 404 | 資源不存在 |
| 429 | 請求過於頻繁 |
| 500 | 伺服器內部錯誤 |
| 503 | 服務不可用 |

### 6.3 錯誤代碼

| 錯誤代碼 | HTTP 狀態碼 | 說明 |
|----------|-------------|------|
| INVALID_PARAMETERS | 400 | 請求參數無效 |
| MISSION_NOT_FOUND | 404 | 任務不存在 |
| UNAUTHORIZED | 401 | 未授權訪問 |
| RATE_LIMIT_EXCEEDED | 429 | 超過速率限制 |
| ANALYSIS_FAILED | 500 | 分析過程失敗 |
| REPORT_GENERATION_FAILED | 500 | 報告生成失敗 |
| DATABASE_ERROR | 500 | 資料庫錯誤 |

---

## 7. 範例

### 7.1 完整工作流程範例

#### 步驟 1: 登入獲取 Token
```bash
curl -X POST http://localhost:8000/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

#### 步驟 2: 獲取任務列表
```bash
curl -X GET http://localhost:8000/api/v1/tasks/ \
  -H "Authorization: Bearer <token>"
```

#### 步驟 3: 分析速度
```bash
curl -X GET "http://localhost:8000/api/v1/analysis/speed/123e4567?include_trend=true" \
  -H "Authorization: Bearer <token>"
```

#### 步驟 4: 偵測異常
```bash
curl -X GET "http://localhost:8000/api/v1/analysis/anomalies/123e4567" \
  -H "Authorization: Bearer <token>"
```

#### 步驟 5: 生成報告
```bash
curl -X POST http://localhost:8000/api/v1/reports/generate \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "mission_id": "123e4567-e89b-12d3-a456-426614174000",
    "format": "pdf",
    "include_sections": ["summary", "speed_analysis", "anomaly_detection"],
    "language": "zh_TW"
  }'
```

#### 步驟 6: 檢查報告狀態
```bash
curl -X GET http://localhost:8000/api/v1/reports/status/report-20241028-001 \
  -H "Authorization: Bearer <token>"
```

#### 步驟 7: 下載報告
```bash
curl -X GET http://localhost:8000/api/v1/reports/download/report-20241028-001 \
  -H "Authorization: Bearer <token>" \
  -o report.pdf
```

### 7.2 Python 客戶端範例

```python
import requests

class ATPAnalysisClient:
    def __init__(self, base_url, username, password):
        self.base_url = base_url
        self.token = self._login(username, password)
    
    def _login(self, username, password):
        response = requests.post(
            f"{self.base_url}/auth/login",
            json={"username": username, "password": password}
        )
        return response.json()["data"]["access_token"]
    
    @property
    def headers(self):
        return {"Authorization": f"Bearer {self.token}"}
    
    def analyze_speed(self, mission_id, include_trend=False):
        response = requests.get(
            f"{self.base_url}/analysis/speed/{mission_id}",
            params={"include_trend": include_trend},
            headers=self.headers
        )
        return response.json()
    
    def detect_anomalies(self, mission_id):
        response = requests.get(
            f"{self.base_url}/analysis/anomalies/{mission_id}",
            headers=self.headers
        )
        return response.json()
    
    def generate_report(self, mission_id, format="pdf"):
        response = requests.post(
            f"{self.base_url}/reports/generate",
            json={
                "mission_id": mission_id,
                "format": format,
                "include_sections": [
                    "summary",
                    "speed_analysis",
                    "anomaly_detection",
                    "recommendations"
                ]
            },
            headers=self.headers
        )
        return response.json()

# 使用範例
client = ATPAnalysisClient(
    base_url="http://localhost:8000/api/v1",
    username="admin",
    password="password123"
)

# 分析速度
speed_result = client.analyze_speed("123e4567-e89b-12d3-a456-426614174000", include_trend=True)
print(f"最高速度: {speed_result['data']['speed_statistics']['max_speed']} km/h")

# 偵測異常
anomalies = client.detect_anomalies("123e4567-e89b-12d3-a456-426614174000")
print(f"偵測到 {anomalies['data']['total_anomalies']} 個異常")

# 生成報告
report = client.generate_report("123e4567-e89b-12d3-a456-426614174000", format="pdf")
print(f"報告 ID: {report['data']['report_id']}")
```

---

## 附錄

### A. 速率限制

| 端點類型 | 限制 | 時間窗口 |
|----------|------|----------|
| 分析 API | 100 請求 | 每分鐘 |
| 報告生成 | 10 請求 | 每分鐘 |
| 圖表數據 | 200 請求 | 每分鐘 |

### B. 版本歷史

| 版本 | 日期 | 變更內容 |
|------|------|----------|
| 1.0 | 2025-10-28 | 初版發布 |

---

**文件結束**
