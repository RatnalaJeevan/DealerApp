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
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;

import java.util.ArrayList;

public class Adapter_feature_list extends RecyclerView.Adapter<Adapter_feature_list.MyViewHolder> {
    Context context;
    ArrayList<Pojo_part_list> pojo_part_listArrayList;

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
    public void onBindViewHolder(@NonNull Adapter_feature_list.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_part_list list = pojo_part_listArrayList.get(position);
        holder.text_feature_list.setText(list.getPart_name());

        holder.rl_check_box.setVisibility(list.isSelected() ? View.VISIBLE : View.INVISIBLE);
        holder.rl_uncheck_box.setVisibility(list.isSelected() ? View.INVISIBLE : View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setSelected(!list.isSelected());
                holder.rl_check_box.setVisibility(list.isSelected() ? View.VISIBLE : View.INVISIBLE);
                holder.rl_uncheck_box.setVisibility(list.isSelected() ? View.INVISIBLE : View.VISIBLE);

                // Update the is_feature_present value based on the selection
                list.setIs_feature_present(list.isSelected() ? "y" : "n");
                SPHelper.part_id=pojo_part_listArrayList.get(position).getPart_id();
                SPHelper.module_id=pojo_part_listArrayList.get(position).getModule_id();
                System.out.println("list_iselected"+list.isSelected());
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
    public ArrayList<Feature> getSelectedFeatures() {
        ArrayList<Feature> selectedFeatures = new ArrayList<>();
        for (Pojo_part_list partList : pojo_part_listArrayList) {
            if (partList.isSelected()) {
                // Create a new Feature object and add it to the list
               // Feature feature = new Feature(partList.getModule_id(), partList.getPart_id(), "Y");
               // selectedFeatures.add(feature);
            }
        }
        return selectedFeatures;
    }
}




