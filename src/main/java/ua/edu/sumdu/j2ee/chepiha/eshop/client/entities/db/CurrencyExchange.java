package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db;

import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.util.Objects;

@Table(value = "lab4_chepihavv_currency_exchange")
public class CurrencyExchange {
    long id;
    int r030;
    double rate;
    Date exchangeDate;

    public CurrencyExchange() {
    }

    public CurrencyExchange(int r030, double rate, Date exchangeDate) {
        this.r030 = r030;
        this.rate = rate;
        this.exchangeDate = exchangeDate;
    }

    public CurrencyExchange(long id, int r030, double rate, Date exchangeDate) {
        this.id = id;
        this.r030 = r030;
        this.rate = rate;
        this.exchangeDate = exchangeDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getR030() {
        return r030;
    }

    public void setR030(int r030) {
        this.r030 = r030;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyExchange that = (CurrencyExchange) o;
        return id == that.id &&
                r030 == that.r030 &&
                Double.compare(that.rate, rate) == 0 &&
                exchangeDate.equals(that.exchangeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, r030, rate, exchangeDate);
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                "id=" + id +
                ", r030=" + r030 +
                ", rate=" + rate +
                ", exchangeDate=" + exchangeDate +
                '}';
    }
}
