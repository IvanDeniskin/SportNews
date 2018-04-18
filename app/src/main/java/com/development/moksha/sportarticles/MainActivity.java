package com.development.moksha.sportarticles;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> items;
    RequestQueue queue;
    ItemAdapter adaptor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();

        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        // создаем адаптер
        adaptor = new ItemAdapter(this,R.layout.list_item, items);

        // присваиваем адаптер списку
        lvMain.setAdapter(adaptor);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("111", "itemClick: position = " + position + ", id = "  + id);
                Intent intent = new Intent(getApplicationContext(), ArticleDetail.class);
                intent.putExtra("article", items.get(position).article);
                startActivity(intent);
            }
        });

        queue = Volley.newRequestQueue(this);

        initButtons();

        progressDialog = new ProgressDialog(this,R.style.Progress);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        makeRequest("football");

    }


    void makeRequest(String category){
        progressDialog.show();
        String url = "http://mikonatoruri.win/list.php?category=" + category;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    parseJSON(response);
                    progressDialog.hide();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    parseRequestError(getApplicationContext(),error);
                    progressDialog.hide();
                }
            });
        queue.add(jsonObjectRequest);

    }

    void parseJSON(JSONObject obj){
        items.clear();
        try {
            JSONArray events = obj.getJSONArray("events");
            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);

                Item item = new Item();
                item.title = event.getString("title");
                item.place = event.getString("place");
                item.koef = event.getString("coefficient");
                item.time = event.getString("time");
                item.article = event.getString("article");
                item.preview = event.getString("preview");
                items.add(item);
            }
            adaptor.notifyDataSetChanged();
            ListView lvMain = (ListView) findViewById(R.id.lvMain);
            lvMain.smoothScrollToPosition(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void initButtons(){
        Button football = (Button)findViewById(R.id.btnFootball);
        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest("football");
            }
        });

        Button hockey = (Button)findViewById(R.id.btnHockey);
        hockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest("hockey");
            }
        });

        Button volleyball = (Button)findViewById(R.id.btnVolleyball);
        volleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest("volleyball");
            }
        });

        Button basketball = (Button)findViewById(R.id.btnBasketball);
        basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest("basketball");
            }
        });

        Button tennis = (Button)findViewById(R.id.btnTennis);
        tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest("tennis");
            }
        });

        Button cybersport = (Button)findViewById(R.id.btnCybersport);
        cybersport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest("cybersport");
            }
        });
    }

    // Server errors handling
    static void parseRequestError(Context context, VolleyError error){
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(context,context.getString(R.string.error_timeout), Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(context,context.getString(R.string.error_auth), Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(context,context.getString(R.string.error_server), Toast.LENGTH_LONG).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(context,context.getString(R.string.error_network), Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(context,context.getString(R.string.error_parse), Toast.LENGTH_LONG).show();
        } else{
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null) {
                Log.e("Status code", String.valueOf(networkResponse.statusCode));
                Toast.makeText(context,R.string.error + " " + String.valueOf(networkResponse.statusCode), Toast.LENGTH_LONG).show();
            }
        }
    }
}
