package com.prodev.booz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<String>();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Loginpage.class));
            return;
        }
        

        Toast.makeText(this, "Welcome " + SharedPrefManager.getInstance(this).getUsername(), Toast.LENGTH_LONG).show();

        volley();

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String currentDateandTime = sdf.format(new Date());
        //Toast.makeText(this, ""+Integer.parseInt(currentDateandTime), Toast.LENGTH_LONG).show();

        if(Integer.parseInt(currentDateandTime)>=13&&Integer.parseInt(currentDateandTime)<16){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The booking for lunch is now over, you can book for dinner after 4PM!");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "About Us",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           Intent i = new Intent(MainActivity.this,helpDesk.class);
                           startActivity(i);

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
        else if(Integer.parseInt(currentDateandTime)>=20&&Integer.parseInt(currentDateandTime)<=23){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The booking for Dinner is now over, you can book for Lunch tomorrow after 9AM!");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "About Us",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(MainActivity.this,helpDesk.class);
                            startActivity(i);

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        else if(Integer.parseInt(currentDateandTime)>=0&&Integer.parseInt(currentDateandTime)<9){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The booking for yesterday's Dinner is now over, you can book for Lunch after 9AM!");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "About Us",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(MainActivity.this,helpDesk.class);
                            startActivity(i);

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else{

        }




    }



//---------------------------------------------------------------------------------------
public void volley(){
    viewPager = (ViewPager) findViewById(R.id.viewpager);

    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


    //adapter.addFragment(new MyFragment(var), var, argsBundle); //while adding fragment.

    adapter.addFragment(new FragOne(), "COOKHOUSE");
    //adapter.addFragment(new FragTwo(), "RESTAURANTS");
    //adapter.addFragment(new FragThree(), "SWEETS/BEVERAGES");



    viewPager.setAdapter(adapter);

    tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);

    /*String url = "http://prabhat.coolpage.biz/Booz/items/get_cat.php";


    RequestQueue rq = Volley.newRequestQueue(this);
    JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET,url,null,
            new Response.Listener<JSONArray> (){

                @Override
                public void onResponse(JSONArray response) {

                    try{
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            // Get current json object
                            list.add(response.getJSONObject(i).getString("category"));

                        }
                        //Toast.makeText(MainActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();

                        viewPager = (ViewPager) findViewById(R.id.viewpager);

                        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


                        //adapter.addFragment(new MyFragment(var), var, argsBundle); //while adding fragment.

                        adapter.addFragment(new FragOne(), "Specials");
                        adapter.addFragment(new FragTwo(), "RESTAURANTS");
                        adapter.addFragment(new FragThree(), "SNACKS");



                        viewPager.setAdapter(adapter);

                        tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            },new Response.ErrorListener(){

        @Override
        public void onErrorResponse(VolleyError error){
            //Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
        }

    });

    rq.add(jar);*/

}


//---------------------------------------------

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

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
        }
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();





        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String currentDateandTime = sdf.format(new Date());
        //Toast.makeText(this, ""+Integer.parseInt(currentDateandTime), Toast.LENGTH_LONG).show();

        if(Integer.parseInt(currentDateandTime)>=13&&Integer.parseInt(currentDateandTime)<16){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The booking for lunch is now over, you can book for dinner after 4PM!");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "About Us",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(MainActivity.this,helpDesk.class);
                            startActivity(i);

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
        else if(Integer.parseInt(currentDateandTime)>=20&&Integer.parseInt(currentDateandTime)<=23){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The booking for Dinner is now over, you can book for Lunch tomorrow after 9AM!");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "About Us",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(MainActivity.this,helpDesk.class);
                            startActivity(i);

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        else if(Integer.parseInt(currentDateandTime)>=0&&Integer.parseInt(currentDateandTime)<9){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The booking for yesterday's Dinner is now over, you can book for Lunch after 9AM!");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "About Us",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(MainActivity.this,helpDesk.class);
                            startActivity(i);

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else{

        }





    }
}

