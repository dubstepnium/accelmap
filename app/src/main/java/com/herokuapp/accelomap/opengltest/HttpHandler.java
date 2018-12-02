package com.herokuapp.accelomap.opengltest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//https://accelomap.herokuapp.com/api/datapoint

public class HttpHandler {

    public HttpHandler() {}

    public int getId(String urlString) {
        String response = "";
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if(responseCode >= 200 && responseCode < 300) {
                urlConnection.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                response = streamToString(in);
            }

        } catch(MalformedURLException e) {
            e.printStackTrace();

        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        if(response.length() > 0)
            return Integer.parseInt(response);
        else
            return -1;
    }

    public void postAccelData(String urlString) {
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if(responseCode >= 200 && responseCode < 300) {
                urlConnection.setRequestMethod("GET");

                urlConnection.connect();
            }

        } catch(MalformedURLException e) {
            e.printStackTrace();

        } catch(IOException e) {
            e.printStackTrace();

        } finally {

            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private String streamToString(InputStream in) {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sBuilder = new StringBuilder();
        String line;

        try {
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line);
            }

        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            try {
                in.close();

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return sBuilder.toString();
    }
}
