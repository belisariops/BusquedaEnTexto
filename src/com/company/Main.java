package com.company;

import java.io.*;

import algorithms.Automata;
import algorithms.SuffixArray;
import textUtilities.TextCleaner;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        /*Se inicializan las variables para la ejecusion de los experimentos, BrufferedWriter son los distintos archivos para los resultados de los
        * experimentos, list es la lista de intersecciones, tetPAth es el archivo utilizado en el momento*/

        String textPath;
        Automata automata;
        List<Integer> list;
        BufferedWriter resultExperiment;
        BufferedWriter resultExperimentArray;
        BufferedWriter resultIntersecion;

        ArrayList<String> listaArchivos = new ArrayList<>();
        listaArchivos.add("128m.txt");
        listaArchivos.add("256m.txt");

        TextCleaner cleaner;
        try {

            // Para cada archivo a analizar
            for(String archivo: listaArchivos) {

                //Se limpia, donde se extraen los caracteres que no se consideraron, y se obtienen las palabras aleatorias del texto
                cleaner = new TextCleaner(archivo);

                textPath = cleaner.clean();

                // Se crean los sitintos archivos de resultado.

                resultExperiment = new BufferedWriter(new FileWriter("Experiment" + textPath));
                resultExperimentArray = new BufferedWriter(new FileWriter("ExperimentArray" + textPath));
                resultIntersecion = new BufferedWriter(new FileWriter("Intersecion" + textPath));

                // Se obtiene la lista de palabras aleatorias

                ArrayList<String> words = cleaner.getNextRandom();

                BufferedReader br;

                StringBuilder builder;
                StringBuilder builderArray;

                // Tiempo de contruccion del SuffixArray.

                long firstTime;

                // Variable apra calcular el tiempo desde donde se calcula el actual.

                long fromTime;


                // Se crea un StringBuilder como parametro para el SuffixArray
                br = new BufferedReader(new FileReader(textPath));
                StringBuilder text = new StringBuilder();
                String line;
                while((line = br.readLine())!=null){
                    text.append(line);
                }
                br.close();

                // Se genera el SuffixArray para el texto
                fromTime = System.nanoTime();

                SuffixArray array = new SuffixArray(text);

                firstTime = System.nanoTime()-fromTime;

                int j = 0;

                // Para cada una de las palabras aleatorias

                for (String word : words) {
                    if (j == 125){
                        break;
                    }

                    if(!word.equals("")) {

                        builderArray = new StringBuilder();

                        builder = new StringBuilder();

                        builderArray.append(j);
                        builder.append(j++);
                        builder.append(",");
                        builderArray.append(",");

                        builderArray.append(firstTime);
                        builderArray.append(",");

                        // Se busca utilizando el array
                        fromTime = System.nanoTime();

                        array.findPattern(word);

                        builderArray.append(System.nanoTime() - fromTime);
                        builderArray.append(System.lineSeparator());

                        resultExperimentArray.write(builderArray.toString());

                        br = new BufferedReader(new FileReader(textPath));

                        // Se crea un automata para la palabra.
                        fromTime = System.nanoTime();

                        automata = new Automata(word);

                        builder.append(System.nanoTime() - fromTime);
                        builder.append(",");

                        // Se utiliza el automata sobre el texto.
                        fromTime = System.nanoTime();

                        list = automata.run(br);

                        builder.append(System.nanoTime() - fromTime);
                        builder.append(System.lineSeparator());

                        resultExperiment.write(builder.toString());

                        // Se escriben los resultados en el archivo de intersecciones
                        resultIntersecion.write("Repeticiones: " + automata.getCantRepeticiones() + System.lineSeparator());
                        resultIntersecion.write("Posiciones :" + System.lineSeparator());
                        for (int i : list) {
                            resultIntersecion.write(i + System.lineSeparator());
                        }
                        br.close();
                    }
                }

                resultExperiment.close();
                resultExperimentArray.close();
                resultIntersecion.close();



            }
        } catch(IOException e){
            System.out.println("Error E/S: " + e);
        }
    }
}
