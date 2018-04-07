import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
        while(antiPrime < 10000000000.0) {
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
                System.out.println(format.format(antiPrime));
            }
            //
            antiPrime++;
        }
        //
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
}

