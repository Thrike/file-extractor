package com.thrike.fileextractor.service;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileMover {

    private String fileFolderSource;
    private String fileFolderDestination;
    private Scanner userInput = new Scanner(System.in);

    private FileFinder fileFinder = new FileFinder();

    public void start() {
        try {
            fileFolderSource = setSourceOrDestinationDirectory("source");
            fileFolderDestination = setSourceOrDestinationDirectory("destination");
            List<File> listOfFilesToTransfer = fileFinder.findFilesInSourceDirectory(fileFolderSource);
            boolean userConfirmedFileTransfer = confirmFileTransfer();
            if (fileFinder.filesAreAvailableForTransfer(listOfFilesToTransfer) && userConfirmedFileTransfer && fileFolderDestination != null) {
                transferFiles(listOfFilesToTransfer);
                System.out.println("Files transferred successfully");
            }
        } catch (NoSuchFileException n) {
            System.out.println("Unable to move file. Could not find file: " + n.getFile());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void transferFiles(List<File> listOfFilesToTransfer) throws IOException {
        System.out.println("Transferring files to: " + fileFolderDestination + "\n");
        for (int a = 0; a < listOfFilesToTransfer.size(); a++) {
            if (listOfFilesToTransfer.size() > 0) {
                Path sourceFileFolderPath = Paths.get(String.valueOf(listOfFilesToTransfer.get(a)));
                Path destinationFileFolderPath = Paths.get(fileFolderDestination + listOfFilesToTransfer.get(a).getName());
                Files.move(sourceFileFolderPath, destinationFileFolderPath);
            }
        }
    }

    private boolean confirmFileTransfer() {
        boolean userConfirmedFileTransfer = false;
        System.out.println(String.format("\nTransfer all above files with the following configuration? (Y/N) \n" +
                "Source: " + "[" + fileFolderSource + "]" + "  -->  " + "Destination: " + "[" + fileFolderDestination + "]\n"));
        String userConfirmationChoice = userInput.nextLine();
        if (userConfirmationChoice.equalsIgnoreCase("Y")) {
            userConfirmedFileTransfer = true;
        } else if (userConfirmationChoice.equalsIgnoreCase("N")) {
            userConfirmedFileTransfer = false;
            System.out.println("Aborting file transfer...");
        } else {
            System.out.println("\nPlease enter 'Y' or 'N'");
            start();
        }
        return userConfirmedFileTransfer;
    }

    private String setSourceOrDestinationDirectory(String sourceOrDestinationFolder) {
        File fileMoverCurrentDirectory = new File(System.getProperty("user.dir"));
        JFileChooser currentDirectoryChooser = createJFileChooser(fileMoverCurrentDirectory, sourceOrDestinationFolder);
        return currentDirectoryChooser.getSelectedFile().getPath() + "\\";
    }

    private JFileChooser createJFileChooser(File fileMoverCurrentDirectory, String sourceOrDestinationFolder) {
        JFileChooser currentDirectoryChooser = new JFileChooser();
        currentDirectoryChooser.setCurrentDirectory(fileMoverCurrentDirectory);
        currentDirectoryChooser.setDialogTitle(String.format("Please select the %s directory", sourceOrDestinationFolder));
        currentDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        currentDirectoryChooser.showSaveDialog(null);
        return currentDirectoryChooser;
    }
}
