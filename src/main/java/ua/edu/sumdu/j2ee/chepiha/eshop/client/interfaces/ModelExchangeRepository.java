package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

public interface ModelExchangeRepository<T> extends ModelRepository<T> {

    Integer checkLastDateUpdate();

}
