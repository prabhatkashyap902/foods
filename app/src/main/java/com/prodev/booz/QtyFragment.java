package com.prodev.booz;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;


/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class QtyFragment extends DialogFragment {

    EditText et;
    Button btn;

    public QtyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_qty, container, false);



        et = (EditText) v.findViewById(R.id.et_qty);
        btn = (Button)v.findViewById(R.id.btn_qty);

        et.setHint("Enter "+UserInfo.name+" Quantity");

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(et.getText().toString().trim().equals("")||Integer.parseInt(et.getText().toString().trim())>0){
                        String url  = "http://prabhat.coolpage.biz/Booz/items/add_temp.php?mobile="
                                + SharedPrefManager.getInstance(getActivity()).getUsername()+"&itemid="+UserInfo.itemId+"&itemname="+UserInfo.name+
                                "&qty="+et.getText().toString()+"&prices="+UserInfo.price+"&totals="+(UserInfo.price*Integer.parseInt(et.getText().toString()));


                        final RequestQueue rq = Volley.newRequestQueue(getActivity());
                        final StringRequest sr =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dismiss();
                                Toast.makeText(getActivity(), "Your Order has been moved to My Cart!", Toast.LENGTH_SHORT).show();
                               /* Intent i =  new Intent(getActivity(),OrderAct.class);
                                startActivity(i);*/
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        rq.add(sr);
                    }
                    else{
                        Toast.makeText(getActivity(), "Enter some valid value!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        return v;
    }
}
