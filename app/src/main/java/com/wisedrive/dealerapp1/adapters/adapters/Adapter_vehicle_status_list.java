package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.Buy_package;
import com.wisedrive.dealerapp1.CongratulationsPage;
import com.wisedrive.dealerapp1.Customer_page_activity;
import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.RequestVehInspection;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.New_Req_Fragment;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_New_req;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_vehicle_status_list;

import java.util.ArrayList;

public class Adapter_vehicle_status_list extends RecyclerView.Adapter<Adapter_vehicle_status_list.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_vehicle_status_list> pojo_vehicle_status_listArrayList;


    public Adapter_vehicle_status_list(Context context, ArrayList<Pojo_vehicle_status_list> pojo_vehicle_status_listArrayList) {
        this.context = context;
        this.pojo_vehicle_status_listArrayList = pojo_vehicle_status_listArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_vehicle_status_list, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Pojo_vehicle_status_list list = pojo_vehicle_status_listArrayList.get(position);
        holder.tv_veh_no.setText(list.getVehicle_no().toUpperCase());
        holder.tv_veh_status.setText(list.getInspection_status());
       // holder.tv_button_text.setText(list.getTv_button_text());

        if(list.getId().equals("1")){
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.blue));
            holder.rl_list_button.setVisibility(View.GONE);
        }
        else  if(list.getId().equals("2")){
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.blue));
            holder.rl_list_button.setVisibility(View.GONE);
        }
        else  if(list.getId().equals("4")){
            holder.rl_list_button.setVisibility(View.GONE);
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
        }
        else  if(list.getId().equals("5")){
            holder.rl_list_button.setVisibility(View.VISIBLE);
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
            holder.tv_button_text.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
            holder.rl_list_button.setBackgroundResource(R.drawable.reinspect_back);
            holder.tv_button_text.setText("Request Inspection");

        }
        else  if(list.getId().equals("6")){
            holder.rl_list_button.setVisibility(View.VISIBLE);
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
            holder.tv_button_text.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
            holder.rl_list_button.setBackgroundResource(R.drawable.reinspect_back);
            holder.tv_button_text.setText("Request Inspection");
        }
        else  if(list.getId().equals("7")){
            holder.rl_list_button.setVisibility(View.GONE);
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
            holder.tv_button_text.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
            holder.rl_list_button.setBackgroundResource(R.drawable.reinspect_back);
        }
        else  if(list.getId().equals("8")){
            holder.rl_list_button.setVisibility(View.VISIBLE);
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.light_blue_wd));
            holder.tv_button_text.setTextColor(ContextCompat.getColor(context, R.color.rep_req_clr));
            holder.rl_list_button.setBackgroundResource(R.drawable.reinspect_back);
            holder.tv_button_text.setText("Request Inspection");
        }
        else if(list.getIs_dealer_active().equalsIgnoreCase("y")&&list.getId().equals("3")){
            holder.rl_list_button.setVisibility(View.VISIBLE);
            holder.tv_veh_status.setTextColor(ContextCompat.getColor(context, R.color.ab_green));
            holder.rl_list_button.setBackgroundResource(R.drawable.list_button);
            holder.tv_button_text.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.tv_button_text.setText("List");
        }
        else {
            holder.rl_list_button.setVisibility(View.VISIBLE);
            holder.tv_button_text.setTextColor(ContextCompat.getColor(context, R.color.color4));
            holder.rl_list_button.setBackgroundResource(R.drawable.buy_package_back);
            holder.tv_button_text.setText("Buy Package");
        }

        holder.rl_list_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String status = list.getInspection_status();
                Intent intent;
                SPHelper.is_img_present=list.getIs_image_present();
                SPHelper.is_feature_present=list.getIs_feature_present();
                SPHelper.vehid=list.getVehicle_id();
                SPHelper.vehno=list.getVehicle_no();
                if (holder.tv_button_text.getText().toString().equals("List"))
                {
                    if(list.getIs_image_present().equalsIgnoreCase("y")){

                        MainActivity.getInstance().text_add_images.setTextColor(ContextCompat.getColorStateList(context, R.color.black));
                    }else {
                        MainActivity.getInstance().text_add_images.setTextColor(ContextCompat.getColorStateList(context, R.color.red));
                    }

                    if(list.getIs_feature_present().equalsIgnoreCase("y")){

                        MainActivity.getInstance().text_add_features.setTextColor(ContextCompat.getColorStateList(context, R.color.black));
                    }else {
                        MainActivity.getInstance().text_add_features.setTextColor(ContextCompat.getColorStateList(context, R.color.red));
                    }
                    if(list.getListingCount().equals("0"))
                    {
                        SPHelper.is_swap="y";
                        MainActivity.getInstance().get_listed_veh_list();
                        MainActivity.getInstance().rl_transperant_listed_cars.setVisibility(View.VISIBLE);
                        MainActivity.getInstance().rl_listed_cars.setVisibility(View.VISIBLE);
                        MainActivity.getInstance().rl_transperant_cars_list.setVisibility(View.GONE);
                        MainActivity.getInstance().card_btm_nav.setVisibility(View.GONE);
                    }else {
                        SPHelper.is_swap="n";
                        MainActivity.getInstance().rl_portal_transperant.setVisibility(View.VISIBLE);
                        MainActivity.getInstance().sel_veh_no.setText(SPHelper.vehno);
                    }


                } else if (list.getId().equals("5"))
                {
                    SPHelper.goneto="repair";
                    SPHelper.is_d_loc="y";
                    intent = new Intent(context, RequestVehInspection.class);
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_inup, R.anim.slide_outup);
                        ActivityCompat.startActivity(activity, intent, options.toBundle());
                    }
                }
                else if (list.getId().equals("6"))
                {
                    SPHelper.goneto="expired";
                    SPHelper.is_d_loc="y";
                    intent = new Intent(context, RequestVehInspection.class);
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_inup, R.anim.slide_outup);
                        ActivityCompat.startActivity(activity, intent, options.toBundle());
                    }
                }
                else if (list.getId().equals("8"))
                {
                    SPHelper.goneto="act";
                    SPHelper.is_d_loc="y";
                    intent = new Intent(context, RequestVehInspection.class);
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_inup, R.anim.slide_outup);
                        ActivityCompat.startActivity(activity, intent, options.toBundle());
                    }
                }
                else  if(holder.tv_button_text.getText().toString().equals("Buy Package"))
                {
                    MainActivity.getInstance(). rl_transperant_cars_list.setVisibility(View.GONE);
                    MainActivity.getInstance().card_btm_nav.setVisibility(View.VISIBLE);
                    MainActivity.getInstance().count2=1;
                    MainActivity.getInstance().count1=0;
                    MainActivity.getInstance().count3=0;
                    MainActivity.getInstance().tv_home.setVisibility(View.GONE);
                    MainActivity.getInstance().tv_pack.setVisibility(View.VISIBLE);
                    MainActivity.getInstance().tv_customer.setVisibility(View.GONE);
                    MainActivity.getInstance().tv_profile.setVisibility(View.GONE);
                    MainActivity.getInstance().iv_filter.setVisibility(View.GONE);
                    MainActivity.getInstance().iv_search.setVisibility(View.GONE);
                    MainActivity.getInstance().iv_home.setImageDrawable(context.getDrawable(R.drawable.unselected_home));
                    MainActivity.getInstance().iv_pack.setImageDrawable(context.getDrawable(R.drawable.packages_sel_black));
                    MainActivity.getInstance().iv_customer.setImageDrawable(context.getDrawable(R.drawable.unslected_cust));
                    MainActivity.getInstance().iv_profile.setImageDrawable(context.getDrawable(R.drawable.profile_icon));
                    MainActivity.getInstance().replaceFragment(new PackageFragment());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojo_vehicle_status_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_veh_no, tv_veh_status, tv_button_text;
        RelativeLayout rl_list_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_veh_no = itemView.findViewById(R.id.tv_veh_no);
            tv_veh_status = itemView.findViewById(R.id.tv_veh_status);
            tv_button_text = itemView.findViewById(R.id.tv_button_text);
            rl_list_button = itemView.findViewById(R.id.rl_list_button);
        }
    }

}


