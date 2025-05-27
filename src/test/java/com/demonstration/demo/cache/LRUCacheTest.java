package com.demonstration.demo.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LRUCache Tests")
class LRUCacheTest {

    private LRUCache<String, Integer> cache;
    private static final int CAPACITY = 3;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(CAPACITY);
    }


    @Test
    void shouldPutAndGetValues() {
        cache.putValue("key1", 100);
        assertEquals(100, cache.getValue("key1"));
    }

    @Test
    void shouldRemoveOldestWhenExceedingCapacity() {
        cache.putValue("key1", 100);
        cache.putValue("key2", 200);
        cache.putValue("key3", 300);
        cache.putValue("key4", 400);

        assertNull(cache.getValue("key1"));
        assertEquals(400, cache.getValue("key4"));
    }

    @Test
    void shouldUpdateLRUOrderOnAccess() {
        cache.putValue("key1", 100);
        cache.putValue("key2", 200);
        cache.putValue("key3", 300);

        cache.getValue("key1");
        cache.putValue("key4", 400);

        assertEquals(100, cache.getValue("key1"));
        assertNull(cache.getValue("key2"));
    }

    @Test
    void shouldRemoveValue() {
        cache.putValue("key1", 100);
        cache.removeValue("key1");
        assertNull(cache.getValue("key1"));
    }

    @Test
    void shouldBeThreadSafeWithSynchronizedMethods() throws InterruptedException {
        final int numThreads = 10;
        final int operationsPerThread = 100;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    String key = "key" + (threadId * operationsPerThread + j) % 10;
                    cache.putValue(key, threadId * operationsPerThread + j);
                    cache.getValue(key);
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertTrue(cache.size() <= CAPACITY);
    }
}
