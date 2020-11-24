package data;


import java.util.Arrays;

public class SuperMarket {
    private static int pocet = 0;
    public final int poradi;
    public final int[] zasoby;
    public final Cesta[] cesty;

    public SuperMarket(int pocetZbozi, int[] cenyCest) {
        poradi = pocet++;
        this.zasoby = new int[pocetZbozi];
        this.cesty = new Cesta[cenyCest.length];
        for (int i = 0; i < cenyCest.length; i++) {
            cesty[i] = new Cesta(i, cenyCest[i]);
        }
        Arrays.sort(cesty);
    }

    @Override
    public String toString() {
        return "SuperMarket " + poradi;
    }
}
