package logi.log;

/**
 * Enum reprezentující úroveň zprávy
 */
public enum MsgLevel {
    INFO(0), SKLAD(1), ZASOBOVANI(1), WARNING(3), KONEC(4), ERROR(5), ZASOBY(1);
    public final int level;

    /**
     * Konstruktor nastaví úroveň na zadanou
     * @param level úroveň zprávy
     */
    MsgLevel(int level) {
        this.level = level;
    }
}
