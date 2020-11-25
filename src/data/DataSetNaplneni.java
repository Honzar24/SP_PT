package data;

public interface DataSetNaplneni {
    void setDostupneZboziTovarny(int tovarna, int zbozi, int mnozstvi);

    void setTovarny(Tovarna[] tovarny);

    void setSuperMarkety(SuperMarket[] superMarkety);

    void setZasobySupermarketu(int supermarket, int zbozi, int zasoby);
}
