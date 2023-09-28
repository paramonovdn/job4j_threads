package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

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
            CompletableFuture<Integer> rowSumCompletable = CompletableFuture.supplyAsync(() -> {
                int rowSum = 0;
                for (int j = 0; j <= matrix.length - 1; j++) {
                    rowSum += matrix[finalI][j];
                }
                return rowSum;
            });
            int finalI1 = i;
            CompletableFuture<Integer> colSumCompletable = CompletableFuture.supplyAsync(() -> {
                int colSum = 0;
                for (int j = 0; j <= matrix.length - 1; j++) {
                    colSum += matrix[j][finalI1];
                }
                return colSum;
            });
            sums[i] = new Sums(rowSumCompletable.get(), colSumCompletable.get());
        }
        return sums;
    }
}