package orchestrator;

import ingestion.Record;
import org.junit.Test;
import org.mockito.Mockito;
import sink.Sink;
import throttling.SimpleRateLimiter;
import transform.Transformer;

import java.util.Arrays;

public class FanOutEngineTest {

    @Test
    public void testFanOutExecution() throws Exception {
        FanOutEngine engine = new FanOutEngine();

        Sink mockSink = Mockito.mock(Sink.class);
        Transformer mockTransformer = Mockito.mock(Transformer.class);

        Mockito.when(mockTransformer.transform(Mockito.any()))
                .thenReturn("{mock-data}");

        Record record = new Record("1", "Bob", "HR");

        engine.fanOut(
                record,
                Arrays.asList(mockSink),
                Arrays.asList(mockTransformer),
                Arrays.asList(new SimpleRateLimiter(10))
        );

        // Allow async execution to complete
        Thread.sleep(200);

        Mockito.verify(mockSink, Mockito.atLeastOnce())
                .send(Mockito.any());
    }
}
