package com.zeotap.fanout.util;

/**
 * Retry configuration for Fanout Engine.
 * Immutable & thread-safe.
 */
public class RetryPolicy {

    private final int maxRetries;
    private final long initialDelayMs;
    private final double backoffMultiplier;

    // ✅ Default retry policy (production friendly)
    public static final RetryPolicy DEFAULT =
            new RetryPolicy(3, 100, 2.0);

    public RetryPolicy(int maxRetries, long initialDelayMs, double backoffMultiplier) {

        // 🔒 Validation (VERY IMPORTANT for assignment)
        if (maxRetries < 0) {
            throw new IllegalArgumentException("maxRetries cannot be negative");
        }
        if (initialDelayMs <= 0) {
            throw new IllegalArgumentException("initialDelayMs must be > 0");
        }
        if (backoffMultiplier < 1.0) {
            throw new IllegalArgumentException("backoffMultiplier must be >= 1.0");
        }

        this.maxRetries = maxRetries;
        this.initialDelayMs = initialDelayMs;
        this.backoffMultiplier = backoffMultiplier;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public long getInitialDelayMs() {
        return initialDelayMs;
    }

    public double getBackoffMultiplier() {
        return backoffMultiplier;
    }

    @Override
    public String toString() {
        return "RetryPolicy{" +
                "maxRetries=" + maxRetries +
                ", initialDelayMs=" + initialDelayMs +
                ", backoffMultiplier=" + backoffMultiplier +
                '}';
    }
}
