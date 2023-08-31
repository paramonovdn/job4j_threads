package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CASCountTest {

    @Test
    public void whenManyIncrementAndOneGet() {
        CASCount count = new CASCount();
        for (int i = 0; i < 100; i++) {
            count.increment();
        }
        assertThat(count.get()).isEqualTo(100);
    }

    @Test
    public void whenSerialIncrementAndThenCheck() {
        CASCount count = new CASCount();
        assertThat(count.get()).isEqualTo(0);
        count.increment();
        assertThat(count.get()).isEqualTo(1);
        count.increment();
        assertThat(count.get()).isEqualTo(2);
        count.increment();
        assertThat(count.get()).isEqualTo(3);
        count.increment();
        assertThat(count.get()).isEqualTo(4);

    }
}
