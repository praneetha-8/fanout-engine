import ingestion.*;
import orchestrator.FanOutEngine;
import sink.*;
import throttling.*;
import transform.*;
import metrics.MetricsReporter;

import java.util.*;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) throws Exception {

        BlockingQueue<Record> queue = new ArrayBlockingQueue<>(1000);

        MetricsReporter.start();

        new Thread(() -> {
            try {
                FileRecordReader.read("sample-data/input.csv", queue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        FanOutEngine engine = new FanOutEngine();

        List<Sink> sinks = Arrays.asList(new RestApiSink());
        List<Transformer> transformers = Arrays.asList(new JsonTransformer());
        List<SimpleRateLimiter> limiters = Arrays.asList(new SimpleRateLimiter(5));

        while (true) {
            Record record = queue.take();
            engine.fanOut(record, sinks, transformers, limiters);
            MetricsReporter.processed.incrementAndGet();
        }
    }
}
