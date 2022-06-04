package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class XMLService {

    public static Exchange convertStringXMLToObject(String dataXML) {

        if(dataXML.length() == 0){
            return null;
        }

        try {
            Exchange exchange = ConvertUseJaxbXMLToObject.convert(dataXML);
            System.out.println("convertStringXMLToObject :: dataCurrency :: " + exchange );
            return exchange;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
