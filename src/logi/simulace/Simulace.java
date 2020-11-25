package logi.simulace;

import data.*;
import logi.log.Log;
import logi.log.Logovatelne;
import logi.log.Logujici;

import java.util.List;

/**
 * Interface umožňující běh simulace
 */
public interface Simulace extends Logujici, DataSet {
    void run();

    /**
     * Vrací informaci jestli je simulace ukončena
     * @return true/false jestli je simulace ukončena
     */
    boolean skonceno();

    /**
     * Ukončí simulaci
     */
    void ukonciSimulaci();

    /**
     * Posune simulaci o jeden den
     * @return log s informacemi o běhu
     */
    Log nextDay();

    /**
     * Vrátí číslo dne běhu simulace
     * @return číslo dne
     */
    int getDen();

    /**
     * Vrátí DataSet simulace
     * @return DataSet simulace
     */
    DataSet getDataSet();

    /**
     * Vrátí Log simulace
     * @return Log simulace
     */
    Logujici getLog();

    /**
     * Přidá do Logu novou zprávu
     * @param novaZprava zpráva k přidání
     * @return Log simulace
     */
    @Override
    default Logujici log(Logovatelne novaZprava) {
        return getLog().log(novaZprava);
    }

    /**
     * Přidá novou objednácku
     * @param novaObjednavka objednávka k přidání
     * @param den den, který se objednávka přidává
     */
    @Override
    default void addObjednavka(Objednavka novaObjednavka, int den) {
        getDataSet().addObjednavka(novaObjednavka, den);
    }

    /**
     * Nastaví supermarketu zásoby určitého zboží
     * @param supermarket id supermarketu
     * @param zbozi id zboží
     * @param zasoby počet zásob
     */
    @Override
    default void setZasobySupermarketu(int supermarket, int zbozi, int zasoby) {
        getDataSet().setZasobySupermarketu(supermarket, zbozi, zasoby);
    }

    /**
     * Vrátí počet zásob urřitého zboží v supermarketu
     * @param supermarket id supermarketu
     * @param zbozi id zboží
     * @return počet položek
     */
    @Override
    default int getZasobySupermarketu(int supermarket, int zbozi) {
        return getDataSet().getZasobySupermarketu(supermarket, zbozi);
    }

    /**
     * Vrátí počet cest supermarketu
     * @param supermarket id supermarketu
     * @return počet cest supermarketu
     */
    @Override
    default int getPocetCest(int supermarket) {
        return getDataSet().getPocetCest(supermarket);
    }

    /**
     * Vrátí cestu ze supermarketu na určitém indexu
     * @param supermarket id supermarketu
     * @param i index cesty
     * @return cesta vedoucí ze supermarketu
     */
    @Override
    default Cesta getCesta(int supermarket, int i) {
        return getDataSet().getCesta(supermarket, i);
    }

    /**
     * Vrátí počet dostupných kusů zboží v továrně
     * @param tovarna id továrny
     * @param zbozi id zboží
     * @return počet dostupných kusů zboží v továrně
     */
    @Override
    default int getDostupneZbozi(int tovarna, int zbozi) {
        return getDataSet().getDostupneZbozi(tovarna, zbozi);
    }

    /**
     * Vrátí pole se zásobami supermarketu
     * @param cisloSupermarketu id supermarketu
     * @return pole se zásobami supermarketu
     */
    @Override
    default int[] getSupermarketZasoby(int cisloSupermarketu) {
        return getDataSet().getSupermarketZasoby(cisloSupermarketu);
    }

    /**
     * Nastaví továrně zadaný počet zboží
     * @param tovarna id továrny
     * @param zbozi id zboží
     * @param mnozstvi počet kusů
     */
    @Override
    default void setDostupneZboziTovarny(int tovarna, int zbozi, int mnozstvi) {
        getDataSet().setDostupneZboziTovarny(tovarna, zbozi, mnozstvi);
    }

    /**
     * Nastaví továrny DataSetu
     * @param tovarny pole továren
     */
    @Override
    default void setTovarny(Tovarna[] tovarny) {
        getDataSet().setTovarny(tovarny);
    }
    /**
     * Nastaví supermarkety DataSetu
     * @param superMarkety pole supermarketů
     */
    @Override
    default void setSuperMarkety(SuperMarket[] superMarkety) {
        getDataSet().setSuperMarkety(superMarkety);
    }

    /**
     * Vrátí továrnu se zadaným id
     * @param tovarna id továrny
     * @return továrna se zadaným id
     */
    @Override
    default Tovarna getTovarna(int tovarna) {
        return getDataSet().getTovarna(tovarna);
    }

    /**
     * Vrátí objednávky pro zadaný den
     * @param den číslo dne
     * @return List objednávak pro zadaný den
     */
    @Override
    default List<Objednavka> getObjednavky(int den) {
        return getDataSet().getObjednavky(den);
    }

    /**
     * Vrátí celkovou cenu objednávek pro zadaný den
     * @param den číslo dne
     * @return celková cena objednávek
     */
    @Override
    default int getCelkovaCena(int den) {
        return getDataSet().getCelkovaCena(den);
    }

    /**
     * Vrátí celkovou cenu objednávek pro všechny dny
     * @return celková cena objednávek
     */
    @Override
    default int getCelkovaCena() {
        return getDataSet().getCelkovaCena();
    }

    /**
     * Vrátí kompletní informace z logu
     * @return kompletní informace z logu
     */
    @Override
    default String getFullText() {
        return getLog().getFullText();
    }

    /**
     * Vrátí zkrácené informace z logu
     * @return zkrácené informace z logu
     */
    @Override
    default String getShortText() {
        return getLog().getShortText();
    }

    /**
     * Vrátí velikost logu
     * @return velikost logu
     */
    @Override
    default int getSize() {
        return getLog().getSize();
    }

    /**
     * Zjistí jestli log obsahuje zadaný podřetězec
     * @param patern hledaný podřetězec
     * @return Log s informacemi o nalezení
     */
    @Override
    default Logovatelne find(String patern) {
        return getLog().find(patern);
    }

    /**
     * Vrátí počet továren
     * @return počet továren
     */
    @Override
    default int getPocetTovarny() {
        return getDataSet().getPocetTovarny();
    }

    /**
     * Vrátí počet supermarketů
     * @return počet supermarketů
     */
    @Override
    default int getPocetSupermarketu() {
        return getDataSet().getPocetSupermarketu();
    }

    /**
     * Vrátí počet druhů zboží
     * @return počet druhů zboží
     */
    @Override
    default int getPocetZbozi() {
        return getDataSet().getPocetZbozi();
    }

    /**
     * Vrátí počet dní simulace
     * @return počet dní
     */
    @Override
    default int getPocetDni() {
        return getDataSet().getPocetDni();
    }
}
