package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarModels;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;

import java.util.ArrayList;

public class AdapterModel extends RecyclerView.Adapter<AdapterModel.RecyclerViewHolder> {
   ArrayList<PojoAllCarModels> carModels;
   Context context;

    public AdapterModel(ArrayList<PojoAllCarModels> carModels, Context context) {
        this.carModels = carModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterModel.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_models,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterModel.RecyclerViewHolder holder, int position) {

        holder.car_model.setText(carModels.get(position).getCar_model());
        if(carModels.get(position).getIsSelected().equals("n")){
            holder.car_model.setTextColor(context.getColor(R.color.lightgrey));
            holder.rl_insp.setBackground(context.getDrawable(R.drawable.map_border));
        }else{
            holder.car_model.setTextColor(context.getColor(R.color.black));
            holder.rl_insp.setBackground(context.getDrawable(R.drawable.blue_border));
        }
        holder.rl_insp.setOnClickListener(view -> {
            SPHelper.carmodelid=carModels.get(position).getModel_id();
            SPHelper.model_name=carModels.get(position).getCar_model();
            for (int i=0;i<carModels.size();i++)
            {
                if (i == position)
                {
                    carModels.get(i).setIsSelected("y");

                } else {
                    carModels.get(i).setIsSelected("n");
                }
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView car_model;
        RelativeLayout rl_insp;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            car_model=itemView.findViewById(R.id.car_model);
            rl_insp=itemView.findViewById(R.id.rl_car_model);
        }
    }
}
