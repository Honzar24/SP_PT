import data.DataSet;
import logi.simulace.GreedSimulace;
import logi.simulace.Simulace;
import parser.Parser;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> argumenty = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            argumenty.put(args[i], i);
        }
        Integer f = argumenty.get("-f");
        DataSet dataSet = Parser.parseFile(args[f + 1]);
        Simulace simulace = new GreedSimulace(dataSet);
        long start = System.nanoTime();
        while (!simulace.skonceno()) {
            simulace.nextDay();
        }
        long stop = System.nanoTime();
        System.out.println(simulace.getLog());
        System.out.printf("Čas simulace v ms %d\n", (stop - start) / 1000000);

    }
}
