package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.AddNewCar;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCarImageList;

import java.util.ArrayList;

public class AdapterCarImageList extends RecyclerView.Adapter<AdapterCarImageList.RecyclerViewHolder> {
    ArrayList<PojoCarImageList> carImageLists;
    Context context;
    public int adapter_position=0;
    public AdapterCarImageList(ArrayList<PojoCarImageList> carImageLists, Context context) {
        this.carImageLists = carImageLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCarImageList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_imagelist, parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarImageList.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.car_image_position_name.setText(carImageLists.get(position).getName());
        if (carImageLists.get(position).getImage() == null)
        {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
            holder.car_image_position.setImageResource(R.drawable.add_car_icon);
        } else {
            holder.car_image_position.setImageURI(carImageLists.get(position).getImage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewCar.getInstance().selectedObject=position;
                adapter_position=position;
                if (carImageLists.get(position).getImage() == null)
                {
                    AddNewCar.getInstance().finalids.add(carImageLists.get(position).getId());
                   AddNewCar.getInstance().rl_show_popup.setVisibility(View.VISIBLE);
                   AddNewCar.getInstance().img1.setImageResource(R.drawable.add_car_icon);

                } else{
                    AddNewCar.getInstance().img1.setImageURI(carImageLists.get(position).getImage());
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
