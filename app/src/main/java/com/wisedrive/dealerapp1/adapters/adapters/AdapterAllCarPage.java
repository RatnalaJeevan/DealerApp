package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.wisedrive.dealerapp1.AddNewCar;
import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.OnLoadMoreListener;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.RequestVehInspection;
import com.wisedrive.dealerapp1.SoldVehDetails;
import com.wisedrive.dealerapp1.VehicleImages;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoActVehlist;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoNewVehImgs;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterAllCarPage extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    double final_amount;
    int x;
    private DecimalFormat IndianCurrencyFormat;
    public String finalpricefrom,finalpriceto;
    private String kdriven,kdrivenfrom,kdrivento,actualprice;
    ArrayList<PojoAllCarsList> allCarsPages = new ArrayList();
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private Activity activity;
    public ProgressBar progressBar;
    ArrayList<PojoNewVehImgs> pojoNewVehImgs=new ArrayList<>();
    private OnLoadMoreListener onLoadMoreListener;
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public AdapterAllCarPage(Activity activity, ArrayList<PojoAllCarsList> allCarsPages, RecyclerView recyclerView)
    {
        this.activity = activity;
        this.allCarsPages = allCarsPages;
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
        return allCarsPages.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return allCarsPages == null ? 0 : allCarsPages.size();
    }
    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.items_new_all_cars_list, null);
            return new RecyclerViewHolder(view) {
            };
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderView, @SuppressLint("RecyclerView") int position) {

        if (holderView instanceof RecyclerViewHolder)
        {
            final RecyclerViewHolder holder = (RecyclerViewHolder) holderView;
            IndianCurrencyFormat=new DecimalFormat("##,##,###");
            PojoAllCarsList recyclerdata=allCarsPages.get(position);

            pojoNewVehImgs.clear();

            for (int i = 0; i < recyclerdata.getVehicleImages().length(); i++) {
                JSONObject apartment = null;
                try {
                    apartment = recyclerdata.getVehicleImages().getJSONObject(i);

                    PojoNewVehImgs carobj = new PojoNewVehImgs(apartment);
                    pojoNewVehImgs.add(carobj);
                  //  Toast.makeText(activity,"called" +String.valueOf( pojoNewVehImgs.size()),Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(pojoNewVehImgs.size()==0){
                //Toast.makeText(activity,"empty",Toast.LENGTH_SHORT).show();
                holder.rl_photos.setVisibility(View.INVISIBLE);
            }
            else{
                holder.rl_photos.setVisibility(View.VISIBLE);
                if(pojoNewVehImgs.size()==1){
                    holder.iv1.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(pojoNewVehImgs.get(0).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv1);
                    holder.iv2.setVisibility(View.GONE);
                    holder.iv3.setVisibility(View.GONE);
                    holder.tv_exta_img.setVisibility(View.GONE);
                }else{
                    if(pojoNewVehImgs.size()==2){
                        holder.iv1.setVisibility(View.VISIBLE);
                        holder.iv2.setVisibility(View.VISIBLE);
                        Glide.with(activity).load(pojoNewVehImgs.get(0).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv1);
                        Glide.with(activity).load(pojoNewVehImgs.get(1).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv2);
                        holder.iv3.setVisibility(View.GONE);
                        holder.tv_exta_img.setVisibility(View.GONE);
                    }
                    else if(pojoNewVehImgs.size()==3){
                        holder.iv1.setVisibility(View.VISIBLE);
                        holder.iv2.setVisibility(View.VISIBLE);
                        holder.iv3.setVisibility(View.VISIBLE);
                        Glide.with(activity).load(pojoNewVehImgs.get(0).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv1);
                        Glide.with(activity).load(pojoNewVehImgs.get(1).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv2);
                        Glide.with(activity).load(pojoNewVehImgs.get(2).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv3);
                        holder.tv_exta_img.setVisibility(View.GONE);
                    }else{
                        holder.iv1.setVisibility(View.VISIBLE);
                        holder.iv2.setVisibility(View.VISIBLE);
                        holder.iv3.setVisibility(View.VISIBLE);
                        Glide.with(activity).load(pojoNewVehImgs.get(0).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv1);
                        Glide.with(activity).load(pojoNewVehImgs.get(1).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv2);
                        Glide.with(activity).load(pojoNewVehImgs.get(2).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.iv3);
                        holder.tv_exta_img.setVisibility(View.VISIBLE);
                        holder.tv_exta_img.setText("+"+(pojoNewVehImgs.size()-3));
                    }
                }
                //if size of that array is equals to 1
                //
            }

            if(SPHelper.title.equals("Approved Vehicles With Cooling Period")){
                holder.linear_cp.setVisibility(View.VISIBLE);
                holder.rl_cp_comments.setVisibility(View.VISIBLE);
                holder.tv_cp_days.setText(recyclerdata.getCooling_period_days());
                holder.tv_cp_kms.setText(recyclerdata.getCooling_period_kms());
                if(recyclerdata.getCooling_period_comments()==null||
                recyclerdata.getCooling_period_comments().equals("null")){
                    holder.tv_cp_comments.setText("");
                }else{
                    holder.tv_cp_comments.setText(recyclerdata.getCooling_period_comments());
                }

             }else{
                holder.linear_cp.setVisibility(View.GONE);
                holder.rl_cp_comments.setVisibility(View.GONE);
            }
            //insp status
            if(recyclerdata.getInspection_warranty_status()==null||recyclerdata.getInspection_warranty_status().equals("null"))
            {
                if(recyclerdata.getInspection_status_by_mechanic()==null||recyclerdata.getInspection_status_by_mechanic().equals("null")){
                    holder.tv_insp_on_date.setText("Not Inspected");
                }
                else if(recyclerdata.getInspection_status_by_mechanic().equalsIgnoreCase("completed")){
                    holder.tv_insp_on_date.setText("In Review");
                }
                else{

                    String s1=allCarsPages.get(position).getInspection_status_by_mechanic();
                    String rs1=s1.substring(0,1).toUpperCase()+s1.substring(1).toLowerCase();
                    holder.tv_insp_on_date.setText(rs1);
                }
            }
            else{
                String s1=allCarsPages.get(position).getInspection_warranty_status();
                String rs1=s1.substring(0,1).toUpperCase()+s1.substring(1).toLowerCase();
                holder.tv_insp_on_date.setText(rs1);
            }

            //if page is coming from sold,customer and remaining
            if (SPHelper.comingfrom.equals("sold"))
            {
                holder.tv_last_insp.setText(Common.getDateFromString(recyclerdata.getPackage_sold_on()));
                holder.iv_edit.setVisibility(View.INVISIBLE);
                holder.rl_cv.setBackground(AppCompatResources.getDrawable(activity,R.drawable.cv_allcars));
                holder.rl_cv.setBackgroundTintList(AppCompatResources.getColorStateList(activity,R.color.bg_location));
                holder.rl_sold_label.setVisibility(View.VISIBLE);
                holder.tv_inspect_status.setVisibility(View.GONE);
            }else if(SPHelper.comingfrom.equals("customer"))
            {
                holder.tv_last_insp.setText(Common.getDateFromString(recyclerdata.getPackage_sold_on()));
                holder.iv_edit.setVisibility(View.INVISIBLE);
                holder.rl_cv.setBackground(AppCompatResources.getDrawable(activity,R.drawable.cv_allcars));
                holder.rl_cv.setBackgroundTintList(AppCompatResources.getColorStateList(activity,R.color.bg_location));
                holder.tv_cust_name.setTextColor(ContextCompat.getColorStateList(activity, R.color.white));
                holder.rl_sold_label.setVisibility(View.VISIBLE);
                holder.rl_sold_label.setBackground(AppCompatResources.getDrawable(activity,R.drawable.cv_soldcars));
                holder.rl_sold_label.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.black));
                holder.tv_inspect_status.setVisibility(View.GONE);
            }
            else {
                if(recyclerdata.getInspection_date()==null||recyclerdata.getInspection_date().equals("null")||
                        recyclerdata.getInspection_date().equals("")){

                    holder.tv_last_insp.setText("");
                }else{

                    holder.tv_last_insp.setText(Common.getDateFromString(recyclerdata.getInspection_date()));
                }
                holder.iv_edit.setVisibility(View.VISIBLE);

                holder.rl_sold_label.setVisibility(View.GONE);
                if(SPHelper.title.equals("Repair Request"))
                {
                    holder.rl_cv.setBackground(AppCompatResources.getDrawable(activity,R.drawable.cv_allcars));
                    holder.rl_cv.setBackgroundTintList(AppCompatResources.getColorStateList(activity,R.color.bg_location));
                    holder.rl_rep_req.setVisibility(View.VISIBLE);
                    }
                else{
                    holder.rl_cv.setBackground(AppCompatResources.getDrawable(activity,R.drawable.cv_all_cars));
                    holder.rl_cv.setBackgroundTintList(AppCompatResources.getColorStateList(activity,R.color.bg_location));
                    holder.rl_rep_req.setVisibility(View.GONE);
                    }

                if(SPHelper.title.equals("Expired Inspection List")){
                    holder.tv_inspect_status.setVisibility(View.VISIBLE);
                }else{
                    holder.tv_inspect_status.setVisibility(View.GONE);
                }

            }
            //insp status
          //  holder.tv_insp_on_date.setBackground(AppCompatResources.getDrawable(activity,R.drawable.cardview_act_code));
            if(holder.tv_insp_on_date.getText().equals("Requested"))
            {
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.black));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.black));
               // holder.tv_last_insp.setTextColor(ContextCompat.getColorStateList(activity, R.color.req_clr));
            }
            else if(holder.tv_insp_on_date.getText().equals("Not Inspected")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
               // holder.tv_last_insp.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
            }
            else if(holder.tv_insp_on_date.getText().equals("Reinspect")||holder.tv_insp_on_date.getText().equals("Reinspection")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
              //  holder.tv_last_insp.setTextColor(ContextCompat.getColorStateList(activity, R.color.re_insp_clr));
            }
            else if(holder.tv_insp_on_date.getText().equals("Repair requested")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
               // holder.tv_last_insp.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
            }
            else if(holder.tv_insp_on_date.getText().equals("Expired")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
              //  holder.tv_last_insp.setTextColor(ContextCompat.getColorStateList(activity, R.color.rep_req_clr));
                holder.tv_insp_on_date.setText("EXPIRED");
                holder.tv_inspect_status.setText("Req. Inspection");
            }
            else if(holder.tv_insp_on_date.getText().equals("In Review")){
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.req_clr));
               // holder.tv_last_insp.setTextColor(ContextCompat.getColorStateList(activity, R.color.req_clr));
            }
            else{
                //approved
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.app_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(activity, R.color.app_clr));
               // holder.tv_last_insp.setTextColor(ContextCompat.getColorStateList(activity, R.color.app_clr));
            }

            //edit
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
            //insp date

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
                holder.tv_kmsdriven.setText(kdriven);
            }
            //fuel
            holder.tv_fueltype.setText(recyclerdata.getFuel_type());

            //year
            if(recyclerdata.getManufacturing_year()==null||recyclerdata.getManufacturing_year().equals("null")){
                holder.tv_makeyear.setVisibility(View.INVISIBLE);

            }else{
                holder.tv_makeyear.setText(recyclerdata.getManufacturing_year());
            }

            //trans type

            if(recyclerdata.getTransmission_type()==null||recyclerdata.getTransmission_type().equals("null")){
                holder.tv_trans.setVisibility(View.INVISIBLE);

            }else{
                holder.tv_trans.setText(recyclerdata.getTransmission_type());
            }
            //insurance  --bank name ?

            if(recyclerdata.getInsurance_provider()==null||recyclerdata.getInsurance_provider().equals("null")){
                holder.tv_insu.setVisibility(View.INVISIBLE);

            }else{
                holder.tv_insu.setText(recyclerdata.getInsurance_provider());
            }

            //customer name
            if(recyclerdata.getCustomer_name()==null||recyclerdata.getCustomer_name().equals("null")){
                holder.tv_cust_name.setVisibility(View.INVISIBLE);

            }else{
                holder.tv_cust_name.setText(recyclerdata.getCustomer_name());
            }


            //veh is public or private ?

            if(recyclerdata.getIs_vehicle_public()==null){
                SPHelper.veh_ispublic="";
            }
            else if(recyclerdata.getIs_vehicle_public().equalsIgnoreCase("y"))
            {
                SPHelper.veh_ispublic=recyclerdata.getIs_vehicle_public();
            }


            holder.rl_sold_label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SPHelper.vehid=recyclerdata.getVehicle_id();
                    SPHelper.brandlogo=recyclerdata.getBrand_icon();
                    SPHelper.model_name=recyclerdata.getVehicle_model();
                    SPHelper.fueltype=recyclerdata.getFuel_type();
                    SPHelper.manufacture_year=recyclerdata.getManufacturing_year();
                    SPHelper.vehno=recyclerdata.getVehicle_no();
                    SPHelper.cool_no_ofdays=recyclerdata.getCooling_period_days();
                    SPHelper.cool_kms=recyclerdata.getCooling_period_kms();
                    SoldVehDetails bottomSheetDialogFragment = new SoldVehDetails();
                    bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                }
            });

            holder.rl_exp_veh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerdata.getInspection_status_by_mechanic().equalsIgnoreCase("requested")){
                        Common.CallToast(activity,"Vehicle is already requested for inspection",1);
                    }else{
                        SPHelper.vehno=recyclerdata.getVehicle_no();
                        SPHelper.goneto="expired";
                        Intent intent=new Intent(activity, RequestVehInspection.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                }
            });
            holder.rl_insp_req.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SPHelper.vehno=recyclerdata.getVehicle_no();
                    if(recyclerdata.getInspection_status_by_mechanic().equalsIgnoreCase("requested")){
                        Common.CallToast(activity,"Vehicle is already requested for inspection",1);
                    }else{
                        SPHelper.vehno=recyclerdata.getVehicle_no();
                        SPHelper.goneto="repair";
                        Intent intent=new Intent(activity, RequestVehInspection.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                }
            });
            holder.rl_photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SPHelper.vehid=recyclerdata.getVehicle_id();

                    SPHelper.veh_imgs.clear();
                    for (int i = 0; i < recyclerdata.getVehicleImages().length(); i++) {
                        JSONObject apartment = null;
                        try {
                            apartment = recyclerdata.getVehicleImages().getJSONObject(i);
                            PojoNewVehImgs carobj = new PojoNewVehImgs(apartment);
                            SPHelper.veh_imgs.add(carobj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    VehicleImages bottomSheetDialogFragment1 = new VehicleImages();
                    bottomSheetDialogFragment1.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment1.getTag());

                }
            });

            holder.rl_reasons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllCarsPage.getInstance().comments.setText(recyclerdata.getInspection_comments());
                    AllCarsPage.getInstance().dialog.show();
                }
            });

            holder.tv_inspect_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      if(SPHelper.title.equals("Expired Inspection List"))
                      {
                          if(recyclerdata.getInspection_status_by_mechanic().equalsIgnoreCase("requested")){
                              Common.CallToast(activity,"Vehicle is already requested for inspection",1);
                          }else{
                              SPHelper.vehno=recyclerdata.getVehicle_no();
                              SPHelper.goneto="expired";
                              Intent intent=new Intent(activity, RequestVehInspection.class);
                              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              activity.startActivity(intent);
                          }
                      }else{

                      }
                }
            });
        }
        else if (holderView instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holderView;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_insp_on_date,tv_cust_name,tv_exta_img;
        ImageView iv_edit,iv1,iv2,iv3;
        RelativeLayout rl_sold_label,rl_cv,rl_photos,rl_insp_req,rl_rep_req,rl_exp_veh;
        public TextView tv_make_model,tv_insu;
        ImageView brand_logo,transmission_type,iv_more,iv_haswarranty,iv_nowarranty;
        TextView tv_makeyear,tv_fueltype,tv_kmsdriven,tv_carno,tv_cp_comments,
                tv_inspect_status,no_kms,no_days,tv_trans,tv_cp_kms,tv_cp_days,
                label_listingprice, tv_last_insp,tv_label_transmissiontype;
        RelativeLayout rl_reasons,rl_insp_status,rl_cp_comments,rl_last_insp;
        LinearLayout linear_cp;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_last_insp=itemView.findViewById(R.id.rl_last_insp);
            rl_reasons=itemView.findViewById(R.id.rl_reasons);
            rl_exp_veh=itemView.findViewById(R.id.rl_exp_veh);
            rl_rep_req=itemView.findViewById(R.id.rl_rep_req);
            rl_insp_req=itemView.findViewById(R.id.rl_insp_req);
            linear_cp=itemView.findViewById(R.id.linear_cp);
            tv_exta_img=itemView.findViewById(R.id.tv_exta_img);
            iv1=itemView.findViewById(R.id.iv1);
            iv2=itemView.findViewById(R.id.iv2);
            iv3=itemView.findViewById(R.id.iv3);
            tv_insp_on_date=itemView.findViewById(R.id.tv_insp_on_date);
            rl_sold_label=itemView.findViewById(R.id.rl_sold_label);
            rl_cv=itemView.findViewById(R.id.rl_cv);
            iv_edit=itemView.findViewById(R.id.iv_edit);
            rl_photos=itemView.findViewById(R.id.rl_photos);
            tv_cust_name=itemView.findViewById(R.id.tv_cust_name);
            brand_logo=itemView.findViewById(R.id.brand_logo);
            tv_last_insp=itemView.findViewById(R.id.tv_last_insp);
            tv_make_model=itemView.findViewById(R.id.tv_make_model);
            tv_makeyear=itemView.findViewById(R.id.tv_makeyear);
            tv_insu=itemView.findViewById(R.id.tv_insu);
            tv_kmsdriven=itemView.findViewById(R.id.tv_kmsdriven);
            tv_fueltype=itemView.findViewById(R.id.tv_fueltype);
            tv_carno=itemView.findViewById(R.id.tv_carno);
            tv_inspect_status=itemView.findViewById(R.id.tv_inspect_status);
            tv_trans=itemView.findViewById(R.id.tv_trans);
            tv_cp_kms=itemView.findViewById(R.id.tv_cp_kms);
            tv_cp_days=itemView.findViewById(R.id.tv_cp_days);
            rl_cp_comments=itemView.findViewById(R.id.rl_cp_comments);
            tv_cp_comments=itemView.findViewById(R.id.tv_cp_comments);
        }
    }

    public int getScreenWidth()
    {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public void setimage(){


    }
}
