package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class LoadService {

    public static String load(String url) {
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
            StringBuilder stringBuilder = new StringBuilder();
            while ((c = r.read(  )) != -1) {
                stringBuilder.append((char) c);
            }
            result = stringBuilder.toString();
        } catch (MalformedURLException e) {
            log.error("Wrong URL: " + url);
        } catch (IOException e) {
            log.error("Critical error in LoadService.load");
        }
        return result;
    }

}
