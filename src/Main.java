import data.DataSet;
import logi.log.Logovatelne;
import logi.log.Message;
import logi.log.MsgLevel;
import logi.log.Printable;
import logi.simulace.GreedSimulace;
import logi.simulace.RucniSimulace;
import logi.simulace.Simulace;
import parser.Parser;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Hlavní třída pro spuštění simulátoru
 */
public class Main {
    /**
     * Funkce spouštící simulátor.
     * Očekávané parametry:
     * -f "adresa souboru" - pro spuštění simulátoru
     * -f "adresa souboru" -h - pro spuštění ruční simulace
     * -f "název souboru" -g - pro vygenerování souboru s daty
     *
     * @param args porametry spuštění
     */
    public static void main(String[] args) {
        System.out.println("program byl spuštěn s argumenty: " + Arrays.toString(args));
        HashMap<String, Integer> argumenty = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            argumenty.put(args[i], i);
        }
        if (!argumenty.containsKey("-f")) {
            System.out.println("Nebyl zadán soubor, jeho zadání povedete přidáním: -f soubor");
            return;
        }
        String file = args[argumenty.get("-f") + 1];
        if (argumenty.containsKey("-g")) {
            Generovani.generuj(file);
            return;
        }
        DataSet dataSet = Parser.parseFile(file);
        Simulace simulace = new GreedSimulace(dataSet);
        if (argumenty.containsKey("-h") || argumenty.containsKey("-H")) {
            simulace = new RucniSimulace(simulace);
        }
        System.out.println("Spouštím simulaci");
        long start = System.nanoTime();
        simulace.run();
        long stop = System.nanoTime();
        System.out.println("Simulace dokončena");
        long casBehu = (stop - start) / 1000000;
        simulace.log(new Message("Čas běhu simulace v ms: " + casBehu, MsgLevel.INFO));
        System.out.printf("Čas simulace v ms %d\n", casBehu);
        System.out.print("Chcete vypsat log?(a/n)");
        if (new Scanner(System.in).nextLine().trim().toLowerCase().startsWith("a")) {
            simulace.print(new PrintWriter(System.out, true));
        }
        System.out.print("Chcete zapsat statistiku do souborů?(a/n)");
        if (new Scanner(System.in).nextLine().trim().toLowerCase().startsWith("a")) {
            generujStatistiky(simulace);
        }
    }

    /**
     * Vygeneruje soubory do kterých se zapíší statistiky
     * @param simulace simulace která má být zapsaná
     */
    private static void generujStatistiky(Simulace simulace) {
        safePrint("MasterLog.txt", simulace);
        safePrint("Supermarkety.txt", simulace.find("zásoby den"));
        safePrint("Reseni.txt", simulace.find(MsgLevel.INFO.toString().toLowerCase()));
        try (PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("Tovarny.txt")), true)) {
            Logovatelne deniLog;
            for (int i = 0; i <= simulace.getDen(); i++) {
                printWriter.println("----Den " + i);
                deniLog = simulace.find("den " + i);
                for (int j = 0; j < simulace.getPocetTovarny(); j++) {
                    printWriter.println("---Tovarna " + j);
                    deniLog.find("továrna " + j).print(printWriter);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Soubor %s nelze otevřít!", "Tovarny.txt");
        }
    }

    /**
     * Vygeneruje soubor a zapíše statistiky
     * @param jmenoSouboru jméno generovaného souboru
     * @param printable statistiky k zapsání
     */
    private static void safePrint(String jmenoSouboru, Printable printable) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(jmenoSouboru)), true)) {
            printable.print(printWriter);
        } catch (FileNotFoundException e) {
            System.out.printf("Soubor %s nelze ovevřít!", jmenoSouboru);
        }
    }
}
