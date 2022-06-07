package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigApp;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyRate;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.DCurrency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories.CurrencyRateRepository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories.DCurrencyRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoadExchangeService {

    private static final LoggerMsgService logger = new LoggerMsgService(LoadExchangeService.class) ;

    @Autowired
    private DCurrencyRepository dCurrencyRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public String loadCurrency() {
        return LoadService.load( ConfigApp.URL_LOAD_EXCHANGE_RATE);
    }

    public List<CurrencyRate> loadCurrencyRate() {
        return currencyRateRepository.findActualExchange();
    }

    public float getSelectedCurrencyRate (String cc) {

        logger.msgDebug("getSelectedCurrencyRate: input data - " + cc);
        List<CurrencyRate> currencyRateList = loadCurrencyRate();
        float result = 1;

        logger.msgDebug("getSelectedCurrencyRate: load list - " + currencyRateList);
        for(CurrencyRate currencyRate: currencyRateList) {
            if( currencyRate.getCc().equals(cc) ){
                result = (float) currencyRate.getRate();
            }
        }

        logger.msgDebug("getSelectedCurrencyRate: result rate - " + result);
        return result;
    }

    public Exchange filterListCurrency(Exchange dataExchange) {
        logger.msgDebug("filterListCurrency: input data - " + dataExchange);

        List<DCurrency> dCurrencyList = dCurrencyRepository.findAll();
        logger.msgDebug("filterListCurrency: need code R030 - " +  dCurrencyList);

        Set<Integer> currencyNeed = new HashSet<>();
        for(DCurrency dCurrency: dCurrencyList){
            currencyNeed.add(dCurrency.getR030());
        }

        logger.msgDebug("filterListCurrency: currencyNeed - " +  currencyNeed);
        Exchange resultExchange = new Exchange();
        for (Currency currency: dataExchange.getCurrencies()) {
            if( currencyNeed.contains(currency.getR030())){
                resultExchange.addCurrency(currency);
            }
        }
        logger.msgDebug("filterListCurrency: currencyNeed - " +  resultExchange);
        return resultExchange;
    }

}


