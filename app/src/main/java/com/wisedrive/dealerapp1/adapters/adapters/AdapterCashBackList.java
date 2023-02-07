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
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferModuleList;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterCashBackList extends RecyclerView.Adapter<AdapterCashBackList.RecyclerViewViewHolder> {

    ArrayList<PojoOfferModuleList> pojoOfferModuleLists;
    Context context;
    private DecimalFormat IndianCurrencyFormat;
    int count=0;
    public AdapterCashBackList(ArrayList<PojoOfferModuleList> pojoOfferModuleLists, Context context) {
        this.pojoOfferModuleLists = pojoOfferModuleLists;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterCashBackList.RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cash_back_list,parent,false);
        return new RecyclerViewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCashBackList.RecyclerViewViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoOfferModuleList recyclerdata=pojoOfferModuleLists.get(position);
        holder.tv_offer_name.setText(recyclerdata.getOffer_title());
        holder.tv_offer_desrptn.setText(recyclerdata.getOffer_details());

        if(pojoOfferModuleLists.get(position).getIsSelected().equals("n"))
        {
            holder.max_ft.setVisibility(View.INVISIBLE);
        }else{
            holder.max_ft.setVisibility(View.VISIBLE);
        }
        //selection of itemsis count or less than count
        //before that chech one service ,whether offer is taken or not
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.selected_offer_id=recyclerdata.getOffer_id();

                if(recyclerdata.getIs_offer_taken()==null||
                     recyclerdata.getIs_offer_taken().equalsIgnoreCase("y")){
                     Common.CallToast(context,"This offer is already taken ",1);
                }else{
                    if(Activate.getInstance().cat_id.equals("2"))
                    {
                        //cashback
                        System.out.println("count"+count);
                        if(recyclerdata.getIsSelected().equals("y")){
                            count=count-1;
                            System.out.println("count"+count);
                            pojoOfferModuleLists.get(position).setIsSelected("n");

                        }
                        else if(count>=Activate.getInstance().cashback_count){
                            Common.CallToast(context,"Selected count reached", 1);
                        }else{
                            if(recyclerdata.getIsSelected().equals("n")){
                                count=count+1;
                                System.out.println("count"+count);
                                pojoOfferModuleLists.get(position).setIsSelected("y");
                            }
                        }
                    }
                    else{
                        //addon
                        System.out.println("count"+count);
                        if(recyclerdata.getIsSelected().equals("y")){
                            count=count-1;
                            System.out.println("count"+count);
                            pojoOfferModuleLists.get(position).setIsSelected("n");
                        }
                        else if(count>=2){
                            Common.CallToast(context,"Selected count reached", 1);
                        }else{

                                if(recyclerdata.getIsSelected().equals("n")){
                                    count=count+1;
                                    System.out.println("count"+count);
                                    pojoOfferModuleLists.get(position).setIsSelected("y");
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


    public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl1;
        TextView tv_offer_name,tv_offer_desrptn;
        ImageView max_ft;
        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_offer_name=itemView.findViewById(R.id.tv_offer_name);
            rl1=itemView.findViewById(R.id.rl1);
            max_ft=itemView.findViewById(R.id.max_ft);
            tv_offer_desrptn=itemView.findViewById(R.id.tv_offer_desrptn);
        }
    }
}
