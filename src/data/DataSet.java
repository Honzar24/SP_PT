package data;

import java.util.List;

public interface DataSet extends DataSetNaplneni, DataSetZakladniInfo {

    void addObjednavka(Objednavka novaObjednavka, int den);

    Tovarna getTovarna(int tovarna);

    List<Objednavka> getObjednavky(int den);

    Cesta getCesta(int supermarket, int i);

    int getCelkovaCena(int den);

    int getCelkovaCena();

    int getZasobySupermarketu(int supermarket, int zbozi);

    int getPocetCest(int supermarket);

    int getDostupneZbozi(int tovarna, int zbozi);

    int[] getSupermarketZasoby(int cisloSupermarketu);
}
