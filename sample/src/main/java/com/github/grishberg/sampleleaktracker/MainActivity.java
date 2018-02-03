package com.github.grishberg.sampleleaktracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<int[]> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.trackCreatedActivity(this);

        createLargeObject();
        simulateMemoryLeak();
    }

    private void createLargeObject() {
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "createLargeObject: i = " + i);
            list.add(new int[1024 * 1024 * 2]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.logReferences();
    }

    private void simulateMemoryLeak() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "start thread with leak");
                try {
                    Thread.sleep(60 * 1000 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
