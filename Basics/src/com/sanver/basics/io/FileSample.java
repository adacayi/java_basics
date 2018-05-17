package com.sanver.basics.io;

import java.io.File;
import java.io.IOException;

public class FileSample {

	public static void main(String[] args) {
		String baseDirectory = "src\\com\\sanver\\basics\\io\\";
		String fileName = "Trial.txt.bak";
		String newFileName = "Trial.txt";
		File file = new File(baseDirectory + fileName);
		File destinationFile = new File(baseDirectory + newFileName);
		try {
			if (!file.exists())
				file.createNewFile();
			if (destinationFile.exists())
				destinationFile.delete();
			file.renameTo(destinationFile);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
