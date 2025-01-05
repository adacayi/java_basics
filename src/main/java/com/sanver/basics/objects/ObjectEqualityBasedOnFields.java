package com.sanver.basics.objects;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

/**
 * The main purpose of this class is to show how <code>Objects.equals()</code> and <code>Objects.deepEquals()</code> work
 */
public class ObjectEqualityBasedOnFields {
    public static void main(String... args) {
        Student first = new Student("Ahmet", 23, new String[]{"Maths", "Science", "Sports"});
        Student second = new Student("Ahmet", 23, new String[]{"Maths", "Science", "Sports"});
        System.out.printf("%s%n", first.equals(second));
        System.out.printf("%d %d %nFirst hashcode equals second = %s%n", first.hashCode(), second.hashCode(),
                first.hashCode() == second.hashCode());
    }

    // Instead of overriding hashcode and equals methods as shown below so that if fields are equal, equals return true and hashcode is the same,
    // we can use lombok @EqualsAndHashCode annotation. Note the hashcode with lombok value will be different from the below implementation.
    // Check the .class file for the lombok generated code.
    public static class Student {
        private String name;
        private int age;
        private String[] lessons;

        public Student(final String name, final int age, final String[] lessons) {
            this.name = name;
            this.age = age;
            this.lessons = lessons;
        }

        @Override
        public int hashCode() {
            Field[] fields = this.getClass().getDeclaredFields();
            int i;
            int n = fields.length;
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
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof Student)) {
                return false;
            }

            Field[] fields = this.getClass().getDeclaredFields();
            Object first;
            Object second;
            for (Field field : fields) {
                try {
                    first = field.get(this);
                    second = field.get(obj);
                    if (field.getType().isArray()) {
                        if (!Objects.deepEquals(first, second)) {
                            return false;
                        }
                    } else if (!Objects.equals(first, second)) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    public static int arrayHashCode(Object obj) {
        if (obj instanceof Object[] objectArray)
            return Arrays.deepHashCode(objectArray);
        if (obj instanceof byte[] byteArray)
            return Arrays.hashCode(byteArray);
        if (obj instanceof short[] shortArray)
            return Arrays.hashCode(shortArray);
        if (obj instanceof int[] intArray)
            return Arrays.hashCode(intArray);
        if (obj instanceof long[] longArray)
            return Arrays.hashCode(longArray);
        if (obj instanceof char[] charArray)
            return Arrays.hashCode(charArray);
        if (obj instanceof float[] floatArray)
            return Arrays.hashCode(floatArray);
        if (obj instanceof double[] doubleArray)
            return Arrays.hashCode(doubleArray);
        return Arrays.hashCode((boolean[]) obj);
    }
}
