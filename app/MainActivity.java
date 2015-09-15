package com.KMoskvitin.myassistent.app;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.KMoskvitin.myassistent.app.mapper.CursorMapper;

import java.text.SimpleDateFormat;
import java.util.*;


public class MainActivity extends ListActivity {

    private MyAdapter adapter;
    private List<User> userList = new ArrayList<User>();
    private SQLiteDatabase db;

    private String anecdote;
    private String weather;
    private String currency;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anecdote = getString(R.string.anecdote);
        weather = getString(R.string.weather);
        currency = getString(R.string.currency);
        dateFormat = new SimpleDateFormat("HH.mm.ss, dd.MM.yyyy");
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.message);

        ListView chat = getListView();
        adapter = new MyAdapter(MainActivity.this, userList);
        chat.setAdapter(adapter);


        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(chat, new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            String[] deleteItemTime = new String[1];
                            deleteItemTime[0] = adapter.getItem(position).getChatTime();
                            adapter.remove(adapter.getItem(position));
                            db.delete("chat", "chatTime = ?", deleteItemTime);
                            Toast.makeText(MainActivity.this, "Message deleted.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        chat.setOnTouchListener(touchListener);
        chat.setOnScrollListener(touchListener.makeScrollListener());

        UserDataBase userDataBase = new UserDataBase(MainActivity.this);
        db = userDataBase.getWritableDatabase();
        Cursor cursor = db.query("chat", null, null, null, null, null, null);
        List<User> dBList;
        dBList = CursorMapper.create(User.class).map(cursor);
        if (dBList.size() != 0) {
            userList.addAll(dBList);
            Log.i(Const.LOG_TAG, "Size of userList " + userList.size());
        }
//
//        String sysMessage = "Enter \"" + currency + "\" to " +
//                "know the exchange rate in PrivatBank\n" +
//                "Enter \"" + anecdote + "\" bot to show you a random anecdote.\n" +
//                "Enter \"" + weather + "\" to see the actual weather.";
//        userList.add(new User(Const.TYPE_SYSTEM, sysMessage, getTimeString()));

        adapter.notifyDataSetChanged();

        ImageView sendButton = (ImageView) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() != 0) {
                    userList.add(new User(Const.TYPE_USER, editText.getText().toString(), getTimeString()));
                    addToDb(Const.TYPE_USER, editText.getText().toString(), "", userList.get(userList.size() - 1).getChatTime());
                }

                final String message = editText.getText().toString();
                if (message.toLowerCase().contains(anecdote) || message.toLowerCase().contains(weather)
                        || message.toLowerCase().contains(currency)) {
                    if (isOnline()) {
                        new AsyncTask<Void, Void, User>() {
                            @Override
                            protected User doInBackground(Void... params) {
                                if (message.toLowerCase().contains(anecdote)) {
                                    return new User(Const.TYPE_BOT, GetAnecdote.getAnecdote(), getTimeString());
                                }
                                if (message.toLowerCase().contains(weather)) {
                                    GetWeather getWeather = new GetWeather();
                                    return new User(Const.TYPE_WEATHER, getWeather.getMessage(), getWeather.getId(), getTimeString());
                                }
                                if (message.toLowerCase().contains(currency)) {
                                    return new User(Const.TYPE_SYSTEM, GetCurrency.getCurrency(), getTimeString());
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(User user) {
                                addToDb(user.getType(), user.getMessage(), user.getImageId(), user.getChatTime());
                                userList.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        }.execute();
                    } else {
                        Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }
                }
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addToDb(int type, String message, String imageId, String time) {
        ContentValues cv = new ContentValues();
        cv.put("type", type);
        cv.put("message", message);
        cv.put("chatTime", time);
        if (type == Const.TYPE_WEATHER) {
            cv.put("imageId", imageId);
        } else {
            cv.put("imageId", "");
        }
        db.insert("chat", null, cv);
    }


    private String getTimeString() {
        calendar = GregorianCalendar.getInstance(Locale.UK);
        return dateFormat.format(calendar.getTime());
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
