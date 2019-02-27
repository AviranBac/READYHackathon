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

import org.json.JSONArray;
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

    List<Detection> planesDetections = new ArrayList<>();
    ListView planesDetected;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RetrieveFeedTask().execute();

        planesDetected = (ListView) findViewById(R.id.planesList);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

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
                URL url = new URL("http://10.10.247.124:3000/detections");
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

            try {
                JSONArray allDetections = new JSONArray(response);
                Toast.makeText(getApplicationContext(), allDetections.length() + "", Toast.LENGTH_LONG).show();

                for (int i = 0; i < allDetections.length(); i++) {
                    JSONObject detection = allDetections.getJSONObject(i);
                    int id = detection.getInt("ID");
                    String detectionTime = detection.getString("DETECTION_TIME");
                    double height = detection.getDouble("HEIGHT");
                    double distance = detection.getDouble("DISTANCE");
                    String isOurs = detection.getString("IS_OURS");
                    String isShutDown = detection.getString("IS_SHUTDOWN");
                    Detection currDetection = new Detection(id, detectionTime, height, distance, isOurs.equals("כן"), isShutDown.equals("לא"));
                    planesDetections.add(currDetection);
                    arrayAdapter.add(currDetection.getDetectionTime().toString());
                }
                planesDetected.setAdapter(arrayAdapter);

            } catch (JSONException e) {
                    e.printStackTrace();
            }
        }
    }
}
