package com.company;

import Automata.Automata;
import SuffixArray.SuffixArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
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

        String t = "banananana";
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
        StringBuilder s = new StringBuilder("bananananananananana");
        SuffixArray array = new SuffixArray(s);
        System.out.println(array.findPattern("ana"));


    }
}
