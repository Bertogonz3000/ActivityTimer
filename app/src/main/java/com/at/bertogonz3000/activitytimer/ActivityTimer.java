package com.at.bertogonz3000.activitytimer;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import java.sql.Time;

public class ActivityTimer {

    private Chronometer chrono;

    private String name;

    private long pauseOffset = 0, chronoTime = 0;

    //Constructor
    public ActivityTimer(String name, Context context){
        this.name = name;
        chrono = new Chronometer(context);
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronoTime += 1000;
                Log.i("TIMETEST", "TICK! Time= " + chronoTime);
            }
        });


        }


    //METHODS PERTAINING TO THE CHRONO


    //Start the chrono
    public void start(){
        chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chrono.start();
        Log.e("TIMETEST", "Started");
    }


    //Stop the chrono
    public void stop(){
        chrono.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
        Log.e("TIMETEST", "Stopped");

    }


    //Set the time of the chrono
    public void setTime(int days, int hours, int minutes, int seconds){

        long newTime = (days * 86400000) + (hours * 3600000) + (minutes * 60000) + (seconds * 1000);

        chrono.setBase(SystemClock.elapsedRealtime() - newTime);
    }


    //Get the elapsedTime from the chrono
    public long getChronoTime(){
        return chronoTime;
    }





    //NAME METHODS

    //set name
    public void setName(String name){
        this.name = name;
    }

    //get name
    public String getName(){
        return name;
    }
}
