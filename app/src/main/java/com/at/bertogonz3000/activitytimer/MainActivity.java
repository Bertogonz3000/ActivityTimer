package com.at.bertogonz3000.activitytimer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser user;

    private ArrayList<ActivityTimer> timers = new ArrayList<ActivityTimer>();

//    //ListView declarations
//    private ListView timerListView;
//    private timerListAdapter adapter;

    //ReyclerView declarations
    private RecyclerView rvTimers;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init the recycler view
        initRV();

//        //Initiate the List View
//        initLV();

        //Initiate user sign in through Firebase
        initAuth();

    }

    //Setup Firebase User Authentication
    private void initAuth(){

        //Selecting auth providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        //Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), 0624);
    }

    //Return from sign in Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0624){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                //Success!
                user = FirebaseAuth.getInstance().getCurrentUser();
            } else {
                //fail!
                Toast.makeText(this, "Sign-in failed!", Toast.LENGTH_LONG).show();
            }

        }
    }

//    //Init the List View
//    private void initLV(){
//
//        timerListView = findViewById(R.id.timerListView);
//
//        adapter = new timerListAdapter(this, timers);
//
//        timerListView.setAdapter(adapter);
//
//        timerListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//    }

    //Init recyclerView
    private void initRV(){
        //link the RV to the layout RV
        rvTimers = findViewById(R.id.rvTimers);

        //We know that changes in content don't change layout size of RV, so we set it
        rvTimers.setHasFixedSize(true);

        //Init layout manager
        manager = new LinearLayoutManager(this);
        rvTimers.setLayoutManager(manager);

        //Set the adapter
        adapter = new timerAdapter(timers);
        rvTimers.setAdapter(adapter);
    }


    //On click for sign out button
    public void soOnClick(View v){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getBaseContext(), "Sign out Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //OnClick to create a new timer
    public void newTimer(View v){
        //Make a popup to input information
        ActivityTimer timer = new ActivityTimer("Hello, World!", this);
        timers.add(timer);
        adapter.notifyItemInserted(timers.size()-1);
        //adapter.notifyDataSetChanged();
    }


}
