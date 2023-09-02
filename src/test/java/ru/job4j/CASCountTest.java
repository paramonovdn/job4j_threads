package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CASCountTest {
    @Test
    public void whenManyIncrementAndOneGet() throws InterruptedException {
        CASCount count = new CASCount();
        Thread t1 = new Thread(
                () -> {
                    for (int i = 0; i < 33; i++) {
                        count.increment();
                    }
                }
        );
        Thread t2 = new Thread(
                () -> {
                    for (int i = 0; i < 33; i++) {
                        count.increment();
                    }
                }
        );
        Thread t3 = new Thread(
                () -> {
                    for (int i = 0; i < 33; i++) {
                        count.increment();
                    }
                }
        );
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        assertThat(count.get()).isEqualTo(99);
    }
}
