package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db;

import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(value = "lab4_chepihavv_d_currency")
public class DCurrencyR030 {
    int r030;

    public DCurrencyR030() {
    }

    public DCurrencyR030(int r030) {
        this.r030 = r030;
    }

    public int getR030() {
        return r030;
    }

    public void setR030(int r030) {
        this.r030 = r030;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DCurrencyR030 that = (DCurrencyR030) o;
        return r030 == that.r030;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r030);
    }
}
