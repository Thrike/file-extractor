package com.thrike.fileextractor.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileFinder {

    private FileDisplay fileDisplay = new FileDisplay();

    /* TODO
break method into multiple methods that gets the files to process (the first half), and then performs and action (the last half)
 */
    public List<File> findFilesInSourceDirectory(String fileFolderSource) {
        File sourceDirectoryToSearch = new File(fileFolderSource);
        List<File> filesPresent = new ArrayList<>();
        List<File> directoriesPresent = new ArrayList<>();
        List<File> filesToProcess = Arrays.asList(sourceDirectoryToSearch.listFiles());
        for (File file : filesToProcess) {
            if (file.isFile()) {
                filesPresent.add(file);
            } else {
                directoriesPresent.add(file);
            }
        }
        List<File> subDirectoryFilesToAdd = findFilesInSubDirectory(directoriesPresent);
        for (File file : subDirectoryFilesToAdd) {
            filesPresent.add(file);
        }
        if (filesPresent.size() == 0) {
            System.out.println(String.format("No files found in directory: %s, shutting down... \n", fileFolderSource));
            System.exit(0);
        } else {
            System.out.println(String.format("Found %s files in %s:", filesPresent.size(), fileFolderSource));
            fileDisplay.displayFilesFoundInDirectory(filesPresent);
        }
        return filesPresent;
    }

    public List<File> findFilesInSubDirectory(List<File> directoryToSearch) {
        List<File> filesFoundInDirectories = new ArrayList<>();
        for (File directory : directoryToSearch) {
            List<File> subDirectoryFilesToMove = Arrays.asList(directory.listFiles());
            System.out.println(subDirectoryFilesToMove);
            for (File file : subDirectoryFilesToMove) {
                if (file.isFile()) {
                    filesFoundInDirectories.add(file);
                }
            }
        }
        return filesFoundInDirectories;
    }

    public boolean filesAreAvailableForTransfer(List<File> listOfFilesToTransfer) {
        boolean thereAreFilesToTransfer;
        if (listOfFilesToTransfer.size() == 0) {
            thereAreFilesToTransfer = false;
        } else {
            thereAreFilesToTransfer = true;
        }
        return thereAreFilesToTransfer;
    }
}
