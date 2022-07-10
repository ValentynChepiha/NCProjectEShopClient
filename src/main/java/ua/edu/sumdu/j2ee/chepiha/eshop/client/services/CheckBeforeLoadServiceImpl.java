package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyExchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.CheckBeforeLoadService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.LoadExchangeService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelExchangeRepository;

import java.sql.Date;

@Service
public class CheckBeforeLoadServiceImpl implements CheckBeforeLoadService {

    private static final LoggerMsgService logger = new LoggerMsgService(CheckBeforeLoadServiceImpl.class) ;

    private final ModelExchangeRepository<CurrencyExchange> currencyExchangeRepository;
    private final LoadExchangeService loadExchangeService;
    private final ConversionService conversionService;

    @Autowired
    public CheckBeforeLoadServiceImpl(ModelExchangeRepository<CurrencyExchange> currencyExchangeRepository,
                                      LoadExchangeService loadExchangeService, ConversionService conversionService) {
        this.currencyExchangeRepository = currencyExchangeRepository;
        this.loadExchangeService = loadExchangeService;
        this.conversionService = conversionService;
    }

    @Override
    public void checkUpdateExchangeRate() {
        logger.msgInfo("checkUpdateExchangeRate start...");
        if (currencyExchangeRepository.checkLastDateUpdate() > 0) {
            return;
        }
        Exchange exchange = new Exchange();
        exchange = conversionService.convert(loadExchangeService.loadCurrency(), Exchange.class);

        logger.msgDebug("checkUpdateExchangeRate :: loaded " + exchange.size() + " rows");
        if(exchange.size()==0){
            return;
        }

        Exchange exchangeFiltered = loadExchangeService.filterListCurrency(exchange);
        logger.msgDebug("checkUpdateExchangeRate :: filtered " + exchangeFiltered.size() + " rows");

        for(Currency currency: exchangeFiltered.getCurrencies()) {
            currencyExchangeRepository.create(
                    new CurrencyExchange(
                            currency.getR030(),
                            currency.getRate(),
                            conversionService.convert(currency.getExchangedate(), Date.class))
            );
        }
    }

}
