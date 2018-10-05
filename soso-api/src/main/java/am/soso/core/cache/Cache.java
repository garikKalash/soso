package am.soso.core.cache;

import java.util.Map;

/**
 * Created by Garik Kalashyan on 22-Feb-18.
 */
public interface Cache<T> {
    T getById(Integer id);
    void put(T obj);
    Map<Integer,T> loadAll();
    void putAll(Map<Integer, T> data);
    void refreshAll();
    boolean remove(Integer id);
    void load(Integer... ids);
}
