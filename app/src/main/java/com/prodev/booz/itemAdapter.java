package com.prodev.booz;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.Itemholder>{
    Context context;
    ArrayList<item> list = new ArrayList<item>();




    class Itemholder extends RecyclerView.ViewHolder {
        ImageView itemphoto,additempic;
        TextView itemname, itemprice,rest_name;

        public Itemholder(@NonNull View itemView) {
            super(itemView);
            itemphoto = (ImageView) itemView.findViewById(R.id.item_photo);
            itemprice = (TextView) itemView.findViewById(R.id.item_price);
            itemname = (TextView) itemView.findViewById(R.id.item_name);
            additempic=(ImageView)itemView.findViewById(R.id.item_add_photo);

        }

        public void bind(final String n, final int p, String u, final int item_id) {
            itemname.setText(n);
            itemprice.setText("Rs. "+p);

            String web = "http://prabhat.coolpage.biz/Booz/items/images/"+u;
            web = web.replace(" ","%20");
            Picasso.get().load(web).into(itemphoto);
            //var web:String = "http://192.168.8.100/SalesWeb/images/" + u
            //web = web.replace(" ", "%20")
            //Picasso.with(itemView.context).load(web).into(itemView.item_photo)

           additempic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UserInfo.itemId = item_id;
                    UserInfo.name=n;
                    UserInfo.price=p;
                    QtyFragment obj = new QtyFragment();
                    obj.show(((Activity)itemView.getContext()).getFragmentManager(),"Qty");

                   /* var obj = QtyFragment()
                    var manager = (itemView.context as Activity).fragmentManager
                    obj.show(manager, "Qty")*/

                }
            });
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   UserInfo.itemId = item_id;
                   UserInfo.name=n;
                   UserInfo.price=p;
                   Intent i=new Intent(context,DetailsforItem.class);
                   i.putExtra("name",n);
                   context.startActivity(i);
               }
           });


        }
    }
    public itemAdapter(Context context, ArrayList<item> list){
        this.context=context;
        this.list=list;
    }



    @NonNull
    @Override
    public Itemholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);

        return new Itemholder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull Itemholder viewHolder, int pos) {
        viewHolder.bind(list.get(pos).name, list.get(pos).price,list.get(pos).photo,list.get(pos).id);

    }

    @Override
    public int getItemCount() {


        return list.size();
    }



}