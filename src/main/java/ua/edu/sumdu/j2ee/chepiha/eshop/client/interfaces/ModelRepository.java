package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import java.util.List;

public interface ModelRepository <T> {

    void create(T entity);
    void update(T entity);
    void delete(long id);

    List<T> findAll();

    T findOne(long id);
    T findOne(int r030);

}
