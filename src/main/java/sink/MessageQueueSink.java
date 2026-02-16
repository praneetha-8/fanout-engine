package sink;

public class MessageQueueSink implements Sink {

    @Override
    public void send(Object data) throws Exception {
        Thread.sleep(20); // simulate queue publish
        System.out.println("MQ Sink -> " + data);
    }
}
