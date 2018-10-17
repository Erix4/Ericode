import java.io.*;
import java.net.URL;
import java.util.*;

class Convot1 {
    //
    public static void main(String args[]) {
        Convot1 convot = new Convot1();
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
        //aas = alas(as);
        as = twoLists.get(0);
        qs = twoLists.get(1);
        //
        try {
            write(encode(qs, as), aas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        String answer = "";
        //
        System.out.println("t to train, c to talk, r to reset, and w to watch two robots talk.");
        while(!answer.equals("t") && !answer.equals("c") && !answer.equals("w") && !answer.equals("r")) {
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine();
            if (!answer.equals("t") && !answer.equals("c") && !answer.equals("w") && !answer.equals("r")){
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
                    qs.add(question);
                    as.add(answer);
                    aas.add(answer + "×" + (qs.size() - 1));
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

        }//</editor-fold>
        //<editor-fold desc="Talk">
        else if(answer.equals("c")){
            String input = "";
            Integer c = 0;
            //
            System.out.println(qs.get(0));
            //
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            //
            read = rewrite(as, aas, c, input);
            //
            as = read.get(0);
            aas = read.get(1);
            //
            while(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                //
                if(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {//quit?
                    c = compare(qs, input);//find answer
                    if (c == -1){
                        System.out.println("What's that mean?");
                        //
                        String input1 = input;
                        //
                        scanner = new Scanner(System.in);
                        input = scanner.nextLine();//ask question
                        //
                        qs.add(input1);
                        as.add(input);
                        aas.add(input + "×" + (qs.size() - 1));
                        //
                    }else {
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
            }
            //
            convs = encode(qs, as);
            //
            try {
                write(convs, aas);
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
                if (compare(qs, c1) == -1){
                    c2 = as.get(r.nextInt(as.size() + 1));
                }else {
                    c2 = as.get(compare(qs, c1));
                }
                System.out.println("c2: " + c2);
                sleep(3000);
                //
                if (compare(qs, c2) == -1){
                    c1 = as.get(r.nextInt(as.size() + 1));
                }else {
                    c1 = as.get(compare(qs, c2));
                }
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
        else if (answer.equals("r")){
            as.clear();
            qs.clear();
            aas.clear();
            as.add("Good.");
            qs.add("How are you?");
            aas.add("Good.×0");
            try {
                write(encode(qs, as),aas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //
    }
    //
    public  List<List<String>> read() throws IOException {
        //
        List<List<String>> combined = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("covs2.txt");
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
        br = new BufferedReader(new FileReader("C:\\Users\\Eric\\Documents\\CodingTest\\aas2.txt"));
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
        URL filePath = classLoader.getResource("covs2.txt");
        File file = new File(filePath.getFile());
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter(file));
        BufferedWriter backup = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\Backups\\covsB2.txt"));
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
        BufferedWriter thingThatWrites1 = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\aas2.txt"));
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
        a = (item.toLowerCase().replaceAll("[^a-zA-Z0-9' ]", "").split("\\s+"));
        for(String word :a){ //this is another type of loop statement that will go through every item in your array, designating the item 'word' and performing the task you need to with it
            compare.add(word);
        }
        //
        for(int n = 0; n < qs.size(); n++){
            //
            List<String> q = new ArrayList<>();
            String[] s = new String[0];
            //
            s = (qs.get(n).toLowerCase().replaceAll("[^a-zA-Z0-9' ]", "").split("\\s+"));
            Collections.addAll(q, s);
            //
            bqs.add(q);
        }
        //</editor-fold>
        //
        //<editor-fold desc="Sort">
        List<Integer> value = new ArrayList<>();
        List<Integer> sort = new ArrayList<>();
        //
        boolean good = false;
        //
        for(int n = 0; n < bqs.size(); n++){
            //
            int words = 0;
            int chara = 0;
            int charm = 0;
            int charo = 0;
            boolean bonus = false;
            //
            for (String word:compare) {//initial compare
                if ((bqs.get(n).contains(word))){
                    words++;
                    chara += word.length();
                    good = true;
                    bonus = true;
                }else{
                    //charm += compare.get(b).length();
                }
            }
            //
            for (String word:bqs.get(n)){//compare others
                if (!compare.contains(word)){
                    charo += word.length();
                }
            }
            //
            chara *= words;
            chara -= charm;
            chara -= charo;
            //
            if (bonus){
                chara += 30;
            }
            //
            value.add(chara);
            int b = 0;
            for (b = 0;b < sort.size() &&  chara < value.get(sort.get(b)); b++){}
            sort.add(b, n);
        }
        //</editor-fold>
        //
        if (good) {
            return (sort.get(0));
        }else{
            return -1;
        }
    }
    //
    public static List<List<String>> rewrite(List<String> as, List<String> aas, Integer replace, String replacement){
        List<List<String>> combined = new ArrayList<>();
        List<String[]> both = new ArrayList<>();//string, as #
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
        List<Integer> key = new ArrayList<>();//
        List<List<String[]>> questions = new ArrayList<>();//questions<alters<string, value>>
        //
        while (both.size() > 0){//organize alters in question list
            //
            if (key.contains(Integer.parseInt(both.get(0)[1]))){//is there a list for current question?
                int list;
                for (list = 0; !Integer.toString(list).equals(both.get(0)[1]); list++){}//look for  list
                //
                int alter;
                for (alter = 0; alter < questions.get(list).size() && !questions.get(list).get(alter)[0].equals((both.get(0))[0]); alter++){}
                // /\--Check if one question has an identical alter
                //
                if (alter != questions.get(list).size()){//found identical
                    //
                    String[] atler = new String[]{both.get(0)[0], Integer.toString(Integer.parseInt(questions.get(list).get(alter)[1]) + 1)};
                    //
                    questions.get(list).set(alter, atler);//set alter of question to itself with value + 1
                }else {//new alter
                    //
                    String[] atler = new String[]{both.get(0)[0], "1"};
                    //
                    questions.get(list).add(atler);//add new alter to question
                }
                //
                both.remove(0);
                //
            }else{//new question
                //
                String[] atler = new String[]{both.get(0)[0], "1"};
                List<String[]> question = new ArrayList<>();
                question.add(atler);
                //
                questions.add(question);
                key.add(Integer.parseInt(both.get(0)[1]));
                //
                both.remove(0);
            }
        }
        //</editor-fold>
        //
        for (int a = 0; a < questions.size(); a++){//sort each question
            //
            Collections.sort(questions.get(a), new Comparator<String[]>() {//sort each question by value
                public int compare(String[] s1, String s2[]) {
                    int i1 = Integer.parseInt(s1[1]);
                    int i2 = Integer.parseInt(s2[1]);
                    return Integer.compare(i2, i1);
                }
            });
            //
            if (!questions.get(a).get(0)[0].equals(as.get(key.get(a)))){//answer has changed
                as.set(key.get(a), questions.get(a).get(0)[0]);//update answer
            }
            //
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