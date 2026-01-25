package com.zeotap.fanout.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessedEventTracker {

    private static final Set<String> processed = ConcurrentHashMap.newKeySet();

    public static boolean alreadyProcessed(String key) {
        return !processed.add(key);
    }
}
