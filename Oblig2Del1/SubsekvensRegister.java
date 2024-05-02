// Oppgave 2:

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class SubsekvensRegister{
    private ArrayList<HashMap<String, Subsekvens>> hashBeholder;
    private HashMap<String, Subsekvens> register;

    public SubsekvensRegister(){
        hashBeholder = new ArrayList<>();
        register = new HashMap<>();
    }

    // Setter inn HashMap med subsekvenser
    public void settInn(HashMap<String, Subsekvens> nyHashmap){
        hashBeholder.add(nyHashmap);
    }

    // Tar ut en vilkaarlig HashMap med subsekvenser
    public HashMap<String, Subsekvens> taUt(){
        if (hashBeholder.isEmpty()){            // hvis den er tom returneres ingenting
            return null;
        } else{
            Random random = new Random();
            int randomTall = random.nextInt(hashBeholder.size());
            return hashBeholder.remove(randomTall);      // ellers tas en vilkaarlig HashMap
        }
    }

    // Sporr om hvor mange HashMap-er beholderen inneholder
    public int antallHashMaper(){
        return hashBeholder.size();
    }

// Oppgave 3:
// Metode som leser én fil med én persons immunrepertoar
    public static HashMap<String, Subsekvens> lesFil(String filnavn){
        HashMap<String, Subsekvens> nyHashMap = new HashMap<>();
        
        try {
            File fil = new File(filnavn);
            Scanner sc = new Scanner(fil);

            while (sc.hasNextLine()){
                String linje = sc.nextLine();

                // Sjekker om linjen er kortere enn tre tegn
                if (linje.length() < 3){
                    System.out.println("Linjen er kortere enn tre tegn!");
                }

                // Deler opp linjen for å finne subsekvenser med lengde tre tegn
                // for-lokke
                for (int i = 0; i <= linje.length()-3; i++){        // -3 for at vi skal holde oss innenfor linjes lengde
                    String subsekvens = linje.substring(i,i+3);     // bruker substring() for å hente ut subsekvens fra linje
                    int antall = 1;

                    // Hvis avlest subsekvens allerede finnes, hoppes det over
                    if (nyHashMap.containsKey(subsekvens)){
                        continue;
                    }

                    // Oppretter et Subsekvens-objekt og legger det inn i HashMap-en
                    Subsekvens subsekvensObj = new Subsekvens(subsekvens, antall);
                    nyHashMap.put(subsekvens, subsekvensObj);
                }
            }
            sc.close();
        } catch (FileNotFoundException e){
            System.out.println("Filen ble ikke funnet!");
        }  

        return nyHashMap;
    } 

    // Oppgave 4:
    // Slaa sammen to HashMaper
    public static HashMap<String, Subsekvens> slaaSammen(HashMap<String, Subsekvens> hashMap1, HashMap<String, Subsekvens> hashMap2){
        HashMap<String, Subsekvens> flettetHashMap = new HashMap<>();

        // Legger inn subsekvenser fra forste HashMap inn i ny HashMap
        // for each-lokke
        for (String nokkel : hashMap1.keySet()){
            Subsekvens subsekvens1 = hashMap1.get(nokkel);
            flettetHashMap.put(nokkel, subsekvens1);    // legger inn subsekvensen
        }

        // Legger inn subsekvenser fra andre HashMap inn i samme HashMap
        // for each-lokke
        for (String nokkel : hashMap2.keySet()){
            Subsekvens subsekvens2 = hashMap2.get(nokkel);

            // ... men maa sjekke forst om nokkel allerede er lagt inn fra forste HashMap
            if (flettetHashMap.containsKey(nokkel)){
                Subsekvens subsekvensFinnes = flettetHashMap.get(nokkel);
                subsekvensFinnes.endreAntall(subsekvensFinnes.hentAntall() + subsekvens2.hentAntall());   // Oker antallet
            } else {    // Subsekvensen er ikke lagt til, saa vi legger den til
                flettetHashMap.put(nokkel, subsekvens2);
            }
        } 
        return flettetHashMap;
    }
}
