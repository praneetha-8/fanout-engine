package transform;

import ingestion.Record;

public class JsonTransformer implements Transformer {

    @Override
    public Object transform(Record record) {
        return "{ \"id\": \"" + record.id + "\", \"name\": \"" + record.name + "\" }";
    }
}
