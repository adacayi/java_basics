package com.sanver.basics.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DynamicProxySample {
//  Source:https://www.baeldung.com/java-dynamic-proxies

  //  Simply put, proxies are fronts or wrappers that pass function invocation through their own facilities
//  (usually onto real methods) – potentially adding some functionality.
//
//  Dynamic proxies allow one single class with one single method to service multiple method calls to arbitrary classes
//  with an arbitrary number of methods. A dynamic proxy can be thought of as a kind of Facade,
//  but one that can pretend to be an implementation of any interface.
//  Under the cover, it routes all method invocations to a single handler – the invoke() method.

  public static void main(String[] args) {
    var target = new HashMap<String, String>();
    var proxyInstance = (Map<String, String>) Proxy.newProxyInstance(DynamicProxySample.class.getClassLoader(),
        new Class[]{Map.class},
        invocationHandler(target));
    proxyInstance.put("key", "value");
    System.out.println(proxyInstance.get("key"));
  }

  private static InvocationHandler invocationHandler(Object target) {
    return (proxy, method, args) -> {
      var startTime = LocalDateTime.now();
      System.out.printf("Execution of %s started: %s\n",
          method.getName(),
          startTime.format(DateTimeFormatter.ISO_LOCAL_TIME));

      var result = method.invoke(target, args);

      var endTime = LocalDateTime.now();
      System.out.printf("Execution of %s ended: %s\n",
          method.getName(),
          endTime.format(DateTimeFormatter.ISO_LOCAL_TIME));

      var duration = Duration.between(startTime, endTime);
      System.out.printf("Time elapsed is %02d:%03d\n", duration.getSeconds(), duration.getNano() / 1000000);

      return result;
    };
  }
}



