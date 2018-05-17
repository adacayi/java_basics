package com.sanver.basics.exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TryWithResources {

	public static void main(String[] args) {
		try (FileInputStream stream = new FileInputStream("trial.txt")) {
			stream.read();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
	}
}
