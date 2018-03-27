package com.example.ab.myapplication.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ab.myapplication.constants.Constants;

public class AllBlocksInfo extends AsyncTask<Void, Void, Boolean> {
    Context contex;
    String blockId;
    private Response.Listener successListener;
    boolean result = false;

    public AllBlocksInfo(Context contex, Response.Listener successListener) {
        this.blockId = blockId;
        this.contex = contex;
        this.successListener = successListener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(contex);
            String url = Constants.SERVER_URL + "/parkingblock/";
            StringRequest request = new StringRequest(url, successListener, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error while logging in");
                }
            });
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}