package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.AddNewCar;
import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.RequestVehInspection;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllPayments;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_New_req;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_colours;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_vehicle_status_list;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_new_reqlist  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context context;
    private View view;
    ArrayList<Pojo_New_req> pojo_new_reqArrayList;
    public ProgressBar progressBar;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private DecimalFormat IndianCurrencyFormat;

    public Adapter_new_reqlist( Context context) {
        pojo_new_reqArrayList=new ArrayList<>();
        this.context = context;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, @SuppressLint("RecyclerView") int position) {
        switch (getItemViewType(position))
        {
            case ITEM:
                final RecyclerViewHolder holder = (RecyclerViewHolder) holder1;
                Pojo_New_req list = pojo_new_reqArrayList.get(position);
                IndianCurrencyFormat=new DecimalFormat("##,##,###");
                holder.expiry_date.setText(Common.getDateFromString(list.getPosted_on_date()));
                  holder.tv_no_leads.setText(list.getCount_1()+" Leads");
                holder. tv_make_model.setText(list.getModel());
                holder. tv_price_range.setText("RS "+IndianCurrencyFormat.format((int)list.getPrice_range_from())+"-"+
                        IndianCurrencyFormat.format((int)list.getPrice_range_to()));
                holder.tv_kmsdriven.setText(IndianCurrencyFormat.format((int)list.getKms_from())+"-"+
                        IndianCurrencyFormat.format((int)list.getKms_to()));
                holder.tv_makeyear.setText(list.getYear_from()+"-"+list.getYear_to());
                holder.owner_no.setText(list.getNo_of_owners_from()+"-"+list.getNo_of_owners_to());

                if(list.getFuel_type().length()>7){
                    holder.tv_fuel_type.setText("Any");
                }else {
                    holder.tv_fuel_type.setText(list.getFuel_type());
                }

                if(list.getTransmission_type().length()>9){
                    holder.tv_trans_type.setText("Any");
                }else {
                    holder.tv_trans_type.setText(list.getTransmission_type());
                }

                if(list.getMatchingVehList().size()==0){
                    holder.tv_matching_count.setText(list.getMatchingVehList().size()+" Matching car, Add Car");
                }
                else if(list.getMatchingVehList().size()==1){
                    holder.tv_matching_count.setText(list.getMatchingVehList().size()+" Matching car");
                }
                else {
                    holder.tv_matching_count.setText(list.getMatchingVehList().size()+" Matching cars");
                }

                Glide.with(context).load(list.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(holder.brand_logo);

                holder.tv_color.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

                holder.rl_matching_car.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int matchingcount = list.getMatchingVehList().size();
                        if (matchingcount>0)
                        {
                            SPHelper.matc_cars=list.getMatchingVehList();
                            MainActivity.getInstance().rl_transperant_cars_list.setVisibility(View.VISIBLE);
                            MainActivity.getInstance().rl_cars_list.setVisibility(View.VISIBLE);
                            MainActivity.getInstance().card_btm_nav.setVisibility(View.GONE);
                            Adapter_vehicle_status_list adapter_vehicle_status_list =
                                    new Adapter_vehicle_status_list(context, SPHelper.matc_cars);
                            LinearLayoutManager linearLayoutManager = new
                                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            MainActivity.getInstance().rv_vehicle_status_list.setLayoutManager(linearLayoutManager);
                            MainActivity.getInstance().rv_vehicle_status_list.setAdapter(adapter_vehicle_status_list);

                        }
                        else
                        {
                            SPHelper.camefrom="add";
                            SPHelper.vehno="";
                            Intent intent = new Intent(context, AddNewCar.class);
                            if (context instanceof Activity) {
                                Activity activity = (Activity) context;
                                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_inup, R.anim.slide_outup);
                                ActivityCompat.startActivity(activity, intent, options.toBundle());
                            }
                        }
                    }

                });

                String csvString = list.getColor();

                String[] values = csvString.split(",");
                ArrayList<String> sel_clr=new ArrayList<>();
                for (String value : values) {
                    System.out.println(value);
                    sel_clr.add(value);
                }
                if(sel_clr.size()>1){
                    holder.tv_color.setText(sel_clr.get(0)+" +"+(sel_clr.size()-1));
                }else {
                    holder.tv_color.setText(sel_clr.get(0));
                }

                holder.l_color.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String csvString = pojo_new_reqArrayList.get(position).getColor();

                        String[] values = csvString.split(",");
                        ArrayList<String> sel_clr=new ArrayList<>();
                        SPHelper.sel_clr_list=new ArrayList<>();
                        for (String value : values)
                        {
                            System.out.println(value);
                            sel_clr.add(value);
                        }

                        if(sel_clr.size()>1)
                        {
                            System.out.println("value"+sel_clr);
                            SPHelper.sel_clr_list=sel_clr;
                            MainActivity.getInstance().rl_color_popup_transperant.setVisibility(View.VISIBLE);
                            MainActivity.getInstance().card_btm_nav.setVisibility(View.GONE);
                            Adapter_Colours  adapter_colours= new Adapter_Colours(context,SPHelper.sel_clr_list);
                            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            MainActivity.getInstance().rv_colors.setLayoutManager(linearLayoutManager3);
                            MainActivity.getInstance().rv_colors.setAdapter(adapter_colours);
                        }
                    }
                });

                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder1;
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.items_new_req_list, parent, false);
                viewHolder = new RecyclerViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;

    }


    @Override
    public int getItemCount() {
        return pojo_new_reqArrayList == null ? 0 : pojo_new_reqArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pojo_new_reqArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() throws JSONException {
        isLoadingAdded = true;
        add(new Pojo_New_req());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pojo_new_reqArrayList.size() - 1;
        Pojo_New_req result = getItem(position);

        if (result != null) {
            pojo_new_reqArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(Pojo_New_req movie) {
        pojo_new_reqArrayList.add(movie);
        notifyItemInserted(pojo_new_reqArrayList.size() - 1);
    }

    public void addAll(ArrayList<Pojo_New_req> moveResults) {
        for (Pojo_New_req result : moveResults) {
            add(result);
        }
    }

    public Pojo_New_req getItem(int position) {
        return pojo_new_reqArrayList.get(position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView expiry_date,tv_no_leads,tv_make_model,tv_price_range,
                tv_kmsdriven,tv_makeyear,owner_no,tv_fuel_type,tv_trans_type,tv_color,tv_matching_count;
        ImageView brand_logo;
        RelativeLayout rl_matching_car;
        LinearLayout l_color;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            l_color=itemView.findViewById(R.id.l_color);
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder
    {


        public LoadingViewHolder(View view) {
            super(view);
            progressBar =  view.findViewById(R.id.itemProgressbar);
        }
    }


}

