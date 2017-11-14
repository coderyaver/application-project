package com.example.yavar.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class stopwatch extends AppCompatActivity {
    Button btnstart,btnpause,btnlap;
    TextView txtTimer;
    Handler customHandler=new Handler();
    LinearLayout container;
    long startTime=0L,timeInMilliSeconds=0L,timeSwapBuff=0L,updateTime=0L;

     Runnable updateTimerThread= new Runnable() {
         @Override
         public void run() {
             timeInMilliSeconds=SystemClock.uptimeMillis()-startTime;
             updateTime=timeSwapBuff+timeInMilliSeconds;
             int secs=(int)(updateTime/1000);
             int mins=secs/60;
             secs%=60;
             int milliseconds=(int)(updateTime%1000);
             txtTimer.setText(""+mins+":"+String.format("%2d",secs)+":"
                                         +String.format("%3d",milliseconds));
             customHandler.postDelayed(this,0);
         }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        btnstart=(Button)findViewById(R.id.btnstart);
        btnpause=(Button)findViewById(R.id.btnpause);
        btnlap=(Button)findViewById(R.id.btnlap);
        txtTimer=(TextView) findViewById(R.id.timervalue);
        container=(LinearLayout)findViewById(R.id.container);

        btnstart.setOnClickListener(new View.OnClickListener()
                                    {
                                       @Override
                                        public void onClick(View view)
                                       {
                                           startTime= SystemClock.uptimeMillis();
                                           customHandler.postDelayed(updateTimerThread,0);
                                       }
                                    }

        );

        btnpause.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            timeSwapBuff+=timeInMilliSeconds;
                                            customHandler.removeCallbacks(updateTimerThread);
                                        }
                                    }

        );
        btnlap.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            LayoutInflater inflater=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View addview=inflater.inflate(R.layout.activity_row,null);
                                            TextView txtValue=(TextView)addview.findViewById(R.id.txtContent);
                                            txtValue.setText(txtTimer.getText());
                                            container.addView(addview);
                                        }
                                    }

        );
    }
}
