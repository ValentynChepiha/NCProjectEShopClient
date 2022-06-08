package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.controllers.MainController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EndpointBasketDonePostService {

    private static final LoggerMsgService logger = new LoggerMsgService(MainController.class) ;

    @Autowired
    private ParseBasketDataValue parseBasketDataValue;

    private String selectedCurrency;
    private Map<Long, Integer> idCountMap;
    private Map<String, String> clientInfoMap ;

    public Map<Long, Integer> getIdCountMap() {
        return idCountMap;
    }

    public Map<String, String> getClientInfoMap() {
        return clientInfoMap;
    }

    public boolean start (String orderBody) {
        logger.msgInfo("start :: start...");

        List<String> listParams = parseBasketDataValue.setStringToListString(orderBody, "&");
        selectedCurrency = parseBasketDataValue.getSelectedCurrency(listParams);
        idCountMap = parseBasketDataValue.getMapSelectedIdCount(parseBasketDataValue.getSelectedProducts(listParams));
        clientInfoMap = parseBasketDataValue.getClientInfo(listParams);

        if( idCountMap.size() == 0 && clientInfoMap.size() != 4 ) {
            return false;
        }

        logger.msgDebug("/basket/done: selectedCurrency - " + selectedCurrency);
        logger.msgDebug("/basket/done: idCountMap - " + idCountMap);
        logger.msgDebug("/basket/done: clientInfoMap - " + clientInfoMap);
        return true;
    }

    public String urlGoHome () {
        return  "redirect:/" + selectedCurrency;
    }

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
