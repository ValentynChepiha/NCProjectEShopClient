package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import java.util.Map;

public interface EndpointBasketDonePostService {
    String getSelectedCurrency();

    void setSelectedCurrency(String newSelectedCurrency);

    Map<Long, Integer> getIdCountMap();

    Map<String, String> getClientInfoMap();

    void setIdCountMap(Map<Long, Integer> dataMap);

    void setClientInfoMap(Map<String, String> dataMap);

    boolean start(String orderBody);

    String urlGoHome();

    Map<String, String> prepareMapToUpload();
}
