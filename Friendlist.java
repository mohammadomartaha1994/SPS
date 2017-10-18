package com.example.mohammad.gp_sps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Friendlist extends AppCompatActivity {
    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    Friendlist.MyCustomAdapter myadapter;
    TextView etUserName;
    int currentID;
    String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        //session
        currentUserName=Session.getUsername();
        currentID=Session.getID();
        //
        final Intent theItem = new Intent(this,FriendProfile.class);
        myadapter=new Friendlist.MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter);

//

        String url="http://www.googleps.me/SPSProject/showfriends.php?id="+currentID+"";

        new Friendlist.MyAsyncTaskgetNews().execute(url);


    }
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;
        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket_friendlist, null);

            final   AdapterItems s = listnewsDataAdpater.get(position);

            etUserName=(TextView)myView.findViewById(R.id.etUserName);
            etUserName.setText(s.username);

            ImageView image=(ImageView)myView.findViewById(R.id.imageView4);
            Picasso.with(Friendlist.this).load(s.photo).into(image);

            Button AddFriendBtn = (Button)myView. findViewById(R.id.AddFriendBtn);
            AddFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    {


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        Toast.makeText(getApplication(),"Friend Deleted",Toast.LENGTH_LONG).show();

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Friendlist.this);
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
                        nunFriendRequest nunfriendRequest = new nunFriendRequest(currentID,s.ID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Friendlist.this);
                        queue.add(nunfriendRequest);

                        Intent refresh = new Intent(Friendlist.this, Friendlist.class);
                        startActivity(refresh);//Start the same Activity
                        finish(); //finish Activity.

                    }


                }
            });



            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent theItem = new Intent(Friendlist.this, FriendProfileAdded.class);
                    theItem.putExtra("id",s.ID);
                    theItem.putExtra("name",s.name);
                    theItem.putExtra("username",s.username);
                    theItem.putExtra("age",s.age);
                    theItem.putExtra("photo",s.photo);
                    theItem.putExtra("phone",s.phone);
                    startActivity(theItem);
                }
            });




            return myView;
        }

    }

    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(7000);

                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    NewsData = ConvertInputToStringNoChange(in);
                    publishProgress(NewsData);
                } finally {
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                JSONArray json=new JSONArray(progress[0]);
                for(int i=0;i<json.length();i++){
                    JSONObject user=json.getJSONObject(i);
                    listnewsData.add(new AdapterItems(user.getInt("user_id"),
                            user.getString("name"),user.getString("username"),
                            user.getInt("age"),user.getInt("phone"),user.getString("password"),
                            user.getString("photo")));
                }
                myadapter.notifyDataSetChanged();

            } catch (Exception ex) {
            }

        }

        protected void onPostExecute(String  result2){

        }


    }

    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }

}