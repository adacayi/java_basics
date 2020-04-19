package com.sanver.basics.io.streams;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BufferedWriterSample {

	public static void main(String[] args) {
		String fileName = "src/main/java/com/sanver/basics/io/streams/TrialOut.txt";
		String message = "Ve aleykumselam.\nYou're welcome...";

		try (FileOutputStream stream = new FileOutputStream(fileName);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream, "Windows-1254");
				BufferedWriter writer = new BufferedWriter(outputStreamWriter)) {
			writer.write(message);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
