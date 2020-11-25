package data;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Třída reprezentující data používaná simulací
 */
public class DataSet {
    /**
     * Počet továren, supermarketů, zboží a dní simulace
     */
    public final int D, S, Z, T;
    /**
     * List instancí třídy Objednavka reprezentující objednávky supermarketů
     */
    private final List<Objednavka>[] objednavky;
    /**
     * Pole instancí třídy Supermarket reprezentující jednotlivé supermarkety
     */
    private SuperMarket[] superMarkety;
    /**
     * Pole instancí třídy Tovarna reprezentující jednotlivé továrny
     */
    private Tovarna[] tovarny;

    /**
     * Konstruktor třídy, který se stará o uložení počtu supermarketů, továren, druhů zboží a počtu dní, inicializaci polí o správné velikosti a vytvoření LinkedListu objednávek
     * @param pocetTovaren celkový počet továren
     * @param pocetSupermarketu celkový počet supermarketů
     * @param pocetDruhuZbozi celkový počet druhů zboží
     * @param pocetDni celkový počet dní
     */
    public DataSet(int pocetTovaren, int pocetSupermarketu, int pocetDruhuZbozi, int pocetDni) {
        D = pocetTovaren;
        S = pocetSupermarketu;
        Z = pocetDruhuZbozi;
        T = pocetDni;
        superMarkety = new SuperMarket[S];
        tovarny = new Tovarna[D];
        objednavky = new List[T];
        for (int i = 0; i < objednavky.length; i++) {
            objednavky[i] = new LinkedList<>();
        }
    }

    /**
     * TODO:
     * @param head
     */
    public DataSet(int... head) {
        this(head[0], head[1], head[2], head[3]);
    }

    /**
     * Přidá novou objednávku do LinkedListu objednávek
     * @param novaObjednavka objednávka k přidání
     * @param den den který je objednávka vytvořena
     */
    public void addObjednavka(Objednavka novaObjednavka, int den) {
        objednavky[den].add(novaObjednavka);
    }

    /**
     * Nastaví zásoby supermarketu na zadanou hodnotu
     * @param supermarket id sopermarketu kterému se mají nastavit zásoby
     * @param zbozi id zboží kterého se nastavují zásoby
     * @param zasoby počet kusů zboží
     */
    public void setZasobySupermarketu(int supermarket, int zbozi, int zasoby) {
        superMarkety[supermarket].zasoby[zbozi] = zasoby;
    }

    /**
     * Vrátí zásoby zadaného zboží zadaného supermarketu
     * @param supermarket id supermarketu který nás zajímá
     * @param zbozi id zboží které nás zajímá
     * @return počet kusů zboží v supermarketu
     */
    public int getZasobySupermarketu(int supermarket, int zbozi) {
        return superMarkety[supermarket].zasoby[zbozi];
    }

    /**
     * Vrací počet cest vedoucích ze zadaného supermarketu
     * @param supermarket id supermarketu který nás zajímá
     * @return počet cest vedoucích ze supermarketu
     */
    public int getPocetCest(int supermarket) {
        return superMarkety[supermarket].cesty.length;
    }

    /**
     * TODO:
     * @return
     */
    public int getCelkovaCena() {
        return Arrays.stream(objednavky).flatMap(Collection::stream).mapToInt(Objednavka::getCena).sum();
    }

    /**
     * Vrací cestu vedoucí ze adaného supermarketu do zadané továrny TODO:??
     * @param supermarket id supermarketu
     * @param i id továrny??
     * @return cesta ze supermarketu do továrny
     */
    public Cesta getCesta(int supermarket, int i) {
        return superMarkety[supermarket].cesty[i];
    }

    /**
     * Zjištění počtu zadaného zboží v zadané továrně
     * @param tovarna id továrny které nás zajímá
     * @param zbozi id zboží
     * @return počet dostupného zboží
     */
    public int getDostupneZbozi(int tovarna, int zbozi) {
        return tovarny[tovarna].sklad[zbozi];
    }

    /**
     * Nastavení dostupného zadaného zboží v zadané továrně
     * @param tovarna id továrny
     * @param zbozi id zboží
     * @param mnozstvi počet kusů
     */
    public void setDostupneZboziTovarny(int tovarna, int zbozi, int mnozstvi) {
        tovarny[tovarna].sklad[zbozi] = mnozstvi;
    }

    /**
     * Nastaví pole továren na zadané
     * @param tovarny pole instancí všech továren
     */
    public void setTovarny(Tovarna[] tovarny) {
        this.tovarny = tovarny;
    }

    /**
     * Nastaví pole supermarketů na zadané
     * @param superMarkety pole všech supermarketů
     */
    public void setSuperMarkety(SuperMarket[] superMarkety) {
        this.superMarkety = superMarkety;
    }

    /**
     * Vrátí instanci továrny se zadaným id
     * @param tovarna id továrny
     * @return
     */
    public Tovarna getTovarna(int tovarna) {
        return tovarny[tovarna];
    }

    /**
     * Vrátí list všech objednávek pro zadaný den
     * @param den číslo dne
     * @return list objednávek
     */
    public List<Objednavka> getObjednavky(int den) {
        return objednavky[den];
    }

    /**
     * TODO:
     * @param den
     * @return
     */
    public int getCelkovaCena(int den) {
        return getObjednavky(den).stream().mapToInt(Objednavka::getCena).sum();
    }

    /**
     * Převede DataSet do Stringu, který je možný vypsat do konzole
     * @return informace o DataSetu jako String
     */
    @Override
    public String toString() {
        int suma = 0;
        for (List<Objednavka> list : objednavky) {
            suma += list.size();
        }
        return "DataSet{" + String.format("D=%d,S=%d,Z=%d,T=%d,počet objednávek:%d", D, S, Z, T, suma) + "}";
    }
}
