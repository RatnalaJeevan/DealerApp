package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.CheckEligibility;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.RequestVehInspection;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.PojoSearchResults;

import java.util.ArrayList;

public class AdapterSearchResults extends RecyclerView.Adapter<AdapterSearchResults.RecyclerViewHolder> {

    ArrayList<PojoSearchResults> searchResults;
    Context context;
    public  String vehnno="";
    public AdapterSearchResults(ArrayList<PojoSearchResults> searchResults, Context context) {
        this.searchResults = searchResults;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSearchResults.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_search_results,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchResults.RecyclerViewHolder holder, int position) {
        PojoSearchResults data=searchResults.get(position);
        holder.veh_no.setText(data.getVehicle_no());
        Glide.with(context).load(data.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(holder.car_brand);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.veh_sel="y";
                vehnno=data.getVehicle_no();
                if(SPHelper.it_is.equals("req"))
                {
                    ((RequestVehInspection)context).selected_vehno.setText(vehnno);
                }else {
                    ((CheckEligibility)context).entered_vehno.setText(vehnno);
                    ((CheckEligibility)context).hideKeybaord();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView veh_no;
        ImageView car_brand;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            car_brand=itemView.findViewById(R.id.car_brand);
            veh_no=itemView.findViewById(R.id.veh_no);
        }
    }
}
