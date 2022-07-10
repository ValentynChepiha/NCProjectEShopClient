package ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

@Service
public class EngineTransformationXMLUseJaxb<T> {

//        logger.msgDebug("convert :: start....");
//        logger.msgDebug("convert :: data length - " + dataXML.length());
//        logger.msgDebug("convert :: data - " + dataXML);
//        logger.msgDebug("ConvertUseJaxbXMLToObject.convert :: empty result - " + entity);

    @SneakyThrows
    public T start(String dataXML, JAXBContext jaxbContext) {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(dataXML));
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (T) jaxbUnmarshaller.unmarshal(xsr);
    }

}
