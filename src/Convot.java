import javafx.util.Pair;

import java.io.*;
import java.util.*;

class Convot {
    //
    public static void main(String args[]) {
        //<editor-fold desc="Start">
        //
        List<String> convs = new ArrayList<>();
        List<String> qs = new ArrayList<>();
        List<String> as = new ArrayList<>();
        List<String> aas = new ArrayList<>();
        //
        try {
            convs = read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        List<List<String>> twoLists = decode(convs);
        qs = twoLists.get(0);
        as = twoLists.get(1);
        //
        String answer = "";
        //
        System.out.println("t to train, c to talk, and w to watch two robots talk.");
        while(!answer.equals("t") && !answer.equals("c") && !answer.equals("w")) {
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine();
            if (!answer.equals("t") && !answer.equals("c") && !answer.equals("w")){
                System.out.println("That's not a valid command.");
            }
        }
        //</editor-fold>
        //<editor-fold desc="Train">
        if (answer.equals("t")){
            while(!answer.equals("quit")) {
                System.out.println("Ask me a question.");
                Scanner scanner = new Scanner(System.in);
                answer = scanner.nextLine();
                //
                if (!answer.equals("quit") && !answer.equals(qs.get(compare(qs, answer)))) {
                    qs.add(answer);
                    //
                    System.out.println("What's the answer?");
                    scanner = new Scanner(System.in);
                    answer = scanner.nextLine();
                    //
                    as.add(answer);
                }else if(answer.equals(qs.get(compare(qs, answer)))){
                    System.out.println(as.get(compare(qs, answer)));
                }
            }
            //
            convs = encode(qs, as);
            //
            try {
                write(convs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(answer.equals("c")){
            String input = "";
            Integer c = 0;
            //
            System.out.println(qs.get(0));
            //
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            //
            while(!input.equals("quit")) {
                //
                if(!input.equals("quit")) {
                    c = compare(qs, input);
                    System.out.println(as.get(c));
                    //
                    if (!(as.get(c) == qs.get(compare(qs, as.get(c))))) {
                        //
                        scanner = new Scanner(System.in);
                        input = scanner.nextLine();
                        //
                        if (!input.equals("quit")) {
                            qs.add(as.get(c));
                            as.add(input);
                        }
                    } else {
                        scanner = new Scanner(System.in);
                        input = scanner.nextLine();
                    }
                    //
                }
            }
            //
            convs = encode(qs, as);
            //
            try {
                write(convs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(answer.equals("w")){
            String c1 = "";
            String c2 = "";
            String fix = "";
            Random r = new Random();
            //
            c1 = qs.get(0);
            System.out.println("c1: " + c1);
            //
            for (int n = 0; n < 50; n++){
                c2 = as.get(compare(qs, c1));
                System.out.println("c2: " + c2);
                sleep(3000);
                //
                c1 = as.get(compare(qs, c2));
                if (c1.equals(fix)){
                    c1 = as.get(r.nextInt(as.size() + 1));
                }
                fix = c1;
                System.out.println("c1: " + c1);
                sleep(3000);
            }
        }
        //</editor-fold>
        //
    }
    //
    public static List<String> read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Eric\\Documents\\CodingTest\\covs.txt"));
        List<String> covs = new ArrayList<>();
        String line = br.readLine();
        while(line != null){
            covs.add(line);
            line = br.readLine();
        }
        return covs;
    }
    //
    public static void write(List<String> covs) throws IOException {
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\covs.txt"));
        covs.forEach(string ->{
            try {
                thingThatWrites.write(string);
                thingThatWrites.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("File Saved!");
        thingThatWrites.flush();
        thingThatWrites.close();
    }
    //
    public static List<List<String>> decode(List<String> covs){
        List<String> qs = new ArrayList<>();
        List<String> as = new ArrayList<>();
        //
        for (double n = 0; n < covs.size(); n++){
            if (n/2 == Math.floor(n/2)){
                qs.add(covs.get((int)n));
            }else {
                as.add(covs.get((int)n));
            }
        }
        //
        //<editor-fold desc="Return">
        List<List<String>> combined = new ArrayList<List<String>>();
        combined.add(qs);
        combined.add(as);
        //
        return combined;
        //</editor-fold>
    }
    //
    public static List<String> encode(List<String> qs, List<String> as){
        List<String> covs = new ArrayList<>();
        //
        for(double n = 0; n < as.size()*2; n++){
            if (n/2 == Math.floor(n/2)){
                covs.add(qs.get((int)n/2));
            }else {
                covs.add(as.get((int)(n-1)/2));
            }
        }
        //
        return covs;
    }
    //
    public static Integer compare(List<String> qs, String item){
        //<editor-fold desc="Listing">
        List<List<String>> bqs = new ArrayList<>();
        List<String> compare = new ArrayList<>();
        String[] a = new String[0];
        //
        a = (item.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+"));
        for(String word :a){ //this is another type of loop statement that will go through every item in your array, designating the item 'word' and performing the task you need to with it
            compare.add(word);
        }
        //
        for(int n = 0; n < qs.size(); n++){
            //
            List<String> q = new ArrayList<>();
            String[] s = new String[0];
            //
            Integer nm = 0;
            Integer nmb = 0;
            //
            s = (qs.get(n).toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+"));
            for(String word :s){ //this is another type of loop statement that will go through every item in your array, designating the item 'word' and performing the task you need to with it
                q.add(word);
            }
            //
            bqs.add(q);
        }
        //</editor-fold>
        //
        //<editor-fold desc="Sort">
        List<Integer> value = new ArrayList<>();
        List<Integer> sort = new ArrayList<>();
        //
        for(int n = 0; n < bqs.size(); n++){
            //
            int val = 0;
            for (int b = 0; b < compare.size(); b++){
                if ((bqs.get(n).contains(compare.get(b)))){
                    val++;
                }
            }
            value.add(val);
            int b = 0;
            for (b = 0;b < sort.size() &&  val < value.get(sort.get(b)); b++){}
            sort.add(b, n);
        }
        //</editor-fold>
        //
        return (sort.get(0));
    }
    //
    public static List<List<String>> rewrite(List<String> as, List<String> aas, Integer replace, String replacement){
        List<List<String>> combined = new ArrayList<>();
        //
        if (as.get(replace).equals(replacement)){
            //
        }else if (aas.contains(replacement)){
            as.set(replace, replacement);
        }else{
            aas.add(replacement);
        }
        //
        combined.add(as);
        combined.add(aas);
        //
        return combined;
    }
    //
    private static void sleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}