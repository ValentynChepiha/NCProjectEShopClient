package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyExchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories.CurrencyExchangeRepository;

import java.text.ParseException;

@Service
public class CheckBeforeLoad {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    private LoadCurrencyService loadCurrencyService;

    public void checkUpdateExchangeRate() {

        if (currencyExchangeRepository.checkLastDateUpdate()>0) {
            return;
        }

        System.out.println("checkUpdate :: start...");
        Exchange exchange = XMLService.convertStringXMLToObject( loadCurrencyService.loadCurrency() );

        System.out.println("checkUpdate :: loaded " + exchange.size() + " rows");
        if(exchange.size()==0){
            return;
        }

        Exchange exchangeFiltered = loadCurrencyService.filterListCurrency(exchange);
        System.out.println("checkUpdate :: filtered " + exchangeFiltered.size() + " rows");

        try {
            for(Currency currency: exchangeFiltered.getCurrencies()) {
                currencyExchangeRepository.create(
                        new CurrencyExchange(
                                currency.getR030(),
                                currency.getRate(),
                                ConvertStringToDate.parseStringToDate(currency.getExchangedate()))
                );
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
