package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarBrands;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;

import java.util.ArrayList;

public class AdapterBrandLogos extends RecyclerView.Adapter<AdapterBrandLogos.RecyclerviewHolder> {

    ArrayList<PojoAllCarBrands> pojoAllCarBrands;
    Context context;

    public AdapterBrandLogos(ArrayList<PojoAllCarBrands> pojoAllCarBrands, Context context) {
        this.pojoAllCarBrands = pojoAllCarBrands;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterBrandLogos.RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_logo,parent,false);
        return new RecyclerviewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBrandLogos.RecyclerviewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(pojoAllCarBrands.get(position).getBrand_icon()).placeholder(R.drawable.add_copy).into(holder.iv);

        if(pojoAllCarBrands.get(position).getIs_Selected().equals("n")){
            holder.iv.setBackground(null);
        }else{
            holder.iv.setBackground(context.getDrawable(R.drawable.blue_border));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(SPHelper.camefrom.equals("filter")){
                    if(pojoAllCarBrands.get(position).getIs_Selected().equals("n")){
                        pojoAllCarBrands.get(position).setIs_Selected("y");
                        SPHelper.pojoAllCarBrands.get(position).setIs_Selected("y");
                    }else{
                        pojoAllCarBrands.get(position).setIs_Selected("n");
                        SPHelper.pojoAllCarBrands.get(position).setIs_Selected("n");
                    }
                }else{
                    SPHelper.brand_name=pojoAllCarBrands.get(position).getCar_brand();
                    SPHelper.carbrandid=pojoAllCarBrands.get(position).getId();
                    SPHelper.carmodelid="";
                    for (int i=0;i<pojoAllCarBrands.size();i++)
                    {
                        if (i == position)
                        {
                            pojoAllCarBrands.get(i).setIs_Selected("y");

                        } else {
                            pojoAllCarBrands.get(i).setIs_Selected("n");
                        }
                    }
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoAllCarBrands.size();
    }

    public class RecyclerviewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
        }
    }
}
