package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.pools.RolColSum.asyncSum;
import static ru.job4j.pools.RolColSum.sum;

public class RolColSumTest {
    @Test
    public void whenSum() {
        int[][] array =
                {
                        {1, 2, 3},
                        {4, 3, 6},
                        {7, 8, 9}
                };
        Sums[] expected = {
                new Sums(6, 12),
                new Sums(13, 13),
                new Sums(24, 18)
        };
        assertThat(sum(array)).isEqualTo(expected);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] array =
                {
                        {1, 2, 3},
                        {4, 3, 6},
                        {7, 8, 9}
                };
        Sums[] expected = {
                new Sums(6, 12),
                new Sums(13, 13),
                new Sums(24, 18)
        };
        assertThat(asyncSum(array)).isEqualTo(expected);
    }
}