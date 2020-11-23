package logi.log;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Log implements Logovatelne {

    private final List<Logovatelne> log;
    private final String name;


    public Log() {
        this("Log");
    }

    public Log(String jmeno) {
        log = new LinkedList<>();
        name = jmeno;
    }

    public Log(List<Logovatelne> log) {
        this(log, "log");
    }

    public Log(List<Logovatelne> log, String nazev) {
        this.log = log;
        name = nazev;
    }

    public void log(String msg) {
        log.add(new Message(msg, MsgLevel.INFO));
    }

    public void log(Logovatelne log) {
        if (log.getSize() != 0) {
            this.log.add(log);
        }
    }

    @Override
    public String getLog() {
        return log.stream().map(Logovatelne::getLog).collect(Collectors.joining("\n"));
    }

    @Override
    public int getSize() {
        return log.size();
    }


    @Override
    public Logovatelne find(String patern) {
        if (name.toLowerCase().contains(patern.trim())) {
            return new Log(this.log, "find " + patern);
        }
        List<Logovatelne> collect = log.stream().map(e -> e.find(patern)).filter(Objects::nonNull).filter(e -> e.getSize() > 0).collect(Collectors.toList());
        return new Log(collect, "find " + patern);
    }

    @Override
    public void print(PrintWriter vystup) {
        log.forEach(e -> e.print(vystup));
    }

    @Override
    public String toString() {
        return String.format("Log %s s počtem položek %d", name, getSize());
    }
}
