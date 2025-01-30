package com.sanver.basics.io.streams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataInputStreamSample {

    public static void main(String[] args) {
        byte value1 = 127;
        short value2 = 15_431;
        int value3 = 1_821_178_321;
        long value4 = 1_000_000_000_000_000L;
        char value5 = 'A';
        float value6 = 2.23f;
        double value7 = 3.14159265;
        boolean value8 = true;
        String fileName = "src/main/java/com/sanver/basics/io/streams/TrialWithNumbers.txt";
        try (FileOutputStream stream = new FileOutputStream(fileName);
             BufferedOutputStream buff = new BufferedOutputStream(stream);
             DataOutputStream writer = new DataOutputStream(buff)) {
            writer.writeByte(value1);
            writer.writeShort(value2);
            writer.writeInt(value3);
            writer.writeLong(value4);
            writer.writeChar(value5);
            writer.writeFloat(value6);
            writer.writeDouble(value7);
            writer.writeBoolean(value8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (FileInputStream stream = new FileInputStream(fileName);
             BufferedInputStream buff = new BufferedInputStream(stream);
             DataInputStream reader = new DataInputStream(buff)) {
            System.out.println(reader.readByte());
            System.out.println(reader.readShort());
            System.out.println(reader.readInt());
            System.out.println(reader.readLong());
            System.out.println(reader.readChar());
            System.out.println(reader.readFloat());
            System.out.println(reader.readDouble());
            System.out.println(reader.readBoolean());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
