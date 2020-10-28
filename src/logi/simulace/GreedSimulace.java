package logi.simulace;

import data.Cesta;
import data.DataSet;
import data.Objednavka;
import data.SuperMarket;
import logi.log.Log;
import logi.log.MSG_Level;
import logi.log.Message;

import java.util.Collections;
import java.util.List;

public class GreedSimulace extends Simulace {

    public GreedSimulace(DataSet dataSet) {
        super(dataSet);
    }

    @Override
    public Log zpracovaniObjednavek(List<Objednavka> objednavky) {
        Log log = new Log();
        Collections.sort(objednavky);
        for (Objednavka ak : objednavky) {
            int chteneMnostvi = ak.mnostvi;
            int cisloSupermarketu = ak.supermarket;
            int cisloChtenehoZbozi = ak.zbozi;
            SuperMarket superMarket = dataSet.superMarkety[cisloSupermarketu];
            int zasoby = superMarket.zasoby[cisloChtenehoZbozi];


            if (zasoby > 0) {
                int zboziZezasob = chteneMnostvi - zasoby;
                if (zboziZezasob >= 0) {
                    log.log(String.format("Supermarket %d využil svoje zásoby zboží %d v počtu %d zbývá %d ks zásob", cisloSupermarketu, cisloChtenehoZbozi, chteneMnostvi, zboziZezasob));
                    continue;
                } else
                //zásoby nestačily
                {
                    chteneMnostvi = Math.abs(zasoby);
                }
            }
            // pokryti poptavky z tovaten

            int index = 0;
            while (chteneMnostvi > 0 && index < superMarket.cesty.length) {
                Cesta cesta = superMarket.cesty[index++];
                int cisloTovarny = cesta.kam;
                int dostupneZbozi = dataSet.tovarny[cisloTovarny].sklad[cisloChtenehoZbozi];
                int cenaDodavky;

                if (dostupneZbozi > 0) {
                    int roz = dostupneZbozi - chteneMnostvi;
                    if (roz >= 0) {
                        dataSet.tovarny[cisloTovarny].sklad[cisloChtenehoZbozi] = roz;
                        cenaDodavky = cesta.cena * chteneMnostvi;
                        ak.addCena(cenaDodavky);
                        log.log(String.format("Supermarket %d dostal %d ks zbozi druhu %d za %d Kč",
                                cisloSupermarketu, chteneMnostvi, cisloChtenehoZbozi, cenaDodavky));
                        chteneMnostvi = 0;
                        break;
                    } else {
                        dataSet.tovarny[cisloTovarny].sklad[cisloChtenehoZbozi] = 0;
                        cenaDodavky = cesta.cena * dostupneZbozi;
                        ak.addCena(cenaDodavky);
                        log.log(String.format("Supermarket %d dostal %d ks zbozi druhu %d za %d Kč",
                                cisloSupermarketu, dostupneZbozi, cisloChtenehoZbozi, cenaDodavky));
                        chteneMnostvi = Math.abs(roz);
                    }
                }
            }

            if (chteneMnostvi > 0) {
                log.log(new Message(String.format("Supermarket %d nemohl být zásoben v počtu %d ks zbozi druhu %d", cisloSupermarketu, chteneMnostvi, cisloChtenehoZbozi), MSG_Level.alert));
                ukonciSimulaci();
                return log;
            }
        }

        return log;
    }

}
