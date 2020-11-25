package data;

import java.util.List;

/**
 * Interface reprezentující dataset simulace
 */
public interface DataSet extends DataSetNaplneni, DataSetZakladniInfo {
    /**
     * Přidá objednávkuu do DataSetu
     * @param novaObjednavka objenávka k přidání
     * @param den den objednávky
     */
    void addObjednavka(Objednavka novaObjednavka, int den);

    /**
     * Vrátí továrnu dle zadaného id
     * @param tovarna id továrny
     * @return továrna se zadaným id
     */
    Tovarna getTovarna(int tovarna);

    /**
     * Vrátí list objednávek za určitý den
     * @param den číslo dne
     * @return list objednávek
     */
    List<Objednavka> getObjednavky(int den);

    /**
     * Vrátí cestu ze supermarketu na určitém indexu
     * @param supermarket id supermarketu
     * @param i index cesty
     * @return cesta vedoucí ze supermarketu
     */
    Cesta getCesta(int supermarket, int i);

    /**
     * Vrátí celkovou cenu objednávek pro zadaný den
     * @param den číslo dne
     * @return celková cena objednávek
     */
    int getCelkovaCena(int den);

    /**
     * Vrátí celkovou cenu objednávek pro všechny dny
     * @return celková cena objednávek
     */
    int getCelkovaCena();

    /**
     * Vrátí počet zásob supermarketu zadaného zboží
     * @param supermarket id supermarketu
     * @param zbozi id zboží
     * @return počet zásob supermarketu
     */
    int getZasobySupermarketu(int supermarket, int zbozi);

    /**
     * Vrátí počet cest supermarketu
     * @param supermarket id supermarketu
     * @return počet cest supermarketu
     */
    int getPocetCest(int supermarket);

    /**
     * Vrátí počet dostupných kusů zboží v továrně
     * @param tovarna id továrny
     * @param zbozi id zboží
     * @return počet dostupných kusů zboží v továrně
     */
    int getDostupneZbozi(int tovarna, int zbozi);

    /**
     * Vrátí pole se zásobami supermarketu
     * @param cisloSupermarketu id supermarketu
     * @return pole se zásobami supermarketu
     */
    int[] getSupermarketZasoby(int cisloSupermarketu);
}
