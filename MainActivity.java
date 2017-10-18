package com.example.mohammad.gp_sps;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView etUserName = (TextView)findViewById(R.id.aetUserName);
        final TextView etAge = (TextView)findViewById(R.id.aetAge);
        final TextView welcomeMassage = (TextView)findViewById(R.id.tvWelcomeMsg);
        final Button btn = (Button)findViewById(R.id.button);
        final TextView etphone = (TextView)findViewById(R.id.phone);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddProfileImage.class);
                startActivity(intent);
            }
        });



        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);
        int user_id = intent.getIntExtra("user_id",-1);
        String photo = intent.getStringExtra("photo");
        int phone = intent.getIntExtra("phone",-1);


        String message = name;
        welcomeMassage.setText(username);
        etUserName.setText(message);
        etAge.setText(age + "");
        etphone.setText(phone+"");



        ImageView etimage = (ImageView)findViewById(R.id.imageView2);
        Picasso.with(MainActivity.this).load(photo).into(etimage);



//
        Session.setUsername(username);
        Session.setID(user_id);
        Session.setage(age);
        Session.setname(name);
        Session.setphoto(photo);
        Session.setphone(phone);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View mHeaderView = navigationView.getHeaderView(0);


        TextView companyNameTxt = (TextView) mHeaderView.findViewById(R.id.navname);
        companyNameTxt.setText(username);
       // TextView companyemalTxt = (TextView) mHeaderView.findViewById(R.id.navemail);
        //companyemalTxt.setText(message);
        ImageView imageView =(ImageView) mHeaderView.findViewById(R.id.imageView);
        Picasso.with(MainActivity.this).load(photo).into(imageView);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            final Intent myintent=new Intent(this,AddFriends.class);
            startActivity(myintent);
        } else if (id == R.id.nav_gallery) {
            final Intent myintent=new Intent(this,Friendlist.class);
            startActivity(myintent);
        } else if (id == R.id.nav_slideshow) {
            final Intent myintent=new Intent(this,SendImageForFriend.class);
            startActivity(myintent);

        } else if (id == R.id.gallery) {
            final Intent myintent=new Intent(this,AllImages.class);
            startActivity(myintent);

        } else if (id == R.id.nav_send) {
            final Intent myintent=new Intent(this,LogIn.class);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myintent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


