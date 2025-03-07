package com.sanver.basics.objects;

/**
 * This class demonstrates the equality behavior of wrapper classes in Java, specifically focusing on
 * the difference between `==` operator (reference equality) and the `.equals()` method (value equality).
 * It also covers the concept of caching in wrapper classes like Integer and how it affects the behavior
 * of the `==` operator.
 *
 * <p><b>Wrapper Classes:</b></p>
 * <p>Wrapper classes (like Integer, Long, Boolean, Character, etc.) are used to represent primitive
 * data types as objects. This is useful when you need to work with primitives in collections or other
 * object-oriented contexts.</p>
 *
 * <p><b>Equality:</b></p>
 * <ul>
 *   <li><b>`==` Operator:</b> When used with objects, `==` checks for <i>reference equality</i>. It returns
 *   `true` if both operands refer to the exact same object in memory.</li>
 *   <li><b>`.equals()` Method:</b> The `.equals()` method, when properly overridden in a class, checks for
 *   <i>value equality</i>. For wrapper classes, it returns `true` if the two objects represent the
 *   same primitive value.</li>
 * </ul>
 *
 * <p><b>Caching in Wrapper Classes:</b></p>
 * <p>Java employs a caching mechanism for certain commonly used values in some wrapper classes, particularly
 * `Integer`. This caching can lead to unexpected behavior when using `==`.</p>
 * <ul>
 *   <li><b>Integer Caching:</b> Integer values from -128 to 127 (inclusive) are cached. This means that if
 *   you create two `Integer` objects with values within this range, and those values are same they might be the same object.
 *   they might refer to the same object in memory (same reference). Consequently, `==` will return `true`
 *   for these cached values.</li>
 *   <li><b>Values Outside the Cache:</b> For `Integer` values outside the -128 to 127 range, each new `Integer`
 *   object created with the same value will be a distinct object. In this case, `==` will return `false`,
 *   even though `.equals()` will return `true`.</li>
 *   <li><b>Other Wrapper Classes:</b> Caching may also be present in other wrapper classes, such as `Character` (for a
 *   limited range of values) and `Boolean` (always caches `true` and `false`).</li>
 * </ul>
 *
 * <p><b>Best Practices:</b></p>
 * <ul>
 *   <li><b>Use `.equals()` for Value Equality:</b> To compare the values of wrapper objects, always use the
 *   `.equals()` method. This method is designed to check for value equality and will consistently return
 *   the expected result, regardless of caching.</li>
 *   <li><b>Be Cautious with `==`:</b> Avoid using `==` with wrapper objects unless you specifically need
 *   to check for reference equality and you are fully aware of the caching behavior.</li>
 * </ul>
 */
public class WrapperObjectEquality {

    public static void main(String[] args) {
        // Boolean Examples
        System.out.println("--- Boolean Examples ---");
        System.out.printf("%n--- Reference Equality (Boolean Caching) ---%n");
        Boolean bool1 = true;
        System.out.println("Boolean bool1 = true;");
        Boolean bool2 = true;
        System.out.println("Boolean bool2 = true;");
        System.out.println("bool1 == bool2: " + (bool1 == bool2)); // true (cached)
        System.out.println("bool1.equals(bool2): " + bool1.equals(bool2)); // true (value equality)

        System.out.println();
        Boolean bool3 = false;
        System.out.println("Boolean bool3 = false;");
        Boolean bool4 = false;
        System.out.println("Boolean bool4 = false;");
        System.out.println("bool3 == bool4: " + (bool3 == bool4)); // true (cached)
        System.out.println("bool3.equals(bool4): " + bool3.equals(bool4)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Boolean bool5 = new Boolean(true);
        System.out.println("Boolean bool5 = new Boolean(true);");
        Boolean bool6 = new Boolean(true);
        System.out.println("Boolean bool6 = new Boolean(true);");

        System.out.println("bool5 == bool6: " + (bool5 == bool6)); // false
        System.out.println("bool5.equals(bool6): " + bool5.equals(bool6)); // true

        // Byte Examples
        System.out.printf("%n--- Byte Examples ---%n");
        System.out.printf("%n--- Reference Equality within the cache range (-128 to 127) ---%n");
        Byte byte1 = 127;
        System.out.println("Byte byte1 = 127;");
        Byte byte2 = 127;
        System.out.println("Byte byte2 = 127;");
        System.out.println("byte1 == byte2: " + (byte1 == byte2)); // true (cached)
        System.out.println("byte1.equals(byte2): " + byte1.equals(byte2)); // true (value equality)

        System.out.println();
        Byte byte3 = -128;
        System.out.println("Byte byte3 = -128;");
        Byte byte4 = -128;
        System.out.println("Byte byte4 = -128;");
        System.out.println("byte3 == byte4: " + (byte3 == byte4)); // true (cached)
        System.out.println("byte3.equals(byte4): " + byte3.equals(byte4)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Byte byte5 = new Byte((byte) 127);
        System.out.println("Byte byte5 = new Byte((byte)127);");
        Byte byte6 = new Byte((byte) 127);
        System.out.println("Byte byte6 = new Byte((byte)127);");
        System.out.println("byte5 == byte6: " + (byte5 == byte6)); // false
        System.out.println("byte5.equals(byte6): " + byte5.equals(byte6)); // true

        // Short Examples
        System.out.printf("%n--- Short Examples ---%n");
        System.out.printf("%n--- Reference Equality within the cache range (-128 to 127) ---%n");
        Short short1 = 127;
        System.out.println("Short short1 = 127;");
        Short short2 = 127;
        System.out.println("Short short2 = 127;");
        System.out.println("short1 == short2: " + (short1 == short2)); // true (cached)
        System.out.println("short1.equals(short2): " + short1.equals(short2)); // true (value equality)

        System.out.printf("%n--- Reference Equality outside the cache range (-128 to 127) ---%n");
        Short short3 = 128;
        System.out.println("Short short3 = 128;");
        Short short4 = 128;
        System.out.println("Short short4 = 128;");
        System.out.println("short3 == short4: " + (short3 == short4)); // false (not cached)
        System.out.println("short3.equals(short4): " + short3.equals(short4)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Short short5 = new Short((short) 127);
        System.out.println("Short short5 = new Short((short)127);");
        Short short6 = new Short((short) 127);
        System.out.println("Short short6 = new Short((short)127);");
        System.out.println("short5 == short6: " + (short5 == short6)); // false
        System.out.println("short5.equals(short6): " + short5.equals(short6)); // true

        // Character Examples
        System.out.printf("%n--- Character Examples ---%n");
        System.out.printf("%n--- Reference Equality within the likely cache range (ASCII: 0 to 127) ---%n");
        Character char1 = 127;
        System.out.println("Character char1 = 127;");
        Character char2 = 127;
        System.out.println("Character char2 = 127;");
        System.out.println("char1 == char2: " + (char1 == char2)); // true (cached for some values)
        System.out.println("char1.equals(char2): " + char1.equals(char2)); // true (value equality)

        System.out.printf("%n--- Reference Equality outside the likely cache range (ASCII: 0 to 127) ---%n");
        Character char3 = 128;
        System.out.println("Character char3 = 128;");
        Character char4 = 128;
        System.out.println("Character char4 = 128;");
        System.out.println("char3 == char4: " + (char3 == char4)); // false (not cached)
        System.out.println("char3.equals(char4): " + char3.equals(char4)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Character char5 = new Character('a');
        System.out.println("Character char5 = new Character('a');");
        Character char6 = new Character('a');
        System.out.println("Character char6 = new Character('a');");

        System.out.println("char5 == char6: " + (char5 == char6)); // false
        System.out.println("char5.equals(char6): " + char5.equals(char6)); // true

        // Integer Examples
        System.out.printf("%n--- Integer Examples ---%n");
        System.out.printf("%n--- Reference Equality within the cache range (-128 to 127) ---%n");
        Integer int1 = 127;
        System.out.println("Integer int1 = 127;");
        Integer int2 = 127;
        System.out.println("Integer int2 = 127;");
        System.out.println("int1 == int2: " + (int1 == int2)); // true (cached)
        System.out.println("int1.equals(int2): " + int1.equals(int2)); // true (value equality)

        System.out.printf("%n--- Reference Equality outside the cache range (-128 to 127) ---%n");
        Integer int3 = 128;
        System.out.println("Integer int3 = 128;");
        Integer int4 = 128;
        System.out.println("Integer int4 = 128;");
        System.out.println("int3 == int4: " + (int3 == int4)); // false (not cached)
        System.out.println("int3.equals(int4): " + int3.equals(int4)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Integer int5 = new Integer(127);
        System.out.println("Integer int5 = new Integer(127);");
        Integer int6 = new Integer(127);
        System.out.println("Integer int6 = new Integer(127);");
        System.out.println("int5 == int6: " + (int5 == int6)); // false
        System.out.println("int5.equals(int6): " + int5.equals(int6)); // true

        // Long Examples
        System.out.printf("%n--- Long Examples ---%n");
        System.out.printf("%n--- Reference Equality within the cache range (-128 to 127) ---%n");
        Long long1 = 127L;
        System.out.println("Long long1 = 127L;");
        Long long2 = 127L;
        System.out.println("Long long2 = 127L;");
        System.out.println("long1 == long2: " + (long1 == long2)); // true (cached, may depend on the implementation)
        System.out.println("long1.equals(long2): " + long1.equals(long2)); // true (value equality)

        System.out.printf("%n--- Reference Equality outside the cache range (-128 to 127) ---%n");
        Long long3 = 128L;
        System.out.println("Long long3 = 128L;");
        Long long4 = 128L;
        System.out.println("Long long4 = 128L;");
        System.out.println("long3 == long4: " + (long3 == long4)); // false (outside cache, may depend on the implementation)
        System.out.println("long3.equals(long4): " + long3.equals(long4)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Long long5 = new Long(127);
        System.out.println("Long long5 = new Long(127);");
        Long long6 = new Long(127);
        System.out.println("Long long6 = new Long(127);");

        System.out.println("long5 == long6: " + (long5 == long6)); // false
        System.out.println("long5.equals(long6): " + long5.equals(long6)); // true

        // Float Examples
        System.out.printf("%n--- Float Examples ---%n");
        System.out.printf("%n--- Reference Equality (No caching available) ---%n");
        Float float1 = 127.0f;
        System.out.println("Float float1 = 127.0f;");
        Float float2 = 127.0f;
        System.out.println("Float float2 = 127.0f;");
        System.out.println("float1 == float2: " + (float1 == float2)); // false (not cached)
        System.out.println("float1.equals(float2): " + float1.equals(float2)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Float float3 = new Float(127.0f);
        System.out.println("Float float3 = new Float(127.0f);");
        Float float4 = new Float(127.0f);
        System.out.println("Float float4 = new Float(127.0f);");
        System.out.println("float3 == float4: " + (float3 == float4)); // false
        System.out.println("float3.equals(float4): " + float3.equals(float4)); // true

        // Double Examples
        System.out.printf("%n--- Double Examples ---%n");
        System.out.printf("%n--- Reference Equality (No caching available) ---%n");
        Double double1 = 127.0;
        System.out.println("Double double1 = 127.0;");
        Double double2 = 127.0;
        System.out.println("Double double2 = 127.0;");
        System.out.println("double1 == double2: " + (double1 == double2)); // false (not cached)
        System.out.println("double1.equals(double2): " + double1.equals(double2)); // true (value equality)

        System.out.printf("%n--- Reference Equality with the new keyword ---%n");
        Double double3 = new Double(127.0);
        System.out.println("Double double3 = new Double(127.0);");
        Double double4 = new Double(127.0);
        System.out.println("Double double4 = new Double(127.0);");
        System.out.println("double3 == double4: " + (double3 == double4)); // false
        System.out.println("double3.equals(double4): " + double3.equals(double4)); // true
    }
}