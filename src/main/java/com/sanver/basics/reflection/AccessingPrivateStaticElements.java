package com.sanver.basics.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class AccessingPrivateStaticElements {
    private static float PI = 3.14f;
    private static final float PI_FINAL = 3.14f;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println(AccessingPrivateStaticElements.PI);
        Field piField = AccessingPrivateStaticElements.class.getDeclaredField("PI");
        piField.setAccessible(true);
        piField.set(null, 3.14159f);
        System.out.println(AccessingPrivateStaticElements.PI);

        // This part did not work
        System.out.println(AccessingPrivateStaticElements.PI_FINAL);
        piField = AccessingPrivateStaticElements.class.getDeclaredField("PI_FINAL");
        piField.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(piField, piField.getModifiers() & ~Modifier.FINAL);
        piField.set(null, 3.14159f);
        System.out.println(AccessingPrivateStaticElements.PI_FINAL);
    }
}
