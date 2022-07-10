package ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Product;

import javax.xml.bind.JAXBContext;

public class ConverterXMLToGoods implements Converter<String, Goods> {

    @SneakyThrows
    @Override
    public Goods convert(String source) {
        EngineTransformationXMLUseJaxb<Goods> engine = new EngineTransformationXMLUseJaxb<>();
        JAXBContext jaxbContext = JAXBContext.newInstance(Goods.class, Product.class);
        return engine.start(source, jaxbContext);
    }

}
