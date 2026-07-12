package orderprocessing;

public class Producer implements Runnable {
    private final BoundedBufferManual buffer;
    private final int itemsToProduce;
    private final int producerId;

    public Producer(BoundedBufferManual buffer, int producerId, int itemsToProduce) {
        this.buffer = buffer;
        this.producerId = producerId;
        this.itemsToProduce = itemsToProduce;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= itemsToProduce; i++) {
                Order order = new Order(producerId * 1000 + i, "Ordine da Producer-" + producerId);
                buffer.put(order);
                SharedStats.orderProduced();
                Thread.sleep((long) (Math.random() * 500)); // simula tempo di lavoro variabile
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + Thread.currentThread().getName() + "] Interrotto.");
        }
    }
}