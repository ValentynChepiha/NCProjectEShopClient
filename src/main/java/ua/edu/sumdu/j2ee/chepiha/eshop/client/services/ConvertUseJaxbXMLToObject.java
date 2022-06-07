package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;

@Service
public class ConvertUseJaxbXMLToObject<T> {

    private static final LoggerMsgService logger = new LoggerMsgService(ConvertUseJaxbXMLToObject.class) ;

    public T convert(String dataXML, T entity, Class parent, Class child)
            throws JAXBException, XMLStreamException, IOException {

        logger.msgDebug("convert :: start....");
        logger.msgDebug("convert :: data length - " + dataXML.length());
        logger.msgDebug("convert :: data - " + dataXML);
        logger.msgDebug("ConvertUseJaxbXMLToObject.convert :: empty result - " + entity);

        JAXBContext jaxbContext = JAXBContext.newInstance(parent, child);
        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(dataXML));
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        entity = (T) jaxbUnmarshaller.unmarshal(xsr);
        logger.msgDebug("convert :: result - " + entity);

        return entity;
    }
}
