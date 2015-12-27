package com.delta.irfan.projectbeta.datastreamer;

import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by irfan on 12/13/2015.
 */
public class HttpGetData {
    public HttpGetData(){};

    public String PostData(){

        return "";
    }
    public String GetData(String sURL, String sParam) throws IOException {
        URL url = new URL(sURL+sParam );
        String responseString;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            responseString = readStream(in);

        }
        finally {
            urlConnection.disconnect();
        }
        return responseString;
    }
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
