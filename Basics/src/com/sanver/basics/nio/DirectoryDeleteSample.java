package com.sanver.basics.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class DirectoryDeleteSample {
	static Path temporary;
	static Path inner;

	public static void main(String[] args) {
		temporary = Paths.get("temporary");
		inner = Paths.get("temporary\\inner");
		try {
			System.out.println("Directory to be generated: " + temporary.toAbsolutePath());
			System.out.println("\nCleaning first if exists..\n");
			deleteFolder(temporary);// If directory already exists then creating will give an exception, so we
			// delete it.
			Files.createDirectory(temporary);
			Files.createDirectory(inner);
			System.out.println("\nContent of parent of " + temporary + "\n");
			Files.list(temporary.toAbsolutePath().getParent()).forEach(x -> System.out.println(x.getFileName()));
			System.out.println("\nContent of " + temporary + "\n");
			Files.list(temporary).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void deleteFolder(Path folder) throws IOException {
		if (!Files.exists(folder))
			return;

		Files.walk(folder).sorted(Comparator.reverseOrder()).peek(f -> System.out.println("Deleting " + f))
				.forEach(t -> {
					try {
						Files.deleteIfExists(t);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}
}
