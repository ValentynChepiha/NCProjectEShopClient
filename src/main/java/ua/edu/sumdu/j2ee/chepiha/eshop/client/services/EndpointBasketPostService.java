package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigApp;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;

import java.util.List;
import java.util.Map;

@Service
public class EndpointBasketPostService {

    private static final LoggerMsgService logger = new LoggerMsgService(EndpointBasketPostService.class) ;

    @Autowired
    private ParseBasketDataValue parseBasketDataValue;
    @Autowired
    private LoadGoodsService loadGoodsService;
    @Autowired
    private LoadExchangeService loadExchangeService;

    private String selectedCurrency;
    private Map<Long, Integer> mapIdCount;
    private Goods goods;

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public Map<Long, Integer> getMapIdCount() {
        return mapIdCount;
    }

    public Goods getGoods() {
        return goods;
    }

    public boolean start (String orderBody) {
        logger.msgInfo("start :: start...");

        List<String> listParams = parseBasketDataValue.setStringToListString(orderBody, "&");

        selectedCurrency = parseBasketDataValue.getSelectedCurrency( listParams );
        List<String> selectedProducts = parseBasketDataValue.getSelectedProducts(listParams);

        logger.msgDebug("/basket: selectedCurrency - " + selectedCurrency);
        logger.msgDebug("/basket: selectedProducts - " + selectedProducts);

        if(selectedProducts.size() == 0){
            return false;
        }

        mapIdCount = parseBasketDataValue.getMapSelectedIdCount(selectedProducts);
        if( mapIdCount.size()==0 ){
            return false;
        }

        goods = loadGoodsService.convertGoodsPriceUseExchangeRate(
                urlLoadSelectedGoods(),
                loadExchangeService.getSelectedCurrencyRate(selectedCurrency) );

        return true;
    }

    public String urlGoHome () {
        return  "redirect:/" + selectedCurrency;
    }

    private String urlLoadSelectedGoods () {
        List<Long> listId = parseBasketDataValue.getListSelectedId(mapIdCount);
        return ConfigApp.URL_LOAD_GOODS + "/" + parseBasketDataValue.concatenateId(listId, ",");
    }

}
