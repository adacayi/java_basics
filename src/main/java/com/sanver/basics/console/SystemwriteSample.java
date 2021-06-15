package com.sanver.basics.console;

import static com.sanver.basics.utils.Utils.sleep;

import java.io.IOException;

public class SystemwriteSample {

	public static void main(String[] args) {
		char c = 66;
		int i = c;

		System.out.write(i);
		System.out.write(c);
		sleep(3000);
		System.out.flush();// Without this it will not write to console

		try {
			System.out.write("\nSelamunaleykum".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.flush();
	}

}
