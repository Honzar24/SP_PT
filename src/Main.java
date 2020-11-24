import data.DataSet;
import logi.simulace.GreedSimulace;
import logi.simulace.RucniSimulace;
import logi.simulace.Simulace;
import parser.Parser;

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
     * Funkce si z parametrů předaných při spuštění vezme adresu dat, které následně převede do datasetu a spustí s nimi simulaci.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("program byl spuštěn s argumenty: " + Arrays.toString(args));
        HashMap<String, Integer> argumenty = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            argumenty.put(args[i], i);
        }
        if (!argumenty.containsKey("-f")) {
            System.out.println("Nebyl zadán soubor jeho zadání povedete přidáním: -f soubor");
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
        System.out.printf("Čas simulace v ms %d\n", (stop - start) / 1000000);
        System.out.print("Chcete vypsat log?(a/n)");
        if (new Scanner(System.in).nextLine().trim().toLowerCase().startsWith("a")) {
            simulace.print(new PrintWriter(System.out, true));
        }
    }
}
