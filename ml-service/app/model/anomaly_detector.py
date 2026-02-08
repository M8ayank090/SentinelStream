"""
Anomaly detection model using Isolation Forest
"""
import logging
import numpy as np
from sklearn.ensemble import IsolationForest
from sklearn.preprocessing import StandardScaler
from app.feature_engineering.feature_extractor import FeatureExtractor
from app.schemas.log_schema import LogSchema

logger = logging.getLogger(__name__)

class AnomalyDetector:
    """Isolation Forest-based anomaly detector for logs"""
    
    def __init__(self, contamination=0.1, n_estimators=100):
        self.model = None
        self.scaler = StandardScaler()
        self.contamination = contamination
        self.n_estimators = n_estimators
        self.feature_extractor = FeatureExtractor()
        self.is_fitted = False
    
    def initialize_model(self):
        """Initialize the Isolation Forest model"""
        self.model = IsolationForest(
            contamination=self.contamination,
            n_estimators=self.n_estimators,
            random_state=42,
            n_jobs=-1
        )
        
        # Generate synthetic training data for initial fitting
        training_data = self._generate_training_data()
        self.fit(training_data)
        logger.info("Anomaly detection model initialized")
    
    def fit(self, X):
        """Fit the model with training data"""
        if X is not None and len(X) > 0:
            X_scaled = self.scaler.fit_transform(X)
            self.model.fit(X_scaled)
            self.is_fitted = True
            logger.info(f"Model fitted with {len(X)} samples")
    
    def predict(self, log: LogSchema) -> float:
        """
        Predict anomaly score for a log entry
        Returns score between 0 and 1 (higher = more anomalous)
        """
        try:
            features = self.feature_extractor.extract(log)
            features_array = np.array(features).reshape(1, -1)
            
            # Normalize features
            features_scaled = self.scaler.transform(features_array)
            
            # Get anomaly score (negative distance to isolation)
            # Normalize to 0-1 range
            anomaly_score = self.model.score_samples(features_scaled)[0]
            normalized_score = 1 / (1 + np.exp(-anomaly_score))  # Sigmoid normalization
            
            return float(normalized_score)
        except Exception as e:
            logger.error(f"Error predicting anomaly score: {e}")
            return 0.0
    
    def _generate_training_data(self, n_samples=1000):
        """Generate synthetic normal log data for training"""
        # Generate synthetic "normal" behavior
        normal_response_times = np.random.normal(100, 30, n_samples)
        normal_error_rates = np.random.normal(0.1, 0.05, n_samples)
        normal_request_sizes = np.random.normal(500, 100, n_samples)
        
        training_data = np.column_stack([
            normal_response_times,
            normal_error_rates,
            normal_request_sizes
        ])
        
        return training_data
