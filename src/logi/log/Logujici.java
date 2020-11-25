package logi.log;

/**
 * Umožňuje logovat
 */
public interface Logujici extends Logovatelne {
    /**
     * Přidá do logu zprávu
     * @param novaZprava zpráva k přidání
     * @return log do kterého byla zpráva přidaná
     */
    Logujici log(Logovatelne novaZprava);
}
