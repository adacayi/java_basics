package com.sanver.basics.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryDeleteSample {
	static Path temporary;
	static Path inner;

	public static void main(String[] args) {
		temporary = Paths.get("temporary");
		inner = Paths.get("temporary\\inner");
		try {
			clear();// If directory already exists then creating will give an exception, so we
					// delete it.
			Files.createDirectory(temporary);
			Files.createDirectory(inner);
			Files.list(temporary.toAbsolutePath().getParent()).forEach(x->System.out.println(x.getFileName()));
			System.out.println();
			Files.list(temporary).forEach(System.out::println);
			clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void clear() throws IOException {
		if (!Files.exists(temporary))
			return;

		if (Files.exists(inner))// To delete a folder it must be empty. It cannot even contain empty subfolders.
			Files.delete(inner);

		Files.delete(temporary);
	}
}
