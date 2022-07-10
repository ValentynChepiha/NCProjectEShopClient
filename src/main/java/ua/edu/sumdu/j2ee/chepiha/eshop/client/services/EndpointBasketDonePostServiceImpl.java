package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.EndpointBasketDonePostService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ParseBasketDataValueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EndpointBasketDonePostServiceImpl implements EndpointBasketDonePostService {

    private final ParseBasketDataValueService parseBasketDataValueService;
    private final StringBuilder selectedCurrency;
    private final Map<String, String> clientInfoMap;
    private final Map<Long, Integer> idCountMap;

    @Autowired
    public EndpointBasketDonePostServiceImpl(ParseBasketDataValueService parseBasketDataValueService) {
        this.parseBasketDataValueService = parseBasketDataValueService;
        selectedCurrency = new StringBuilder();
        clientInfoMap = new HashMap<>();
        idCountMap = new HashMap<>();
    }

    @Override
    public String getSelectedCurrency() {
        return selectedCurrency.toString();
    }

    @Override
    public void setSelectedCurrency(String newSelectedCurrency) {
        selectedCurrency.setLength(0);
        selectedCurrency.append(newSelectedCurrency);
    }

    @Override
    public Map<Long, Integer> getIdCountMap() {
        return idCountMap;
    }

    @Override
    public Map<String, String> getClientInfoMap() {
        return clientInfoMap;
    }

    @Override
    public void setClientInfoMap(Map<String, String> ClientInfoMap) {
        clientInfoMap.clear();
        clientInfoMap.putAll(ClientInfoMap);
    }

    @Override
    public void setIdCountMap(Map<Long, Integer> newIdCountMap) {
        idCountMap.clear();
        idCountMap.putAll(newIdCountMap);
    }

    @Override
    public boolean start(String orderBody) {
        log.info("EndpointBasketDonePostServiceImpl.start :: starting...");

        List<String> listParams = parseBasketDataValueService.setStringToListString(orderBody, "&");
        setSelectedCurrency( parseBasketDataValueService.getSelectedCurrency(listParams) );
        setIdCountMap( parseBasketDataValueService.getMapSelectedIdCount(parseBasketDataValueService.getSelectedProducts(listParams)) );
        setClientInfoMap( parseBasketDataValueService.getClientInfo(listParams) );

        if( idCountMap.size() == 0 && clientInfoMap.size() != 4 ) {
            return false;
        }

        log.debug("/basket/done: selectedCurrency - " + getSelectedCurrency() );
        log.debug("/basket/done: idCountMap - " + getIdCountMap() );
        log.debug("/basket/done: clientInfoMap - " + getClientInfoMap() );
        return true;
    }

    @Override
    public String urlGoHome() {
        return  "redirect:/" + getSelectedCurrency();
    }

    @Override
    public Map<String, String> prepareMapToUpload() {
        Map<String, String> requestParams = new HashMap<>();
        for (Long key: idCountMap.keySet()) {
            requestParams.put("item_"+key, idCountMap.get(key).toString());
        }
        for (String key: clientInfoMap.keySet()) {
            requestParams.put(key, clientInfoMap.get(key));
        }
        return requestParams;
    }

}
