package logi.log;

import java.io.PrintWriter;

public class Message implements Logovatelne {
    public final String msg;
    public final MsgLevel level;

    public Message(String msg) {
        this.msg = msg;
        this.level = MsgLevel.INFO;
    }

    public Message(String msg, MsgLevel level) {
        this.msg = msg;
        this.level = level;
    }

    @Override
    public String toString() {
        return level + " >" + msg + " ";
    }

    @Override
    public String getLog() {
        return toString();
    }

    @Override
    public int getSize() {
        return 1;
    }

    private boolean contains(String patern) {
        return getLog().toLowerCase().contains(patern);
    }

    @Override
    public Logovatelne find(String patern) {
        return contains(patern) ? this : null;
    }

    @Override
    public void print(PrintWriter vystup) {
        vystup.printf(toString() + "\n");
    }
}