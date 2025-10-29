# Integration Guide: Python API & Web UI with Java Backend

This guide explains how to integrate the new Python-based REST API and Web UI with the existing Java-based ATP_Re system.

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                      Web Browser (User)                         │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│              Streamlit Web UI (Port 8501)                       │
│  - File Upload Interface                                        │
│  - Data Visualization (Charts, Graphs)                          │
│  - Task Management                                              │
│  - Event Monitoring                                             │
│  - Report Generation                                            │
└───────────────────────────┬─────────────────────────────────────┘
                            │ HTTP/WebSocket
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│              FastAPI REST API (Port 8000)                       │
│  - RESTful Endpoints                                            │
│  - WebSocket Streaming                                          │
│  - File Upload Handling                                         │
│  - Data Query & Filtering                                       │
└────────────┬─────────────────────────────┬────────────────────┘
             │                             │
             ▼                             ▼
┌────────────────────────┐    ┌──────────────────────────────────┐
│  Shared File System    │    │    SQL Server Database           │
│  - Upload Directory    │    │    - atp_tasks                   │
│  - Data Files          │    │    - atp_data                    │
│  - Reports             │    │    - atp_events                  │
└────────────┬───────────┘    └──────────────┬───────────────────┘
             │                                │
             │                                │
             └────────────────┬───────────────┘
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│              Java Backend System                                │
│  - Tools_re (File Operations, User Management)                  │
│  - connect_re (Database & FTP)                                  │
│  - core_re (Data Models)                                        │
│  - decode_re (Basic Decoding)                                   │
│  - decoder_re (Advanced Decoding)                               │
│  - drawGraphics_re (Graphics Generation)                        │
│  - ui_re (Swing Desktop UI)                                     │
└─────────────────────────────────────────────────────────────────┘
```

## Integration Points

### 1. Database Integration

The Python API and Java backend share the same SQL Server database.

#### Shared Database Schema

Both systems access these tables:
- **atp_tasks**: Task management
- **atp_data**: ATP measurement data
- **atp_events**: System events
- **uploaded_files**: File tracking

#### Java Side Configuration

Ensure your Java `ConnectDB` class connects to the same database:
```java
// connect_re/ConnectDB.java
String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=ATP_DB";
String dbUser = "atp_user";
String dbPassword = "your_password";
```

#### Python Side Configuration

Update `.env` file:
```bash
DB_HOST=localhost
DB_PORT=1433
DB_NAME=ATP_DB
DB_USER=atp_user
DB_PASSWORD=your_password
```

### 2. File System Integration

Both systems access shared directories for file storage.

#### Shared Directories

```
/shared_data/
├── uploads/          # Uploaded files (Python writes, Java reads)
├── processed/        # Processed data (Java writes, Python reads)
├── reports/          # Generated reports (Both read/write)
└── logs/            # System logs (Both read/write)
```

#### Configuration

**Python (.env):**
```bash
UPLOAD_DIR=/shared_data/uploads
```

**Java (configuration):**
```java
String uploadDir = "/shared_data/uploads";
```

### 3. Processing Workflow

#### Workflow 1: File Upload via Web UI → Java Processing

```
1. User uploads file via Streamlit Web UI
   ↓
2. FastAPI saves file to shared upload directory
   ↓
3. FastAPI creates task record in database (status: 'pending')
   ↓
4. Java backend monitors database for new tasks
   ↓
5. Java DecodeTask picks up the task and processes the file
   ↓
6. Java updates task status (status: 'processing' → 'completed')
   ↓
7. Java writes decoded data to atp_data table
   ↓
8. WebSocket pushes real-time updates to Web UI
   ↓
9. User views results in Web UI
```

#### Workflow 2: Java Processing → Web UI Visualization

```
1. Java backend processes ATP data (existing workflow)
   ↓
2. Java writes data to database (atp_data, atp_events tables)
   ↓
3. User opens Web UI and selects task
   ↓
4. FastAPI queries database and returns data
   ↓
5. Streamlit displays interactive charts and graphs
```

### 4. Real-time Updates (WebSocket)

#### Java Backend → WebSocket

When Java completes processing, trigger a WebSocket notification:

```java
// Example: Notify Python API when task status changes
import java.net.http.*;
import java.net.URI;

public class NotifyAPI {
    public static void notifyTaskUpdate(int taskId, String status) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String json = String.format(
                "{\"task_id\": %d, \"status\": \"%s\"}", 
                taskId, status
            );
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/v1/tasks/" + taskId + "/status"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();
            
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 5. User Authentication Integration

#### Option A: Shared Database Authentication

Use the existing user authentication from Java:

```python
# api/app/auth.py (to be created)
from sqlalchemy.orm import Session
from api.app.database import get_db

def authenticate_user(username: str, password: str, db: Session):
    """
    Authenticate against existing Java user table
    Assumes Java uses CheckUser class for authentication
    """
    # Query the existing user table used by Java
    user = db.execute(
        "SELECT * FROM users WHERE username = :username",
        {"username": username}
    ).fetchone()
    
    if user and verify_password(password, user.password_hash):
        return user
    return None
```

#### Option B: API Token Authentication

Generate API tokens for Web UI users:

```python
from fastapi import Depends, HTTPException
from fastapi.security import HTTPBearer

security = HTTPBearer()

async def verify_token(credentials = Depends(security)):
    token = credentials.credentials
    # Verify token against database or JWT
    if not is_valid_token(token):
        raise HTTPException(status_code=401, detail="Invalid token")
    return token
```

### 6. Report Generation Integration

#### Java-generated Reports → Web UI

```python
# api/app/routers/reports.py
@router.get("/download/{report_id}")
async def download_report(report_id: str):
    """Download report generated by Java backend"""
    report_path = f"/shared_data/reports/{report_id}.pdf"
    
    if not os.path.exists(report_path):
        raise HTTPException(404, "Report not found")
    
    return FileResponse(report_path)
```

#### Web UI-generated Reports → Java

Save reports to shared directory where Java can access them.

### 7. Event Monitoring Integration

Java backend can insert events that Web UI monitors:

```java
// Example: Insert event from Java
import java.sql.*;

public class EventLogger {
    public static void logEvent(int taskId, String eventType, 
                                String severity, String message) {
        String sql = "INSERT INTO atp_events (task_id, event_type, " +
                    "event_time, severity, message) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, taskId);
            stmt.setString(2, eventType);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setString(4, severity);
            stmt.setString(5, message);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## Implementation Steps

### Step 1: Database Schema Setup

Run this SQL script to create the required tables (if they don't exist):

```sql
-- Create tables for API/Web UI integration
CREATE TABLE atp_tasks (
    id INT PRIMARY KEY IDENTITY(1,1),
    task_name VARCHAR(255) NOT NULL,
    task_type VARCHAR(50),
    status VARCHAR(50) DEFAULT 'pending',
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    completed_at DATETIME NULL,
    file_path VARCHAR(500),
    description TEXT
);

CREATE TABLE atp_data (
    id INT PRIMARY KEY IDENTITY(1,1),
    task_id INT,
    timestamp DATETIME,
    speed FLOAT,
    position FLOAT,
    data_type VARCHAR(50),
    raw_data TEXT,
    decoded_data TEXT,
    FOREIGN KEY (task_id) REFERENCES atp_tasks(id)
);

CREATE TABLE atp_events (
    id INT PRIMARY KEY IDENTITY(1,1),
    task_id INT,
    event_type VARCHAR(100),
    event_time DATETIME,
    severity VARCHAR(20),
    message TEXT,
    details TEXT,
    FOREIGN KEY (task_id) REFERENCES atp_tasks(id)
);

CREATE TABLE uploaded_files (
    id INT PRIMARY KEY IDENTITY(1,1),
    filename VARCHAR(255) NOT NULL,
    original_filename VARCHAR(255),
    file_size INT,
    file_type VARCHAR(50),
    upload_time DATETIME DEFAULT GETDATE(),
    status VARCHAR(50) DEFAULT 'uploaded',
    processed BIT DEFAULT 0
);
```

### Step 2: Configure Shared Directories

```bash
# Create shared directories
mkdir -p /shared_data/uploads
mkdir -p /shared_data/processed
mkdir -p /shared_data/reports
mkdir -p /shared_data/logs

# Set appropriate permissions
chmod 777 /shared_data/uploads
chmod 777 /shared_data/processed
chmod 777 /shared_data/reports
chmod 777 /shared_data/logs
```

### Step 3: Update Java Backend

Add database polling for new tasks:

```java
// decoder_re/TaskMonitor.java (new class)
public class TaskMonitor extends Thread {
    private volatile boolean running = true;
    
    @Override
    public void run() {
        while (running) {
            try {
                // Check for pending tasks
                String sql = "SELECT * FROM atp_tasks WHERE status = 'pending'";
                ResultSet rs = ConnectDB.executeQuery(sql);
                
                while (rs.next()) {
                    int taskId = rs.getInt("id");
                    String filePath = rs.getString("file_path");
                    
                    // Process the task
                    processTask(taskId, filePath);
                }
                
                // Wait before checking again
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void processTask(int taskId, String filePath) {
        // Update status to processing
        updateTaskStatus(taskId, "processing");
        
        // Use existing DecodeTask to process
        DecodeTask task = new DecodeTask(filePath);
        task.execute();
        
        // Update status to completed
        updateTaskStatus(taskId, "completed");
    }
    
    private void updateTaskStatus(int taskId, String status) {
        String sql = "UPDATE atp_tasks SET status = ?, " +
                    "updated_at = GETDATE() WHERE id = ?";
        ConnectDB.executeUpdate(sql, status, taskId);
    }
    
    public void shutdown() {
        running = false;
    }
}
```

### Step 4: Start Services

```bash
# Start Python API
cd api
python main.py &

# Start Streamlit UI
cd streamlit_ui
streamlit run app.py &

# Start Java backend
cd java_app
java -jar ATP_Re.jar
```

Or use the provided start script:
```bash
./start.sh  # Linux/Mac
start.bat   # Windows
```

## Testing Integration

### Test 1: File Upload Flow

```bash
# Upload file via API
curl -X POST "http://localhost:8000/api/v1/upload/" \
  -F "file=@test_data.dat" \
  -F "create_task=true"

# Check task was created
curl "http://localhost:8000/api/v1/tasks/"

# Java should pick up and process the task
# Check status after processing
curl "http://localhost:8000/api/v1/tasks/1"
```

### Test 2: Data Query Flow

```bash
# Query data (should return data processed by Java)
curl -X POST "http://localhost:8000/api/v1/data/query" \
  -H "Content-Type: application/json" \
  -d '{"task_id": 1, "limit": 10}'
```

### Test 3: WebSocket Real-time Updates

```javascript
// Connect to WebSocket
const ws = new WebSocket('ws://localhost:8000/api/v1/ws/stream');

ws.onopen = () => {
  ws.send(JSON.stringify({action: 'subscribe', task_id: 1}));
};

ws.onmessage = (event) => {
  console.log('Received:', event.data);
};
```

## Troubleshooting

### Issue: Python API can't connect to database

**Solution:** Check SQL Server configuration:
```sql
-- Enable TCP/IP connections
-- SQL Server Configuration Manager → SQL Server Network Configuration → Protocols
-- Enable TCP/IP

-- Check firewall allows port 1433
```

### Issue: Java can't read uploaded files

**Solution:** Check file permissions:
```bash
chmod 644 /shared_data/uploads/*
chown java_user:java_group /shared_data/uploads/*
```

### Issue: WebSocket connection fails

**Solution:** Check CORS settings in `config/settings.py`:
```python
CORS_ORIGINS = [
    "http://localhost:8501",
    "http://your-domain.com"
]
```

## Performance Optimization

### 1. Database Connection Pooling

Both Java and Python should use connection pooling:

**Python:**
```python
# Already configured in api/app/database.py
engine = create_engine(
    DATABASE_URL,
    pool_size=10,
    max_overflow=20,
    pool_pre_ping=True
)
```

**Java:**
```java
// Use connection pooling library like HikariCP
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=ATP_DB");
config.setUsername("atp_user");
config.setPassword("password");
config.setMaximumPoolSize(10);
```

### 2. Caching

Implement Redis caching for frequently accessed data:

```python
from redis import Redis
cache = Redis(host='localhost', port=6379)

@router.get("/tasks/{task_id}")
async def get_task(task_id: int):
    # Check cache first
    cached = cache.get(f"task:{task_id}")
    if cached:
        return json.loads(cached)
    
    # Query database
    task = db.query(ATPTask).filter(ATPTask.id == task_id).first()
    
    # Cache result
    cache.setex(f"task:{task_id}", 300, json.dumps(task))
    return task
```

## Security Considerations

1. **Use HTTPS in production**: Configure SSL certificates
2. **Implement authentication**: Add JWT or OAuth2
3. **Validate file uploads**: Check file types and scan for malware
4. **SQL injection prevention**: Use parameterized queries (already implemented)
5. **Rate limiting**: Add rate limiting to API endpoints

## Conclusion

This integration allows the existing Java-based ATP_Re system to leverage modern web technologies while maintaining its core functionality. The shared database and file system approach ensures data consistency between both systems.
