package textUtilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class textCleaner {

    private String nameResult;
    private String randomResult;
    private BufferedReader file;
    private ArrayList<String> randomWord = null;

    private int count = 0;

    public textCleaner(String textname) {
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
        StringBuilder auxLine;
        ArrayList<String> randomAux = new ArrayList<>();
        ArrayList<String> wordsAux = new ArrayList<>();
        randomWord = new ArrayList<>();

        String[] auxWords;

        int j = 0;
        count = 0;


        while((line = this.file.readLine()) != null){
            cleanedLine = line.toLowerCase();

            String original = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
            String originalEl = "!\"#$%&'()*+,-\\./0123456789:;<=>?@[\\]^_`{|}~¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿÷øƒ–—‘’‚“”„†‡•…‰€™ß„";
            // Cadena de caracteres ASCII que reemplazarán los originales.
            String ascii = "aaaaaaaceeeeiiiionoooooouuuuyy";
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
            }



            cleanedLine = cleanedLine.replaceAll(" ", "");

            result.write(cleanedLine);

            randomAux.add(this.getRandomWord(wordsAux));

            j++;
            if(j == 10){

                randomWord.add(this.getRandomWord(randomAux));
                j = 0;
            }
            if(randomWord.size()>32){
                auxLine = new StringBuilder();
                for (String str: randomWord) {
                    if(!str.equals("")){
                        auxLine.append(";");
                        auxLine.append(str);
                    }
                }
                randResult.write(auxLine.toString());
                count++;
                randomWord = new ArrayList<>();
            }
        }

        randomWord.add(this.getRandomWord(randomAux));

        randResult.close();
        result.close();
        file.close();
        return nameResult;
    }

    private String getRandomWord(ArrayList<String> words){
        Random rand = new Random();
        int whose = rand.nextInt(words.size());

        return words.get(whose);
    }

    public String getRandom(){
        Random rand = new Random();
        int whose;
        if(randomWord.size() == 0){
            whose = rand.nextInt(count);
        }else{
            whose = rand.nextInt(count+1);
        }
        if(count == 0){
            if(randomWord.size() == 0){
                return "";
            }else{
                return this.getRandomWord(randomWord);
            }
        }else{
            if(whose == count+1){
                return this.getRandomWord(randomWord);
            }else{
                try {
                    FileReader auxFile = new FileReader(randomResult);
                    BufferedReader reader = new BufferedReader(auxFile);
                    String line;
                    int k = 0;
                    while ((line = reader.readLine()) != null) {
                        if (k == whose) {
                            ArrayList<String> val = new ArrayList<>();
                            for (String pal : line.split(":")) {
                                val.add(pal);
                            }
                            return this.getRandomWord(val);
                        } else {
                            k++;
                        }
                    }
                }
                catch(IOException e){
                    System.out.println("Error E/S: " + e);
                }
            }
        }
        return "";
    }
}
