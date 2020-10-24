package logi.simulace;


import data.DataSet;
import data.Objednavka;
import logi.log.Log;
import logi.log.Logovatelne;

import java.util.List;

public abstract class Simulace implements Logovatelne {
    protected DataSet dataSet;
    protected int den;
    protected Log log;
    protected boolean konec=false;

    public Simulace(DataSet dataSet) {
        this.dataSet = dataSet;
        this.log=new Log();
    }

    public final Log nextDay() {
        if (!konec) {
            log.log("Generace dne " + den);
            for (int i = 0; i < dataSet.tovarny.length; i++) {
                log.log(dataSet.tovarny[i].nastavDen(den));
            }
            log.log(zpracovaniObjednavek(dataSet.objednavky[den]));
            if (dataSet.T==den)
            {
                konec=true;
                log.log("Konec simulace");
            }
        }
        return log;
    }

    protected abstract Log zpracovaniObjednavek(List<Objednavka> objednavky);

    public boolean skonceno()
    {
        return konec;
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
