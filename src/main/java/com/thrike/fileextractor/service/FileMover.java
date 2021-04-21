package com.thrike.fileextractor.service;

import com.thrike.fileextractor.view.JFileChooserCreator;
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
    private final JFileChooserCreator jFileChooserCreator = new JFileChooserCreator();

    public void start() {
        try {
            fileFolderSource = setSourceOrDestinationDirectory("source");
            fileFolderDestination = setSourceOrDestinationDirectory("destination");
            List<Path> listOfFilesToTransfer = fileFinder.findFiles(fileFolderSource);
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

    private void transferFiles(List<Path> listOfFilesToTransfer) throws IOException {
        log.info("Transferring files to: " + fileFolderDestination + "\n");
        for (Path path : listOfFilesToTransfer) {
            Path sourceFileFolderPath = Paths.get(String.valueOf(path));
            Path destinationFileFolderPath = Paths.get(fileFolderDestination + path.getFileName());
            Files.move(sourceFileFolderPath, destinationFileFolderPath);
        }
    }

    private boolean confirmFileTransfer() {
        boolean userConfirmedFileTransfer = false;
        System.out.printf(("Transfer all above files with the following configuration? (Y/N) \nSource: %s  -->  Destination: %s \n"), fileFolderSource, fileFolderDestination);
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
        JFileChooser currentDirectoryChooser = jFileChooserCreator.createJFileChooser(fileMoverCurrentDirectory, sourceOrDestinationFolder);
        return currentDirectoryChooser.getSelectedFile().getPath() + "\\";
    }
}
