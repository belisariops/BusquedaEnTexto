package textUtilities;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by americo on 04-06-17.
 */
public class textCleaner {

    private String nameResult;
    private BufferedReader file;
    private ArrayList<String> randomWord = null;

    private long lenght;

    public textCleaner(String textname) {
        FileReader aFile;
        try {
            nameResult = textname.split(".")[0] + "Result.txt";
            aFile = new FileReader(textname);
            this.lenght = new File(textname).length();
            this.file = new BufferedReader(aFile);
        } catch (IOException e) {
            nameResult = "";
            System.out.println("Error E/S: " + e);
        }
    }

    public String clean() throws IOException{
        BufferedWriter result = new BufferedWriter(new FileWriter(nameResult));
        String line;
        String cleanedLine;
        ArrayList<String> randomAux = new ArrayList<String>();
        ArrayList<String> wordsAux = new ArrayList<String>();
        randomWord = new ArrayList<String>();

        String[] auxWords;

        int j = 0;


        while((line = this.file.readLine()) != null){
            cleanedLine = line.toLowerCase();

            String original = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
            String originalEl = "!\"#$%&'()*+,-./0123456789:;<=>?@[\\]^_`{|}~¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿÷øƒ–—‘’‚“”„†‡•…‰€™";
            // Cadena de caracteres ASCII que reemplazarán los originales.
            String ascii = "aaaaaaaceeeeiiiionoooooouuuuyy";
            String output = cleanedLine;
            for (int i=0; i<original.length(); i++) {
                // Reemplazamos los caracteres especiales.

                output = output.replace(original.charAt(i), ascii.charAt(i));

            }
            output = cleanedLine;
            for (int i=0; i<originalEl.length(); i++) {
                // Reemplazamos los caracteres especiales.

                output = output.replace(originalEl.charAt(i), (char)32);

            }

            auxWords = cleanedLine.split(" ");

            for(String word : auxWords){
                wordsAux.add(word);
            }



            cleanedLine = cleanedLine.replaceAll(" ", "");

            result.write(cleanedLine);

            randomAux.add(this.getRandomWord(wordsAux));

            j++;
            if(j == 10){

                randomWord.add(this.getRandomWord(randomAux));
                j = 0;
            }
        }

        randomWord.add(this.getRandomWord(randomAux));

        result.close();
        file.close();
        return nameResult;
    }

    private String getRandomWord(ArrayList<String> words){
        Random rand = new Random();
        int whose = rand.nextInt(words.size());

        return words.get(whose);
    }
}
