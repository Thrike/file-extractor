package com.thrike.fileextractor;

import com.thrike.fileextractor.service.FileMover;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileExtractorApplication {

	public static void main(String[] args) {
		FileMover fileMover = new FileMover();
		fileMover.start();
	}
}
