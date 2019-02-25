package com.prodev.booz;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frag2_list extends AppCompatActivity {
    public RecyclerView recyclerView;
    final ArrayList<item> list=new ArrayList<item>();
    public itemAdapter madp;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag2_list);

        recyclerView  =(RecyclerView)findViewById(R.id.recycle_frg4);
        s=getIntent().getExtras().getString("name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(s);

       volleyf();
    }
    public void volleyf(){
        s=s.replace(" ","%20");
        String url="http://prabhat.coolpage.biz/Booz/items/get_rest_name.php?rest_name="+s;

        final RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray> (){

                    @Override
                    public void onResponse(JSONArray response) {



                        try {list.clear();
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Get current json object


                                list.add(new item(jsonObject.getInt("id"),jsonObject.getString("name"),
                                        jsonObject.getInt("price"),jsonObject.getString("photo")));


                                madp =  new itemAdapter(getApplicationContext(),list);


                            }
                            for(int i=0;i<1;i++){
                                madp =  new itemAdapter(Frag2_list.this,list);
                                //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                                //recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(madp);
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



        //Toast.makeText(MainActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
        madp =  new itemAdapter(Frag2_list.this,list);
        recyclerView.setLayoutManager(new GridLayoutManager(Frag2_list.this, 1));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(madp);


        rq.add(jar);


    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % this.spanCount;
            if (this.includeEdge) {
                if (position < this.spanCount) {
                    outRect.top = this.spacing;
                }
                outRect.bottom = this.spacing;
            } else if (position >= this.spanCount) {
                outRect.top = this.spacing;
            }
        }
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
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
