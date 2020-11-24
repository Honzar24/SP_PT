package logi.simulace;

import logi.log.Log;
import logi.log.Logovatelne;

public interface Simulace extends Logovatelne {
    void run();

    boolean skonceno();

    void ukonciSimulaci();

    Log nextDay();

    int getDen();
}
