package com.glebestraikh.carfactory.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final ConcurrentHashMap<Class<?>, AtomicLong> counters = new ConcurrentHashMap<>();

    private IdGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static long nextId(Class<?> clazz) {
        return counters.computeIfAbsent(clazz, c -> new AtomicLong(1))
                .getAndIncrement();
    }
}
