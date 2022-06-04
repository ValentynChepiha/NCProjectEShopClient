package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigUrl;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.DCurrency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories.DCurrencyRepository;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoadCurrencyService {

    @Autowired
    private DCurrencyRepository dCurrencyRepository;

    public String loadCurrency() {
        return LoadService.load( ConfigUrl.URLLoadExchangeRate );
    }

    public Exchange filterListCurrency(Exchange dataExchange) {

//        Integer[] arrayCurrencyNeed = (Integer[]) dCurrencyRepository.findAllR030().toArray();
//        System.out.println( " load array :: " + arrayCurrencyNeed);

//        Set<Integer> currencyNeed = new HashSet<>(Arrays.asList( arrayCurrencyNeed ));

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


