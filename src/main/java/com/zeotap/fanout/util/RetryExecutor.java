package com.zeotap.fanout.util;

public class RetryExecutor {

    private final RetryPolicy retryPolicy;

    public RetryExecutor(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public void executeWithRetry(Runnable task, Runnable onFailure) {

        int attempt = 0;
        long delay = retryPolicy.getInitialDelayMs();

        while (attempt < retryPolicy.getMaxRetries()) {
            try {
                task.run();
                return; // ✅ SUCCESS
            } catch (Exception e) {
                attempt++;

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }

                delay = (long) (delay * retryPolicy.getBackoffMultiplier());
            }
        }

        // ❌ DLQ
        onFailure.run();
    }
}
