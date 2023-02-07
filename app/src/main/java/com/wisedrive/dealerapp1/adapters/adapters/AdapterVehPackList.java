package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehPackList;

import java.util.ArrayList;

public class AdapterVehPackList extends RecyclerView.Adapter<AdapterVehPackList.RecyclerViewHolder> {

    ArrayList<PojoVehPackList> pojoVehPackLists;
    Context context;

    public AdapterVehPackList(ArrayList<PojoVehPackList> pojoVehPackLists, Context context) {
        this.pojoVehPackLists = pojoVehPackLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterVehPackList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_veh_pack_list,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVehPackList.RecyclerViewHolder holder, int position) {
        PojoVehPackList data=pojoVehPackLists.get(position);
        holder.pack_name.setText(data.getPackage_name());
        holder.tv_act_code.setText(data.getActivation_code());
        holder.tv_sold_date.setText(Common.getDateFromString(data.getPackage_activated_on()));
        holder.tv_act_date.setText(Common.getDateFromString(data.getPackage_activated_on()));
        if(data.getPackage_type().equalsIgnoreCase("addon")){
            holder.pack_type.setVisibility(View.VISIBLE);
            holder.pack_type.setText(data.getPackage_type());
        }else{
            holder.pack_type.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return pojoVehPackLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView pack_name,tv_act_date,tv_act_code,tv_sold_date,pack_type;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            pack_name=itemView.findViewById(R.id.pack_name);
            tv_act_date=itemView.findViewById(R.id.tv_act_date);
            tv_act_code=itemView.findViewById(R.id.tv_act_code);
            tv_sold_date=itemView.findViewById(R.id.tv_sold_date);
            pack_type=itemView.findViewById(R.id.pack_type);
        }
    }
}
