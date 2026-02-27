package raf.aleksabuncic.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SortUtils {
    private static final ConcurrentHashMap<Class<?>, Set<String>> fieldCache = new ConcurrentHashMap<>();

    public static boolean isInvalidField(Class<?> entityClass, String fieldName) {
        if (fieldName == null) return true;

        Set<String> fields = fieldCache.computeIfAbsent(entityClass, clazz ->
                Arrays.stream(clazz.getDeclaredFields())
                        .map(Field::getName)
                        .collect(Collectors.toSet())
        );

        return !fields.contains(fieldName);
    }
}