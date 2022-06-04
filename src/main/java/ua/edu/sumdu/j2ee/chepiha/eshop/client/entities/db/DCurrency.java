package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db;

import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(value = "lab4_chepihavv_d_currency")
public class DCurrency {
    long id;
    int r030;
    String txt;
    String cc;

    public DCurrency() {
    }

    public DCurrency(long id, int r030, String txt, String cc) {
        this.id = id;
        this.r030 = r030;
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
        DCurrency dCurrency = (DCurrency) o;
        return id == dCurrency.id &&
                r030 == dCurrency.r030 &&
                txt.equals(dCurrency.txt) &&
                cc.equals(dCurrency.cc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, r030, txt, cc);
    }

    @Override
    public String toString() {
        return "DCurrency{" +
                "id=" + id +
                ", r0030=" + r030 +
                ", txt='" + txt + '\'' +
                ", cc='" + cc + '\'' +
                '}';
    }
}
