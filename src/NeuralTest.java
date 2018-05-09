import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NeuralTest {
    //
    public static void main(String args[]){
        //
        List<Double> weights = new ArrayList<>();
        String input1;
        String input2;
        //
        Random randy = new Random();
        //
        for (int n = 0; n < 6; n++){
            weights.add(randy.nextDouble());
        }
        //

        //
        for(int n = 0; n < 50; n++) {
            System.out.println("Enter 0 or 1:");
            Scanner scanner = new Scanner(System.in);
            input1 = scanner.nextLine();
            if (input1.equals("0") || input1.equals("1")) {
                System.out.println("Accepted: " + input1);
                weights = train(Integer.parseInt(input1), weights);
            } else {
                System.out.println(input1 + " is not a valid number");
            }
        }
        //
    }
    //
    public static List<Double> train(int input, List<Double> weights) {
        List<Integer> inputs = new ArrayList<>();
        List<Double> neurons = new ArrayList<>();
        List<Double> errors = new ArrayList<>();
        errors.add(0.0);
        errors.add(0.0);
        //
        if(input == 1){
            inputs.add(0);
            inputs.add(1);
        }else {
            inputs.add(1);
            inputs.add(0);
        }
        //
        //<editor-fold desc="Test">
        neurons.add(constrain((inputs.get(0) * weights.get(0)) + (inputs.get(1) * weights.get(1))));
        neurons.add(constrain((inputs.get(0) * weights.get(2)) + (inputs.get(1) * weights.get(3))));
        //
        Double output = (neurons.get(0) * weights.get(4)) + neurons.get(1) * weights.get(5);
        //
        output = constrain(output);
        //
        System.out.print(output);
        //
        //Double error = inputs.get(0) - (output * derivative(output));
        Double error = inputs.get(0) - output;
        //
        System.out.println(", " + error);
        //</editor-fold>
        //
        while (Math.abs(error) > .001) {
            //
            weights.set(4, weights.get(4) + (error * neurons.get(0)));
            weights.set(5, weights.get(5) + (error * neurons.get(1)));
            //
            errors.set(0, weights.get(4) * error * derivative(neurons.get(0)));
            errors.set(1, weights.get(5) * error * derivative(neurons.get(1)));
            //
            weights.set(0, weights.get(0) + (errors.get(0) * inputs.get(0)));
            weights.set(1, weights.get(1) + (errors.get(0) * inputs.get(1)));
            weights.set(2, weights.get(2) + (errors.get(1) * inputs.get(0)));
            weights.set(3, weights.get(3) + (errors.get(1) * inputs.get(1)));
            //
            //<editor-fold desc="Test">
            neurons.set(0, constrain((inputs.get(0) * weights.get(0)) + (inputs.get(1) * weights.get(1))));
            neurons.set(1, constrain((inputs.get(0) * weights.get(2)) + (inputs.get(1) * weights.get(3))));
            //
            output = (neurons.get(0) * weights.get(4)) + neurons.get(1) * weights.get(5);
            //
            output = constrain(output);
            //
            System.out.print(output);
            //
            //System.out.println(derivative(output));
            //
            error = inputs.get(0) - output;
            //
            System.out.println(", " + error);
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
}
