package com.example.designer2.horizentalrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String JASON_URL = "https://mufassirislam.com/apps/blog/api/category.php?action=getCategoryList";
    private List<Posts> firstPost;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstPost = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_id);
        jsonrequest();
    }
    public void jsonrequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JASON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray resultArray = object.getJSONArray("result");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject jsonObject = resultArray.getJSONObject(i);
                        Posts posts = new Posts();
                        posts.setName(jsonObject.getString("category_name"));
                        posts.setImgUrl(jsonObject.getString("thumb"));

                        firstPost.add(posts);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setuprecyclerview(firstPost);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });





        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
    private void setuprecyclerview(List<Posts> firstPost){

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,firstPost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(myadapter);
    }
}



