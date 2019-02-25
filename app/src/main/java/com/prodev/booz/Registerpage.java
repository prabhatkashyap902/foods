package com.prodev.booz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registerpage extends AppCompatActivity {


    Button register, login;
    EditText name, email, pass, mob;
    ProgressDialog progressDialog;
    String a = "@gmail.com";
    String b ="@yahoo.com";
    String c = "@yahoo.co.in";
    String d = "@rediffmail.com";
    String e = "@outlook.com";
    String f = "@hotmail.com";
    String g = "@iiit-bh.ac.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);



        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }




        name = (EditText) findViewById(R.id.lonameet);
        email = (EditText) findViewById(R.id.emailet);
        pass = (EditText) findViewById(R.id.passwordet);
        mob = (EditText)findViewById(R.id.mobileet);
        register = (Button) findViewById(R.id.registerbtn);
        login = (Button) findViewById(R.id.Loginbtn);
        progressDialog = new ProgressDialog(this);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equals("")|| email.getText().toString().equals("")|| pass.getText().toString().equals("")
                        || mob.getText().toString().equals("")){
                    Toast.makeText(Registerpage.this, "Oops! you forgot to enter the details!", Toast.LENGTH_SHORT).show();
                }
                else{


                    String k  =email.getText().toString().trim().toLowerCase();
                    if(k.contains(a)||k.contains(b)||k.contains(c)||k.contains(d)||k.contains(e)||k.contains(f)||k.contains(g)){


                        if(mob.getText().toString().trim().length()==10){

                            registerUser();
                        }
                        else{
                            Toast.makeText(Registerpage.this, "Enter a valid Mobile number", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(Registerpage.this, "Enter a valid email address!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Registerpage.this, Loginpage.class));
                return;
            }
        });



    }




    public void registerUser() {
        final String emails = email.getText().toString().trim();
        final String username = name.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String mobile = mob.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                           // Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Registerpage.this,MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), " Connect to Internet!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", emails);
                params.put("password", password);
                params.put("mobile",mobile);
                return params;
            }
        };

RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.exitid:
                finishAffinity ();
            case R.id.aboutid:
                Intent i = new Intent(this,helpDesk.class);
                startActivity(i);
        }
        return true;
    }

}