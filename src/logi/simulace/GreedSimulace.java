package logi.simulace;

import data.DataSet;
import data.Objednavka;
import logi.log.Log;

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
        for (Objednavka ak:objednavky) {
            // TODO: 24.10.20  greedy přístup simulace ceny cest a velikosti objednávek mají přednost
        }
        return log;
    }

}
