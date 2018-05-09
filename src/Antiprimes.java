import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Antiprimes {
    public static void main(String args[]) {
        //
        Double ACheck;
        Integer score;
        Double antiPrime = 3.0;
        //
        List<Double> primes = new ArrayList<>();
        List<Double> antiPrimes = new ArrayList<>();
        List<Integer> apScore = new ArrayList<>();
        //
        primes.add(2.0);
        antiPrimes.add(2.0);
        apScore.add(2);
        //
        System.out.println(2);
        //
        String answer = "";
        //
        while(!answer.toLowerCase().replaceAll("[^a-zA-Z ]", "").equals("quit")) {
            //
            score = 0;
            ACheck = 1.0;
            //
            for(ACheck = 1.0; ACheck <= antiPrime; ACheck++){
                if(antiPrime / ACheck == Math.round(antiPrime / ACheck)){
                    score++;
                }
            }
            //
            if(score > apScore.get(apScore.size() - 1)){
                antiPrimes.add(antiPrime);
                apScore.add(score);
                //
                DecimalFormat format = new DecimalFormat("0.#");
                System.out.println(format.format(antiPrime) + ", " + score);
            }
            //
            antiPrime++;
            //
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine();
        }
        //
        List<String> aPD = new ArrayList<String>();
        for (Double d : antiPrimes) {
            // Apply formatting to the string if necessary
            aPD.add(d.toString());
        }
        //
        try {
            write(aPD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    public static Double newPrime(List<Double> primes){
        Double PCheck = primes.get(primes.size() - 1) + 1;
        //
        int n = 0;
        //
        for (PCheck = PCheck; (n != primes.size()); PCheck++){
        //
            for (n = 0; n < primes.size() && (PCheck / primes.get(n) != Math.round(PCheck / primes.get(n))); n++) {}
        }
        //
        return PCheck - 1;
    }
    //
    public static void write(List<String> antiprimes) throws IOException {
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\antiPrimes.txt"));
        antiprimes.forEach(string ->{
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
    public static List<Double> read() throws IOException {
        //
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Eric\\Documents\\CodingTest\\antiPrimes.txt"));
        List<String> antiPrimes = new ArrayList<>();
        String line = br.readLine();
        while(line != null){
            antiPrimes.add(line);
            line = br.readLine();
        }
        //
        List<Double> aPS = new ArrayList<Double>();
        for (String d : antiPrimes) {
            // Apply formatting to the string if necessary
            aPS.add(Double.parseDouble(d));
        }
        //
        return aPS;
    }
}