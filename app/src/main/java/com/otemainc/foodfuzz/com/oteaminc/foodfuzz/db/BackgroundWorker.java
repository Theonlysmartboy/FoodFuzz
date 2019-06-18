package com.otemainc.foodfuzz.com.oteaminc.foodfuzz.db;

import android.content.Context;
import android.os.AsyncTask;

import com.otemainc.foodfuzz.LoginActivity;

public class BackgroundWorker extends AsyncTask<String,Void,Void> {
    Context context;
    public BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected Void doInBackground(String... strings) {
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
