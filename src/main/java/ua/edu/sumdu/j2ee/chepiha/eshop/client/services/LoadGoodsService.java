package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigUrl;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;

@Service
public class LoadGoodsService {

    @Autowired
    LoadXMLService<Goods> loadXMLService;
    
    public Goods load() {

        System.out.println("LoadProductService.load :: start...");
        Goods goods = new Goods();
        goods = loadXMLService.convertStringXMLToObject(
                LoadService.load( ConfigUrl.URLLoadGoods ),
                goods,
                Goods.class,
                Product.class
        );

        System.out.println("LoadProductService.load :: loaded " + goods.size() + " rows");

        return goods;
    }
    
}
