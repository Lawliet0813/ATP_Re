from pydantic import BaseModel, Field
from typing import Optional, List
from datetime import datetime


class TaskCreate(BaseModel):
    """Schema for creating a new task"""
    task_name: str = Field(..., description="Task name")
    task_type: str = Field(..., description="Task type (decode/analyze/report)")
    description: Optional[str] = None
    file_path: Optional[str] = None


class TaskResponse(BaseModel):
    """Schema for task response"""
    id: int
    task_name: str
    task_type: str
    status: str
    created_at: datetime
    updated_at: datetime
    completed_at: Optional[datetime] = None
    file_path: Optional[str] = None
    description: Optional[str] = None
    
    class Config:
        from_attributes = True


class DataQuery(BaseModel):
    """Schema for querying ATP data"""
    task_id: Optional[int] = None
    start_time: Optional[datetime] = None
    end_time: Optional[datetime] = None
    data_type: Optional[str] = None
    limit: int = Field(default=100, ge=1, le=1000)
    offset: int = Field(default=0, ge=0)


class DataResponse(BaseModel):
    """Schema for ATP data response"""
    id: int
    task_id: int
    timestamp: datetime
    speed: Optional[float] = None
    position: Optional[float] = None
    data_type: str
    raw_data: Optional[str] = None
    decoded_data: Optional[str] = None
    
    class Config:
        from_attributes = True


class EventQuery(BaseModel):
    """Schema for querying events"""
    task_id: Optional[int] = None
    event_type: Optional[str] = None
    severity: Optional[str] = None
    start_time: Optional[datetime] = None
    end_time: Optional[datetime] = None
    limit: int = Field(default=100, ge=1, le=1000)
    offset: int = Field(default=0, ge=0)


class EventResponse(BaseModel):
    """Schema for event response"""
    id: int
    task_id: int
    event_type: str
    event_time: datetime
    severity: str
    message: str
    details: Optional[str] = None
    
    class Config:
        from_attributes = True


class FileUploadResponse(BaseModel):
    """Schema for file upload response"""
    id: int
    filename: str
    original_filename: str
    file_size: int
    file_type: str
    upload_time: datetime
    status: str
    
    class Config:
        from_attributes = True


class ReportRequest(BaseModel):
    """Schema for report generation request"""
    task_id: int
    report_type: str = Field(..., description="Type of report (summary/detailed/analysis)")
    format: str = Field(default="pdf", description="Report format (pdf/excel/html)")
    include_charts: bool = Field(default=True)
    date_range: Optional[dict] = None


class ReportResponse(BaseModel):
    """Schema for report response"""
    report_id: str
    task_id: int
    report_type: str
    format: str
    status: str
    download_url: Optional[str] = None
    created_at: datetime


class WebSocketMessage(BaseModel):
    """Schema for WebSocket messages"""
    type: str = Field(..., description="Message type (data/event/status)")
    task_id: Optional[int] = None
    data: dict
    timestamp: datetime = Field(default_factory=datetime.utcnow)


class StatusResponse(BaseModel):
    """Schema for status response"""
    status: str
    message: str
    data: Optional[dict] = None
