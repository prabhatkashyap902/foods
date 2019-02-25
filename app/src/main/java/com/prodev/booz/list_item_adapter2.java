package com.prodev.booz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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


public class list_item_adapter2 extends ArrayAdapter<list_item2> {
    private Context mContext;
    private List<list_item2> moviesList = new ArrayList<>();


    public list_item_adapter2(@NonNull Context context, ArrayList<list_item2> list) {
        super(context,0,list);
        mContext = context;
        moviesList = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listviewrow2,parent,false);

        final list_item2 items  = moviesList.get(position);

        TextView bill_no = (TextView)listItem.findViewById(R.id.order_list);
        bill_no.setText("Order No: "+ items.getmBill_no());

        TextView name = (TextView) listItem.findViewById(R.id.name_list);
        name.setText("Name: "+items.getmName());

        TextView price = (TextView) listItem.findViewById(R.id.price_list);
        price.setText("Price: "+items.getmPrice());

        TextView qty = (TextView) listItem.findViewById(R.id.qty_list);
        qty.setText("Quantity:"+items.getmQty());

        TextView total = (TextView)listItem.findViewById(R.id.totalperid);
        total.setText("Total: "+Integer.parseInt(items.getmPrice())*Integer.parseInt(items.getmQty()));





        notifyDataSetChanged();


        return listItem;
    }


}
