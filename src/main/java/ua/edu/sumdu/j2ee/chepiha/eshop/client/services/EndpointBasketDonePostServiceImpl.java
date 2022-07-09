package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.controllers.MainController;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.EndpointBasketDonePostService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ParseBasketDataValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EndpointBasketDonePostServiceImpl implements EndpointBasketDonePostService {

    private static final LoggerMsgService logger = new LoggerMsgService(MainController.class) ;

    private final ParseBasketDataValue parseBasketDataValue;
    private final StringBuilder selectedCurrency;
    private final Map<String, String> clientInfoMap;
    private final Map<Long, Integer> idCountMap;

    @Autowired
    public EndpointBasketDonePostServiceImpl(ParseBasketDataValue parseBasketDataValue) {
        this.parseBasketDataValue = parseBasketDataValue;
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
        logger.msgInfo("start :: start...");

        List<String> listParams = parseBasketDataValue.setStringToListString(orderBody, "&");
        setSelectedCurrency( parseBasketDataValue.getSelectedCurrency(listParams) );
        setIdCountMap( parseBasketDataValue.getMapSelectedIdCount(parseBasketDataValue.getSelectedProducts(listParams)) );
        setClientInfoMap( parseBasketDataValue.getClientInfo(listParams) );

        if( idCountMap.size() == 0 && clientInfoMap.size() != 4 ) {
            return false;
        }

        logger.msgDebug("/basket/done: selectedCurrency - " + getSelectedCurrency() );
        logger.msgDebug("/basket/done: idCountMap - " + getIdCountMap() );
        logger.msgDebug("/basket/done: clientInfoMap - " + getClientInfoMap() );
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
