package com.sanver.basics.collections;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

class CustomHashMapTest {
    static List<Arguments> getSizeAndExpectedCapacity() {
        var hashMapDefaultInitialCapacity = 16;
        var hashMapDefaultLoadFactor = 0.75;
        var firstThreshold = (int) (hashMapDefaultInitialCapacity * hashMapDefaultLoadFactor);
        return IntStream.range(1, 385).mapToObj(i -> Arguments.of(i,
                i > firstThreshold ? (int) (((int) Math.pow(2, (int) log(2, (i - 1) / firstThreshold) + 1)) * firstThreshold / hashMapDefaultLoadFactor) : hashMapDefaultInitialCapacity)).collect(Collectors.toList());
    }

    static double log(int base, int number) {
        return Math.log(number) / Math.log(base);
    }

    @Nested
    class Size {
        @Test
        void givenEmptyHashMap_size_shouldReturnZero() {
            // Given
            var hashMap = new CustomHashMap<>();
            // When
            var size = hashMap.size();

            // Then
            assertThat(size).isEqualTo(0);
        }

        @Test
        void givenOneElementHashMap_size_shouldReturnOne() {
            // Given
            var hashMap = new CustomHashMap<>();
            hashMap.put(new Object(), new Object());

            // When
            var size = hashMap.size();

            // Then
            assertThat(size).isEqualTo(1);
        }

        @Test
        void given100ElementHashMap_size_shouldReturn100() {
            // Given
            var hashMap = new CustomHashMap<>();
            IntStream.range(0, 100).forEach(i -> hashMap.put(new Object(), new Object()));

            // When
            var size = hashMap.size();

            // Then
            assertThat(size).isEqualTo(100);
        }
    }

    @Nested
    class Capacity {
        @Test
        void givenEmptyHashMap_capacity_shouldReturnZero() {
            // Given
            var hashMap = new CustomHashMap<>();
            // When
            var capacity = hashMap.capacity();

            // Then
            assertThat(capacity).isEqualTo(0);
        }

        @ParameterizedTest
        @MethodSource("com.sanver.basics.collections.CustomHashMapTest#getSizeAndExpectedCapacity")
        void givenNonEmptyHashMap_capacity_shouldReturnAsExpected(int size, int expectedCapacity) {
            // Given
            var hashMap = new CustomHashMap<>();
            IntStream.range(0, size).forEach(i -> hashMap.put(new Object(), new Object()));

            // When
            var capacity = hashMap.capacity();

            // Then
            assertThat(capacity).isEqualTo(expectedCapacity);
        }
    }

    @Nested
    class Put {
        @Test
        void givenNonExistingKey_put_shouldPutEntryAndReturnNull() {
            // Given
            var key = new Object();
            var value = new Object();
            var hashMap = new CustomHashMap<>();

            // When
            var result = hashMap.put(key, value);

            // Then
            assertThat(result).isNull();
            assertThat(hashMap.get(key)).isSameAs(value);
        }

        @Test
        void givenExistingKey_put_shouldPutEntryAndReturnOldValue() {
            // Given
            var key = new Object();
            var oldValue = new Object();
            var newValue = new Object();
            var hashMap = new CustomHashMap<>();
            hashMap.put(key, oldValue);

            // When
            var result = hashMap.put(key, newValue);

            // Then
            assertThat(result).isSameAs(oldValue);
            assertThat(hashMap.get(key)).isSameAs(newValue);
        }

        @Test
        void givenKeysWithSameHashButDifferentValue_put_shouldPutEntries() {
            // Given
            var map = new CustomHashMap<Integer, Integer>();
            var entriesWithSameIndex = new int[][]{{1, 3}, {33, 5}, {65, 10}};

            // When
            for (var entry : entriesWithSameIndex) {
                map.put(entry[0], entry[1]);
            }

            // Then
            for (var entry : entriesWithSameIndex) {
                assertThat(map.get(entry[0])).isEqualTo(entry[1]);
            }
            assertThat(map.size()).isEqualTo(entriesWithSameIndex.length);
        }
    }

    @Nested
    class Get {
        @Test
        void givenEmptyMap_get_shouldReturnNull() {
            // Given
            var hashMap = new CustomHashMap<>();

            // When
            var result = hashMap.get(new Object());

            // Then
            assertThat(result).isNull();
        }

        @Test
        void givenEmptiedMap_get_shouldReturnNull() {
            // Given
            var hashMap = new CustomHashMap<>();
            var key = new Object();
            hashMap.put(key, new Object());
            hashMap.remove(key);

            // When
            var result = hashMap.get(new Object());

            // Then
            assertThat(result).isNull();
        }

        @Test
        void givenNonExistingKey_get_shouldReturnNull() {
            // Given
            var hashMap = new CustomHashMap<>();
            IntStream.range(1, 12).forEach(i -> hashMap.put(new Object(), new Object()));

            // When
            var result = hashMap.get(new Object());

            // Then
            assertThat(result).isNull();
        }

        @Test
        void givenExistingKey_get_shouldReturnValue() {
            // Given
            var hashMap = new CustomHashMap<>();
            IntStream.range(0, 100).forEach(i -> hashMap.put(new Object(), new Object()));
            var key = new Object();
            var value = new Object();
            hashMap.put(key, value);

            // When
            var result = hashMap.get(key);

            // Then
            assertThat(result).isSameAs(value);
        }

        @Test
        void givenExistingKeys_get_shouldReturnValues() {
            // Given
            var hashMap = new CustomHashMap<>();
            var entries = IntStream.range(0, 100).mapToObj(i -> new Object[]{new Object(), new Object()}).collect(Collectors.toList());
            entries.forEach(entry -> hashMap.put(entry[0], entry[1]));

            // When
            var results = entries.stream().map(entry -> new Object[]{entry[0], hashMap.get(entry[0])}).collect(Collectors.toList());

            // Then
            assertThat(results).containsExactlyElementsOf(entries);
        }
    }

    @Nested
    class Remove {

        @Test
        void givenEmptyHashMap_remove_doesNothingAndReturnsNull() {
            // Given
            var hashMap = new CustomHashMap<>();

            // When
            var result = hashMap.remove(new Object());

            // Then
            assertThat(result).isNull();
            assertThat(hashMap.entrySet()).isEmpty();
        }

        @Test
        void givenEmptiedHashMap_remove_doesNothingAndReturnsNull() {
            // Given
            var hashMap = new CustomHashMap<>();
            var key = new Object();
            hashMap.put(key, new Object());
            hashMap.remove(key);

            // When
            var result = hashMap.remove(new Object());

            // Then
            assertThat(result).isNull();
            assertThat(hashMap.entrySet()).isEmpty();
        }

        @Test
        void givenNonExistingKey_remove_doesNothingAndReturnsNull() {
            // Given
            var hashMap = new CustomHashMap<>();
            var entries = IntStream.range(0, 13).mapToObj(i-> new Object[]{new Object(), new Object()}).collect(Collectors.toList());
            entries.forEach(entry -> hashMap.put(entry[0], entry[1]));

            // When
            var result = hashMap.remove(new Object());

            // Then
            assertThat(result).isNull();
            var resultAsObjectArrayList = hashMap.entrySet().stream().map(entry -> new Object[]{entry.getKey(), entry.getValue()}).collect(Collectors.toList());
            assertThat(resultAsObjectArrayList).containsExactlyInAnyOrderElementsOf(entries);
        }

        @Test
        void givenExistingKey_remove_removesTheEntryAndReturnsTheValue() {
            // Given
            var hashMap = new CustomHashMap<>();
            var entries = IntStream.range(0, 13).mapToObj(i-> new Object[]{new Object(), new Object()}).collect(Collectors.toList());
            entries.forEach(entry -> hashMap.put(entry[0], entry[1]));
            var entryToRemove = entries.get(3);

            // When
            var result = hashMap.remove(entryToRemove[0]);

            // Then
            assertThat(result).isEqualTo(entryToRemove[1]);
            var resultAsObjectArrayList = hashMap.entrySet().stream().map(entry -> new Object[]{entry.getKey(), entry.getValue()}).collect(Collectors.toList());
            assertThat(resultAsObjectArrayList).containsExactlyInAnyOrderElementsOf(entries.stream().filter(x -> !x.equals(entryToRemove)).collect(Collectors.toList()));
        }
    }

    @Nested
    class KeySet {
        @Test
        void givenEmptyHashMap_keySet_shouldReturnEmptySet() {
            // Given
            var hashMap = new CustomHashMap<>();

            // When
            var result = hashMap.keySet();

            // Then
            assertThat(result).isNotNull();
            assertThat(result).isEmpty();
        }

        @Test
        void givenNonEmptyHashMap_keySet_shouldReturnKeys() {
            // Given
            var entries = IntStream.range(0, 100).mapToObj(x -> new Object[]{new Object(), new Object()}).collect(Collectors.toList());
            var keys = entries.stream().map(entry -> entry[0]).collect(Collectors.toList());
            var hashMap = new CustomHashMap<>();
            entries.forEach(entry -> hashMap.put(entry[0], entry[1]));

            // When
            var result = hashMap.keySet();

            // Then
            assertThat(result).isNotNull();
            assertThat(result).containsExactlyInAnyOrderElementsOf(keys);
        }

        @Test
        void keyset_isImmutable() {
            // Given
            var entries = IntStream.range(0, 100).mapToObj(x -> new Object[]{new Object(), new Object()}).collect(Collectors.toList());
            var hashMap = new CustomHashMap<>();
            entries.forEach(entry -> hashMap.put(entry[0], entry[1]));

            // When
            var result = hashMap.keySet();

            // Then
            assertThat(result).isNotNull();
            assertThatThrownBy(() -> result.add(new Object())).isInstanceOf(UnsupportedOperationException.class);
            assertThatThrownBy(() -> result.remove(entries.get(0)[0])).isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    class EntrySet {
        @Test
        void givenEmptyHashMap_entrySet_shouldReturnEmptySet() {
            // Given
            var hashMap = new CustomHashMap<>();

            // When
            var result = hashMap.entrySet();

            // Then
            assertThat(result).isNotNull();
            assertThat(result).isEmpty();
        }

        @Test
        void givenNonEmptyHashMap_entrySet_shouldReturnEntries() {
            // Given
            var entries = IntStream.range(0, 100).mapToObj(x -> new Object[]{new Object(), new Object()}).collect(Collectors.toList());
            var hashMap = new CustomHashMap<>();
            entries.forEach(entry -> hashMap.put(entry[0], entry[1]));

            // When
            var result = hashMap.entrySet();

            // Then
            assertThat(result).isNotNull();
            var resultAsObjectArrayList = result.stream().map(entry -> new Object[]{entry.getKey(), entry.getValue()}).collect(Collectors.toList());
            assertThat(resultAsObjectArrayList).containsExactlyInAnyOrderElementsOf(entries);
        }

        @Test
        void entrySet_isImmutable() {
            // Given
            var entries = IntStream.range(0, 100).mapToObj(x -> new Object[]{new Object(), new Object()}).collect(Collectors.toList());
            var hashMap = new CustomHashMap<>();
            entries.forEach(entry -> hashMap.put(entry[0], entry[1]));

            // When
            var result = hashMap.entrySet();

            // Then
            assertThat(result).isNotNull();
            var exception = catchThrowable(() ->
                    result.add(new CustomHashMap.Node<>(new Object(), new Object(), 0, null)));
            assertThat(exception).isInstanceOf(UnsupportedOperationException.class);
            assertThatThrownBy(() -> result.remove(result.toArray()[0])).isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
