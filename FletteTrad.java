// Oppgave 9: Flette traader sammen

import java.util.ArrayList;
import java.util.HashMap;

public class FletteTrad implements Runnable{
    Monitor2 monitor2;
    ArrayList<HashMap<String, Subsekvens>> array;
    HashMap<String, Subsekvens> hash;
    HashMap<String, Subsekvens> map;
    HashMap<String, Subsekvens> nyHash;

    public FletteTrad(Monitor2 monitor2){
        this.monitor2 = monitor2;
    }

    @Override
    public void run(){
        while (monitor2.antallHashMaper() > 1){
            HashMap<String, Subsekvens> subsekvens1 = monitor2.taUt();
            HashMap<String, Subsekvens> subsekvens2 = monitor2.taUt();

            // Sjekker om det er nok HashMap-er
            if (subsekvens1 == null || subsekvens2 == null){
                System.out.println("Ikke nok HashMap-er!");
                System.exit(0);
                
            } else {
            // Ellers slaas de sammen
            HashMap<String, Subsekvens> flettet = monitor2.slaaSammen(subsekvens1, subsekvens2);
            monitor2.settInn(flettet);
            }
        } 
    }
}