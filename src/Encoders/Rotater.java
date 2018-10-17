package Encoders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Rotater {
    //
    public static void main(String args[]) {
        String input;
        System.out.println("Enter a phrase.");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        //
        String[] words = (input.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+"));
        //
        List<List<Character>> characters = new ArrayList<>();
        //
        for (String word : words) {
            List<Character> WordChar = new ArrayList<>();
            //
            for (int n = 0; n < word.length(); n++){
                WordChar.add(word.charAt(n));
            }
            //
            WordChar.add(0, WordChar.get(WordChar.size() - 1));
            WordChar.remove(WordChar.size() - 1);
            //
            characters.add(WordChar);
        }
        //
        characters.get(0).add(0, characters.get(characters.size() - 1).get((characters.get(characters.size() - 1)).size() - 1));
        characters.get(characters.size() - 1).remove(characters.get(characters.size() - 1).size() - 1);
        //
        for (int n = 1; n < characters.size(); n++){
            characters.get(n).add(0, characters.get(n - 1).get(characters.get(n - 1).size() - 1));
            characters.get(n - 1).remove(characters.get(n - 1).size() - 1);
        }
        //
        Random rando = new Random();
        //
        for (List<Character> word : characters){
            String ra = String.valueOf((char)(rando.nextInt(25) + 'a'));
            word.add(0, ra.toCharArray()[0]);
        }
        //
        List<List<Character>> reversed = new ArrayList<>();
        //
        for (List<Character> word : characters){
            List<Character> first = new ArrayList<>();
            for (Character chara : word){
                first.add(0, chara);
            }
            //
            reversed.add(0, first);
        }
        //
        StringBuilder build = new StringBuilder("");
        //
        for (List<Character> word : reversed){
            for (Character chara : word){
                build.append(chara);
            }
        }
        //
        String result = build.toString();
        //
        System.out.println(result);
        //
    }
}
