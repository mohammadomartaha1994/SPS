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

public class FriendProfile extends AppCompatActivity {
    Response.Listener<String> responseListener;
    String currentUserName;
    int currentID;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        //session
        currentUserName = Session.getUsername();
        currentID = Session.getID();
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
        final String photo =intent.getStringExtra("photo");
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
        Picasso.with(FriendProfile.this).load(photo).into(imageView2);


        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplication(), "Friend Added", Toast.LENGTH_LONG).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FriendProfile.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                };
                FriendRequestWithKey friendRequest = new FriendRequestWithKey(currentID, id,"mohammad", responseListener);
                RequestQueue queue = Volley.newRequestQueue(FriendProfile.this);
                queue.add(friendRequest);







            }
        });



    }


}




