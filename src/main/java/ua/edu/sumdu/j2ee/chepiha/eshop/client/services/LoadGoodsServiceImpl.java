package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.LoadGoodsService;

import java.util.List;

@Service
public class LoadGoodsServiceImpl implements LoadGoodsService {

    private static final LoggerMsgService logger = new LoggerMsgService(LoadGoodsServiceImpl.class) ;

    private final LoadXMLService<Goods> loadXMLService;

    @Autowired
    public LoadGoodsServiceImpl(LoadXMLService<Goods> loadXMLService) {
        this.loadXMLService = loadXMLService;
    }

    @Override
    public Goods load(String url) {
        logger.msgInfo("load :: start...");
        Goods goods = new Goods();
        logger.msgDebug("load :: goods - " + goods);
        goods = loadXMLService.convertStringXMLToObject(
                LoadService.load( url ),
                goods,
                Goods.class,
                Product.class
        );
        logger.msgDebug("load :: loaded " + goods.size() + " rows");
        return goods;
    }

    @Override
    public Goods convertGoodsPriceUseExchangeRate(String url, float rate) {
        logger.msgInfo("convertGoodsPriceUseExchangeRate :: start...");
        logger.msgDebug("convertGoodsPriceUseExchangeRate : url - " + url);
        Goods goods = load( url );
        if(rate <= 0){
            return goods;
        }

        List<Product> productList = goods.getGoods();
        logger.msgDebug("convertGoodsPriceUseExchangeRate : productList, old price - " + productList);
        for(Product product: productList) {
            product.setPrice( ((float) Math.round( product.getPrice() * 100 / rate )) / 100 );
        }

        goods.setGoods(productList);
        logger.msgDebug("convertGoodsPriceUseExchangeRate : productList, new price - " + productList);
        return goods;
    }
    
}
