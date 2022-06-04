package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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
}
