package com.example.mohammad.gp_sps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //session
        String currentUserName=Session.getUsername();
        int currentID=Session.getID();
        //

        final EditText etUserName = (EditText)findViewById(R.id.UserName);
        final EditText etPassword = (EditText)findViewById(R.id.Password);
        final Button bLogin = (Button)findViewById(R.id.login);
        final Button registerLink = (Button)findViewById(R.id.signup);
        final Button forgot = (Button)findViewById(R.id.forgotPass);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this,Forgot.class);
                startActivity(intent);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LogIn.this,Signup.class);
                LogIn.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final ProgressDialog loading = ProgressDialog.show(LogIn.this,"LogIn...","Please wait...",false,false);
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");
                                int user_id = jsonResponse.getInt("user_id");
                                String photo = jsonResponse.getString("photo");
                                int phone = jsonResponse.getInt("phone");
                                Intent intent = new Intent(LogIn.this,MainActivity.class);
                                intent.putExtra("name",name);
                                intent.putExtra("username",username);
                                intent.putExtra("age",age);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("photo",photo);
                                intent.putExtra("phone",phone);
                                loading.dismiss();
                                LogIn.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
                                builder.setMessage("Login Faild")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                                loading.dismiss();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest LoginRequest = new LoginRequest(username,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LogIn.this);
                queue.add(LoginRequest);

            }
        });
    }
}
