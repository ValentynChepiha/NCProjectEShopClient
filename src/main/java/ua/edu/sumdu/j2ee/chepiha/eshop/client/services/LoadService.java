package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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

}
