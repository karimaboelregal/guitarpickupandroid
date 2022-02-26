package com.example.guitarpickup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Base64;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class registerAPI extends AsyncTask<Void, Void, Boolean> {
    String myUrl = "http://192.168.1.20:8000/api/register/";
    String email, username, pass;
    registerAPI(String email, String username, String pass) {
        this.email = email;
        this.username = username;
        this.pass = pass;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {

            URL web = new URL(myUrl);
//            String auth = "adminahmed:mohamed13";
//            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
//            String authHeaderValue = "Basic " + new String(encodedAuth);
            HttpURLConnection  myConnection = (HttpURLConnection) web.openConnection();
            myConnection.setRequestMethod("POST");
//            myConnection.setRequestProperty("Authorization", authHeaderValue);
            myConnection.setDoOutput(true);
            myConnection.setDoInput(true);

            String urlParameters  = "username="+username+"&password="+pass+"&email="+email+"";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;

            try( DataOutputStream wr = new DataOutputStream( myConnection.getOutputStream())) {
                wr.write( postData );
            }
            int responseCode = myConnection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("success");
                return true;

            }
                String result;
                BufferedInputStream bis = new BufferedInputStream(myConnection.getErrorStream());
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                int result2 = bis.read();
                while(result2 != -1) {
                    buf.write((byte) result2);
                    result2 = bis.read();
                }
                result = buf.toString();
                System.out.println(result);
                // Error handling code goes here
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
