package logi.simulace;

import data.Cesta;
import data.DataSet;
import data.Objednavka;
import logi.log.Log;
import logi.log.Message;
import logi.log.MsgLevel;

import java.util.Collections;
import java.util.List;

public class GreedSimulace extends BaseSimulace {

    public GreedSimulace(DataSet dataSet) {
        super(dataSet);
    }

    @Override
    public Log zpracovaniObjednavek(List<Objednavka> objednavky) {
        Log log = new Log("Greedy zpracovani objednavek pro den " + den);
        Collections.sort(objednavky);
        for (Objednavka objednavka : objednavky) {
            int chteneMnostvi = objednavka.mnostvi;
            int cisloSupermarketu = objednavka.supermarket;
            int cisloZbozi = objednavka.zbozi;
            int zasoby = dataSet.getZasobySupermarketu(cisloSupermarketu, cisloZbozi);
            //pokrytí poptávky ze zásob
            if (zasoby > 0) {
                int zboziZezasob = zasoby - chteneMnostvi;
                if (zboziZezasob >= 0) {
                    dataSet.setZasobySupermarketu(cisloSupermarketu, cisloZbozi, zboziZezasob);
                    log.log(new Message(String.format("Supermarket %d využil svoje zásoby zboží %d v počtu %d zbývá %d ks zásob",
                            cisloSupermarketu, cisloZbozi, chteneMnostvi, zboziZezasob), MsgLevel.SKLAD));
                    continue;
                } else
                //zásoby nestačily
                {
                    dataSet.setZasobySupermarketu(cisloSupermarketu, cisloZbozi, 0);
                    log.log(new Message(String.format("Supermarket %d využil svoje zásoby zboží %d v počtu %d zbývá %d ks zásob",
                            cisloSupermarketu, cisloZbozi, zasoby,
                            dataSet.getZasobySupermarketu(cisloSupermarketu, cisloZbozi)), MsgLevel.SKLAD));
                    chteneMnostvi = Math.abs(zboziZezasob);
                }
            }
            //pokryti poptavky z tovaten
            int index = 0;
            do {
                Cesta cesta = dataSet.getCesta(cisloSupermarketu, index);
                int cisloTovarny = cesta.kam;
                int dostupneZbozi = dataSet.getDostupneZbozi(cisloTovarny, cisloZbozi);
                int cenaDodavky;

                if (dostupneZbozi > 0) {
                    int roz = dostupneZbozi - chteneMnostvi;
                    if (roz >= 0) {
                        dataSet.setDostupneZboziTovarny(cisloTovarny, cisloZbozi, roz);
                        cenaDodavky = cesta.cena * chteneMnostvi;
                        objednavka.addCena(cenaDodavky);
                        log.log(new Message(String.format("Supermarket %d dostal %d ks zboží %d za %d Kč z továrna %d",
                                cisloSupermarketu, chteneMnostvi, cisloZbozi, cenaDodavky, cisloTovarny), MsgLevel.ZASOBOVANI));
                        chteneMnostvi = 0;
                        break;
                    } else {
                        dataSet.setDostupneZboziTovarny(cisloTovarny, cisloZbozi, 0);
                        cenaDodavky = cesta.cena * dostupneZbozi;
                        objednavka.addCena(cenaDodavky);
                        log.log(new Message(String.format("Supermarket %d dostal %d ks zboží %d za %d Kč z továrna %d",
                                cisloSupermarketu, dostupneZbozi, cisloZbozi, cenaDodavky, cisloTovarny), MsgLevel.ZASOBOVANI));
                        chteneMnostvi = Math.abs(roz);
                    }
                }
            } while (chteneMnostvi > 0 && ++index < dataSet.getPocetCest(cisloSupermarketu));
            if (chteneMnostvi > 0) {
                log.log(new Message(String.format("Supermarket %d nemohl být zásoben v počtu %d ks zboží %d", cisloSupermarketu, chteneMnostvi, cisloZbozi), MsgLevel.ALERT));
                ukonciSimulaci();
                return log;
            }
        }

        return log;
    }

}
