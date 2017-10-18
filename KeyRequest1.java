package com.example.mohammad.gp_sps;

/**
 * Created by mohammad on 11/20/2016.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by mohammad on 11/20/2016.
 */

public class KeyRequest1 extends StringRequest {
    private static final String LOGIN_REQUEST_URL="http://www.googleps.me/SPSProject/getthekey.php";
    private Map<String,String> params;

    public KeyRequest1( int currentID,int id, Response.Listener<String> listener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("currentID",currentID+"");
        params.put("id",id+"");
    }
    @Override
    public  Map<String,String> getParams(){
        return params;
    }
}
