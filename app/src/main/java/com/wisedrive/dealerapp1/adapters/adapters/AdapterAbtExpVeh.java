package com.wisedrive.dealerapp1.adapters.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.AddNewCar;
import com.wisedrive.dealerapp1.OnLoadMoreListener;
import com.wisedrive.dealerapp1.pojos.PojoAbtExpVehList;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterAbtExpVeh extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DecimalFormat IndianCurrencyFormat;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private Activity activity;
    public ProgressBar progressBar;
    private OnLoadMoreListener onLoadMoreListener;
    String kdriven="";
    double final_amount;
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }
    ArrayList<PojoAbtExpVehList> pojoAbtExpVehLists=new ArrayList<>();


    public AdapterAbtExpVeh(Activity activity, ArrayList<PojoAbtExpVehList> pojoAbtExpVehLists, RecyclerView recyclerView)
    {
        this.activity = activity;
        this.pojoAbtExpVehLists = pojoAbtExpVehLists;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_insp_on_date,label_map,tv_cust_name;
        ImageView iv_hide,iv_edit;
        RelativeLayout rl_sold_label,rl_cv,rl_photos;
        LinearLayout linear_cp;
        View v3;
        public TextView tv_make_model,tv_insu;
        ImageView brand_logo,transmission_type,iv_more,iv_haswarranty,iv_nowarranty;
        TextView tv_makeyear,tv_fueltype,tv_kmsdriven,tv_carno,insurance_exp_date,tv_inspect_status,no_kms,no_days,
                label_listingprice, tv_price,tv_label_transmissiontype;
        RelativeLayout rl_car_brief,rl_insp_status,rl_cp_comments;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_cp_comments=itemView.findViewById(R.id.rl_cp_comments);
            tv_insp_on_date=itemView.findViewById(R.id.tv_insp_on_date);
            label_map=itemView.findViewById(R.id.label_map);
            iv_hide=itemView.findViewById(R.id.iv_hide);
            rl_sold_label=itemView.findViewById(R.id.rl_sold_label);
            rl_cv=itemView.findViewById(R.id.rl_cv);
            v3=itemView.findViewById(R.id.v3);
            iv_edit=itemView.findViewById(R.id.iv_edit);
            rl_photos=itemView.findViewById(R.id.rl_photos);
            tv_cust_name=itemView.findViewById(R.id.tv_cust_name);
            brand_logo=itemView.findViewById(R.id.brand_logo);
            linear_cp=itemView.findViewById(R.id.linear_cp);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_make_model=itemView.findViewById(R.id.tv_make_model);
            tv_makeyear=itemView.findViewById(R.id.tv_makeyear);
            tv_insu=itemView.findViewById(R.id.tv_insu);
            tv_kmsdriven=itemView.findViewById(R.id.tv_kmsdriven);
            tv_fueltype=itemView.findViewById(R.id.tv_fueltype);
            tv_carno=itemView.findViewById(R.id.tv_carno);
            tv_inspect_status=itemView.findViewById(R.id.tv_inspect_status);
        }
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar =  view.findViewById(R.id.itemProgressbar);
        }
    }
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    @Override
    public int getItemViewType(int position) {
        return pojoAbtExpVehLists.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.items_new_all_cars_list, null);
            return new RecyclerViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderView, int position) {

        if (holderView instanceof RecyclerViewHolder) {
            final RecyclerViewHolder holder = (RecyclerViewHolder) holderView;
            IndianCurrencyFormat = new DecimalFormat("##,##,###");
            PojoAbtExpVehList recyclerdata = pojoAbtExpVehLists.get(position);
            holder.linear_cp.setVisibility(View.GONE);
            holder.rl_cp_comments.setVisibility(View.GONE);
            holder.iv_edit.setVisibility(View.GONE);
            if(recyclerdata.getInspection_warranty_status()==null||recyclerdata.getInspection_warranty_status().equals("null"))
            {
                if(recyclerdata.getInspection_status_by_mechanic()==null||recyclerdata.getInspection_status_by_mechanic().equals("null")){
                    holder.tv_inspect_status.setText("-");
                }else{

                    String s1=pojoAbtExpVehLists.get(position).getInspection_status_by_mechanic();
                    String rs1=s1.substring(0,1).toUpperCase()+s1.substring(1);
                    holder.tv_inspect_status.setText(rs1);
                }
            }
            else{
                String s1=pojoAbtExpVehLists.get(position).getInspection_warranty_status();
                String rs1=s1.substring(0,1).toUpperCase()+s1.substring(1);
                holder.tv_inspect_status.setText(rs1);
            }

            if(recyclerdata.getInsurance_provider()==null||recyclerdata.getInsurance_provider().equals("null")){
                holder.tv_insu.setVisibility(View.INVISIBLE);

            }else{
                holder.tv_insu.setText(recyclerdata.getInsurance_provider());
            }
            if(recyclerdata.getInspection_date()==null||recyclerdata.getInspection_date().equals("null")||
                    recyclerdata.getInspection_date().equals("")){
                holder.tv_insp_on_date.setText("");
            }else{
                holder.tv_insp_on_date.setText(Common.getDateFromString(recyclerdata.getInspection_date()));
            }
            holder.tv_inspect_status.setBackground(AppCompatResources.getDrawable(activity,R.drawable.cardview_act_code));
            if(holder.tv_inspect_status.getText().equals("Requested")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.req_clr));
            }
            else if(holder.tv_inspect_status.getText().equals("Reinspect")||holder.tv_inspect_status.getText().equals("Reinspection")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.re_insp_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.re_insp_clr));
            }else if(holder.tv_inspect_status.getText().equals("Repair Requested")|| holder.tv_inspect_status.getText().equals("Expired")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
            }else{
                //approved or completed
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.app_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.app_clr));
            }
            //car logo

            if(recyclerdata.getBrand_icon() == null || recyclerdata.getBrand_icon().isEmpty() || recyclerdata.getBrand_icon().equals("null"))
            {
                holder.brand_logo.setImageResource(R.drawable.icon_noimage);
            }else {
                Glide.with(activity).load(recyclerdata.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(holder.brand_logo);
            }
            //make
            holder.tv_make_model.setText(recyclerdata.getVehicle_model());
            //veh no
            holder.tv_carno.setText(recyclerdata.getVehicle_no());
            //kms

            if(recyclerdata.getOdometer()==null||recyclerdata.getOdometer().equals("null")||recyclerdata.getOdometer().equals(""))
            {
                kdriven="";
                holder.tv_kmsdriven.setVisibility(View.INVISIBLE);
            }else{
                holder.tv_kmsdriven.setVisibility(View.VISIBLE);
                double kmsdriven= Double.parseDouble(recyclerdata.getOdometer());
                int y=(int)kmsdriven;
                kdriven = IndianCurrencyFormat.format(y);
                holder.tv_kmsdriven.setText(kdriven+" "+"km");
            }
            //fuel
            holder.tv_fueltype.setText(recyclerdata.getFuel_type());

            //year
            if(recyclerdata.getManufacturing_year()==null||recyclerdata.getManufacturing_year().equals("null")){
                holder.tv_makeyear.setVisibility(View.INVISIBLE);

            }else{
                holder.tv_makeyear.setText(recyclerdata.getManufacturing_year());
            }
            //insurance  --bank name ?

            //price
            if(recyclerdata.getActual_price()==null||recyclerdata.getActual_price().equals("null")){
                holder.tv_price.setVisibility(View.INVISIBLE);

            }else{
                holder.tv_price.setVisibility(View.VISIBLE);
                final_amount= Double.parseDouble(recyclerdata.getActual_price());
                int x=(int)final_amount;
                String actualprice = IndianCurrencyFormat.format(x);
                holder.tv_price.setText("₹"+" "+actualprice);
            }
            //veh is public or private ?

            if(recyclerdata.getIs_vehicle_public()==null){
                SPHelper.veh_ispublic="";
            }
            else if(recyclerdata.getIs_vehicle_public().equalsIgnoreCase("y"))
            {
                SPHelper.veh_ispublic=recyclerdata.getIs_vehicle_public();
            }

            holder.iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SPHelper.camefrom="edit";
                    SPHelper.vehid=recyclerdata.getVehicle_id();
                    System.out.println("veh_id"+SPHelper.vehid);
                    SPHelper.selling_price=recyclerdata.getActual_price();
                    SPHelper.vehno=recyclerdata.getVehicle_no();
                    SPHelper.manufacture_year=recyclerdata.getManufacturing_year();
                    SPHelper.kmsdriven=recyclerdata.getOdometer();
                    SPHelper.no_owners=recyclerdata.getOwnership();
                    SPHelper.veh_color=recyclerdata.getColor();
                    SPHelper.insu_pol=recyclerdata.getInsurance_policy();
                    SPHelper.ins_exp_date=recyclerdata.getInsurance_validity();
                    SPHelper.insurance_provider= recyclerdata.getInsurance_provider();
                    SPHelper.insurancetype=recyclerdata.getInsurance_type();
                    Intent intent=new Intent(activity, AddNewCar.class);
                    activity.startActivity(intent);
                    activity.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);
                }
            });

        }else if (holderView instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holderView;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return pojoAbtExpVehLists == null ? 0 : pojoAbtExpVehLists.size();
    }
    public void setLoaded() {
        isLoading = false;
    }

}
