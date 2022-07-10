package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import lombok.extern.slf4j.Slf4j;
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
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelFindRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class LoadExchangeServiceImpl implements LoadExchangeService {

    private final ModelFindRepository<DCurrency> dCurrencyRepository;
    private final ModelRateRepository<CurrencyRate> currencyRateRepository;

    @Value("${api.url.load.exchange.rate}")
    private String urlLoadExchangeRate;

    @Autowired
    public LoadExchangeServiceImpl(ModelFindRepository<DCurrency> dCurrencyRepository,
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

        log.debug("getSelectedCurrencyRate: input data - " + cc);
        List<CurrencyRate> currencyRateList = loadCurrencyRate();
        float result = 1;

        log.debug("getSelectedCurrencyRate: load list - " + currencyRateList);
        for(CurrencyRate currencyRate: currencyRateList) {
            if( currencyRate.getCc().equals(cc) ){
                result = (float) currencyRate.getRate();
            }
        }

        log.debug("getSelectedCurrencyRate: result rate - " + result);
        return result;
    }

    @Override
    public Exchange filterListCurrency(Exchange dataExchange) {
        log.debug("filterListCurrency: input data - " + dataExchange);

        List<DCurrency> dCurrencyList = dCurrencyRepository.findAll();
        log.debug("filterListCurrency: need code R030 - " +  dCurrencyList);

        Set<Integer> currencyNeed = new HashSet<>();
        for(DCurrency dCurrency: dCurrencyList){
            currencyNeed.add(dCurrency.getR030());
        }

        log.debug("filterListCurrency: currencyNeed - " +  currencyNeed);
        Exchange resultExchange = new Exchange();
        for (Currency currency: dataExchange.getCurrencies()) {
            if( currencyNeed.contains(currency.getR030())){
                resultExchange.addCurrency(currency);
            }
        }
        log.debug("filterListCurrency: currencyNeed - " +  resultExchange);
        return resultExchange;
    }

}


