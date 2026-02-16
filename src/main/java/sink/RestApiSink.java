package sink;

public class RestApiSink implements Sink {

    @Override
    public void send(Object data) throws Exception {
        Thread.sleep(50); // simulate latency
        System.out.println("REST Sink -> " + data);
    }
}
