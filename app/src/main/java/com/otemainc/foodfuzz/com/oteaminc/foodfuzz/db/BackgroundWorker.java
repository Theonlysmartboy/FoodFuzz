package com.otemainc.foodfuzz.com.oteaminc.foodfuzz.db;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.otemainc.foodfuzz.LoginActivity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<String,Void,Void> {
    protected Context context;
    public BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(String... strings) {
        String type = strings[0];
        String login_url = "http://10.0.2.2:8082/foodfuzzbackend/auth/login.php";
        if(type.equals("login")){
            try {
                URL url = new URL(login_url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream loginout = con.getOutputStream();
                BufferedWriter loginwriter = new BufferedWriter(new OutputStreamWriter(loginout, StandardCharsets.UTF_8));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
