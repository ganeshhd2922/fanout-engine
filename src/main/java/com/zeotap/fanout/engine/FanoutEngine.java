package com.zeotap.fanout.engine;

import com.zeotap.fanout.consumer.EventConsumer;
import com.zeotap.fanout.event.Event;
import com.zeotap.fanout.util.RetryExecutor;
import com.zeotap.fanout.util.RetryPolicy;



import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Fanout Engine:
 * - Sends each event to all consumers
 * - Uses thread pool
 * - Supports retry + DLQ
 */
public class FanoutEngine {

    private final List<EventConsumer> consumers;
    private final ExecutorService executor;
    private final RetryExecutor retryExecutor;

    // ✅ PROPER CONSTRUCTOR
    public FanoutEngine(
            List<EventConsumer> consumers,
            int threads,
            RetryPolicy retryPolicy
    ) {
        this.consumers = consumers;
        this.executor = Executors.newFixedThreadPool(threads);
        this.retryExecutor = new RetryExecutor(retryPolicy);
    }

    // ✅ Fanout logic
   public void publish(Event event) {
    for (EventConsumer consumer : consumers) {
        executor.submit(() ->
            retryExecutor.executeWithRetry(
                () -> {
                    try {
                        consumer.consume(event);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> System.out.println(
                    "[DLQ] Consumer=" + consumer.name() +
                    " Event=" + event.getId()
                )
            )
        );
    }
}

    // ✅ Graceful shutdown
   public void shutdown() {
    executor.shutdown();
    try {
        executor.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

}
