package com.zeotap.fanout;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.zeotap.fanout.consumer.EventConsumer;
import com.zeotap.fanout.engine.FanoutEngine;
import com.zeotap.fanout.event.Event;
import com.zeotap.fanout.util.RetryPolicy;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class FanoutEngineTest {

    @Test
    public void shouldFanoutEventToAllConsumers() {

        AtomicInteger counter = new AtomicInteger(0);

        EventConsumer c1 = new EventConsumer() {
            @Override
            public void consume(Event event) {
                counter.incrementAndGet();
            }

            @Override
            public String name() {
                return "C1";
            }
        };

        EventConsumer c2 = new EventConsumer() {
            @Override
            public void consume(Event event) {
                counter.incrementAndGet();
            }

            @Override
            public String name() {
                return "C2";
            }
        };

        RetryPolicy retryPolicy = new RetryPolicy(1, 10, 1.0);

        FanoutEngine engine = new FanoutEngine(
                Arrays.asList(c1, c2),
                2,
                retryPolicy
        );

        engine.publish(new Event("test-id", "test-event"));
        engine.shutdown();

        assertEquals(2, counter.get());
    }
}
