package com.company;

import java.io.IOException;
import algorithms.Automata;
import textUtilities.TextCleaner;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here

        TextCleaner cleaner = new TextCleaner("english200MB.txt");
        try {
            cleaner.clean();
        } catch(IOException e){
            System.out.println("Error E/S: " + e);
        }

        System.out.print(cleaner.getRandom()+System.lineSeparator());
        System.out.print(cleaner.getRandom()+System.lineSeparator());
        System.out.print(cleaner.getRandom()+System.lineSeparator());
        System.out.print(cleaner.getRandom()+System.lineSeparator());
        System.out.print(cleaner.getRandom()+System.lineSeparator());
        System.out.print(cleaner.getRandom()+System.lineSeparator());


        System.out.println((int)'a');
        int [][] a;
        a = new int[2][2];
        int b =0;
        for (int i=0;i<2;i++)
            for (int j=0;j<2;j++) {
                a[i][j] = b;
                b++;
            }
        for (int i=0;i<2;i++)
            for (int j=0;j<2;j++) {
                System.out.println(a[i][j]);
            }

        String t = "banana";
        String p = "ana";
        BufferedReader br = new BufferedReader(new StringReader(t));
        Automata automata = new Automata(p);
        List<Integer> list = null;
        try {
            list = automata.run(br);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Repeticiones: "+automata.getCantRepeticiones());
        System.out.println("Posiciones :");
        for (Integer i: list) {
            System.out.println(i);
        }
    }
}
