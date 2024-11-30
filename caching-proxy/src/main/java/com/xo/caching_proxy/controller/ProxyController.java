package com.xo.caching_proxy.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ProxyController {
    private static final Logger logger = LoggerFactory.getLogger(ProxyController.class);

    private String originUrl;
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
        logger.info("Origin URL set to: {}", originUrl);
    }

    public void clearCache() {
        cache.clear();
        logger.info("Cache cleared");
    }

    @GetMapping("/**")
    public ResponseEntity<String> handleRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        String fullUrl = originUrl + path;

        logger.info("Received request for path: {}", path);
        logger.info("Full URL to be requested: {}", fullUrl);

        try {
            // Check cache
            if (cache.containsKey(fullUrl)) {
                logger.info("Cache hit: {}", fullUrl);
                return ResponseEntity.ok(cache.get(fullUrl));
            }

            // Forward request to origin
            logger.info("Cache miss: Forwarding to {}", fullUrl);
            ResponseEntity<String> response = restTemplate.getForEntity(fullUrl, String.class);

            // Cache response
            cache.put(fullUrl, response.getBody());
            return response;
        } catch (RestClientException e) {
            logger.error("Error forwarding request to origin", e);
            return ResponseEntity.status(500).body("Error forwarding request: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error processing request", e);
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }
}
