package sink;

public class WideColumnDbSink implements Sink {

    @Override
    public void send(Object data) throws Exception {
        Thread.sleep(40); // simulate async DB upsert
        System.out.println("WideDB Sink -> " + data);
    }
}
