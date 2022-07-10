package ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ConverterStringToDate implements Converter<String, Date> {

    @SneakyThrows
    @Override
    public Date convert(String source) {
        SimpleDateFormat f=new SimpleDateFormat("dd.MM.yyyy");
        return new Date(f.parse(source).getTime());
    }

}
