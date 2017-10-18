package com.example.mohammad.gp_sps;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by mohammad on 10/18/2016.
 */

public class FriendRequestWithKey extends StringRequest {
   // private static final String REGISTER_REQUEST_URL="http://googleps.me/SPSProject/addFriend.php";
   private static final String REGISTER_REQUEST_URL="http://googleps.me/SPSProject/addFriend.php";

    private Map<String,String> params;

    public FriendRequestWithKey(int user_ID, int userFriends_ID , String password ,Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("user_ID",user_ID+"");
        params.put("userFriends_ID",userFriends_ID+"");
        params.put("password",password);
        Log.d("key",password);
    }

    @Override
    public  Map<String,String> getParams(){
        return params;
    }

}
