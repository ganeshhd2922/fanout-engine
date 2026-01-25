# 🚀 Fanout Engine – High Throughput Event Processing

A concurrent, fault-tolerant fanout engine built in Java using Maven.

## 📌 Overview

This project implements a high-throughput fanout engine that delivers events to multiple consumers in parallel.
Each consumer processes events independently, ensuring reliability even in the presence of failures.

The system includes:

Retry handling with exponential backoff

Dead Letter Queue (DLQ) for failed events

Clean separation of concerns and testability

## ✨ Key Features

✅ Parallel fanout using configurable thread pool
✅ Independent event delivery per consumer
✅ Retry mechanism with exponential backoff
✅ Dead Letter Queue (DLQ) handling
✅ Graceful shutdown
✅ Maven-based build & execution
✅ Unit tested with JUnit

## 🧠 High-Level Architecture
Event
  |
  v
FanoutEngine
  |-- Consumer A  (Thread Pool)
  |-- Consumer B  (Thread Pool)
  |-- Consumer C  (Thread Pool)
          |
          v
     RetryExecutor
          |
          v
      RetryPolicy

## 🔁 How Fanout Works

An event is published to the FanoutEngine

The engine submits one task per consumer to an ExecutorService

Consumers process the event concurrently

Failures are retried based on retry policy

After all retries fail, the event is sent to DLQ

🔄 Retry & Backoff Strategy

Retry logic is implemented using two components:

🧩 RetryPolicy

Defines retry configuration:

maxRetries

initialDelayMs

backoffMultiplier

RetryPolicy retryPolicy = new RetryPolicy(3, 100, 2.0);

⚙️ RetryExecutor

Executes consumer logic with retry & backoff automatically applied.

☠️ Dead Letter Queue (DLQ)

If an event fails after all retries:

Event is marked as failed

Consumer name and event ID are logged

Other consumers remain unaffected

📄 Example log:

[DLQ] Consumer=C1 Event=event-123

🧵 Threading Model

Uses ExecutorService with fixed thread pool

Enables controlled concurrency

Prevents consumer blocking

Supports graceful shutdown

## 📂 Project Structure
src/main/java
 └── com.zeotap.fanout
     ├── App.java
     ├── engine
     │   └── FanoutEngine.java
     ├── consumer
     │   └── EventConsumer.java
     ├── event
     │   └── Event.java
     └── util
         ├── RetryExecutor.java
         └── RetryPolicy.java

src/test/java
 └── com.zeotap.fanout
     └── FanoutEngineTest.java

🛡️ Non-Functional Considerations
⚡ Performance

Parallel processing via thread pool

High throughput under load

## 🧯 Fault Tolerance

Retries for transient failures

DLQ for permanent failures

🔒 Reliability

Failure isolation per consumer

Thread-safe design

🛑 Graceful Shutdown

Ensures in-flight tasks complete safely

## ▶️ Build & Run
Prerequisites

Java 8+

Maven 3+

Build
mvn clean package

Run Application
mvn exec:java

🧪 Run Tests
mvn clean test


## ✅ Expected Output:

BUILD SUCCESS
Tests run: X, Failures: 0, Errors: 0
