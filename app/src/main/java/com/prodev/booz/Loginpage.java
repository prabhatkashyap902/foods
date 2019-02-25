package com.prodev.booz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Loginpage extends AppCompatActivity {

    private EditText editTextMobile, editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    private TextView registerttv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        editTextMobile = (EditText)findViewById(R.id.lomobileet);
        editTextPassword=(EditText)findViewById(R.id.lopasswordet);
        buttonLogin=(Button)findViewById(R.id.loLoginbtn);
        registerttv = (TextView)findViewById(R.id.registertv);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextPassword.getText().toString().trim().equals("") || editTextMobile.getText().toString().trim().equals(""))
                {
                    Toast.makeText(Loginpage.this, "oops! you forgot to enter!", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    userLogin();
                }
            }
        });

        registerttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Loginpage.this, Registerpage.class));
            }
        });

    }


    private void userLogin(){
        final String mobile = editTextMobile.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("mobile"),
                                                obj.getString("email")
                                        );
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), "Some Fault is there, Please restart the app!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("password", password);
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
