package ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

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
}
