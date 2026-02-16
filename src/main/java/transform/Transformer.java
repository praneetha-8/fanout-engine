package transform;

import ingestion.Record;

public interface Transformer {
    Object transform(Record record);
}
