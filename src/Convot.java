import java.io.*;
import java.net.URL;
import java.util.*;

class Convot {
    //
    public static void main(String args[]) {
        Convot convot = new Convot();
        convot.runConvot();
    }

    public void runConvot(){
        //<editor-fold desc="Start">
        //
        List<String> convs = new ArrayList<>();
        List<String> qs = new ArrayList<>();
        List<String> as = new ArrayList<>();
        List<String> aas = new ArrayList<>();
        List<List<String>> read = new ArrayList<>();
        //
        try {
            read = read();
            convs = read.get(0);
            aas = read.get(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        List<List<String>> twoLists = decode(convs);
        qs = twoLists.get(0);
        as = twoLists.get(1);
        //
        twoLists = saidIWASFINE(as, qs, aas);
        as = twoLists.get(0);
        qs = twoLists.get(1);
        aas = alas(as);
        //
        try {
            write(encode(qs, as), aas);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    qs.add(answer);
                    //
                }else if(answer.equals(qs.get(compare(qs, answer)))){
                    System.out.println(as.get(compare(qs, answer)));
                }
            }
            //
            convs = encode(qs, as);
            //
            try {
                write(convs, aas);
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
            while(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                //
                if(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {//quit?
                    c = compare(qs, input);//find answer
                    System.out.println(as.get(c));//print
                    //
                    if (!(as.get(c).equals(qs.get(compare(qs, as.get(c)))))) {//answer != any question?
                        //
                        scanner = new Scanner(System.in);
                        input = scanner.nextLine();//ask question
                        //
                        if (!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {//!quit?
                            qs.add(as.get(c));
                            as.add(input);
                            //
                            aas.add(input + "×" + (qs.size() - 1));
                        }
                    } else {//old question
                        scanner = new Scanner(System.in);
                        input = scanner.nextLine();
                        //
                        if (!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                            read = rewrite(as, aas, c, input);
                            //
                            as = read.get(0);
                            aas = read.get(1);
                        }
                        //System.out.println("");
                    }
                    //
                }
            }
            //
            convs = encode(qs, as);
            //
            try {
                write(convs, aas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(answer.equals("w")){
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
    public  List<List<String>> read() throws IOException {
        List<List<String>> combined = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("covs.txt");
        File file = new File(filePath.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> covs = new ArrayList<>();
        String line = br.readLine();
        while(line != null){
            covs.add(line);
            line = br.readLine();
        }
        combined.add(covs);
        //
        br = new BufferedReader(new FileReader("C:\\Users\\Eric\\Documents\\CodingTest\\aas.txt"));
        List<String> aas = new ArrayList<>();
        line = br.readLine();
        while(line != null){
            aas.add(line);
            line = br.readLine();
        }
        combined.add(aas);
        //
        return combined;
    }
    //
    public void write(List<String> covs, List<String> aas) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("covs.txt");
        File file = new File(filePath.getFile());
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter(file));
        BufferedWriter backup = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\Backups\\covsB.txt"));
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
        //
        BufferedWriter thingThatWrites1 = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\aas.txt"));
        aas.forEach(string ->{
            try {
                thingThatWrites1.write(string);
                thingThatWrites1.newLine();
            }   catch (IOException e){
                e.printStackTrace();
            }
        });
        //
        System.out.println("File Saved!");
        thingThatWrites1.flush();
        thingThatWrites1.close();
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
        for(int n = 0; n < bqs.size(); n++){
            //
            int val = 0;
            for (int b = 0; b < compare.size(); b++){
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
        List<String[]> both = new ArrayList<>();
        //
        //<editor-fold desc="Organize">
        aas.add(replacement + "×" + replace);
        String[] each;
        //
        for (int n = 0; n < aas.size(); n++){
            each = aas.get(n).split("×");
            both.add(each);//string, as #
        }
        //
        List<String[]> alters = new ArrayList<>();//string, value, as #
        //
        for (int n = 0; n < both.size(); n++){//repeat # of aas
            //<editor-fold desc="repeat?">
            List<Integer> repeat = new ArrayList<>();
            Boolean rp = false;
            //
            for (int c = 0; c < alters.size(); c++){//# of alters
                if (alters.get(c)[0].equals(both.get(n)[0])){//string repeat?
                    repeat.add(c);
                }
            }
            //
            for(int c = 0; c < repeat.size() - 1; c++){//# of repeats
                if (alters.get(repeat.get(c))[2].equals(both.get(n)[1])){//question # repeat?
                    rp = true;
                }
            }
            //</editor-fold>//
            //
            if (rp){//if repeat aas
                Integer a;
                //
                for (a = 0; !alters.get(a)[0].equals(both.get(n)[0]); a++){}//repeat until current aas
                //
                String[] use = new String[]{alters.get(a)[0], Integer.toString(Integer.parseInt(alters.get(a)[1]) + 1), alters.get(a)[2]};
                alters.set(a, use);
                //
            }else {//if new aas
                String num = "0";
                String[] use = new String[]{both.get(n)[0], num, both.get(n)[1]};
                alters.add(use);
                //
            }
        }
        //</editor-fold>
        //
        List<List<String[]>> sorted = new ArrayList<>();
        List<Integer> key = new ArrayList<>();
        //
        List<String[]> temp = new ArrayList<>();
        //
        temp.add(alters.get(0));
        sorted.add(temp);
        key.add(Integer.parseInt(alters.get(0)[2]));
        //
        for (int a = 1; a < alters.size(); a++) {//# of alters
            Integer b;
            Integer ua = Integer.parseInt(alters.get(a)[2]);
            //
            if (key.contains(ua)){
                //
                Integer d;
                for (d = 0; !key.get(d).equals(ua); d++){}
                //
                temp = sorted.get(d);
                //
                for (b = 0; b < (sorted.size()) && isaBoolean(alters, temp, a, b); b++){}//# of sorted or temp v > alters v
                //
                temp.add(b, alters.get(a));
                sorted.add(temp);
                key.add(ua);
                //
            }else{
                temp.clear();
                //
                temp.add(alters.get(a));
                sorted.add(temp);
                key.add(ua);
            }
            //
        }
        //
        Integer d;
        for (d = 0; !key.get(d).equals(replace); d++){}
        temp = sorted.get(d);
        //
        if(temp.get(0)[0] != as.get(replace)){
            as.set(replace, temp.get(0)[0]);
        }
        //
        combined.add(as);
        combined.add(aas);
        //
        return combined;
    }

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
    //
    private static List<String> alas(List<String> as){
        List<String> aas = new ArrayList<>();
        //
        for (int n = 0; n < as.size(); n++){
            aas.add(as.get(n) + "×" + n);
        }
        //
        return aas;
    }
    //
    private static List<List<String>> saidIWASFINE(List<String> as, List<String> qs, List<String> aas){
        for (int n = 0; n < as.size(); n++){
            if (as.get(n).equals("Said I was fine.")){
                as.remove(n);
                qs.remove(n);
            }
        }
        //
        for (int n = 0; n < aas.size(); n++){
            String aasr = aas.get(n).split("×")[0];
            if (aasr.equals("Said I was fine.")){
                aas.remove(n);
            }
        }
        //
        List<List<String>> both = new ArrayList<>();
        both.add(as);
        both.add(qs);
        both.add(aas);
        //
        return both;
    }
}