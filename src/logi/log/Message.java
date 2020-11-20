package logi.log;

public class Message implements Logovatelne {
    public final String msg;
    public final MsgLevel level;

    public Message(String msg, MsgLevel level) {
        this.msg = msg;
        this.level = level;
    }

    @Override
    public String toString() {
        return level + ">" + msg;
    }

    @Override
    public String getLog() {
        return toString();
    }

    @Override
    public int getSize() {
        return 1;
    }
}