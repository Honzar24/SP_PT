package logi.log;

import java.io.PrintWriter;

/**
 * Uvožňuje být logovaný
 */
public interface Logovatelne extends Printable {

    /**
     * Metoda ke zjištění celé zprávy
     * @return celá zpráva
     */
    String getFullText();

    /**
     * Metoda ke zjištění kreatší verze zprávy
     * @return krátká verze zprávy
     */
    String getShortText();

    /**
     * Vrátí velikost logu
     * @return velikost logu
     */
    int getSize();

    /**
     * Zjistí jesltli obsahuje podřetězec
     * @param patern hledaný podřetězec
     * @return Logovatelne
     */
    Logovatelne find(String patern);

    /**
     * Vypíše celou zprávu do výstupu
     * @param vystup kam se má vypisovat
     */
    @Override
    default void print(PrintWriter vystup) {
        vystup.println(getFullText());
    }
}
