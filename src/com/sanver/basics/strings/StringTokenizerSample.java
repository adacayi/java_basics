package com.sanver.basics.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class StringTokenizerSample {

	public static void main(String[] args) {
		System.out.print("Enter parameters separated with comma: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			line = reader.readLine();

			String delimeter = ",";
			StringTokenizer tokenizer = new StringTokenizer(line, delimeter);
			System.out.println("Parameters you entered are: ");
			while (tokenizer.hasMoreTokens()) {
				System.out.println(tokenizer.nextToken());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
