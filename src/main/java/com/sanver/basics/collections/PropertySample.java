package com.sanver.basics.collections;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;

public class PropertySample {

    public static final String USER_HOME = "user.home";
    public static final String SEPARATOR = "%n-----------------------------------------------------------------------------------%n%n";

    public static void main(String[] args) {
        System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v)); // System.getProperties() returns Properties. Properties extends Hashtable<Object,Object>.
        System.out.printf(SEPARATOR);
        System.out.printf("Another way of getting properties:%n%n");
        Iterator<?> iterator = (Iterator<?>) System.getProperties().propertyNames();
        String key;

        while (iterator.hasNext()) {
            key = (String) iterator.next();
            System.out.println(key + ": " + System.getProperty(key));
        }

        System.out.printf(SEPARATOR);
        Properties properties = new Properties();
        Properties systemProperties = System.getProperties();
        properties.putAll(System.getProperties());// This will copy all system properties to properties.
        // Any change here won't affect System properties.
        properties.setProperty(USER_HOME, "C:\\home");// This won't change System properties.
        System.out.printf("%s%n", System.getProperty(USER_HOME));
        System.out.printf("%s%n", properties.getProperty(USER_HOME));
        systemProperties.setProperty(USER_HOME, "C:\\home");// This will change System properties
        System.out.printf("%s%n", System.getProperty(USER_HOME));
        loadFromResource();
        loadFromFile();
    }

    private static void loadFromResource() {
        Properties properties;
        System.out.printf(SEPARATOR);
        properties = new Properties();
        System.out.println("Reading from resources folder");

        var classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("trial.properties");

        if (url != null)
            try (var stream = url.openStream()) {
                properties.load(stream);
            } catch (IOException e) {
                System.out.println("Error occured when opening the stream. " + e);
            }

        properties.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private static void loadFromFile() {
        String pathString = "src/main/resources/trial.properties";
        Properties properties;
        System.out.printf(SEPARATOR);
        properties = new Properties();

        try (var inputStream = Files.newInputStream(Paths.get(pathString));
             var stream = new BufferedInputStream(inputStream)) {
            properties.load(stream);// the properties will be added to the existing properties object.
            System.setProperties(properties);// This cleans all the initial properties in System
            System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
