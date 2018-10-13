package com.at.bertogonz3000.activitytimer;

import android.app.Activity;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class timerAdapter extends RecyclerView.Adapter<timerAdapter.timerVH> {

    private ArrayList<ActivityTimer> dataSet;

    //ViewHolder class
    public static class timerVH extends RecyclerView.ViewHolder{

        private TextView tv;
        private Chronometer chrono;
        private Button btnStartStop, editButton;
        private boolean stopped = true;
        private long pauseOffset;
        private ActivityTimer timer;

        public timerVH(View v){
            super(v);

            tv = v.findViewById(R.id.tvName);
            chrono = v.findViewById(R.id.chrono);
            chrono.setBase(SystemClock.elapsedRealtime());

            btnStartStop = v.findViewById(R.id.btnStartStop);
            btnStartStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startStop();
                }
            });

            editButton = v.findViewById(R.id.editButton);
            //TODO - Set the onClickListener for editButton
        }

        public void startStop() {
                if (stopped) {
                    timer.start();
                    chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chrono.start();
                    stopped = false;
                    btnStartStop.setText("Stop");
                } else {
                    timer.stop();
                    chrono.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
                    stopped = true;
                    btnStartStop.setText("Start");
                }
            }


    }


    //Constructor
    public timerAdapter(ArrayList<ActivityTimer> dataSet){
        this.dataSet = dataSet;
    }

    //Create new views
    @NonNull
    @Override
    public timerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_timer, parent, false);

        timerVH vh = new timerVH(v);

        return vh;
    }

    //Replace the contents of a view
    @Override
    public void onBindViewHolder(@NonNull timerVH holder, int position) {
        //Setup the textView
        holder.tv.setText(dataSet.get(position).getName());

        //Setup the ActivityTimer
        holder.timer = dataSet.get(position);

        //Once the timer has been set, we can get the time from the chrono
        holder.chrono.setBase(SystemClock.elapsedRealtime()
                                                - holder.timer.getChronoTime());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
