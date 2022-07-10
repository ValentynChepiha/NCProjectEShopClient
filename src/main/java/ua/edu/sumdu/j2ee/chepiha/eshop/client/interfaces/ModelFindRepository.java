package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import java.util.List;

public interface ModelFindRepository<T> {

    List<T> findAll();

    T findOne(long id);
    T findOne(int r030);
}
