package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_leads_page;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_leads_page extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ProgressBar progressBar;
    Context context;
    ArrayList<Pojo_leads_page> pojo_leads_pageArrayList;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 0;
    private static final int ITEM = 1;

    public Adapter_leads_page (Context context) {
        this.context = context;
        pojo_leads_pageArrayList =new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.items_leads_page, parent, false);
                viewHolder = new MyViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {

        switch (getItemViewType(position))
        {
            case ITEM:
                final MyViewHolder holder = (MyViewHolder) holder1;
                Pojo_leads_page list = pojo_leads_pageArrayList.get(position);

                if(list.getLead_name()==null||list.getLead_name().equals("")){
                     holder.name.setVisibility(View.GONE);
                }else {
                     holder.name.setVisibility(View.VISIBLE);
                     holder.name.setText(list.getLead_name());
                }
                holder.phone_number.setText(list.getLead_phone_no());
                holder.text_date.setText(Common.getDateFromString(list.getCreated_on_date()));
                holder.time.setText(list.getCreated_on_time());

                holder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+list.getLead_phone_no()));
                        context.startActivity(callIntent);
                    }
                });

                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder1;
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return pojo_leads_pageArrayList == null ? 0 : pojo_leads_pageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pojo_leads_pageArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() throws JSONException {
        isLoadingAdded = true;
        add(new Pojo_leads_page());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pojo_leads_pageArrayList.size() - 1;
        Pojo_leads_page result = getItem(position);

        if (result != null) {
            pojo_leads_pageArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(Pojo_leads_page movie) {
        pojo_leads_pageArrayList.add(movie);
        notifyItemInserted(pojo_leads_pageArrayList.size() - 1);
    }

    public void addAll(ArrayList<Pojo_leads_page> moveResults) {
        for (Pojo_leads_page result : moveResults) {
            add(result);
        }
    }
    public Pojo_leads_page getItem(int position) {
        return pojo_leads_pageArrayList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone_number,text_date,time,text_am_pm;
        ImageView imv_menu,call;
        AppCompatButton purchased;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            phone_number= itemView.findViewById(R.id.phone_number);
            text_date=itemView.findViewById(R.id.text_date);
            time=itemView.findViewById(R.id.time);
            text_am_pm=itemView.findViewById(R.id.text_am_pm);
            purchased=itemView.findViewById(R.id.purchased);
            imv_menu=itemView.findViewById(R.id.imv_menu);
            call=itemView.findViewById(R.id.call);
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


