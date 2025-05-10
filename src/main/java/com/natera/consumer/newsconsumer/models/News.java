package com.natera.consumer.newsconsumer.models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash(value = "News", timeToLive = 3600 * 24)
@Builder
@Jacksonized
public class News implements Serializable {
    @Id
    private String id;
    private String title;
    private String link;
    private String description;
    private String publishedDate;
    private String mediaUrl;
}
