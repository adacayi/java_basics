package com.sanver.basics.exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TryWithResources {

	public static void main(String[] args) {
		try (FileInputStream stream = new FileInputStream("trial.txt")) {
			stream.read();
		} catch (FileNotFoundException e) { // If we had this after the catch(IOException) it would result in a "FileNotFoundException has already been caught" error, because catch(IOException) would have caught it.
			System.out.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e1) { // This block will not be executed since stream.read() throws a FileNotFoundException in this case, and it is caught in the first catch block.
			System.out.println("IOException: " + e1.getMessage());
		}
	}
}
