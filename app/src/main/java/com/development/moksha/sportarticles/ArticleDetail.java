package com.development.moksha.sportarticles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticleDetail extends AppCompatActivity {

    RequestQueue queue;
    ArrayList<Article> articles;
    ArticleAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Intent intent = getIntent();

        articles = new ArrayList<>();
        ListView lvInfo = (ListView) findViewById(R.id.info);
        adapter = new ArticleAdapter(this,R.layout.article_item, articles);
        lvInfo.setAdapter(adapter);

        progressDialog = new ProgressDialog(this,R.style.Progress);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();



        queue = Volley.newRequestQueue(this);

        makeRequest("http://mikonatoruri.win/post.php?article=" + intent.getStringExtra("article"));

    }

    void handleResponse(JSONObject response){
        try{
            TextView team1 = (TextView)findViewById(R.id.team1); team1.setText(response.getString("team1"));
            TextView team2 = (TextView)findViewById(R.id.team2); team2.setText(response.getString("team2"));
            TextView time = (TextView)findViewById(R.id.time); time.setText(response.getString("time"));
            TextView place = (TextView)findViewById(R.id.place); place.setText(response.getString("place"));
            TextView tournament = (TextView)findViewById(R.id.tournament); tournament.setText(response.getString("tournament"));
            JSONArray arts = response.getJSONArray("article");
            articles.clear();
            for(int i=0; i< arts.length(); i++){
                JSONObject obj = arts.getJSONObject(i);
                Article art = new Article();
                art.header = obj.getString("header");
                art.text = obj.getString("text");
                articles.add(art);
            }
            Article prediction = new Article();
            prediction.text = response.getString("prediction");
            articles.add(prediction);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void makeRequest(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleResponse(response);
                progressDialog.hide();
            }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    MainActivity.parseRequestError(getApplicationContext(),error);
                }
            });
        queue.add(jsonObjectRequest);
    }
}
