import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oblig2Del2B {
    public static void main(String[] args){
        // Sjekker om det er gitt 1 argument som parameter til programmet
        if (args.length < 1){
            System.out.println("Mangler mappenavn som parameter!");
            System.exit(1);     // 1 for ugyldig avslutning
        } else if (args.length > 1){
            System.out.println("For langt, trenger kun mappenavn til testdatafilen.");
            System.exit(1);
        }

        String mappenavn = args[0];
        Monitor2 monitor2 = new Monitor2();
        // ArrayList<Thread> listeAvlest = new ArrayList<>();  // Liste med alle traader som lages naar de avleses

        try{
            // Steg 1: Leser av fil og setter inn HashMap i monitor
            // Leser av hver linje i metadata.csv for aa hente ut filnavnene
            File fil = new File(mappenavn + "/" + "metadata.csv");
            Scanner sc = new Scanner(fil);

            // Leser av filnavn saa lenge det er en neste linje
            while (sc.hasNextLine()){
                String linjeFilnavn = sc.nextLine();
                String filnavn = mappenavn + "/" + linjeFilnavn;

                // Starter traad
                LeseTrad leseTraad = new LeseTrad(filnavn, monitor2);
                Thread traad = new Thread(leseTraad);
                traad.start();
            }

            // Lukker scanneren
            // sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Mappesti og fil ble ikke funnet!");
        }
        
        for (int i = 0; i < 8; i++){
            FletteTrad flettet = new FletteTrad(monitor2);
            flettet.run();
        }
        
        
        System.out.println("Utskrift:");
        HashMap<String, Subsekvens> resultat = monitor2.hentUtTo();
        /*
        // Utskrift alt
        for (Subsekvens s : resultat.values()){
            System.out.print(s);
        }
        */

        // Subsekvens med flest forekomst
        String mestFrekvent = "";
        int forekomst = 0;

        // for each-lokke
        for (String frekventSubsekvens : resultat.keySet()){
            Subsekvens s = resultat.get(frekventSubsekvens);
            // Sjekker for hver subsekvens om antallet er mer enn 0, som saa okes for hver runde
            if (s.hentAntall() > forekomst){
                mestFrekvent = frekventSubsekvens;
                forekomst = s.hentAntall();
            }
        }

        System.out.println("Mest frekvente subsekvens: " + mestFrekvent + " med " + forekomst + " forekomster.");
        System.out.println("(Ser at dette ikke stemmer)");
            
    }
}
