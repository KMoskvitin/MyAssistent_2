package com.KMoskvitin.myassistent.app;

import android.util.Log;
import com.KMoskvitin.myassistent.app.Parse.TodayParser;
import com.KMoskvitin.myassistent.app.Parse.UsdToday;

import java.util.List;

public class GetCurrency {

    public static String getCurrency(){
        try {
            String currency = GetRequestToJSonString.getString(Const.TODAY_CURRENCY_URL);
            if (currency != null){
                Log.i(Const.LOG_TAG, currency);
            }else {
                Log.e(Const.LOG_TAG, "error today currency json");
            }
            List<UsdToday> list = TodayParser.parse(currency);
            String[] rate = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                rate[i] = list.get(i).getCurrency() + " Покупка "
                        + list.get(i).getPurchaseRate() + " продажа " + list.get(i).getSaleRate();
            }
            return rate[2] + "\n" + rate[1] + "\n" + rate[0];
        } catch (Exception e) {
            Log.e(Const.LOG_TAG, "Error server currency");
            e.printStackTrace();
        }
        return "Не получилось загрузить курс валюты";
    }
}
