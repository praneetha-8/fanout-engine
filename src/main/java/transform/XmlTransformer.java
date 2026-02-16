package transform;

import ingestion.Record;

public class XmlTransformer implements Transformer {

    @Override
    public Object transform(Record record) {
        return "<record>" +
               "<id>" + record.id + "</id>" +
               "<name>" + record.name + "</name>" +
               "<dept>" + record.department + "</dept>" +
               "</record>";
    }
}
