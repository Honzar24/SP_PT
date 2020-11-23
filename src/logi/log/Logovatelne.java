package logi.log;

public interface Logovatelne extends Printable {

    String getLog();

    int getSize();

    Logovatelne find(String patern);

}
