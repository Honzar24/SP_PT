import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Generovani {
    static Random rand = new Random();
    static int d = rand.nextInt(50) + 1;
    static int s = rand.nextInt(50) + 1;
    static int z = rand.nextInt(100) + 1;
    static int t = rand.nextInt(60) + 1;


    public static void zapis_hlavicku(BufferedWriter writer) throws IOException {
        writer.write("# Data pro semestralni praci KIV/PT 2020/2021\n\n");
        writer.write("# BLOK: pocet tovaren D, pocet supermarketu S, pocet druhu zbozi Z, pocet dni T\n");
        writer.write(d + " " + s + " " + z + " " + t + "\n");
    }

    public static void zapis_matici(BufferedWriter writer, int[] data, int del) throws IOException {

        for(int i = 0; i < data.length; i++){
            writer.write(data[i] + " ");
            if((i+1) % del == 0){
                writer.write("\n");
            }
        }
    }

    public static int[] gen_prevoz(){
        int[] prevoz = new int[s * d];
        for(int i = 0; i < prevoz.length; i++){
            int in = (int) (rand.nextGaussian() + 3);
            while (in > 5 || in < 0){
                in =(int) (rand.nextGaussian() + 3);
            }
            prevoz[i] = in;
        }
        return prevoz;
    }

    public static int[] gen(int size){
        int[] ints = new int[size];
        for(int i = 0; i < ints.length; i++){
            int in = (int) (rand.nextGaussian()*20 + 50);
            while (in > 100 || in < 0){
                in =(int) (rand.nextGaussian()*15 + 50);
            }
            ints[i] = in;
        }
        return ints;
    }

    public static void main(String[] args) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            zapis_hlavicku(writer);
            writer.write("\n# BLOK: Cena prevozu jednoho zbozi c_{s,d}\n");
            zapis_matici(writer, gen_prevoz(), s);
            writer.write("\n# BLOK: Pocatecni skladove zasoby q_{z,s}\n");
            zapis_matici(writer, gen(z*s), z);
            writer.write("\n# BLOK: Produkce tovaren p_{d,z,t}\n");
            zapis_matici(writer, gen(d*z*t), d);
            writer.write("\n# BLOK: Poptavka zbozi r_{s,z,t}\n");
            zapis_matici(writer, gen(s*z*t), s);

        } catch (Exception e) {
            System.err.println("Chyba při zapisování do souboru.");
        }
    }
}