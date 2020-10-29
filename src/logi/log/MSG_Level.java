package logi.log;


public enum MSG_Level {
    Info(0), Sklad(1), Zasobovani(1), Warning(3), Alert(4), Error(5);
    public final int level;

    MSG_Level(int level) {
        this.level = level;
    }
}
