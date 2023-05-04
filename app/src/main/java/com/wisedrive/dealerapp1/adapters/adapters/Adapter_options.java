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
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list_2;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_options;

import java.util.ArrayList;

public class Adapter_options extends RecyclerView.Adapter<Adapter_options.MyViewHolder> {
    Context context;
    View view;
    ArrayList<Pojo_options> pojo_optionsArrayList;

    public Adapter_options (Context context, ArrayList<Pojo_options> pojo_optionsArrayList) {
        this.context = context;
        this. pojo_optionsArrayList = pojo_optionsArrayList;
    }

    @NonNull
    @Override
    public Adapter_options.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_options,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_options.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_options list = pojo_optionsArrayList.get(position);
        holder.text_feature_list_2.setText(list.getText_feature_list_2());

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
        return   pojo_optionsArrayList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_feature_list_2;
        RelativeLayout rl_check_box,rl_uncheck_box;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_feature_list_2= itemView.findViewById(R.id.text_feature_list_2);
            rl_check_box=itemView.findViewById(R.id.rl_check_box);
            rl_uncheck_box=itemView.findViewById(R.id.rl_uncheck_box);

        }



    }
}
