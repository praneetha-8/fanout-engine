package metrics;

import orchestrator.FanOutEngine;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MetricsReporter {

    public static AtomicLong processed = new AtomicLong(0);
    private static long lastCount = 0;

    public static void start() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                    long current = processed.get();
                    long throughput = current - lastCount;
                    lastCount = current;

                    System.out.println(
                            "Processed=" + current +
                            ", Throughput/sec=" + throughput +
                            ", Failures=" + FanOutEngine.failures.get()
                    );
                }, 5, 5, TimeUnit.SECONDS);
    }
}
