package com.example.mohammad.gp_sps;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText etUserName = (EditText) findViewById(R.id.username);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final EditText etAge = (EditText) findViewById(R.id.password2);
        final EditText etName = (EditText) findViewById(R.id.mail);
        final EditText etphone = (EditText) findViewById(R.id.phone);
        final Button bRegister = (Button) findViewById(R.id.done);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final int phone = Integer.valueOf(String.valueOf(etphone.getText()));
                final int age = Integer.valueOf(String.valueOf(etAge.getText()));


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(Signup.this, LogIn.class);
                                Signup.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
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

                RegisterRequest registerRequest = new RegisterRequest(name, username, age,phone, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Signup.this);
                queue.add(registerRequest);

            }


        });
    }}
