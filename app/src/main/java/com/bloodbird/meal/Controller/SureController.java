package com.bloodbird.meal.Controller;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bloodbird.meal.Models.Sure;
import com.bloodbird.meal.Models.SureCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SureController {


void getSureInList(Context context, int ayet_no, final SureCallback sureCallback){
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    String url = "https://api.acikkuran.com/surah/" + ayet_no + "?author=13";

    JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null,

            new Response.Listener<JSONObject>() {

                // Takes the response from the JSON request
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject obj = response.getJSONObject("data");

                        Gson gson = new Gson();
                        sureCallback.sure= gson.fromJson(String.valueOf(obj), Sure.class);
                        sureCallback.done=true;
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            },
            // The final parameter overrides the method onErrorResponse() and passes VolleyError
            //as a parameter
            new Response.ErrorListener() {
                @Override
                // Handles errors that occur due to Volley
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", "Error");
                }
            }
    );
    // Adds the JSON object request "obreq" to the request queue
    requestQueue.add(obreq);



}

Sure waitCall(SureCallback sureCallback){
    while(!sureCallback.done);

    return sureCallback.sure;
}



}
