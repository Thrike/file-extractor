package com.thrike.fileextractor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileMover {

    private String fileFolderSource;
    private String fileFolderDestination;
//    private Scanner userInput = new Scanner(System.in);

    public FileMover(String fileFolderSource, String fileFolderDestination) {
        this.fileFolderSource = fileFolderSource;
        this.fileFolderDestination = fileFolderDestination;
    }

    public void start() {
        try {
            List<String> listOfFilesToTransfer = findFilesInSourceDirectory();
            if(filesAreAvailableForTransfer(listOfFilesToTransfer)){
                transferFiles(listOfFilesToTransfer);
                System.out.println("Files transferred successfully");
            }
        } catch (NoSuchFileException n) {
            System.out.println("Unable to move file. Could not find file: " + n.getFile());
        } catch (IOException e){
            System.out.println(e);
        }
    }

    private void transferFiles(List<String> listOfFilesToTransfer) throws IOException {
        System.out.println("Transferring files to: " +  fileFolderDestination + "\n");
        for(int a = 0; a < listOfFilesToTransfer.size(); a++){
            if(listOfFilesToTransfer.size() > 0){
                Path sourceFileFolderPath = Paths.get(fileFolderSource + listOfFilesToTransfer.get(a));
                Path destinationFileFolderPath = Paths.get(fileFolderDestination + listOfFilesToTransfer.get(a));
                Files.move(sourceFileFolderPath, destinationFileFolderPath);
            }
        }
    }

    private List<String> findFilesInSourceDirectory() {
        File sourceDirectoryToSearch = new File(fileFolderSource);
        List<String> filesPresent = Arrays.asList(sourceDirectoryToSearch.list());
        if(filesPresent.size() == 0){
            System.out.println(String.format("No files found in directory: %s, shutting down... \n", fileFolderSource));
        } else {
            System.out.println(String.format("Found %s files in %s:", filesPresent.size(), fileFolderSource));
            System.out.println("=========================================");
            filesPresent.forEach((file) -> System.out.println(file));
            System.out.println("=========================================");
        }
        return filesPresent;
    }

    private boolean filesAreAvailableForTransfer(List<String> listOfFilesToTransfer){
        boolean filesToTransfer;
        if(listOfFilesToTransfer.size() == 0){
            filesToTransfer = false;
        } else {
            filesToTransfer = true;
        }
        return filesToTransfer;
    }
}
