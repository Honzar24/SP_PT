package data;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LinkedDataSet implements DataSet {
    public final int D, S, Z, T;
    private final List<Objednavka>[] objednavky;
    private SuperMarket[] superMarkety;
    private Tovarna[] tovarny;

    public LinkedDataSet(int pocetTovaren, int pocetSupermarketu, int pocetDruhuZbozi, int pocetDni) {
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

    @Override
    public int getPocetTovarny() {
        return D;
    }

    @Override
    public int getPocetSupermarketu() {
        return S;
    }

    @Override
    public int getPocetZbozi() {
        return Z;
    }

    @Override
    public int getPocetDni() {
        return T;
    }

    @Override
    public void addObjednavka(Objednavka novaObjednavka, int den) {
        objednavky[den].add(novaObjednavka);
    }

    @Override
    public void setZasobySupermarketu(int supermarket, int zbozi, int zasoby) {
        superMarkety[supermarket].zasoby[zbozi] = zasoby;
    }

    @Override
    public int getZasobySupermarketu(int supermarket, int zbozi) {
        return superMarkety[supermarket].zasoby[zbozi];
    }

    @Override
    public int getPocetCest(int supermarket) {
        return superMarkety[supermarket].cesty.length;
    }

    @Override
    public Cesta getCesta(int supermarket, int i) {
        return superMarkety[supermarket].cesty[i];
    }

    @Override
    public int getDostupneZbozi(int tovarna, int zbozi) {
        return tovarny[tovarna].sklad[zbozi];
    }

    @Override
    public int[] getSupermarketZasoby(int cisloSupermarketu) {
        return superMarkety[cisloSupermarketu].zasoby;
    }

    @Override
    public void setDostupneZboziTovarny(int tovarna, int zbozi, int mnozstvi) {
        tovarny[tovarna].sklad[zbozi] = mnozstvi;
    }

    @Override
    public void setTovarny(Tovarna[] tovarny) {
        this.tovarny = tovarny;
    }

    @Override
    public void setSuperMarkety(SuperMarket[] superMarkety) {
        this.superMarkety = superMarkety;
    }

    @Override
    public Tovarna getTovarna(int tovarna) {
        return tovarny[tovarna];
    }

    @Override
    public List<Objednavka> getObjednavky(int den) {
        return objednavky[den];
    }

    @Override
    public int getCelkovaCena(int den) {
        return getObjednavky(den).stream().mapToInt(Objednavka::getCena).sum();
    }

    @Override
    public int getCelkovaCena() {
        return Arrays.stream(objednavky).flatMap(Collection::stream).mapToInt(Objednavka::getCena).sum();
    }

    @Override
    public String toString() {
        int suma = Arrays.stream(objednavky).mapToInt(List::size).sum();
        return "DataSet{" + String.format("D=%d,S=%d,Z=%d,T=%d,počet objednávek:%d", D, S, Z, T, suma) + "}";
    }
}
