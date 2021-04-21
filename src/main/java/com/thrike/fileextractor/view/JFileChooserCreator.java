package com.thrike.fileextractor.view;

import javax.swing.*;
import java.io.File;

public class JFileChooserCreator {

    public JFileChooser createJFileChooser(File fileMoverCurrentDirectory, String sourceOrDestinationFolder) {
        JFileChooser currentDirectoryChooser = new JFileChooser();
        currentDirectoryChooser.setCurrentDirectory(fileMoverCurrentDirectory);
        currentDirectoryChooser.setDialogTitle(String.format("Please select the %s directory", sourceOrDestinationFolder));
        currentDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        currentDirectoryChooser.showSaveDialog(null);
        return currentDirectoryChooser;
    }
}
