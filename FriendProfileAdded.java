package com.example.mohammad.gp_sps;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendProfileAdded extends AppCompatActivity {
    Response.Listener<String> responseListener;
    String currentUserName;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile_added);
        //session
        currentUserName = Session.getUsername();
        final int currentID = Session.getID();
        //
        final TextView etUserName = (TextView) findViewById(R.id.username);
        final TextView etEmail = (TextView) findViewById(R.id.email);
        final TextView etAge = (TextView) findViewById(R.id.age);
        final ImageView imageView2 = (ImageView)findViewById(R.id.imageView2);
        final Button CallPhone = (Button)findViewById(R.id.phone);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        final String name = intent.getStringExtra("name");
        final String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);
        final String photo = intent.getStringExtra("photo");
        final int phone = intent.getIntExtra("phone",-1);


        CallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0"+phone));
                if(intent.resolveActivity(getPackageManager()) != null ){
                    startActivity(intent);

                }
            }
        });


        etUserName.setText(username);
        etEmail.setText(name);
        etAge.setText(age + "");
        Picasso.with(FriendProfileAdded.this).load(photo).into(imageView2);




        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent theItem = new Intent(FriendProfileAdded.this, SendImage.class);
                theItem.putExtra("id",id);
                startActivity(theItem);
            }
        });




    }


}




