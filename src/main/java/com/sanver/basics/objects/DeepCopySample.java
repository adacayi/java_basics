package com.sanver.basics.objects;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;

public class DeepCopySample {
    public static void main(String[] args) {
        var person1 = new Person("Johnny", 16);
        var person2 = deepCopy(person1);
        person1.setAge(1);
        System.out.println(person1);
        System.out.println(person2);
    }

    public static <T> T deepCopy(T object) {
        // The advantage of using Gson instead of ObjectMapper is, it doesn't require a default constructor for the class to be deserialized
        // https://www.baeldung.com/java-deep-copy
        var gson = new Gson();
        return gson.<T>fromJson(gson.toJson(object), object.getClass());
    }

    @Data
    static class Person {
        @NonNull String name;
        @NonNull int age;
    }
}
