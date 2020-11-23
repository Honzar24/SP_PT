package data;

import logi.log.Log;
import logi.log.Message;
import logi.log.MsgLevel;

public class Tovarna {
    private static int cislo = 0;
    private final int poradi;
    public final int[] sklad;
    public final int[][] vyroba;

    public Tovarna(int pocetZbozi, int pocetDnu) {
        vyroba = new int[pocetDnu][pocetZbozi];
        sklad = new int[pocetZbozi];
        poradi = cislo++;
    }

    public Log nastavDen(int cisloDne) {
        Log log = new Log();
        for (int i = 0; i < vyroba[cisloDne].length; i++) {
            if (sklad[i] != 0) {
                log.log(new Message(String.format("Továrna %d vyhazuje zbozi %d v počtu %d ks", poradi, i, sklad[i]), MsgLevel.SKLAD));
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
