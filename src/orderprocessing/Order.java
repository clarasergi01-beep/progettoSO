package orderprocessing;

public class Order {
    private final int id;
    private final String description;
    private final long timestampCreation;

    public Order(int id, String description) {
        this.id = id;
        this.description = description;
        this.timestampCreation = System.currentTimeMillis();
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public long getTimestampCreation() { return timestampCreation; }

    @Override
    public String toString() {
        return "Order#" + id + " [" + description + "]";
    }
}