package com.sanver.basics.exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionHandlingAndTryWithResources {

	public static void main(String[] args) {
		try (FileInputStream stream = new FileInputStream("non-existing-file.txt")) {
		} catch (FileNotFoundException e) { // If we had this after the catch(IOException) it would result in a "FileNotFoundException has already been caught" error, because catch(IOException) would have caught it.
			System.out.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e1) { // This block will not be executed since new FileInputStream("non-existing-file.txt") throws a FileNotFoundException in this case, and it is caught in the first catch block.
			System.out.println("IOException: " + e1.getMessage());
		} finally{
			// stream.close(); // This line would give a compile error, since stream is only accessible from within the try with resources block.
			System.out.println("Process finished");
		}
	}
}
