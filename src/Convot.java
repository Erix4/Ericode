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
                if (!answer.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit") && !answer.equals(qs.get(compare(qs, answer)))) {
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
            while(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                //
                if(!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                    c = compare(qs, input);
                    System.out.println(as.get(c));
                    //
                    if (!(as.get(c).equals(qs.get(compare(qs, as.get(c)))))) {
                        //
                        scanner = new Scanner(System.in);
                        input = scanner.nextLine();
                        //
                        if (!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                            qs.add(as.get(c));
                            as.add(input);
                        }
                    } else {
                        scanner = new Scanner(System.in);
                        input = scanner.nextLine();
                        //
                        if (!input.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
                            rewrite(as, aas, c, input);
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
                write(convs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(answer.equals("w")){
            String c1 = "";
            String c2 = "";
            String fix = "";
            String fix2 = "";
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
                if (c1.equals(fix) || c1.equals(fix2)){
                    c1 = as.get(r.nextInt(as.size() + 1));
                }
                fix = c1;
                fix2 =fix;
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
        URL filePath = classLoader.getResource("test.txt");
        File file = new File(filePath.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> covs = new ArrayList<>();
        String line = br.readLine();
        while(line != null){
            covs.add(line);
            line = br.readLine();
        }
        return covs;
    }
    //
    public void write(List<String> covs) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("test.txt");
        File file = new File(filePath.getFile());
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter(file));
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
        String[] each = new String[0];
        //
        //<editor-fold desc="Organize">
        aas.add(replacement + "," + replace);
        //
        for (int n = 0; n < aas.size(); n++){
            each = aas.get(0).split(",");
            both.add(each);
        }
        //
        List<String[]> alters = new ArrayList<>();
        List<Integer> value = new ArrayList<>();
        for (int n = 0; n < aas.size(); n++){
            String check = (both.get(n)[1]);
            if (alters.contains(both.get(n)[0])){
                for (int a = 0; alters.get(a)[0] != both.get(n)[0]; a++){
                    value.set(Integer.parseInt(alters.get(a)[1]), value.get(Integer.parseInt(alters.get(a)[1]) + 1));
                }
            }else {
                value.add(0);
                String[] use = new String[]{both.get(n)[0], Integer.toString(value.size())};
                alters.add(use);
            }
        }
        //</editor-fold>
        //
        List<String[]> sorted = new ArrayList<>();
        for (int a = 0; a < alters.size(); a++) {
            Integer b;
            for (b = 0; b < (sorted.size() - 1) && isaBoolean(alters, value, sorted, a, b); b++){}
            //
            sorted.add(b, alters.get(a));
        }
        //
        if(sorted.get(0)[0] != as.get(replace)){
            as.set(replace, sorted.get(0)[0]);
        }
        //
        combined.add(as);
        combined.add(aas);
        //
        return combined;
    }

    private static boolean isaBoolean(List<String[]> alters, List<Integer> value, List<String[]> sorted, int a, Integer b) {
        Integer valueToGet = Integer.parseInt(alters.get(a)[1]);
        Integer valueOne =  value.get(valueToGet);
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