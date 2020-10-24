package logi;

import java.util.ArrayList;
import java.util.List;

public class Graf {
    private int[][] maticeHodnoceni;

    public Graf(int pocetVrcholu) {
        maticeHodnoceni = new int[pocetVrcholu][pocetVrcholu];
        for (int i = 0; i < maticeHodnoceni.length; i++) {
            for (int j = 0; j < maticeHodnoceni[i].length; j++) {
                maticeHodnoceni[i][j] = -1;
            }
        }
    }

    public void pridejHranu(int odkud, int kam, int hodnota) {
        maticeHodnoceni[odkud][kam] = hodnota;
        maticeHodnoceni[kam][odkud] = hodnota;
    }

    public int[] sousedi(int vrchol) {
        List<Integer> sousedi = new ArrayList<>();
        for (int i = 0; i < maticeHodnoceni[vrchol].length; i++) {
            if (maticeHodnoceni[vrchol][i] >= 0) {
                sousedi.add(i);
            }
        }
        int[] ret = new int[sousedi.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = sousedi.get(i);
        }
        return ret;
    }

    public int getPocetVrcholu() {
        return maticeHodnoceni.length;
    }

}
