package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.PojOurServices;

import java.util.ArrayList;

public class AdapterOurServices extends RecyclerView.Adapter<AdapterOurServices.RecyclerViewHolder> {

    ArrayList<PojOurServices> pojOurServices;
    Context context;

    public AdapterOurServices(ArrayList<PojOurServices> pojOurServices, Context context) {
        this.pojOurServices = pojOurServices;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterOurServices.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View  v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_our_services,parent,false);
       return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOurServices.RecyclerViewHolder holder, int position) {
        holder.label_servicetype.setText(pojOurServices.get(position).getPackage_discription());
    }

    @Override
    public int getItemCount() {
        return pojOurServices.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView label_servicetype;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            label_servicetype=itemView.findViewById(R.id.label_servicetype);
        }
    }
}
