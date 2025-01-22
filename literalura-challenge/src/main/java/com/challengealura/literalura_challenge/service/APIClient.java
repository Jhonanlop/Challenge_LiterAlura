package com.challengealura.literalura_challenge.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient implements IAPIClient {
    @Override
    public String obtenerDatos(String urlString) {
        HttpURLConnection con = null;
        StringBuilder response = new StringBuilder();
        int maxRedirects = 5;

        try {
            while (maxRedirects-- > 0) {
                System.out.println("Solicitando URL: " + urlString);
                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);


                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");
                con.setRequestProperty("Accept", "application/json");

                int responseCode = con.getResponseCode();
                System.out.println("Código de respuesta: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {

                    String newUrl = con.getHeaderField("Location");
                    System.out.println("Redirigido a: " + newUrl);

                    if (newUrl == null) {
                        System.out.println("Encabezado 'Location' no encontrado.");
                        break;
                    }

                    urlString = newUrl;
                    continue;
                }

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    break;
                } else {
                    System.out.println("Error: " + responseCode + ". No se pudo obtener los datos.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return response.toString().isEmpty() ? null : response.toString();
    }
}