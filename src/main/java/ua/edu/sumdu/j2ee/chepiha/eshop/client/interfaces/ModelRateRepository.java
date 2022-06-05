package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import java.util.List;

public interface ModelRateRepository<T>  {

    List<T> findActualExchange();

}
