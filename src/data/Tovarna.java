package data;

import logi.log.Log;
import logi.log.Message;
import logi.log.MsgLevel;

/**
 * Třída reprezentující továrnu se skladovými zásobami a informacemi o výrobě
 */
public class Tovarna {
    /**
     * Celkový počet vytvořených instancí třídy
     */
    private static int cislo = 0;
    /**
     * Pořadí vytvoření továrny, id továrny
     */
    private final int poradi;
    /**
     * Pole se skladovými zásobami jednotlivého zboží
     */
    public final int[] sklad;
    /**
     * Dvourozměrné pole s informacemi o výrobě zboží v jednotlivých dnech
     */
    public final int[][] vyroba;

    /**
     * Konstruktor třídy, inicializuje pole o správné velikosti
     * @param pocetZbozi celkový počet druhů zboží
     * @param pocetDnu počed dnů trvání simulace
     */
    public Tovarna(int pocetZbozi, int pocetDnu) {
        vyroba = new int[pocetDnu][pocetZbozi];
        sklad = new int[pocetZbozi];
        poradi = cislo++;
    }

    /**
     * Metoda nastaví den simulace na zadaný
     * @param cisloDne číslo dne na které se má nastavit
     * @return vrací log s uloženými informacemi
     */
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

    /**
     * Převede továrnu do Stringu, který je možný vypsat do konzole
     * @return informace o továrně jako String
     */
    @Override
    public String toString() {
        return "Tovarna " + poradi;
    }
}
