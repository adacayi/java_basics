package com.sanver.basics.io.streams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WritingDataToSystemIn {

	public static void main(String[] args) {
		String message = "Selamunaleykum";
		InputStream stream = new ByteArrayInputStream((message + "\n").getBytes());
		// InputStream oldStream = System.in;
		System.setIn(stream);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));// It is neccessary to set the
																						// reader after changing
																						// System.in. Else it will still
																						// read from the old stream.

		try {
			System.out.println(reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
