package com.delta.irfan.projectbeta.datastreamer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by irfan on 12/27/2015.
 */
public class HttpPostData {
    public HttpPostData(){};
    public String PostData(String sURL, String sParam) throws IOException {
        String responseString = "";
        byte[] postData       = sParam.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        URL url            = new URL(sURL);

        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
        conn.setUseCaches( false );
        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
        }

        //Get Response
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
//            response.append('\r');
        }
        rd.close();

        responseString = response.toString();
        return responseString;

    }
}
