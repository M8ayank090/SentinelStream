package com.sentinelstream.common.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Kafka configuration properties
 */
@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String bootstrapServers = "localhost:9092";
    private String groupId = "sentinelstream-group";
    private int concurrency = 1;
    private int pollTimeoutMs = 3000;
    private int maxPollRecords = 500;
}
