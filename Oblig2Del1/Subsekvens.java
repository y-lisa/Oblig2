// Del 1: Oppgave 1
public class Subsekvens{
    private int antall;
    public final String subsekvens;

    public Subsekvens(String subsekvens, int antall){
        this.subsekvens = subsekvens;
        this.antall = antall;
    }

    // Hente antall forekomster av en sekvens
    public int hentAntall(){
        return antall;
    }

    // Endre antall forekomster av en sekvens
    public void endreAntall(int nyAntall){
        this.antall = nyAntall;
    }

    // toString-metode
    public String toString(){
        return "(" + subsekvens + "," + antall + ")";
    }
}