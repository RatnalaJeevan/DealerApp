package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferModuleList;

import java.util.ArrayList;

public class AdapterOfferPurchasedList extends RecyclerView.Adapter<AdapterOfferPurchasedList.RecyclerViewHolder> {

    ArrayList<PojoOfferModuleList> pojoOfferModuleLists;
    Context context;

    public AdapterOfferPurchasedList(ArrayList<PojoOfferModuleList> pojoOfferModuleLists, Context context) {
        this.pojoOfferModuleLists = pojoOfferModuleLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterOfferPurchasedList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_offers_list,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfferPurchasedList.RecyclerViewHolder holder, int position) {
        holder.tv_offer_name.setText(pojoOfferModuleLists.get(position).getOffer_title());
        holder.tv_offer_desrptn.setText(pojoOfferModuleLists.get(position).getOffer_details());

    }

    @Override
    public int getItemCount() {
        return pojoOfferModuleLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_offer_name,tv_offer_desrptn;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_offer_name=itemView.findViewById(R.id.tv_offer_name);
            tv_offer_desrptn=itemView.findViewById(R.id.tv_offer_desrptn);
        }
    }
}
