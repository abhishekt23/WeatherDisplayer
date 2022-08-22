package com.example.weatherapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {
    Button click;
    TextView high1;
    TextView high2;
    TextView high3;
    TextView high4;
    TextView high5;
    TextView low1;
    TextView low2;
    TextView low3;
    TextView low4;
    TextView low5;
    TextView quote2;
    TextView date0;
    TextView currenttemp;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;
    TextView time5;
    TextView quote;
    EditText zip;
    String zipCode;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click = findViewById(R.id.id_getInfo);
        zip = findViewById(R.id.editText);
        high1 = findViewById(R.id.id_text_high1);
        high2 = findViewById(R.id.id_text_high2);
        high3 = findViewById(R.id.id_text_high3);
        high4 = findViewById(R.id.id_text_high4);
        high5 = findViewById(R.id.id_text_high5);
        low1 = findViewById(R.id.id_text_low1);
        low2 = findViewById(R.id.id_text_low2);
        low3 = findViewById(R.id.id_text_low3);
        low4 = findViewById(R.id.id_text_low4);
        low5 = findViewById(R.id.id_text_low5);
        quote2 = findViewById(R.id.id_text_quote2);
        date0 = findViewById(R.id.id_text_date);
        currenttemp = findViewById(R.id.id_text_current);
        time1 = findViewById(R.id.id_text_time1);
        time2 = findViewById(R.id.id_text_time2);
        time3 = findViewById(R.id.id_text_time3);
        time4 = findViewById(R.id.id_text_time4);
        time5 = findViewById(R.id.id_text_time5);
        quote = findViewById(R.id.id_text_quote);
        image1 = findViewById(R.id.imageView);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        image4 = findViewById(R.id.imageView4);
        image5 = findViewById(R.id.imageView6);
        image6 = findViewById(R.id.imageView7);

        image1.setImageResource(R.drawable.weather);
        image2.setImageResource(R.drawable.weather);
        image3.setImageResource(R.drawable.weather);
        image4.setImageResource(R.drawable.weather);
        image5.setImageResource(R.drawable.weather);
        image6.setImageResource(R.drawable.weather);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncThread run = new AsyncThread();
                run.execute(zipCode);

            }
        });

        zip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                zipCode = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        String data1 = "";
        String data2 = "";

        @Override
        protected Void doInBackground(String... Strings) {
            try {
                String zipString = Strings[0];
                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?zip=" + zipString + ",us&APPID=c8b5f7a89b4b0c2ffcf25bcbc7fdc9de&units=imperial");
                URL urlCurrent = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zipString + ",us&APPID=c8b5f7a89b4b0c2ffcf25bcbc7fdc9de&units=imperial");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) urlCurrent.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStream inputStream2 = httpURLConnection2.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                String line = "";
                String line2 = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data1 = data1 + line;
                    Log.d("TAG", data1);

                }
                while (line2 != null) {
                    line2 = bufferedReader2.readLine();
                    data2 = data2 + line2;
                    Log.d("TAG2", data2);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONObject json = new JSONObject(data1);
                JSONObject json2 = new JSONObject(data2);

                long unixSeconds1 = json2.getLong("dt");
                Date date1 = new java.util.Date(unixSeconds1 * 1000L);
                SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("HH:mm z");
                sdf1.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                String formattedDate1 = sdf1.format(date1);
                date0.setText(formattedDate1);
                Log.d("object", unixSeconds1 + "");
                Double temp = json2.getJSONObject("main").getDouble("temp");
                currenttemp.setText(temp + "");
                String info = json2.getJSONArray("weather").getJSONObject(0).getString("main");
                quote2.setText(info);
                Log.d("info", temp + "");

                long unixSeconds2 = json2.getLong("dt");
                Date date2 = new java.util.Date(unixSeconds2 * 1000L);
                SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("HH:mm");
                sdf2.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                String formattedDate2 = sdf2.format(date2);
                time1.setText(formattedDate2);
                Double temp_min1 = json2.getJSONObject("main").getDouble("temp_min");
                Double temp_max1 = json2.getJSONObject("main").getDouble("temp_max");
                String info1 = json2.getJSONArray("weather").getJSONObject(0).getString("main");
                high1.setText(temp_max1 + "");
                low1.setText(temp_min1 + "");
                Log.d("info1", temp_max1 + "");
                Log.d("info2", temp_min1 + "");
                Log.d("yes", info1);
                Log.d("object1", unixSeconds2 + "");


                long unixSeconds3 = json.getJSONArray("list").getJSONObject(0).getLong("dt");
                Date date3 = new java.util.Date(unixSeconds3 * 1000L);
                SimpleDateFormat sdf3 = new java.text.SimpleDateFormat("HH:mm");
                sdf3.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                String formattedDate3 = sdf3.format(date3);
                time2.setText(formattedDate3);
                Double temp_min2 = json.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_min");
                Double temp_max2 = json.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_max");
                String info2 = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
                high2.setText(temp_max2 + "");
                low2.setText(temp_min2 + "");
                Log.d("yes1", info2);
                Log.d("object2", unixSeconds3 + "");


                long unixSeconds4 = json.getJSONArray("list").getJSONObject(1).getLong("dt");
                Date date4= new java.util.Date(unixSeconds4 * 1000L);
                SimpleDateFormat sdf4 = new java.text.SimpleDateFormat("HH:mm");
                sdf4.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                String formattedDate4 = sdf4.format(date4);
                time3.setText(formattedDate4);
                Double temp_min3 = json.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_min");
                Double temp_max3 = json.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_max");
                String info3 = json.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main");
                high3.setText(temp_max3 + "");
                low3.setText(temp_min3 + "");
                Log.d("yes2", info3);
                Log.d("object3", unixSeconds4 + "");




                long unixSeconds5 = json.getJSONArray("list").getJSONObject(2).getLong("dt");
                Date date5 = new java.util.Date(unixSeconds5 * 1000L);
                SimpleDateFormat sdf5 = new java.text.SimpleDateFormat("HH:mm");
                sdf5.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                String formattedDate5 = sdf5.format(date5);
                time4.setText(formattedDate5);
                Double temp_min4 = json.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_min");
                Double temp_max4 = json.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_max");
                String info4 = json.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main");
                high4.setText(temp_max4 + "");
                low4.setText(temp_min4 + "");
                Log.d("yes3", info4);
                Log.d("object4", unixSeconds5 + "");


                long unixSeconds6 = json.getJSONArray("list").getJSONObject(3).getLong("dt");
                Date date6 = new java.util.Date(unixSeconds6 * 1000L);
                SimpleDateFormat sdf6 = new java.text.SimpleDateFormat("HH:mm");
                sdf6.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                String formattedDate6 = sdf6.format(date6);
                time5.setText(formattedDate6);
                Double temp_min5 = json.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_min");
                Double temp_max5 = json.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_max");
                String info5 = json.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main");
                high5.setText(temp_max5 + "");
                low5.setText(temp_min5 + "");
                Log.d("yes4", info5);
                Log.d("object5", unixSeconds6 + "");





                switch (info) {
                    case "Clear":
                        image1.setImageResource(R.drawable.clear);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Clouds":
                        image1.setImageResource(R.drawable.bclouds);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Rain":
                        image1.setImageResource(R.drawable.rain);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Thunderstorms":
                        image1.setImageResource(R.drawable.thunderstorm);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Snow":
                        image1.setImageResource(R.drawable.snow);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Mist":
                        image1.setImageResource(R.drawable.mist);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                }
                switch (info1) {
                    case "Clear":
                        image2.setImageResource(R.drawable.clear);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Clouds":
                        image2.setImageResource(R.drawable.bclouds);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Rain":
                        image2.setImageResource(R.drawable.rain);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Thunderstorms":
                        image2.setImageResource(R.drawable.thunderstorm);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Snow":
                        image2.setImageResource(R.drawable.snow);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Mist":
                        image2.setImageResource(R.drawable.mist);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                }
                switch (info2) {
                    case "Clear":
                        image3.setImageResource(R.drawable.clear);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Clouds":
                        image3.setImageResource(R.drawable.bclouds);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Rain":
                        image3.setImageResource(R.drawable.rain);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Thunderstorms":
                        image3.setImageResource(R.drawable.thunderstorm);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Snow":
                        image3.setImageResource(R.drawable.snow);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Mist":
                        image3.setImageResource(R.drawable.mist);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                }
                switch (info3) {
                    case "Clear":
                        image4.setImageResource(R.drawable.clear);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Clouds":
                        image4.setImageResource(R.drawable.bclouds);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Rain":
                        image4.setImageResource(R.drawable.rain);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Thunderstorms":
                        image4.setImageResource(R.drawable.thunderstorm);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Snow":
                        image4.setImageResource(R.drawable.snow);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Mist":
                        image4.setImageResource(R.drawable.mist);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                }
                switch (info4) {
                    case "Clear":
                        image5.setImageResource(R.drawable.clear);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Clouds":
                        image5.setImageResource(R.drawable.bclouds);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Rain":
                        image5.setImageResource(R.drawable.rain);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Thunderstorms":
                        image5.setImageResource(R.drawable.thunderstorm);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Snow":
                        image5.setImageResource(R.drawable.snow);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Mist":
                        image5.setImageResource(R.drawable.mist);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                }
                switch (info5) {
                    case "Clear":
                        image6.setImageResource(R.drawable.clear);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Clouds":
                        image6.setImageResource(R.drawable.bclouds);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Rain":
                        image6.setImageResource(R.drawable.rain);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Thunderstorms":
                        image6.setImageResource(R.drawable.thunderstorm);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Snow":
                        image6.setImageResource(R.drawable.snow);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                    case "Mist":
                        image6.setImageResource(R.drawable.mist);
                        quote.setText("Be sure to catch some beautiful sun outside!");
                        break;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

       /* public String timeConvert(String time) {
            String str = time.substring(11, 13);
            int i = Integer.parseInt(str);
            if (i == 0)
                return "12:00 AM";
            if (i > 12)
                return (i -= 12) + ":00 PM";
            else
                return i + ":00 AM";


        }
        */

    }
}

