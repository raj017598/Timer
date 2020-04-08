package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button StartButton, StopButton;
    private TextView textView;
    private Handler handler;

    long start = 0L;
    long update = 0L;
    long swapTime = 0L;
    long millisecond = 0L;
    long delay = 0L;

    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartButton = (Button)findViewById(R.id.btnStart);
        StopButton = (Button)findViewById(R.id.btnStop);
        textView = (TextView)findViewById(R.id.textView);

        handler = new Handler();

        StartButton.setOnClickListener(this);
        StopButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.btnStart :
            {
                if(timerStarted)
                {
                    StartButton.setText("Start");
                    swapTime += millisecond;
                    handler.removeCallbacks(time);
                    timerStarted = !timerStarted;
                }
                else
                {
                    StartButton.setText("Pause");
                    start = SystemClock.uptimeMillis();
                    handler.postDelayed(time, delay);
                    timerStarted = !timerStarted;
                }
                break;
            }
            case R.id.btnStop:
            {
                start = 0L;
                update = 0L;
                swapTime = 0L;
                millisecond = 0L;

                timerStarted = false;

                StartButton.setText("Start");
                textView.setText("00:00:00");
                handler.removeCallbacks(time);
                break;
            }
            default:
            {
                break;
            }
        }

    }

    Runnable time = new Runnable() {
        @Override
        public void run() {

            millisecond = SystemClock.uptimeMillis() - start;
            update = swapTime + millisecond;
            int seconds = (int) (update/100);
            int minutes = seconds/60;
            seconds = seconds%60;
            int milliseconds = (int) (millisecond%100);
            String str = String.format("%02d",minutes)+":"+String.format("%02d",seconds)+":"+String.format("%02d",milliseconds);
            textView.setText(str);
            handler.postDelayed(this,delay);

        }
    };
}
