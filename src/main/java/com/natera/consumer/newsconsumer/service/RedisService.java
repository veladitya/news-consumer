package com.natera.consumer.newsconsumer.service;

import com.natera.consumer.newsconsumer.models.News;
import com.natera.consumer.newsconsumer.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private final NewsRepository newsRepository;

    public RedisService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    // Method: Check if the key exists in Redis
    public boolean exists(String key) {
        boolean exists = newsRepository.existsById(key);
        logger.info("Checking existence of key {}: {}", key, exists);
        return exists;
    }

    // Method: Save data in Redis with TTL
    public void saveWithTTL(String key, News value) {
        newsRepository.save(value);
        logger.info("Saved key {}", key);
    }
}
