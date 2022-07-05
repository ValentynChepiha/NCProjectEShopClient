package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@Service
public class LoadXMLService<T> {

    @Autowired
    ConvertUseJaxbXMLToObject<T> convertUseJaxbXMLToObject;

    public T convertStringXMLToObject(String dataXML, T entity, Class parent, Class child) {

        if(dataXML.length() == 0){
            return null;
        }

        try {
            entity = convertUseJaxbXMLToObject.convert(dataXML, entity, parent, child);
            System.out.println("convertStringXMLToObject :: loaded :: " + entity );
            return entity;
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
