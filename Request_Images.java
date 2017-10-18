package com.example.mohammad.gp_sps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Request_Images extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__images);

        //session
        String currentUserName=Session.getUsername();
        int currentID=Session.getID();
        //


    }
}
