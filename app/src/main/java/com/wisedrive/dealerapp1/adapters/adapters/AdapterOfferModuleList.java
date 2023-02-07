package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.Activate;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.SelectPackages;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferModuleList;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterOfferModuleList extends RecyclerView.Adapter<AdapterOfferModuleList.RecyclerViewHolder> {
    ArrayList<PojoOfferModuleList> pojoOfferModuleLists;
    Context context;
    int count=0;
    private DecimalFormat IndianCurrencyFormat;
    public AdapterOfferModuleList(ArrayList<PojoOfferModuleList> pojoOfferModuleLists, Context context) {
        this.pojoOfferModuleLists = pojoOfferModuleLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterOfferModuleList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_offers_list,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfferModuleList.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_offer_name.setText(pojoOfferModuleLists.get(position).getOffer_title());
        IndianCurrencyFormat=new DecimalFormat("##,##,###");
        PojoOfferModuleList recyclerdata=pojoOfferModuleLists.get(position);

        if(recyclerdata.getIs_offer_taken().equalsIgnoreCase("y")){
            holder.max_ft.setVisibility(View.VISIBLE);
        }
        if(pojoOfferModuleLists.get(position).getIsSelected().equals("n"))
        {
            holder.max_ft.setVisibility(View.INVISIBLE);
        }else{
            holder.max_ft.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                SPHelper.selected_offer_id=pojoOfferModuleLists.get(position).getOffer_id();
                //Activate.getInstance().getOfferPresence();
                System.out.println("offertaken"+Activate.getInstance().is_offer_taken);
                if(recyclerdata.getIs_offer_taken()==null||
                        recyclerdata.getIs_offer_taken().equalsIgnoreCase("y")){
                    Common.CallToast(context,"This offer is already taken ",1);
                }else if(pojoOfferModuleLists.get(position).getOffer_id().equals("2")&&
                        Activate.getInstance().isvalid.equalsIgnoreCase("n"))
                {
                    Common.CallToast(context,"Fastag is not valid",1);
                }
                else{
                        System.out.println("count"+count);
                        if(recyclerdata.getIsSelected().equals("y"))
                        {
                            count=count-1;
                            System.out.println("count"+count);
                            pojoOfferModuleLists.get(position).setIsSelected("n");
                            Activate.getInstance().rl_show_popup.setVisibility(View.GONE);
                        }
                        else if(count>=Activate.getInstance().general_count){
                            Common.CallToast(context,"Selected count reached", 1);
                        }else{
                            if(recyclerdata.getIsSelected().equals("n"))
                            {
                                count=count+1;
                                System.out.println("count"+count);
                                pojoOfferModuleLists.get(position).setIsSelected("y");
                                if(pojoOfferModuleLists.get(position).getOffer_id().equals("2"))
                                {
                                    Activate.getInstance().rl_show_popup.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return pojoOfferModuleLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_offer_max_details;
        TextView tv_offer_name;
        ImageView max_ft,min_ft;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_offer_name=itemView.findViewById(R.id.tv_offer_name);
            rl_offer_max_details=itemView.findViewById(R.id.rl_offer_max_details);
            max_ft=itemView.findViewById(R.id.max_ft);
        }
    }
}
