package com.filemodifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileModifier {

    private static List<Path> getFilePaths(String folderPath, String fileName) throws IOException {
        return Files.walk(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .filter(x -> x.getFileName().toString().equals(fileName))
                .collect(Collectors.toList());
    }

    private static void replaceString(List<Path> filePaths, String pattern, String newText) {
        for (Path file : filePaths) {
            findAndChangeString(file, pattern, newText);
        }
    }

    public void execute(String folderPath, String fileName, String pattern, String newText) throws IOException {
        List<Path> filePaths = getFilePaths(folderPath, fileName);
        if (filePaths.isEmpty()) {
            System.out.println(String.format("No files with name %s found.", fileName));
        } else {
            replaceString(filePaths, pattern, newText);
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
