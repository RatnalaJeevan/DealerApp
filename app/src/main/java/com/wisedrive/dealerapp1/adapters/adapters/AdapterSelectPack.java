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

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.SelectPackages;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSelectPack;

import java.util.ArrayList;

public class AdapterSelectPack extends RecyclerView.Adapter<AdapterSelectPack.RecyclerViewHolder> {

    ArrayList<PojoSelectPack> pojoSelectPacks;
    Context context;

    public AdapterSelectPack(ArrayList<PojoSelectPack> pojoSelectPacks, Context context) {
        this.pojoSelectPacks = pojoSelectPacks;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSelectPack.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_select_packs,parent,false);
        return new RecyclerViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterSelectPack.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.label_pack_type.setText(pojoSelectPacks.get(position).getDisplay_name());
        holder.label1.setText(position+1+".");

        holder.label_pending.setText(pojoSelectPacks.get(position).getNo_of_warranties_left()+" pending");
//        holder.paid_date.setText(pojoSelectPacks.get(position).getPayment_date());
        //holder.exp_date.setText(pojoSelectPacks.get(position).getValid_days()+"\tdays");
        holder.exp_date.setText(Common.getDateFromString(pojoSelectPacks.get(position).getValid_to_date()));
        holder.sub_pack_name.setText("("+pojoSelectPacks.get(position).getSub_package_name()+")");


//        for(int i=0;i<pojoSelectPacks.size();i++){
//            int j=i+1;
//            holder.label1.setText(j+".");
//        }
        if(pojoSelectPacks.get(position).getIsSelected().equals("n")){
          //  holder.rl_1st.setBackground(context.getDrawable(R.drawable.cv_circle_light_grey));
            holder.selected.setVisibility(View.GONE);
        }else{
           // holder.rl_1st.setBackground(context.getDrawable(R.drawable.blue_border));
            holder.selected.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPHelper.selected_pack_id=pojoSelectPacks.get(position).getPackage_id();
                SPHelper.dpp_id=pojoSelectPacks.get(position).getDpp_id();
                for (int i=0;i<pojoSelectPacks.size();i++)
                {
                    if (i == position) {
                        pojoSelectPacks.get(i).setIsSelected("y");

                    } else {
                        pojoSelectPacks.get(i).setIsSelected("n");
                    }
                }

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojoSelectPacks.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView label1,label_pack_type,pending_packs,paid_date,exp_date,sub_pack_name,label_pending;
        RelativeLayout rl_1st;
        ImageView selected;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            label1=itemView.findViewById(R.id.label1);
            label_pack_type=itemView.findViewById(R.id.label_pack_type);
            rl_1st=itemView.findViewById(R.id.rl_1st);
            selected=itemView.findViewById(R.id.selected);
            exp_date=itemView.findViewById(R.id.exp_date);
            sub_pack_name=itemView.findViewById(R.id.sub_pack_name);
            label_pending=itemView.findViewById(R.id.label_pending);
        }
    }
}
