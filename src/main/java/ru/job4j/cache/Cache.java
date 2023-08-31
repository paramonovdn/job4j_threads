package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        Base newModel = new Base(model.getId(), model.getVersion() + 1);
        newModel.setName(model.getName());

        BiFunction<Integer, Integer, Base> versionControl =
                ((storedVersion, modelVersion) -> storedVersion != modelVersion ? stored : newModel);

        if (stored.equals(versionControl.apply(stored.getVersion(), model.getVersion()))) {
            throw new OptimisticException("Versions are not equal");
        }

        return !stored.equals(memory.computeIfPresent(model.getId(), (k, v) ->
                versionControl.apply(v.getVersion(), model.getVersion())));
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