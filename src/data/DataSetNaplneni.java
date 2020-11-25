package data;

/**
 * Interface umožňující naplnění DataSetu daty
 */
public interface DataSetNaplneni {

    /**
     * Nastavení dostupného zadaného zboží v zadané továrně
     * @param tovarna id továrny
     * @param zbozi id zboží
     * @param mnozstvi počet kusů
     */
    void setDostupneZboziTovarny(int tovarna, int zbozi, int mnozstvi);

    /**
     * Nastaví pole továren na zadané
     * @param tovarny pole instancí všech továren
     */
    void setTovarny(Tovarna[] tovarny);

    /**
     * Nastaví pole supermarketů na zadané
     * @param superMarkety pole všech supermarketů
     */
    void setSuperMarkety(SuperMarket[] superMarkety);

    /**
     * Nastaví zásoby supermarketu na zadanou hodnotu
     * @param supermarket id sopermarketu kterému se mají nastavit zásoby
     * @param zbozi id zboží kterého se nastavují zásoby
     * @param zasoby počet kusů zboží
     */
    void setZasobySupermarketu(int supermarket, int zbozi, int zasoby);
}
