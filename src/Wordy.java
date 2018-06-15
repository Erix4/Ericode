import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Wordy {
    public static void main(String args[]) {
        List<String> words = new ArrayList<>();
        List<List<Double>> numbers = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        Wordy wordy = new Wordy();
        Long seed = 0L;
        Random randy = new Random(seed);
        //
        for (int n = 0; n < 50; n++){
            weights.add(randy.nextDouble());
        }
        //
        try {
            words = wordy.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        for (int n = 0; n < words.size(); n++){
            List<Double> values = new ArrayList<>();
            //
            for (int a = 0; a < 5; a++){
                char ch = words.get(n).charAt(a);
                double pos = ch - 'a' + 1;
                values.add(pos / 13 - 1);
            }
            //
            numbers.add(values);
        }
        //
        String input;
        System.out.println("Enter a seed");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        //
        seed = Long.parseLong(input);
        System.out.println(generate(randy.nextDouble(), weights));
        //
    }
    //
    public List<String> read() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("words.txt");
        File file = new File(filePath.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> words = new ArrayList<>();
        String line = br.readLine();
        String[] a;
        while(line != null){
            a = (line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+"));
            for(String word :a){ //this is another type of loop statement that will go through every item in your array, designating the item 'word' and performing the task you need to with it
                words.add(word);
            }
            line = br.readLine();
        }
        //
        return words;
    }
    //
    public static List<Double> train(Double input, List<Double> weights, List<Double> answer) {
        List<Integer> inputs = new ArrayList<>();
        List<Double> neurons = new ArrayList<>();
        List<Double> errors = new ArrayList<>();
        Double catchError = 2.0;
        Double catchCatchError = 2.0;
        Double LR = 1.0;
        errors.add(0.0);
        errors.add(0.0);
        errors.add(0.0);

        Double sum = 0.0;
        for (int a = 0; a < 5; a++){
            neurons.add(constrain(input * weights.get(a)));
        }
        //
        for(int b = 0; b < 2; b++){
            for (int c = 0; c < 5; c++){
                Double weighted = 0.0;
                for (int d = 0; d < 5; d++){
                    weighted += neurons.get(d) * weights.get(b * 25 + c * 5 + d);
                }
                neurons.add(constrain(weighted));
            }
        }
        //
        String output = new StringBuilder().append((char)((neurons.get(10) + 1) * 13 + 'a' - 1)).toString();
        for (int c = 0; c < 4; c++) {
            output += ((char)((neurons.get(11 + c) + 1) * 13 + 'a' - 1));
        }
        //
        System.out.print(output);
        //
        //Double error = inputs.get(0) - (output * derivative(output));
        /*Double error = (answer - output);// * derivative(output);*/
        //
        //System.out.println(", " + error);
        //</editor-fold>
        //
        /*while (Math.abs(error) > .001) {
            //
            weights.set(9, weights.get(9) + (LR * error * neurons.get(0)));
            weights.set(10, weights.get(10) + (LR * error * neurons.get(1)));
            weights.set(11, weights.get(11) + (LR * error * neurons.get(2)));
            //
            errors.set(0, LR * weights.get(9) * error * derivative(neurons.get(0)));
            errors.set(1, LR * weights.get(10) * error * derivative(neurons.get(1)));
            errors.set(2, LR * weights.get(11) * error * derivative(neurons.get(2)));
            //
            weights.set(0, weights.get(0) + (LR * errors.get(0) * inputs.get(0)));
            weights.set(1, weights.get(1) + (LR * errors.get(0) * inputs.get(1)));
            weights.set(2, weights.get(2) + (LR * errors.get(0) * inputs.get(2)));
            weights.set(3, weights.get(3) + (LR * errors.get(1) * inputs.get(0)));
            weights.set(4, weights.get(4) + (LR * errors.get(1) * inputs.get(1)));
            weights.set(5, weights.get(5) + (LR * errors.get(1) * inputs.get(2)));
            weights.set(6, weights.get(6) + (LR * errors.get(2) * inputs.get(0)));
            weights.set(7, weights.get(7) + (LR * errors.get(2) * inputs.get(1)));
            weights.set(8, weights.get(8) + (LR * errors.get(2) * inputs.get(2)));
            //
            //<editor-fold desc="Test">
            neurons.add(constrain((inputs.get(0) * weights.get(0)) + (inputs.get(1) * weights.get(1)) + inputs.get(2) * weights.get(2)));
            neurons.add(constrain((inputs.get(0) * weights.get(3)) + (inputs.get(1) * weights.get(4)) + inputs.get(2) * weights.get(5)));
            neurons.add(constrain((inputs.get(0) * weights.get(6)) + (inputs.get(1) * weights.get(7)) + inputs.get(2) * weights.get(8)));
            //
            output = (neurons.get(0) * weights.get(9)) + (neurons.get(1) * weights.get(10)) + (neurons.get(2) * weights.get(11));
            //
            output = constrain(output);
            //
            //System.out.print(output);
            //
            if (catchError.equals(output)){
                System.out.print("Error found. Time buffer...");
                sleep(3000);
            }else if (catchCatchError.equals(output)){
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
            error = LR * (answer - output);// * derivative(output);
            //
            //System.out.println(", " + error);
            //</editor-fold>
        }*/
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
    public static String generate(Double input, List<Double> weights) {
        List<Double> neurons = new ArrayList<>();
        //
        Double sum = 0.0;
        for (int a = 0; a < 5; a++){
            neurons.add(constrain(input * weights.get(a)));
        }
        //
        for(int b = 0; b < 2; b++){
            for (int c = 0; c < 5; c++){
                Double weighted = 0.0;
                for (int d = 0; d < 5; d++){
                    weighted += neurons.get(d) * weights.get(b * 25 + c * 5 + d);
                }
                neurons.add(constrain(weighted));
            }
        }
        //
        String output = new StringBuilder().append((char)((neurons.get(10) + 1) * 13 + 'a' - 1)).toString();
        for (int c = 0; c < 4; c++) {
            output += ((char)((neurons.get(11 + c) + 1) * 13 + 'a' - 1));
        }
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
        for (int n = 0; n < jamesScore - playerScore; n++){
            player += "0";
        }
        /*
        if(jamesScore > 10 && jamesScore > playerScore){
            for (int n = 0; n < jamesScore - 10; n++){
                player += "0";
            }
        }
        */
        build.add(player);
        //
        for (int n = 0; n < jamesScore; n++){
            james += "+";
        }
        //
        for (int n = 0; n < playerScore - jamesScore; n++){
            james += "0";
        }
        /*
        if(playerScore > 10 && playerScore > jamesScore){
            for (int n = 0; n < playerScore - 10; n++){
                james += "0";
            }
        }
        */
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
