package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name="exchange")
public class Exchange {

    private List<Currency> currencies = new ArrayList<>();

    public Exchange(){}

    public Exchange(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @XmlElement(name="currency")
    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public int size() {
        return currencies.size();
    }

    public void addCurrency (Currency currency) {
        currencies.add(currency);
    }

    @Override
    public String toString() {

        if(currencies.size() == 0){
            return "Exchange list is empty";
        }

        StringBuilder currenciesString = new StringBuilder();
        for(Currency currency: currencies){
            currenciesString.append(currency.toString());
        }

        return "Exchange {" +
                "currencies = " + currenciesString.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exchange exchange = (Exchange) o;
        return Objects.equals(currencies, exchange.currencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencies);
    }
}
