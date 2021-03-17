package com.thrike.fileextractor;

import com.thrike.fileextractor.service.FileMover;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileExtractorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileExtractorApplication.class, args);
		System.setProperty("java.awt.headless", "false");
//		GUI gui = new GUI("Picture Extractor");
		String sourceFolder = "E:\\Programming\\Java\\Projects\\picext_source";
		String destinationFolder = "E:\\Programming\\Java\\Projects\\picext_source";
		FileMover pictureMover = new FileMover(sourceFolder, destinationFolder);
		pictureMover.move();
	}

}
