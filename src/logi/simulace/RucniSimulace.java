package logi.simulace;


import data.DataSet;
import data.Objednavka;
import logi.log.Log;
import logi.log.Logovatelne;
import logi.log.Logujici;

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
                out.println("Dokunčuji simulaci na příkaz uživatele");
                simulace.run();
            case "exit":
                konec();
                break;
            case "next":
                next(input);
                break;
            case "info":
                vypisInfo(prikaz);
                break;
            case "objednávka":
                zadaniObjednavky();
                break;
            case "log":
                simulace.print(out);
                break;
            default:
                out.println("nerozpoznany příkaz zkus help " + input[0]);
        }
    }

    private void konec() {
        if (!skonceno()) {
            ukonciSimulaci();
        } else {
            konec = true;
        }
    }

    private void zadaniObjednavky() {
        Objednavka objednavka = vytvorObjednavku();
        if (objednavka != null) {
            int den = zadejDen();
            if (den >= 0) {
                addObjednavka(objednavka, den);
                return;
            }
        }
        out.println("Zadání objednávky selhalo zkuste to znovu!");
    }

    private void vypisInfo(String prikaz) {
        String[] paterns = prikaz.trim().substring(4).trim().split("&");
        Logovatelne vysledek = simulace.find("");
        for (int i = 0; i < paterns.length; i++) {
            vysledek = vysledek.find(paterns[i].trim().toLowerCase() + " ");
        }
        vysledek.print(out);
    }

    private int zadejDen() {
        out.printf("Zadej den:");
        String vztup = "";
        try {
            vztup = in.nextLine();
            int zadanyDen = Integer.parseInt(vztup);
            if (zadanyDen < simulace.getDen()) {
                out.println("Nemůžeš zadávat objednávky retrospektivně!");
                return -1;
            }
            if (zadanyDen >= getPocetDni()) {
                out.println("Nemůžeš zadávat objednávky mimo simulovaný úsek! Maximum je " + (getPocetDni() - 1));
                return -1;
            }
            return zadanyDen;
        } catch (NumberFormatException e) {
            out.printf(vztup + " není platné číslo!\nZadávání objednávky selhalo ");
            return -1;
        }

    }

    private Objednavka vytvorObjednavku() {
        int cisloSupermarketu;
        int cisloZbozi;
        int pocetZbozi;
        String vztup = "";
        try {
            out.printf("Zadejte číslo zboží:");
            vztup = in.nextLine();
            cisloZbozi = Integer.parseInt(vztup);
            if (cisloZbozi >= getPocetZbozi() || cisloZbozi < 0) {
                out.printf("Zadané číslo(%d) zboží je neplatné kldané číslo a maximum je %d\n", cisloZbozi, getPocetZbozi() - 1);
                return null;
            }
            out.printf("Zadejte množství zboži k objednání:");
            vztup = in.nextLine();
            pocetZbozi = Integer.parseInt(vztup);
            if (pocetZbozi <= 0) {
                out.println("Počet zboží musí být kladné nenulové číslo");
                return null;
            }
            out.printf("Do jakého supermarketu to mám odvést:");
            vztup = in.nextLine();
            cisloSupermarketu = Integer.parseInt(vztup);
            if (cisloSupermarketu >= getPocetSupermarketu() || cisloSupermarketu < 0) {
                out.printf("Zadané číslo(%d) supermaketu je neplatné kldané číslo a maximum je %d\n", cisloZbozi, getPocetSupermarketu() - 1);
                return null;
            }
        } catch (NumberFormatException e) {
            out.printf(vztup + " není platné číslo!\nZadávání objednávky selhalo ");
            return null;
        }
        Objednavka objednavka = new Objednavka(cisloSupermarketu, cisloZbozi, pocetZbozi);
        out.println("Vytvořená objednávka:" + objednavka);
        return objednavka;
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
    public DataSet getDataSet() {
        return simulace.getDataSet();
    }

    @Override
    public Logujici getLog() {
        return simulace.getLog();
    }
}
