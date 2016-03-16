package ch.opentrainingcenter.service;

public interface RepositoryService<T> {

    T find(Class<T> type, int id);

    T doSave(T t);

    T update(T t);

    void remove(Class<T> type, int id);

}
