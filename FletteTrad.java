// Oppgave 9: Flette traader sammen

import java.util.ArrayList;
import java.util.HashMap;

public class FletteTrad implements Runnable{
    Monitor2 monitor2;

    public FletteTrad(Monitor2 monitor2){
        this.monitor2 = monitor2;
    }

    @Override
    public void run(){
        while (monitor2.antallHashMaper() > 1){
            HashMap<String, Subsekvens> subsekvens1 = monitor2.taUt();
            HashMap<String, Subsekvens> subsekvens2 = monitor2.taUt();

            // Slaar sammen HashMap-ene
            HashMap<String, Subsekvens> flettet = monitor2.slaaSammen(subsekvens1, subsekvens2);
            monitor2.settInn(flettet);
            
        } System.exit(0);   // kun 1 traad igjen
    }
}
