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

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.AddNewCar;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCarImageList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;

import java.util.ArrayList;

public class AdapterEditedVehImgList extends RecyclerView.Adapter<AdapterEditedVehImgList.RecyclerViewHolder> {

    ArrayList<PojoVehicleImageList> carImageLists;
    Context context;

    public AdapterEditedVehImgList(ArrayList<PojoVehicleImageList> carImageLists, Context context) {
        this.carImageLists = carImageLists;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterEditedVehImgList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_imagelist, null);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEditedVehImgList.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.car_image_position_name.setText(carImageLists.get(position).getImage_name());
        if (carImageLists.get(position).getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            holder.car_image_position.setImageURI(carImageLists.get(position).getImage());
        }
        if (carImageLists.get(position).getVehicle_images() != null && !carImageLists.get(position).getVehicle_images().isEmpty() && !carImageLists.get(position).getVehicle_images().equals("null")) {
            Glide.with(context).load(carImageLists.get(position).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.car_image_position);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewCar.getInstance().selectedObject=position;
                if (carImageLists.get(position).getImage() == null)
                {
                    AddNewCar.getInstance().finalids.add(carImageLists.get(position).getId());
                    AddNewCar.getInstance().rl_show_popup.setVisibility(View.VISIBLE);

                } else{
                    AddNewCar.getInstance().rl_show_popup.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carImageLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView car_image_position;
        TextView car_image_position_name;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image_position = itemView.findViewById(R.id.car_image_position);
            car_image_position_name = itemView.findViewById(R.id.car_image_position_name);
        }
    }
}
