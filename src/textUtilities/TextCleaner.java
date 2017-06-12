package textUtilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class TextCleaner {

    private String nameResult;
    private String randomResult;
    private BufferedReader file;
    private ArrayList<String> randomWord = null;

    public TextCleaner(String textname) {
        FileReader aFile;
        try {
            String[] local = textname.split("\\.");
            nameResult = local[0] + "Result.txt";
            randomResult = local[0] + "RandomResult.txt";
            aFile = new FileReader(textname);
            this.file = new BufferedReader(aFile);
        } catch (IOException e) {
            nameResult = "";
            randomResult = "";
            System.out.println("Error E/S: " + e);
        }
    }

    public String clean() throws IOException{
        BufferedWriter result = new BufferedWriter(new FileWriter(nameResult));
        BufferedWriter randResult = new BufferedWriter(new FileWriter(randomResult));
        String line;
        String cleanedLine;
        ArrayList<String> wordsAux = new ArrayList<>();
        randomWord = new ArrayList<>();

        String[] auxWords;

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

            auxWords = cleanedLine.split(" ");

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


        randResult.close();
        result.close();
        file.close();
        return nameResult;
    }

    private String getRandomWord(ArrayList<String> words){
        Random rand = new Random();
        if(words.size() == 0){
            return "";
        }
        int whose = rand.nextInt(words.size());

        return words.get(whose);
    }

    public ArrayList<String> getNextRandom(){
        return randomWord;
    }
}
