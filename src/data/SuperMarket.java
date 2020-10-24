package data;


public class SuperMarket {
    private static int pocet = 0;
    public final int poradi;
    public int[] zasoby;

    public SuperMarket(int pocetZbozi) {
        poradi = pocet++;
        this.zasoby = new int[pocetZbozi];
    }

    @Override
    public String toString() {
        return "SuperMarket " + poradi;
    }
}
