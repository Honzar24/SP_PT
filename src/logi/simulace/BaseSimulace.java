package logi.simulace;


import data.DataSet;
import data.Objednavka;
import logi.log.Log;

import java.util.List;

public abstract class BaseSimulace implements Simulace {
    protected DataSet dataSet;
    protected int den;
    protected Log log;
    protected boolean konec = false;

    public BaseSimulace(DataSet dataSet) {
        this.dataSet = dataSet;
        this.log = new Log();
    }

    public void run() {
        while (!skonceno()) {
            nextDay();
        }
    }

    public final Log nextDay() {
        if (!konec) {
            if (den > 0) {
                log.log("Končí den " + (den - 1));
                log.log(String.format("Celková cena za přepravu v tento den je %d Kč.", dataSet.getCelkovaCena(den - 1)));
            }

            for (int i = 0; i < dataSet.D; i++) {
                log.log(dataSet.getTovarna(i).nastavDen(den));
            }
            log.log("Začátek dne " + den);
            log.log(zpracovaniObjednavek(dataSet.getObjednavky(den)));

            if (dataSet.T == ++den || konec) {
                konec = true;
                log.log(String.format("Celková cena za přepravu v tento den je %d Kč.", dataSet.getCelkovaCena(den - 1)));
                log.log("Konec simulace");
                log.log("Celková cena za vyřízení objednávek je " + dataSet.getCelkovaCena());
            }
        }
        return log;
    }

    protected abstract Log zpracovaniObjednavek(List<Objednavka> objednavky);

    public boolean skonceno() {
        return konec;
    }

    protected final void ukonciSimulaci() {
        konec = true;
    }

    @Override
    public int getSize() {
        return log.getSize();
    }

    @Override
    public String getLog() {
        return log.getLog();
    }
}
