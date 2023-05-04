package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.pojo_static_image_data;

import java.util.ArrayList;

public class Adapter_static_images extends RecyclerView.Adapter<Adapter_static_images.MyViewHolder> {
    Context context;
    View view;
    ArrayList<pojo_static_image_data>pojo_static_image_dataArrayList;

    public Adapter_static_images (Context context, ArrayList<pojo_static_image_data>pojo_static_image_dataArrayList) {
        this.context = context;
        this.pojo_static_image_dataArrayList =pojo_static_image_dataArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_image_upload,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        pojo_static_image_data list =pojo_static_image_dataArrayList.get(position);
        holder.car_image_position_name.setText(list.getCar_image_position_name());
        holder.car_image_position.setImageResource(list.getCar_image_position());

    }

    @Override
    public int getItemCount() {
        return pojo_static_image_dataArrayList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView car_image_position;
        TextView car_image_position_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image_position = itemView.findViewById(R.id.car_image_position);
            car_image_position_name= itemView.findViewById(R.id.car_image_position_name);
        }



    }
}

