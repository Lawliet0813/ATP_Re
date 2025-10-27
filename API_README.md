# ATP_Re - Stage 4: API & Web UI

This directory contains the REST API and Web UI implementation for the ATP_Re system.

## Architecture Overview

```
ATP_Re/
├── api/                    # FastAPI REST API
│   ├── app/
│   │   ├── routers/       # API endpoints
│   │   │   ├── tasks.py   # Task management
│   │   │   ├── data.py    # Data queries
│   │   │   ├── events.py  # Event queries
│   │   │   ├── upload.py  # File upload
│   │   │   ├── reports.py # Report generation
│   │   │   └── websocket.py # WebSocket streaming
│   │   ├── models.py      # Database models
│   │   ├── schemas.py     # Pydantic schemas
│   │   └── database.py    # Database connection
│   └── main.py            # API entry point
├── streamlit_ui/          # Streamlit Web UI
│   └── app.py             # Main UI application
└── config/                # Configuration
    └── settings.py        # Application settings
```

## Features

### REST API Features

1. **Task Management**
   - Create, read, update, delete tasks
   - Query tasks by status
   - Update task status

2. **Data Queries**
   - Query ATP data with filters
   - Get data summary statistics
   - Time-range filtering

3. **Event Monitoring**
   - Query events by severity
   - Event type filtering
   - Event statistics

4. **File Upload**
   - Upload ATP data files
   - Automatic task creation
   - File validation

5. **Report Generation**
   - Generate summary reports
   - Multiple output formats (PDF, Excel, HTML)
   - Task statistics

6. **WebSocket Streaming**
   - Real-time data updates
   - Event notifications
   - Task status updates

### Web UI Features

1. **Dashboard**
   - Overview of all tasks
   - Task status metrics
   - Recent task list

2. **File Upload**
   - Drag-and-drop file upload
   - Automatic task creation
   - Upload progress

3. **Task Management**
   - View all tasks
   - Filter by status
   - Create new tasks

4. **Data Analysis**
   - Interactive charts
   - Speed/position visualization
   - Data statistics

5. **Event Monitoring**
   - Event filtering by severity
   - Event timeline
   - Real-time updates

6. **Reports**
   - Generate custom reports
   - Multiple formats
   - Quick summaries

## Installation

### Prerequisites

- Python 3.8 or higher
- SQL Server database (or compatible)
- pip package manager

### Setup

1. Install dependencies:
```bash
pip install -r requirements.txt
```

2. Configure environment variables:
```bash
cp .env.example .env
# Edit .env with your configuration
```

3. Initialize the database:
```bash
python -c "from api.app.models import init_db; from config.settings import settings; from api.app.models import get_database_url; init_db(get_database_url(settings))"
```

## Running the Application

### Start the API Server

```bash
# Development mode with auto-reload
cd api
python main.py

# Or using uvicorn directly
uvicorn api.main:app --reload --host 0.0.0.0 --port 8000
```

The API will be available at:
- API: http://localhost:8000
- API Documentation: http://localhost:8000/docs
- Alternative Docs: http://localhost:8000/redoc

### Start the Web UI

```bash
# In a new terminal
cd streamlit_ui
streamlit run app.py
```

The Web UI will be available at: http://localhost:8501

## API Usage Examples

### Create a Task

```bash
curl -X POST "http://localhost:8000/api/v1/tasks/" \
  -H "Content-Type: application/json" \
  -d '{
    "task_name": "Decode Train Data",
    "task_type": "decode",
    "description": "Process uploaded train data file"
  }'
```

### Upload a File

```bash
curl -X POST "http://localhost:8000/api/v1/upload/" \
  -F "file=@/path/to/your/file.dat" \
  -F "create_task=true"
```

### Query Data

```bash
curl -X POST "http://localhost:8000/api/v1/data/query" \
  -H "Content-Type: application/json" \
  -d '{
    "task_id": 1,
    "limit": 100,
    "offset": 0
  }'
```

### Query Events

```bash
curl -X POST "http://localhost:8000/api/v1/events/query" \
  -H "Content-Type: application/json" \
  -d '{
    "task_id": 1,
    "severity": "high",
    "limit": 50
  }'
```

### Generate Report

```bash
curl -X POST "http://localhost:8000/api/v1/reports/generate" \
  -H "Content-Type: application/json" \
  -d '{
    "task_id": 1,
    "report_type": "summary",
    "format": "pdf",
    "include_charts": true
  }'
```

## WebSocket Usage

### Connect to WebSocket

```javascript
const ws = new WebSocket('ws://localhost:8000/api/v1/ws/stream');

ws.onopen = () => {
  // Subscribe to task updates
  ws.send(JSON.stringify({
    action: 'subscribe',
    task_id: 1
  }));
};

ws.onmessage = (event) => {
  const message = JSON.parse(event.data);
  console.log('Received:', message);
};
```

### Message Types

- `connection`: Connection established
- `subscription`: Subscription status
- `data`: Real-time data updates
- `event`: Event notifications
- `status`: Task status updates
- `pong`: Heartbeat response

## Configuration

Edit `.env` file to configure:

- Database connection (host, port, credentials)
- API server settings (host, port)
- File upload settings (allowed types, max size)
- CORS origins
- Logging level

## Database Schema

### Tables

1. **atp_tasks**: Task management
   - id, task_name, task_type, status, created_at, updated_at, completed_at, file_path, description

2. **atp_data**: ATP data points
   - id, task_id, timestamp, speed, position, data_type, raw_data, decoded_data

3. **atp_events**: System events
   - id, task_id, event_type, event_time, severity, message, details

4. **uploaded_files**: Uploaded file tracking
   - id, filename, original_filename, file_size, file_type, upload_time, status, processed

## Development

### Running Tests

```bash
pytest api/tests/
```

### API Documentation

The API includes automatic interactive documentation:
- Swagger UI: http://localhost:8000/docs
- ReDoc: http://localhost:8000/redoc

## Integration with Java Backend

The API is designed to integrate with the existing Java-based ATP_Re system:

1. **Database**: Shares the same database schema
2. **File System**: Accesses the same file storage
3. **Processing**: Can trigger Java backend processing tasks
4. **Real-time**: WebSocket provides real-time updates from Java processes

## Troubleshooting

### Database Connection Issues

- Verify SQL Server is running
- Check database credentials in `.env`
- Ensure firewall allows connection to database port

### File Upload Issues

- Check `UPLOAD_DIR` exists and is writable
- Verify file size is within `MAX_UPLOAD_SIZE`
- Check file extension is in `ALLOWED_EXTENSIONS`

### WebSocket Connection Issues

- Verify CORS settings include your client origin
- Check firewall allows WebSocket connections
- Ensure client uses correct WebSocket URL

## License

Copyright © 2025 ATP_Re Project
