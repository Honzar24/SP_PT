package logi.simulace;


import logi.log.Log;
import logi.log.Logovatelne;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class RucniSimulace implements Simulace {

    private final Scanner in;
    private final PrintWriter out;
    private final Simulace simulace;
    private final String help = "Vítej v ručním ovládání\n" +
            "už jsi zjistil že existuje příkaz help\n" +
            "Další příkazy:\n" +
            "\tnext [0-I] \nPosune simulaci o zadaný počet dnů. Pokud není zadáno číslo tak se simulace posune o jeden den.\n" +
            "\texit\nUkončí simulaci okamžitě i její ovládání\n" +
            "\tend | run\nDokončí simulaci a ukončí ovládání!\n" +
            "\tinfo [hledaný výraz]\nNalezne v logu všechny výskyty hledaného výrazu." +
            "\tlog\nVypíše dosavadní log simulace obsahující všechny informace.";
    private boolean konec;

    public RucniSimulace(Simulace ovladanaSimulace) {
        this(ovladanaSimulace, System.in, System.out);
    }

    public RucniSimulace(Simulace ovladanaSimulace, InputStream vstup, OutputStream vystup) {
        simulace = ovladanaSimulace;
        in = new Scanner(vstup);
        out = new PrintWriter(vystup, true);
        konec = false;
    }

    @Override
    public void run() {
        out.println("Ruční ovládání Simulace aktivováno. Napiš help pro nápovědu.");
        do {
            vykonaniPrikazu(getPrikaz());
        } while (!simulace.skonceno() || !konec);
        out.println("Simulace ukončena\nRučni ovladani bylo ukončeno!");
    }

    private void vykonaniPrikazu(String prikaz) {
        String[] input = prikaz.trim().split(" ");
        switch (input[0]) {
            case "help":
                out.print(help);
                break;
            case "run":
                out.println("Dokončuji simulaci");
                simulace.run();
                out.println("Simulace dokončena");
                break;
            case "end":
                out.println("");
                simulace.run();
            case "exit":
                ukonciSimulaci();
                break;
            case "next":
                next(input);
                break;
            case "info":
                String[] paterns = prikaz.trim().substring(4).trim().split("&");
                Logovatelne vysledek = simulace.find("");
                for (int i = 0; i < paterns.length; i++) {
                    vysledek = vysledek.find(paterns[i].trim().toLowerCase() + " ");
                }
                vysledek.print(out);
                break;
            case "log":
                simulace.print(out);
                break;
            default:
                out.println("nerozpoznany příkaz zkus help " + input[0]);
        }
    }

    private void next(String[] input) {
        try {
            int pocet = input.length < 2 ? 1 : Integer.parseInt(input[1]);
            out.println("Generuji simulaci pro " + pocet + " dní.");
            for (int i = 0; i < pocet; i++) {
                if (simulace.skonceno()) {
                    ukonciSimulaci();
                    break;
                }
                out.printf("Simuluji %d. den\n", getDen());
                simulace.nextDay();
            }
        } catch (NumberFormatException e) {
            out.println(input[1] + " není platné číslo.");
        }
    }

    private String getPrikaz() {
        out.printf("C>");
        return in.nextLine();
    }

    @Override
    public boolean skonceno() {
        return simulace.skonceno();
    }

    @Override
    public void ukonciSimulaci() {
        konec = true;
        simulace.ukonciSimulaci();
        out.println("Simulace ukončena");
    }

    @Override
    public Log nextDay() {
        return simulace.nextDay();
    }

    @Override
    public int getDen() {
        return simulace.getDen();
    }

    @Override
    public String getFullText() {
        return simulace.getFullText();
    }

    @Override
    public String getShortText() {
        return simulace.getShortText();
    }

    @Override
    public int getSize() {
        return simulace.getSize();
    }

    @Override
    public Logovatelne find(String patern) {
        return simulace.find(patern);
    }

    @Override
    public void print(PrintWriter vystup) {
        simulace.print(vystup);
    }
}
