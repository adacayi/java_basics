package com.sanver.basics.io;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class demonstrates the usage of {@link java.io.RandomAccessFile} and its
 * different access modes.
 * <p>
 * {@link java.io.RandomAccessFile} allows random (non-sequential) read and
 * write access to a file. It can be opened in different modes that dictate
 * whether the file can be read, written to, or whether write operations are
 * synchronized with the underlying storage.
 * </p>
 * <p>
 * This example showcases how to open files in each of the supported modes
 * ("r", "rw", "rws", "rwd"), demonstrates basic read and write operations, and
 * ensures proper file cleanup.
 * </p>
 * <p>
 *
 * <h2>Supported Access Modes:</h2>
 * <ul>
 * <li>
 * <b>"r" (Read-Only):</b> Opens the file for reading only. Attempting to
 * write to a file opened in this mode will throw an {@link IOException}.
 * </li>
 * <li>
 * <b>"rw" (Read-Write):</b> Opens the file for both reading and writing.
 * Changes made to the file are not guaranteed to be synchronously written to
 * the storage device.
 * </li>
 * <li>
 * <b>"rws" (Read-Write Synchronized):</b> Opens the file for reading and
 * writing, and every update to the file's content is synchronously written to
 * the underlying storage device. This mode guarantees that changes to both
 * the file's content and metadata are written immediately to disk.
 * </li>
 * <li>
 * <b>"rwd" (Read-Write Synchronized Data):</b> Opens the file for reading
 * and writing, and every update to the file's content is synchronously
 * written to the underlying storage device. Changes to the file's content
 * are guaranteed to be immediately written to disk, but metadata changes
 * may be buffered.
 * </li>
 * </ul>
 *
 * <h2>Error Handling:</h2>
 * <ul>
 * <li>
 * {@link FileNotFoundException}: Thrown if a file specified to be opened in
 * <code>"r"</code> mode does not exist. When using other modes, the file
 * will be created if it does not exist.
 * </li>
 * <li>
 * {@link IOException}: Thrown if an I/O error occurs during file operations
 * (e.g., when trying to write to a read-only file, or an error during
 * closing).
 * </li>
 * </ul>
 *
 * @see java.io.RandomAccessFile
 */
public class RandomAccessFileSample {
    public static final String CONTENT_AFTER_MODIFICATION = "File Content: %n%n";
    private static final String FILE_NAME = "test_random_access.txt";

    /**
     * The main method of the class. Demonstrates how to open files in
     * different modes, write and read from them, and then delete the file.
     *
     * @param args Command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        try {
            createFileAndWriteData();
            demonstrateFileOperations();
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        } finally {
            deleteFile();
        }
    }

    private static void createFileAndWriteData() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.writeBytes("""
                    First line
                    Second line
                    Third line""");
            System.out.println("File created and initial data written.");
        }
    }

    private static void demonstrateFileOperations() throws IOException {
        // Read-only mode
        try (RandomAccessFile readOnlyFile = new RandomAccessFile(FILE_NAME, "r")) {
            System.out.printf("%nOpened file in read only mode%n");
            System.out.println("Read data in read only mode");
            readFile(readOnlyFile);
        }

        // Read-write mode
        try (RandomAccessFile readWriteFile = new RandomAccessFile(FILE_NAME, "rw")) {
            System.out.printf("%nOpened file in read/write mode%n");
            readWriteFile.write("First - 1".getBytes()); // Writes from the beginning of the file, overwriting the existing content, but not truncating all the file (unlike the FileOutputStream behavior for existing files with the constructors not containing the boolean append parameter)
            readWriteFile.seek(0); // Go to the beginning of the file
            readFile(readWriteFile);
        }

        // Read-write-sync mode
        // The "rws" mode is used to open a file for both reading and writing, while also ensuring a high level of data integrity through synchronization.
        // The primary benefit of "rws" is that it provides the strongest guarantees of data integrity.
        // If your program or the system crashes or loses power immediately after a write() operation returns, you can be confident that the data you just wrote (and the file's metadata) has been safely stored on the disk.
        try (RandomAccessFile readWriteSyncFile = new RandomAccessFile(FILE_NAME, "rws")) {
            System.out.printf("%nOpened file in read/write/sync mode%n");
            readWriteSyncFile.writeBytes("First line\n");
            readWriteSyncFile.seek(0); // Go to the beginning of the file
            readFile(readWriteSyncFile);
        }

        // Read-write-sync-data mode
        // "rwd" is similar to "rws" in that it guarantees data synchronization (the content is written to disk immediately). However, it does not guarantee that metadata is synchronized. In other words, file metadata updates may be buffered and written asynchronously. Thus, "rwd" offers a performance advantage over "rws" when metadata updates are not critical to be synchronized.
        try (RandomAccessFile readWriteSyncDataFile = new RandomAccessFile(FILE_NAME, "rwd")) {
            System.out.printf("%nOpened file in read/write/sync-data mode%n");
            readWriteSyncDataFile.writeBytes("Is first?");
            readWriteSyncDataFile.seek(0); // Go to the beginning of the file
            readFile(readWriteSyncDataFile);
        }
    }

    private static void readFile(RandomAccessFile readOnlyFile) throws IOException {
        System.out.printf(CONTENT_AFTER_MODIFICATION);
        String readData;
        while ((readData = readOnlyFile.readLine()) != null) {
            System.out.println(readData);
        }
    }

    private static void deleteFile() {
        File file = new File(FILE_NAME);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("File was not deleted");
        }
    }
}