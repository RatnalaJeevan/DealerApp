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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list_2;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_features;
import com.wisedrive.dealerapp1.pojos.pojos.pojo_static_image_data;

import java.util.ArrayList;

public class Adapter_features extends RecyclerView.Adapter<Adapter_features.MyViewHolder> {
    Context context;
    View view;
    ArrayList<Pojo_features> pojo_featuresArrayList;

    RecyclerView rv_feature_list1;
    Adapter_feature_list adapter_feature_list;
    ArrayList<Pojo_feature_list> pojo_feature_listArrayList;

    RecyclerView rv_feature_list2;
    Adapter_feature_list2 adapter_feature_list2;
    ArrayList<Pojo_feature_list_2> pojo_feature_list_2ArrayList;


    public Adapter_features (Context context, ArrayList<Pojo_features>pojo_featuresArrayList) {
        this.context = context;
        this.pojo_featuresArrayList =pojo_featuresArrayList;
    }

    @NonNull
    @Override
    public Adapter_features.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feaures,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_features.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_features list =pojo_featuresArrayList.get(position);
        holder.text_feature.setText(list.getText_feature());

        pojo_feature_listArrayList= new ArrayList<>();
        pojo_feature_listArrayList.add(new Pojo_feature_list("Power Steering"));
        pojo_feature_listArrayList.add(new Pojo_feature_list("Cruise Control"));
        pojo_feature_listArrayList.add(new Pojo_feature_list("Navigation System"));
        pojo_feature_listArrayList.add(new Pojo_feature_list("Adjustable Steering"));
        adapter_feature_list = new  Adapter_feature_list(context, pojo_feature_listArrayList);
        GridLayoutManager layoutManager1 = new GridLayoutManager(context, 1);
        rv_feature_list1.setLayoutManager(layoutManager1);
        rv_feature_list1.setAdapter(adapter_feature_list);

        pojo_feature_list_2ArrayList= new ArrayList<>();
        pojo_feature_list_2ArrayList.add(new Pojo_feature_list_2("Air Conditioning"));
        adapter_feature_list2 = new  Adapter_feature_list2(context, pojo_feature_list_2ArrayList);
        GridLayoutManager layoutManager2 = new GridLayoutManager(context, 1);
        rv_feature_list2.setLayoutManager(layoutManager2);
        rv_feature_list2.setAdapter(adapter_feature_list2);

        holder.rl_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.isVisible = !list.isVisible;
                notifyItemChanged(position);
            }
        });

        // Update the visibility of the item based on its status
        if (list.isVisible()) {
            holder.rl_1.setVisibility(View.VISIBLE);
            holder.rl_2.setVisibility(View.VISIBLE);
            holder.down_arrow.setVisibility(View.INVISIBLE);
             holder.up_arrow_button.setVisibility(View.VISIBLE);
        } else {
            holder.rl_1.setVisibility(View.GONE);
            holder.rl_2.setVisibility(View.GONE);
             holder.down_arrow.setVisibility(View.VISIBLE);
             holder.up_arrow_button.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return pojo_featuresArrayList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_feature;
        ImageView down_arrow,up_arrow_button;
        RelativeLayout rl_1,rl_2,rl_features;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_feature= itemView.findViewById(R.id.text_feature);
            rv_feature_list1=(RecyclerView)itemView.findViewById(R.id.rv_feature_list1);
            rv_feature_list2=(RecyclerView) itemView.findViewById(R.id.rv_feature_list2);
            down_arrow=itemView.findViewById(R.id.down_arrow);
            up_arrow_button=itemView.findViewById(R.id.up_arrow_button);
            rl_1=itemView.findViewById(R.id.rl_1);
            rl_2=itemView.findViewById(R.id.rl_2);
            rl_features=itemView.findViewById(R.id.rl_features);


        }



    }
}



