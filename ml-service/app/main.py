"""
ML Anomaly Detection Service
Python FastAPI application for scoring logs with Isolation Forest
"""
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import logging
import os
from app.model.anomaly_detector import AnomalyDetector
from app.schemas.log_schema import LogSchema, ScoreResponse

# Initialize FastAPI app
app = FastAPI(
    title="SentinelStream ML Anomaly Detector",
    description="Real-time anomaly detection using Isolation Forest",
    version="1.0.0"
)

# Enable CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Initialize anomaly detector
anomaly_detector = AnomalyDetector()

@app.on_event("startup")
async def startup_event():
    """Initialize model on startup"""
    logger.info("ML Anomaly Detection Service started")
    anomaly_detector.initialize_model()

@app.post("/score", response_model=ScoreResponse)
async def score_log(log: LogSchema):
    """
    Score a log entry for anomalies
    Returns anomaly score between 0 and 1 (higher = more anomalous)
    """
    try:
        score = anomaly_detector.predict(log)
        is_anomaly = score > 0.7  # anomaly threshold

        return ScoreResponse(
            score=score,
            is_anomaly=is_anomaly,
            log_id=log.id,
            model_version="1.0.0"
        )
    except Exception as e:
        logger.error(f"Error scoring log: {e}")
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/batch-score")
async def batch_score(logs: list[LogSchema]):
    """
    Batch scoring of multiple logs
    """
    try:
        results = []
        for log in logs:
            score = anomaly_detector.predict(log)
            results.append({
                "log_id": log.id,
                "score": score,
                "is_anomaly": score > 0.7
            })
        return {"results": results, "count": len(results)}
    except Exception as e:
        logger.error(f"Error in batch scoring: {e}")
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/health")
async def health_check():
    """Health check endpoint"""
    return {
        "status": "healthy",
        "service": "ml-anomaly-detection",
        "model_loaded": anomaly_detector.model is not None
    }

@app.get("/model/info")
async def model_info():
    """Get model information"""
    return {
        "model_type": "Isolation Forest",
        "version": "1.0.0",
        "threshold": 0.7,
        "n_estimators": 100,
        "contamination": 0.1
    }

@app.post("/model/retrain")
async def retrain_model():
    """Trigger model retraining"""
    try:
        anomaly_detector.initialize_model()
        logger.info("Model retraining triggered")
        return {"status": "success", "message": "Model retrained"}
    except Exception as e:
        logger.error(f"Error retraining model: {e}")
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        app,
        host="0.0.0.0",
        port=int(os.getenv("PORT", 8088)),
        log_level="info"
    )
