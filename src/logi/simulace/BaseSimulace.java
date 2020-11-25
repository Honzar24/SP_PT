package logi.simulace;


import data.DataSet;
import data.Objednavka;
import logi.log.Log;
import logi.log.Logujici;
import logi.log.Message;
import logi.log.MsgLevel;

import java.util.Arrays;
import java.util.List;

/**
 * Třída reprezentující simulaci
 */
public abstract class BaseSimulace implements Simulace {
    /**
     * Data potřebná pro běh simulace
     */
    protected final DataSet dataSet;
    /**
     * Log simulace s událostmi
     */
    protected final Log log;
    /**
     * Aktuální den simulace
     */
    protected int den;
    /**
     * Informace o konci simulace
     */
    protected boolean konec = false;

    /**
     * Konstruktor vytvářející novou simulaci
     * @param dataSet data pro běh simulace
     */
    public BaseSimulace(DataSet dataSet) {
        this.dataSet = dataSet;
        this.log = new Log();
        log.log("Začátek Simulace");
    }

    /**
     * Metoda spouštící simulaci
     */
    @Override
    public void run() {
        while (!skonceno()) {
            nextDay();
        }
    }

    /**
     * Metoda posouvá simulaci o jeden den dopředu, veškeré informace o událostech ukládá do logu
     * @return Log s informacemi o událostech během dne
     */
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

    /**
     * Zpracuje objednávky v aktuálním dni simulace
     * @param objednavky list objednávek
     * @return Log s informacemi o zpracování objednávek
     */
    protected abstract Log zpracovaniObjednavek(List<Objednavka> objednavky);

    /**
     * vrátí informaci o konci simulace (skončeno - true, běžící - false)
     * @return informacr o konci simulace
     */
    @Override
    public boolean skonceno() {
        return konec;
    }

    /**
     * Nastaví konec na true
     */
    @Override
    public final void ukonciSimulaci() {
        konec = true;
    }

    /**
     * Vrátí číslo dne simulace
     * @return číslo dne
     */
    @Override
    public int getDen() {
        return den;
    }

    /**
     * Vrátí DataSet simulace
     * @return DataSet simulace
     */
    @Override
    public DataSet getDataSet() {
        return dataSet;
    }

    /**
     * Vrátí Log s informacemi o simulaci
     * @return Log s informacemi
     */
    @Override
    public Logujici getLog() {
        return log;
    }
}
