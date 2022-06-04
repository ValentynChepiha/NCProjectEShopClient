package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConvertStringToDate {

    public static Date parseStringToDate(String stringDate) throws ParseException {
        SimpleDateFormat f=new SimpleDateFormat("dd.MM.yyyy");
        return new Date(f.parse(stringDate).getTime());
    }

}
