package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;
import com.wisedrive.dealerapp1.pojos.pojos.pojo_static_image_data;

import java.util.ArrayList;

public class Adapter_static_images extends RecyclerView.Adapter<Adapter_static_images.MyViewHolder> {
    Context context;
    private final int GALLERY_REQ_CODE = 1000;
    private final int CAMERA_REQ_CODE = 100;
    int selectedPosition = RecyclerView.NO_POSITION;
    View view;
    ArrayList<Pojo_part_list> pojo_part_listArrayList;

    public Adapter_static_images(Context context, ArrayList<Pojo_part_list>pojo_part_listArrayList) {
        this.context = context;
        this. pojo_part_listArrayList = pojo_part_listArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_image_upload, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_part_list list =pojo_part_listArrayList.get(position);
        holder.setCar_image_position(list.getImage());
        holder.car_image_position_name.setText(list.getPart_name());
        holder.rl_car_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                showPhotoDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo_part_listArrayList.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView car_image_position;
        TextView car_image_position_name;
        RelativeLayout rl_car_images;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image_position = itemView.findViewById(R.id.car_image_position);
            car_image_position_name = itemView.findViewById(R.id.car_image_position_name);
            rl_car_images = itemView.findViewById(R.id.rl_car_images);
        }

        public void setCar_image_position(String car_image_position_path) {
            if (car_image_position_path != null && !car_image_position_path.isEmpty()) {
                // Clear the Glide cache
                Glide.with(context).clear(car_image_position);
                // Load the image into the ImageView using a library like Glide
                Glide.with(context).load(car_image_position_path).into(car_image_position);
            } else {
                Log.d("Adapter_static_images", "car_image_position_path is empty");
                // Clear the Glide cache
                Glide.with(context).clear(car_image_position);
                // Set the default image
                car_image_position.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.upload_symbol));
            }
        }

    }

    public void showPhotoDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.image_upload_options_pop);
        TextView cancel = dialog.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView textView1 = dialog.findViewById(R.id.takefromgallery);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromGallery();
                dialog.cancel();
            }
        });

        TextView textView = dialog.findViewById(R.id.Recapture);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromCamera();
                dialog.cancel();
            }
        });


        dialog.show();
    }

    private void takePictureFromGallery() {
        Intent picphoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(picphoto, GALLERY_REQ_CODE);
    }

    private void takePictureFromCamera() {
        Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) context).startActivityForResult(takepicture, CAMERA_REQ_CODE);
    }



   }


