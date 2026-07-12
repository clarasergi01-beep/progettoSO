package orderprocessing;

public class Consumer implements Runnable {
    private final BoundedBufferManual buffer;
    private final int itemsToConsume;

    public Consumer(BoundedBufferManual buffer, int itemsToConsume) {
        this.buffer = buffer;
        this.itemsToConsume = itemsToConsume;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsToConsume; i++) {
                Order order = buffer.take();
                SharedStats.orderConsumed();
                Thread.sleep((long) (Math.random() * 800)); // simula tempo di elaborazione
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + Thread.currentThread().getName() + "] Interrotto.");
        }
    }
}