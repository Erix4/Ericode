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
        for (int n = 0; n < 8; n++){
            weights.add(randy.nextDouble());
        }
        //
        for(int n = 0; n < 50; n++) {
            System.out.println("Enter 0 or 1:");
            Scanner scanner = new Scanner(System.in);
            input1 = scanner.nextLine();
            if (input1.equals("0") || input1.equals("1")) {
                System.out.println("Accepted: " + input1);
                train(Integer.parseInt(input1), weights);
            } else {
                System.out.println(input1 + " is not a valid number");
            }
        }
        //
    }
    //
    public static void train(int input, List<Double> weights){
        List<Integer> inputs = new ArrayList<>();
        List<Double> neurons = new ArrayList<>();
        //
        inputs.add(0);
        inputs.add(0);
        inputs.set(input, 1);
        //
        for(int n = 0; n < 4; n++) {
            neurons.add(0.0);
        }
        //
        //System.out.println("Starting weights: " + weights);
        //
        double sum = inputs.get(0) * weights.get(0);
        sum =+ inputs.get(1) * weights.get(1);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(0, sum);
        //
        sum = inputs.get(0) * weights.get(2);
        sum =+ inputs.get(1) * weights.get(3);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(1, sum);
        //
        sum = neurons.get(0) * weights.get(4);
        sum =+ neurons.get(1) * weights.get(5);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(2, sum);
        //
        sum = neurons.get(0) * weights.get(6);
        sum =+ neurons.get(1) * weights.get(7);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(3, sum);
        //
        //System.out.println();
        if (neurons.get(2) > neurons.get(3)){
            System.out.println("Result: 0");
        }else {
            System.out.println("Result: 1");
        }
        //
        System.out.println("Training...");
        //
        List<Double> derivatives = new ArrayList<>();
        //1
        double neuron = neurons.get(2);
        sum = neuron * (1 - neuron) *(neuron - (input * -1 +1));
        derivatives.add(sum);
        neuron = neurons.get(0);
        sum = sum * neuron;
        weights.set(4, weights.get(4) + sum);
        //2
        neuron = neurons.get(2);
        sum = neuron * (1 - neuron) *(neuron - (input * -1 +1));
        derivatives.add(sum);
        neuron = neurons.get(1);
        sum = sum * -neuron;
        weights.set(5, weights.get(5) + sum);
        //3
        neuron = neurons.get(3);
        sum = neuron * (1 - neuron) *(neuron - (input * -1 +1));
        derivatives.add(sum);
        neuron = neurons.get(0);
        sum = sum * -neuron;
        weights.set(6, weights.get(6) + sum);
        //
        neuron = neurons.get(3);
        sum = neuron * (1 - neuron) *(neuron - (input * -1 +1));
        derivatives.add(sum);
        neuron = neurons.get(1);
        sum = sum * -neuron;
        weights.set(7, weights.get(7) + sum);
        //5
        neuron = neurons.get(0);
        sum = neuron * (1 - neuron) *((derivatives.get(0) * weights.get(4)) + (derivatives.get(1) * weights.get(5)));
        neuron = inputs.get(0);
        sum = sum * -neuron;
        weights.set(0, weights.get(0) + sum);
        //6
        neuron = neurons.get(0);
        sum = neuron * (1 - neuron) *((derivatives.get(0) * weights.get(4)) + (derivatives.get(1) * weights.get(5)));
        neuron = inputs.get(1);
        sum = sum * -neuron;
        weights.set(1, weights.get(1) + sum);
        //7
        neuron = neurons.get(1);
        sum = neuron * (1 - neuron) *((derivatives.get(2) * weights.get(6)) + (derivatives.get(3) * weights.get(7)));
        neuron = inputs.get(0);
        sum = sum * -neuron;
        weights.set(2, weights.get(2) + sum);
        //8
        neuron = neurons.get(1);
        sum = neuron * (1 - neuron) *((derivatives.get(2) * weights.get(6)) + (derivatives.get(3) * weights.get(7)));
        neuron = inputs.get(1);
        sum = sum * -neuron;
        weights.set(3, weights.get(3) + sum);
        //
        System.out.println("End weights: " + weights);
        //
        sum = inputs.get(0) * weights.get(0);
        sum =+ inputs.get(1) * weights.get(1);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(0, sum);
        //
        sum = inputs.get(0) * weights.get(2);
        sum =+ inputs.get(1) * weights.get(3);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(1, sum);
        //
        sum = neurons.get(0) * weights.get(4);
        sum =+ neurons.get(1) * weights.get(5);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(2, sum);
        //
        sum = neurons.get(0) * weights.get(6);
        sum =+ neurons.get(1) * weights.get(7);
        sum = 1/(1 + Math.pow(Math.E, -sum));
        neurons.set(3, sum);
        //
        if (neurons.get(2) > neurons.get(3)){
            System.out.println("Result: 0");
        }else {
            System.out.println("Result: 1");
        }
    }
}
