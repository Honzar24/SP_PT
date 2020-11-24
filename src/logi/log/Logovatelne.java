package logi.log;

import java.io.PrintWriter;

public interface Logovatelne extends Printable {

    String getFullText();

    String getShortText();

    int getSize();

    Logovatelne find(String patern);

    @Override
    default void print(PrintWriter vystup) {
        vystup.println(getFullText());
    }
}
