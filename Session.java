package com.example.mohammad.gp_sps;

import android.app.Application;

/**
 * Created by mohammad on 10/14/2016.
 */
public class Session extends Application {

    private static String username;
    private static int id;
    private static String name;
    private static int age;
    private static String photo;
    private static int phone;


    @Override
    public void onCreate() {
        super.onCreate();
        username="";
        id=0;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static int getID() {
        return id;
    }

    public static void setID(int id) {
        Session.id = id;
    }

    public static String getname(){return name;}
    public static void setname(String name){Session.name = name;}

    public static int getage(){return age;}
    public static void setage(int age){Session.age = age;}

    public static String getphoto(){return photo;}
    public static void setphoto(String photo){Session.photo = photo;}

    public static int getphone(){return phone;}
    public static void setphone(int phone){Session.phone = phone;}


}