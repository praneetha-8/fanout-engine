package orchestrator;

import ingestion.Record;
import sink.Sink;
import throttling.SimpleRateLimiter;
import transform.Transformer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class FanOutEngine {

    private final ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static AtomicLong failures = new AtomicLong(0);

    public void fanOut(
            Record record,
            List<Sink> sinks,
            List<Transformer> transformers,
            List<SimpleRateLimiter> limiters) {

        for (int i = 0; i < sinks.size(); i++) {
            final int index = i;

            executor.submit(() -> {
                int attempts = 0;
                boolean success = false;

                while (attempts < 3 && !success) {
                    try {
                        attempts++;
                        limiters.get(index).acquire();
                        Object data = transformers.get(index).transform(record);
                        sinks.get(index).send(data);
                        success = true;
                    } catch (Exception e) {
                        if (attempts == 3) {
                            failures.incrementAndGet();
                            System.err.println("Failed after retries: " + record.id);
                        }
                    }
                }
            });
        }
    }
}
