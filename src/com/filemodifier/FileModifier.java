package com.filemodifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileModifier {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter full path to target directory and press enter:");
            String folderPath = scanner.nextLine();
            System.out.println("Enter name of file to search for and press enter:");
            String fileName = scanner.nextLine();

            List<Path> filePaths = Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(x -> x.getFileName().toString().equals(fileName))
                    .collect(Collectors.toList());
            if (filePaths.isEmpty()) {
                System.out.println(String.format("No files with name %s found.", fileName));
            } else {
                getSearchPatternAndReplace(scanner, filePaths);
            }
        } catch (IOException e) {
            System.out.println("Oops. It seems There is no such directory: " + e.getMessage() + ". Please try again.");
        }
    }

    private static void getSearchPatternAndReplace(Scanner scanner, List<Path> filePaths) {
        System.out.println("Enter regex pattern to look for and press enter:");
        String pattern = scanner.nextLine();
        System.out.println("Enter replacement string and press enter: ");
        String newText = scanner.nextLine();
        for (Path file : filePaths) {
            findAndChangeString(file, pattern, newText);
        }
    }


    private static void findAndChangeString(Path file, String pattern, String newText) {
        try (Stream<String> lines = Files.lines(file)) {
            List<String> modifiedLines = lines.map(x -> x.replaceAll(pattern, newText))
                    .collect(Collectors.toList());
            Files.write(file, modifiedLines);
        } catch (IOException e) {
            System.out.println("Something went wrong while reading and writing to the file. Please try again.");
            e.printStackTrace();
        }
    }
}
