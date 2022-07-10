package ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;

import javax.xml.bind.JAXBContext;

public class ConverterXMLToExchange implements Converter<String, Exchange> {

    @SneakyThrows
    @Override
    public Exchange convert(String source) {
        EngineTransformationXMLUseJaxb<Exchange> engine = new EngineTransformationXMLUseJaxb<>();
        JAXBContext jaxbContext = JAXBContext.newInstance(Exchange.class, Currency.class);
        return engine.start(source, jaxbContext);
    }

}
