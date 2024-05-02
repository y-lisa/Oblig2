// Oppgave 7:
// Traadklasse som leser en fil og legger HashMap inn i beholder av klassen Monitor1

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LeseTrad implements Runnable{
    public Monitor1 beholder = new Monitor1();
    public String filnavn;

    public LeseTrad(String filnavn, Monitor1 beholder){
        this.beholder = beholder;
        this.filnavn = filnavn;
    }

    @Override
    public void run() {
        // Leser en fil
        HashMap<String, Subsekvens> nyHashmap = beholder.lesFil(filnavn);
        // ... og legger HashMap inn i beholderen
        beholder.settInn(nyHashmap);
    }


}