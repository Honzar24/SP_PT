package data;

public class Tovarna {
    public int [] sklad;
    public int [] [] vyroba;

    public Tovarna(int pocetZbozi, int pocetDnu) {
        vyroba=new int[pocetDnu][pocetZbozi];
        sklad=new int[pocetZbozi];
    }
}
