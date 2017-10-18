package com.example.mohammad.gp_sps;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohammad on 10/15/2016.
 */
public class Request_Session extends StringRequest {
    String currentUserName=Session.getUsername();
    int currentID=Session.getID();


        private static final String REGISTER_REQUEST_URL="http://www.googleps.me/SPSProject/Register.php";
        private Map<String,String> params;

        public Request_Session(String name,String username,int age,String password, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("age",age + "");
        params.put("password",password);
    }
        @Override
        public  Map<String,String> getParams(){
        return params;
    }

    }
///////// get from siginup