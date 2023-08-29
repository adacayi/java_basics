package com.sanver.basics.comparing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Value;

public class ComparatorObjectsWithNullFieldsSample {

  private static final String PRODUCT_1 = "Product 1";
  private static final String PRODUCT_2 = "Product 2";

  @Value
  static class Product {

    String name;
    int code;
    String country;

    @Override
    public String toString() {
      return String.format("[%s, %s, %s]", name, code, country);
    }
  }

  public static void main(String[] args) {
    var product1 = new Product(PRODUCT_1, 3, null);
    var product2 = new Product(PRODUCT_1, 3, "US");
    var product3 = new Product(PRODUCT_1, 3, "IN");
    var product4 = new Product(PRODUCT_1, 2, "IN");
    var product5 = new Product(PRODUCT_2, 2, "IN");

    var list = new ArrayList<>(List.of(product1, product2, product3, product4, product5));
    var comparatorNullsFirst = Comparator
        .comparing(Product::getName)
        .thenComparing(Product::getCode)
        .thenComparing(Product::getCountry, Comparator.nullsFirst(Comparator.comparing(x -> x)));
    var comparatorNullsLast = Comparator
        .comparing(Product::getName)
        .thenComparing(Product::getCode)
        .thenComparing(Product::getCountry, Comparator.nullsLast(Comparator.comparing(x -> x)));
    list.sort(comparatorNullsFirst);
    System.out.println(list);
    list.sort(comparatorNullsLast);
    System.out.println(list);
  }
}
