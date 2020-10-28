package data;

import java.util.LinkedList;
import java.util.List;

public class DataSet {
    public final int D, S, Z, T;
    public SuperMarket[] superMarkety;
    public Tovarna[] tovarny;
    public List<Objednavka>[] objednavky;

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

    public boolean addObjednavka(Objednavka novaObjednavka, int den) {
        return objednavky[den].add(novaObjednavka);
    }

    @Override
    public String toString() {
        int suma = 0;
        for (List<Objednavka> list : objednavky) {
            suma += list.size();
        }
        return "DataSet{" + String.format("D=%d,S=%d,Z=%d,T=%d,aktualní počet objednávek:%d", D, S, Z, T, suma) + "}";
    }
}
