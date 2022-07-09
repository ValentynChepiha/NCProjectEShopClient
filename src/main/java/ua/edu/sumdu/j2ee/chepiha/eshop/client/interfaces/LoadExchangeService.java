package ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces;

import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyRate;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;

import java.util.List;

public interface LoadExchangeService {

    String loadCurrency();

    List<CurrencyRate> loadCurrencyRate();

    float getSelectedCurrencyRate(String cc);

    Exchange filterListCurrency(Exchange dataExchange);
}
