from fastapi import APIRouter, WebSocket, WebSocketDisconnect, Depends
from sqlalchemy.orm import Session
from typing import List, Dict
import json
import asyncio
from datetime import datetime

from api.app.database import get_db
from api.app.models import ATPData, ATPEvent

router = APIRouter(prefix="/ws", tags=["websocket"])


class ConnectionManager:
    """Manages WebSocket connections"""
    
    def __init__(self):
        self.active_connections: List[WebSocket] = []
        self.task_subscribers: Dict[int, List[WebSocket]] = {}
    
    async def connect(self, websocket: WebSocket):
        """Accept a new WebSocket connection"""
        await websocket.accept()
        self.active_connections.append(websocket)
    
    def disconnect(self, websocket: WebSocket):
        """Remove a WebSocket connection"""
        self.active_connections.remove(websocket)
        # Remove from all task subscriptions
        for task_id in list(self.task_subscribers.keys()):
            if websocket in self.task_subscribers[task_id]:
                self.task_subscribers[task_id].remove(websocket)
    
    def subscribe_to_task(self, task_id: int, websocket: WebSocket):
        """Subscribe a WebSocket to a specific task"""
        if task_id not in self.task_subscribers:
            self.task_subscribers[task_id] = []
        if websocket not in self.task_subscribers[task_id]:
            self.task_subscribers[task_id].append(websocket)
    
    def unsubscribe_from_task(self, task_id: int, websocket: WebSocket):
        """Unsubscribe a WebSocket from a specific task"""
        if task_id in self.task_subscribers and websocket in self.task_subscribers[task_id]:
            self.task_subscribers[task_id].remove(websocket)
    
    async def send_personal_message(self, message: str, websocket: WebSocket):
        """Send a message to a specific WebSocket"""
        await websocket.send_text(message)
    
    async def broadcast(self, message: str):
        """Broadcast a message to all connected WebSockets"""
        for connection in self.active_connections:
            try:
                await connection.send_text(message)
            except:
                pass
    
    async def broadcast_to_task(self, task_id: int, message: str):
        """Broadcast a message to all subscribers of a specific task"""
        if task_id in self.task_subscribers:
            for connection in self.task_subscribers[task_id]:
                try:
                    await connection.send_text(message)
                except:
                    pass


manager = ConnectionManager()


@router.websocket("/stream")
async def websocket_endpoint(websocket: WebSocket):
    """
    WebSocket endpoint for real-time data streaming.
    
    Messages from client should be JSON with format:
    {
        "action": "subscribe" | "unsubscribe" | "ping",
        "task_id": int (for subscribe/unsubscribe)
    }
    """
    await manager.connect(websocket)
    
    try:
        # Send welcome message
        await websocket.send_json({
            "type": "connection",
            "status": "connected",
            "timestamp": datetime.utcnow().isoformat()
        })
        
        while True:
            # Receive message from client
            data = await websocket.receive_text()
            
            try:
                message = json.loads(data)
                action = message.get("action")
                
                if action == "subscribe":
                    task_id = message.get("task_id")
                    if task_id:
                        manager.subscribe_to_task(task_id, websocket)
                        await websocket.send_json({
                            "type": "subscription",
                            "status": "subscribed",
                            "task_id": task_id,
                            "timestamp": datetime.utcnow().isoformat()
                        })
                
                elif action == "unsubscribe":
                    task_id = message.get("task_id")
                    if task_id:
                        manager.unsubscribe_from_task(task_id, websocket)
                        await websocket.send_json({
                            "type": "subscription",
                            "status": "unsubscribed",
                            "task_id": task_id,
                            "timestamp": datetime.utcnow().isoformat()
                        })
                
                elif action == "ping":
                    await websocket.send_json({
                        "type": "pong",
                        "timestamp": datetime.utcnow().isoformat()
                    })
                
                else:
                    await websocket.send_json({
                        "type": "error",
                        "message": f"Unknown action: {action}",
                        "timestamp": datetime.utcnow().isoformat()
                    })
            
            except json.JSONDecodeError:
                await websocket.send_json({
                    "type": "error",
                    "message": "Invalid JSON format",
                    "timestamp": datetime.utcnow().isoformat()
                })
    
    except WebSocketDisconnect:
        manager.disconnect(websocket)


async def send_data_update(task_id: int, data: dict):
    """
    Helper function to send data updates to subscribed clients.
    Call this function when new data is available.
    """
    message = json.dumps({
        "type": "data",
        "task_id": task_id,
        "data": data,
        "timestamp": datetime.utcnow().isoformat()
    })
    await manager.broadcast_to_task(task_id, message)


async def send_event_update(task_id: int, event: dict):
    """
    Helper function to send event updates to subscribed clients.
    Call this function when new events occur.
    """
    message = json.dumps({
        "type": "event",
        "task_id": task_id,
        "event": event,
        "timestamp": datetime.utcnow().isoformat()
    })
    await manager.broadcast_to_task(task_id, message)


async def send_task_status_update(task_id: int, status: str):
    """
    Helper function to send task status updates to subscribed clients.
    Call this function when task status changes.
    """
    message = json.dumps({
        "type": "status",
        "task_id": task_id,
        "status": status,
        "timestamp": datetime.utcnow().isoformat()
    })
    await manager.broadcast_to_task(task_id, message)
