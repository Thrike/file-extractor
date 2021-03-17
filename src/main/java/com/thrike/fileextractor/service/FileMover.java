package com.thrike.fileextractor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMover {

    private String fileFolderSource;
    private String fileFolderDestination;
    private String test;

    public FileMover(String fileFolderSource, String fileFolderDestination, String test) {
        this.fileFolderSource = fileFolderSource;
        this.fileFolderDestination = fileFolderDestination;
        this.test = test;
    }

    public void move() {
        try {
            findFilesInSourceDirectory();
            transferFiles();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void transferFiles() throws IOException {
        Path sourceFileFolderPath = Paths.get(fileFolderSource);
        Path destinationFileFolderPath = Paths.get(fileFolderDestination);
        System.out.println("Transferring files from: " + sourceFileFolderPath.toString() + " to " +  destinationFileFolderPath.toString());

        Path transferFiles = Files.move(sourceFileFolderPath, destinationFileFolderPath);
        if (transferFiles != null) {
            System.out.println("Files moved successfully!");
        } else {
            System.out.println("Error during file transfer");
        }
    }

    private void findFilesInSourceDirectory() {
        File sourceDirectoryToSearch = new File(test);
        String filesPresent[] = sourceDirectoryToSearch.list();
        System.out.println(String.format("Found %s files:", filesPresent.length));
        System.out.println("=========================================");
        for (int i = 0; i < filesPresent.length; i++) {
            System.out.println(filesPresent[i]);
        }
        System.out.println("=========================================");
    }
}
