package data;

public class Objednavka implements Comparable<Objednavka> {
    public final int kam;
    public final int zbozi;
    public final int mnostvi;

    public Objednavka(int kam, int zbozi, int mnostvi) {
        this.kam = kam;
        this.zbozi = zbozi;
        this.mnostvi = mnostvi;
    }

    @Override
    public int compareTo(Objednavka objednavka) {
        return Integer.compare(this.mnostvi,objednavka.mnostvi);
    }
}
