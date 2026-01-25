package com.zeotap.fanout.core;

public class FanoutConfig {

    private final int queueCapacity;
    private final int workerThreads;
    private final int maxRetries;

    public FanoutConfig(int queueCapacity, int workerThreads, int maxRetries) {
        this.queueCapacity = queueCapacity;
        this.workerThreads = workerThreads;
        this.maxRetries = maxRetries;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public int getMaxRetries() {
        return maxRetries;
    }
}
