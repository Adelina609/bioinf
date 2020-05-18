package task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//20, 40, 60, 80
public class NucleotidGenerator {

    private static int length = 1000;
    private static String[] gcNucleotids = {"G", "C"};
    private static String[] atNucleotids = {"A", "T"};
    private static String[] atgcNuclotids = {"G", "C", "A", "T"};

    private static HashMap<String, String> relations = new HashMap<>();
    private static int gc = 0;

    public static void main(String[] args) throws IOException {
        relations.put("A", "T");
        relations.put("T", "A");
        relations.put("C", "G");
        relations.put("G", "C");

        for (int j = 20; j < 81; j++) {
            gc = j;
            isGCEnded = false;
            for (int i = 0; i < 10000; i++) {
                String generatedChain = generate();
                //String relatedChain = findRelatedChain(generatedChain);
                ORFFinder.dnaSequence = generatedChain;
                ORFFinder.isStrength = true;
                ORFFinder.findOrf();
                if (ORFFinder.getResults()) {
                    successes++;
                }
            }
            isGCEnded = true;
            collectResults(j);
        }
        fileWriter.flush();
        fileWriter.close();
    }

    private static boolean isGCEnded = false;
    private static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter("results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int successes = 0;

    private static void collectResults(int gc) throws IOException {
        if (isGCEnded) {
            String gcS = "" + gc;
            String probability = "" + (successes/100);
            fileWriter.append(gcS);
            fileWriter.append(",");
            fileWriter.append(probability);
            fileWriter.append("\n");
            successes = 0;
        }
    }

    private static Random random = new Random();

    private static String generate() {
        int gcLength = length * gc / 100;
        ArrayList<String> generatedString = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gcLength / 2; i++) {
            generatedString.add(gcNucleotids[0]);
            generatedString.add(gcNucleotids[1]);
        }
        for (int i = 0; i < (length - gcLength) / 2; i++) {
            generatedString.add(atNucleotids[0]);
            generatedString.add(atNucleotids[1]);
        }
        if (generatedString.size() != length) {
            for (int i = 0; i <= length - generatedString.size(); i++) {
                int index = random.nextInt(4);
                generatedString.add(atgcNuclotids[index]);
            }
        }
        Collections.shuffle(generatedString);

        for (String s : generatedString) {
            sb.append(s);
        }
        return sb.toString();
    }

    private static ArrayList<String> foundChain = new ArrayList<>();

    private static String findRelatedChain(String chainFirst) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chainFirst.length(); i++) {
            sb.append(relations.get(String.valueOf(chainFirst.charAt(i))));
        }
        return sb.toString();
    }
}
