package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigApp;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParseBasketDataValue {

    private List<String> listParams = new ArrayList<>();

    public void setStringToListString (String data, String separator) {
        listParams.clear();
        listParams.addAll( Arrays.asList( data.split(separator) ) );
    }

    public String getSelectedCurrency() {
         List<String> result = listParams.
                                    stream().
                                    filter( item -> "currencySelector".equals(item.split("=")[0])).
                                    collect(Collectors.toList());
         return result.size()>0 ? result.get(0).split("=")[1] : ConfigApp.DEFAULT_CURRENCY;
    }

    public List<String> getSelectedProducts() {
        return listParams.
                stream().
                filter(item ->  "item".equals(item.split("_")[0])).
                collect(Collectors.toList()).
                stream().
                filter(item -> item.split("=").length>1).
                collect(Collectors.toList());
    }

    public List<Long> getListSelectedId(Map<Long, Integer> mapIdCount) {
        return new ArrayList<>(mapIdCount.keySet());
    }

    public Map<Long, Integer> getMapSelectedIdCount(List<String> listItems) {
        class IdCount {
            Long id;
            Integer count;

            public IdCount(Long id, Integer count) {
                this.id = id;
                this.count = count;
            }

            public Long getId() {
                return id;
            }

            public Integer getCount() {
                return count;
            }
        }

        return listItems.
                stream().
                map(item -> new IdCount(
                        Long.valueOf((item.split("=")[0]).split("_")[1]),
                        Integer.valueOf(item.split("=")[1])
                )).
                filter(idCount -> idCount.getCount() > 0).
                collect(Collectors.toMap(IdCount::getId, IdCount::getCount));
    }

    public String concatenateId(List<Long> listId, String separator){
        return listId.
                stream().
                map(Object::toString).
                collect(Collectors.joining(separator));
    }

    public float getTotal(Map<Long, Integer> mapIdCount, List<Product> listProduct) {
        return (float) listProduct.
                        stream().
                        mapToDouble(product -> product.getAmount(mapIdCount.get(product.getId()))).
                        sum();
    }

}
