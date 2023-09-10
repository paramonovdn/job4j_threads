package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.cache.OptimisticException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class IndexParallelSearchTest {

    @Test
    public void whenSearchInteger() {
        Integer[] array = {3, 2, 3, 4, 3};
        Integer element = 3;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        List<Integer> result = indexParallelSearch.getIndex(array, element);
        List<Integer> expected = new ArrayList<>(Arrays.asList(0, 2, 4));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchDouble() {
        Double[] array = {3.0, 2.0, 3.0, 4.0, 3.0};
        Double element = 3.0;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        List<Integer> result = indexParallelSearch.getIndex(array, element);
        List<Integer> expected = new ArrayList<>(Arrays.asList(0, 2, 4));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchCharacter() {
        Character[] array = {'a', 'b', 'c', 'd', 'e'};
        Character element = 'd';
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        List<Integer> result = indexParallelSearch.getIndex(array, element);
        List<Integer> expected = new ArrayList<>(Arrays.asList(3));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchBoolean() {
        Boolean[] array = {true, false, true, false, true};
        Boolean element = true;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        List<Integer> result = indexParallelSearch.getIndex(array, element);
        List<Integer> expected = new ArrayList<>(Arrays.asList(0, 2, 4));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchIntegerMoreTenElements() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer element = 9;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        List<Integer> result = indexParallelSearch.getIndex(array, element);
        List<Integer> expected = new ArrayList<>(Arrays.asList(8, 18));
        assertThat(result).isEqualTo(expected);
    }


    @Test
    public void whenSearchIntegerAndCatchException() {
        Integer[] array = {1, 2, 3};
        Integer element = 4;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        assertThatThrownBy(() -> indexParallelSearch.getIndex(array, element))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Element not found");
    }

    @Test
    public void whenSearchIntegerMoreTenElementsAndCatchException() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer element = 12;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        assertThatThrownBy(() -> indexParallelSearch.getIndex(array, element))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Element not found");
    }


}
