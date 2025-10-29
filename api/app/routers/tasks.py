from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List
from datetime import datetime

from api.app.database import get_db
from api.app.models import ATPTask
from api.app.schemas import TaskCreate, TaskResponse

router = APIRouter(prefix="/tasks", tags=["tasks"])


@router.post("/", response_model=TaskResponse, status_code=status.HTTP_201_CREATED)
async def create_task(task: TaskCreate, db: Session = Depends(get_db)):
    """Create a new ATP task"""
    db_task = ATPTask(
        task_name=task.task_name,
        task_type=task.task_type,
        description=task.description,
        file_path=task.file_path,
        status="pending"
    )
    db.add(db_task)
    db.commit()
    db.refresh(db_task)
    return db_task


@router.get("/", response_model=List[TaskResponse])
async def get_tasks(
    skip: int = 0,
    limit: int = 100,
    status_filter: str = None,
    db: Session = Depends(get_db)
):
    """Get list of ATP tasks"""
    query = db.query(ATPTask)
    
    if status_filter:
        query = query.filter(ATPTask.status == status_filter)
    
    tasks = query.offset(skip).limit(limit).all()
    return tasks


@router.get("/{task_id}", response_model=TaskResponse)
async def get_task(task_id: int, db: Session = Depends(get_db)):
    """Get a specific ATP task by ID"""
    task = db.query(ATPTask).filter(ATPTask.id == task_id).first()
    if not task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Task with id {task_id} not found"
        )
    return task


@router.put("/{task_id}", response_model=TaskResponse)
async def update_task(task_id: int, task: TaskCreate, db: Session = Depends(get_db)):
    """Update an existing ATP task"""
    db_task = db.query(ATPTask).filter(ATPTask.id == task_id).first()
    if not db_task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Task with id {task_id} not found"
        )
    
    db_task.task_name = task.task_name
    db_task.task_type = task.task_type
    db_task.description = task.description
    db_task.file_path = task.file_path
    db_task.updated_at = datetime.utcnow()
    
    db.commit()
    db.refresh(db_task)
    return db_task


@router.patch("/{task_id}/status")
async def update_task_status(
    task_id: int,
    new_status: str,
    db: Session = Depends(get_db)
):
    """Update task status"""
    db_task = db.query(ATPTask).filter(ATPTask.id == task_id).first()
    if not db_task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Task with id {task_id} not found"
        )
    
    db_task.status = new_status
    db_task.updated_at = datetime.utcnow()
    
    if new_status in ["completed", "failed"]:
        db_task.completed_at = datetime.utcnow()
    
    db.commit()
    return {"message": "Task status updated", "status": new_status}


@router.delete("/{task_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_task(task_id: int, db: Session = Depends(get_db)):
    """Delete an ATP task"""
    db_task = db.query(ATPTask).filter(ATPTask.id == task_id).first()
    if not db_task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Task with id {task_id} not found"
        )
    
    db.delete(db_task)
    db.commit()
    return None
