package com.example.mohammad.gp_sps;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohammad on 10/14/2016.
 */
public class AllFriendsRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL="http://www.googleps.me/SPSProject/listall.php";
    private Map<String,String> params;

    public AllFriendsRequest( String username,int user_id, Response.Listener<String> listener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("user_id", String.valueOf(user_id));
    }
    @Override
    public  Map<String,String> getParams(){
        return params;
    }
}
