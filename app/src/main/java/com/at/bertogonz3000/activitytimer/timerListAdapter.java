package com.at.bertogonz3000.activitytimer;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;

public class timerListAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<ActivityTimer> dataSet;

    public timerListAdapter(Context context, ArrayList<ActivityTimer> dataSet){
        super();
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        //This is like a third way to refer to some object - for now
        //I dont want to use it, so I'm just going to return position
        //This MAY come in handy when I adapt this app to work
        //on the firebase database...but not just yet.
        //  https://stackoverflow.com/questions/6711592/what-is-the-intent-of-the-methods-getitem-and-getitemid-in-the-android-class-bas
        //TODO - figure out how to use this with firebase!
        return position;
    }


    //This is the method that actually updates the views in the
    //ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Inflate the activity_timer layout from R
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View timerView = inflater.inflate(R.layout.activity_timer, parent, false);

        TextView tvName = (TextView) timerView.findViewById(R.id.tvName);
        final Chronometer chrono = (Chronometer) timerView.findViewById(R.id.chrono);
        final Button btnStartStop = (Button) timerView.findViewById(R.id.btnStartStop);
        Button resetButton = (Button) timerView.findViewById(R.id.resetButton);
        Button editButton = (Button) timerView.findViewById(R.id.editButton);

        btnStartStop.setOnClickListener(new View.OnClickListener() {

            Boolean stopped = true;
            long pauseOffset;

            @Override
            public void onClick(View v) {
                if (stopped){
                    chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chrono.start();
                    stopped = false;
                    btnStartStop.setText("Stop");
                } else {
                    chrono.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
                    stopped = true;
                    btnStartStop.setText("Start");
                }
            }
        });

        return timerView;

    }
}
