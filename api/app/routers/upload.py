from fastapi import APIRouter, Depends, HTTPException, UploadFile, File, status
from sqlalchemy.orm import Session
from typing import List
import os
import shutil
from datetime import datetime
import uuid

from api.app.database import get_db
from api.app.models import UploadedFile, ATPTask
from api.app.schemas import FileUploadResponse
from config.settings import settings

router = APIRouter(prefix="/upload", tags=["upload"])

# Ensure upload directory exists
os.makedirs(settings.UPLOAD_DIR, exist_ok=True)


@router.post("/", response_model=FileUploadResponse)
async def upload_file(
    file: UploadFile = File(...),
    create_task: bool = True,
    db: Session = Depends(get_db)
):
    """Upload a file to the server"""
    
    # Check file extension
    file_ext = os.path.splitext(file.filename)[1].lower()
    if file_ext not in settings.ALLOWED_EXTENSIONS:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f"File type {file_ext} not allowed. Allowed types: {settings.ALLOWED_EXTENSIONS}"
        )
    
    # Generate unique filename
    unique_filename = f"{uuid.uuid4()}{file_ext}"
    file_path = os.path.join(settings.UPLOAD_DIR, unique_filename)
    
    # Save file
    try:
        with open(file_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)
        
        file_size = os.path.getsize(file_path)
        
        # Check file size
        if file_size > settings.MAX_UPLOAD_SIZE:
            os.remove(file_path)
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail=f"File size exceeds maximum allowed size of {settings.MAX_UPLOAD_SIZE} bytes"
            )
        
    except Exception as e:
        if os.path.exists(file_path):
            os.remove(file_path)
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to save file: {str(e)}"
        )
    
    # Create database record
    db_file = UploadedFile(
        filename=unique_filename,
        original_filename=file.filename,
        file_size=file_size,
        file_type=file_ext,
        status="uploaded"
    )
    db.add(db_file)
    db.commit()
    db.refresh(db_file)
    
    # Optionally create a task for this file
    if create_task:
        task = ATPTask(
            task_name=f"Process {file.filename}",
            task_type="decode",
            file_path=file_path,
            status="pending",
            description=f"Auto-created task for uploaded file: {file.filename}"
        )
        db.add(task)
        db.commit()
    
    return db_file


@router.get("/", response_model=List[FileUploadResponse])
async def list_uploaded_files(
    skip: int = 0,
    limit: int = 100,
    db: Session = Depends(get_db)
):
    """List all uploaded files"""
    files = db.query(UploadedFile).offset(skip).limit(limit).all()
    return files


@router.get("/{file_id}", response_model=FileUploadResponse)
async def get_uploaded_file(file_id: int, db: Session = Depends(get_db)):
    """Get information about an uploaded file"""
    file = db.query(UploadedFile).filter(UploadedFile.id == file_id).first()
    if not file:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"File with id {file_id} not found"
        )
    return file


@router.delete("/{file_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_uploaded_file(file_id: int, db: Session = Depends(get_db)):
    """Delete an uploaded file"""
    file = db.query(UploadedFile).filter(UploadedFile.id == file_id).first()
    if not file:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"File with id {file_id} not found"
        )
    
    # Delete physical file
    file_path = os.path.join(settings.UPLOAD_DIR, file.filename)
    if os.path.exists(file_path):
        os.remove(file_path)
    
    # Delete database record
    db.delete(file)
    db.commit()
    
    return None
