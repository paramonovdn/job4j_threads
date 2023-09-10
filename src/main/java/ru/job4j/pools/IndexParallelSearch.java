package ru.job4j.pools;

import ru.job4j.cache.OptimisticException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexParallelSearch<T> extends RecursiveTask<List<Integer>> {

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
    protected List<Integer> compute() {
        List<Integer> result = new ArrayList<>();
        if (to - from < 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(element)) {
                    result.add(i);
                }
            }
        } else {
            int mid = (from + to) / 2;
            IndexParallelSearch leftSearch = new IndexParallelSearch(array, from, mid, element);
            IndexParallelSearch rightSearch = new IndexParallelSearch(array, mid + 1, to, element);
            leftSearch.fork();
            rightSearch.fork();
            List<Integer> left = (List<Integer>) leftSearch.join();
            List<Integer> right = (List<Integer>) rightSearch.join();
            result.addAll(left);
            result.addAll(right);
        }
        if (result.size() == 0) {
            throw new OptimisticException("Element not found");
        }
        return result;
    }

    public List<Integer> getIndex(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (List<Integer>) forkJoinPool.invoke(new IndexParallelSearch(array, 0, array.length - 1, element));
    }
}
