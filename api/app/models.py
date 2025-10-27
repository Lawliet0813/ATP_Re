from sqlalchemy import create_engine, Column, Integer, String, DateTime, Float, Text, Boolean
from sqlalchemy.orm import declarative_base, sessionmaker
from datetime import datetime

Base = declarative_base()


class ATPTask(Base):
    """ATP Task Model"""
    __tablename__ = "atp_tasks"
    
    id = Column(Integer, primary_key=True, index=True)
    task_name = Column(String(255), nullable=False)
    task_type = Column(String(50))
    status = Column(String(50), default="pending")
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)
    completed_at = Column(DateTime, nullable=True)
    file_path = Column(String(500))
    description = Column(Text)


class ATPData(Base):
    """ATP Data Model"""
    __tablename__ = "atp_data"
    
    id = Column(Integer, primary_key=True, index=True)
    task_id = Column(Integer, index=True)
    timestamp = Column(DateTime, index=True)
    speed = Column(Float)
    position = Column(Float)
    data_type = Column(String(50))
    raw_data = Column(Text)
    decoded_data = Column(Text)


class ATPEvent(Base):
    """ATP Event Model"""
    __tablename__ = "atp_events"
    
    id = Column(Integer, primary_key=True, index=True)
    task_id = Column(Integer, index=True)
    event_type = Column(String(100))
    event_time = Column(DateTime, index=True)
    severity = Column(String(20))
    message = Column(Text)
    details = Column(Text)


class UploadedFile(Base):
    """Uploaded File Model"""
    __tablename__ = "uploaded_files"
    
    id = Column(Integer, primary_key=True, index=True)
    filename = Column(String(255), nullable=False)
    original_filename = Column(String(255))
    file_size = Column(Integer)
    file_type = Column(String(50))
    upload_time = Column(DateTime, default=datetime.utcnow)
    status = Column(String(50), default="uploaded")
    processed = Column(Boolean, default=False)


def get_database_url(settings):
    """Construct database URL from settings"""
    return f"mssql+pymssql://{settings.DB_USER}:{settings.DB_PASSWORD}@{settings.DB_HOST}:{settings.DB_PORT}/{settings.DB_NAME}"


def get_db_session(database_url: str):
    """Create database session"""
    engine = create_engine(database_url, echo=False)
    SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
    return SessionLocal()


def init_db(database_url: str):
    """Initialize database tables"""
    engine = create_engine(database_url, echo=False)
    Base.metadata.create_all(bind=engine)
