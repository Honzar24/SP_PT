import data.DataSet;
import logi.Log;
import parser.Parser;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> argumenty = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            argumenty.put(args[i], i);
        }
        Integer f = argumenty.get("-f");
        DataSet dataSet = Parser.parseFile(args[f + 1]);


    }
}
