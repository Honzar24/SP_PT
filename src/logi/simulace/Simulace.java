package logi.simulace;

import logi.log.Log;
import logi.log.Logovatelne;

/**
 * Interface umožňující spouštět simulace
 */
public interface Simulace extends Logovatelne {
    /**
     * Spustí simulaci
     */
    void run();

    /**
     * Vrací informaci jestli je simulace ukončena
     * @return true/false jestli je simulace ukončena
     */
    boolean skonceno();

    /**
     * Ukončí simulaci
     */
    void ukonciSimulaci();

    /**
     * Posune simulaci o jeden den
     * @return log s informacemi o běhu
     */
    Log nextDay();

    /**
     * Vrátí číslo dne běhu simulace
     * @return číslo dne
     */
    int getDen();
}
