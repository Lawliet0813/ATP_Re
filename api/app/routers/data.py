from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List

from api.app.database import get_db
from api.app.models import ATPData
from api.app.schemas import DataQuery, DataResponse

router = APIRouter(prefix="/data", tags=["data"])


@router.post("/query", response_model=List[DataResponse])
async def query_data(query: DataQuery, db: Session = Depends(get_db)):
    """Query ATP data with filters"""
    db_query = db.query(ATPData)
    
    if query.task_id:
        db_query = db_query.filter(ATPData.task_id == query.task_id)
    
    if query.start_time:
        db_query = db_query.filter(ATPData.timestamp >= query.start_time)
    
    if query.end_time:
        db_query = db_query.filter(ATPData.timestamp <= query.end_time)
    
    if query.data_type:
        db_query = db_query.filter(ATPData.data_type == query.data_type)
    
    data = db_query.offset(query.offset).limit(query.limit).all()
    return data


@router.get("/{data_id}", response_model=DataResponse)
async def get_data(data_id: int, db: Session = Depends(get_db)):
    """Get specific ATP data by ID"""
    data = db.query(ATPData).filter(ATPData.id == data_id).first()
    if not data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Data with id {data_id} not found"
        )
    return data


@router.get("/task/{task_id}", response_model=List[DataResponse])
async def get_task_data(
    task_id: int,
    skip: int = 0,
    limit: int = 100,
    db: Session = Depends(get_db)
):
    """Get all data for a specific task"""
    data = db.query(ATPData).filter(
        ATPData.task_id == task_id
    ).offset(skip).limit(limit).all()
    return data


@router.get("/task/{task_id}/summary")
async def get_task_data_summary(task_id: int, db: Session = Depends(get_db)):
    """Get summary statistics for task data"""
    from sqlalchemy import func
    
    stats = db.query(
        func.count(ATPData.id).label('count'),
        func.min(ATPData.timestamp).label('start_time'),
        func.max(ATPData.timestamp).label('end_time'),
        func.avg(ATPData.speed).label('avg_speed'),
        func.max(ATPData.speed).label('max_speed')
    ).filter(ATPData.task_id == task_id).first()
    
    if not stats or stats.count == 0:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No data found for task {task_id}"
        )
    
    return {
        "task_id": task_id,
        "data_count": stats.count,
        "start_time": stats.start_time,
        "end_time": stats.end_time,
        "avg_speed": float(stats.avg_speed) if stats.avg_speed else None,
        "max_speed": float(stats.max_speed) if stats.max_speed else None
    }
