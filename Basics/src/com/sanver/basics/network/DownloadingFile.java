package com.sanver.basics.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownloadingFile {

	public static void main(String[] args) {
		String urlAddress = "https://cdn.pixabay.com/photo/2016/09/03/23/18/rose-1642970_1280.jpg";
		Path path = Paths.get("src\\com\\sanver\\basics\\network\\rose.jpg");
		URL url;

		try {
			url = new URL(urlAddress);
			URLConnection connection = url.openConnection();
			Files.copy(new BufferedInputStream(connection.getInputStream()), path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Copy operation is done.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
