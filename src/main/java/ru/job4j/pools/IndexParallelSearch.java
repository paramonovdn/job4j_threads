package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public IndexParallelSearch(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if ((to - from) < 10) {
            return linearSearchIndex();
        }
        int mid = (from + to) / 2;
        IndexParallelSearch leftSearch = new IndexParallelSearch(array, from, mid, element);
        IndexParallelSearch rightSearch = new IndexParallelSearch(array, mid + 1, to, element);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = (Integer) leftSearch.join();
        Integer right = (Integer) rightSearch.join();
        return Math.max(left, right);
    }

    public Integer linearSearchIndex() {
        Integer result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(element)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static <T> Integer getIndex(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = (Integer) forkJoinPool.invoke(new IndexParallelSearch(array, 0, array.length - 1, element));
        return result;
    }
}