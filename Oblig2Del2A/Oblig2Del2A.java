// Oppgave 8:
// Hovedprogram soms starter mange traader.

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oblig2Del2A {
    public static void main(String[] args) {
        // Sjekker om det er gitt 1 argument som parameter til programmet
        if (args.length < 1){
            System.out.println("Mangler mappenavn som parameter!");
            System.exit(1);     // 1 for ugyldig avslutning
        } else if (args.length > 1){
            System.out.println("For langt, trenger kun mappenavn til testdatafilen.");
            System.exit(1);
        }

        String mappenavn = args[0];
        Monitor1 monitor1 = new Monitor1();

        try{
            // Steg 1: Leser av fil og setter inn HashMap i monitor
            // Leser av hver linje i metadata.csv for aa hente ut filnavnene
            File fil = new File(mappenavn + "/" + "metadata.csv");
            Scanner sc = new Scanner(fil);
            ArrayList<Thread> oversiktTraader = new ArrayList<>();  // Oversikt over alle traadene

            // Leser av filnavn saa lenge det er en neste linje
            while (sc.hasNextLine()){
                String linjeFilnavn = sc.nextLine();
                String filnavn = mappenavn + "/" + linjeFilnavn;

                // Starter traad
                LeseTrad leseTraad = new LeseTrad(filnavn, monitor1);
                Thread traad = new Thread(leseTraad);
                oversiktTraader.add(traad);
                traad.start();
            }

            // Steg 2: Fletter Hashmap-er; finner subsekvens med flest forekomst
            // Venter paa at forrige traad skal bli ferdig med sin run() med join()
            for (Thread traad : oversiktTraader){
                traad.join();
            }

            // Subsekvens med flest forekomst
            String mestFrekvent = "";
            int forekomst = 0;

            // Setter inn HashMap-ene
            while (monitor1.antallHashMaper() > 1) {
                HashMap<String, Subsekvens> subsekvens1 = monitor1.taUt();
                HashMap<String, Subsekvens> subsekvens2 = monitor1.taUt();
                HashMap<String, Subsekvens> flettet = monitor1.slaaSammen(subsekvens1, subsekvens2);
                monitor1.settInn(flettet);

                // for each-lokke
                for (String frekventSubsekvens : flettet.keySet()){
                    Subsekvens s = flettet.get(frekventSubsekvens);
                    // Sjekker for hver subsekvens om antallet er mer enn 0, som saa okes for hver runde
                    if (s.hentAntall() > forekomst){
                        mestFrekvent = frekventSubsekvens;
                        forekomst = s.hentAntall();
                    }
                }
            }

            // Skriver ut naar vi er utenfor while-lokka, altsaa naar det er 1 HashMap igjen
            System.out.println("Mest frekvente subsekvens: " + mestFrekvent + " med " + forekomst + " forekomster.");

            // Lukker scanneren
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Mappesti og fil ble ikke funnet!");
        } catch (InterruptedException ie) {
            System.out.println("Traaden ble avbrutt!");
        }
    }
}
