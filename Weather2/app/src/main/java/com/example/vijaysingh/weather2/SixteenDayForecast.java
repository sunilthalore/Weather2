package com.example.vijaysingh.weather2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class SixteenDayForecast extends AppCompatActivity {

    String getSelectedCity = "";

    Handler handler;

    protected Context context;

    // TextView date1, temp1, max1, min1, weather1;
    TableLayout tableLayout16;
    TextView cityTextView;

    public SixteenDayForecast() {
        // Required empty public constructor
        handler = new Handler();
        Log.d("logdcheck", "handlerCreated");



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixteen_day_forecast);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            getSelectedCity = extras.getString("selectedCity");
        }
        updateSixteenDayWeatherData(getSelectedCity);
        context = this;
        Log.d("logdcheck", getSelectedCity);

        cityTextView = (TextView) findViewById(R.id.cityTextView);
        cityTextView.setText(getSelectedCity.toUpperCase());
        tableLayout16 = (TableLayout) findViewById(R.id.tableLayout16);

    }

    private void updateSixteenDayWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getSixteenDaysJSON(context, city);
                if ((json == null)){
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context,
                                    "Sorry, no weather data found",
                                    Toast.LENGTH_LONG).show();
                            Log.d("logdcheck", "5jsonnull");
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderSixteenDayWeather(json);
                            Log.d("logdcheck", "5jsonNotNull");

                        }
                    });
                }
            }
        }.start();
    }

    private void  renderSixteenDayWeather(JSONObject json){
        Log.d("logdcheck", "renderFiveDayWeather: ");
        String countryName;

        try{

            for(int i = 0; i < 13; i++){

                TableRow tableRow = new TableRow(this);
                TextView date = new TextView(this);
                TextView temp = new TextView(this);
                TextView max = new TextView(this);
                TextView min = new TextView(this);
                TextView weather = new TextView(this);


                JSONObject list2= json.getJSONArray("list").getJSONObject(i);
                DateFormat df = DateFormat.getDateInstance();
                String updatedOn = df.format(new Date(list2.getLong("dt") * 1000));
                date.setText(updatedOn);

                JSONObject temp2 = list2.getJSONObject("temp");


                temp.setText("   "+ String.format("%.2f", temp2.getDouble("day"))
                        + "°C");

                max.setText("   " + String.format("%.2f", temp2.getDouble("min"))
                );

                min.setText("  " + String.format("%.2f", temp2.getDouble("max"))
                );

                JSONObject weatherJSON2 = list2.getJSONArray("weather").getJSONObject(0);
                weather.setText("    " + weatherJSON2.getString("description"));



                tableRow.addView(date);
                tableRow.addView(temp);
                tableRow.addView(max);
                tableRow.addView(min);
                tableRow.addView(weather);

                tableLayout16.addView(tableRow, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));



            }

       /*     JSONObject list1 = json.getJSONArray("list").getJSONObject(0);
            date1.setText(list1.getString("dt_txt"));

            JSONObject main1 = list1.getJSONObject("main");

            temp1.setText(String.format("%.2f", main1.getDouble("temp"))
                    + "°C");

            min1.setText(String.format("%.2f", main1.getDouble("temp_min"))
                    + "°C");

            max1.setText(String.format("%.2f", main1.getDouble("temp_max"))
                    + "°C");

            JSONObject weatherJSON1 = list1.getJSONArray("weather").getJSONObject(0);
            weather1.setText(weatherJSON1.getString("description"));

            Log.d("logdcheck", "tried: ");

            countryName = json.getJSONObject("city").getString("country").toUpperCase();
            Toast.makeText(context,countryName,Toast.LENGTH_LONG).show();  */

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("logdcheck", "cached5: ");

        }
    }

}
