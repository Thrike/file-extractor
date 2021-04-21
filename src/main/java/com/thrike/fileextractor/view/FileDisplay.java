package com.thrike.fileextractor.view;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class FileDisplay {

    private final Scanner userInput = new Scanner(System.in);

    public void displayFilesFoundInDirectory(List<Path> filesPresent) {
        System.out.println("View files (Y/N)?");
        String userDisplayFilesChoice = userInput.nextLine();

        if (userDisplayFilesChoice.equalsIgnoreCase("Y")) {
            System.out.println("=========================================");
            filesPresent.forEach(System.out::println);
            System.out.println("=========================================");
        }
    }
}
