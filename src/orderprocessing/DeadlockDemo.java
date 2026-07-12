package orderprocessing;

/**
 Dimostrazione di un deadlock classico: due thread che
 acquisiscono due lock in ordine INVERSO tra loro.
 il programma si blocca volutamente
 */
public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void causeDeadlock() {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread-1 ha acquisito lockA, attende lockB...");
                sleep(100);
                synchronized (lockB) {
                    System.out.println("Thread-1 ha acquisito anche lockB");
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread-2 ha acquisito lockB, attende lockA...");
                sleep(100);
                synchronized (lockA) {
                    System.out.println("Thread-2 ha acquisito anche lockA");
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();
        // Questi due thread non termineranno mai: DEADLOCK.
    }

    /** Versione corretta: entrambi i thread acquisiscono i lock nello stesso ordine. */
    public static void fixedVersion() {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                sleep(100);
                synchronized (lockB) {
                    System.out.println("Thread-1 completato senza deadlock");
                }
            }
        }, "Thread-1-fixed");

        Thread t2 = new Thread(() -> {
            synchronized (lockA) { // stesso ordine: prima A, poi B
                sleep(100);
                synchronized (lockB) {
                    System.out.println("Thread-2 completato senza deadlock");
                }
            }
        }, "Thread-2-fixed");

        t1.start();
        t2.start();
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
