// Oppgave 10:
// Klassen bygger videre paa Monitor1.
// Skal hente ut to HashMap-er som skal flettes, 
// og identifisere naar det er kun én traad igjen.

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor2 extends Monitor1 {
    private final Condition kanFlette;      // bruker Condition til laasen

    public Monitor2() {
        super();        // henter fra Monitor1
        kanFlette = lock.newCondition();
    }

    // flett()
    public HashMap<String, Subsekvens> flett() {
        lock.lock();
        try {
            while (beholder.antallHashMaper() < 2) {
                try {
                    // Venter inne i monitoren hvis det ikke er nok HashMap-er til aa flette
                    kanFlette.await();
                } catch (InterruptedException ie) {
                    System.out.println("Feil i fletting!");
                }
            }

            // Ellers henter ut to HashMap-er som skal flettes
            HashMap<String, Subsekvens> hashMap1 = beholder.taUt();
            HashMap<String, Subsekvens> hashMap2 = beholder.taUt();

            // Fletter de to HashMap-ene
            HashMap<String, Subsekvens> flettetHashMap = slaaSammen(hashMap1, hashMap2);
            beholder.settInn(flettetHashMap);
            kanFlette.signalAll();

            return flettetHashMap;

        } finally {
            lock.unlock();
        }
    }
}