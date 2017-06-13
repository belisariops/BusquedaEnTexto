package textUtilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/*Esta clase implementa funcionalidades para limpiar y extraer las palabras random del texto dado un nombre de archivo*/
public class TextCleaner {

    private String nameResult;
    private BufferedReader file;
    private ArrayList<String> randomWord = null;


    /*El contuctor toma un nombre de archivo y crea los archivos de salida agregando la terminacion Result al nombre original*/
    public TextCleaner(String textname) {
        FileReader aFile;
        try {
            String[] local = textname.split("\\.");
            nameResult = local[0] + "Result.txt";
            aFile = new FileReader(textname);
            this.file = new BufferedReader(aFile);
        } catch (IOException e) {
            nameResult = "";
            System.out.println("Error E/S: " + e);
        }
    }

    /*Llamado a clean ejecuta la limpieza del texto, eliminando acentos y caracteres no esperados, a su vez obtiene las palabras aleatorias
antes de eliminar los espacios seleccionando una de cada 10 de estas, retorna el nombre de archivo donde se coloco el resultado. */
    public String clean() throws IOException{
        BufferedWriter result = new BufferedWriter(new FileWriter(nameResult));
        String line;
        String cleanedLine;
        ArrayList<String> wordsAux = new ArrayList<>();
        randomWord = new ArrayList<>();

        String[] auxWords;

        // Para cada una de las lineas del texto

        while((line = this.file.readLine()) != null){
            cleanedLine = line.toLowerCase();

            String original = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
            String originalEl = "!\"#$%&'()*+,-\\./0123456789:;<=>?@[\\]^_`{|}~\u007F\t\u0015œ\u001E\u001F\u001D\f\u0019\u0014\u001D\u001C\u001D\u000E\u0007\u001B\u0016";
            // Cadena de caracteres ASCII que reemplazarán los originales.
            String ascii = "aaaaaaaceeeeiiiionoooooouuuuyy";

            cleanedLine = cleanedLine.replaceAll("[^\\x00-\\x7F]", "");
            String output = cleanedLine;
            for (int i=0; i<original.length(); i++) {
                // Reemplazamos los caracteres especiales.

                output = output.replace(original.charAt(i), ascii.charAt(i));

            }

            for (int i=0; i<originalEl.length(); i++) {
                // Reemplazamos los caracteres especiales.

                output = output.replace(originalEl.charAt(i), (char)32);

            }

            cleanedLine = output;

            //separo la linea ya limpiada en cada espacio

            auxWords = cleanedLine.split(" ");

            //cada palabra la agrego a una lista, cuado esta llega a 10 saco un elemento aleatorio y vacio la lista.

            for(String word : auxWords){
                wordsAux.add(word);
                if(wordsAux.size()>=10){
                    randomWord.add(this.getRandomWord(wordsAux));
                    wordsAux = new ArrayList<>();
                }
            }


            cleanedLine = cleanedLine.replaceAll(" ", "");

            result.write(cleanedLine+System.lineSeparator());
        }


        result.close();
        file.close();
        return nameResult;
    }


    //Selecciona un String de una lista de forma aleatoria.
    private String getRandomWord(ArrayList<String> words){
        Random rand = new Random();
        if(words.size() == 0){
            return "";
        }
        int whose = rand.nextInt(words.size());

        return words.get(whose);
    }

    // Devuelve la lista de valores random.
    public ArrayList<String> getNextRandom(){
        return randomWord;
    }
}
