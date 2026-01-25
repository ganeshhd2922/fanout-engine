package com.zeotap.fanout;

import com.zeotap.fanout.consumer.*;
import com.zeotap.fanout.engine.FanoutEngine;
import com.zeotap.fanout.event.Event;
import com.zeotap.fanout.util.RetryPolicy;

import java.util.List;
import java.util.UUID;

public class App {

    public static void main(String[] args) {

        // 1️⃣ Consumers
        List<EventConsumer> consumers = List.of(
                new LoggingConsumer("A"),
                new LoggingConsumer("B"),
                new FailingConsumer("C")
        );

        // 2️⃣ Retry Policy
        RetryPolicy retryPolicy = new RetryPolicy(
                3,      // max retries
                200,    // initial delay (ms)
                2.0     // backoff multiplier
        );

        // 3️⃣ Fanout Engine
        FanoutEngine engine = new FanoutEngine(
                consumers,
                5,          // thread pool size
                retryPolicy
        );

        // 4️⃣ Publish Events
        for (int i = 1; i <= 20; i++) {
            Event event = new Event(
                    UUID.randomUUID().toString(),
                    "event-" + i
            );
            engine.publish(event);
        }

        // 5️⃣ Shutdown
        engine.shutdown();
        System.out.println("Fanout Engine shutdown completed");
    }
}
