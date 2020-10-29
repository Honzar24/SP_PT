package data;

import logi.log.Log;
import logi.log.MSG_Level;
import logi.log.Message;
import logi.simulace.PosunDen;

public class Tovarna implements PosunDen {
    private static int cislo = 0;
    private final int poradi;
    public int[] sklad;
    public int[][] vyroba;

    public Tovarna(int pocetZbozi, int pocetDnu) {
        vyroba = new int[pocetDnu][pocetZbozi];
        sklad = new int[pocetZbozi];
        poradi = cislo++;
    }

    @Override
    public Log nastavDen(int cisloDne) {
        Log log = new Log();
        for (int i = 0; i < vyroba[cisloDne].length; i++) {
            if (sklad[i] != 0) {
                log.log(new Message(String.format("Továrna %d vyhazuje zbozi %d v počtu %d ks", poradi, i, sklad[i]), MSG_Level.Sklad));
            }
            sklad[i] = vyroba[cisloDne][i];
        }
        return log;
    }

    @Override
    public String toString() {
        return "Tovarna " + poradi;
    }
}
