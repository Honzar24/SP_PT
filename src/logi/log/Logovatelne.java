package logi.log;

public interface Logovatelne extends Printable {

    String getFullText();

    String getShortText();

    int getSize();

    Logovatelne find(String patern);

}
