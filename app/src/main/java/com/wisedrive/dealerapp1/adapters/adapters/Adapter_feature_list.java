package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_features;

import java.util.ArrayList;

public class Adapter_feature_list extends RecyclerView.Adapter<Adapter_feature_list.MyViewHolder> {
    Context context;
    View view;
    ArrayList<Pojo_feature_list> pojo_feature_listArrayList;

    public Adapter_feature_list (Context context, ArrayList<Pojo_feature_list> pojo_feature_listArrayList) {
        this.context = context;
        this. pojo_feature_listArrayList = pojo_feature_listArrayList;
    }

    @NonNull
    @Override
    public Adapter_feature_list.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature_list,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_feature_list.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_feature_list list = pojo_feature_listArrayList.get(position);
        holder.text_feature_list.setText(list.getText_feature_list());

        holder.rl_check_box.setVisibility(list.isSelected() ? View.VISIBLE : View.INVISIBLE);
        holder.rl_uncheck_box.setVisibility(list.isSelected() ? View.INVISIBLE : View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setSelected(!list.isSelected());
                holder.rl_check_box.setVisibility(list.isSelected() ? View.VISIBLE : View.INVISIBLE);
                holder.rl_uncheck_box.setVisibility(list.isSelected() ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  pojo_feature_listArrayList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_feature_list;
        RelativeLayout rl_check_box,rl_uncheck_box;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_feature_list= itemView.findViewById(R.id.text_feature_list);
            rl_check_box=itemView.findViewById(R.id.rl_check_box);
            rl_uncheck_box=itemView.findViewById(R.id.rl_uncheck_box);
        }



    }
}
