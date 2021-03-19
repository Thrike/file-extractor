package com.thrike.fileextractor;

import com.thrike.fileextractor.service.FileMover;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileExtractorApplication {

	public static void main(String[] args) {
		String sourceFolder = "E:\\Programming\\Java\\Projects\\picext_source\\";
		String destinationFolder = "E:\\Programming\\Java\\Projects\\picext_destination\\";
		FileMover fileMover = new FileMover(sourceFolder, destinationFolder);
//		fileMover.start();
		fileMover.testFileChoose();
	}

}
