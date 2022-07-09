package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;

public interface LoadGoodsService {
    Goods load(String url);

    Goods convertGoodsPriceUseExchangeRate(String url, float rate);
}
