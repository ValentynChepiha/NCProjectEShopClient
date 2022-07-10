package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

public interface ModelRepository <T> extends ModelFindRepository<T> {

    void create(T entity);
    void update(T entity);
    void delete(long id);

}
