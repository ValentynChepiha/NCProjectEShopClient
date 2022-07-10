package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.LoadGoodsService;

import java.util.List;

@Slf4j
@Service
public class LoadGoodsServiceImpl implements LoadGoodsService {

    private final ConversionService conversionService;

    @Autowired
    public LoadGoodsServiceImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Goods load(String url) {
        log.info("LoadGoodsServiceImpl.load :: starting...");
        Goods goods = new Goods();
        log.debug("load :: goods - " + goods);
        goods = conversionService.convert(LoadService.load( url ), Goods.class);
        log.debug("load :: loaded " + goods.size() + " rows");
        return goods;
    }

    @Override
    public Goods convertGoodsPriceUseExchangeRate(String url, float rate) {
        log.info("convertGoodsPriceUseExchangeRate :: start...");
        log.debug("convertGoodsPriceUseExchangeRate : url - " + url);
        Goods goods = load( url );
        if(rate <= 0){
            return goods;
        }

        List<Product> productList = goods.getGoods();
        log.debug("convertGoodsPriceUseExchangeRate : productList, old price - " + productList);
        for(Product product: productList) {
            product.setPrice( product.getPriceByRate(rate) );
        }

        goods.setGoods(productList);
        log.debug("convertGoodsPriceUseExchangeRate : productList, new price - " + productList);
        return goods;
    }
    
}
