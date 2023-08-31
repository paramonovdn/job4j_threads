package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class CacheTest {
    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        model1.setName("model1");
        Base model2 = new Base(2, 2);
        model2.setName("model2");
        cache.add(model1);
        cache.add(model2);
        String expected = new String("Cache{memory={1=Base{id=1, version=1, name='model1'}, "
                + "2=Base{id=2, version=2, name='model2'}}}");
        assertThat(cache.toString()).isEqualTo(expected);
    }

    @Test
    public void whenAddIdenticalId() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        model1.setName("model1");
        Base model2 = new Base(1, 2);
        model2.setName("model2");
        cache.add(model1);
        boolean result = cache.add(model2);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        model1.setName("model1");
        Base model2 = new Base(2, 2);
        model2.setName("model2");
        cache.add(model1);
        cache.add(model2);
        cache.delete(model2);
        String expected = new String("Cache{memory={1=Base{id=1, version=1, name='model1'}}}");
        assertThat(cache.toString()).isEqualTo(expected);
    }

    @Test
    public void whenUpdateEqualsVersion() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        model1.setName("model1");
        cache.add(model1);
        model1.setName("updateModel1");
        cache.update(model1);
        String expected = new String("Cache{memory={1=Base{id=1, version=2, name='updateModel1'}}}");
        assertThat(cache.toString()).isEqualTo(expected);
    }

    @Test
    public void whenUpdateNotEqualsVersion() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        model1.setName("model1");
        Base model1Version2 = new Base(1, 2);
        model1Version2.setName("model1Version2");
        cache.add(model1);
        assertThatThrownBy(() -> cache.update(model1Version2))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions are not equal");
    }
}
