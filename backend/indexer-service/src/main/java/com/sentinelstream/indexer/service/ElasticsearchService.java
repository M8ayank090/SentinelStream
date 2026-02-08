package com.sentinelstream.indexer.service;

import com.sentinelstream.common.dto.EnrichedLogDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Elasticsearch indexing service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchService {

    private final RestHighLevelClient elasticsearchClient;
    private final ObjectMapper objectMapper;

    public void indexLog(EnrichedLogDTO log) throws IOException {
        String index = "logs-" + LocalDate.now();
        
        IndexRequest request = new IndexRequest(index)
                .id(log.getId())
                .source(objectMapper.writeValueAsString(log), XContentType.JSON);

        IndexResponse response = elasticsearchClient.index(request, RequestOptions.DEFAULT);
        log.debug("Log indexed: {} -> {}", log.getId(), response.getId());
    }

    public void createIndexTemplate() {
        try {
            // TODO: Create index template with proper mappings
            log.info("Index template creation not yet implemented");
        } catch (Exception e) {
            log.error("Error creating index template", e);
        }
    }
}
