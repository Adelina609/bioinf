package task1;

import java.util.ArrayList;
import java.util.HashMap;

public class ORFFinder {

    private static String[] stopCodons = {"TAA", "TGA", "TAG"};

    private static String startCodon = "ATG";
    private static String maxOrf = "";
    private static int maxLenOrf = 10;
    public static String dnaSequence;
    public static boolean isStrength;
    public static String isStrengthMax;
    private static ArrayList<String> allOrf = new ArrayList<>();
    private static boolean isEmpty = true;
    private static HashMap<String, String> hashMap = new HashMap<>();

    public static void findOrf() {
        int startOrf = -1;
        int stopOrf = -1;

        for (int i = 0; i < dnaSequence.length() - 3; i += 3) {
            if (getTriplet(i).equals(startCodon)) {
                isEmpty = false;
                startOrf = i;
                String triplet = getTriplet(i + 3);
                while (!(triplet.equals(stopCodons[0]) ||
                        triplet.equals(stopCodons[1]) ||
                        triplet.equals(stopCodons[2]) ||
                        i >= dnaSequence.length() - 3)) {
                    //orf.add(triplet);
                    i += 3;
                    if (i >= dnaSequence.length() - 3) {
                        i -= 3;
                        break;
                    }
                    triplet = getTriplet(i);
                }

                stopOrf = i;
                String orfStr = dnaSequence.substring(startOrf, stopOrf + 3);
                if ((orfStr.substring(orfStr.length() - 3).equals(stopCodons[0]) ||
                        orfStr.substring(orfStr.length() - 3).equals(stopCodons[1]) ||
                        orfStr.substring(orfStr.length() - 3).equals(stopCodons[2]))
                ) {
                    allOrf.add(orfStr);
                    allOrf.add("белок:" + getProtein(orfStr));
                    allOrf.add("длина " + orfStr.length());
                    allOrf.add(getCurrentChain());
                    allOrf.add("начало " + (startOrf + 1));
                    allOrf.add("конец " + (stopOrf + 3));
                    if (orfStr.length() > maxLenOrf) {
                        maxLenOrf = orfStr.length();
                        maxOrf = orfStr;
                        isStrengthMax = getCurrentChain();
                    }
                    startOrf = -1;
                    stopOrf = -1;
                }
            }
        }
    }

    public static void getResults() {
        if (isEmpty || (maxLenOrf == 10)) {
            System.out.println("ОРФ не найдено");
        } else {
            //System.out.println("Все ОРФ: " + splitByOrf(splitStringInList(allOrf)).toString());
            splitByOrf(splitStringInList(allOrf));
            System.out.println("Макс орф: " + splitString(maxOrf) + "\nДлина: " + maxLenOrf + "\nНа цепочке: " + isStrengthMax);
            System.out.println("Оттранслированный: " + splitString(findRelatedChain(maxOrf)));

        }
    }

    private static ArrayList<String> splitByOrf(ArrayList<String> list) {
        System.out.println("Все ОРФ: \n");
        for (int i = 0; i < list.size(); i += 6) {
            System.out.println(list.get(i));
            System.out.println(list.get(i + 1));
            System.out.println(list.get(i + 2));
            System.out.println(list.get(i + 3));
            System.out.println(list.get(i + 4));
            System.out.println(list.get(i + 5));
            System.out.println();
        }
        return list;
    }

    private static ArrayList<String> splitStringInList(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i += 6) {
            String s = list.get(i);
            list.set(i, splitString(s).toString());
        }
        return list;
    }

    private static StringBuilder splitString(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int j = 3; j < sb.length(); j += 4) {
            sb.insert(j, ' ');
        }
        return sb;
    }

    private static String getCurrentChain() {
        return isStrength ? "Прямая" : "Обратная";
    }

    private static String getTriplet(int i) {
        return dnaSequence.substring(i, i + 3);
    }

    private static HashMap<String, String> relations = new HashMap<>();

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
        for (int i = sbString.length() - 1; i > 0; i--) {
            sb2.append(sbString.charAt(i));
        }
        String sbString2 = sb2.toString();
        for (int i = 3; i < sb2.length(); i += 4) {
            sb2.insert(i, ' ');
        }
        return sbString2;
    }

    private static void getTableCodons() {
        String s = "TTT F" + "TCT S" + "TAT Y" +
                "TGT C" +
                "TTC F" +
                "TCC S" +
                "TAC Y" +
                "TGC C" +
                "TTA L" +
                "TCA S" +
                "TAA *" +
                "TGA *" +
                "TTG L" +
                "TCG S" +
                "TAG *" + "TGG W" + "CTT L" + "CCT P" + "CAT H" + "CGT R" + "CTC L" + "CCC P" + "CAC H" + "CGC R" + "CTA L" + "CCA P" + "CAA Q" + "CGA R" + "CTG L" + "CCG P" + "CAG Q" + "CGG R" + "ATT I" + "ACT T" + "AAT N" + "AGT S" + "ATC I" + "ACC T" + "AAC N" + "AGC S" + "ATA I" + "ACA T" + "AAA K" + "AGA R" + "ATG M" + "ACG T" + "AAG K" + "AGG R" + "GTT V" + "GCT A" + "GAT D" + "GGT G" + "GTC V" + "GCC A" + "GAC D" + "GGC G" + "GTA V" + "GCA A" + "GAA E" + "GGA G" + "GTG V" + "GCG A" + "GAG E" + "GGG G";


        for(int i = 0; i < s.length()-4; i+=5){
            hashMap.put(s.substring(i, i+3), s.substring(i+4, i+5));
        }
    }

    public static String getProtein(String s){
        getTableCodons();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length()-3; i+=3){
            sb.append(hashMap.get(s.substring(i, i+3)));
        }
        System.out.println("Транслированный: " + sb.toString());
        return sb.toString();
    }

}
