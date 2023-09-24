package ru.job4j.pools;

import ru.job4j.cache.OptimisticException;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private T element;

    public IndexParallelSearch(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        Integer index = 0;
        if ((to - from) < 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(element)) {
                    index = i;
                }
            }
            return index;
        }
        int mid = (from + to) / 2;
        IndexParallelSearch leftSearch = new IndexParallelSearch(array, from, mid, element);
        IndexParallelSearch rightSearch = new IndexParallelSearch(array, mid + 1, to, element);
        leftSearch.fork();
        rightSearch.fork();
        return (Integer) leftSearch.join() + (Integer) rightSearch.join();
    }


    public Integer getIndex(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = (Integer) forkJoinPool.invoke(new IndexParallelSearch(array, 0, array.length - 1, element));
        if (result == 0 && !array[0].equals(element)) {
            throw new OptimisticException("Element not found");
        }
        return result;
    }
}