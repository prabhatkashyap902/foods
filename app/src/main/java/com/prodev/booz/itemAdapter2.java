package com.prodev.booz;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class itemAdapter2 extends RecyclerView.Adapter<itemAdapter2.Itemholder>{
    Context context;
    ArrayList<item> list = new ArrayList<item>();




    class Itemholder extends RecyclerView.ViewHolder {
        ImageView itemphoto;
        TextView itemname;

        public Itemholder(@NonNull View itemView) {
            super(itemView);
            itemphoto = (ImageView) itemView.findViewById(R.id.item_photo);
            itemname = (TextView) itemView.findViewById(R.id.item_name);


        }

        public void bind(final String n, final int item_id) {
            itemname.setText(n);
           // Glide.with(context).load(Integer.valueOf(item_id)).into(itemphoto);
//            Picasso.get().load(item_id).into(itemphoto);


           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Intent i=new Intent(context,Frag2_list.class);
                   i.putExtra("name",n);
                   context.startActivity(i);
               }
           });


        }
    }
    public itemAdapter2(Context context, ArrayList<item> list){
        this.context=context;
        this.list=list;
    }



    @NonNull
    @Override
    public Itemholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row2, viewGroup, false);

        return new Itemholder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull Itemholder viewHolder, int pos) {
        viewHolder.bind(list.get(pos).name,list.get(pos).id);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }



}