package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            v = new Base(model.getId(), model.getVersion() + 1);
            v.setName(model.getName());
            return v;
        }) == null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    @Override
    public String toString() {
        return "Cache{"
                + "memory=" + memory
                + '}';
    }
}