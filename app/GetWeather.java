package com.KMoskvitin.myassistent.app;

import android.content.res.Resources;
import android.util.Log;

import com.KMoskvitin.myassistent.app.Parse.WeatherJsonResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GetWeather {
    private WeatherJsonResult weatherJsonResult;

    public GetWeather() {
        Gson gson = new GsonBuilder().create();
        try {
            String weather = GetRequestToJSonString.getString(Const.WEATHER_URL);
            if (weather != null) {
                Log.i(Const.LOG_TAG, weather);

            } else {
                Log.e(Const.LOG_TAG, " Weater not string json");
            }
        } catch (JsonSyntaxException e) {
            Log.e(Const.LOG_TAG, "Weather server error");
            e.printStackTrace();
        }
    }

    public String getMessage() {
        if (weatherJsonResult != null) {
            String description = weatherJsonResult.getWeather().getDescription();
            int tmp = (int) weatherJsonResult.getMain().getTemp();
            double humiditi = weatherJsonResult.getMain().getHumidity();
            double windSpeed = weatherJsonResult.getWind().getSpeed();
            double direction = weatherJsonResult.getWind().getDeg();

            return R.string.now + description + ", "  + R.string.temperature + tmp
                    + R.string.celsius + humiditi
                    + "%, " + getDirection(direction) + R.string.wind + windSpeed + R.string.speed;


        } else {
            return String.valueOf(R.string.error_weather);
        }
    }




    private String getDirection(double direction) {
        if (direction > 339 || direction < 22) {
            return String.valueOf(R.string.north);
        }
        if (direction > 23 || direction < 67) {
            return String.valueOf(R.string.northeast);
        }
        if (direction > 68 || direction < 112) {
            return String.valueOf(R.string.east);
        }
        if (direction > 113 || direction < 157) {
            return String.valueOf(R.string.southeast);
        }
        if (direction > 158 || direction < 202) {
            return String.valueOf(R.string.south);
        }
        if (direction > 203 || direction < 247) {
            return String.valueOf(R.string.southwest);
        }
        if (direction > 248 || direction < 292) {
            return String.valueOf(R.string.west);
        }
        if (direction > 293 || direction < 338) {
            return String.valueOf(R.string.northwest);
        }
        return "";
    }

    public String getId() {
        return weatherJsonResult.getWeather().getIcon();
    }
}
