package com.sanver.basics.arrays;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;

import java.util.Arrays;

import static com.sanver.basics.utils.ArrayUtils.arrayDeepCopyOf;
import static com.sanver.basics.utils.ArrayUtils.printArrays;

public class CopyingArray {
    public static void main(String[] args) {
        int[] a;
        int[] b = {3, 2, 1};
        int[][] b1 = {{0, 1, 3}, {2, 4}, {5, 2}};
        int[][] a1;
        a = Arrays.copyOf(b, 5);
        b[1] = 4;
        printArrays(a, b);
        a1 = Arrays.copyOf(b1, 4);
        b1[0][0] = 7;// Note that a1 changes as well when changing an element of an inner array of b1.
        printArrays(a1, b1);
        a1 = b1.clone();
        System.out.println(a1 == b1);
        b1[0][0] = 8;// Note that a1 changes as well when changing an element of an inner array of b1.
        printArrays(a1, b1);
        System.arraycopy(b1, 0, a1, 0, b1.length);
        b1[0][0] = 9;// Note that a1 changes as well when changing an element of an inner array of b1.
        printArrays(a1, b1);
        a1 = arrayDeepCopyOf(b1, b1.length);
        b1[0][0] = 2;
        printArrays(a1, b1);
        var people = new Person[][][]{{{new Person("John", 22), new Person("Ashley", 25)}}};
        var copyPeople = arrayDeepCopyOf(people, people.length);
        printArrays(people, copyPeople);
        people[0][0][0].setAge(40);
        printArrays(people, copyPeople);
        var copyPeople2 = deepCopy(people); // As can be seen this deepCopy method can be used instead of arrayDeepCopy, and it is not specific to arrays
      /* The advantages of using Gson instead of ObjectMapper are
        1- It doesn't require a default constructor for the class to be deserialized
        2- It doesn't require getters for fields for serialization (If there is no getter for a field, its value is not serialized. If no getter exists at all, objectMapper.writeValueAsString will throw an InvalidDefinitionException: No serializer found for class)
        https://www.baeldung.com/java-deep-copy
        */
        people[0][0][0].setAge(80);
        printArrays(people, copyPeople2);
    }

    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object) {
        var gson = new Gson();
        return (T) gson.fromJson(gson.toJson(object), object.getClass());
    }

    @Data
    static class Person {
        @NonNull String name;
        @NonNull int age;
    }
}
