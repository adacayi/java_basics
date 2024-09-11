package com.sanver.basics.objects;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class ObjectEqualityBasedOnFields {
    // Instead of overriding hashcode and equals methods as shown below so that if fields are equal, equals return true and hashcode is the same,
    // we can use lombok @EqualsAndHashCode annotation. Note the hashcode with lombok value will be different from the below implementation.
    // Check the .class file for the lombok generated code.
    public static class Student {
        private String name;
        private int age;
        private String[] lessons;

        public Student(String name, int age, String[] lessons) {
            this.name = name;
            this.age = age;
            this.lessons = lessons;
        }

        @Override
        public int hashCode() {
            Field[] fields = this.getClass().getDeclaredFields();
            int i, n = fields.length;
            Object[] values = new Object[n];
            Object obj;
            for (i = 0; i < n; i++) {
                try {
                    obj = fields[i].get(this);
                    if (fields[i].getType().isArray()) {
                        values[i] = arrayHashCode(obj);
                    } else
                        values[i] = obj;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return Arrays.hashCode(values);
        }

        @Override
        public boolean equals(Object obj) {
            if (!this.getClass().isInstance(obj))
                return false;

            Field[] fields = this.getClass().getDeclaredFields();
            Object first, second;
            for (Field field : fields) {
                try {
                    first = field.get(this);
                    second = field.get(obj);
                    if (field.getType().isArray()) {
                        if (!Objects.deepEquals(first, second))
                            return false;
                    } else if (!Objects.equals(first, second))
                        return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    public static int arrayHashCode(Object obj) {
        if (obj instanceof Object[])
            return Arrays.deepHashCode((Object[]) obj);
        if (obj instanceof byte[])
            return Arrays.hashCode((byte[]) obj);
        if (obj instanceof short[])
            return Arrays.hashCode((short[]) obj);
        if (obj instanceof int[])
            return Arrays.hashCode((int[]) obj);
        if (obj instanceof long[])
            return Arrays.hashCode((long[]) obj);
        if (obj instanceof char[])
            return Arrays.hashCode((char[]) obj);
        if (obj instanceof float[])
            return Arrays.hashCode((float[]) obj);
        if (obj instanceof double[])
            return Arrays.hashCode((double[]) obj);
        return Arrays.hashCode((boolean[]) obj);
    }

    public static void main(String... args) {
        Student first = new Student("Ahmet", 23, new String[]{"Maths", "Science", "Sports"});
        Student second = new Student("Ahmet", 23, new String[]{"Maths", "Science", "Sports"});
        System.out.printf("%s\n", first.equals(second));
        System.out.printf("%d %d \nFirst hashcode equals second = %s\n", first.hashCode(), second.hashCode(),
                first.hashCode() == second.hashCode());
    }
}
