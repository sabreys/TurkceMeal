package com.bloodbird.meal;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bloodbird.meal.Models.Sure;
import com.bloodbird.meal.Models.Sures;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView text;
    Button button;
    EditText sure_no;
    Context context;
    ImageView play, stop;
    Spinner  spinner1;
    MediaPlayer mediaPlayer;

    Sure currentSure;
    Sures[] sures;
    String[] sure_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        text = findViewById(R.id.text);
        button = findViewById(R.id.button);
        sure_no = findViewById(R.id.sayi);
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        sure_name=new String[114];
        sures=new Sures[114];
          spinner1 = (Spinner) findViewById(R.id.spinner);





        mediaPlayer = new MediaPlayer();

        button.setOnClickListener(araButonListener);

        play.setOnClickListener(playButtonListener);
        stop.setOnClickListener(stopButtonListener);

        getSurahList();
    }


    View.OnClickListener araButonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            text.setText("");
            GetSurah asyncTask = new GetSurah();
            asyncTask.execute(sure_no.getText().toString());
        }
    };

    View.OnClickListener playButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mediaPlayer.start();
        }
    };

    View.OnClickListener stopButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mediaPlayer.pause();
        }
    };

    void play_mp3(String uri) {
        Uri myUri = Uri.parse(uri);

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();

        }
        try {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare(); //don't use prepareAsync for mp3 playback
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getSurahList(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://api.acikkuran.com/surahs";

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray obj = response.getJSONArray("data");


                            for(int i=0;i<obj.length();i++)
                            {

                                JSONObject jb1 = obj.getJSONObject(i);
                                sures[i]=new Gson().fromJson(String.valueOf(jb1),Sures.class);
                            }


                            for (int i = 0; i < 114; i++) {
                               sure_name[i]=(i+1)+". "+sures[i].name;
                               Log.d("deneme",sures[i].name);
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (context, android.R.layout.simple_spinner_item,sure_name );

                            dataAdapter.setDropDownViewResource
                                    (android.R.layout.simple_spinner_dropdown_item);

                            spinner1.setAdapter(dataAdapter);

                            spinner1.setOnItemSelectedListener(surahSelectListener);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        requestQueue.add(obreq);
    }

 AdapterView.OnItemSelectedListener surahSelectListener=new AdapterView.OnItemSelectedListener() {
     @Override
     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         text.setText("");
         GetSurah asyncTask = new GetSurah();
         asyncTask.execute(i+1+"");
         ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
     }

     @Override
     public void onNothingSelected(AdapterView<?> adapterView) {

     }
 };
    class GetSurah extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... strings) {



            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String url = "https://api.acikkuran.com/surah/" + strings[0] + "?author=13";

            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null,

                    new Response.Listener<JSONObject>() {

                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject obj = response.getJSONObject("data");

                                Gson gson = new Gson();
                                Sure sure = gson.fromJson(String.valueOf(obj), Sure.class);
                                currentSure = sure;
                                for (int i = 0; i < sure.verse_count; i++) {
                                    text.append(i + ": " + sure.verses.get(i).translation.text + "\n");
                                }

                                play_mp3(sure.audio.mp3);


                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override

                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                        }
                    }
            );
            // Adds the JSON object request "obreq" to the request queue
            requestQueue.add(obreq);


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    }


}