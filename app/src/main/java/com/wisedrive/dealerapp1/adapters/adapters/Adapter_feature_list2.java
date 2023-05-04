package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list_2;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_options;

import java.util.ArrayList;

public class Adapter_feature_list2 extends RecyclerView.Adapter<Adapter_feature_list2.MyViewHolder> {
    Context context;
    View view;
    ArrayList<Pojo_feature_list_2>pojo_feature_list_2ArrayList;

    ArrayList<Pojo_options>pojo_optionsArrayList;
    Adapter_options adapter_options;
    RecyclerView rv_options;

    public Adapter_feature_list2 (Context context, ArrayList<Pojo_feature_list_2>pojo_feature_list_2ArrayList) {
        this.context = context;
        this.pojo_feature_list_2ArrayList =pojo_feature_list_2ArrayList;
    }

    @NonNull
    @Override
    public Adapter_feature_list2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature_list2,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_feature_list2.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_feature_list_2 list =pojo_feature_list_2ArrayList.get(position);
        holder.text_feature_2.setText(list.getText_feature_2());


        pojo_optionsArrayList= new ArrayList<>();
        pojo_optionsArrayList.add(new Pojo_options("Air Conditioning"));
        pojo_optionsArrayList.add(new Pojo_options("Power Steering"));
        adapter_options = new  Adapter_options(context,pojo_optionsArrayList);
        GridLayoutManager layoutManager2 = new GridLayoutManager(context, 1);
        rv_options.setLayoutManager(layoutManager2);
        rv_options.setAdapter(adapter_options);


    }

    @Override
    public int getItemCount() {
        return  pojo_feature_list_2ArrayList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_feature_2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_feature_2= itemView.findViewById(R.id.text_feature_2);
            rv_options=(RecyclerView) itemView.findViewById(R.id.rv_options);

        }



    }
}
