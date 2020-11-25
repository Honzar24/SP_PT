package data;

/**
 * Interface umožňující přístup k základním informacím DataSetu
 */
public interface DataSetZakladniInfo {

    /**
     * Vrátí počet továren
     * @return počet továren
     */
    int getPocetTovarny();

    /**
     * Vrátí počet supermarketů
     * @return počet supermarketů
     */
    int getPocetSupermarketu();

    /**
     * Vrátí počet druhů zboží
     * @return počet druhů zboží
     */
    int getPocetZbozi();

    /**
     * Vrátí počet dní
     * @return počet dní
     */
    int getPocetDni();
}
