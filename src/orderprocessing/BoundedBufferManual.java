package orderprocessing;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Buffer condiviso a capacità limitata (bounded buffer), implementato
 * "a basso livello" con il monitor pattern di Java.
 *
 * Regole del problema Produttore-Consumatore:
 *  - Un produttore deve BLOCCARSI se il buffer è pieno (evita overflow/starvation dei consumatori)
 *  - Un consumatore deve BLOCCARSI se il buffer è vuoto (evita di consumare dati inesistenti)
 *  - L'accesso al buffer deve essere in mutua esclusione (una sola operazione put/take alla volta)
 */
public class BoundedBufferManual {

    private final Queue<Order> buffer = new LinkedList<>();
    private final int capacity;

    public BoundedBufferManual(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Inserisce un ordine nel buffer. Se il buffer è pieno, il thread chiamante
     * si sospende (wait) rilasciando il lock, finché un consumatore non libera spazio.
     */
    public synchronized void put(Order order) throws InterruptedException {
        // IMPORTANTE: while, non if! Un thread può essere svegliato "a sproposito"
        // (spurious wakeup) oppure la condizione può essere ridiventata falsa
        // se un altro producer ha già riempito il buffer nel frattempo.
        while (buffer.size() == capacity) {
            System.out.println("[" + Thread.currentThread().getName() + "] Buffer PIENO, produttore in attesa...");
            wait(); // rilascia il lock e sospende il thread
        }

        buffer.add(order);
        System.out.println("[" + Thread.currentThread().getName() + "] Prodotto: " + order +
                            " (buffer: " + buffer.size() + "/" + capacity + ")");

        // Sveglio TUTTI i thread in attesa (sia altri producer che consumer),
        // perché non so a priori chi sta aspettando cosa. notifyAll() è più
        // sicuro di notify() anche se leggermente meno efficiente.
        notifyAll();
    }

    /**
     * Preleva un ordine dal buffer. Se il buffer è vuoto, il thread chiamante
     * si sospende finché un produttore non inserisce qualcosa.
     */
    public synchronized Order take() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("[" + Thread.currentThread().getName() + "] Buffer VUOTO, consumatore in attesa...");
            wait();
        }

        Order order = buffer.poll();
        System.out.println("[" + Thread.currentThread().getName() + "] Consumato: " + order +
                            " (buffer: " + buffer.size() + "/" + capacity + ")");

        notifyAll();
        return order;
    }

    public synchronized int size() {
        return buffer.size();
    }
}