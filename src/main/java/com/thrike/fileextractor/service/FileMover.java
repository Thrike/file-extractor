package com.thrike.fileextractor.service;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class FileMover {

    private String fileFolderSource;
    private String fileFolderDestination;

    private final Scanner userInput = new Scanner(System.in);
    private final FileFinder fileFinder = new FileFinder();

    public void start() {
        try {
            fileFolderSource = setSourceOrDestinationDirectory("source");
            fileFolderDestination = setSourceOrDestinationDirectory("destination");
            List<File> listOfFilesToTransfer = fileFinder.findFiles(fileFolderSource);
            boolean userConfirmedFileTransfer = confirmFileTransfer();
            if (fileFinder.filesAreAvailableForTransfer(listOfFilesToTransfer) && userConfirmedFileTransfer && fileFolderDestination != null) {
                transferFiles(listOfFilesToTransfer);
                log.info("Files transferred successfully");
            }
        } catch (NoSuchFileException n) {
            log.error("Unable to move file. Could not find file: " + n.getFile());
        } catch (IOException e) {
            log.error("IOException when starting the app: " + e);
        }
    }

    private void transferFiles(List<File> listOfFilesToTransfer) throws IOException {
        log.info("Transferring files to: " + fileFolderDestination + "\n");
        for (File file : listOfFilesToTransfer) {
            Path sourceFileFolderPath = Paths.get(String.valueOf(file));
            Path destinationFileFolderPath = Paths.get(fileFolderDestination + file.getName());
            Files.move(sourceFileFolderPath, destinationFileFolderPath);
        }
    }

    private boolean confirmFileTransfer() {
        boolean userConfirmedFileTransfer = false;
        log.info("Transfer all above files with the following configuration? (Y/N) Source: {}  -->  Destination: {}", fileFolderSource, fileFolderDestination);
        String userConfirmationChoice = userInput.nextLine();
        if (userConfirmationChoice.equalsIgnoreCase("Y")) {
            userConfirmedFileTransfer = true;
        } else if (userConfirmationChoice.equalsIgnoreCase("N")) {
            log.info("Aborting file transfer...");
        } else {
            log.info("\nPlease enter 'Y' or 'N'");
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
