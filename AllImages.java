package com.example.mohammad.gp_sps;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AllImages extends AppCompatActivity {
    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;
    Bitmap bmp;
    private boolean zoomOut;
    int currentID;
    EditText the_key;
    String the_key_value;
    TextView the_massage;
    ImageButton phoneimage;
    int HOUR;
    int MM;
    int MONTH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_images);
        //session
        String currentUserName=Session.getUsername();
        currentID=Session.getID();
        //
        Calendar c = Calendar.getInstance();
        HOUR = c.get(Calendar.HOUR_OF_DAY);
        MONTH = c.get(Calendar.DAY_OF_MONTH);
        MM=c.get(Calendar.MONTH)+1;


        Log.d("MONTH",MM+"");
        Log.d("HOUR",HOUR+"");


        final Intent theItem = new Intent(this,FriendProfile.class);
        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter);
        String url="http://www.googleps.me/SPSProject/allimagesBlob.php?id="+currentID+"";
        new MyAsyncTaskgetNews().execute(url);




    }


    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;
        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
            //return 2;
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
            View myView = mInflater.inflate(R.layout.layout_ticket, null);

            final   AdapterItems s = listnewsDataAdpater.get(position);

            String substr=s.date.substring(14,16);
            int strHour = Integer.valueOf(substr);
            String strMonth1=s.date.substring(8,10);
            int strMonth = Integer.valueOf(strMonth1) ;
            String strMM=s.date.substring(5,7);
            int strMonthMM = Integer.valueOf(strMM) ;
            Log.d("hour",strHour+"");
            Log.d("month",strMonthMM+"");

            if(strMonth < MONTH || strMonthMM > MM){

/////////////////////////// start delete
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplication(),"Image Deleted",Toast.LENGTH_LONG).show();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AllImages.this);
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
                    deleteImageRequest deleteImageRequest = new deleteImageRequest(s.ID,s.ID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AllImages.this);
                    queue.add(deleteImageRequest);

                    //////////////////////////////// end delete after time
                }





            Button delete = (Button)myView.findViewById(R.id.delete);
            Button save = (Button)myView.findViewById(R.id.save);



            //// make save button

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View promptView = layoutInflater.inflate(R.layout.the_key_dialog, null);

                    final AlertDialog alertD1 = new AlertDialog.Builder(AllImages.this).create();

                    the_key=(EditText)promptView.findViewById(R.id.btnAdd1);
                    phoneimage = (ImageButton)promptView.findViewById(R.id.phoneimage);
                    phoneimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:0"+s.phone));
                            if(intent.resolveActivity(getPackageManager()) != null ){
                                startActivity(intent);

                            }
                        }
                    });

                    Button btnAdd2 = (Button) promptView.findViewById(R.id.btnAdd2);

                    btnAdd2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            LayoutInflater layoutInflater = getLayoutInflater();
                            final View promptView = layoutInflater.inflate(R.layout.image_dialog, null);
                            final AlertDialog alertD = new AlertDialog.Builder(AllImages.this).create();
//                            final EditText the_key=(EditText)findViewById(R.id.the_key);
//                            the_key_value = the_key.getText().toString();

 //                           Toast.makeText(AllImages.this,the_key_value,Toast.LENGTH_LONG).show();
                            the_key_value = the_key.getText().toString();
                            ImageView imageView = (ImageView) promptView.findViewById(R.id.imageView3);

                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertD1.dismiss();
                                    alertD.dismiss();
                                }
                            });
                            new DownloadImageTask((ImageView)imageView).execute(s.photo,the_key_value);
                            alertD.setView(promptView);
                            alertD.show();
                        }
                    });




            alertD1.setView(promptView);

            alertD1.show();





                }

            });







            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { ////// delete image

                    {




            AlertDialog.Builder builder1 = new AlertDialog.Builder(AllImages.this);
            builder1.setMessage("Delete this image !");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {



                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (success) {
                                            Toast.makeText(getApplication(),"Image Deleted",Toast.LENGTH_LONG).show();
                                            Intent refresh = new Intent(AllImages.this, AllImages.class);
                                            startActivity(refresh);//Start the same Activity
                                            finish(); //finish Activity.

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(AllImages.this);
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
                            deleteImageRequest deleteImageRequest = new deleteImageRequest(s.ID,s.ID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(AllImages.this);
                            queue.add(deleteImageRequest);






                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();




                    }


                } ////// finish delete image
            });



            TextView msg = (TextView)myView.findViewById(R.id.msg);
            msg.setText(s.sender);

            TextView date = (TextView)myView.findViewById(R.id.date);
            date.setText(s.date);

            the_massage = (TextView)myView.findViewById(R.id.the_massage);
            the_massage.setText(s.name);







            String stringImage= s.photo;


            return myView;
        }

    }


    public class MyAsyncTaskgetNews extends AsyncTask<String, String, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected ArrayList<String>  doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<String> arr = new ArrayList<>();
            try {
                String NewsData;
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(7000);

                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    NewsData = ConvertInputToStringNoChange(in);
                    arr.add(NewsData);
                  //  publishProgress(NewsData);
                } finally {
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return arr;
        }
        protected void onProgressUpdate(String... progress) {


            try {
                JSONArray json=new JSONArray(progress[0]);
                for(int i=0;i<json.length();i++){
                    JSONObject user=json.getJSONObject(i);
                    listnewsData.add(new AdapterItems(user.getInt("id"),user.getString("photo"),
                            user.getString("name"),user.getString("sender"),user.getString("date"),
                            user.getInt("phone")));
                }
                myadapter.notifyDataSetChanged();


            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(ArrayList<String>  result2){

            try {
                for(int j = 0; j < result2.size(); j++) {
                    JSONArray json = new JSONArray(result2.get(j));
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject user = json.getJSONObject(i);
                        listnewsData.add(new AdapterItems(user.getInt("id"), user.getString("photo"),
                                user.getString("name"), user.getString("sender"), user.getString("date"),
                                user.getInt("phone")));
                    }
                }
                myadapter.notifyDataSetChanged();


            } catch (Exception ex) {
            }
        }




    }

    // this method convert any stream to string
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


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        public Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            String key = urls[1];
            Log.d("urldisplay",urldisplay);

            Bitmap mIcon11 = null;
            try {

                InputStream in = IOUtils.toInputStream(urldisplay, "UTF-8");
               urldisplay = AESCrypt.decrypt(key,urldisplay);
                mIcon11=StringToBitMap(urldisplay);


                } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }


        protected void onPostExecute(Bitmap result) {

            bmImage.setImageBitmap(result);
        }}
}
