package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoActVehlist;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllPayments;
import com.wisedrive.dealerapp1.pojos.pojos.PojoPaymentHistory;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.json.JSONException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.time.temporal.Temporal;
import java.util.ArrayList;

public class AdapterAllPayments extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ProgressBar progressBar;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    ArrayList<PojoAllPayments> pojoAllPayments;
    Context context;
    private DealerApis apiInterface;
    private DecimalFormat IndianCurrencyFormat;
    ArrayList<PojoActVehlist> pojoActVehlists=new ArrayList<>();
    AdapterActVehList adapterActVehList;
    public AdapterAllPayments( Context context) {
       pojoAllPayments=new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.items_all_payments, parent, false);
                viewHolder = new RecyclerViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_all_payments,parent,false);
//        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        switch (getItemViewType(position))
        {
            case ITEM:
                final RecyclerViewHolder holder = (RecyclerViewHolder) holder1;
                PojoAllPayments recyclerdata=pojoAllPayments.get(position);
                apiInterface = ApiClient.getClient().create(DealerApis.class);
                IndianCurrencyFormat = new DecimalFormat("##,##,###");

                holder.main_pack.setText(recyclerdata.getMain_package_name()+" "+recyclerdata.getDisplay_name());
                holder.pac_sold_on.setText(recyclerdata.getPayment_date()+" - Rs. "+IndianCurrencyFormat.format((int)recyclerdata.getAmount()));
                holder.payment_mode.setText(recyclerdata.getPayment_mode());
                holder.sub_pack_name.setText("("+recyclerdata.getSub_package_name()+")");

                if(recyclerdata.getPackage_type().equals("Package")){
                    holder.service_type.setVisibility(View.GONE);
                }else{
                    holder.service_type.setVisibility(View.VISIBLE);
                    holder.service_type.setText(recyclerdata.getPackage_type());
                }

                pojoActVehlists=pojoAllPayments.get(position).getActivateVehList();
                if(pojoActVehlists.isEmpty()){
                    holder.rv_act_veh_list.setVisibility(View.GONE);
                }else{
                    holder.rv_act_veh_list.setVisibility(View.VISIBLE);
                    adapterActVehList=new AdapterActVehList(pojoActVehlists,context);
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 3);
                    holder.rv_act_veh_list.setLayoutManager(linearLayoutManager);
                    holder.rv_act_veh_list.setAdapter(adapterActVehList);
                }

                //adapterActVehList.notifyDataSetChanged();
                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder1;
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return pojoAllPayments == null ? 0 : pojoAllPayments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pojoAllPayments.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() throws JSONException {
        isLoadingAdded = true;
        add(new PojoAllPayments());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pojoAllPayments.size() - 1;
        PojoAllPayments result = getItem(position);

        if (result != null) {
            pojoAllPayments.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(PojoAllPayments movie) {
        pojoAllPayments.add(movie);
        notifyItemInserted(pojoAllPayments.size() - 1);
    }

    public void addAll(ArrayList<PojoAllPayments> moveResults) {
        for (PojoAllPayments result : moveResults) {
            add(result);
        }
    }

    public PojoAllPayments getItem(int position) {
        return pojoAllPayments.get(position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        public TextView main_pack, pac_sold_on,payment_mode,sub_pack_name,service_type,label_act_vehicles;
        RecyclerView rv_act_veh_list;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            pac_sold_on=itemView.findViewById(R.id.pac_sold_on);
            main_pack=itemView.findViewById(R.id.main_pack);
            rv_act_veh_list=itemView.findViewById(R.id.rv_act_veh_list);
            payment_mode=itemView.findViewById(R.id.payment_mode);
            sub_pack_name=itemView.findViewById(R.id.sub_pack_name);
            service_type=itemView.findViewById(R.id.service_type);
            label_act_vehicles=itemView.findViewById(R.id.label_act_vehicles);
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
