package parser;

import data.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Parser {
    public static DataSet parseFile(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            Stream<String> lines = reader.lines().map(String::trim).filter(s -> !s.startsWith("#") && !s.isEmpty());
            return parse(lines);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static DataSet parse(Stream<String> lines) {
        List<int[]> numbers = lines
                .map(line -> Pattern.compile(" ").splitAsStream(line)
                        .filter(s -> !s.isEmpty())
                        .mapToInt(Integer::parseInt)
                )
                .map(IntStream::toArray)
                .collect(Collectors.toList());

        DataSet dataSet = vytvorDataSet(numbers);
        int[][] cenaCest = parsujCesty(numbers, dataSet);
        int[][] zasoby = parsujZasoby(numbers, dataSet);
        SuperMarket[] superMarkets = vytvorSupermarkety(dataSet, cenaCest, zasoby);
        dataSet.setSuperMarkety(superMarkets);
        int[][][] produkce = parsujProdukci(numbers, dataSet);
        Tovarna[] tovarny = vytvorTovarny(dataSet);
        prizazeniProdukceTovane(produkce, tovarny);
        dataSet.setTovarny(tovarny);
        int[][][] poptavka = parsujPoptavku(numbers, dataSet);
        naplnObjednavky(dataSet, poptavka);
        return dataSet;
    }

    private static DataSet vytvorDataSet(List<int[]> numbers) {
        int[] head = numbers.remove(0);
        return new LinkedDataSet(head[0], head[1], head[2], head[3]);
    }

    private static void naplnObjednavky(DataSet dataSet, int[][][] poptavka) {
        for (int zbozi = 0; zbozi < poptavka.length; zbozi++) {
            for (int den = 0; den < poptavka[zbozi].length; den++) {
                for (int supermaket = 0; supermaket < poptavka[zbozi][den].length; supermaket++) {
                    if (poptavka[zbozi][den][supermaket] > 0) {
                        dataSet.addObjednavka(new Objednavka(supermaket, zbozi, poptavka[zbozi][den][supermaket]), den);
                    }
                }
            }
        }
    }

    private static int[][][] parsujPoptavku(List<int[]> numbers, DataSetZakladniInfo dataSet) {
        int[][][] poptavka = new int[dataSet.getPocetZbozi()][dataSet.getPocetDni()][dataSet.getPocetSupermarketu()];
        for (int i = 0; i < poptavka.length; i++) {
            for (int j = 0; j < poptavka[i].length; j++) {
                poptavka[i][j] = numbers.remove(0);
            }
        }
        return poptavka;
    }

    private static void prizazeniProdukceTovane(int[][][] produkce, Tovarna[] tovarny) {
        for (int zbozi = 0; zbozi < produkce.length; zbozi++) {
            for (int den = 0; den < produkce[zbozi].length; den++) {
                for (int tovarna = 0; tovarna < produkce[zbozi][den].length; tovarna++) {
                    tovarny[tovarna].vyroba[den][zbozi] = produkce[zbozi][den][tovarna];
                }
            }
        }
    }

    private static Tovarna[] vytvorTovarny(DataSetZakladniInfo dataSet) {
        Tovarna[] tovarny = new Tovarna[dataSet.getPocetTovarny()];
        for (int i = 0; i < tovarny.length; i++) {
            tovarny[i] = new Tovarna(dataSet.getPocetZbozi(), dataSet.getPocetDni());
        }
        return tovarny;
    }

    private static int[][][] parsujProdukci(List<int[]> numbers, DataSetZakladniInfo dataSet) {
        int[][][] produkce = new int[dataSet.getPocetZbozi()][dataSet.getPocetDni()][dataSet.getPocetSupermarketu()];
        for (int i = 0; i < produkce.length; i++) {
            for (int j = 0; j < produkce[i].length; j++) {
                produkce[i][j] = numbers.remove(0);
            }
        }
        return produkce;
    }

    private static SuperMarket[] vytvorSupermarkety(DataSetZakladniInfo dataSet, int[][] cenaCest, int[][] zasoby) {
        SuperMarket[] superMarkets = new SuperMarket[dataSet.getPocetSupermarketu()];

        for (int i = 0; i < superMarkets.length; i++) {
            int[] cenyCest = new int[dataSet.getPocetTovarny()];
            for (int j = 0; j < cenyCest.length; j++) {
                cenyCest[j] = cenaCest[j][i];
            }
            superMarkets[i] = new SuperMarket(dataSet.getPocetZbozi(), cenyCest);
        }

        for (int i = 0; i < zasoby.length; i++) {
            for (int j = 0; j < zasoby[i].length; j++) {
                superMarkets[j].zasoby[i] = zasoby[i][j];
            }
        }
        return superMarkets;
    }

    private static int[][] parsujZasoby(List<int[]> numbers, DataSetZakladniInfo dataSet) {
        int[][] zasoby = new int[dataSet.getPocetZbozi()][dataSet.getPocetSupermarketu()];
        for (int i = 0; i < zasoby.length; i++) {
            zasoby[i] = numbers.remove(0);
        }
        return zasoby;
    }

    private static int[][] parsujCesty(List<int[]> numbers, DataSetZakladniInfo dataSet) {
        int[][] cenaCest = new int[dataSet.getPocetTovarny()][dataSet.getPocetSupermarketu()];
        for (int i = 0; i < cenaCest.length; i++) {
            cenaCest[i] = numbers.remove(0);
        }
        return cenaCest;
    }
}
