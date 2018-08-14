package com.sanver.basics.io.streams;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputStreamSample {

	public static void main(String[] args) {
		String fileName = "bin\\com\\sanver\\basics\\io\\streams\\trial.txt";

		try (FileInputStream stream = new FileInputStream(fileName)) {
			int read = 0;

			while (true) {
				read = stream.read();

				if (read == -1)
					break;

				System.out.print((char) read);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
