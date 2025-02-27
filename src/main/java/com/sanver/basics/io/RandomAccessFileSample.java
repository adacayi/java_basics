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
            file.writeUTF("Initial data written to the file.");
            System.out.println("File created and initial data written.");
        }
    }

    private static void demonstrateFileOperations() throws IOException {
        // Read-only mode
        try (RandomAccessFile readOnlyFile = new RandomAccessFile(FILE_NAME, "r")) {
            System.out.println("Opened file in read only mode");
            String readData = readOnlyFile.readUTF();
            System.out.println("Read data in read only mode: " + readData);
        }

        // Read-write mode
        try (RandomAccessFile readWriteFile = new RandomAccessFile(FILE_NAME, "rw")) {
            System.out.println("Opened file in read/write mode");
            readWriteFile.seek(0); // Go to the beginning of the file
            readWriteFile.writeUTF("Modified data using read/write mode.");
            readWriteFile.seek(0); //reset position
            String readData = readWriteFile.readUTF();
            System.out.println("Read data after modifications: " + readData);
        }

        // Read-write-sync mode
        // The "rws" mode is used to open a file for both reading and writing, while also ensuring a high level of data integrity through synchronization.
        // The primary benefit of "rws" is that it provides the strongest guarantees of data integrity.
        // If your program or the system crashes or loses power immediately after a write() operation returns, you can be confident that the data you just wrote (and the file's metadata) has been safely stored on the disk.
        try (RandomAccessFile readWriteSyncFile = new RandomAccessFile(FILE_NAME, "rws")) {
            System.out.println("Opened file in read/write/sync mode");
            readWriteSyncFile.seek(0);
            readWriteSyncFile.writeUTF("Data modified with sync mode.");
            readWriteSyncFile.seek(0);
            String readData = readWriteSyncFile.readUTF();
            System.out.println("Read data after modifications with sync: " + readData);
        }

        // Read-write-sync-data mode
        // "rwd" is similar to "rws" in that it guarantees data synchronization (the content is written to disk immediately). However, it does not guarantee that metadata is synchronized. In other words, file metadata updates may be buffered and written asynchronously. Thus, "rwd" offers a performance advantage over "rws" when metadata updates are not critical to be synchronized.
        try (RandomAccessFile readWriteSyncDataFile = new RandomAccessFile(FILE_NAME, "rwd")) {
            System.out.println("Opened file in read/write/sync-data mode");
            readWriteSyncDataFile.seek(0);
            readWriteSyncDataFile.writeUTF("Data modified with sync-data mode.");
            readWriteSyncDataFile.seek(0);
            String readData = readWriteSyncDataFile.readUTF();
            System.out.println("Read data after modifications with sync-data: " + readData);
        }
    }
    private static void deleteFile(){
        File file = new File(FILE_NAME);
        if(file.delete())
            System.out.println("File deleted successfully");
        else
            System.out.println("File was not deleted");
    }
}