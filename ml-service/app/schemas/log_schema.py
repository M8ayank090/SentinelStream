"""
Pydantic schemas for ML service
"""
from pydantic import BaseModel
from typing import Optional, Dict, Any
from datetime import datetime

class LogSchema(BaseModel):
    """Log entry schema for ML scoring"""
    id: str
    source: str
    message: str
    severity: Optional[str] = "INFO"
    timestamp: Optional[datetime] = None
    metadata: Optional[Dict[str, Any]] = None
    source_ip: Optional[str] = None
    user_id: Optional[str] = None
    response_code: Optional[int] = None
    
    class Config:
        json_schema_extra = {
            "example": {
                "id": "log_123",
                "source": "web-server-1",
                "message": "User login attempt failed",
                "severity": "HIGH",
                "timestamp": "2024-02-08T10:30:00",
                "source_ip": "192.168.1.100",
                "response_code": 401
            }
        }

class ScoreResponse(BaseModel):
    """Anomaly score response"""
    score: float
    is_anomaly: bool
    log_id: str
    model_version: str
    
    class Config:
        json_schema_extra = {
            "example": {
                "score": 0.85,
                "is_anomaly": True,
                "log_id": "log_123",
                "model_version": "1.0.0"
            }
        }
