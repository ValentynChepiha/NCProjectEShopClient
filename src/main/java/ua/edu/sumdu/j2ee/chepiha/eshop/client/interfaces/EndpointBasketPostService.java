package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;

import java.util.Map;

public interface EndpointBasketPostService {
    String getSelectedCurrency();

    void setSelectedCurrency(String newSelectedCurrency);

    Map<Long, Integer> getMapIdCount();

    void setMapIdCount(Map<Long, Integer> newMapIdCount);

    Goods getGoods();

    void setGoods(Goods newGoods);

    boolean start(String orderBody);

    String urlGoHome();
}
