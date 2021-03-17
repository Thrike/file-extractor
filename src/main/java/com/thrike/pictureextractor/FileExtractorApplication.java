package com.thrike.pictureextractor;

import com.thrike.pictureextractor.gui.GUI;
import com.thrike.pictureextractor.service.FileMover;
import org.springframework.boot.SpringApplication;
import javax.swing.*;
import java.io.IOException;

//@SpringBootApplication
public class FileExtractorApplication {

	public static void main(String[] args) {
//		SpringApplication.run(PictureExtractorApplication.class, args);
//		System.setProperty("java.awt.headless", "false");
//		GUI gui = new GUI("Picture Extractor");
		String sourceFolder = "E:\\Programming\\Java\\Projects\\picext_source";
		String destinationFolder = "E:\\Programming\\Java\\Projects\\picext_source";
		FileMover pictureMover = new FileMover(sourceFolder, destinationFolder);
		pictureMover.move();
	}
}
