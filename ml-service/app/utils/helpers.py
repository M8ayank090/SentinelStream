"""
Utility functions for ML service
"""
import logging
import os
from datetime import datetime

logger = logging.getLogger(__name__)

def setup_logging():
    """Configure logging for the service"""
    logging.basicConfig(
        level=os.getenv("LOG_LEVEL", "INFO"),
        format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
    )

def get_timestamp():
    """Get current timestamp in ISO format"""
    return datetime.utcnow().isoformat()
