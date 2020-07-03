package com.bloodbird.meal;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.savedstate.SavedStateRegistry;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bloodbird.meal.Database.MDatabase;
import com.bloodbird.meal.Database.SuresDb;
import com.bloodbird.meal.Database.VerseDb;
import com.bloodbird.meal.Models.Author;
import com.bloodbird.meal.Models.Sure;
import com.bloodbird.meal.Models.Sures;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView text;
    Button button;

    Context context;
    ImageView play, stop, yazar;
    Spinner spinner1;
    MediaPlayer mediaPlayer;
    boolean playrequest = false;
    Switch switch1;
    MDatabase db;
    Sure currentSure;
    Sures[] sures;
    String[] sure_name;
    Author[] authors;
    String[] author_name;
    SuresDb currentDb;
    boolean authors_changed=false;
   public int YAZAR_ID=13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        text = findViewById(R.id.text);
        switch1 = findViewById(R.id.otooynat);
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        yazar=findViewById(R.id.yazar);
        Log.d("deneme", "first");


        sure_name = new String[114];
        sures = new Sures[114];
        authors=new Author[22];
        author_name=new String[22];

        play.setOnClickListener(playButtonListener);
        stop.setOnClickListener(stopButtonListener);
        Log.d("deneme", (sure_name != null) + " ");

        GetSurahList asyncTask = new GetSurahList();
        asyncTask.execute(1 + "");

        GetAuthors asyncTask2 = new GetAuthors();
        asyncTask2.execute(1 + "");

        yazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Çevirmen");


                b.setItems(author_name, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        authors_changed=true;
                        YAZAR_ID=authors[which].id;
                    }

                });

                b.show();
            }
        });


    }


    /* View.OnClickListener araButonListener = new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             text.setText("");
             GetSurah asyncTask = new GetSurah();
             asyncTask.execute(sure_no.getText().toString());
         }
     };
 */
    View.OnClickListener playButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            playrequest = true;
            mediaPlayer.start();
            if (mediaPlayer.isPlaying())
                playrequest = false;

        }
    };

    View.OnClickListener stopButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mediaPlayer.pause();
        }
    };

    void play_mp3(final String uri) {
        new Runnable() {

            @Override
            public void run() {
                Uri myUri = Uri.parse(uri);

                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();

                    mediaPlayer = null;

                }
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(context, myUri);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {

                            mediaPlayer.start();

                            if (!switch1.isChecked() && !playrequest) {
                                mediaPlayer.pause();
                            }
                            playrequest = false;
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();

    }


    AdapterView.OnItemSelectedListener surahSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            text.setText("");
            GetSurah asyncTask = new GetSurah();
            asyncTask.execute(i + 1 + "");
            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    void spinnerSet() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                        (context, android.R.layout.simple_spinner_item, sure_name);

                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);

                spinner1.setAdapter(dataAdapter);

                spinner1.setOnItemSelectedListener(surahSelectListener);

            }
        });

    }

    class GetSurah extends AsyncTask<String, Integer, List<VerseDb>> {

        @Override
        protected List<VerseDb> doInBackground(String... strings) {
             //FIXME get ayets e bir adet yazar parametresi eklenecek. geçiçi çözümle çalışıyor
            if (MDatabase.getInstance(context).SuresDao().getAyets(Integer.parseInt(strings[0])).size() == 0 || authors_changed) {

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String url = "https://api.acikkuran.com/surah/" + strings[0] + "?author="+YAZAR_ID;

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
                                        text.append(i + ": " + sure.verses.get(i).translation.text + "\n \n");
                                        //FIXME geçici olarak kaldırıyorum. veri tabanını düzeltince açılacak
                                     /*   MDatabase.getInstance(context).SuresDao().insert(
                                                new VerseDb(sure.verses.get(i).id,
                                                        sure.verses.get(i).surah_id,
                                                        sure.verses.get(i).verse_number,
                                                        sure.verses.get(i).page,
                                                        sure.verses.get(i).juzNumber,
                                                        sure.verses.get(i).verse,
                                                        sure.verses.get(i).transcription,
                                                        sure.verses.get(i).translation.text));*/
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

            } else {
                List<VerseDb> list = MDatabase.getInstance(context).SuresDao().getAyets(Integer.parseInt(strings[0]));
                currentDb = MDatabase.getInstance(context).SuresDao().getSurah(Integer.parseInt(strings[0]));


                return list;
            }

            return null;
        }


        @Override
        protected void onPostExecute(List<VerseDb> list) {
            if (list != null) {

                for (int i = 0; i < list.size(); i++) {
                    text.append((i + 1) + ": " + list.get(i).text + "\n \n");


                }
                play_mp3(currentDb.audioUrl);
            }
        }

    }

    class GetSurahList extends AsyncTask<String, Integer, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            getSurahList();
            return null;
        }


        void getSurahList() {

            if (MDatabase.getInstance(context).SuresDao().getSuresDb().size() == 0) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String url = "https://api.acikkuran.com/surahs";


                JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null,

                        new Response.Listener<JSONObject>() {

                            // Takes the response from the JSON request
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray obj = response.getJSONArray("data");


                                    for (int i = 0; i < obj.length(); i++) {

                                        JSONObject jb1 = obj.getJSONObject(i);
                                        sures[i] = new Gson().fromJson(String.valueOf(jb1), Sures.class);

                                        MDatabase.getInstance(getApplicationContext()).SuresDao().insert(new SuresDb(sures[i].id, sures[i].verse_count, sures[i].pageNumber, sures[i].name, sures[i].slug, sures[i].audio.mp3));

                                    }


                                    for (int i = 0; i < 114; i++) {
                                        sure_name[i] = (i + 1) + ". " + sures[i].name;
                                        Log.d("deneme", sures[i].name);
                                    }

                                    spinnerSet();


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
            } else {

                List<SuresDb> suresdb = MDatabase.getInstance(context).SuresDao().getSuresDb();

                for (int i = 0; i < suresdb.size(); i++) {
                    sures[i] = new Sures(suresdb.get(i).id, suresdb.get(i).name);
                    sure_name[i] = (i + 1) + ". " + sures[i].name;
                }

                spinnerSet();

            }
        }
    }


    class GetAuthors extends AsyncTask<String, Integer, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            getAuthorList();
            return null;
        }


        void getAuthorList() {


                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String url = "https://api.acikkuran.com/authors";


                JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null,

                        new Response.Listener<JSONObject>() {

                            // Takes the response from the JSON request
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray obj = response.getJSONArray("data");


                                    for (int i = 0; i < obj.length(); i++) {

                                        JSONObject jb1 = obj.getJSONObject(i);
                                        authors[i] = new Gson().fromJson(String.valueOf(jb1),Author.class);

                                        author_name[i]=authors[i].name;
                                    }




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
    }
}