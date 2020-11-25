package logi.log;

/**
 * Interface umožňující třídám, které ho implementují, logovat
 */
public interface Logovatelne extends Printable {

    /**
     * Vrátí log převedený do Stringu
     * @return log převedený do Stringu
     */
    String getLog();

    /**
     * Vrátí velikost logu
     * @return velikost logu
     */
    int getSize();

    /**
     * TODO:
     * @param patern
     * @return
     */
    Logovatelne find(String patern);

}
