package com.KMoskvitin.myassistent.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyAdapter extends ArrayAdapter<User> {
    private final LayoutInflater inflater;
    private int typeCount = 4;
    private String path = "weatherIcons/_";
    private String fileFormat = ".png";

    public MyAdapter(Context context, List objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }


    @Override
    public int getViewTypeCount() {
        return typeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItem(position) != null) {
            if (convertView == null) {
                switch (getItemViewType(position)) {
                    case Const.TYPE_USER:
                        convertView = inflater.inflate(R.layout.item_user, parent, false);
                        TextView userMessage = (TextView) convertView.findViewById(R.id.usMessage);
                        TextView userTime = (TextView) convertView.findViewById(R.id.time);
                        userTime.setText(getItem(position).getTime());
                        userMessage.setText(getItem(position).getMessage());
                        break;
                    case Const.TYPE_BOT:
                        convertView = inflater.inflate(R.layout.item_bot, parent, false);
                        TextView botMessage = (TextView) convertView.findViewById(R.id.assMessage);
                        TextView botTime = (TextView) convertView.findViewById(R.id.assTime);
                        botTime.setText(getItem(position).getTime());
                        botMessage.setText(getItem(position).getMessage());
                        break;
                    case Const.TYPE_WEATHER:
                        convertView = inflater.inflate(R.layout.item_weather, parent, false);
                        TextView weatherText = (TextView) convertView.findViewById(R.id.weatherText);
                        TextView weatherTime = (TextView) convertView.findViewById(R.id.weatherTime);
                        weatherTime.setText(getItem(position).getTime());
                        weatherText.setText(getItem(position).getMessage());
                        ImageView weatherImage = (ImageView) convertView.findViewById(R.id.weatherImage);
                        InputStream ims = null;
                        try {
                            ims = getContext().getAssets().open(path + getItem(position).getImage() + fileFormat);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Drawable drawable = Drawable.createFromStream(ims, null);
                        weatherImage.setImageDrawable(drawable);
                        break;
                }
            } else {
                User item = getItem(position);
                switch (getItemViewType(position)) {
                    case Const.TYPE_USER:
                        TextView userMessage = (TextView) convertView.findViewById(R.id.usMessage);
                        TextView userTime = (TextView) convertView.findViewById(R.id.time);
                        userTime.setText(getItem(position).getTime());
                        userMessage.setText(item.getMessage());
                        break;
                    case Const.TYPE_BOT:
                        TextView botMessage = (TextView) convertView.findViewById(R.id.assMessage);
                        TextView botTime = (TextView) convertView.findViewById(R.id.assTime);
                        botTime.setText(getItem(position).getTime());
                        botMessage.setText(item.getMessage());
                        break;
                    case Const.TYPE_WEATHER:
                        TextView weatherText = (TextView) convertView.findViewById(R.id.assMessage);
                        TextView weatherTime = (TextView) convertView.findViewById(R.id.weatherTime);
                        weatherTime.setText(getItem(position).getTime());
                        weatherText.setText(getItem(position).getMessage());
                        ImageView weatherImage = (ImageView) convertView.findViewById(R.id.weatherImage);
                        InputStream ims = null;
                        try {
                            ims = getContext().getAssets().open(path + getItem(position).getImage() + fileFormat);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Drawable drawable = Drawable.createFromStream(ims, null);
                        weatherImage.setImageDrawable(drawable);
                        break;
                }
            }
        }
        return convertView;
    }
}


