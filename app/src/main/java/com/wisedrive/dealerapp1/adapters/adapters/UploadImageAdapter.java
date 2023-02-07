package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCarImageList;

import java.util.ArrayList;

public class UploadImageAdapter extends BaseAdapter {
    ArrayList<PojoCarImageList> carImageLists;
    Context context;
    public ImageView uploaded_image;

    public UploadImageAdapter(ArrayList<PojoCarImageList> carImageLists, Context context) {
        this.carImageLists = carImageLists;
        this.context = context;
    }
    @Override
    public int getCount() {
        return carImageLists.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridView;
        if (convertView == null) {
            gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_imagelist, null);

        } else {
            gridView =  convertView;
        }
        uploaded_image =  gridView.findViewById(R.id.car_image_position);
        TextView tv_name =  gridView.findViewById(R.id.car_image_position_name);
        tv_name.setText(carImageLists.get(i).getName());
        if (carImageLists.get(i).getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            uploaded_image.setImageURI(carImageLists.get(i).getImage());
        }
        return gridView;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
