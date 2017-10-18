package com.example.mohammad.gp_sps;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohammad on 9/29/2016.
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://www.googleps.me/SPSProject/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String name,String username,int age,int phone,String password,Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("age",age + "");
        params.put("password",password);
        params.put("phone",phone+"");
    }
    @Override
    public  Map<String,String> getParams(){
        return params;
    }

}
