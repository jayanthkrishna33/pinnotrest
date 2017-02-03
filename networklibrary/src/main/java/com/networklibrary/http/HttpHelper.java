package com.networklibrary.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class HttpHelper {

    public static String GET(String iUrl, boolean useCache) throws IOException {
        URL url = new URL(iUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDefaultUseCaches(useCache);
        conn.connect();
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) stringBuilder.append(data);
        return stringBuilder.toString();
    }

    public static String POST(String iUrl, Map<String, Object> iKeyValuePair) throws IOException {
        URL url = new URL(iUrl);
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> param : iKeyValuePair.entrySet()) {
            if (stringBuilder.length() != 0) stringBuilder.append('&');
            stringBuilder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            stringBuilder.append('=');
            stringBuilder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postBytes = stringBuilder.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postBytes);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0; )
            sb.append((char) c);
        String response = sb.toString();
        return response;
    }

    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }

    }


}
