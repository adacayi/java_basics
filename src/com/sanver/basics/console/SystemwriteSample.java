package com.sanver.basics.console;

import java.io.IOException;

public class SystemwriteSample {

	public static void main(String[] args) {
		char c = 66;
		int i = c;
		System.out.write(i);
		System.out.write(c);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.flush();// Without this it will not write to console
		try {
			System.out.write("\nSelamunaleykum".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.flush();
	}

}
