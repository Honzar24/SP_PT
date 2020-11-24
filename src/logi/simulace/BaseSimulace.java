package logi.simulace;


import data.DataSet;
import data.Objednavka;
import logi.log.Log;
import logi.log.Logovatelne;
import logi.log.Message;
import logi.log.MsgLevel;

import java.util.List;

public abstract class BaseSimulace implements Simulace {
    protected final DataSet dataSet;
    protected final Log log;
    protected int den;
    protected boolean konec = false;

    public BaseSimulace(DataSet dataSet) {
        this.dataSet = dataSet;
        this.log = new Log();
    }

    @Override
    public void run() {
        while (!skonceno()) {
            nextDay();
        }
    }

    @Override
    public final Log nextDay() {
        if (!konec) {
            Log log = new Log("Den " + den);
            Log vyhazovani = new Log("zbytky den " + den);
            if (den > 0) {
                vyhazovani.log("Vyhazovaní přebytků z den " + (den - 1));
            }
            if (den < (dataSet.getPocetDni())) {
                for (int i = 0; i < dataSet.getPocetTovarny(); i++) {
                    vyhazovani.log(dataSet.getTovarna(i).nastavDen(den));
                }
            }
            log.log(vyhazovani);
            log.log(new Message("Začátek den " + den, MsgLevel.INFO));
            log.log(zpracovaniObjednavek(dataSet.getObjednavky(den)));
            log.log(new Message(String.format("Končí den %d celková cena za přepravu v tento den je %d Kč.", den, dataSet.getCelkovaCena(den)), MsgLevel.INFO));
            this.log.log(log);
            if (den >= (dataSet.getPocetDni() - 1)) {
                ukonciSimulaci();
            } else {
                den++;
            }
            if (skonceno()) {
                this.log.log(new Message("Konec simulace " + find("alert").getShortText(), MsgLevel.INFO));
                this.log.log(new Message("Celková cena za vyřízených objednávek je " + dataSet.getCelkovaCena() + " Kč.", MsgLevel.INFO));
            }
        }
        return log;
    }

    protected abstract Log zpracovaniObjednavek(List<Objednavka> objednavky);

    @Override
    public boolean skonceno() {
        return konec;
    }

    @Override
    public final void ukonciSimulaci() {
        konec = true;
    }

    @Override
    public int getDen() {
        return den;
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }

    @Override
    public Logovatelne getLog() {
        return log;
    }
}
