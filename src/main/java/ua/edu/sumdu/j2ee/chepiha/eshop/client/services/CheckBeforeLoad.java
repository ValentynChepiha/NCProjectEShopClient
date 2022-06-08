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

    private static final LoggerMsgService logger = new LoggerMsgService(CheckBeforeLoad.class) ;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    private LoadExchangeService loadExchangeService;
    @Autowired
    private LoadXMLService<Exchange> loadXMLService;

    public void checkUpdateExchangeRate() {
        logger.msgInfo("checkUpdateExchangeRate start...");
        if (currencyExchangeRepository.checkLastDateUpdate() > 0) {
            return;
        }

        Exchange exchange = new Exchange();
        exchange = loadXMLService.convertStringXMLToObject(
                loadExchangeService.loadCurrency(),
                exchange,
                Exchange.class,
                Currency.class);

        logger.msgDebug("checkUpdateExchangeRate :: loaded " + exchange.size() + " rows");
        if(exchange.size()==0){
            return;
        }

        Exchange exchangeFiltered = loadExchangeService.filterListCurrency(exchange);
        logger.msgDebug("checkUpdateExchangeRate :: filtered " + exchangeFiltered.size() + " rows");

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
