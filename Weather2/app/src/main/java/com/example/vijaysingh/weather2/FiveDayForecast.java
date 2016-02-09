package com.example.vijaysingh.weather2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.TableRow.LayoutParams;



import org.json.JSONException;
import org.json.JSONObject;

public class FiveDayForecast extends AppCompatActivity {

    String getSelectedCity = "";

    Handler handler;

    protected Context context;

   // TextView date1, temp1, max1, min1, weather1;
    TableLayout tableLayout;
    TextView cityTextView;

    public FiveDayForecast() {
        // Required empty public constructor
        handler = new Handler();
        Log.d("logdcheck", "handlerCreated");



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_day_forecast);


            Bundle extras = getIntent().getExtras();
            if(extras != null){
                getSelectedCity = extras.getString("selectedCity");
            }
        updateFiveDayWeatherData(getSelectedCity);
        context = this;
        Log.d("logdcheck", getSelectedCity);


    //    date1 = (TextView) findViewById(R.id.date1);
    //    temp1 = (TextView) findViewById(R.id.temp1);
    //    max1 = (TextView) findViewById(R.id.max1);
    //    min1 = (TextView) findViewById(R.id.min1);
    //    weather1 = (TextView) findViewById(R.id.weather1);
        cityTextView = (TextView) findViewById(R.id.cityTextView);
        cityTextView.setText(getSelectedCity.toUpperCase());
        tableLayout = (TableLayout) findViewById(R.id.tableLayout16);

    }

    private void updateFiveDayWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getFiveDaysJSON(context, city);
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
                            renderFiveDayWeather(json);
                            Log.d("logdcheck", "5jsonNotNull");

                        }
                    });
                }
            }
        }.start();
    }

    private void  renderFiveDayWeather(JSONObject json){
        Log.d("logdcheck", "renderFiveDayWeather: ");
        String countryName;

        try{

            for(int i = 0; i < 9; i++){

                TableRow tableRow = new TableRow(this);
                TextView date = new TextView(this);
                TextView temp = new TextView(this);
                TextView max = new TextView(this);
                TextView min = new TextView(this);
                TextView weather = new TextView(this);



                JSONObject list1 = json.getJSONArray("list").getJSONObject(i);
                date.setText(list1.getString("dt_txt"));

                JSONObject main1 = list1.getJSONObject("main");

                temp.setText("  "+ String.format("%.2f", main1.getDouble("temp"))
                        + "°C");

                max.setText(String.format("%.2f", main1.getDouble("temp_min"))
                        );

                min.setText(String.format("%.2f", main1.getDouble("temp_max"))
                        );

                JSONObject weatherJSON1 = list1.getJSONArray("weather").getJSONObject(0);
                weather.setText(weatherJSON1.getString("description"));



                tableRow.addView(date);
                tableRow.addView(temp);
                tableRow.addView(max);
                tableRow.addView(min);
                tableRow.addView(weather);

                tableLayout.addView(tableRow, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));



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
