import os
from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings"""
    
    # API Settings
    API_TITLE: str = "ATP_Re API"
    API_VERSION: str = "1.0.0"
    API_PREFIX: str = "/api/v1"
    
    # Server Settings
    HOST: str = "0.0.0.0"
    PORT: int = 8000
    
    # Database Settings (SQL Server)
    DB_HOST: str = "localhost"
    DB_PORT: int = 1433
    DB_NAME: str = "ATP_DB"
    DB_USER: str = "atp_user"
    DB_PASSWORD: str = ""
    
    # File Upload Settings
    UPLOAD_DIR: str = "./uploads"
    MAX_UPLOAD_SIZE: int = 100 * 1024 * 1024  # 100MB
    ALLOWED_EXTENSIONS: list = [".dat", ".log", ".txt", ".bin", ".zip"]
    
    # WebSocket Settings
    WS_HEARTBEAT_INTERVAL: int = 30
    
    # CORS Settings
    CORS_ORIGINS: list = ["http://localhost:8501", "http://localhost:3000"]
    
    # Logging
    LOG_LEVEL: str = "INFO"
    
    class Config:
        env_file = ".env"
        case_sensitive = True


settings = Settings()
