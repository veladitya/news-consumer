# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the application JAR file
COPY target/news-consumer-0.0.1-SNAPSHOT.jar news-consumer.jar

# Copy the application.yml from resources directory
COPY src/main/resources/application.yml /app/config/application.yml

# Expose the application port
EXPOSE 7082

# Environment variables
ENV SPRING_CONFIG_LOCATION=/app/config/application.yml
ENV REDIS_HOST=localhost
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=my-password
ENV KAFKA_BROKER=localhost:39092,localhost:39093,localhost:39094
ENV KAFKA_TOPIC=nyt.rss.articles

# Add metadata
LABEL maintainer="yourname@example.com"
LABEL description="News Consumer Service"

# Copy the entrypoint script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Run the application
ENTRYPOINT ["/entrypoint.sh"]
