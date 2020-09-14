package com.sanver.basics.hashing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.CRC32C;
import java.util.zip.CheckedInputStream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class FileHashingCRC32C {
    public static void main(String[] args) throws IOException {
        File file = getTempFile();

        try (InputStream stream = Files.newInputStream(file.toPath())) {
            long crc32c = getChecksumCRC32C(stream, 1024);
            var hex = Long.toHexString(crc32c);
            System.out.println(crc32c);
            System.out.println(hex);
        }

        file.deleteOnExit();
    }

    private static long getChecksumCRC32C(InputStream stream, int bufferSize)
            throws IOException {
        CheckedInputStream checkedInputStream = new CheckedInputStream(stream, new CRC32C());
        byte[] buffer = new byte[bufferSize];
        while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {
        }
        return checkedInputStream.getChecksum().getValue();
    }

    private static File getTempFile() throws IOException {
        File file = new File(FileHashingCRC32C.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                             + "file for crc32c hashing.dat");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getPath()))) {
            writer.write(randomAlphanumeric(10000));
        }

        return file;
    }
}
