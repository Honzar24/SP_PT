package logi.simulace;


import data.DataSet;
import data.Objednavka;
import logi.log.Log;
import logi.log.Logovatelne;
import logi.log.Message;
import logi.log.MsgLevel;

import java.io.PrintWriter;
import java.util.List;

public abstract class BaseSimulace implements Simulace {
    protected final DataSet dataSet;
    protected int den;
    protected final Log log;
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
            Log vyhazovani = new Log("vyhazovani den " + den);
            if (den > 0) {
                vyhazovani.log("Vyhazovaní přebytků z dne " + (den - 1));
            }
            for (int i = 0; i < dataSet.D; i++) {
                vyhazovani.log(dataSet.getTovarna(i).nastavDen(den));
            }
            log.log(vyhazovani);
            log.log(new Message("Začátek dne " + den, MsgLevel.INFO));
            log.log(zpracovaniObjednavek(dataSet.getObjednavky(den)));
            log.log(new Message(String.format("Končí den %d celková cena za přepravu v tento den je %d Kč.", den, dataSet.getCelkovaCena(den)), MsgLevel.INFO));
            this.log.log(log);
            if (den < dataSet.T - 1) {
                den++;
            } else {
                ukonciSimulaci();
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
        log.log(new Message("Konec simulace", MsgLevel.INFO));
        log.log(new Message("Celková cena za vyřízení objednávek je " + dataSet.getCelkovaCena() + " Kč.", MsgLevel.INFO));
    }

    @Override
    public int getSize() {
        return log.getSize();
    }

    @Override
    public String getLog() {
        return log.getLog();
    }

    @Override
    public int getDen() {
        return den;
    }

    @Override
    public Logovatelne find(String patern) {
        return log.find(patern);
    }

    @Override
    public void print(PrintWriter vystup) {
        log.print(vystup);
    }
}
