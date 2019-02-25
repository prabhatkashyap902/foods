package com.prodev.booz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


public class list_item_adapter extends ArrayAdapter<list_item>  {
    private Context mContext;
    private List<list_item> moviesList = new ArrayList<>();
    ImageView cancel;
    public List<list_item> moviesList2 = new ArrayList<>();
    public list_item_adapter adp;

    public list_item_adapter(@NonNull Context context,ArrayList<list_item> list) {
        super(context,0,list);
        mContext = context;
        moviesList = list;
        this.adp=this;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listviewrow,parent,false);
cancel = (ImageView)listItem.findViewById(R.id.cancelsingleid);
        final list_item items  = moviesList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name_list);
        name.setText("Name: "+items.getmName());

        TextView price = (TextView) listItem.findViewById(R.id.price_list);
        price.setText("Price: "+items.getmPrice());

        TextView qty = (TextView) listItem.findViewById(R.id.qty_list);
        qty.setText("Quantity:"+items.getmQty());

        TextView total = (TextView)listItem.findViewById(R.id.totalperid);
        total.setText("Total: "+Integer.parseInt(items.getmPrice())*Integer.parseInt(items.getmQty()));
        moviesList2=moviesList;

    cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Warning");
        builder1.setMessage("Are You Sure you want to Cancel this order?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String url2="http://prabhat.coolpage.biz/Booz/items/cancel_order.php?mobile=" +
                                SharedPrefManager.getInstance(getContext()).getUsername()+"&itemid="+items.getmId();


                        RequestQueue re2 = Volley.newRequestQueue(getContext());
                        final StringRequest sr2 =  new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(mContext, "Your "+items.getmName()+" has been removed from cart!", Toast.LENGTH_SHORT).show();
                                moviesList.remove(items);
                                adp.notifyDataSetChanged();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Toast.makeText(con, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        re2.add(sr2);

                        list_item_adapter.this.notifyDataSetChanged();

                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
});
        notifyDataSetChanged();


        return listItem;
    }

    public class ArrayAdapter<list_item> extends BaseAdapter
    {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
