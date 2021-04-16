package com.thrike.fileextractor.service;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileFinder {

    private final FileDisplay fileDisplay = new FileDisplay();

    public List<File> findFiles(String fileFolderSource) {
        File sourceDirectoryToSearch = new File(fileFolderSource);
        List<File> fileList = new ArrayList<>();
        List<File> directoryList = findFilesInSourceDirectory(sourceDirectoryToSearch, fileList);
        System.out.println("Directory List: " + directoryList);
        List<File> subDirectoryFileList = findFilesInSubDirectory(directoryList);
        fileList.addAll(subDirectoryFileList);

        if (fileList.isEmpty()) {
            log.info("No files found in directory: {}, shutting down... %n%n", fileFolderSource);
            System.exit(0);
        } else {
            log.info("Found {} files in {}:", fileList.size(), fileFolderSource);
            fileDisplay.displayFilesFoundInDirectory(fileList);
        }
        return fileList;
    }

    public List<File> findFilesInSourceDirectory(File sourceDirectoryToSearch, List<File> filesPresentList) {
        List<File> directoriesPresentList = new ArrayList<>();
        File[] filesToProcess = sourceDirectoryToSearch.listFiles();
        if (filesToProcess != null) {
            for (File file : filesToProcess) {
                if (file.isFile()) {
                    filesPresentList.add(file);
                } else {
                    directoriesPresentList.add(file);
                }
            }
        }
        return directoriesPresentList;
    }

    public List<File> findFilesInSubDirectory(List<File> directoryToSearch) {
        List<File> filesFoundInDirectories = new ArrayList<>();
        for (File fileDirectory : directoryToSearch) {
            if (fileDirectory != null) {
                File[] subDirectoryFilesToMove = fileDirectory.listFiles();
                for (File file : subDirectoryFilesToMove) {
                    if (file.isFile()) {
                        filesFoundInDirectories.add(file);
                    }
                }
            }
        }
        return filesFoundInDirectories;
    }

    public boolean filesAreAvailableForTransfer(List<File> listOfFilesToTransfer) {
        return !listOfFilesToTransfer.isEmpty();
    }
}
