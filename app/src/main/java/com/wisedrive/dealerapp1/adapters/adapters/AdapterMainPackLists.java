package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojOurServices;
import com.wisedrive.dealerapp1.pojos.pojos.PojoMainPackLists;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSubPackList;

import java.util.ArrayList;

public class AdapterMainPackLists extends RecyclerView.Adapter<AdapterMainPackLists.RecyclerViewHolder> {

    ArrayList<PojoMainPackLists> pojoMainPackLists;
    Context context;
    AdapterSubPackList adapterSubPackList;
    ArrayList<PojoSubPackList> pojoSubPackLists;

    ArrayList<PojOurServices> pojOurServices;
    AdapterOurServices adapterOurServices;
    public AdapterMainPackLists(ArrayList<PojoMainPackLists> pojoMainPackLists, Context context) {
        this.pojoMainPackLists = pojoMainPackLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMainPackLists.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_main_packages_list, parent, false);
        view.getLayoutParams().width = (int) (getScreenWidth()/1.2);
        return new RecyclerViewHolder(view);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull AdapterMainPackLists.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        pojoSubPackLists=new ArrayList<>();
        pojoSubPackLists=pojoMainPackLists.get(position).getPackCombList();
        adapterSubPackList=new AdapterSubPackList(context,pojoSubPackLists);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.rv_veh_category.setLayoutManager(layoutManager);
        holder.rv_veh_category.setAdapter(adapterSubPackList);
        Glide.with(context).load(pojoMainPackLists.get(position).getPackage_logo()).
                placeholder(R.drawable.icon_noimage).into(holder.iv_buy_warranty);

        //if istarter is y,then packnmae is Starter Pack,label2 is display name
        //then normal
        //
       if( pojoMainPackLists.get(position).getIs_starter_pack().equalsIgnoreCase("y")){
           holder.label2.setText(pojoMainPackLists.get(position).getDisplay_name());
           holder.pack_name.setText("Starter Pack");

       }else{
           holder.label2.setText(pojoMainPackLists.get(position).getPackage_type()+" "+"Pack");
           holder.pack_name.setText(pojoMainPackLists.get(position).getDisplay_name());

       }

       //if quantuty is 1,then
//        if(pojoMainPackLists.get(position).getPackage_id().equals("9")){
//            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient_main_pack));
//        }else if(pojoMainPackLists.get(position).getPackage_id().equals("11")){
//            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_shroom));
//        }else if(pojoMainPackLists.get(position).getPackage_id().equals("15")){
//            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient2));
//        }else if(pojoMainPackLists.get(position).getPackage_id().equals("4")){
//            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient3));
//        }else{
//            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient4));
//        }

        if(pojoMainPackLists.get(position).getStart_color().equals("e233ff")){
            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_shroom));
        }else if(pojoMainPackLists.get(position).getStart_color().equals("402565")){
            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient2));
        }else if(pojoMainPackLists.get(position).getStart_color().equals("0b63f6")){
            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient4));
        }else if(pojoMainPackLists.get(position).getStart_color().equals("ff0076")){
            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient_main_pack));
        }else if(pojoMainPackLists.get(position).getStart_color().equals("000066")){
            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient3));
        }else{
            holder.rl_tabs.setBackground(context.getDrawable(R.drawable.cv_gradient5));
        }

        pojOurServices=new ArrayList<>();
        pojOurServices=pojoMainPackLists.get(position).getDescriptionList();
        adapterOurServices=new AdapterOurServices(pojOurServices,context);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.rv_our_services.setLayoutManager(layoutManager1);
        holder.rv_our_services.setAdapter(adapterOurServices);
    }

    @Override
    public int getItemCount() {
        return pojoMainPackLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rv_veh_category,rv_our_services;
        TextView pack_name,label2;
        RelativeLayout rl_tabs;
        ImageView iv_buy_warranty;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_veh_category=itemView.findViewById(R.id.rv_veh_category);
            rv_our_services=itemView.findViewById(R.id.rv_our_services);
            pack_name=itemView.findViewById(R.id.pack_name);
            rl_tabs=itemView.findViewById(R.id.rl_tabs);
            iv_buy_warranty=itemView.findViewById(R.id.iv_buy_warranty);
            label2=itemView.findViewById(R.id.label2);
        }
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
