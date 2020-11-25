package logi.simulace;


import data.DataSet;
import data.Objednavka;
import logi.log.Log;
import logi.log.Logujici;
import logi.log.Message;
import logi.log.MsgLevel;

import java.util.Arrays;
import java.util.List;

public abstract class BaseSimulace implements Simulace {
    protected final DataSet dataSet;
    protected final Log log;
    protected int den;
    protected boolean konec = false;

    public BaseSimulace(DataSet dataSet) {
        this.dataSet = dataSet;
        this.log = new Log();
        log.log("Začátek Simulace");
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
            Log dnesniLog = new Log("Den " + den);
            Log vyhozenoLog = new Log("zbytky den " + den);
            Log zasobyLog = new Log("zásoby den " + den);

            zasobyLog.log(new Message("Počáteční zásoby supermaketů den " + den, MsgLevel.SKLAD));
            for (int i = 0; i < dataSet.getPocetSupermarketu(); i++) {
                int[] supermarketZasoby = dataSet.getSupermarketZasoby(i);
                //není příliš hezký výpis, ale je dostačující "lepší" řešení pro řákek je naznaženo v komentáři níž
                zasobyLog.log(new Message(String.format("Zásoby supermarketu %d %s", i, Arrays.toString(supermarketZasoby)), MsgLevel.ZASOBY));
                //zasobyLog.log(new Message(String.format("Zásoby supermarketu %d [%s]",i,Arrays.stream(supermarketZasoby).mapToObj(c->String.format("%3d",c)).collect(Collectors.joining(","))),MsgLevel.ZASOBY));
            }

            if (den > 0) {
                vyhozenoLog.log(new Message("Vyhazovaní přebytků z den " + (den - 1), MsgLevel.SKLAD));
            }

            if (den < (dataSet.getPocetDni())) {
                for (int i = 0; i < dataSet.getPocetTovarny(); i++) {
                    vyhozenoLog.log(dataSet.getTovarna(i).nastavDen(den));
                }
            }
            dnesniLog.log(vyhozenoLog);
            dnesniLog.log(zasobyLog);
            dnesniLog.log(new Message("Začátek den " + den, MsgLevel.INFO));
            dnesniLog.log(zpracovaniObjednavek(dataSet.getObjednavky(den)));
            dnesniLog.log(new Message(String.format("Končí den %d celková cena za přepravu v tento den je %d Kč.", den, dataSet.getCelkovaCena(den)), MsgLevel.INFO));

            this.log.log(dnesniLog);
            if (den >= (dataSet.getPocetDni() - 1) && !konec) {
                ukonciSimulaci();
            } else {
                den++;
            }
            if (skonceno()) {
                this.log.log(new Message("Simulace končí " + find(MsgLevel.KONEC.toString().toLowerCase()).getShortText().toLowerCase(), MsgLevel.INFO));
                this.log.log(new Message("Celková cena za vyřízení objednávek je " + dataSet.getCelkovaCena() + " Kč.", MsgLevel.INFO));
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
    public Logujici getLog() {
        return log;
    }
}
