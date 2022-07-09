package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class ParseBasketDataValue {

    @Value("${client.default.currency}")
    private String defaultCurrency;

    public List<String> setStringToListString (String data, String separator) {
        return Arrays.asList( data.split(separator) ) ;
    }

    public String getSelectedCurrency(List<String> listParams) {
         return listParams.
                    stream().
                    filter( item -> "currencySelector".equals(item.split("=")[0])).
                    collect(Collectors.toList()).
                    stream().
                    map(item -> item.split("=")[1]).
                    findFirst().
                    orElse(defaultCurrency);
    }

    public List<String> getSelectedProducts(List<String> listParams) {
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
            final Long id;
            final Integer count;

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

    public Map<String, String> getClientInfo (List<String> listParams) {
        class StringPair {
            final String key;
            final String value;

            public StringPair(String key, String value) {
                this.key = key;
                this.value = value;
            }

            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }
        }
        return listParams.
                stream().
                filter( item -> !"item".equals(item.split("_")[0]) ).
                collect(Collectors.toList()).
                stream().
                filter( item -> !"currencySelector".equals(item.split("=")[0]) ).
                map( item -> {
                            int idx = item.indexOf("=");
                            try {
                                return new StringPair(
                                            URLDecoder.decode(item.substring(0, idx), "UTF-8"),
                                            URLDecoder.decode(item.substring(idx + 1), "UTF-8")
                                        );
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                ).
                filter(Objects::nonNull).
                collect(Collectors.toMap(StringPair::getKey, StringPair::getValue));
    }

}
