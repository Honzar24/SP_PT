package logi;


import data.DataSet;

public abstract class Simulace {
    private DataSet dataSet;
    private Log log;

    public Simulace(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public abstract String nextDay();

}
