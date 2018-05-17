package com.sanver.basics.collections;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;

public class PropertySample {

	public static void main(String[] args) {
		System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v)); // To show all system properties
		String file = "bin\\com\\sanver\\basics\\collections\\mail.properties";
		Iterator<?> iterator = (Iterator<?>) System.getProperties().propertyNames();
		String key;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			System.out.println(key + ": " + System.getProperty(key));
		}
		Properties properties = new Properties(); // You can initialise it like new Properties(System.getProperties()).
		// If done so then the initial system properties will be set to properties. 
		// This is different from properties = System.getProperties() in which
		// if you make any changes to the properties, System properties changes.
		System.out.println("\n-----------------------------------------------------------------------------------\n");
		try (BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(Paths.get(file)))) {
			properties.load(stream);// the properties will be added to the existing properties object.
			System.setProperties(properties);// This cleans all the initial properties in System
			System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
