package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.PojoNewVehImgs;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;

import java.util.ArrayList;

public class AdapterNewVehImgs extends RecyclerView.Adapter<AdapterNewVehImgs.RecyclerViewHolder> {

    ArrayList<PojoNewVehImgs> pojoNewVehImg;
    Context context;

    public AdapterNewVehImgs(ArrayList<PojoNewVehImgs> pojoNewVehImg, Context context) {
        this.pojoNewVehImg = pojoNewVehImg;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterNewVehImgs.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_new_veh_list,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNewVehImgs.RecyclerViewHolder holder, int position) {
        Glide.with(context).load(pojoNewVehImg.get(position).getVehicle_images()).placeholder(R.drawable.add_copy).into(holder.car_image_position);
    }

    @Override
    public int getItemCount() {
        return pojoNewVehImg.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView car_image_position;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image_position=itemView.findViewById(R.id.car_image_position);
        }
    }
}
