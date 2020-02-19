package task1;

import java.util.*;
import java.util.stream.Collectors;

//20, 40, 60, 80
public class NucleotidGenerator {

    private static int length = 0;
    private static String[] gcNucleotids = {"G", "C"};
    private static String[] atNucleotids = {"A", "T"};
    private static String[] atgcNuclotids = {"G", "C", "A", "T"};

    private static HashMap<String, String> relations = new HashMap<>();
    private static int gc = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите длину ДНК цепочки, от 100 до 1000:");
        String inputLength = scanner.nextLine();
        while (!(inputLength.matches("[0-9]+"))) {
            System.out.println("Ошибка ввода. Введите длину ДНК цепочки, от 100 до 1000:");
            inputLength = scanner.nextLine();
        }
        System.out.println("Введите ГЦ состав в %, от 20% до 80%, без знака %");
        String inputGC = scanner.nextLine();
        while (!(inputGC.matches("[0-9]+"))) {
            System.out.println("Ошибка ввода. Введите ГЦ состав в %, от 20% до 80%, без знака %");
            inputGC = scanner.nextLine();
        }
        gc = Integer.parseInt(inputGC);
        if (gc < 20 || gc > 80) {
            System.out.println("ГЦ состав " + gc + " не соответствует условиям. Введите ГЦ состав в %, от 20% до 80%, без знака %");
            gc = scanner.nextInt();
        }
        length = Integer.parseInt(inputLength);
        if (length < 100 || length > 1000) {
            System.out.println("Длина " + length + " не соответствует условиям. Введите длину ДНК цепочки, от 100 до 1000:");
            length = scanner.nextInt();
        }
        String generatedChain = generate();
        String relatedChain = findRelatedChain(generatedChain);

        ORFFinder.dnaSequence = generatedChain;
        ORFFinder.isStrength = true;
        ORFFinder.findOrf();
        ORFFinder.dnaSequence = relatedChain;
        ORFFinder.isStrength = false;
        ORFFinder.findOrf();
        ORFFinder.getResults();
    }

    private static String generate() {
        Random random = new Random();
        int gcLength = length * gc / 100;
        ArrayList<String> generatedString = new ArrayList<>();
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

        StringBuilder sb = new StringBuilder();
        for (String s : generatedString) {
            sb.append(s);
        }
        String sbString = sb.toString();
        System.out.println(sbString);
        for(int i = 3; i < sb.length(); i+=4){
            sb.insert(i, ' ');
        }
        System.out.println("Прямая цепочка ДНК: " + sb.toString());
        return sbString;
    }

    private static String findRelatedChain(String chainFirst) {
        relations.put("A", "T");
        relations.put("T", "A");
        relations.put("C", "G");
        relations.put("G", "C");

        ArrayList<String> foundChain = new ArrayList<>();

        for (int i = 0; i < chainFirst.length(); i++) {
            foundChain.add(relations.get(String.valueOf(chainFirst.charAt(i))));
        }
        StringBuilder sb = new StringBuilder();
        for (String s : foundChain) {
            sb.append(s);
        }
        String sbString = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        for(int i = sbString.length()-1; i > 0; i--){
           sb2.append(sbString.charAt(i));
        }
        String sbString2 = sb2.toString();
        for(int i = 3; i < sb2.length(); i+=4){
            sb2.insert(i, ' ');
        }
        System.out.println("Обратная цепочка ДНК: " + sb2.toString());
        System.out.println();
        return sbString2;
    }
}
