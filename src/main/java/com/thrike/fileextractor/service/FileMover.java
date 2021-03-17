package com.thrike.fileextractor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMover {

    private String fileFolderSource;
    private String fileFolderDestination;

    public FileMover(String fileFolderSource, String fileFolderDestination) {
        this.fileFolderSource = fileFolderSource;
        this.fileFolderDestination = fileFolderDestination;
    }

    public void move() {
        try {
            findFilesInSourceDirectory();
            transferPictures();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void transferPictures() throws IOException {
        Path sourceFileFolderPath = Paths.get(fileFolderSource);
        Path destinationFileFolderPath = Paths.get(fileFolderDestination);
        Path temp = Files.move(sourceFileFolderPath, destinationFileFolderPath);

        if (temp != null) {
            System.out.println("Files moved successfully!");
        } else {
            System.out.println("Error during file transfer");
        }
    }

    private void findFilesInSourceDirectory() {
        File sourceDirectoryToSearch = new File(fileFolderSource);
        String filesPresent[] = sourceDirectoryToSearch.list();
        int fileCount = filesPresent.length;
        System.out.println(String.format("Found %s files:", fileCount));
        System.out.println("=========================================");
        for (int i = 0; i < filesPresent.length; i++) {
            System.out.println(filesPresent[i]);
        }
        System.out.println("=========================================");
    }
}
