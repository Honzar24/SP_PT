package logi;

import data.DataSet;
import data.Objednavka;

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

        }
        return log;
    }

}
