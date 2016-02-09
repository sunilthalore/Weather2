package com.example.vijaysingh.weather;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vijaysingh on 08/02/16.
 */
public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=44db6a862fba0b067b1930da0d769e98";

    public static JSONObject getJSON( Context context, String city){
        try{
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while (((tmp=reader.readLine())!=null))
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // check weather request is successfull or not
            if (data.getInt("cod") != 200){
                return null;
            }

            return data;

        } catch (Exception e) {
            return null;
        }
    }
}
