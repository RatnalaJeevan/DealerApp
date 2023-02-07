package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.pojos.pojos.PojoActVehlist;


import java.util.ArrayList;

public class AdapterActVehList extends RecyclerView.Adapter<AdapterActVehList.RecyclerViewHolder> {

    ArrayList<PojoActVehlist> pojoActVehlists;
    Context context;

    public AdapterActVehList( ArrayList<PojoActVehlist> pojoActVehlists, Context context) {
        this.pojoActVehlists = pojoActVehlists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterActVehList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_act_vehlist,parent,false);
       return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActVehList.RecyclerViewHolder holder, int position) {

        PojoActVehlist recyclerdata=pojoActVehlists.get(position);

        holder.payment_date.setText(Common.getDateMonth(recyclerdata.getActivation_date()));
        holder.veh_no.setText(recyclerdata.getVehicle_no());
        Glide.with(context).load(recyclerdata.getBrand_icon()).placeholder(R.drawable.add_copy).into(holder.brandlogo);

    }

    @Override
    public int getItemCount() {
        return pojoActVehlists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView brandlogo;
        TextView payment_date,veh_no;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            brandlogo=itemView.findViewById(R.id.brandlogo);
            payment_date=itemView.findViewById(R.id.payment_date);
            veh_no=itemView.findViewById(R.id.veh_no);
        }
    }
}
