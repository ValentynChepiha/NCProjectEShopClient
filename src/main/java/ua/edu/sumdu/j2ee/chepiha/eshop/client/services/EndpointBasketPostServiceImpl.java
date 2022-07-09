package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.EndpointBasketPostService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.LoadExchangeService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.LoadGoodsService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ParseBasketDataValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class EndpointBasketPostServiceImpl implements EndpointBasketPostService {

    private static final LoggerMsgService logger = new LoggerMsgService(EndpointBasketPostServiceImpl.class) ;

    private final ParseBasketDataValue parseBasketDataValue;
    private final LoadGoodsService loadGoodsService;
    private final LoadExchangeService loadExchangeService;
    private final StringBuilder selectedCurrency;
    private final Map<Long, Integer> mapIdCount;
    private final Goods goods;

    @Value("${api.url.load.goods}")
    private String urlLoadGoods;

    @Autowired
    public EndpointBasketPostServiceImpl(ParseBasketDataValue parseBasketDataValue, LoadGoodsService loadGoodsService,
                                         LoadExchangeService loadExchangeService) {
        this.parseBasketDataValue = parseBasketDataValue;
        this.loadGoodsService = loadGoodsService;
        this.loadExchangeService = loadExchangeService;
        selectedCurrency = new StringBuilder();
        goods = new Goods();
        mapIdCount = new HashMap<>();
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
    public Map<Long, Integer> getMapIdCount() {
        return mapIdCount;
    }

    @Override
    public void setMapIdCount(Map<Long, Integer> newMapIdCount) {
        mapIdCount.clear();
        mapIdCount.putAll(newMapIdCount);
    }

    @Override
    public Goods getGoods() {
        return goods;
    }

    @Override
    public void setGoods(Goods newGoods) {
        goods.clear();
        goods.addAll(newGoods);
    }

    @Override
    public boolean start(String orderBody) {
        logger.msgInfo("start :: start...");

        List<String> listParams = parseBasketDataValue.setStringToListString(orderBody, "&");
        setSelectedCurrency( parseBasketDataValue.getSelectedCurrency( listParams ) );
        List<String> selectedProducts = parseBasketDataValue.getSelectedProducts(listParams);

        logger.msgDebug("/basket: selectedCurrency - " + selectedCurrency);
        logger.msgDebug("/basket: selectedProducts - " + selectedProducts);

        if(selectedProducts.size() == 0){
            return false;
        }

        setMapIdCount( parseBasketDataValue.getMapSelectedIdCount(selectedProducts) );
        if( mapIdCount.size()==0 ){
            return false;
        }
        setGoods(loadGoodsService.convertGoodsPriceUseExchangeRate(
                urlLoadSelectedGoods(),
                loadExchangeService.getSelectedCurrencyRate(selectedCurrency.toString())
        ));
        return true;
    }

    @Override
    public String urlGoHome() {
        return  "redirect:/" + selectedCurrency;
    }

    private String urlLoadSelectedGoods () {
        List<Long> listId = parseBasketDataValue.getListSelectedId(mapIdCount);
        return urlLoadGoods + "/" + parseBasketDataValue.concatenateId(listId, ",");
    }

}
