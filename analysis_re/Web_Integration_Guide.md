# Web UI Integration Guide

## Overview
This guide explains how to integrate the ATP Data Analysis API with a Web UI, enabling browser-based visualization and analysis of ATP system data.

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                   Web Browser                            │
│  ┌──────────────────────────────────────────────┐       │
│  │  Web UI (HTML/CSS/JavaScript)                │       │
│  │  - Charts (Chart.js / D3.js)                 │       │
│  │  - Interactive controls                       │       │
│  │  - Real-time updates                          │       │
│  └──────────────────────────────────────────────┘       │
└─────────────────────────────────────────────────────────┘
                        ↕ HTTP/WebSocket
┌─────────────────────────────────────────────────────────┐
│                   Web Server Layer                       │
│  ┌──────────────────────────────────────────────┐       │
│  │  REST API / WebSocket Server                 │       │
│  │  - Spring Boot / Servlet                      │       │
│  │  - JSON serialization                         │       │
│  │  - Authentication                             │       │
│  └──────────────────────────────────────────────┘       │
└─────────────────────────────────────────────────────────┘
                        ↕
┌─────────────────────────────────────────────────────────┐
│              ATP Analysis API (Java)                     │
│  ┌──────────────────────────────────────────────┐       │
│  │  AnalysisAPIImpl                             │       │
│  │  - SpeedAnalyzer                             │       │
│  │  - EventDetector                             │       │
│  │  - ParkingAccuracyAnalyzer                   │       │
│  │  - StatisticalSummary                        │       │
│  └──────────────────────────────────────────────┘       │
└─────────────────────────────────────────────────────────┘
```

## Implementation Options

### Option 1: Spring Boot REST API (Recommended)

#### 1. Add Dependencies
```xml
<!-- pom.xml -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.7.0</version>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.9.0</version>
    </dependency>
</dependencies>
```

#### 2. Create REST Controller
```java
package com.MiTAC.TRA.ATP.web;

import analysis.*;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import java.util.Vector;
import java.util.Date;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*")
public class AnalysisController {
    
    private final AnalysisAPI analysisAPI;
    private final Gson gson;
    
    public AnalysisController() {
        this.analysisAPI = new AnalysisAPIImpl();
        this.gson = new Gson();
    }
    
    @PostMapping("/speed")
    public String analyzeSpeed(@RequestBody SpeedDataRequest request) {
        SpeedAnalysisResult result = analysisAPI.analyzeSpeedCurve(
            request.timestamps,
            request.locations,
            request.speeds,
            request.targetSpeeds
        );
        return gson.toJson(result);
    }
    
    @PostMapping("/events")
    public String detectEvents(@RequestBody EventDataRequest request) {
        EventDetectionResult result = analysisAPI.detectEvents(
            request.timestamps,
            request.locations,
            request.speeds,
            request.targetSpeeds,
            request.failureData
        );
        return gson.toJson(result);
    }
    
    @PostMapping("/parking")
    public String analyzeParking(@RequestBody ParkingDataRequest request) {
        ParkingAnalysisResult result = analysisAPI.analyzeParkingAccuracy(
            request.timestamps,
            request.locations,
            request.speeds,
            request.stationNames,
            request.stationLocations
        );
        return gson.toJson(result);
    }
    
    @PostMapping("/summary")
    public String calculateSummary(@RequestBody SummaryDataRequest request) {
        SummaryResult result = analysisAPI.calculateStatistics(
            request.timestamps,
            request.locations,
            request.speeds
        );
        return gson.toJson(result);
    }
    
    @PostMapping("/report")
    public String generateReport(@RequestBody ReportDataRequest request) {
        // Perform all analyses
        SpeedAnalysisResult speedResult = analysisAPI.analyzeSpeedCurve(
            request.timestamps, request.locations, 
            request.speeds, request.targetSpeeds
        );
        
        EventDetectionResult eventResult = analysisAPI.detectEvents(
            request.timestamps, request.locations,
            request.speeds, request.targetSpeeds,
            request.failureData
        );
        
        ParkingAnalysisResult parkingResult = analysisAPI.analyzeParkingAccuracy(
            request.timestamps, request.locations, request.speeds,
            request.stationNames, request.stationLocations
        );
        
        SummaryResult summaryResult = analysisAPI.calculateComprehensiveStatistics(
            request.timestamps, request.locations, request.speeds,
            eventResult, parkingResult
        );
        
        String report = analysisAPI.exportAnalysisReport(
            speedResult, eventResult, parkingResult, summaryResult
        );
        
        return gson.toJson(new ReportResponse(report));
    }
    
    // Request classes
    static class SpeedDataRequest {
        Vector<Date> timestamps;
        Vector<Integer> locations;
        Vector<Integer> speeds;
        Vector<Integer> targetSpeeds;
    }
    
    static class EventDataRequest extends SpeedDataRequest {
        Vector failureData;
    }
    
    static class ParkingDataRequest extends SpeedDataRequest {
        Vector<String> stationNames;
        Vector<Integer> stationLocations;
    }
    
    static class SummaryDataRequest {
        Vector<Date> timestamps;
        Vector<Integer> locations;
        Vector<Integer> speeds;
    }
    
    static class ReportDataRequest extends EventDataRequest {
        Vector<String> stationNames;
        Vector<Integer> stationLocations;
    }
    
    static class ReportResponse {
        String report;
        ReportResponse(String report) {
            this.report = report;
        }
    }
}
```

#### 3. Create Main Application
```java
package com.MiTAC.TRA.ATP.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ATPWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ATPWebApplication.class, args);
    }
}
```

### Option 2: Servlet-based API

#### Create Servlet
```java
package com.MiTAC.TRA.ATP.web;

import analysis.*;
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Vector;

public class AnalysisServlet extends HttpServlet {
    
    private AnalysisAPI analysisAPI;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        analysisAPI = new AnalysisAPIImpl();
        gson = new Gson();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            if ("/speed".equals(path)) {
                handleSpeedAnalysis(request, out);
            } else if ("/events".equals(path)) {
                handleEventDetection(request, out);
            } else if ("/parking".equals(path)) {
                handleParkingAnalysis(request, out);
            } else if ("/summary".equals(path)) {
                handleSummary(request, out);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"error\":\"Endpoint not found\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    private void handleSpeedAnalysis(HttpServletRequest request, PrintWriter out) 
            throws IOException {
        // Parse request and perform analysis
        // Write JSON response
    }
    
    // Other handler methods...
}
```

#### Configure web.xml
```xml
<web-app>
    <servlet>
        <servlet-name>AnalysisServlet</servlet-name>
        <servlet-class>com.MiTAC.TRA.ATP.web.AnalysisServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AnalysisServlet</servlet-name>
        <url-pattern>/api/analysis/*</url-pattern>
    </servlet-mapping>
</web-app>
```

## Frontend Implementation

### HTML Structure
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ATP Data Analysis</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>ATP Data Analysis Dashboard</h1>
        
        <!-- Data Upload Section -->
        <div class="upload-section">
            <input type="file" id="dataFile" accept=".json">
            <button onclick="loadData()">Load Data</button>
        </div>
        
        <!-- Analysis Controls -->
        <div class="controls">
            <button onclick="analyzeSpeed()">Analyze Speed</button>
            <button onclick="detectEvents()">Detect Events</button>
            <button onclick="analyzeParking()">Analyze Parking</button>
            <button onclick="generateReport()">Generate Report</button>
        </div>
        
        <!-- Charts -->
        <div class="charts">
            <canvas id="speedChart"></canvas>
            <canvas id="eventChart"></canvas>
        </div>
        
        <!-- Results -->
        <div id="results"></div>
        <pre id="report"></pre>
    </div>
    
    <script src="app.js"></script>
</body>
</html>
```

### JavaScript (app.js)
```javascript
const API_BASE = 'http://localhost:8080/api/analysis';

let currentData = null;

// Load data from file
function loadData() {
    const file = document.getElementById('dataFile').files[0];
    const reader = new FileReader();
    
    reader.onload = function(e) {
        currentData = JSON.parse(e.target.result);
        console.log('Data loaded:', currentData);
    };
    
    reader.readAsText(file);
}

// Analyze speed curve
async function analyzeSpeed() {
    if (!currentData) {
        alert('Please load data first');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/speed`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                timestamps: currentData.timestamps,
                locations: currentData.locations,
                speeds: currentData.speeds,
                targetSpeeds: currentData.targetSpeeds
            })
        });
        
        const result = await response.json();
        displaySpeedAnalysis(result);
        drawSpeedChart(result);
    } catch (error) {
        console.error('Error:', error);
        alert('Analysis failed: ' + error.message);
    }
}

// Display speed analysis results
function displaySpeedAnalysis(result) {
    const html = `
        <div class="analysis-result">
            <h3>Speed Analysis Results</h3>
            <p>Max Speed: ${result.maxSpeed} km/h</p>
            <p>Min Speed: ${result.minSpeed} km/h</p>
            <p>Average Speed: ${result.avgSpeed.toFixed(2)} km/h</p>
            <p>Overspeed Count: ${result.overspeedCount}</p>
            <p>Braking Points: ${result.brakingPoints.length}</p>
            <p>Total Distance: ${(result.totalDistance / 1000).toFixed(2)} km</p>
            <p>Total Time: ${(result.totalTime / 60000).toFixed(2)} minutes</p>
        </div>
    `;
    document.getElementById('results').innerHTML = html;
}

// Draw speed chart
function drawSpeedChart(result) {
    const ctx = document.getElementById('speedChart').getContext('2d');
    
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: currentData.timestamps.map(t => new Date(t).toLocaleTimeString()),
            datasets: [{
                label: 'Speed',
                data: currentData.speeds,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }, {
                label: 'Target Speed',
                data: currentData.targetSpeeds,
                borderColor: 'rgb(255, 205, 86)',
                borderDash: [5, 5],
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Speed (km/h)'
                    }
                }
            }
        }
    });
}

// Detect events
async function detectEvents() {
    if (!currentData) {
        alert('Please load data first');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/events`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(currentData)
        });
        
        const result = await response.json();
        displayEventResults(result);
    } catch (error) {
        console.error('Error:', error);
    }
}

// Display event results
function displayEventResults(result) {
    const html = `
        <div class="analysis-result">
            <h3>Event Detection Results</h3>
            <p>Total Events: ${result.totalEventCount}</p>
            <p>Brake Events: ${result.brakeEvents.length}</p>
            <p>Overspeed Events: ${result.overspeedEvents.length}</p>
            <p>Failure Events: ${result.failureEvents.length}</p>
        </div>
    `;
    document.getElementById('results').innerHTML += html;
}

// Analyze parking
async function analyzeParking() {
    if (!currentData) {
        alert('Please load data first');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/parking`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(currentData)
        });
        
        const result = await response.json();
        displayParkingResults(result);
    } catch (error) {
        console.error('Error:', error);
    }
}

// Display parking results
function displayParkingResults(result) {
    const html = `
        <div class="analysis-result">
            <h3>Parking Accuracy Results</h3>
            <p>Total Parkings: ${result.totalParkings}</p>
            <p>Accurate Parkings (±50cm): ${result.accurateParkings}</p>
            <p>Accuracy Rate: ${result.accuracyRate.toFixed(2)}%</p>
            <p>Average Deviation: ${(result.avgDeviation / 100).toFixed(2)} m</p>
            <p>Max Deviation: ${(result.maxDeviation / 100).toFixed(2)} m</p>
        </div>
    `;
    document.getElementById('results').innerHTML += html;
}

// Generate comprehensive report
async function generateReport() {
    if (!currentData) {
        alert('Please load data first');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/report`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(currentData)
        });
        
        const result = await response.json();
        document.getElementById('report').textContent = result.report;
    } catch (error) {
        console.error('Error:', error);
    }
}
```

### CSS (styles.css)
```css
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 20px;
    background-color: #f0f0f0;
}

.container {
    max-width: 1200px;
    margin: 0 auto;
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

h1 {
    color: #333;
    border-bottom: 2px solid #4CAF50;
    padding-bottom: 10px;
}

.upload-section, .controls {
    margin: 20px 0;
}

button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-right: 10px;
}

button:hover {
    background-color: #45a049;
}

.charts {
    margin: 30px 0;
}

canvas {
    margin: 20px 0;
}

.analysis-result {
    background-color: #f9f9f9;
    padding: 15px;
    margin: 10px 0;
    border-left: 4px solid #4CAF50;
}

.analysis-result h3 {
    margin-top: 0;
    color: #4CAF50;
}

#report {
    background-color: #f5f5f5;
    padding: 20px;
    border-radius: 4px;
    white-space: pre-wrap;
    font-family: monospace;
}
```

## Data Format

### JSON Request Format
```json
{
  "timestamps": ["2024-01-01T10:00:00Z", "2024-01-01T10:00:01Z"],
  "locations": [0, 100, 200],
  "speeds": [0, 50, 80],
  "targetSpeeds": [60, 60, 80],
  "stationNames": ["Station A", "Station B"],
  "stationLocations": [1000, 5000],
  "failureData": []
}
```

### JSON Response Format
```json
{
  "maxSpeed": 120,
  "minSpeed": 0,
  "avgSpeed": 65.5,
  "overspeedCount": 5,
  "brakingPoints": [...],
  "totalDistance": 10000,
  "totalTime": 600000
}
```

## Security Considerations

1. **Authentication**: Add JWT or session-based authentication
2. **CORS**: Configure appropriate CORS policies
3. **Input Validation**: Validate all input data
4. **Rate Limiting**: Implement rate limiting for API endpoints
5. **HTTPS**: Use HTTPS in production

## Deployment

### Spring Boot Deployment
```bash
# Build
mvn clean package

# Run
java -jar target/atp-web-1.0.0.jar
```

### Servlet Deployment
Deploy WAR file to Tomcat/Jetty server.

## Real-time Updates

For real-time analysis, use WebSocket:

```java
@ServerEndpoint("/ws/analysis")
public class AnalysisWebSocket {
    @OnMessage
    public String onMessage(String message) {
        // Process and return analysis results
    }
}
```

## Conclusion

This integration enables web-based access to ATP analysis capabilities while maintaining the existing Java codebase. Choose the implementation option that best fits your infrastructure and requirements.
