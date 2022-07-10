package ua.edu.sumdu.j2ee.chepiha.eshop.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters.ConverterMapToString;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters.ConverterStringToDate;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters.ConverterXMLToExchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.conveters.ConverterXMLToGoods;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ConverterStringToDate());
        registry.addConverter(new ConverterXMLToGoods());
        registry.addConverter(new ConverterXMLToExchange());
        registry.addConverter(new ConverterMapToString());
    }

}
