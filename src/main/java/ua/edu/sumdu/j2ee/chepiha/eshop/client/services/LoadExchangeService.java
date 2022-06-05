package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigUrl;
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

    @Autowired
    private DCurrencyRepository dCurrencyRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public String loadCurrency() {
        return LoadService.load( ConfigUrl.URLLoadExchangeRate );
    }

    public List<CurrencyRate> loadCurrencyRate() {
        return currencyRateRepository.findActualExchange();
    }

    public float getSelectedCurrencyRate (String cc) {

        List<CurrencyRate> currencyRateList = loadCurrencyRate();
        float result = 1;

        for(CurrencyRate currencyRate: currencyRateList) {
            if( currencyRate.getCc().equals(cc) ){
                result = (float) currencyRate.getRate();
            }
        }

        return result;
    }

    public Exchange filterListCurrency(Exchange dataExchange) {
        System.out.println("filterListCurrency :: start...");
        System.out.println("filterListCurrency :: come dataExchange " +  dataExchange.size() + " rows");

        List<DCurrency> dCurrencyList = dCurrencyRepository.findAll();
        System.out.println("filterListCurrency :: need R030 " +  dCurrencyList);

        Set<Integer> currencyNeed = new HashSet<>();
        for(DCurrency dCurrency: dCurrencyList){
            currencyNeed.add(dCurrency.getR030());
        }

        System.out.println("filterListCurrency :: added currencyNeed " +  currencyNeed.size() + " rows");

        Exchange resultExchange = new Exchange();
        for (Currency currency: dataExchange.getCurrencies()) {
            if( currencyNeed.contains(currency.getR030())){
                resultExchange.addCurrency(currency);
            }
        }
        return resultExchange;
    }

}


