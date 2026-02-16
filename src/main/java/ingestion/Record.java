package ingestion;

public class Record {
    public final String id;
    public final String name;
    public final String department;

    public Record(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
}
