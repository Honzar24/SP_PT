package logi.log;

/**
 * Třída reprezentující zprávy simulace
 */
public class Message implements Logovatelne {
    /**
     * Text zprávy
     */
    public final String msg;
    /**
     * Úroveň zprávy
     */
    public final MsgLevel level;

    /**
     * Konstruktor vytvoří zprávu a nastaví jí informační úroveň
     * @param msg text zprávy
     */
    public Message(String msg) {
        this.msg = msg;
        this.level = MsgLevel.INFO;
    }

    /**
     * Konstruktor vytvoří zprávu a nastaví jí úroveň dle zadané
     * @param msg text zprávy
     * @param level úroveň zprávy
     */
    public Message(String msg, MsgLevel level) {
        this.msg = msg;
        this.level = level;
    }

    /**
     * Převede zprávu do Stringu, který je možné vypsat
     * @return zpráva jako String
     */
    @Override
    public String toString() {
        return level + " >" + msg + " ";
    }

    /**
     * Vrátí zprávu včetně úrovně
     * @return zpráva včetně úrovně
     */
    @Override
    public String getFullText() {
        return toString();
    }

    /**
     * vrátí pouze text zprávy
     * @return text zprávy
     */
    @Override
    public String getShortText() {
        return msg;
    }

    /**
     * Vrátí velikost zprávy (vždy 1)
     * @return velikost zprávy (vždy 1)
     */
    @Override
    public int getSize() {
        return 1;
    }

    /**
     * Zjistí jestli zpráva obsahuje zadaný podřetězec
     * @param patern hledaný podřetězec
     * @return true/false o obsahu podřetězce
     */
    private boolean contains(String patern) {
        return getFullText().toLowerCase().contains(patern);
    }

    /**
     * Zjistí jestli zpráva obsahuje zadaný podřetězec
     * @param patern hledaný podřetězec
     * @return pokud obsahuje vrátí this, pokud ne null
     */
    @Override
    public Logovatelne find(String patern) {
        return contains(patern) ? this : null;
    }

}