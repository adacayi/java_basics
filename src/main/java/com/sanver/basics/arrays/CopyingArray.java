package com.sanver.basics.arrays;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.sanver.basics.utils.ArrayPrinter.printArrays;

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
    public static <T> T[] arrayDeepCopyOf(T[] original, int newLength) {
        T[] copy = (original.getClass() == Object[].class) ? (T[]) new Object[newLength] : (T[]) Array.newInstance(original.getClass().getComponentType(), newLength);

        Class<?> eClass;
        int min = Math.min(original.length, newLength);

        for (int i = 0; i < min; i++) {
            eClass = original[i].getClass();

            if (eClass.isArray()) {
                if (eClass == byte[].class)
                    copy[i] = (T) Arrays.copyOf((byte[]) original[i], ((byte[]) original[i]).length);
                else if (eClass == short[].class)
                    copy[i] = (T) Arrays.copyOf((short[]) original[i], ((short[]) original[i]).length);
                else if (eClass == int[].class)
                    copy[i] = (T) Arrays.copyOf((int[]) original[i], ((int[]) original[i]).length);
                else if (eClass == long[].class)
                    copy[i] = (T) Arrays.copyOf((long[]) original[i], ((long[]) original[i]).length);
                else if (eClass == char[].class)
                    copy[i] = (T) Arrays.copyOf((char[]) original[i], ((char[]) original[i]).length);
                else if (eClass == float[].class)
                    copy[i] = (T) Arrays.copyOf((float[]) original[i], ((float[]) original[i]).length);
                else if (eClass == double[].class)
                    copy[i] = (T) Arrays.copyOf((double[]) original[i], ((double[]) original[i]).length);
                else if (eClass == boolean[].class)
                    copy[i] = (T) Arrays.copyOf((boolean[]) original[i], ((boolean[]) original[i]).length);
                else { // element is an array of object references
                    copy[i] = (T) arrayDeepCopyOf((Object[]) original[i], ((Object[]) original[i]).length);
                }
            } else
                copy[i] = original[i]; // This actually needs to be a deep copy of an object if we want the array objects to be different if they are not primitive types
        }

        return copy;
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
