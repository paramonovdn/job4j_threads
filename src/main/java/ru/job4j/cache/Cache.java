package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        if (stored.getVersion() != model.getVersion()) {
            throw new OptimisticException("Versions are not equal");
        }
        Base newModel = new Base(model.getId(), model.getVersion() + 1);
        newModel.setName(model.getName());
        return memory.computeIfPresent(model.getId(), (k, v) -> v = newModel) == null;
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