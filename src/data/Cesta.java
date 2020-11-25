package data;

/**
 * Třída cesta reprezentující cestu ze supermarketu do továrny s určitou cenou
 */
public class Cesta implements Comparable<Cesta> {
    /**
     * Číslo továrny, do které cesta vede
     */
    public final int kam;
    /**
     * Cena cesty do továrny
     */
    public final int cena;

    /**
     * Konstruktor Cesta
     * @param kam číslo továrny
     * @param cena cena cesty
     */
    public Cesta(int kam, int cena) {
        this.kam = kam;
        this.cena = cena;
    }

    /**
     * Metoda sloužící k porovnání dvou cest
     * @param cesta cesta určená k porovnání
     * @return vrací <0 pokud je tato cesta kratší, 0 pokud jsou stejné, >0 pokud je zadaná cesta kratší
     */
    @Override
    public int compareTo(Cesta cesta) {
        return Integer.compare(this.cena, cesta.cena);
    }

    /**
     * Převede cestu do Stringu, který je možný vypsat do konzole
     * @return informace o cestě jako String
     */
    @Override
    public String toString() {
        return String.format("Cesta do továrny %d za cenu %d.", kam, cena);
    }
}
