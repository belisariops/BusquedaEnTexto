package textUtilities;

import java.io.*;

/**
 * Created by americo on 04-06-17.
 */
public class textCleaner {

    private String nameResult;
    private BufferedReader file;

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

        while((line = this.file.readLine()) != null){
            cleanedLine = line.toLowerCase();

            String original = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
            // Cadena de caracteres ASCII que reemplazarán los originales.
            String ascii = "aaaaaaaceeeeiiiionoooooouuuuyy";
            String output = cleanedLine;
            for (int i=0; i<original.length(); i++) {
                // Reemplazamos los caracteres especiales.

                output = output.replace(original.charAt(i), ascii.charAt(i));

            }
            cleanedLine = cleanedLine.replaceAll(" ", "");

            result.write(cleanedLine);
        }

        result.close();
        file.close();
        return nameResult;
    }
}
