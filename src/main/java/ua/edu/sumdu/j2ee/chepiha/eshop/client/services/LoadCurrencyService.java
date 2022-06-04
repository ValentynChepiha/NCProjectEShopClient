package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.DCurrency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Currency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Exchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories.DCurrencyRepository;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoadCurrencyService {

    @Autowired
    private DCurrencyRepository dCurrencyRepository;

    public String loadCurrency() {

        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?xml";
        String result = "";

        try {
            //Open the URLConnection for reading
            URL u = new URL(url);
            URLConnection uc = u.openConnection();
            InputStream raw = uc.getInputStream();
            InputStream buffer = new BufferedInputStream(raw);

            // chain the InputStream to a Reader
            Reader r = new InputStreamReader(buffer);
            int c;
            StringBuffer stringBuffer = new StringBuffer();
            while ((c = r.read(  )) != -1) {
                stringBuffer.append((char) c);
            }
            result = stringBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public Exchange filterListCurrency(Exchange dataExchange) {

//        Integer[] arrayCurrencyNeed = (Integer[]) dCurrencyRepository.findAllR030().toArray();
//        System.out.println( " load array :: " + arrayCurrencyNeed);

//        Set<Integer> currencyNeed = new HashSet<>(Arrays.asList( arrayCurrencyNeed ));

        System.out.println("filterListCurrency :: start...");
        System.out.println("filterListCurrency :: come dataExchange " +  dataExchange.size() + " rows");

        List<DCurrency> dCurrencyList = dCurrencyRepository.findAll();
        System.out.println("filterListCurrency :: need R030 " +  dCurrencyList);

        Set<Integer> currencyNeed = new HashSet<>();
        for(DCurrency dCurrency: dCurrencyList){
            currencyNeed.add(dCurrency.getR030());
        }

        System.out.println("filterListCurrency :: added currencyNeed " +  currencyNeed.size() + " rows");

        Exchange resultExchange = new Exchange();
        for (Currency currency: dataExchange.getCurrencies()) {
            if( currencyNeed.contains(currency.getR030())){
                resultExchange.addCurrency(currency);
            }
        }

        return resultExchange;
    }

}


