package parser;

import data.DataSet;
import data.Objednavka;
import data.SuperMarket;
import data.Tovarna;

import java.io.*;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
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
        return new DataSet(head);
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

    private static int[][][] parsujPoptavku(List<int[]> numbers, DataSet dataSet) {
        int[][][] poptavka = new int[dataSet.Z][dataSet.T][dataSet.S];
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

    private static Tovarna[] vytvorTovarny(DataSet dataSet) {
        Tovarna[] tovarny = new Tovarna[dataSet.D];
        for (int i = 0; i < tovarny.length; i++) {
            tovarny[i] = new Tovarna(dataSet.Z, dataSet.T);
        }
        return tovarny;
    }

    private static int[][][] parsujProdukci(List<int[]> numbers, DataSet dataSet) {
        int[][][] produkce = new int[dataSet.Z][dataSet.T][dataSet.S];
        for (int i = 0; i < produkce.length; i++) {
            for (int j = 0; j < produkce[i].length; j++) {
                produkce[i][j] = numbers.remove(0);
            }
        }
        return produkce;
    }

    private static SuperMarket[] vytvorSupermarkety(DataSet dataSet, int[][] cenaCest, int[][] zasoby) {
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
        return superMarkets;
    }

    private static int[][] parsujZasoby(List<int[]> numbers, DataSet dataSet) {
        int[][] zasoby = new int[dataSet.Z][dataSet.S];
        for (int i = 0; i < zasoby.length; i++) {
            zasoby[i] = numbers.remove(0);
        }
        return zasoby;
    }

    private static int[][] parsujCesty(List<int[]> numbers, DataSet dataSet) {
        int[][] cenaCest = new int[dataSet.D][dataSet.S];
        for (int i = 0; i < cenaCest.length; i++) {
            cenaCest[i] = numbers.remove(0);
        }
        return cenaCest;
    }
}
