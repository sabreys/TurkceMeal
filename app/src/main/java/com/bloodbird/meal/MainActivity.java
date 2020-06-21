package com.bloodbird.meal;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bloodbird.meal.Models.Sure;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView text;
    Button button;
    EditText sure_no;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        text = findViewById(R.id.text);
        button=findViewById(R.id.button);
        sure_no=findViewById(R.id.sayi);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                String url="https://api.acikkuran.com/surah/"+sure_no.getText()+"?author=13";

                JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url,null,

                        new Response.Listener<JSONObject>() {

                            // Takes the response from the JSON request
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject obj = response.getJSONObject("data");

                                    Gson gson = new Gson();
                                    Sure sure=gson.fromJson(String.valueOf(obj),Sure.class);

                                    for (int i=0;i<sure.verse_count;i++){
                                        text.append(i+": "+ sure.verses.get(i).translation.text+"\n");
                                    }

                                }

                                catch (JSONException e) {

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
        });



    }
}