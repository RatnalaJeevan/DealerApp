package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.BuyBackGuarantee;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAddOnLists;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterAddOnList extends RecyclerView.Adapter<AdapterAddOnList.RecyclerViewHolder> {

    Context context;
    ArrayList<PojoAddOnLists> pojoAddOnLists;
    private DecimalFormat IndianCurrencyFormat;
    public AdapterAddOnList(Context context, ArrayList<PojoAddOnLists> pojoAddOnLists) {
        this.context = context;
        this.pojoAddOnLists = pojoAddOnLists;
    }

    @NonNull
    @Override
    public AdapterAddOnList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_add_onlists,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddOnList.RecyclerViewHolder holder, int position) {
        PojoAddOnLists recyclerdata=pojoAddOnLists.get(position);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        holder.addon_name.setText(recyclerdata.getAddon_name());
        holder.validity_days.setText(recyclerdata.getValidity_days()+"\t days");
        int price=(int)Double.parseDouble(recyclerdata.getFinal_price());
        String s = IndianCurrencyFormat.format(price);
        holder.final_price.setText(s);
        Glide.with(context).load(recyclerdata.getAddon_logo()).placeholder(R.drawable.icon_noimage).into(holder.add_onlogo);

        if(pojoAddOnLists.get(position).getAddon_id().equals("2")){
            holder.rl_showroom_warranty.setBackground(context.getDrawable(R.drawable.cv_gradient_main_pack));
        }else if(pojoAddOnLists.get(position).getAddon_id().equals("4")){
            holder.rl_showroom_warranty.setBackground(context.getDrawable(R.drawable.cv_shroom));
        }else if(pojoAddOnLists.get(position).getAddon_id().equals("5")){
            holder.rl_showroom_warranty.setBackground(context.getDrawable(R.drawable.cv_gradient2));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.add_onid=recyclerdata.getAddon_id();
                SPHelper.long_description=recyclerdata.getLong_description();
                SPHelper.addon_name=recyclerdata.getAddon_name();
                SPHelper.add_onprice=0;
                Intent intent=new Intent(context, BuyBackGuarantee.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoAddOnLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView add_onlogo;
        TextView addon_name,final_price,validity_days;
        RelativeLayout rl_showroom_warranty;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            validity_days=itemView.findViewById(R.id.validity_days);
            final_price=itemView.findViewById(R.id.final_price);
            addon_name=itemView.findViewById(R.id.addon_name);
            add_onlogo=itemView.findViewById(R.id.add_onlogo);
            rl_showroom_warranty=itemView.findViewById(R.id.rl_showroom_warranty);
        }
    }
}
