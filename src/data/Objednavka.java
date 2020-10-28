package data;

public class Objednavka implements Comparable<Objednavka> {
    public final int supermarket;
    public final int zbozi;
    public final int mnostvi;
    private int cena;

    public Objednavka(int supermarket, int zbozi, int mnostvi) {
        this.supermarket = supermarket;
        this.zbozi = zbozi;
        this.mnostvi = mnostvi;
    }

    public int getCena() {
        return cena;
    }

    public void addCena(int cena) {
        this.cena += cena;
    }

    @Override
    public int compareTo(Objednavka objednavka) {
        return Integer.compare(this.mnostvi, objednavka.mnostvi);
    }

    @Override
    public String toString() {
        return String.format("Objednavka do supermarketu %d na zbozi %d v počtu %d ks", supermarket, zbozi, mnostvi);
    }
}
