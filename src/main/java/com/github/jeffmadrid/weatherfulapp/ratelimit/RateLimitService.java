package com.github.jeffmadrid.weatherfulapp.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Value("${rate-limit.capacity:5}")
    private int rateLimitCapacity;

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    /**
     * Consider sliding window algorithm.
     */
    private Bucket newBucket(String apiKey) {
        return Bucket.builder()
            .addLimit(Bandwidth.classic(rateLimitCapacity, Refill.intervally(5, Duration.ofHours(1))))
            .build();
    }
}
