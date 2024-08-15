package com.sanver.basics.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.CompletableFuture;

public class MDCSample {

    public static final String USER_NAME = "userName";

    public static void main(String[] args) {
        // To output the value in MDC we put %X{userName} in the pattern in the logback.xml
        // https://www.baeldung.com/mdc-in-log4j-2-logback
        var logger = LoggerFactory.getLogger(MDCSample.class);
        MDC.put(USER_NAME, "John");
        logger.info("Something happened");
        print();
        MDC.put(USER_NAME, "Mark");
        MDC.put("traceId", "2");
        var map = MDC.getCopyOfContextMap();
        print();
        CompletableFuture.runAsync(() -> {
            print();
            MDC.setContextMap(map);
            print();
            MDC.put(USER_NAME, "Jane");
            print();
            MDC.remove(USER_NAME);
            print();

        }).join();
        print();
        MDC.clear();
        print();
    }

    private static void print() {
        System.out.println("Thread Id: " + Thread.currentThread().getId() + ", MDC Context: " + MDC.getCopyOfContextMap());
    }
}
