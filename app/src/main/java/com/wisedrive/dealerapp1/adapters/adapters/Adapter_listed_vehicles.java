package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listed_vehicle_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_vehicle_status_list;

import java.util.ArrayList;

public class Adapter_listed_vehicles extends RecyclerView.Adapter<Adapter_listed_vehicles.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_listed_vehicle_list> pojo_listed_vehicle_listArrayList;

    public  Adapter_listed_vehicles(Context context, ArrayList<Pojo_listed_vehicle_list> pojo_listed_vehicle_listArrayList) {
        this.context = context;
        this. pojo_listed_vehicle_listArrayList= pojo_listed_vehicle_listArrayList;
    }

    @Override
    public Adapter_listed_vehicles.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_listed_cars, null);
        return new MyViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pojo_listed_vehicle_list list = pojo_listed_vehicle_listArrayList.get(position);
        holder.tv_veh_no.setText(list.getVehicle_no());
        holder.tv_veh_make_model.setText(list.getVehicle_make()+"-"+list.getVehicle_model());
       //holder.tv_button_text.setText(list.get());

        if(list.getLeadCount().getCount().equals("0"))
        {
            holder.rl_list_button.setVisibility(View.VISIBLE);
            holder.tv_lead_count.setVisibility(View.INVISIBLE);
        }else {
            holder.rl_list_button.setVisibility(View.INVISIBLE);
            holder.tv_lead_count.setVisibility(View.VISIBLE);
            holder.tv_lead_count.setText(list.getLeadCount().getCount()+" Leads");
        }

        holder.rl_list_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.old_veh_id=list.getVehicle_id();
                SPHelper.ol_veh_no=list.getVehicle_no();
                MainActivity.getInstance().tv_swap_content.setText("Are you sure you want to remove "+SPHelper.ol_veh_no
                +" from warranty portal and add "+SPHelper.vehno);
                MainActivity.getInstance().rl_trans_alert_pop_up.setVisibility(View.VISIBLE);
                MainActivity.getInstance().rl_alert.setVisibility(View.VISIBLE);
                MainActivity.getInstance().card_btm_nav.setVisibility(View.GONE);
                MainActivity.getInstance().rl_transperant_listed_cars.setVisibility(View.GONE);
            }
        });

    }
    @Override
    public int getItemCount() {
        return  pojo_listed_vehicle_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_veh_no,tv_veh_make_model,tv_button_text,tv_lead_count;
        RelativeLayout rl_list_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_veh_no=itemView.findViewById(R.id.tv_veh_no);
            tv_veh_make_model=itemView.findViewById(R.id.tv_veh_make_model);
            tv_lead_count=itemView.findViewById(R.id.tv_lead_count);
            tv_button_text=itemView.findViewById(R.id.tv_button_text);
            rl_list_button=itemView.findViewById(R.id.rl_list_button);

        }
    }

}
