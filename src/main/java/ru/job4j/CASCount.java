package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int value;
        int next;
        do {
            value = count.get();
            next = value + 1;
        } while (!count.compareAndSet(value, next));
    }

    public int get() {
        return count.get();
    }
}