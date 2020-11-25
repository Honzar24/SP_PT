package logi.log;

import java.io.PrintWriter;

/**
 * Interface umožňující výpis
 */
public interface Printable {
    /**
     * Vypíše do vystupu
     * @param vystup kam se má vypisovat
     */
    void print(PrintWriter vystup);
}
