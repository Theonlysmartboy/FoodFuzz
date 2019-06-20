package com.otemainc.foodfuzz.com.oteaminc.foodfuzz.db;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.otemainc.foodfuzz.LoginActivity;
import com.otemainc.foodfuzz.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    protected Context context;
    AlertDialog alert;
        public BackgroundWorker(Context ctx) {
        this.context = ctx;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String email = strings[1];
        String pass = strings[2];
        String login_url = "http://192.168.100.250:8082/foodfuzzbackend/auth/login.php";
        if(type.equals("login")){
            try {
                URL url = new URL(login_url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream loginout = con.getOutputStream();
                BufferedWriter loginwriter = new BufferedWriter(new OutputStreamWriter(loginout, StandardCharsets.UTF_8));
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                loginwriter.write(post_data);
                loginwriter.flush();
                loginwriter.close();
                loginout.close();
                InputStream loginin = con.getInputStream();
                BufferedReader loginreader = new BufferedReader(new InputStreamReader(loginin, StandardCharsets.ISO_8859_1));
                String result = "";
                String line;
                while ((line = loginreader.readLine())!=null){
                    result += line;
                }
                loginreader.close();
                loginin.close();
                con.disconnect();
                return result;
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
        alert = new AlertDialog.Builder(context).create();
        alert.setTitle("Login");
        alert.setIcon(R.drawable.ic_notifications_active);
            }

    @Override
    protected void onPostExecute(String result) {
        alert.setMessage(result);
        alert.show();
           }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}