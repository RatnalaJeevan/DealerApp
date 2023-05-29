package com.wisedrive.dealerapp1.adapters.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.AddNewCar;
import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.RequestVehInspection;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_New_req;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_vehicle_status_list;

import java.util.ArrayList;

public class Adapter_new_reqlist extends RecyclerView.Adapter<Adapter_new_reqlist.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_New_req> pojo_new_reqArrayList;


    public Adapter_new_reqlist(Context context,ArrayList<Pojo_New_req>  pojo_new_reqArrayList) {
        this.context = context;
        this. pojo_new_reqArrayList= pojo_new_reqArrayList;
    }

    @Override
    public Adapter_new_reqlist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_new_req_list, null);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pojo_New_req list = pojo_new_reqArrayList.get(position);
        holder.expiry_date.setText(list.getExpiry_date());
        holder.tv_no_leads.setText(list.getTv_no_leads());
        holder. tv_make_model.setText(list.getTv_make_model());
        holder. tv_price_range.setText(list.getTv_price_range());
        holder.tv_kmsdriven.setText(list.getTv_kmsdriven());
        holder.tv_makeyear.setText(list.getTv_makeyear());
        holder.owner_no.setText(list.getOwner_no());
        holder.tv_fuel_type.setText(list.getTv_fuel_type());
        holder.tv_trans_type.setText(list.getTv_trans_type());
        holder.tv_color.setText(list.getTv_color());
        holder.tv_matching_count.setText(list.getTv_matching_count());
        holder.brand_logo.setImageResource(list.getBrand_logo());
        holder.tv_color.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        holder.rl_matching_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matchingcount = list.getTv_matching_count();
                if (matchingcount.equals("2 Matching  Cars")) {
                    MainActivity.getInstance().rl_transperant_cars_list.setVisibility(View.VISIBLE);
                    MainActivity.getInstance().rl_cars_list.setVisibility(View.VISIBLE);
                    MainActivity.getInstance().card_btm_nav.setVisibility(View.GONE);

                } else if (matchingcount.equals("0 Matching Car, Add Car")) {
                  /* Intent intent = new Intent(context, AddNewCar.class);
                    context.startActivity(intent);
                    context.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);*/

                    Intent intent = new Intent(context, AddNewCar.class);
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_inup, R.anim.slide_outup);
                        ActivityCompat.startActivity(activity, intent, options.toBundle());


                    }
                }
            }


        });
        holder.tv_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().rl_color_popup_transperant.setVisibility(View.VISIBLE);
                MainActivity.getInstance().card_btm_nav.setVisibility(View.GONE);

            }
        });


    }
    @Override
    public int getItemCount() {
        return pojo_new_reqArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expiry_date,tv_no_leads,tv_make_model,tv_price_range,
                tv_kmsdriven,tv_makeyear,owner_no,tv_fuel_type,tv_trans_type,tv_color,tv_matching_count;
        ImageView brand_logo;
        RelativeLayout rl_matching_car;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expiry_date=itemView.findViewById(R.id.expiry_date);
            tv_no_leads=itemView.findViewById(R.id.tv_no_leads);
            tv_make_model=itemView.findViewById(R.id.tv_make_model);
            tv_price_range=itemView.findViewById(R.id.tv_price_range);
            tv_kmsdriven=itemView.findViewById(R.id.tv_kmsdriven);
            tv_makeyear=itemView.findViewById(R.id.tv_makeyear);
            owner_no=itemView.findViewById(R.id.owner_no);
            tv_fuel_type=itemView.findViewById(R.id.tv_fuel_type);
            tv_trans_type=itemView.findViewById(R.id.tv_trans_type);
            tv_color=itemView.findViewById(R.id.tv_color);
            tv_matching_count=itemView.findViewById(R.id.tv_matching_count);
            brand_logo= itemView.findViewById(R.id.brand_logo);
            rl_matching_car=itemView.findViewById(R.id.rl_matching_car);
        }
    }
}

