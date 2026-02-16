package transform;

import ingestion.Record;

import java.util.HashMap;
import java.util.Map;

public class MapTransformer implements Transformer {

    @Override
    public Object transform(Record record) {
        Map<String, String> map = new HashMap<>();
        map.put("id", record.id);
        map.put("name", record.name);
        map.put("department", record.department);
        return map;
    }
}
