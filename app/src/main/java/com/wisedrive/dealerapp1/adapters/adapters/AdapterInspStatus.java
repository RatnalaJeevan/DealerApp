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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;

import java.util.ArrayList;

public class AdapterInspStatus extends RecyclerView.Adapter<AdapterInspStatus.RecyclerviewHolder> {
    ArrayList<PojoSample> pojoSamples;
    Context context;

    public AdapterInspStatus(ArrayList<PojoSample> pojoSamples, Context context) {
        this.pojoSamples = pojoSamples;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInspStatus.RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_insp_statuses,parent,false);
        return new RecyclerviewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull AdapterInspStatus.RecyclerviewHolder holder, int position) {

        holder.status_name.setText(pojoSamples.get(position).getWarranty_status());
        if(pojoSamples.get(position).getIsSelected().equals("n")){
            holder.status_name.setTextColor(context.getColor(R.color.lightgrey));
            holder.iv_tick.setVisibility(View.GONE);
            holder.rl_insp.setBackground(context.getDrawable(R.drawable.map_border));
        }else{
            holder.status_name.setTextColor(context.getColor(R.color.black));
            holder.iv_tick.setVisibility(View.VISIBLE);
            holder.rl_insp.setBackground(context.getDrawable(R.drawable.blue_border));

        }
        holder.rl_insp.setOnClickListener(view ->
        {
            if(pojoSamples.get(position).getIsSelected().equals("n")){
                pojoSamples.get(position).setIsSelected("y");
                SPHelper.selected_insp_statuses.get(position).setIsSelected("y");
            }else{
                pojoSamples.get(position).setIsSelected("n");
                SPHelper.selected_insp_statuses.get(position).setIsSelected("n");
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return pojoSamples.size();
    }

    public class RecyclerviewHolder extends RecyclerView.ViewHolder {
        TextView status_name;
        ImageView iv_tick;
        RelativeLayout rl_insp;
        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            status_name=itemView.findViewById(R.id.status_name);
            iv_tick=itemView.findViewById(R.id.iv_tick);
            rl_insp=itemView.findViewById(R.id.rl_insp);
        }
    }
}
