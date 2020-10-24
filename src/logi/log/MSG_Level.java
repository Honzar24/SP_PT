package logi.log;


public enum MSG_Level {
    info(0), warning(1), alert(2), error(3);
    public final int level;

    MSG_Level(int level) {
        this.level = level;
    }
}
