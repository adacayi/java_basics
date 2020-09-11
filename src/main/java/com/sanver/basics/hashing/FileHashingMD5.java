package com.sanver.basics.hashing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class FileHashingMD5 {
    public static void main(String[] args) throws IOException {
        File file = getTempFile();

        try (InputStream stream = Files.newInputStream(file.toPath())) {
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(stream);
            System.out.println(md5);
        }

        file.deleteOnExit();
    }

    private static File getTempFile() throws IOException {
        File file = new File(FileHashingMD5.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "file for md5 hashing.dat");

        try (BufferedWriter writer=Files.newBufferedWriter(Paths.get(file.getPath()))) {
            writer.write(randomAlphanumeric(10000));
        }

        return file;
    }
}
