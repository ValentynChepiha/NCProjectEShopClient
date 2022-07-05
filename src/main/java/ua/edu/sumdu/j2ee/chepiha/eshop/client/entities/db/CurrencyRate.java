package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db;

import java.util.Objects;

public class CurrencyRate {

    long id;
    int r030;
    double rate;
    String txt;
    String cc;

    public CurrencyRate() {
    }

    public CurrencyRate(long id, int r030, double rate, String txt, String cc) {
        this.id = id;
        this.r030 = r030;
        this.rate = rate;
        this.txt = txt;
        this.cc = cc;
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

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return id == that.id &&
                r030 == that.r030 &&
                Double.compare(that.rate, rate) == 0 &&
                Objects.equals(txt, that.txt) &&
                Objects.equals(cc, that.cc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, r030, rate, txt, cc);
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "id=" + id +
                ", r030=" + r030 +
                ", rate=" + rate +
                ", txt='" + txt + '\'' +
                ", cc='" + cc + '\'' +
                '}';
    }
}
