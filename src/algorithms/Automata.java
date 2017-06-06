package algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by belisariops on 6/4/17.
 */
public class Automata {
    private int[][] matrix;
    private int numStates;
    private int cantRepeticiones;

    public Automata(String pattern) {
        numStates = pattern.length();
        /*Con 26 el tamaño del alfabeto (a-z)*/
        matrix = new int[26][numStates+1];
        int i,c;

        /*Transiciones, si el caracter es el correcto*/
        for (i=0;i < numStates; i++) {
            matrix[(int)pattern.charAt(i)-97][i] = i+1;
        }

        /*Transiciones incorrectas del primer caracter del patron*/
        for (c=0;c<26;c++) {
            if ((char)c+97 != pattern.charAt(0)) {
                matrix[c][0] = 0;
            }
        }

        int x =0;
        /*Transiciones incorrectas de los demas caracteres del patron*/
        for (i=1;i <numStates; i++) {
            for (c = 0; c < 26; c++) {
                if ((char) c + 97 != pattern.charAt(i)) {
                    matrix[c][i] = matrix[c][x];
                }

            }
            x = matrix[pattern.charAt(i) - 97][x];
        }

        /*Transiciones del estado terminal*/
        for (int k=0; k<26;k++) {
            matrix[k][numStates] = matrix[k][x];
        }

    }

    public List<Integer> run(BufferedReader buffer) throws IOException {

        String line;
        int i;
        int index=0;
        int currentState=0;
        cantRepeticiones=0;
        char c;
        ArrayList<Integer> match = new ArrayList<Integer>();
        while ( (line= buffer.readLine())!= null) {
            for (i=0;i<line.length();i++) {
                c = line.charAt(i);
                currentState = this.matrix[((int)c)-97][currentState];
                if (currentState == numStates) {
                    cantRepeticiones++;
                    match.add(index-numStates+1);
                }
                index++;
            }
        }


        return match;
    }

    public int getCantRepeticiones() {
        return cantRepeticiones;
    }

}
