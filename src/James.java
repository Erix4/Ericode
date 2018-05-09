import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class James {
        public static void main(String args[]){
            //
            List<Double> weights = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();
            List<String> holdString = new ArrayList<>();
            String input1;
            Double imperfectGuess = 0.0;
            Integer guess = 0;
            Integer playerScore = 0;
            Integer jamesScore = 0;
            //
            Random randy = new Random();
            //
            for (int n = 0; n < 6; n++){
                weights.add(randy.nextDouble());
            }
            //
            System.out.println("Enter random numbers, 0 or 1");
            Scanner scanner = new Scanner(System.in);
            for (int n = 0; n < 2; n++) {
                input1 = scanner.nextLine();
                if (input1.equals("0") || input1.equals("1")) {
                    numbers.add(Integer.parseInt(input1));
                } else {
                    System.out.println(input1 + " is not a valid number");
                }
            }
            //
            System.out.println("I will now try to guess your input");
            System.out.println("Enter score at any time for the score");
            for(int n = 0; n < 50; n++) {
                    imperfectGuess = guess(numbers, weights);
                    if (imperfectGuess > 0){
                        guess = 1;
                    }else{
                        guess = 0;
                    }
                    //
                    input1 = scanner.nextLine();
                    //
                    if (input1.equals("0") || input1.equals("1") || input1.equals("score")) {
                        if (input1.equals("score")){
                            holdString = build(playerScore, jamesScore);
                            System.out.println(holdString.get(0));
                            System.out.println(holdString.get(1));
                            System.out.println(holdString.get(2));
                        }else {
                            //
                            System.out.println(guess + ", " + imperfectGuess);
                            //
                            if (input1.equals(Integer.toString(guess))) {
                                jamesScore++;
                                System.out.println("I win!");
                            } else {
                                playerScore++;
                                System.out.println("You win!");
                                weights = train(numbers, weights, Integer.parseInt(input1));
                                numbers.add(Integer.parseInt(input1));
                            }
                        }
                    } else {
                        System.out.println(input1 + " is not a valid number");
                    }
                    //
            }
            //
        }
        //
        public static List<Double> train(List<Integer> numbers, List<Double> weights, Integer answer) {
            List<Integer> inputs = new ArrayList<>();
            List<Double> neurons = new ArrayList<>();
            List<Double> errors = new ArrayList<>();
            Double catchError = 2.0;
            Double catchCatchError = 2.0;
            Double LR = 1.0;
            errors.add(0.0);
            errors.add(0.0);
            //
            inputs.add(numbers.get(numbers.size() - 2) * 2 - 1);
            inputs.add(numbers.get(numbers.size() - 1) * 2 - 1);
            //
            //<editor-fold desc="Test">
            neurons.add(constrain((inputs.get(0) * weights.get(0)) + (inputs.get(1) * weights.get(1))));
            neurons.add(constrain((inputs.get(0) * weights.get(2)) + (inputs.get(1) * weights.get(3))));
            //
            Double output = (neurons.get(0) * weights.get(4)) + neurons.get(1) * weights.get(5);
            //
            output = constrain(output);
            //
            //System.out.print(output);
            //
            //Double error = inputs.get(0) - (output * derivative(output));
            Double error = (answer - output) * derivative(output);
            //
            //System.out.println(", " + error);
            //</editor-fold>
            //
            while (Math.abs(error) > .001) {
                //
                weights.set(4, weights.get(4) + (LR * error * neurons.get(0)));
                weights.set(5, weights.get(5) + (LR * error * neurons.get(1)));
                //
                errors.set(0, LR * weights.get(4) * error * derivative(neurons.get(0)));
                errors.set(1, LR * weights.get(5) * error * derivative(neurons.get(1)));
                //
                weights.set(0, weights.get(0) + (LR * errors.get(0) * inputs.get(0)));
                weights.set(1, weights.get(1) + (LR * errors.get(0) * inputs.get(1)));
                weights.set(2, weights.get(2) + (LR * errors.get(1) * inputs.get(0)));
                weights.set(3, weights.get(3) + (LR * errors.get(1) * inputs.get(1)));
                //
                //<editor-fold desc="Test">
                neurons.set(0, constrain((inputs.get(0) * weights.get(0)) + (inputs.get(1) * weights.get(1))));
                neurons.set(1, constrain((inputs.get(0) * weights.get(2)) + (inputs.get(1) * weights.get(3))));
                //
                output = (neurons.get(0) * weights.get(4)) + neurons.get(1) * weights.get(5);
                //
                output = constrain(output);
                //
                //System.out.print(output);
                //
                if (catchError.equals(output)){
                    System.out.print("Error found. Time buffer...");
                    sleep(3000);
                }
                //
                if (catchCatchError.equals(output)){
                    System.out.print("Secondary error found. Time buffer...");
                    LR = LR * .5;
                    sleep(3000);
                }
                //
                catchCatchError = catchError;
                //
                catchError = output;
                //
                //System.out.println(derivative(output));
                //
                error = LR * (answer - output) * derivative(output);
                //
                //System.out.println(", " + error);
                //</editor-fold>
            }
            //
            return weights;
        }
        //
        public static Double constrain(Double input){
            if(input >= 1){
                input = 1.0;
            }else if (input <= -1){
                input = -1.0;
            }
            return input;
        }
        //
        public static Double derivative(Double input){
            input = input * (1 - input);
            return input;
        }
        //
    public static Double guess(List<Integer> number, List<Double> weights) {
        List<Integer> inputs = new ArrayList<>();
        List<Double> neurons = new ArrayList<>();
        //
        inputs.add(number.get(number.size() - 2));
        inputs.add(number.get(number.size() - 1));
        //
        neurons.add(constrain((inputs.get(0) * weights.get(0)) + (inputs.get(1) * weights.get(1))));
        neurons.add(constrain((inputs.get(0) * weights.get(2)) + (inputs.get(1) * weights.get(3))));
        //
        Double output = (neurons.get(0) * weights.get(4)) + neurons.get(1) * weights.get(5);
        //
        output = constrain(output);
        //
        return output;
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
    public static List<String> build(Integer playerScore, Integer jamesScore){
            List<String> build = new ArrayList<>();
            String player = "Player Score: ";
            String james = "My Score:     ";
            String stats = "";
            //
        for (int n = 0; n < playerScore; n++){
            player += "+";
        }
        //
        for (int n = 0; n < 10 - playerScore; n++){
            player += "0";
        }
        //
        if(jamesScore > 10 && jamesScore > playerScore){
            for (int n = 0; n < jamesScore - 10; n++){
                player += "0";
            }
        }
        //
        build.add(player);
        //
        for (int n = 0; n < jamesScore; n++){
            james += "+";
        }
        //
        for (int n = 0; n < 10 - jamesScore; n++){
            james += "0";
        }
        //
        if(playerScore > 10 && playerScore > jamesScore){
            for (int n = 0; n < playerScore - 10; n++){
                james += "0";
            }
        }
        //
        build.add(james);
        //
        stats = playerScore + " to " + jamesScore + ", ";
        if (playerScore > jamesScore){
            stats += "You're winning!";
        }else {
            stats += "I'm winning!";
        }
        build.add(stats);
            //
            return build;
    }
}
