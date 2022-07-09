package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyRate;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.DCurrency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.LoadExchangeService;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelRateRepository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelRepository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelRepositoryFind;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@PropertySource("classpath:application.properties")
public class LoadExchangeServiceImpl implements LoadExchangeService {

    private static final LoggerMsgService logger = new LoggerMsgService(LoadExchangeServiceImpl.class) ;

    private final ModelRepositoryFind<DCurrency> dCurrencyRepository;
    private final ModelRateRepository<CurrencyRate> currencyRateRepository;

    @Value("${api.url.load.exchange.rate}")
    private String urlLoadExchangeRate;

    @Autowired
    public LoadExchangeServiceImpl(ModelRepositoryFind<DCurrency> dCurrencyRepository,
                                   ModelRateRepository<CurrencyRate> currencyRateRepository) {
        this.dCurrencyRepository = dCurrencyRepository;
        this.currencyRateRepository = currencyRateRepository;
    }

    @Override
    public String loadCurrency() {
        return LoadService.load( urlLoadExchangeRate );
    }

    @Override
    public List<CurrencyRate> loadCurrencyRate() {
        return currencyRateRepository.findActualExchange();
    }

    @Override
    public float getSelectedCurrencyRate(String cc) {

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

    @Override
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


