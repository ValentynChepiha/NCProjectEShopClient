package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;

import java.util.List;
import java.util.Map;

public interface ParseBasketDataValueService {
    List<String> setStringToListString(String data, String separator);

    String getSelectedCurrency(List<String> listParams);

    List<String> getSelectedProducts(List<String> listParams);

    List<Long> getListSelectedId(Map<Long, Integer> mapIdCount);

    Map<Long, Integer> getMapSelectedIdCount(List<String> listItems);

    String concatenateId(List<Long> listId, String separator);

    float getTotal(Map<Long, Integer> mapIdCount, List<Product> listProduct);

    Map<String, String> getClientInfo(List<String> listParams);
}
