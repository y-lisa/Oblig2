import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oblig2Del2B {
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
        Monitor2 monitor2 = new Monitor2();
        ArrayList<Thread> listeAvlest = new ArrayList<>();  // Liste med alle traader som lages naar de avleses

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
                listeAvlest.add(traad);
                traad.start();
            }

            // Lukker scanneren
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Mappesti og fil ble ikke funnet!");
        }

        // Steg 2: Fletter Hashmap-er
        // Venter på at alle FletteTrad-trådene fullfører

        try {
            for (Thread nyTraad : listeAvlest) {
                nyTraad.join();
            }    
        } catch (InterruptedException ie) {
            System.out.println("Feil i avlesing av fil!");
        }
        
        // Ny liste med flettede traader
        ArrayList<Thread> listeFlettet = new ArrayList<>();

        // Antall traader settes lik aatte
        // Disse skal flettes sammen i en monitor2 til det kun er én HashMap
        for (int i = 0; i < 8; i++){
            FletteTrad fletteTrad = new FletteTrad(monitor2);
            Thread tradFlettet = new Thread(fletteTrad);
            listeFlettet.add(tradFlettet);
            tradFlettet.start();
        }

        try {
            for (Thread tradFlettet : listeFlettet){
                tradFlettet.join();
            }
        } catch (InterruptedException ie) {
            System.out.println("Feil i fletting!");
        } 

        // Subsekvens med flest forekomst
        String mestFrekvent = "";
        int forekomst = 0;
        //
        System.out.println("Mest frekvente subsekvens: " + mestFrekvent + " med " + forekomst + " forekomster.");
        
    }
}