High-Throughput Fan-Out Engine (Java Backend Engineering Challenge)

============================================================

1. Overview

This project implements a High-Throughput Data Fan-Out & Transformation Engine.
The system reads records from a large flat-file source and distributes them
concurrently to multiple downstream systems (mock sinks), ensuring correct
data transformation, backpressure handling, throttling, and resilience.

The design is inspired by real-world data propagation pipelines used for
analytics, search indexing, caches, and external APIs.

============================================================

2. Setup Instructions

Prerequisites:
- Java 8 or higher
- Maven 3.6+
- Git

Build the project:
mvn clean compile

Run the application:
mvn exec:java "-Dexec.mainClass=App"

The application will:
- Stream records from a sample CSV file
- Fan-out records to multiple sinks in parallel
- Print metrics every 5 seconds

============================================================

3. Architecture Diagram (Textual)

The data flow in the system is as follows:

File Reader (Streaming, BufferedReader)
        
        ↓
BlockingQueue<Record>  (Backpressure)
        
        ↓
Fan-Out Orchestrator (ExecutorService)
   
   ↓        ↓        ↓        ↓
REST Sink  gRPC Sink  MQ Sink  Wide-Column DB Sink
   
   ↓        ↓        ↓        ↓
JSON     Protobuf     XML      Map / CQL-style Data

Each sink has:
- Its own transformer
- Its own rate limiter
- Independent failure handling

============================================================

4. Design Decisions

Backpressure Handling:
A bounded BlockingQueue is used between the ingestion layer and the processing
layer. If downstream sinks are slow, producers block automatically. This
prevents memory overflow and ensures the application can safely process very
large files (up to 100GB) with a small heap size.

Concurrency Model:
An ExecutorService backed by a fixed thread pool sized to the number of
available CPU cores is used. Each record is fanned out to multiple sinks in
parallel. This model provides predictable performance, avoids race conditions,
and scales linearly with available cores.

Transformation Strategy:
A Strategy Pattern is used for transformations. Each sink has its own
Transformer implementation (JSON, Protobuf, XML, Map). Adding a new sink does
not require changes to the core orchestrator.

Rate Limiting:
Each sink is protected by an independent rate limiter implemented using a
Semaphore. This prevents overwhelming downstream systems and allows fine-
grained throughput control per sink.

Resilience and Retries:
Each sink operation supports up to 3 retry attempts. Failures after retries
are recorded, ensuring that every record is accounted for as either a success
or a failure.

============================================================

5. Assumptions

- Input files are well-formed CSV records.
- Mock sinks simulate network latency and failures but do not perform real
  network calls.
- Protobuf, XML, and database formats are simulated for architectural
  demonstration purposes.
- Downstream systems are assumed to be eventually consistent.
- The focus is on correctness, scalability, and architecture rather than
  real protocol implementations.

============================================================

6. Observability

The system prints a status update every 5 seconds showing:
- Total records processed
- Throughput (records per second)
- Failure count after retries

This allows easy monitoring of pipeline health and performance.

============================================================

7. Prompts (AI Tool Usage Disclosure)

AI tools (ChatGPT) were used for:
- Understanding assignment requirements
- Designing the fan-out architecture
- Choosing concurrency and backpressure strategies
- Debugging Java and Maven issues
- Writing and refining README documentation

The final design, implementation, and understanding of the system are my own.

============================================================

8. Conclusion

This project demonstrates a scalable, resilient, and extensible fan-out
processing engine using standard Java concurrency primitives, clean design
patterns, and streaming-based memory management. The architecture mirrors
real-world distributed data pipelines while remaining simple and testable.
