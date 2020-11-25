package data;

/**
 * Třída reprezentující objednávku zboží supermarketem o určitém množství
 */
public class Objednavka implements Comparable<Objednavka> {
    /**
     * Id supermarketu
     */
    public final int supermarket;
    /**
     * Id zboží
     */
    public final int zbozi;
    /**
     * Množstí objednaného zboží
     */
    public final int mnostvi;
    /**
     * Cena za zboží
     */
    private int cena;

    /**
     * Konstruktor třídy, nastavuje atributy třídy
     * @param supermarket id supermarketu
     * @param zbozi id zboží
     * @param mnostvi množství objednaného zboží
     */
    public Objednavka(int supermarket, int zbozi, int mnostvi) {
        this.supermarket = supermarket;
        this.zbozi = zbozi;
        this.mnostvi = mnostvi;
    }

    /**
     * Vrátí cenu kterou supermarket zaplatí za zboží
     * @return cena
     */
    public int getCena() {
        return cena;
    }

    /**
     * Připočítá zadanou cenu k aktuální
     * @param cena přidávaná cena
     */
    public void addCena(int cena) {
        this.cena += cena;
    }

    /**
     * Metoda sloužící k porovnání množstí objednaného zboží dvou objednávek
     * @param objednavka objednávka určená k porovnání
     * @return vrací <0 pokud je v této objednávce méně objednaného zboží , 0 pokud je množství stejné, >0 pokud je v zadané objednávce větší množství objednaného zboží
     */
    @Override
    public int compareTo(Objednavka objednavka) {
        return Integer.compare(objednavka.mnostvi, this.mnostvi);
    }

    /**
     * Převede objednávku do Stringu, který je možný vypsat do konzole
     * @return informace o objednávce jako String
     */
    @Override
    public String toString() {
        return String.format("Objednavka do supermarketu %d na zbozi %d v počtu %d ks", supermarket, zbozi, mnostvi);
    }
}
