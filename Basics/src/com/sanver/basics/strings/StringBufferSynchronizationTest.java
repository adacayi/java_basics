package com.sanver.basics.strings;

public class StringBufferSynchronizationTest {

	public static void main(String[] args) {
		StringBuffer buffer = new StringBuffer();
		int count = 100000;
		
		Runnable append = () -> {
			for (int i = 0; i < count; i++)
				buffer.append("a");
		};
		Runnable remove = () -> {
			for (int i = 0; i < count;) {
				if (buffer.length() > 0) {
					buffer.deleteCharAt(0);
					i++;
				}
			}
		};

		Thread t1 = new Thread(append);
		Thread t2 = new Thread(remove);
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished. Buffer size= " + buffer.length());// Since StringBuffer is synchronised the size
																		// is 0. If we used StringBuilder instead the
																		// code would not end always.
	}
}
