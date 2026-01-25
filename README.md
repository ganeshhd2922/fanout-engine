Fanout Engine – High Throughput Event Processing
Overview

This project implements a high-throughput fanout engine that delivers events to multiple consumers in parallel.
It is designed to be reliable, fault-tolerant, and testable, with built-in retry logic and Dead Letter Queue (DLQ) handling.

The engine ensures that each event is independently delivered to all registered consumers, even in the presence of transient failures.

Key Features

Parallel fanout using a configurable thread pool

Retry mechanism with configurable retries and exponential backoff

Dead Letter Queue (DLQ) handling for failed events

Graceful shutdown with proper synchronization

Unit tests covering asynchronous behavior

Clean Maven-based project structure

Architecture
Event
  │
  ▼
FanoutEngine
  ├── Consumer A (Thread 1)
  ├── Consumer B (Thread 2)
  ├── Consumer C (Thread 3)
  │
  └── RetryExecutor
        └── RetryPolicy
              ├── maxRetries
              ├── initialDelayMs
              └── backoffMultiplier

How Fanout Works

An event is published to the FanoutEngine

The engine submits a task for each consumer to an ExecutorService

Each consumer processes the event independently and concurrently

Failures are retried according to the configured retry policy

If all retries fail, the event is sent to the Dead Letter Queue (DLQ)

Retry & Backoff Strategy

The retry mechanism is implemented using:

RetryPolicy – defines retry configuration

RetryExecutor – executes tasks with retry logic

Retry Policy Parameters

maxRetries – number of retry attempts

initialDelayMs – initial delay before retry

backoffMultiplier – exponential backoff factor

Example:

RetryPolicy retryPolicy = new RetryPolicy(3, 100, 2.0);

Dead Letter Queue (DLQ)

If an event fails to process after all retry attempts:

The event is logged as a DLQ entry

Consumer name and event ID are recorded

Example log:

[DLQ] Consumer=C1 Event=event-123

Threading Model

Uses ExecutorService with a fixed thread pool

Ensures high throughput and controlled concurrency

Graceful shutdown waits for all in-flight tasks to complete

How to Run the Application
Prerequisites

Java 8+

Maven 3+

Run
mvn clean compile
mvn exec:java

How to Run Tests
mvn clean test


Expected result:

BUILD SUCCESS
Tests run: X, Failures: 0, Errors: 0

Testing Strategy

JUnit 4 is used for unit testing

Asynchronous behavior is handled using:

AtomicInteger for thread-safe counters

awaitTermination to ensure background tasks complete

Tests validate:

Fanout delivery to all consumers

Correct parallel execution

Design Decisions

ExecutorService chosen for controlled concurrency

RetryExecutor separates retry logic from business logic

Immutable RetryPolicy ensures thread safety

Anonymous consumers in tests for flexibility

Graceful shutdown to avoid race conditions in tests

Future Improvements (Optional)

Persist DLQ events to file or database

Add metrics (processed, retried, failed counts)

Support dynamic consumer registration

Replace ExecutorService with CompletableFuture

Conclusion

This fanout engine demonstrates:

Concurrent event processing

Robust retry and failure handling

Clean separation of concerns

Production-ready testing practices