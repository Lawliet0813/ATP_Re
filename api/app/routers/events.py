from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List

from api.app.database import get_db
from api.app.models import ATPEvent
from api.app.schemas import EventQuery, EventResponse

router = APIRouter(prefix="/events", tags=["events"])


@router.post("/query", response_model=List[EventResponse])
async def query_events(query: EventQuery, db: Session = Depends(get_db)):
    """Query ATP events with filters"""
    db_query = db.query(ATPEvent)
    
    if query.task_id:
        db_query = db_query.filter(ATPEvent.task_id == query.task_id)
    
    if query.event_type:
        db_query = db_query.filter(ATPEvent.event_type == query.event_type)
    
    if query.severity:
        db_query = db_query.filter(ATPEvent.severity == query.severity)
    
    if query.start_time:
        db_query = db_query.filter(ATPEvent.event_time >= query.start_time)
    
    if query.end_time:
        db_query = db_query.filter(ATPEvent.event_time <= query.end_time)
    
    events = db_query.order_by(
        ATPEvent.event_time.desc()
    ).offset(query.offset).limit(query.limit).all()
    
    return events


@router.get("/{event_id}", response_model=EventResponse)
async def get_event(event_id: int, db: Session = Depends(get_db)):
    """Get specific ATP event by ID"""
    event = db.query(ATPEvent).filter(ATPEvent.id == event_id).first()
    if not event:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Event with id {event_id} not found"
        )
    return event


@router.get("/task/{task_id}", response_model=List[EventResponse])
async def get_task_events(
    task_id: int,
    skip: int = 0,
    limit: int = 100,
    severity: str = None,
    db: Session = Depends(get_db)
):
    """Get all events for a specific task"""
    query = db.query(ATPEvent).filter(ATPEvent.task_id == task_id)
    
    if severity:
        query = query.filter(ATPEvent.severity == severity)
    
    events = query.order_by(
        ATPEvent.event_time.desc()
    ).offset(skip).limit(limit).all()
    
    return events


@router.get("/task/{task_id}/summary")
async def get_task_events_summary(task_id: int, db: Session = Depends(get_db)):
    """Get summary statistics for task events"""
    from sqlalchemy import func
    
    # Count events by severity
    severity_counts = db.query(
        ATPEvent.severity,
        func.count(ATPEvent.id).label('count')
    ).filter(
        ATPEvent.task_id == task_id
    ).group_by(ATPEvent.severity).all()
    
    # Count events by type
    type_counts = db.query(
        ATPEvent.event_type,
        func.count(ATPEvent.id).label('count')
    ).filter(
        ATPEvent.task_id == task_id
    ).group_by(ATPEvent.event_type).all()
    
    # Get total count
    total = db.query(func.count(ATPEvent.id)).filter(
        ATPEvent.task_id == task_id
    ).scalar()
    
    return {
        "task_id": task_id,
        "total_events": total,
        "by_severity": {s.severity: s.count for s in severity_counts},
        "by_type": {t.event_type: t.count for t in type_counts}
    }
