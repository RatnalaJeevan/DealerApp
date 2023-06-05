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

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Module_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_features extends RecyclerView.Adapter<Adapter_features.MyViewHolder> {
    Context context;
    ArrayList<Pojo_Module_list> pojo_module_listArrayList;
   public ArrayList<Pojo_part_list> pojo_part_listArrayList=new ArrayList<>();
    public Adapter_features(Context context, ArrayList<Pojo_Module_list> pojo_module_listArrayList) {
        this.context = context;
        this.pojo_module_listArrayList = pojo_module_listArrayList;
    }

    @NonNull
    @Override
    public Adapter_features.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feaures, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_features.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Module_list list = pojo_module_listArrayList.get(position);
        list.setIsSelected("n"); // Add this line to set the isSelected value
        holder.text_feature.setText(list.getModule_name());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (list.isVisible())
                {
                    holder.rl_1.setVisibility(View.GONE);
                    list.isVisible = false;
                    holder.down_arrow.setVisibility(View.VISIBLE);
                    holder.up_arrow_button.setVisibility(View.INVISIBLE);
                } else {
                    holder.rl_1.setVisibility(View.VISIBLE);
                    list.isVisible = true;
                    holder.down_arrow.setVisibility(View.INVISIBLE);
                    holder.up_arrow_button.setVisibility(View.VISIBLE);

                    inside_feature_list(holder.rv_feature_list1,position);
                }

                System.out.println("isvisible"+list.isVisible);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo_module_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_feature;
        ImageView down_arrow, up_arrow_button;
        RelativeLayout rl_1;
        RecyclerView rv_feature_list1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_feature = itemView.findViewById(R.id.text_feature);
            rv_feature_list1 = itemView.findViewById(R.id.rv_feature_list1);
            down_arrow = itemView.findViewById(R.id.down_arrow);
            up_arrow_button = itemView.findViewById(R.id.up_arrow_button);
            rl_1 = itemView.findViewById(R.id.rl_1);
        }
    }


    private void inside_feature_list(RecyclerView recyclerView, int sel_pos)
    {
        pojo_part_listArrayList=new ArrayList<>();
        pojo_part_listArrayList = pojo_module_listArrayList.get(sel_pos).getPartDetails();

        Adapter_feature_list adapter_feature_list = new Adapter_feature_list(context, pojo_part_listArrayList);
        GridLayoutManager layoutManager1 = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(adapter_feature_list);
        adapter_feature_list.notifyDataSetChanged();
    }
}