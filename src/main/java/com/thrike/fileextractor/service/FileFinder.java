package com.thrike.fileextractor.service;

import com.thrike.fileextractor.view.FileDisplay;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileFinder {

    private final FileDisplay fileDisplay = new FileDisplay();

    public List<Path> findFiles(String fileFolderSource) throws IOException {
        File sourceDirectory = new File(fileFolderSource);
        List<Path> filesList = collectFiles(sourceDirectory);
        if (filesList.isEmpty()) {
            System.out.printf("No files found in directory: %s, shutting down...", fileFolderSource);
            System.exit(0);
        } else {
            System.out.printf("Found %s files in %s \n", filesList.size(), fileFolderSource);
            fileDisplay.displayFilesFoundInDirectory(filesList);
        }
        return filesList;
    }

    public List<Path> collectFiles(File sourceDirectory) throws IOException {
        Path path = Paths.get(sourceDirectory.getAbsolutePath());
        List<Path> listOfFiles;
        try (Stream<Path> pathStream = Files.walk(path)) {
            listOfFiles = pathStream.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return listOfFiles;
    }

    public boolean filesAreAvailableForTransfer(List<Path> listOfFilesToTransfer) {
        return !listOfFilesToTransfer.isEmpty();
    }
}
