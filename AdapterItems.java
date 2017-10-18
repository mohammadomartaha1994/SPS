package com.example.mohammad.gp_sps;

/**
 * Created by mohammad on 10/2/2016.
 */
public class AdapterItems
{
    public   int ID;
    public  String UserName;
    public  String Password;
    public  String photo;
    public  String sender;
    public  String name;
    public  int age;
    public  String username;
    public  String password;
    public  String date;
    public  int phone;


    //for news details
    AdapterItems( int ID, String UserName,String Password)
    {
        this. ID=ID;
        this. UserName=UserName;
        this. Password=Password;
    }

    AdapterItems( int ID, String photo,String name,String sender,String date)
    {
        this. ID=ID;
        this. photo=photo;
        this. name=name;
        this. sender=sender;
        this. date=date;
    }

    AdapterItems( int ID, String name,String username,int  age,int phone,String password,String photo)
    {
        this. ID=ID;
        this. name=name;
        this. username=username;
        this. age=age;
        this. password=password;
        this. photo=photo;
        this. phone= phone;


    }

    AdapterItems( int ID, String photo,String name,String sender,String date,int phone)
    {
        this. ID=ID;
        this. photo=photo;
        this. name=name;
        this. sender=sender;
        this. date=date;
        this. phone=phone;
    }
}