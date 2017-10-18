package com.example.mohammad.gp_sps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SendImage extends AppCompatActivity implements View.OnClickListener {

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;


    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL;

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_SENDER = "sender";
    private String KEY_ID_SENDER = "Sid";
    private String KEY_DATE = "date";
    private String KEY_ID = "Aid";

    String currentUserName = Session.getUsername();
    int currentID = Session.getID();  /// id for sender
    int phoneNumber = Session.getphone();

    Intent intent;
    int id;
    String key;
    String Final_massage;
    EditText the_key, the_massage;
    ImageButton phone,message;
    int Callphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);


        UPLOAD_URL = "http://www.googleps.me/SPSProject/upload_photo_blob.php";

        //session
        //

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);


        the_key = (EditText) findViewById(R.id.the_key);
        the_massage = (EditText) findViewById(R.id.the_massage);
        phone = (ImageButton)findViewById(R.id.phone);



        message = (ImageButton) findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/*");
//                Uri uri_image =Uri.parse("android.resource://" + getPackageName() + "/" + R.mipmap.ic_launcher);
//                intent.putExtra(Intent.EXTRA_STREAM,uri_image);
//
//                Intent chooser = Intent.createChooser(intent,"Share Key Via ..");
//                if(intent.resolveActivity(getPackageManager()) != null ){
//                    startActivity(chooser);
//
//                }

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is the key we are going to Share :\n "+the_key.getText().toString());
                sendIntent.setType("text/plain");
                // Put this line here
                sendIntent.setPackage("com.whatsapp");
                //
                startActivity(sendIntent);
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);





        intent = getIntent();
        id = intent.getIntExtra("id", -1);  // id for friend

        Callphone = intent.getIntExtra("phone",-1);


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0"+Callphone));
                if(intent.resolveActivity(getPackageManager()) != null ){
                    startActivity(intent);

                }
            }
        });




        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {

        if(the_key.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext()," Please, Put The Key ! ",Toast.LENGTH_LONG).show();
        }
        else if (the_massage.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext()," Write a Massage ",Toast.LENGTH_LONG).show();
        }
        else  {


        key = the_key.getText().toString();
        Final_massage = the_massage.getText().toString();


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(SendImage.this, "Successful", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        //Toast.makeText(SendImage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(SendImage.this, " successful ", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
               String image = getStringImage(bitmap);
                //byte[] image = getBitmapBytes(bitmap);
                String image1 = "";

                Log.d("image", key);

                //// encrypt
                try {
                    //image = rsa.encrypt(image);
                    image = AESCrypt.encrypt(key, image);

                } catch (GeneralSecurityException e) {
                    Toast.makeText(getApplicationContext(), "NOT ENCRYPTED", Toast.LENGTH_LONG).show();
                }




//                try {
//                    image = AESCrypt.decrypt("mohammad",image);
//
//            } catch (GeneralSecurityException e) {
//                Toast.makeText(getApplicationContext(),"NOT DECRYPTED",Toast.LENGTH_LONG).show();
//            }


                //Getting Image Name

                String sender = currentUserName;

                Log.d("the_key", Final_massage);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd....HH:mm:ss");
                final String currentDateandTime = sdf.format(new Date());
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //
                Bitmap x = StringToBitMap(image);

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, Final_massage);
                params.put(KEY_SENDER, sender);
                params.put(KEY_ID_SENDER, id + "");
                params.put("phone",phoneNumber+"");

                // params.put(KEY_ID,currentID+"");
                params.put(KEY_DATE, currentDateandTime);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


}

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == buttonChoose){
            showFileChooser();
        }

        if(v == buttonUpload){
            uploadImage();
        }
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


    ////////////////////////////////////////////////////////////////////////////////////////////


    private byte[] getBitmapBytes(Bitmap bitmap)
    {
        int chunkNumbers = 10;
        int bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
        byte[] imageBytes = new byte[bitmapSize];
        int rows, cols;
        int chunkHeight, chunkWidth;
        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        int yCoord = 0;
        int bitmapsSizes = 0;

        for (int x = 0; x < rows; x++)
        {
            int xCoord = 0;
            for (int y = 0; y < cols; y++)
            {
                Bitmap bitmapChunk = Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkWidth, chunkHeight);
                byte[] bitmapArray = getBytesFromBitmapChunk(bitmapChunk);
                System.arraycopy(bitmapArray, 0, imageBytes, bitmapsSizes, bitmapArray.length);
                bitmapsSizes = bitmapsSizes + bitmapArray.length;
                xCoord += chunkWidth;

                bitmapChunk.recycle();
                bitmapChunk = null;
            }
            yCoord += chunkHeight;
        }

        return imageBytes;
    }


    private byte[] getBytesFromBitmapChunk(Bitmap bitmap)
    {
        int bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmapSize);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }

}