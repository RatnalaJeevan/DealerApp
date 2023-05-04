package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_feature_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_leads_page;

import java.util.ArrayList;

public class Adapter_leads_page extends RecyclerView.Adapter<Adapter_leads_page.MyViewHolder> {
    Context context;
    View view;
    ArrayList<Pojo_leads_page> pojo_leads_pageArrayList;

    public Adapter_leads_page (Context context, ArrayList<Pojo_leads_page>pojo_leads_pageArrayList) {
        this.context = context;
        this.pojo_leads_pageArrayList =pojo_leads_pageArrayList;
    }

    @NonNull
    @Override
    public Adapter_leads_page.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_leads_page,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_leads_page.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_leads_page list = pojo_leads_pageArrayList.get(position);
        holder.name.setText(list.getName());
        holder.phone_number.setText(list.getPhone_number());
        holder.text_date.setText(list.getText_date());
        holder.time.setText(list.getTime());
        holder.text_am_pm.setText(list.getText_am_pm());

        if(position==0){
            holder.purchased.setVisibility(View.VISIBLE);

        }else{
            holder.purchased.setVisibility(View.GONE);

        }



     /*   holder.imv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.getMenuInflater().inflate(R.menu.leads_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_home:
                                // Handle the Home menu item click
                                return true;
                            case R.id.menu_profile:
                                // Handle the Profile menu item click
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return   pojo_leads_pageArrayList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone_number,text_date,time,text_am_pm;
        ImageView imv_menu;
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

        }



    }
}


