package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import java.util.List;

public interface ModelExchangeRepository<T> extends ModelRepository<T> {

    Integer checkLastDateUpdate();

}
