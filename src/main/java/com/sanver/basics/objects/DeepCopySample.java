package com.sanver.basics.objects;

import com.google.gson.Gson;

import java.util.StringJoiner;

public class DeepCopySample {
    public static void main(String[] args) {
        var person1 = new Person("Kimi", 18);
        var person2 = deepCopy(person1);
        person1.name = "George";
        person1.age = 26;
        System.out.println(person1);
        System.out.println(person2);
    }

    public static <T> T deepCopy(T object) {
        /* The advantages of using Gson instead of ObjectMapper are
        1- It doesn't require a default constructor for the class to be deserialized
        2- It doesn't require getters for fields for serialization (If there is no getter for a field, its value is not serialized. If no getter exists at all, objectMapper.writeValueAsString will throw an InvalidDefinitionException: No serializer found for class)
        https://www.baeldung.com/java-deep-copy
        */
        var gson = new Gson();
        return gson.<T>fromJson(gson.toJson(object), object.getClass());
    }



    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("age=" + age)
                    .toString();
        }
    }
}
