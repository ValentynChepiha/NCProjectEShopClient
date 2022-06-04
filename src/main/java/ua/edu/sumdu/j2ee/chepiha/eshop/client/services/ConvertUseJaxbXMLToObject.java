package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;

public class ConvertUseJaxbXMLToObject {
    public static Exchange convert(String dataXML) throws JAXBException, XMLStreamException, IOException {

        System.out.println("ConvertUseJaxbXMLToObject.convert :: start....");
        System.out.println("ConvertUseJaxbXMLToObject.convert :: data length :: " + dataXML.length());
        Exchange exchange = new Exchange();

        System.out.println("ConvertUseJaxbXMLToObject.convert :: empty result :: " + exchange);

        JAXBContext jaxbContext = JAXBContext.newInstance(Exchange.class, Currency.class);

        System.out.println("ConvertUseJaxbXMLToObject.convert :: Create JAXBContext.newInstance");

        XMLInputFactory xif = XMLInputFactory.newFactory();
        System.out.println("ConvertUseJaxbXMLToObject.convert :: Create XMLInputFactory.newFactory");

        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        System.out.println("ConvertUseJaxbXMLToObject.convert :: Off dtd");

        XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(dataXML));
        System.out.println("ConvertUseJaxbXMLToObject.convert :: Read stream ");

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        System.out.println("ConvertUseJaxbXMLToObject.convert :: Create jaxbContext.createUnmarshaller");

        exchange = (Exchange) jaxbUnmarshaller.unmarshal(xsr);
        System.out.println("ConvertUseJaxbXMLToObject.convert :: Unmarshal result" );
        System.out.println("ConvertUseJaxbXMLToObject.convert :: Rows :: " + exchange.size());

        return exchange;
    }
}
