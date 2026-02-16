package transform;

import ingestion.Record;
import org.junit.Assert;
import org.junit.Test;

public class JsonTransformerTest {

    @Test
    public void testJsonTransformation() {
        JsonTransformer transformer = new JsonTransformer();
        Record record = new Record("1", "Alice", "Engineering");

        Object result = transformer.transform(record);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.toString().contains("Alice"));
    }
}
