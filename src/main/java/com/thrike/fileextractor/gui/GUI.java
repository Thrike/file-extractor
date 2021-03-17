package com.thrike.fileextractor.gui;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    private JPanel mainPanel;
    private JButton testButton;

    public GUI(String title) throws HeadlessException {
        super(title);

        this.setSize(400,400);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
    }
}
