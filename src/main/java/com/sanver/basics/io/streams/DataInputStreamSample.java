package com.sanver.basics.io.streams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataInputStreamSample {

	public static void main(String[] args) {
		byte value1 = 127;
		short value2 = 15431;
		double value3 = 3.14159265;
		double value4 = 2.23;
		int value5 = 1821178321;
		String fileName = "src/main/java/com/sanver/basics/io/streams/TrialWithNumbers.txt";
		try (FileOutputStream stream = new FileOutputStream(fileName);
				BufferedOutputStream buff = new BufferedOutputStream(stream);
				DataOutputStream writer = new DataOutputStream(buff)) {
			writer.writeByte(value1);
			writer.writeShort(value2);
			writer.writeDouble(value3);
			writer.writeDouble(value4);
			writer.writeInt(value5);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try (FileInputStream stream = new FileInputStream(fileName);
				BufferedInputStream buff = new BufferedInputStream(stream);
				DataInputStream reader = new DataInputStream(buff)) {
			System.out.println(reader.readByte());
			System.out.println(reader.readShort());
			System.out.println(reader.readDouble());
			System.out.println(reader.readDouble());
			System.out.println(reader.readInt());

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
