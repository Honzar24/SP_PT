package data;

public class Cesta implements Comparable<Cesta> {
    private final int kam;
    private final int cena;

    public Cesta(int kam, int cena) {
        this.kam = kam;
        this.cena = cena;
    }

    @Override
    public int compareTo(Cesta cesta) {
        return Integer.compare(this.cena, cesta.cena);
    }

    @Override
    public String toString() {
        return String.format("Cesta do továrny %d za cenu %d.", kam, cena);
    }
}
