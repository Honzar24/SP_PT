package logi.simulace;

import data.*;
import logi.log.Log;
import logi.log.Logovatelne;
import logi.log.Logujici;

import java.util.List;

public interface Simulace extends Logujici, DataSet {
    void run();

    boolean skonceno();

    void ukonciSimulaci();

    Log nextDay();

    int getDen();

    DataSet getDataSet();

    Logujici getLog();

    @Override
    default Logujici log(Logovatelne novaZprava) {
        return getLog().log(novaZprava);
    }

    @Override
    default void addObjednavka(Objednavka novaObjednavka, int den) {
        getDataSet().addObjednavka(novaObjednavka, den);
    }

    @Override
    default void setZasobySupermarketu(int supermarket, int zbozi, int zasoby) {
        getDataSet().setZasobySupermarketu(supermarket, zbozi, zasoby);
    }

    @Override
    default int getZasobySupermarketu(int supermarket, int zbozi) {
        return getDataSet().getZasobySupermarketu(supermarket, zbozi);
    }

    @Override
    default int getPocetCest(int supermarket) {
        return getDataSet().getPocetCest(supermarket);
    }

    @Override
    default Cesta getCesta(int supermarket, int i) {
        return getDataSet().getCesta(supermarket, i);
    }

    @Override
    default int getDostupneZbozi(int tovarna, int zbozi) {
        return getDataSet().getDostupneZbozi(tovarna, zbozi);
    }

    @Override
    default int[] getSupermarketZasoby(int cisloSupermarketu) {
        return getDataSet().getSupermarketZasoby(cisloSupermarketu);
    }

    @Override
    default void setDostupneZboziTovarny(int tovarna, int zbozi, int mnozstvi) {
        getDataSet().setDostupneZboziTovarny(tovarna, zbozi, mnozstvi);
    }

    @Override
    default void setTovarny(Tovarna[] tovarny) {
        getDataSet().setTovarny(tovarny);
    }

    @Override
    default void setSuperMarkety(SuperMarket[] superMarkety) {
        getDataSet().setSuperMarkety(superMarkety);
    }

    @Override
    default Tovarna getTovarna(int tovarna) {
        return getDataSet().getTovarna(tovarna);
    }

    @Override
    default List<Objednavka> getObjednavky(int den) {
        return getDataSet().getObjednavky(den);
    }

    @Override
    default int getCelkovaCena(int den) {
        return getDataSet().getCelkovaCena(den);
    }

    @Override
    default int getCelkovaCena() {
        return getDataSet().getCelkovaCena();
    }

    @Override
    default String getFullText() {
        return getLog().getFullText();
    }

    @Override
    default String getShortText() {
        return getLog().getShortText();
    }

    @Override
    default int getSize() {
        return getLog().getSize();
    }

    @Override
    default Logovatelne find(String patern) {
        return getLog().find(patern);
    }

    @Override
    default int getPocetTovarny() {
        return getDataSet().getPocetTovarny();
    }

    @Override
    default int getPocetSupermarketu() {
        return getDataSet().getPocetSupermarketu();
    }

    @Override
    default int getPocetZbozi() {
        return getDataSet().getPocetZbozi();
    }

    @Override
    default int getPocetDni() {
        return getDataSet().getPocetDni();
    }
}
