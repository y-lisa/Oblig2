// Del 2: Oppgave 6
// Bygger en monitor rundt SubsekvensRegister vha komposisjon

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor1{
    private ArrayList<HashMap<String, Subsekvens>> hashBeholder;
    private HashMap<String, Subsekvens> register;
    private SubsekvensRegister beholder;
    private final Lock lock;

    public Monitor1() {
        beholder = new SubsekvensRegister();
        lock = new ReentrantLock();
    }

    // settInn()
    public void settInn(HashMap<String, Subsekvens> nyHashmap){
        // Laaser metoden slik at kun én traad kan kjore om gangen
        lock.lock();
        try {
            beholder.settInn(nyHashmap);
        } finally {
            // Aapner laasen igjen
            lock.unlock();
        }
    }

    // taUt()
    public HashMap<String, Subsekvens> taUt(){
        // Laaser metoden
        lock.lock();
        try {
            return beholder.taUt();
        } finally{
            lock.unlock();
        }
    }

    // antallHashMaper()
    public int antallHashMaper(){
        lock.lock();
        try{
            return beholder.antallHashMaper();
        } finally {
            lock.unlock();
        }
    }

    // lesFil()
    // Statiske metoder deler ikke ressurser, slik at det ikke trengs laas.
    public static HashMap<String, Subsekvens> lesFil(String filnavn){
        return SubsekvensRegister.lesFil(filnavn);
    }

    // slaaSammen()
    public static HashMap<String, Subsekvens> slaaSammen(HashMap<String, Subsekvens> hashMap1, HashMap<String, Subsekvens> hashMap2){
        return SubsekvensRegister.slaaSammen(hashMap1, hashMap2);
    }
}