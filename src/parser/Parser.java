package parser;

import data.DataSet;
import data.Objednavka;
import data.SuperMarket;
import data.Tovarna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Parser {
    public static DataSet parseFile(String filePath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
        Stream<String> lines = reader.lines().map(String::trim).filter(s -> !s.startsWith("#") && !s.isEmpty());
        return parse(lines);
    }

    public static DataSet parse(Stream<String> lines) {
        List<int[]> numbers = lines
                .map(line -> Pattern.compile(" ").splitAsStream(line).mapToInt(Integer::parseInt))
                .map(IntStream::toArray)
                .collect(Collectors.toList());
        int[] head = numbers.remove(0);

        DataSet dataSet = new DataSet(head);


        int[][] cenaCest = new int[dataSet.D][dataSet.S];
        for (int i = 0; i < cenaCest.length; i++) {
            cenaCest[i] = numbers.remove(0);
        }

        int[][] zasoby = new int[dataSet.Z][dataSet.S];
        for (int i = 0; i < zasoby.length; i++) {
            zasoby[i] = numbers.remove(0);
        }


        SuperMarket[] superMarkets = new SuperMarket[dataSet.S];

        for (int i = 0; i < superMarkets.length; i++) {
            int[] cenyCest = new int[dataSet.D];
            for (int j = 0; j < cenyCest.length; j++) {
                cenyCest[j] = cenaCest[j][i];
            }
            superMarkets[i] = new SuperMarket(dataSet.Z, cenyCest);
        }

        for (int i = 0; i < zasoby.length; i++) {
            for (int j = 0; j < zasoby[i].length; j++) {
                superMarkets[j].zasoby[i] = zasoby[i][j];
            }
        }

        dataSet.superMarkety = superMarkets;

        int[][][] produkce = new int[dataSet.Z][dataSet.T][dataSet.S];
        for (int i = 0; i < produkce.length; i++) {
            for (int j = 0; j < produkce[i].length; j++) {
                produkce[i][j] = numbers.remove(0);
            }
        }

        Tovarna[] tovarny = new Tovarna[dataSet.D];
        for (int i = 0; i < tovarny.length; i++) {
            tovarny[i] = new Tovarna(dataSet.Z, dataSet.T);
        }

        for (int zbozi = 0; zbozi < produkce.length; zbozi++) {
            for (int den = 0; den < produkce[zbozi].length; den++) {
                for (int tovarna = 0; tovarna < produkce[zbozi][den].length; tovarna++) {
                    tovarny[tovarna].vyroba[den][zbozi] = produkce[zbozi][den][tovarna];
                }
            }
        }

        dataSet.tovarny = tovarny;

        int[][][] poptavka = new int[dataSet.Z][dataSet.T][dataSet.S];
        for (int i = 0; i < poptavka.length; i++) {
            for (int j = 0; j < poptavka[i].length; j++) {
                poptavka[i][j] = numbers.remove(0);
            }
        }

        for (int zbozi = 0; zbozi < poptavka.length; zbozi++) {
            for (int den = 0; den < poptavka[zbozi].length; den++) {
                for (int supermaket = 0; supermaket < poptavka[zbozi][den].length; supermaket++) {
                    dataSet.addObjednavka(new Objednavka(supermaket, zbozi, poptavka[zbozi][den][supermaket]), den);
                }
            }
        }

        return dataSet;
    }
}
