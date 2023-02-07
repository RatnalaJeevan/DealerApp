package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.wisedrive.dealerapp1.R;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter {
    ArrayList<PojoVehicleImageList> vehicleImageLists;
    Context context;
    public ImageView car_image_position;
    public TextView car_image_position_name;
    public RelativeLayout rl_whole;

    public SlideAdapter(ArrayList<PojoVehicleImageList> vehicleImageLists, Context context) {
        this.vehicleImageLists = vehicleImageLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return vehicleImageLists.size();
    }
    @Override
    public void destroyItem(ViewGroup itemView, int position, Object view) {
        itemView.removeView( (View)view);
    }
    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == ( object);
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup itemView, int position) {
        View viewLayout = LayoutInflater.from(context).inflate(R.layout.items_show_veh_images, itemView, false);

        car_image_position_name=viewLayout.findViewById(R.id.car_image_position_name);
        car_image_position=(ZoomageView)viewLayout.findViewById(R.id.car_image_position);
        rl_whole=viewLayout.findViewById(R.id.rl_whole);
        PojoVehicleImageList recyclerdata=vehicleImageLists.get(position);

        if(vehicleImageLists.get(position).getVehicle_images().equals("")){
            rl_whole.setVisibility(View.GONE);
        }else{
            rl_whole.setVisibility(View.VISIBLE);
            car_image_position_name.setText(recyclerdata.getImage_name());
            if (vehicleImageLists.get(position).getVehicle_images() != null && !vehicleImageLists.get(position).getVehicle_images().isEmpty() && !vehicleImageLists.get(position).getVehicle_images().equals("null")) {
                Glide.with(context).load(vehicleImageLists.get(position).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(car_image_position);
            }
        }
        itemView.addView(viewLayout);
        return viewLayout;
    }
}
