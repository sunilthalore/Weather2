package com.example.vijaysingh.weather2;


import android.content.Context;
import android.util.Log;

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

    public static final String OPEN_5DAY_WEATHER_API =
            "http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=44db6a862fba0b067b1930da0d769e98";

    public static final String OPEN_16DAY_WEATHER_API =
            "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=16&appid=44db6a862fba0b067b1930da0d769e98";



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


    ////// fucntion to fetch 5 days forecast JSON

    public static JSONObject getFiveDaysJSON(Context context, String city){
        Log.d("logdcheck", "entered fivedayjson fn");

        try {
            URL url5 = new URL(String.format(OPEN_5DAY_WEATHER_API,city));
            Log.d("logdcheck", url5.toString());

            HttpURLConnection connection5 = (HttpURLConnection) url5.openConnection();
            Log.d("logdcheck", "connection5 opened");


            BufferedReader reader5 = new BufferedReader(
                    new InputStreamReader(connection5.getInputStream()));
            Log.d("logdcheck", "bufferreader5 created");


            StringBuffer json5 = new StringBuffer(10000);
            Log.d("logdcheck", "stringbuffer5 created");

            String tmp5 = "";
            while (((tmp5=reader5.readLine())!=null))
                json5.append(tmp5).append("\n");
            Log.d("logdcheck", "exited while loop");

            reader5.close();
            Log.d("logdcheck", "reader5 closed");


            JSONObject data5 = new JSONObject(json5.toString());

            // check weather request is successfull or not
            if (data5.getInt("cod") != 200){
                return null;
            }

            return data5;


        }  catch (Exception e) {
            Log.d("logdcheck", "catched in fetch");

            return null;
        }
    }


    ////// fucntion to fetch 16 days forecast JSON

    public static JSONObject getSixteenDaysJSON(Context context, String city){
        Log.d("logdcheck", "entered sixteendayJSON fn");
        try {
            URL url16 = new URL(String.format(OPEN_16DAY_WEATHER_API,city));
            Log.d("logdcheck", url16.toString());

            HttpURLConnection connection16 = (HttpURLConnection) url16.openConnection();
            Log.d("logdcheck", "connection16 opened");


            BufferedReader reader16 = new BufferedReader(
                    new InputStreamReader(connection16.getInputStream()));
            Log.d("logdcheck", "bufferreader16 created");


            StringBuffer json16 = new StringBuffer(10000);
            Log.d("logdcheck", "stringbuffer16 created");

            String tmp16 = "";
            while (((tmp16=reader16.readLine())!=null))
                json16.append(tmp16).append("\n");
            Log.d("logdcheck", "exited while loop");

            reader16.close();
            Log.d("logdcheck", "reader16 closed");


            JSONObject data16 = new JSONObject(json16.toString());

            // check weather request is successfull or not
            if (data16.getInt("cod") != 200){
                return null;
            }

            return data16;



        }  catch (Exception e) {
            Log.d("logdcheck", "catched16 in fetch");
            return null;
        }
    }
}
