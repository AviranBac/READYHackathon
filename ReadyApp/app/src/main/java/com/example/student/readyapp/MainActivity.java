package com.example.student.readyapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RetrieveFeedTask().execute();

        ListView planesDetected = (ListView) findViewById(R.id.planesList);

        Detection firstDetection = new Detection(0, new Date(), 100, 1, true, false);
        Detection secondDetection = new Detection(1, new Date(), 100, 5, true, false);

        final List<Detection> planesDetections = new ArrayList<>();
        planesDetections.add(firstDetection);
        planesDetections.add(secondDetection);

        List<String> planesDetectionsDates = new ArrayList<>();
        planesDetectionsDates.add(firstDetection.getDetectionTime().toString());
        planesDetectionsDates.add(secondDetection.getDetectionTime().toString());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                planesDetectionsDates);

        planesDetected.setAdapter(arrayAdapter);

        planesDetected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), " " + planesDetections.get(position).getDistance(), Toast.LENGTH_LONG).show();
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("http://10.10.247.124:3000/url");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }

            //try {
                //JSONObject jObj = new JSONObject();
                //JSONObject mJsonObjectProperty = jObj.getJSONObject("main");
                //String tempature = mJsonObjectProperty.getString("temp");
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            //} //catch (JSONException e) {
               // e.printStackTrace();
            //}

        }
    }
}
