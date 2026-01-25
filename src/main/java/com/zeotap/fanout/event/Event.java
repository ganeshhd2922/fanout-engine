package com.zeotap.fanout.event;

public class Event {
    private final String id;
    private final String payload;

    public Event(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }
}
