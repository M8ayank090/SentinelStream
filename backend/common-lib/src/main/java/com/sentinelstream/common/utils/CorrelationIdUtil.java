package com.sentinelstream.common.utils;

import org.slf4j.MDC;
import java.util.UUID;

/**
 * Utility for correlation tracking across services
 */
public class CorrelationIdUtil {
    private static final String CORRELATION_ID = "correlationId";

    public static String getCorrelationId() {
        String id = MDC.get(CORRELATION_ID);
        return id != null ? id : generateNewId();
    }

    public static void setCorrelationId(String id) {
        MDC.put(CORRELATION_ID, id);
    }

    public static String generateNewId() {
        return UUID.randomUUID().toString();
    }

    public static void clear() {
        MDC.remove(CORRELATION_ID);
    }
}
