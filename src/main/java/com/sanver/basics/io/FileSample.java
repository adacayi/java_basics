package com.sanver.basics.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Arrays;

public class FileSample {

	public static void main(String[] args) throws IOException {
		final String format = "%-30s%s%n";
		var baseDirectory = "src/main/java/com/sanver/basics/io";
		var fileName = "trial.bat";
		var file = new File(baseDirectory, fileName);
		file.deleteOnExit(); // Requests that the file or directory denoted by this abstract pathname be deleted when the virtual machine terminates.
		var numberFormat = NumberFormat.getInstance();
		System.out.printf(format, "file.getAbsolutePath()", file.getAbsolutePath());
		System.out.printf(format, "file.exists()", file.exists());
		System.out.printf(format, "file.createNewFile()", file.createNewFile());
		var renamed = new File(baseDirectory, "trial2.txt");
		renamed.deleteOnExit();
		System.out.printf(format, "file.renameTo()", file.renameTo(renamed)); // If successful, this returns true. However, this does not change the file instance path, so, if you invoke the exists() method, it will return false now, because it checks for the old path.
		// Many aspects of the behavior of this method are inherently platform-dependent: The rename operation might not be able to move a file from one filesystem to another, it might not be atomic, and it might not succeed if a file with the destination abstract pathname already exists. With renameTo() this File object is not changed to name the destination file or directory. Note that the java.nio.file.Files class defines the move method to move or rename a file in a platform independent manner.
		System.out.printf(format,"file.getAbsolutePath()", file.getAbsolutePath());
		System.out.printf(format,"file.exists()", file.exists()); // Because renameTo changed the file name, but did not change the file instance's path, this now checks for the initial path, rather than the renamed one, thus returns false.
		System.out.printf(format, "renamed.delete()", renamed.delete());
		System.out.printf(format, "file.createNewFile()", file.createNewFile());
		System.out.printf(format, "file.toPath()", file.toPath());
		System.out.printf(format, "file.getPath()", file.getPath());
		System.out.printf(format, "getPath.equals(toPath)", file.toPath().equals(file.getPath()));
		System.out.printf(format, "file.canExecute()", file.canExecute()); // If file not exists this returns false.
		printReadWriteStatus(format, file);
		System.out.printf(format, "file.getName()", file.getName());
		System.out.printf(format, "file.getParent()", file.getParent()); // Returns the parent as a String
		System.out.printf(format, "file.getParentFile()", file.getParentFile()); // Returns the parent File
		System.out.printf(format, "file.getTotalSpace()", numberFormat.format(file.getTotalSpace()));
		System.out.printf(format, "file.getFreeSpace()", numberFormat.format(file.getFreeSpace()));
		System.out.printf(format, "file.getUsableSpace()", numberFormat.format(file.getUsableSpace()));
		System.out.printf(format, "file.isDirectory()", file.isDirectory());
		System.out.printf(format, "file.isFile()", file.isFile());
		System.out.printf(format, "file.isAbsolute()", file.isAbsolute()); // Tests whether this abstract pathname is absolute. The definition of absolute pathname is system dependent. On UNIX systems, a pathname is absolute if its prefix is "/". On Microsoft Windows systems, a pathname is absolute if its prefix is a drive specifier followed by "\\", or if its prefix is "\\\\".
		System.out.printf(format, "file.getAbsoluteFile()", file.getAbsoluteFile()); // Returns the absolute form of this abstract pathname. Equivalent to new File(this.getAbsolutePath).
		System.out.printf(format, "file.getCanonicalFile()", file.getCanonicalFile()); // Returns the canonical form of this abstract pathname. Equivalent to new File(this.getCanonicalPath). A canonical file path is an **absolute, unique pathname** with all symbolic links fully resolved (if applicable), and it takes into account the actual filesystem.
		/*
		 * The difference between getAbsoluteFile() and getCanonicalFile() is as follows:
		 *
		 * 1. getAbsoluteFile():
		 *    - Returns the absolute form of the file path.
		 *    - Equivalent to calling new File(this.getAbsolutePath()).
		 *    - It does not resolve symbolic links, relative paths (".."), or redundant path components like ".".
		 *    - Simply constructs an absolute path by appending the relative path (if provided) to the current working directory or root directory.
		 *    - Does not check whether the file exists on the filesystem.
		 *
		 *    Example:
		 *        File file = new File("temp/../example.txt");
		 *        System.out.println(file.getAbsoluteFile());
		 *        Output: /current/working/dir/temp/../example.txt
		 *
		 * 2. getCanonicalFile():
		 *    - Returns the canonical form of the file path, which is an absolute, unique path.
		 *    - Equivalent to new File(this.getCanonicalPath()).
		 *    - Resolves symbolic links (if the system supports it), relative references (".." and "."),
		 *      and normalizes the file path.
		 *    - Always checks the actual file system to return the unique "real" path for the file.
		 *    - May throw IOException if the operation fails (e.g., file does not exist or there are permission issues).
		 *
		 *    Example:
		 *        File file = new File("temp/../example.txt");
		 *        System.out.println(file.getCanonicalFile());
		 *        Output: /current/working/dir/example.txt
		 *
		 * Key Differences:
		 * | Aspect                | getAbsoluteFile()                                                    | getCanonicalFile()                                                   |
		 * |-----------------------|----------------------------------------------------------------------|----------------------------------------------------------------------|
		 * | Resulting Path        | Absolute path without resolving symbolic links or normalizing paths. | Absolute path after resolving symbolic links and normalizing paths.  |
		 * | Symbolic Links        | Does not resolve symbolic links.                                     | Resolves symbolic links to their destination.                        |
		 * | Filesystem Check      | Doesn't check the filesystem.                                        | Always consults the filesystem for the canonical path.               |
		 * | Redundant Paths       | Retains redundant references like "." or "..".                       | Cleans the path by resolving redundancies (e.g., "..").              |
		 * | IOException Handling  | Does not throw any exceptions.                                       | May throw IOException if resolution fails or file doesn't exist.     |
		 * | Performance           | Faster, as it doesn't involve filesystem operations.                 | Slower, as it involves consulting the filesystem.                    |
		 *
		 * Use getAbsoluteFile() when you need an absolute path without resolving it against the filesystem.
		 * Use getCanonicalFile() when you need a fully resolved, unique path reflecting the actual filesystem.
		 */
		System.out.printf(format, "file.getCanonicalPath()", file.getCanonicalPath()); // This returns a string
		System.out.printf(format, "file.isHidden()", file.isHidden());
		Files.setAttribute(file.toPath(), "dos:hidden", true);
		System.out.printf(format, "file.isHidden()", file.isHidden());
		System.out.printf(format, "file.lastModified()", Instant.ofEpochMilli(file.lastModified()));
		System.out.printf(format, "file.list()", Arrays.toString(file.list())); // Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname. If this abstract pathname does not denote a directory, then this method returns null. Otherwise, an array of strings is returned, one for each file or directory in the directory. Names denoting the directory itself and the directory's parent directory are not included in the result. Each string is a file name rather than a complete path.
		System.out.printf(format, "getParentFile().list()", Arrays.toString(file.getParentFile().list())); // This only lists the names of files and directories that are directly contained within the given directory. It does not recursively list the contents of any subdirectories.
		System.out.printf(format, "file.delete()", file.delete());// Use Java.nio.file.Files#delete instead. When Java.nio.file.Files#delete fails, this void method returns one of a series of exception types to better indicate the cause of the failure, whereas file.delete() just returns false.
		System.out.printf(format, "file.exists()", file.exists());
		System.out.printf(format, "file.createNewFile()",file.createNewFile());
		System.out.printf(format, "file.exists()", file.exists());
		System.out.printf(format, "file.setReadOnly()", file.setReadOnly());
		printReadWriteStatus(format, file);
		System.out.printf(format, "file.setReadable(false)", file.setReadable(false));
		printReadWriteStatus(format, file);
		System.out.printf(format, "file.setWritable(true)", file.setWritable(true));
		printReadWriteStatus(format, file);
	}

	private static void printReadWriteStatus(String format, File file) {
		System.out.printf(format, "file.canRead()", file.canRead());
		System.out.printf(format, "file.canWrite()", file.canWrite());
	}
}
