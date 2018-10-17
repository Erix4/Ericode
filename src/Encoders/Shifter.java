package Encoders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shifter {
    public static void main(String args[]){
        List<Integer> numbers = new ArrayList<>();
        List<Character> characters = new ArrayList<>();
        //
        String input;
        System.out.println("Enter a phrase.");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        //
        for (int a = 0; a < input.length(); a++){
            char b = input.charAt(a);
            int pos = b - 'a' + 1;// space=-64, .=-50, ?=-33, ,=-52 upper=-31_-6
            numbers.add(pos);
        }
        //
        Integer up = 0;
        Integer down = 0;
        if (numbers.size() > 1) {
            down = numbers.get(1);
        }
        //
        int z = constrain(numbers.get(0) * 2 - up + down);
        characters.add((char)z);
        //
        for (int a = 1; a < numbers.size() - 1; a++){
            //
            up = numbers.get(a - 1);
            down = numbers.get(a + 1);
            //
            z = constrain(numbers.get(0) * 2 - up + down);
            characters.add((char)z);
            //
        }
        //
        up = numbers.get(numbers.size() - 1);
        down = 0;
        //
        if (numbers.size() > 1) {
            z = constrain(numbers.get(0) * 2 - up + down);
            characters.add((char) z);
        }
        //
        StringBuilder d = new StringBuilder();
        for (Character ch: characters){
            d.append(ch);
        }

        String finale = d.toString();
        System.out.println(finale);
    }
    //
    public static Integer constrain (Integer number){
        Integer constrained = 0;
        //
        if (number < 'a'){
            constrained = Math.abs(number - ((int)Math.floor(number / ('z' - 'a')) * ('z' - 'a'))) + 'a';
        } else if (number > 'z'){
            constrained = (number - ((int)Math.floor(number / ('z' - 'a')) * ('z' - 'a'))) + 'a';
        }else{
            constrained = number;
        }
        //
        return constrained;
    }
}
