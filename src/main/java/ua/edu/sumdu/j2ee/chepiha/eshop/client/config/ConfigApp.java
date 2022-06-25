package ua.edu.sumdu.j2ee.chepiha.eshop.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.LoggerMsgService;

@Component
@PropertySource("classpath:application.properties")
public class ConfigApp {

    private static final LoggerMsgService logger = new LoggerMsgService(ConfigApp.class) ;

    @Value("${api.url.load.exchange.rate}")
    private String urlLoadExchangeRate;
    @Value("${api.url.load.goods}")
    private String urlLoadGoods;
    @Value("${api.url.create.order}")
    private String urlCreateOrder;
    @Value("${client.default.currency}")
    private String defaultCurrency;

    public ConfigApp() {

    }

    public String getUrlLoadExchangeRate() {
        logger.msgDebug("getUrlLoadExchangeRate :: " + urlLoadExchangeRate);
        return urlLoadExchangeRate;
    }

    public String getUrlLoadGoods() {
        logger.msgDebug("getUrlLoadGoods :: " + urlLoadGoods);
        return urlLoadGoods;
    }

    public String getUrlCreateOrder() {
        logger.msgDebug("getUrlCreateOrder :: " + urlCreateOrder);
        return urlCreateOrder;
    }

    public String getDefaultCurrency() {
        logger.msgDebug("getDefaultCurrency :: " + defaultCurrency);
        return defaultCurrency;
    }
}
