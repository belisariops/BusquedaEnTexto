package com.company;

import textUtilities.textCleaner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here

        textCleaner cleaner = new textCleaner("english200MB.txt");
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
    }
}
