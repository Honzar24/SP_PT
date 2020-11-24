import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Třída pro spuštění generátoru dat
 *
 */
public class Generovani {
    static Random rand = new Random();
    /**
     * Počet továren
     */
    static int d;
    /**
     * počet supermarketů
     */
    static int s;
    /**
     * Počet zboží
     */
    static int z;
    /**
     * Počet dní běhu simulace
     */
    static int t;

    /**
     * Funkce generující číslo normálním rozdělením v intervalu 0 - max.
     * Využívá metodu nextGaussian třídy Random a ověřuje jestli generované číslo náleží do intervalu.
     * @param max maximální hodnota, kterou může číslo nabývat
     * @return generované číslo
     */
    public static int generuj_cislo(int max){
        int in = (int) (rand.nextGaussian()*(max / 4) + (max / 2));
        while (in > max || in < 0){
            in =(int) (rand.nextGaussian()*(max / 4) + (max / 2));
        }
        return in;
    }

    /**
     * Funkce zapisující hlavičku dat do souboru.
     * @param writer sloužící k zapisování
     * @throws IOException při chybě při zapisování
     */
    public static void zapis_hlavicku(BufferedWriter writer) throws IOException {
        writer.write("# Data pro semestralni praci KIV/PT 2020/2021\n\n");
        writer.write("# BLOK: pocet tovaren D, pocet supermarketu S, pocet druhu zbozi Z, pocet dni T\n");
        writer.write(d + " " + s + " " + z + " " + t + "\n");
    }

    /**
     * Funkce zapisuje pole čísel do souboru jako matici
     * @param writer sloužící k zapisování
     * @param data pole čísel určených k zápisu
     * @param del počet čísel v jednom řádku
     * @throws IOException
     */
    public static void zapis_matici(BufferedWriter writer, int[] data, int del) throws IOException {
        for (int i = 0; i < data.length; i++) {
            writer.write(data[i] + " ");
            if ((i + 1) % del == 0) {
                writer.write("\n");
            }
        }
    }

    /**
     * Funkce vytváří pole hodnot s použitím funkce generuj_cislo()
     * @param size velikost pole
     * @param max maximální hodnota
     * @return
     */
    public static int[] generuj_pole(int size, int max){
        int[] ints = new int[size];
        for(int i = 0; i < ints.length; i++){
            ints[i] = generuj_cislo(max);
        }
        return ints;
    }

	 
    public static void generuj(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            System.out.println("Generuji random data do souboru " + fileName);
            d = generuj_cislo(49) + 1;
            s = generuj_cislo(49) + 1;
            z = generuj_cislo(99) + 1;
            t = generuj_cislo(59) + 1;
            zapis_hlavicku(writer);
            writer.write("\n# BLOK: Cena prevozu jednoho zbozi c_{s,d}\n");
            zapis_matici(writer, generuj_pole(s * d, 5), s);
            writer.write("\n# BLOK: Pocatecni skladove zasoby q_{z,s}\n");
            zapis_matici(writer, generuj_pole(z*s, 100), s);
            writer.write("\n# BLOK: Produkce tovaren p_{d,z,t}\n");
            zapis_matici(writer, generuj_pole(d*z*t, 100), d);
            writer.write("\n# BLOK: Poptavka zbozi r_{s,z,t}\n");
            zapis_matici(writer, generuj_pole(s*z*t, 100), s);

        } catch (Exception e) {
            System.err.println("Chyba při zapisování do souboru.");
        }
    }
}
