package logi.log;


public enum MsgLevel {
    INFO(0), SKLAD(1), ZASOBOVANI(1), WARNING(3), KONEC(4), ERROR(5), ZASOBY(1);
    public final int level;

    MsgLevel(int level) {
        this.level = level;
    }
}
