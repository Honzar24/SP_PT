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

/**
 * Třída reprezentující simulaci ručně řízenou
 */
public class RucniSimulace implements Simulace {
    /**
     * Scanner pro čtení příkazů
     */
    private final Scanner in;
    /**
     * PrintWriter umožňující výpis
     */
    private final PrintWriter out;
    /**
     * Běžící simulace
     */
    private final Simulace simulace;
    /**
     * Úvodní text při startu ruční simulace
     */
    private final String help = "Vítej v ručním ovládání\n" +
            "už jsi zjistil že existuje příkaz help\n" +
            "Další příkazy:\n" +
            "\tnext [0-I] \nPosune simulaci o zadaný počet dnů. Pokud není zadáno číslo tak se simulace posune o jeden den.\n" +
            "\texit\nUkončí simulaci okamžitě i její ovládání\n" +
            "\tend | run\nDokončí simulaci a ukončí ovládání!\n" +
            "\tinfo [hledaný výraz]\nNalezne v logu všechny výskyty hledaného výrazu." +
            "\tlog\nVypíše dosavadní log simulace obsahující všechny informace." +
            "\tobjednávka\nspustí zadávání objednávky uživatelem";
    /**
     * Informace o konci simulace
     */
    private boolean konec;

    /**
     * Konstruktor vytvářející ručně ovládanou simulaci s výpisem do příkazové řádky a čtení z ní
     * @param ovladanaSimulace Simulace určená k ovládání
     */
    public RucniSimulace(Simulace ovladanaSimulace) {
        this(ovladanaSimulace, System.in, System.out);
    }

    /**
     * Konstruktor vytvářející ručně ovládanou simulaci s určeným vstupem a výstupem
     * @param ovladanaSimulace Simulace určená k ovládání
     * @param vstup textový vstup do simulace
     * @param vystup textový výstup ze simulace
     */
    public RucniSimulace(Simulace ovladanaSimulace, InputStream vstup, OutputStream vystup) {
        simulace = ovladanaSimulace;
        in = new Scanner(vstup);
        out = new PrintWriter(vystup, true);
        konec = false;
    }

    /**
     * Metoda spouštící simulaci.
     */
    @Override
    public void run() {
        out.println("Ruční ovládání Simulace aktivováno. Napiš help pro nápovědu.");
        do {
            vykonaniPrikazu(getPrikaz());
        } while (!simulace.skonceno() || !konec);
        out.println("Simulace ukončena\nRučni ovladani bylo ukončeno!");
    }

    /**
     * Stará se o vykonání správného příkazu na základě zadaného vstupu použitím dalších metod
     * @param prikaz zadaný příkaz
     */
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

    /**
     * Metoda ukončí simulaci
     */
    private void konec() {
        if (!skonceno()) {
            ukonciSimulaci();
        } else {
            konec = true;
        }
    }

    /**
     * Umožňuje zadání objednávky do simulace
     */
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

    /**
     * Metoda čte zadaný den a ověřuje jeho platnost
     * @return číslo dne
     */
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

    /**
     * Metoda vytvoří novou objednávku na základě zadaných hodnot v příkazové řádce
     * @return vytvořená objednávka
     */
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

    /**
     * Posune simulaci o x dní, počet se zadává v příkazové řádce
     * @param input vstup
     */
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

    /**
     * Metoda přečte příkaz a vrátí ho
     * @return příkaz
     */
    private String getPrikaz() {
        out.printf("C>");
        return in.nextLine();
    }

    /**
     * vrátí informaci o konci simulace (skončeno - true, běžící - false)
     * @return informacr o konci simulace
     */
    @Override
    public boolean skonceno() {
        return simulace.skonceno();
    }

    /**
     * Metoda se stará o správné ukončení simulace
     */
    @Override
    public void ukonciSimulaci() {
        konec = true;
        simulace.ukonciSimulaci();
        out.println("Simulace ukončena");
    }

    /**
     * Metoda posune simulaci na další den
     * @return Log s informacemi o proběhlém dni
     */
    @Override
    public Log nextDay() {
        return simulace.nextDay();
    }

    /**
     * Vrátí číslo dne simulace
     * @return číslo dne
     */
    @Override
    public int getDen() {
        return simulace.getDen();
    }

    /**
     * Vrátí DataSet simulace
     * @return DataSet simulace
     */
    @Override
    public DataSet getDataSet() {
        return simulace.getDataSet();
    }

    /**
     * Vrátí Log s informacemi o simulaci
     * @return Log s informacemi
     */
    @Override
    public Logujici getLog() {
        return simulace.getLog();
    }
}
