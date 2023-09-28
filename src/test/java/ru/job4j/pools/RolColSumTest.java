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
        int colSum0 = sum(array)[0].getColSum();
        int colSum1 = sum(array)[1].getColSum();
        int colSum2 = sum(array)[2].getColSum();
        int rowSum0 = sum(array)[0].getRowSum();
        int rowSum1 = sum(array)[1].getRowSum();
        int rowSum2 = sum(array)[2].getRowSum();
        Integer expectedRowSum0 = 6;
        Integer expectedRowSum1 = 13;
        Integer expectedRowSum2 = 24;
        Integer expectedColSum0 = 12;
        Integer expectedColSum1 = 13;
        Integer expectedColSum2 = 18;
        assertThat(colSum0).isEqualTo(expectedColSum0);
        assertThat(colSum1).isEqualTo(expectedColSum1);
        assertThat(colSum2).isEqualTo(expectedColSum2);
        assertThat(rowSum0).isEqualTo(expectedRowSum0);
        assertThat(rowSum1).isEqualTo(expectedRowSum1);
        assertThat(rowSum2).isEqualTo(expectedRowSum2);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] array =
                {
                        {1, 2, 3},
                        {4, 3, 6},
                        {7, 8, 9}
                };
        int colSum0 = asyncSum(array)[0].getColSum();
        int colSum1 = asyncSum(array)[1].getColSum();
        int colSum2 = asyncSum(array)[2].getColSum();
        int rowSum0 = asyncSum(array)[0].getRowSum();
        int rowSum1 = asyncSum(array)[1].getRowSum();
        int rowSum2 = asyncSum(array)[2].getRowSum();
        Integer expectedRowSum0 = 6;
        Integer expectedRowSum1 = 13;
        Integer expectedRowSum2 = 24;
        Integer expectedColSum0 = 12;
        Integer expectedColSum1 = 13;
        Integer expectedColSum2 = 18;
        assertThat(colSum0).isEqualTo(expectedColSum0);
        assertThat(colSum1).isEqualTo(expectedColSum1);
        assertThat(colSum2).isEqualTo(expectedColSum2);
        assertThat(rowSum0).isEqualTo(expectedRowSum0);
        assertThat(rowSum1).isEqualTo(expectedRowSum1);
        assertThat(rowSum2).isEqualTo(expectedRowSum2);
    }
}