package com.sanver.basics.optional;

import java.util.Optional;

public class OptionalFilterSample {

  class A {

    B b;
  }

  class B {

    String value;
  }

  public static void main(String[] args) {
    var a = new OptionalFilterSample().new A();
    printExtension(getExtension(a));
    a.b = new OptionalFilterSample().new B();
    printExtension(getExtension(a));
    a.b.value = "something";
    printExtension(getExtension(a));
    a.b.value = "file.txt";
    printExtension(getExtension(a));
  }

  private static void printExtension(Optional<String> extensionResult) {
    if (extensionResult.isPresent()) {
      System.out.println(extensionResult.get());
    } else {
      System.out.println("No extension found");
    }
  }

  private static Optional<String> getExtension(A a) {
    return Optional.ofNullable(a.b)
        .filter(x -> Optional.ofNullable(x.value).filter(y -> y.contains(".")).isPresent())
        .map(x -> x.value.substring(x.value.indexOf(".")));
  }
}
