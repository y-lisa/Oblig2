// Oppgave 5: Testprogram

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oblig2Del1{
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
        SubsekvensRegister beholder = new SubsekvensRegister();

        try{
            // Leser av hver linje i metadata.csv for aa hente ut filnavnene
            File fil = new File(mappenavn + "/" + "metadata.csv");
            Scanner sc = new Scanner(fil);

            // Leser av filnavn saa lenge det er en neste linje
            while (sc.hasNextLine()){
                String linjeFilnavn = sc.nextLine();

                // Steg 1: Henter ut subsekvenser fra filene
                // bruker lesFil()
                HashMap<String, Subsekvens> nyHashMap = beholder.lesFil(mappenavn + "/" + linjeFilnavn);
                beholder.settInn(nyHashMap);

                // Sjekker om metoden antallHashMaper fungerer ved aa sjekke om det legges inn riktig antall HashMaper
                System.out.println("Antall HashMap-er: " + beholder.antallHashMaper());
            }

            // Steg 2: Slår sammen subsekvenser (fletter alle HashMap-ene i beholderen)
            // Brukes til aa finne forekomst senere
            String mestFrekvent = "";
            int forekomst = 0;

            // Setter inn HashMap-ene
            while (beholder.antallHashMaper() > 1) {
                HashMap<String, Subsekvens> subsekvens1 = beholder.taUt();
                HashMap<String, Subsekvens> subsekvens2 = beholder.taUt();
                HashMap<String, Subsekvens> flettet = beholder.slaaSammen(subsekvens1, subsekvens2);
                beholder.settInn(flettet);

                // Finner subsekvensen med flest forekomster!
                // for each-lokke
                for (String frekventSubsekvens : flettet.keySet()){
                    Subsekvens s = flettet.get(frekventSubsekvens);
                    // Sjekker for hvher subsekvens om antallet er mer enn 0, som saa okes for hver runde
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
        }
    }
}
