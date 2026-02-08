"""
Feature extraction from log entries
"""
import logging
from app.schemas.log_schema import LogSchema

logger = logging.getLogger(__name__)

class FeatureExtractor:
    """Extract numerical features from log entries for ML model"""
    
    def extract(self, log: LogSchema) -> list:
        """
        Extract numerical features from a log entry
        Returns a list of features suitable for ML model
        """
        features = []
        
        try:
            # Response time feature (from metadata if available)
            response_time = self._get_response_time(log)
            features.append(response_time)
            
            # Error rate feature (based on response code)
            error_rate = self._get_error_rate(log)
            features.append(error_rate)
            
            # Request size feature (from metadata)
            request_size = self._get_request_size(log)
            features.append(request_size)
            
            # Timestamp feature (hour of day as cyclic feature)
            hour_sin = self._get_hour_sin(log)
            features.append(hour_sin)
            
            # Timestamp feature (hour of day cyclic component)
            hour_cos = self._get_hour_cos(log)
            features.append(hour_cos)
            
            # Message length feature
            message_length = len(log.message) if log.message else 0
            features.append(message_length / 1000.0)  # Normalize
            
            # Severity level feature (numeric)
            severity_score = self._get_severity_score(log)
            features.append(severity_score)
            
        except Exception as e:
            logger.error(f"Error extracting features: {e}")
            features = [0.0] * 7  # Return zeros if extraction fails
        
        return features
    
    def _get_response_time(self, log: LogSchema) -> float:
        """Extract response time from log metadata"""
        if log.metadata and "response_time_ms" in log.metadata:
            return min(float(log.metadata["response_time_ms"]), 10000) / 10000.0
        return 0.5  # Default normalized value
    
    def _get_error_rate(self, log: LogSchema) -> float:
        """Extract error rate based on response code"""
        if log.response_code:
            if 400 <= log.response_code < 500:
                return 0.7
            elif log.response_code >= 500:
                return 0.9
        return 0.1  # Normal responses
    
    def _get_request_size(self, log: LogSchema) -> float:
        """Extract request size from metadata"""
        if log.metadata and "request_size_bytes" in log.metadata:
            return min(float(log.metadata["request_size_bytes"]), 100000) / 100000.0
        return 0.5  # Default normalized value
    
    def _get_hour_sin(self, log: LogSchema) -> float:
        """Extract hour-of-day as sine component (cyclic feature)"""
        if log.timestamp:
            hour = log.timestamp.hour
            import math
            return math.sin(2 * math.pi * hour / 24)
        return 0.0
    
    def _get_hour_cos(self, log: LogSchema) -> float:
        """Extract hour-of-day as cosine component (cyclic feature)"""
        if log.timestamp:
            hour = log.timestamp.hour
            import math
            return math.cos(2 * math.pi * hour / 24)
        return 0.0
    
    def _get_severity_score(self, log: LogSchema) -> float:
        """Map severity level to numeric score"""
        severity_map = {
            "CRITICAL": 0.95,
            "HIGH": 0.75,
            "MEDIUM": 0.5,
            "LOW": 0.25,
            "INFO": 0.1
        }
        return severity_map.get(log.severity, 0.5)
