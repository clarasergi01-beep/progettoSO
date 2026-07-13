Order Processing System: Producer-Consumer
----------------------------------------------------------------------------------------------
Questo progetto è un'analisi sperimentale della sincronizzazione tra thread e della gestione delle risorse condivise in Java. 
Implementa il classico problema del Produttore-Consumatore per dimostrare come i sistemi operativi gestiscono la mutua esclusione e la cooperazione tra processi paralleli.

Il sistema simula una pipeline di elaborazione ordini dove:
1. Produttori generano ordini a velocità variabile.
2. Consumatori elaborano gli ordini con un ritmo differente.
3. Un Buffer Limitato funge da arbitro per evitare overflow (pieno) o underflow (vuoto).


Architettura del Software
---------------------------------------------------------
Classe                                   Responsabilità
-------------------------------------------------------------------
Order                                    Modello dati immutabile (Thread-Safe per design).
BoundedBufferManual                      Implementazione del Monitor tramite wait/notify.
BoundedBufferBlockingQueue               Implementazione moderna tramite java.util.concurrent.
SharedStats                              Gestore delle statistiche con contatori atomici.
Producer / Consumer                      Thread operativi che simulano il carico di lavoro.
Main                                     Orchestratore che gestisce il ciclo di vita dei thread e il join().
