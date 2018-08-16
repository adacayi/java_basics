package com.sanver.basics.collections;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;

public class PropertySample {

    public static void main(String[] args) {
        String separator = "\n-----------------------------------------------------------------------------------\n";
        System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v)); // To show all system properties
        System.out.println(separator);
        System.out.println("\nAnother way of getting properties:\n");
        Iterator<?> iterator = (Iterator<?>) System.getProperties().propertyNames();
        String key;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            System.out.println(key + ": " + System.getProperty(key));
        }

        System.out.println(separator);
        String pathString = "src/main/java/com/sanver/basics/collections/mail.properties";
        Properties properties = new Properties();
        Properties systemProperties = System.getProperties();
        properties.putAll(System.getProperties());// This will copy all system properties to properties.
        // Any change here won't effect System properties.
        properties.setProperty("user.home", "Morden");// This won't change System properties.
        System.out.printf("%s\n", System.getProperty("user.home"));
        System.out.printf("%s\n", properties.getProperty("user.home"));
        systemProperties.setProperty("user.home", "Morden");// This will change System properties
        System.out.printf("%s\n", System.getProperty("user.home"));
        System.out.println(separator);
        properties = new Properties();
        try (BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(Paths.get(pathString)))) {
            properties.load(stream);// the properties will be added to the existing properties object.
            System.setProperties(properties);// This cleans all the initial properties in System
            System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(separator);
        properties = new Properties();
        System.out.println("Reading from resources folder");
        URL url = PropertySample.class.getResource("/trial.properties");

        if (url != null)
            try {
                properties.load(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }


        properties.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
