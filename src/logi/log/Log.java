package logi.log;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Třída reprezentující log, do kterého se zapisují události
 */
public class Log implements Logujici {
    /**
     * List všech zpráv
     */
    private final List<Logovatelne> log;
    /**
     * Název logu
     */
    private final String name;

    /**
     * Konstruktor vytvoří log a pojmenuje je "Log"
     */
    public Log() {
        this("Log");
    }

    /**
     * Konstruktor vytvoří log a pojmenuje ho zadaným jménem
     * @param jmeno název logu
     */
    public Log(String jmeno) {
        log = new LinkedList<>();
        name = jmeno + " ";
    }

    /**
     * Konstruktor vytvoří log s předaným seznamem zpráv a pojmenuje ho "Log"
     * @param log seznam zpráv
     */
    public Log(List<Logovatelne> log) {
        this(log, "log");
    }

    /**
     * Konstruktor vytvoří log s předaným seznamem zpráv a zadaným jménem
     * @param log seznam zpráv
     * @param nazev název logu
     */
    public Log(List<Logovatelne> log, String nazev) {
        this.log = log;
        name = nazev + " ";
    }

    /**
     * Přidá do logu novou zrávu
     * @param msg zpráva k přidání
     */
    public void log(String msg) {
        log.add(new Message(msg, MsgLevel.INFO));
    }

    /**
     * Přidá do logu předaný log
     * @param log log určený k přidání
     * @return this
     */
    public Logujici log(Logovatelne log) {
        if (log.getSize() != 0) {
            this.log.add(log);
        }
        return this;
    }

    /**
     * Vrátí kompletní informace z logu
     * @return kompletní informace z logu
     */
    @Override
    public String getFullText() {
        return log.stream().map(Logovatelne::getFullText).collect(Collectors.joining("\n"));
    }

    /**
     * Vrátí zkrácené informace z logu
     * @return zkrácené informace z logu
     */
    @Override
    public String getShortText() {
        return log.stream().map(Logovatelne::getShortText).collect(Collectors.joining("\n"));
    }

    /**
     * Vrátí velikost logu
     * @return velikost logu
     */
    @Override
    public int getSize() {
        return log.size();
    }

    /**
     * Zjistí jestli log obsahuje zadaný podřetězec
     * @param patern hledaný podřetězec
     * @return Log s informacemi o nalezení
     */
    @Override
    public Logovatelne find(String patern) {
        if (name.toLowerCase().contains(patern.trim() + " ")) {
            return new Log(this.log, "find " + patern);
        }
        List<Logovatelne> collect = log.stream().map(e -> e.find(patern)).filter(Objects::nonNull).filter(e -> e.getSize() > 0).collect(Collectors.toList());
        return new Log(collect, "find " + patern);
    }

    /**
     * Vypíše log do výstupu
     * @param vystup kam se má vypisovat
     */
    @Override
    public void print(PrintWriter vystup) {
        log.forEach(e -> e.print(vystup));
    }

    /**
     * Převede informace o logu do Stringu, který je možný vypsat
     * @return String s informacemi o logu
     */
    @Override
    public String toString() {
        return String.format("Log %s s počtem položek %d", name, getSize());
    }
}
