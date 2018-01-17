package com.filemodifier;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter full path to target directory and press enter:");
            String folderPath = scanner.nextLine();
            System.out.println("Enter name of file to search for and press enter:");
            String fileName = scanner.nextLine();
            System.out.println("Enter regex pattern to look for and press enter:");
            String pattern = scanner.nextLine();
            System.out.println("Enter replacement string and press enter: ");
            String newText = scanner.nextLine();

            FileModifier fileModifier = new FileModifier();
            fileModifier.execute(folderPath, fileName, pattern, newText);
        } catch (IOException e) {
            System.out.println("Oops. It seems There is no such directory: " + e.getMessage() + ". Please try again.");
        }
    }
}
