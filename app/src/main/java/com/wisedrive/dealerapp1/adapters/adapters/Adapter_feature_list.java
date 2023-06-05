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
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.Feature;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_features;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_imagearray;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;

import java.util.ArrayList;

public class Adapter_feature_list extends RecyclerView.Adapter<Adapter_feature_list.MyViewHolder> {
    Context context;
    ArrayList<Pojo_part_list> pojo_part_listArrayList;
    int count=0;


    public Adapter_feature_list(Context context, ArrayList<Pojo_part_list> pojo_part_listArrayList) {
        this.context = context;
        this.pojo_part_listArrayList = pojo_part_listArrayList;
    }

    @NonNull
    @Override
    public Adapter_feature_list.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_feature_list.MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position)
    {
        Pojo_part_list list = pojo_part_listArrayList.get(position);
        holder.text_feature_list.setText(list.getPart_name());

        if(list.getIs_selected().equals("n"))
        {
            holder.rl_check_box.setVisibility(View.INVISIBLE);
        }else
        {
            holder.rl_check_box.setVisibility(View.VISIBLE);
        }
        if(count==0)
        {
            if(list.getIs_feature_present()==null||list.getIs_feature_present().equals("")||
                    list.getIs_feature_present().equalsIgnoreCase("n"))
            {
                list.setIs_selected("n");
                holder.rl_check_box.setVisibility(View.INVISIBLE);
            }else {
                holder.rl_check_box.setVisibility(View.VISIBLE);
                list.setIs_selected("y");

            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                count=1;

                if(list.getIs_selected().equals("n"))
                {

                    SPHelper.part_ids.add(pojo_part_listArrayList.get(position).getPart_id());
                    SPHelper.module_ids.add(pojo_part_listArrayList.get(position).getModule_id());
                    list.setIs_selected("y");

                }else
                {
                    list.setIs_selected("n");

                }

                SPHelper.part_id=pojo_part_listArrayList.get(position).getPart_id();
                SPHelper.module_id=pojo_part_listArrayList.get(position).getModule_id();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo_part_listArrayList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_feature_list;
        RelativeLayout rl_check_box, rl_uncheck_box;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_feature_list = itemView.findViewById(R.id.text_feature_list);
            rl_check_box = itemView.findViewById(R.id.rl_check_box);
            rl_uncheck_box = itemView.findViewById(R.id.rl_uncheck_box);
        }
    }

}




