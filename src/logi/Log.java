package logi;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Log implements Logovatelne {

    private List<Logovatelne> log = new LinkedList<>();

    public void log(String msg) {
        log.add(new Message(msg, MSG_Level.info));
    }

    public void log(Logovatelne log)
    {
        if (log.getSize()!=0)
        this.log.add(log);
    }

    @Override
    public String getLog() {
        return log.stream().map(Logovatelne::getLog).collect(Collectors.joining("\n"));
    }

    @Override
    public int getSize() {
        return log.size();
    }

}
