package com.example.student.readyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    List<Detection> planesDetections;
    ListView planesDetected;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RetrieveFeedTask().execute();

        planesDetected = (ListView) findViewById(R.id.planesList);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, new ArrayList<String>());

        planesDetected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), " " + planesDetections.get(position).getDistance(), Toast.LENGTH_LONG).show();
                Detection detectionToShow = planesDetections.get(position);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                TextView detectionID = mView.findViewById(R.id.detectionId);
                TextView detectionTime = mView.findViewById(R.id.detectionTime);
                TextView height = mView.findViewById(R.id.height);
                TextView distance = mView.findViewById(R.id.distance);
                TextView isOurs = mView.findViewById(R.id.isOurs);
                TextView isShotDown = mView.findViewById(R.id.isShotDown);

                detectionID.setText("מספר גילוי: " + detectionToShow.getId());
                detectionTime.setText("זמן גילוי: " + detectionToShow.getDetectionTime());
                height.setText("גובה: " + detectionToShow.getHeight());
                distance.setText("מרחק: " + detectionToShow.getDistance());

                if (detectionToShow.isShotDown()) {
                    isShotDown.setText("יורט");
                } else {
                    isShotDown.setText("לא יורט");
                }

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new RetrieveFeedTask().execute();
            }
        }, 0, 1000);
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listrow, R.id.textView2, new ArrayList<String>());
            planesDetections = new ArrayList<>();
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
                //Toast.makeText(getApplicationContext(), allDetections.length() + "", Toast.LENGTH_LONG).show();

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
