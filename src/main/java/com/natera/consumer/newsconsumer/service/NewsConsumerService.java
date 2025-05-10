package com.natera.consumer.newsconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natera.consumer.newsconsumer.models.News;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class NewsConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(NewsConsumerService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisService redisService;

    @KafkaListener(topics = "nyt.rss.articles", groupId = "news-consumer-group")
    public void consumeMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            String key = record.key();
            String message = record.value();
            News news = objectMapper.readValue(message, News.class);
            String publishedDate = news.getPublishedDate();
            Instant publishedAt = Instant.from(DATE_FORMATTER.parse(publishedDate));

            if (publishedAt.isBefore(Instant.now().minus(24, ChronoUnit.HOURS))) {
                logger.info("Skipping old message: {}", key);
                acknowledgment.acknowledge();
                return;
            }

            if (redisService.exists(key)) {
                logger.info("Article already exists in Redis: {}", key);
                acknowledgment.acknowledge();
                return;
            }

            redisService.saveWithTTL(key, news);
            logger.info("Saved new article with key: {}", key);
            acknowledgment.acknowledge();

        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage());
            // Do not acknowledge to allow retry
        }
    }


}
