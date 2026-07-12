package orderprocessing;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Stessa semantica di BoundedBufferManual, ma usando le classi
 * di java.util.concurrent che implementano internamente lo stesso
 * meccanismo in modo ottimizzato*/
public class BoundedBufferBlockingQueue {

    private final BlockingQueue<Order> buffer;

    public BoundedBufferBlockingQueue(int capacity) {
        this.buffer = new ArrayBlockingQueue<>(capacity);
    }

    public void put(Order order) throws InterruptedException {
        buffer.put(order); // si blocca internamente se pieno, senza bisogno di wait/notify espliciti
        System.out.println("[" + Thread.currentThread().getName() + "] Prodotto: " + order);
    }

    public Order take() throws InterruptedException {
        Order order = buffer.take(); // si blocca internamente se vuoto
        System.out.println("[" + Thread.currentThread().getName() + "] Consumato: " + order);
        return order;
    }

    public int size() {
        return buffer.size();
    }
}
