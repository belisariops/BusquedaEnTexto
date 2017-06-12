package com.company;

import java.io.*;

import algorithms.Automata;
import textUtilities.TextCleaner;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String textPath;
        Automata automata;
        List<Integer> list;
        BufferedWriter resultExperiment;
        BufferedWriter resultIntersecion;

        ArrayList<String> listaArchivos = new ArrayList<>();
        listaArchivos.add("english50MB.txt");
        listaArchivos.add("english100MB.txt");
        listaArchivos.add("english200MB.txt");
        listaArchivos.add("english1024MB.txt");
        listaArchivos.add("english.txt");

        TextCleaner cleaner;
        try {
            for(String archivo: listaArchivos) {
                cleaner = new TextCleaner(archivo);

                textPath = cleaner.clean();

                resultExperiment = new BufferedWriter(new FileWriter("Experiment" + textPath));
                resultIntersecion = new BufferedWriter(new FileWriter("Intersecion" + textPath));

                ArrayList<String> words = cleaner.getNextRandom();

                BufferedReader br;

                StringBuilder builder;

                long fromTime;
                int j = 0;

                for (String word : words) {

                    builder = new StringBuilder();

                    builder.append(j);
                    builder.append(",");

                    br = new BufferedReader(new FileReader(textPath));

                    fromTime = System.nanoTime();

                    automata = new Automata(word);

                    builder.append(fromTime - System.nanoTime());
                    builder.append(",");
                    fromTime = System.nanoTime();

                    list = automata.run(br);

                    builder.append(fromTime - System.nanoTime());
                    builder.append(System.lineSeparator());

                    resultExperiment.write(builder.toString());

                    resultIntersecion.write("Repeticiones: " + automata.getCantRepeticiones() + System.lineSeparator());
                    resultIntersecion.write("Posiciones :"+ System.lineSeparator());
                    for (int i : list) {
                        resultIntersecion.write(i+System.lineSeparator());
                    }
                    br.close();
                }

                resultExperiment.close();
                resultIntersecion.close();

            }
        } catch(IOException e){
            System.out.println("Error E/S: " + e);
        }
    }
}
