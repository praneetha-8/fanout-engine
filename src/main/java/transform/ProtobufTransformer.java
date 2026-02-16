package transform;

import ingestion.Record;

public class ProtobufTransformer implements Transformer {

    @Override
    public Object transform(Record record) {
        // Simulated Protobuf format
        return "PROTOBUF[id=" + record.id +
               ", name=" + record.name +
               ", dept=" + record.department + "]";
    }
}
