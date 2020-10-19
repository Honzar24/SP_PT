package logi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Log
{
    public enum MSG_Level
    {
        log(0), warning(1), alert(2), error(3);
        public final int level;
        MSG_Level(int level) {
            this.level = level;
        }
    }
    public static class Message
    {
        public final String msg;
        public final MSG_Level level;

        public Message(String msg, MSG_Level level) {
            this.msg = msg;
            this.level = level;
        }

        @Override
        public String toString() {
            return level+">"+msg;
        }
    }
    private static List<Message> log=new LinkedList<>();

    public static void log(String msg)
    {
        log.add(new Message(msg,MSG_Level.log));
    }

    public static void log(Message message)
    {
        log.add(message);
    }

}
