package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ParseBasketDataValueService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class ParseBasketDataValueServiceImpl implements ParseBasketDataValueService {

    @Value("${client.default.currency}")
    private String defaultCurrency;

    private final ConversionService conversionService;

    @Autowired
    public ParseBasketDataValueServiceImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public List<String> setStringToListString(String data, String separator) {
        log.debug("setStringToListString :: data: " + data + "; separator: " + separator);
        return Arrays.asList( data.split(separator) ) ;
    }

    @Override
    public String getSelectedCurrency(List<String> listParams) {
        log.debug("getSelectedCurrency :: listParams: " + listParams.toString());
         return listParams.
                    stream().
                    filter( item -> "currencySelector".equals(item.split("=")[0])).
                    collect(Collectors.toList()).
                    stream().
                    map(item -> item.split("=")[1]).
                    findFirst().
                    orElse(defaultCurrency);
    }

    @Override
    public List<String> getSelectedProducts(List<String> listParams) {
        log.debug("getSelectedProducts :: listParams: " + listParams.toString());
        return listParams.
                stream().
                filter(item ->  "item".equals(item.split("_")[0])).
                collect(Collectors.toList()).
                stream().
                filter(item -> item.split("=").length>1).
                collect(Collectors.toList());
    }

    @Override
    public List<Long> getListSelectedId(Map<Long, Integer> mapIdCount) {
        log.debug("getListSelectedId :: mapIdCount: " + conversionService.convert(mapIdCount, String.class));
        return new ArrayList<>(mapIdCount.keySet());
    }

    @Override
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

        log.debug("getMapSelectedIdCount :: listItems: " + listItems.toString());
        return listItems.
                stream().
                map(item -> new IdCount(
                        Long.valueOf((item.split("=")[0]).split("_")[1]),
                        Integer.valueOf(item.split("=")[1])
                )).
                filter(idCount -> idCount.getCount() > 0).
                collect(Collectors.toMap(IdCount::getId, IdCount::getCount));
    }

    @Override
    public String concatenateId(List<Long> listId, String separator){
        log.debug("concatenateId :: listId: " + listId + "; separator: " + separator);
        return listId.
                stream().
                map(Object::toString).
                collect(Collectors.joining(separator));
    }

    @Override
    public float getTotal(Map<Long, Integer> mapIdCount, List<Product> listProduct) {
        log.debug("getTotal :: mapIdCount: " + conversionService.convert(mapIdCount, String.class)
                    + "; listProduct: " + listProduct);
        return (float) listProduct.
                        stream().
                        mapToDouble(product -> product.getAmount(mapIdCount.get(product.getId()))).
                        sum();
    }

    @Override
    public Map<String, String> getClientInfo(List<String> listParams) {
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

        log.debug("getClientInfo :: listParams: " + listParams.toString());
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
                                log.debug("getClientInfo :: error decode: idx: " + idx + "; item: " + item);
                                return null;
                            }
                        }
                ).
                filter(Objects::nonNull).
                collect(Collectors.toMap(StringPair::getKey, StringPair::getValue));
    }

}
