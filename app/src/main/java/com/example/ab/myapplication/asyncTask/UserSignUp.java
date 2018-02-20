package com.example.ab.myapplication.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ab.myapplication.constants.Constants;

import org.json.JSONObject;

import java.util.HashMap;

public class UserSignUp extends AsyncTask<Void, Void, Boolean> {
    Context contex;
    String uname;
    String uemail;
    String uaddress;
    String umobile;
    String upwd;
    String ucpwd;
    boolean success;
    Response.Listener listener;

    public UserSignUp(Context contex, String uname, String uemail, String uaddress, String umobile, String upwd, String ucpwd, Response.Listener successListener) {
        this.contex = contex;
        this.uname = uname;
        this.uemail = uemail;
        this.uaddress = uaddress;
        this.umobile = umobile;
        this.upwd = upwd;
        this.ucpwd = ucpwd;
        this.listener = successListener;
        success = false;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(contex);
            String url = Constants.SERVER_URL + "/user/";
            HashMap<String, String> paramsBody = new HashMap<>();
            paramsBody.put("name", uname);
            paramsBody.put("email", uemail);
            paramsBody.put("password", upwd);
            paramsBody.put("address", uaddress);
            paramsBody.put("mobileNo", umobile);
            JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(paramsBody), listener, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error while signUp request.************"+error.toString());
                }
            });
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}