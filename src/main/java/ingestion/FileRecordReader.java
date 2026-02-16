package ingestion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class FileRecordReader {

    public static void read(String filePath, BlockingQueue<Record> queue) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            Record record = new Record(parts[0], parts[1], parts[2]);
            queue.put(record); // backpressure
        }

        reader.close();
    }
}
