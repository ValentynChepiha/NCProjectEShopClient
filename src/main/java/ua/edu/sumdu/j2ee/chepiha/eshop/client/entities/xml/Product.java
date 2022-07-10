package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

    @XmlElement(name = "id")
    long id;
    @XmlElement(name = "name")
    String name;
    @XmlElement(name = "brand")
    String brand;
    @XmlElement(name = "price")
    float price;
    @XmlElement(name = "count")
    int count;
    @XmlElement(name = "discount")
    float discount;
    @XmlElement(name = "idGift")
    long idGift;
    @XmlElement(name = "nameGift")
    String nameGift;

    public Product() {
    }

    public Product(long id, String name, String brand, float price, int count, float discount,
                   long idGift, String nameGift) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.count = count;
        this.discount = discount;
        this.idGift = idGift;
        this.nameGift = nameGift;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public long getIdGift() {
        return idGift;
    }

    public void setIdGift(long idGift) {
        this.idGift = idGift;
    }

    public String getNameGift() {
        return nameGift;
    }

    public void setNameGift(String nameGift) {
        this.nameGift = nameGift;
    }

    public float getAmount(int count) {
        return ((float) Math.round(getPrice() * 100 * count)) / 100;
    }

    public float getPriceByRate(float rate) {
        return ((float) Math.round( getPrice() * 100 / rate )) / 100;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", discount=" + discount +
                ", idGift=" + idGift +
                ", nameGift='" + nameGift + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Float.compare(product.price, price) == 0 &&
                count == product.count &&
                Float.compare(product.discount, discount) == 0 &&
                idGift == product.idGift &&
                Objects.equals(name, product.name) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(nameGift, product.nameGift);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, price, count, discount, idGift, nameGift);
    }
}
