package com.company;

import java.io.FileReader;
import java.io.IOException;
import algorithms.Automata;
import textUtilities.TextCleaner;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String textPath;
        ArrayList<String> words = new ArrayList<>();
        Automata automata;
        List<Integer> list;

        TextCleaner cleaner = new TextCleaner("english.txt");
        try {
            textPath = cleaner.clean();

            for(int i = 0; i<5; i++){
                words.add(cleaner.getRandom());
            }

            BufferedReader br;

            for(String word : words){
                br = new BufferedReader(new FileReader(textPath));
                automata = new Automata(word);
                list = automata.run(br);
                System.out.println("Repeticiones: "+automata.getCantRepeticiones());
                //System.out.println("Posiciones :");
                for (Integer i: list) {
                    //System.out.println(i);
                }
                br.close();
            }
        } catch(IOException e){
            System.out.println("Error E/S: " + e);
        }
    }
}
