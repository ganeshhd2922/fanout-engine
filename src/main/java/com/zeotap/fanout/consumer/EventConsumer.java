package com.zeotap.fanout.consumer;

import com.zeotap.fanout.event.Event;

@FunctionalInterface
public interface EventConsumer {
    void consume(Event event) throws Exception;

    default String name() {
        return getClass().getSimpleName();
    }
}
