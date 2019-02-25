package com.prodev.booz;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragThree extends Fragment{
    public RecyclerView recyclerView;
     ArrayList<item> list=new ArrayList<item>();
    public itemAdapter madp;

    SwipeRefreshLayout mSwipeRefreshLayout;


    public FragThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_frg3);



        volleyf();
    }



    public void volleyf(){
        String url="http://prabhat.coolpage.biz/Booz/items/get_items.php?category=sweets";

        final RequestQueue rq = Volley.newRequestQueue(getContext());
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


                                madp =  new itemAdapter(getContext(),list);


                            }
                            for(int i=0;i<1;i++){
                                madp =  new itemAdapter(getContext(),list);
                               // recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
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
        madp =  new itemAdapter(getContext(),list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
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
}