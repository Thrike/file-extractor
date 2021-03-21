package com.thrike.fileextractor.service;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileMover {

    private String fileFolderSource;
    private String fileFolderDestination;
    private Scanner userInput = new Scanner(System.in);

    public FileMover() {
    }

    public void start() {
        try {
            fileFolderSource = setSourceOrDestinationDirectory("source");
            fileFolderDestination = setSourceOrDestinationDirectory("destination");
            List<File> listOfFilesToTransfer = findFilesInSourceDirectory();
            boolean userConfirmedFileTransfer = confirmFileTransfer();
            if(filesAreAvailableForTransfer(listOfFilesToTransfer) && userConfirmedFileTransfer && fileFolderDestination != null){
                transferFiles(listOfFilesToTransfer);
                System.out.println("Files transferred successfully");
            }
        } catch (NoSuchFileException n) {
            System.out.println("Unable to move file. Could not find file: " + n.getFile());
        } catch (IOException e){
            System.out.println(e);
        }
    }

    private void transferFiles(List<File> listOfFilesToTransfer) throws IOException {
        System.out.println("Transferring files to: " +  fileFolderDestination + "\n");
        for(int a = 0; a < listOfFilesToTransfer.size(); a++){
            if(listOfFilesToTransfer.size() > 0){
                Path sourceFileFolderPath = Paths.get(fileFolderSource + listOfFilesToTransfer.get(a).getName());
                System.out.println(sourceFileFolderPath);
                Path destinationFileFolderPath = Paths.get(fileFolderDestination + listOfFilesToTransfer.get(a).getName());
                System.out.println(destinationFileFolderPath);
                Files.move(sourceFileFolderPath, destinationFileFolderPath);
            }
        }
    }

    private List<File> findFilesInSourceDirectory() {
        File sourceDirectoryToSearch = new File(fileFolderSource);
        List<File> filesPresent = new ArrayList<>();
        List<File> filesToProcess = Arrays.asList(sourceDirectoryToSearch.listFiles());
        for(File file : filesToProcess){
            if(file.isFile()){
                filesPresent.add(file);
            } else {
                System.out.println("Not a file, must be searched");
            }
        }
        System.out.println(sourceDirectoryToSearch.isDirectory());
        if(filesPresent.size() == 0){
            System.out.println(String.format("No files found in directory: %s, shutting down... \n", fileFolderSource));
        } else {
            System.out.println(String.format("Found %s files in %s:", filesPresent.size(), fileFolderSource));
            displayFilesFoundInDirectory(filesPresent);
        }
        return filesPresent;
    }

    private boolean filesAreAvailableForTransfer(List<File> listOfFilesToTransfer){
        boolean filesToTransfer;
        if(listOfFilesToTransfer.size() == 0){
            filesToTransfer = false;
        } else {
            filesToTransfer = true;
        }
        return filesToTransfer;
    }

    private boolean confirmFileTransfer(){
        boolean userConfirmedFileTransfer = false;
        System.out.println(String.format("\nTransfer all above files with the following configuration? (Y/N) \n" +
                "Source: " + "[" + fileFolderSource  + "]" + " -->  " + "Destination: " + "[" + fileFolderDestination + "]\n"));
        String userConfirmationChoice = userInput.nextLine();
        if(userConfirmationChoice.equalsIgnoreCase("Y")){
            userConfirmedFileTransfer = true;
        } else if(userConfirmationChoice.equalsIgnoreCase("N")){
            userConfirmedFileTransfer = false;
            System.out.println("Aborting file transfer...");
        } else {
            System.out.println("\nPlease enter 'Y' or 'N'");
            start();
        }
        return userConfirmedFileTransfer;
    }

    private void displayFilesFoundInDirectory(List<File> filesPresent){
        System.out.println("View files (Y/N)?");
        String userDisplayFilesChoice = userInput.nextLine();
        if(userDisplayFilesChoice.equalsIgnoreCase("Y")){
            System.out.println("=========================================");
            filesPresent.forEach((file) -> System.out.println(file));
            System.out.println("=========================================");
        } else {
            System.out.println("Continuing with file transfer...");
        }
    }

    private String setSourceOrDestinationDirectory(String sourceOrDestinationFolder){
        File fileMoverCurrentDirectory = new File(System.getProperty("user.dir"));
        JFileChooser currentDirectoryChooser = new JFileChooser();
        currentDirectoryChooser.setCurrentDirectory(fileMoverCurrentDirectory);
        currentDirectoryChooser.setDialogTitle(String.format("Please select the %s directory", sourceOrDestinationFolder));
        currentDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        currentDirectoryChooser.showSaveDialog(null);
        return currentDirectoryChooser.getSelectedFile().getPath() + "\\";
    }

}
