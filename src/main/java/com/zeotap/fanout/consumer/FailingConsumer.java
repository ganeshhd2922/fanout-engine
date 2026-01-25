package com.zeotap.fanout.consumer;

import com.zeotap.fanout.event.Event;

import java.util.Random;

public class FailingConsumer implements EventConsumer {

    private final String name;
    private final Random random = new Random();

    public FailingConsumer(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void consume(Event event) throws Exception {
        // 50% failure simulation
        if (random.nextBoolean()) {
            throw new RuntimeException("Simulated failure");
        }

        System.out.println(
            "[Consumer-" + name + "] SUCCESS event=" + event.getId()
        );
    }
}
