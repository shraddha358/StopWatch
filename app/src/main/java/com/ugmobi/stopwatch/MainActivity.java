package com.ugmobi.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private AppCompatButton startButton, stopButton, resetButton;
    private boolean isRunning = false;
    private long pauseOffset;
    private boolean isChronometerHeld = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(v -> {
            if (!isRunning) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                isRunning = true;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                resetButton.setEnabled(true);
            }
        });

        stopButton.setOnClickListener(v -> {
            if (isRunning) {
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                isRunning = false;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
            }
        });

        resetButton.setOnClickListener(v -> {
            if (isRunning) {
                if (isChronometerHeld) {
                    // Resume chronometer
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    isChronometerHeld = false;
                    resetButton.setText("Hold");
                } else {
                    // Pause chronometer
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    isChronometerHeld = true;
                    resetButton.setText("Resume");
                }
            }
        });
    }

}