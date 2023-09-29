package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i <= matrix.length - 1; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j <= matrix.length - 1; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i <= matrix.length - 1; i++) {
            int finalI = i;
            CompletableFuture<Sums> completableFutureSums = supplyAsync(() -> {
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j <= matrix.length - 1; j++) {
                    rowSum += matrix[finalI][j];
                    colSum += matrix[j][finalI];
                }
                return new Sums(rowSum, colSum);
            });
            sums[finalI] = completableFutureSums.get();
        }
        return sums;
    }
}