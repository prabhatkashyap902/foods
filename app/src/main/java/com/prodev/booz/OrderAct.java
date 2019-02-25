package com.prodev.booz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;


import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class OrderAct extends AppCompatActivity {
    ArrayList<list_item> list = new ArrayList<list_item>();
    ListView lv;
    int tot = 0;
    Button btn;
    TextView totaltx;
    list_item_adapter itemsAdapter;
   // public static final String ACCOUNT_SID = "AC3ff852b1fbc449f278cb283d67552bbc";
    //public static final String AUTH_TOKEN = "aca21885c205ffac3d7dbc1c0a102f81";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        lv=(ListView)findViewById(R.id.order_lv);
        totaltx=(TextView)findViewById(R.id.total);
        btn =(Button)findViewById(R.id.paybtn);


       // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalorder();


        lv.invalidateViews();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnonclick();

            }
        });




    }



    public void btnonclick(){

        String[] s = { "Select One...","IIIT","IMI","BGU" };
        final String[] ref = {"fb2019-sum1","fb2019-sum2","fb2019-sum3",
                "fb2019-iiit1","fb2019-iiit2","fb2019-iiit3",
                "fb2019-imi1","fb2019-imi2","fb2019-imi3",
                "fb2019-bgu1","fb2019-bgu2","fb2019-bgu3",
                "fb2019-cet1","fb2019-cet2","fb2019-cet3",
                "fb2019-soa1","fb2019-soa2","fb2019-soa3",
                "fb2019-iter1","fb2019-iter2","fb2019-iter3",
                "fb2019-amri1","fb2019-amri2","fb2019-amri3",
                "fb2019-giet1","fb2019-giet2","fb2019-giet3",
                "fb2019-admin"};
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(OrderAct.this,
                android.R.layout.simple_spinner_dropdown_item, s);


        final Spinner sp = new Spinner(OrderAct.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        sp.setAdapter(adp);

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAct.this);







        final EditText edittext = new EditText(getApplicationContext());



        edittext.setHint("Referral Code(optional)");

        LinearLayout lay = new LinearLayout(getApplicationContext());
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(sp);
        lay.addView(edittext);
        builder.setView(lay);



        builder.setTitle("Location");
        builder.setMessage("Are You Sure you want to Finalize these orders. It won't change further and It will be purely COD. Location to deliver: - ");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Done",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!sp.getSelectedItem().toString().equals("Select One...")){
                            int flag=0;
                            for(int i=0;i<ref.length;i++){
                                if(edittext.getText().toString().trim().toLowerCase().equals(ref[i].toLowerCase())||edittext.getText().toString().trim().equals("")){
                                    flag++; }
                            }
                            if(flag>0){
                                Toast.makeText(OrderAct.this, ""+sp.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//---------------------------------------------------------------------------------
                                String url="http://prabhat.coolpage.biz/Booz/items/bill.php?mobile="
                                        +SharedPrefManager.getInstance(getApplicationContext()).getUsername()+
                                        "&place="+sp.getSelectedItem().toString()+"&ref="+edittext.getText().toString().trim().toLowerCase() ;
                                RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
                                final StringRequest sr =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                                rq.add(sr);
//-----------------------------------------------------------------------------------------------
                                String url3="http://prabhat.coolpage.biz/Booz/items/confirm_order.php?mobile=" +
                                        SharedPrefManager.getInstance(getApplicationContext()).getUsername();



                                RequestQueue re3 = Volley.newRequestQueue(getApplicationContext());
                                final StringRequest sr3 =  new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        finish();










                                        Intent i = new Intent(getApplicationContext(), TotalAct.class);
                                        i.putExtra("bno",response);
                                        startActivity(i);

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                                re3.add(sr3);
//-----------------------------------------------------------------------------------------------------------------
                                dialog.cancel();

                            }
                            else{
                                Toast.makeText(OrderAct.this, "Wrong Referral Code, you can skip it also!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(OrderAct.this, "Please select your Delivery location", Toast.LENGTH_SHORT).show();
                        }




                    }
                });
        builder.setNegativeButton("Wait..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder.create();


        if(Integer.parseInt(totaltx.getText().toString())!=0)
        {alert11.show();}
        else{
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
        }
    }






    public void totalorder(){


        String url = "http://prabhat.coolpage.biz/Booz/items/get_temp.php?mobile="+SharedPrefManager.getInstance(this).getUsername();


        RequestQueue rq = Volley.newRequestQueue(this);

        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET,url,null,
                new com.android.volley.Response.Listener<JSONArray> (){

                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){

                                list.add(new list_item(response.getJSONObject(i).getString("name"),
                                        response.getJSONObject(i).getString("price"),
                                        response.getJSONObject(i).getString("qty"),
                                        response.getJSONObject(i).getString("id")));
                                tot = tot +  (Integer.parseInt(response.getJSONObject(i).getString("price"))*Integer.parseInt(response.getJSONObject(i).getString("qty")));

                            }
                            //Toast.makeText(MainActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();

                            itemsAdapter = new list_item_adapter(OrderAct.this, list);
                            lv.setAdapter(itemsAdapter);
                            if(list.size()==0){
                                Toast.makeText(OrderAct.this, "Your Cart is Empty!", Toast.LENGTH_LONG).show();
                            }
                            else{}

                            totaltx.setText( ""+tot);


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

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.aboutid:
                Intent k= new Intent(this,helpDesk.class);
                startActivity(k);
            break;


            case R.id.historyid:
                Intent j= new Intent(this,TotalAct.class);
                startActivity(j);

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



}

