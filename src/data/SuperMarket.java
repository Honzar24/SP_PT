package data;


import java.util.Arrays;

/**
 * Třída reprezentující supermarket se zásobami a cestami
 */
public class SuperMarket {
    /**
     * Celkový počet vytvořených instancí třídy
     */
    private static int pocet = 0;
    /**
     * Pořadí vytvoření supermarketu, id supermarketu
     */
    public final int poradi;
    /**
     * Pole se zásobami jednotlivého zboží
     */
    public final int[] zasoby;
    /**
     * Pole s cestami vedoucími do jednotlivých továren
     */
    public final Cesta[] cesty;

    /**
     * Konstruktor třídy, inicializuje pole zásob a cest, vytvoří jednotlivé cesty a seřadí je
     * @param pocetZbozi
     * @param cenyCest
     */
    public SuperMarket(int pocetZbozi, int[] cenyCest) {
        poradi = pocet++;
        this.zasoby = new int[pocetZbozi];
        this.cesty = new Cesta[cenyCest.length];
        for (int i = 0; i < cenyCest.length; i++) {
            cesty[i] = new Cesta(i, cenyCest[i]);
        }
        Arrays.sort(cesty);
    }

    /**
     * Převede supermarket do Stringu, který je možný vypsat do konzole
     * @return informace o supermarketu jako String
     */
    @Override
    public String toString() {
        return "SuperMarket " + poradi;
    }
}
