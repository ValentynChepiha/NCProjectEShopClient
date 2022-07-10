package ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters;

import org.springframework.core.convert.converter.Converter;

import java.util.Map;
import java.util.stream.Collectors;

public class ConverterMapToString implements Converter<Map<?,?>, String> {
    @Override
    public String convert(Map<?, ?> source) {
        return source
                .keySet()
                .stream()
                .map( key -> key + " :: " + source.get(key) )
                .collect(Collectors.joining("; ", "{", "}"));
    }
}
