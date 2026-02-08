package com.sentinelstream.common.constants;

/**
 * Application-wide constants
 */
public final class AppConstants {

    // Kafka Topics
    public static final String KAFKA_TOPIC_LOGS_RAW = "logs.raw";
    public static final String KAFKA_TOPIC_LOGS_ENRICHED = "logs.enriched";
    public static final String KAFKA_TOPIC_ALERTS_GENERATED = "alerts.generated";
    public static final String KAFKA_TOPIC_AUDIT_EVENTS = "audit.events";

    // HTTP Headers
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_USER_ID = "X-User-ID";
    public static final String HEADER_CORRELATION_ID = "X-Correlation-ID";

    // JWT Claims
    public static final String JWT_CLAIM_ROLES = "roles";
    public static final String JWT_CLAIM_USER_ID = "userId";
    public static final String JWT_CLAIM_USERNAME = "username";

    // Security Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_ANALYST = "ROLE_ANALYST";
    public static final String ROLE_VIEWER = "ROLE_VIEWER";

    // Log Severity Levels
    public static final String SEVERITY_CRITICAL = "CRITICAL";
    public static final String SEVERITY_HIGH = "HIGH";
    public static final String SEVERITY_MEDIUM = "MEDIUM";
    public static final String SEVERITY_LOW = "LOW";
    public static final String SEVERITY_INFO = "INFO";

    // Alert Status
    public static final String ALERT_STATUS_OPEN = "OPEN";
    public static final String ALERT_STATUS_IN_REVIEW = "IN_REVIEW";
    public static final String ALERT_STATUS_RESOLVED = "RESOLVED";
    public static final String ALERT_STATUS_FALSE_POSITIVE = "FALSE_POSITIVE";

    // Date Formats
    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd HH:mm:ss";

    // Elasticsearch
    public static final String ES_INDEX_LOGS = "logs";
    public static final String ES_INDEX_ALERTS = "alerts";
    public static final int ES_SHARDS = 5;
    public static final int ES_REPLICAS = 1;

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 1000;

    private AppConstants() {}
}
