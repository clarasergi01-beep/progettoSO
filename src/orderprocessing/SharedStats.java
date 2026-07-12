package orderprocessing;

public class SharedStats {
    // Versione SBAGLIATA (commentata, da citare in relazione come "cosa succede se..."):
    // private static int produced = 0;
    // public static void orderProduced() { produced++; }  // NON atomico! race condition

    private static final java.util.concurrent.atomic.AtomicInteger produced = new java.util.concurrent.atomic.AtomicInteger(0);
    private static final java.util.concurrent.atomic.AtomicInteger consumed = new java.util.concurrent.atomic.AtomicInteger(0);

    public static void orderProduced() { produced.incrementAndGet(); }
    public static void orderConsumed() { consumed.incrementAndGet(); }

    public static void printFinalReport() {
        System.out.println("\n=== REPORT FINALE ===");
        System.out.println("Ordini prodotti:  " + produced.get());
        System.out.println("Ordini consumati: " + consumed.get());
        System.out.println("Coerenza: " + (produced.get() == consumed.get() ? "OK ✔" : "ERRORE ✘"));
    }
}