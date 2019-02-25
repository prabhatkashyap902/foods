package com.prodev.booz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class DetailsforItem extends AppCompatActivity {
    String s ;
    TextView tx,tx1,tx2;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsfor_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout) ;
        im=(ImageView)findViewById(R.id.image_id);

        tx = (TextView)findViewById(R.id.descrp);
        tx1 = (TextView)findViewById(R.id.category);
        tx2 = (TextView)findViewById(R.id.price);
        s= getIntent().getExtras().getString("name");



        volleyf();


    }


    public void volleyf(){
        String url="http://prabhat.coolpage.biz/Booz/items/get_description.php?name="+s;

        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
        final RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray> (){

                    @Override
                    public void onResponse(JSONArray response) {



                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                final JSONObject jsonObject = response.getJSONObject(i);
                                // Get current json object

                                tx.setText("Description: "+jsonObject.getString("description"));
                                tx1.setText("Category: "+jsonObject.getString("category"));
                                tx2.setText("Price: "+jsonObject.getString("price"));
                                collapsingToolbarLayout.setTitle(jsonObject.getString("name"));
                                String web = "http://prabhat.coolpage.biz/Booz/items/images/"+jsonObject.getString("photo");
                                web = web.replace(" ","%20");
                                Picasso.get().load(web).into(im);



                                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                                fab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            UserInfo.itemId = Integer.parseInt(jsonObject.getString("id"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        QtyFragment obj = new QtyFragment();
                                        obj.show(((Activity)view.getContext()).getFragmentManager(),"Qty");

                                    }
                                });


                            }



                        }
                        catch (Exception e){}
                    }

                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                //Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
            }

        });


        rq.add(jar);


    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, Loginpage.class));
                break;
            case R.id.exitid:
                finishAffinity ();
                break;
            case R.id.orderpage:
                Intent i=new Intent(this,OrderAct.class);
                startActivity(i);
                break;
            case R.id.orderhistory:
                Intent j=new Intent(this,TotalAct.class);
                startActivity(j);
                break;
            case R.id.aboutid:
                Intent k=new Intent(this,helpDesk.class);
                startActivity(k);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

}
