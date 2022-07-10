package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

@Slf4j
public class UploadService {

    public static String upload(String urlString, Map<String, String> requestParams) {
        URL url = null;
        try {
            url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            StringJoiner prepareParams = new StringJoiner("&");
            for(Map.Entry<String,String> entry : requestParams.entrySet())
                prepareParams.add(
                        URLEncoder.encode(entry.getKey(), "UTF-8")
                                + "="
                                + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] outPrepareParams = prepareParams.toString().getBytes(StandardCharsets.UTF_8);
            int length = outPrepareParams.length;

            con.setFixedLengthStreamingMode(length);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.connect();
            try(OutputStream os = con.getOutputStream()) {
                os.write(outPrepareParams);
                os.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HTTP Response: " + responseCode);
            }
            final StringBuffer response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return response.toString();
        } catch (MalformedURLException e) {
            log.error("Wrong URL: " + url);
            return "error";
        } catch (ProtocolException e) {
            log.error("Communication error in UploadService.upload");
            return "error";
        } catch (IOException e) {
            log.error("Critical error in UploadService.upload");
            return "error";
        }
    }

}
