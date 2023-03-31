package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.wisedrive.dealerapp1.ExpiringVehList;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoExpVehList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterExpVehList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<PojoExpVehList> pojoExpVehLists;
    Context context;

    public AdapterExpVehList(ArrayList<PojoExpVehList> pojoExpVehLists, Context context) {
        this.pojoExpVehLists = pojoExpVehLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_exp_veh_list, parent, false);
        view.getLayoutParams().width = (int) (getScreenWidth()/1.7);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderView, int position) {

        final MyViewHolder holder = (MyViewHolder) holderView;
        PojoExpVehList recylerdata=pojoExpVehLists.get(position);
        holder.content.setText("Inspections expiring in "+recylerdata.getDay_count()+"days");
        holder.tv_count_exp_veh_list.setText(recylerdata.getVehicle_count());
//        if(recylerdata.getExpiry_list_id().equals("1")||recylerdata.getExpiry_list_id().equals("3")){
//            holder.tv_count_exp_veh_list.setTextColor(Color.parseColor("#696df6"));
//            holder.tv_count_exp_veh_list.setBackground(context.getDrawable(R.drawable.circle_white));
//            holder.tv_count_exp_veh_list.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color1));
//            holder.rl_buy_requests.setBackground(context.getDrawable(R.drawable.cv_new_car));
//           // holder.rl_buy_requests.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color2));
//
//        }else if(recylerdata.getExpiry_list_id().equals("2")){
//            holder.tv_count_exp_veh_list.setTextColor(Color.parseColor("#cc70d8"));
//            holder.tv_count_exp_veh_list.setBackground(context.getDrawable(R.drawable.circle_white));
//            holder.tv_count_exp_veh_list.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color3));
//            holder.rl_buy_requests.setBackground(context.getDrawable(R.drawable.cv_gradient1));
//            //holder.rl_buy_requests.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color4));
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recylerdata.getVehicle_count()==null||recylerdata.getVehicle_count().equals("0")){
                    Common.CallToast(context,"No Cars to show",1);
                }else{
                    SPHelper.exp_vehid=recylerdata.getExpiry_list_id();
                    Intent intent=new Intent(context.getApplicationContext(), ExpiringVehList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoExpVehLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class  MyViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tv_count_exp_veh_list,content;
        CardView rl_buy_requests;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.content);
            tv_count_exp_veh_list=itemView.findViewById(R.id.tv_count_exp_veh_list);
            rl_buy_requests=itemView.findViewById(R.id.rl_buy_requests);
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
