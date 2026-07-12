package orderprocessing;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int BUFFER_CAPACITY = 5;
        final int NUM_PRODUCERS = 3;
        final int NUM_CONSUMERS = 2;
        final int ITEMS_PER_PRODUCER = 10;

        BoundedBufferManual buffer = new BoundedBufferManual(BUFFER_CAPACITY);

        List<Thread> threads = new ArrayList<>();

        // Avvio dei produttori
        for (int i = 1; i <= NUM_PRODUCERS; i++) {
            Thread t = new Thread(new Producer(buffer, i, ITEMS_PER_PRODUCER), "Producer-" + i);
            threads.add(t);
            t.start();
        }

        // Avvio dei consumatori (calcolo il totale item da consumare per bilanciare)
        int totalItems = NUM_PRODUCERS * ITEMS_PER_PRODUCER;
        int itemsPerConsumer = totalItems / NUM_CONSUMERS;
        for (int i = 1; i <= NUM_CONSUMERS; i++) {
            Thread t = new Thread(new Consumer(buffer, itemsPerConsumer), "Consumer-" + i);
            threads.add(t);
            t.start();
        }

        // Attendo la terminazione di TUTTI i thread prima di stampare il report finale.
        // join() è bloccante: il Main Thread aspetta che ogni thread finisca il suo lavoro.
        for (Thread t : threads) {
            t.join();
        }

        SharedStats.printFinalReport();
    }
}