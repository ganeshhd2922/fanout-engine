package com.zeotap.fanout.consumer;

import com.zeotap.fanout.event.Event;
import com.zeotap.fanout.util.ProcessedEventTracker;

public class LoggingConsumer implements EventConsumer {

    private final String name;

    public LoggingConsumer(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void consume(Event event) {
        String key = name + ":" + event.getId();

        if (ProcessedEventTracker.alreadyProcessed(key)) {
            return; // idempotent
        }

        System.out.println(
            "[Consumer-" + name + "] processed event " +
            event.getId() + " payload=" + event.getPayload()
        );
    }
}
