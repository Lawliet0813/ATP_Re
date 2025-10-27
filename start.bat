@echo off
REM ATP_Re - Start Script for Windows
REM This script starts both the API server and the Streamlit UI

echo Starting ATP_Re System...
echo.

REM Check if Python is installed
python --version >nul 2>&1
if errorlevel 1 (
    echo Python is not installed. Please install Python 3.8 or higher.
    exit /b 1
)

REM Check if .env file exists
if not exist .env (
    echo .env file not found. Copying from .env.example...
    copy .env.example .env
    echo Please edit .env with your configuration before continuing.
    exit /b 1
)

REM Create necessary directories
if not exist uploads mkdir uploads
if not exist reports mkdir reports
if not exist logs mkdir logs

REM Install dependencies if needed
pip show fastapi >nul 2>&1
if errorlevel 1 (
    echo Installing dependencies...
    pip install -r requirements.txt
)

echo Starting API Server...
start "ATP_Re API" cmd /k "cd api && python main.py"

timeout /t 3 /nobreak >nul

echo Starting Streamlit UI...
start "ATP_Re UI" cmd /k "cd streamlit_ui && streamlit run app.py"

echo.
echo ATP_Re System is running!
echo API Documentation: http://localhost:8000/docs
echo Web UI: http://localhost:8501
echo.
echo Close the command windows to stop the system.
pause
