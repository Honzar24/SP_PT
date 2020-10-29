package logi.log;


public enum MSG_Level {
    info(0), Sklad(1), Zasobovani(1), warning(3), alert(4), error(5);
    public final int level;

    MSG_Level(int level) {
        this.level = level;
    }
}
