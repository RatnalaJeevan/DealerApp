package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_colours;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listed_vehicle_list;

import java.util.ArrayList;

public class Adapter_Colours extends RecyclerView.Adapter<Adapter_Colours.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_colours> pojoColoursArrayList;

    public  Adapter_Colours(Context context, ArrayList<Pojo_colours> pojoColoursArrayList) {
        this.context = context;
        this. pojoColoursArrayList= pojoColoursArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_colour, null);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pojo_colours list = pojoColoursArrayList.get(position);
        holder.tv_color.setText(list.getTv_color());

    }
    @Override
    public int getItemCount() {
        return   pojoColoursArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_color;
        RelativeLayout rl_colour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_color=itemView.findViewById(R.id.tv_color);
            rl_colour=itemView.findViewById(R.id.rl_colour);


        }
    }

}
