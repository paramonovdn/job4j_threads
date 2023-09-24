package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.cache.OptimisticException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class IndexParallelSearchTest {

    @Test
    public void whenSearchInteger() {
        Integer[] array = {1, 2, 3, 4, 5};
        Integer element = 1;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        Integer result = indexParallelSearch.getIndex(array, element);
        Integer expected = 0;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchIntegerMoreTenElements() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        Integer element = 25;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        Integer result = indexParallelSearch.getIndex(array, element);
        Integer expected = 24;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchDouble() {
        Double[] array = {1.0, 2.0, 3.0, 4.0, 5.0};
        Double element = 5.0;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        Integer result = indexParallelSearch.getIndex(array, element);
        Integer expected = 4;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchDoubleMoreTenElement() {
        Double[] array = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0};
        Double element = 15.0;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        Integer result = indexParallelSearch.getIndex(array, element);
        Integer expected = 14;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSearchCharacter() {
        Character[] array = {'a', 'b', 'c', 'd', 'e'};
        Character element = 'd';
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        Integer result = indexParallelSearch.getIndex(array, element);
        Integer expected = 3;
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
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        Integer element = 33;
        IndexParallelSearch indexParallelSearch = new IndexParallelSearch(array, 0, array.length - 1, element);
        assertThatThrownBy(() -> indexParallelSearch.getIndex(array, element))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Element not found");
    }


}
