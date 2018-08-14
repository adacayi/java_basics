package com.sanver.basics.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ReadingDataFromUrlWithStreamAPI {

	public static void main(String[] args) {
		String urlAddress = "http://www.google.com";
		URL url;

		try {
			url = new URL(urlAddress);
			URLConnection connection = url.openConnection();
			try (InputStreamReader stream = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
					BufferedReader reader = new BufferedReader(stream)) {
				reader.lines().forEach(System.out::println);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
