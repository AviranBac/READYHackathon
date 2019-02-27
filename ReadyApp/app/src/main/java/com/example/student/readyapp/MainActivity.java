package com.example.student.readyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView planesDetected = (ListView) findViewById(R.id.planesList);
        List<String> planesDetections = new ArrayList<>();
        planesDetections.add("one");
        planesDetections.add("two");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                planesDetections);

        planesDetected.setAdapter(arrayAdapter);
        
    }
}
