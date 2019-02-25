package com.prodev.booz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TotalAct extends AppCompatActivity {
    TextView tv;
    ArrayList<list_item2> list = new ArrayList<list_item2>();
    ListView lv;
    list_item_adapter2 itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        lv=(ListView)findViewById(R.id.total_lv);



/*
        String url="http://prabhat.coolpage.biz/Booz/items/get_total.php?bill_no=" + getIntent().getStringExtra("bno");
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        final StringRequest sr =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        rq.add(sr);
*/ getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalorder();

    }


    public void totalorder(){


        String url = "http://prabhat.coolpage.biz/Booz/items/history.php?mobile="+SharedPrefManager.getInstance(this).getUsername();


        RequestQueue rq = Volley.newRequestQueue(this);

        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET,url,null,
                new com.android.volley.Response.Listener<JSONArray> (){

                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){

                                list.add(new list_item2(response.getJSONObject(i).getString("bill_no"),response.getJSONObject(i).getString("itemname"),
                                        response.getJSONObject(i).getString("prices"),
                                        response.getJSONObject(i).getString("qty"),
                                        response.getJSONObject(i).getString("itemid")));
                              //  tot = tot +  (Integer.parseInt(response.getJSONObject(i).getString("price"))*Integer.parseInt(response.getJSONObject(i).getString("qty")));

                            }
                            //Toast.makeText(MainActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
                            itemsAdapter = new list_item_adapter2(TotalAct.this, list);
                            lv.setAdapter(itemsAdapter);

                            //totaltx.setText( ""+tot);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                },new com.android.volley.Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                //Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
            }

        });

        rq.add(jar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu4, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.homeid:
                finish();
                Intent k= new Intent(this,MainActivity.class);
                startActivity(k);
                break;

            case R.id.aboutid:
                Intent i= new Intent(this,helpDesk.class);
                startActivity(i);
                break;


            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, Loginpage.class));
                break;

            case R.id.exitid:
                finishAffinity ();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return true;
    }



    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
