package sink;

public class GrpcSink implements Sink {

    @Override
    public void send(Object data) throws Exception {
        Thread.sleep(30); // simulate gRPC latency
        System.out.println("gRPC Sink -> " + data);
    }
}
