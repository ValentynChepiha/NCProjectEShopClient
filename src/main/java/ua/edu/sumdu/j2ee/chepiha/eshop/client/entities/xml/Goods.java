package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name="List")
public class Goods {

    private List<Product> goods = new ArrayList<>();

    public Goods(){}

    public Goods(List<Product> goods) {
        this.goods = goods;
    }

    @XmlElement(name="item")
    public List<Product> getGoods() {
        return goods;
    }

    public void setGoods(List<Product> goods) {
        this.goods = goods;
    }

    public int size() {
        return goods.size();
    }

    public void addProduct (Product product) {
        goods.add(product);
    }

    public void clear() {
        goods.clear();
    }

    public void addAll(Goods goods) {
        this.goods.addAll( goods.getGoods() );
    }

    @Override
    public String toString() {

        if(goods.size() == 0){
            return "Storage is empty. Nothing to sell.";
        }

        StringBuilder currenciesString = new StringBuilder();
        for(Product product: goods){
            currenciesString.append(product.toString());
        }

        return "Exchange {" +
                "goods = " + currenciesString.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods1 = (Goods) o;
        return Objects.equals(goods, goods1.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods);
    }
}
