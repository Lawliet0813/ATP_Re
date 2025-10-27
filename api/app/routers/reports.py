from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
import uuid
from datetime import datetime

from api.app.database import get_db
from api.app.models import ATPTask, ATPData, ATPEvent
from api.app.schemas import ReportRequest, ReportResponse

router = APIRouter(prefix="/reports", tags=["reports"])


@router.post("/generate", response_model=ReportResponse)
async def generate_report(report: ReportRequest, db: Session = Depends(get_db)):
    """Generate a report for a specific task"""
    
    # Verify task exists
    task = db.query(ATPTask).filter(ATPTask.id == report.task_id).first()
    if not task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Task with id {report.task_id} not found"
        )
    
    # Generate unique report ID
    report_id = str(uuid.uuid4())
    
    # Get data for report
    data_count = db.query(ATPData).filter(ATPData.task_id == report.task_id).count()
    event_count = db.query(ATPEvent).filter(ATPEvent.task_id == report.task_id).count()
    
    # In a real implementation, you would:
    # 1. Generate the actual report file (PDF/Excel/HTML)
    # 2. Save it to a reports directory
    # 3. Return the download URL
    
    # For now, return a mock response
    response = ReportResponse(
        report_id=report_id,
        task_id=report.task_id,
        report_type=report.report_type,
        format=report.format,
        status="generated",
        download_url=f"/api/v1/reports/download/{report_id}",
        created_at=datetime.utcnow()
    )
    
    return response


@router.get("/{report_id}/status")
async def get_report_status(report_id: str):
    """Get the status of a report generation"""
    # In a real implementation, check the actual report status
    # For now, return a mock status
    return {
        "report_id": report_id,
        "status": "completed",
        "progress": 100,
        "message": "Report generated successfully"
    }


@router.get("/task/{task_id}/summary")
async def get_task_report_summary(task_id: int, db: Session = Depends(get_db)):
    """Get a summary report for a task"""
    from sqlalchemy import func
    
    task = db.query(ATPTask).filter(ATPTask.id == task_id).first()
    if not task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Task with id {task_id} not found"
        )
    
    # Get data statistics
    data_stats = db.query(
        func.count(ATPData.id).label('count'),
        func.min(ATPData.timestamp).label('start_time'),
        func.max(ATPData.timestamp).label('end_time'),
        func.avg(ATPData.speed).label('avg_speed'),
        func.max(ATPData.speed).label('max_speed')
    ).filter(ATPData.task_id == task_id).first()
    
    # Get event statistics
    event_stats = db.query(
        ATPEvent.severity,
        func.count(ATPEvent.id).label('count')
    ).filter(
        ATPEvent.task_id == task_id
    ).group_by(ATPEvent.severity).all()
    
    return {
        "task": {
            "id": task.id,
            "name": task.task_name,
            "type": task.task_type,
            "status": task.status,
            "created_at": task.created_at,
            "completed_at": task.completed_at
        },
        "data_statistics": {
            "count": data_stats.count if data_stats else 0,
            "start_time": data_stats.start_time if data_stats else None,
            "end_time": data_stats.end_time if data_stats else None,
            "avg_speed": float(data_stats.avg_speed) if data_stats and data_stats.avg_speed else None,
            "max_speed": float(data_stats.max_speed) if data_stats and data_stats.max_speed else None
        },
        "event_statistics": {
            "by_severity": {e.severity: e.count for e in event_stats}
        }
    }
