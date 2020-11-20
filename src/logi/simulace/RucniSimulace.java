package logi.simulace;


import logi.log.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class RucniSimulace implements Simulace {

    private final Scanner in;
    private final PrintWriter out;
    private final Simulace simulace;
    private final String help = "Vítej v ručním ovládání\n" +
            "už jsi zjistil že existuje příkaz help";
    private boolean konec = false;

    public RucniSimulace(Simulace ovladanaSimulace) {
        this(ovladanaSimulace, System.in, System.out);
    }

    public RucniSimulace(Simulace ovladanaSimulace, InputStream vstup, OutputStream vystup) {
        simulace = ovladanaSimulace;
        in = new Scanner(vstup);
        out = new PrintWriter(vystup, true);
    }

    @Override
    public void run() {
        out.println("Ruční ovládání Simulace aktivováno. Napiš help pro nápovědu.");
        while (!konec || simulace.skonceno()) {
            String[] input = getInput().trim().split(" ");
            switch (input[0]) {
                case "help":
                case "h":
                    out.println(help);
                    break;
                case "quit":
                case "q":
                    konec = true;
                    break;
                case "n":
                case "next":

                    simulace.nextDay();
                    break;
                default:
                    out.println("nerozpoznany příkaz zkus help " + input[0]);
            }
        }
    }

    private void vypis(String s) {
        out.printf(s);
    }

    @Override
    public boolean skonceno() {
        return konec;
    }

    @Override
    public Log nextDay() {
        return simulace.nextDay();
    }

    private String getInput() {
        vypis("C>");
        return in.nextLine();
    }


    @Override
    public String getLog() {
        return simulace.getLog();
    }

    @Override
    public int getSize() {
        return simulace.getSize();
    }
}
