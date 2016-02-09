package com.example.vijaysingh.weather;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Handler;



/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements View.OnClickListener {

    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTempField;
    TextView weatherIcon;
    Typeface weatherFont;

    Button delhiButton, mumbaiButton, bangaloreButton, chennaiButton, jaipurButton, searchCity;

    Handler handler;

    String selectedCity = "mumbai";


    public WeatherFragment() {
        // Required empty public constructor
        handler = new Handler();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weatherfont.ttf");

        updateWeatherData(selectedCity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView) rootView.findViewById(R.id.city_field);
        updatedField = (TextView) rootView.findViewById(R.id.update_field);
        detailsField = (TextView) rootView.findViewById(R.id.details_filed);
        currentTempField = (TextView) rootView.findViewById(R.id.current_temp_field);
        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);

        weatherIcon.setTypeface(weatherFont);

        delhiButton = (Button) rootView.findViewById(R.id.delhiButton);
        bangaloreButton = (Button) rootView.findViewById(R.id.bangaloreButton);
        chennaiButton = (Button) rootView.findViewById(R.id.chennaiButton);
        mumbaiButton = (Button) rootView.findViewById(R.id.mumbaiButton);
        jaipurButton = (Button) rootView.findViewById(R.id.jaipurButton);
        searchCity = (Button) rootView.findViewById(R.id.searchCity);

        delhiButton.setOnClickListener(this);
        mumbaiButton.setOnClickListener(this);
        bangaloreButton.setOnClickListener(this);
        chennaiButton.setOnClickListener(this);
        jaipurButton.setOnClickListener(this);
        searchCity.setOnClickListener(this);



        return rootView;


    }

    private void updateWeatherData(final String city){
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if ((json == null)) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }

                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });

                }
              }
            }.start();
        }

    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + "hPa");

            currentTempField.setText(
                    String.format("%.2f", main.getDouble("temp")) + "C");

            DateFormat df = DateFormat.getDateInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise")*1000,
                    json.getJSONObject("sys").getLong("sunset")*1000);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId/100;
        String icon = "";
        if (actualId == 800){
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime<sunset){
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id){
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;

            }
        }
        weatherIcon.setText(icon);
    }

    public void changeCity(String city){
        updateWeatherData(city);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bangaloreButton:
                selectedCity = "bangalore";
                updateWeatherData(selectedCity);
                break;
            case R.id.delhiButton:
                selectedCity = "delhi";
                updateWeatherData(selectedCity);
                break;
            case R.id.mumbaiButton:
                selectedCity = "mumbai";
                updateWeatherData(selectedCity);
                break;
            case R.id.chennaiButton:
                selectedCity = "chennai";
                updateWeatherData(selectedCity);
                break;
            case R.id.jaipurButton:
                selectedCity = "jaipur";
                updateWeatherData(selectedCity);
                break;
            case R.id.searchCity:
            //    showInputDialog();
                updateWeatherData(selectedCity);
                break;
        }

    }

  /*  private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Search city");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedCity = input.getText().toString();
            }
        });
        builder.show();
    }   */
}
