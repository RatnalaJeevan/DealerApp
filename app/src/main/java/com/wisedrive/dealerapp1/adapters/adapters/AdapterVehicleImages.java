package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;
import com.wisedrive.dealerapp1.R;
import java.util.ArrayList;

public class AdapterVehicleImages extends BaseAdapter
{
    ArrayList<PojoVehicleImageList> vehicleImageLists;
    Context context;
    public ImageView car_image_position;
    public TextView car_image_position_name;
    public RelativeLayout rl_whole;
    public AdapterVehicleImages(ArrayList<PojoVehicleImageList> c, Context context) {
        this.vehicleImageLists = vehicleImageLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return vehicleImageLists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridView;
        if (convertView == null) {
            gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_imagelist, null);

        } else {
            gridView = (View) convertView;
        }
        car_image_position =  gridView.findViewById(R.id.car_image_position);
        car_image_position_name =  gridView.findViewById(R.id.car_image_position_name);
        rl_whole=gridView.findViewById(R.id.rl_whole);
        car_image_position_name.setText(vehicleImageLists.get(i).getImage_name());
        if(SPHelper.comingfrom.equals("edit"))
        {
            if (vehicleImageLists.get(i).getVehicle_images() != null && !vehicleImageLists.get(i).getVehicle_images().isEmpty() && !vehicleImageLists.get(i).getVehicle_images().equals("null")) {
                Glide.with(context).load(vehicleImageLists.get(i).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(car_image_position);
            }
        }else{
            if(vehicleImageLists.get(i).getVehicle_images().equals("")){
                rl_whole.setVisibility(View.GONE);
            }else{
                rl_whole.setVisibility(View.VISIBLE);
                if (vehicleImageLists.get(i).getVehicle_images() != null && !vehicleImageLists.get(i).getVehicle_images().isEmpty() && !vehicleImageLists.get(i).getVehicle_images().equals("null")) {
                    Glide.with(context).load(vehicleImageLists.get(i).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(car_image_position);
                }
            }
        }

        if (vehicleImageLists.get(i).getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            car_image_position.setImageURI(vehicleImageLists.get(i).getImage());
        }
        if(vehicleImageLists.get(i).getId()==null
                ||vehicleImageLists.get(i).getId().equals("")){
            SPHelper.veh_fnt_img="";

        }else if(vehicleImageLists.get(i).getId().equals("16")){
            SPHelper.veh_fnt_img=vehicleImageLists.get(i).getVehicle_images();
        }else{
            SPHelper.veh_fnt_img="";
        }
        return gridView;
    }
}
