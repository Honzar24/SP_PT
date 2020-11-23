package data;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DataSet {
    public final int D, S, Z, T;
    private final List<Objednavka>[] objednavky;
    private SuperMarket[] superMarkety;
    private Tovarna[] tovarny;

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

    public DataSet(int... head) {
        this(head[0], head[1], head[2], head[3]);
    }

    public void addObjednavka(Objednavka novaObjednavka, int den) {
        objednavky[den].add(novaObjednavka);
    }

    public void setZasobySupermarketu(int supermarket, int zbozi, int zasoby) {
        superMarkety[supermarket].zasoby[zbozi] = zasoby;
    }

    public int getZasobySupermarketu(int supermarket, int zbozi) {
        return superMarkety[supermarket].zasoby[zbozi];
    }

    public int getPocetCest(int supermarket) {
        return superMarkety[supermarket].cesty.length;
    }

    public Cesta getCesta(int supermarket, int i) {
        return superMarkety[supermarket].cesty[i];
    }

    public int getDostupneZbozi(int tovarna, int zbozi) {
        return tovarny[tovarna].sklad[zbozi];
    }

    public void setDostupneZboziTovarny(int tovarna, int zbozi, int mnozstvi) {
        tovarny[tovarna].sklad[zbozi] = mnozstvi;
    }

    public void setTovarny(Tovarna[] tovarny) {
        this.tovarny = tovarny;
    }

    public void setSuperMarkety(SuperMarket[] superMarkety) {
        this.superMarkety = superMarkety;
    }

    public Tovarna getTovarna(int tovarna) {
        return tovarny[tovarna];
    }

    public List<Objednavka> getObjednavky(int den) {
        return objednavky[den];
    }

    public int getCelkovaCena(int den) {
        return getObjednavky(den).stream().mapToInt(Objednavka::getCena).sum();
    }

    public int getCelkovaCena() {
        return Arrays.stream(objednavky).flatMap(Collection::stream).mapToInt(Objednavka::getCena).sum();
    }

    @Override
    public String toString() {
        int suma = Arrays.stream(objednavky).mapToInt(List::size).sum();
        return "DataSet{" + String.format("D=%d,S=%d,Z=%d,T=%d,počet objednávek:%d", D, S, Z, T, suma) + "}";
    }
}
