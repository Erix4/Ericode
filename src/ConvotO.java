import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class ConvotO {
    //
    public static void main(String args[]) {
        ConvotO convot = new ConvotO();
        convot.runConvot();
    }

    public void runConvot(){
        //<editor-fold desc="Start">
        //
        List<String> convs = new ArrayList<>();
        List<String> qs = new ArrayList<>();
        List<String> as = new ArrayList<>();
        List<List<String>> read = new ArrayList<>();
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
        //aas = alas(as);
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
                if (!answer.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit") && !answer.equals(qs.get(compare(qs, answer)))) {
                    System.out.println("What's the answer?");
                    scanner = new Scanner(System.in);
                    String question = answer;
                    answer = scanner.nextLine();
                    //
                    as.add(answer);
                    qs.add(question);
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

        }//</editor-fold>
        //<editor-fold desc="Talk">
        else if(answer.equals("c")){
            String input = "";
            Integer c = 0;
            //
            System.out.println("Ask me a question.");
            //
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            //
            c = compare(qs, input);
            System.out.println(as.get(c));
            //
            scanner = new Scanner(System.in);
            input = scanner.nextLine();
            //
            if (!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {//!quit?
                qs.add(as.get(c));
                as.add(input);
            }
            //
            while(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                //
                if(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {//quit?
                    c = compare(qs, input);//find answer
                    System.out.println(as.get(c));//print
                    //
                    //
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();//ask question
                    //
                    if (!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {//!quit?
                        qs.add(as.get(c));
                        as.add(input);
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
        }
        //</editor-fold>
        //<editor-fold desc="Watch">
        else if(answer.equals("w")){
            String c1 = "";
            String c2 = "";
            Boolean check = false;
            List<String> record = new ArrayList<>();
            Random r = new Random();
            //
            c1 = qs.get(r.nextInt(as.size() + 1));
            System.out.println("c1: " + c1);
            //
            for (int n = 0; n < 500; n++){
                c2 = as.get(compare(qs, c1));
                System.out.println("c2: " + c2);
                sleep(3000);
                //
                c1 = as.get(compare(qs, c2));
                //
                for (int a = 0; a < record.size(); a++){
                    if (record.get(a).equals(c1)){
                        check = true;
                    }
                }
                //
                if (check){
                    c1 = as.get(r.nextInt(as.size() + 1));
                }
                record.add(c1);
                if(record.size() > 10) {
                    record.remove(0);
                }
                System.out.println("c1: " + c1);
                sleep(3000);
            }
        }
        //</editor-fold>
        //
    }
    //
    public  List<String> read() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("covsO.txt");
        File file = new File(filePath.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> covs = new ArrayList<>();
        String line = br.readLine();
        while(line != null){
            covs.add(line);
            line = br.readLine();
        }
        //
        return covs;
    }
    //
    public void write(List<String> covs) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("covsO.txt");
        File file = new File(filePath.getFile());
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter(file));
        BufferedWriter backup = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\Backups\\covsOB.txt"));
        covs.forEach(string ->{
            try {
                thingThatWrites.write(string);
                thingThatWrites.newLine();
                backup.write(string);
                backup.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thingThatWrites.flush();
        thingThatWrites.close();
        backup.flush();
        backup.close();
    } //There are Backups!
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
        for(int n = 0; n < bqs.size(); n++){//line
            //
            int val = 0;
            for (int b = 0; b < compare.size(); b++){//word
                if ((bqs.get(n).contains(compare.get(b)))){
                    val++;
                }
            }
            if(qs.get(n) == item){
                val =+ 10;
            }
            //
            value.add(val);
            int b = 0;
            for (b = 0;b < sort.size() &&  val < value.get(sort.get(b)); b++){}//find value order
            sort.add(b, n);
        }
        //</editor-fold>
        //
        return (sort.get(0));
    }
    //

    private static boolean isaBoolean(List<String[]> alters, List<String[]> sorted, int a, Integer b) {
        Integer valueOne = Integer.parseInt(alters.get(a)[1]);
        Integer valueTwo = Integer.parseInt(sorted.get(b)[1]);
        return valueOne < valueTwo;
    }

    //
    private static void sleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //
    private static void trim(List<String> qs, List<String> as){
        List<Integer> remove = new ArrayList<>();
        String temp = "";
        //
        for(int n = 0; n < qs.size(); n++){
            temp = qs.get(n);
            qs.set(n, "");
            if(qs.contains(temp)){
                qs.remove(n);
                as.remove(n);
            }else{
                qs.set(n, temp);
            }
        }
    }
}