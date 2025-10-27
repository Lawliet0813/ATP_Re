#!/bin/bash

# ATP_Re - Start Script
# This script starts both the API server and the Streamlit UI

echo "🚆 Starting ATP_Re System..."
echo ""

# Check if Python is installed
if ! command -v python3 &> /dev/null; then
    echo "❌ Python 3 is not installed. Please install Python 3.8 or higher."
    exit 1
fi

# Check if requirements are installed
if ! python3 -c "import fastapi" &> /dev/null; then
    echo "📦 Installing dependencies..."
    pip install -r requirements.txt
fi

# Create necessary directories
mkdir -p uploads reports logs

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️  .env file not found. Copying from .env.example..."
    cp .env.example .env
    echo "⚠️  Please edit .env with your configuration before continuing."
    exit 1
fi

echo "Starting API Server..."
cd api
python main.py &
API_PID=$!
cd ..

echo "API Server started (PID: $API_PID)"
echo "API Documentation: http://localhost:8000/docs"
echo ""

# Wait for API to start
sleep 3

echo "Starting Streamlit UI..."
cd streamlit_ui
streamlit run app.py &
UI_PID=$!
cd ..

echo "Streamlit UI started (PID: $UI_PID)"
echo "Web UI: http://localhost:8501"
echo ""

echo "✅ ATP_Re System is running!"
echo ""
echo "To stop the system, press Ctrl+C or run:"
echo "  kill $API_PID $UI_PID"

# Wait for processes
wait
