#!/bin/bash

echo "Starting News Consumer..."
java -jar news-consumer.jar \
  --spring.config.location=${SPRING_CONFIG_LOCATION} \
  --spring.kafka.bootstrap-servers=${KAFKA_BROKER} \
  --spring.redis.host=${REDIS_HOST} \
  --spring.redis.port=${REDIS_PORT} \
  --spring.redis.password=${REDIS_PASSWORD}
