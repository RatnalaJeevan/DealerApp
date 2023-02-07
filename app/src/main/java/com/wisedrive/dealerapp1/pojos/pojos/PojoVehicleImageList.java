package com.wisedrive.dealerapp1.pojos.pojos;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class PojoVehicleImageList {
    @SerializedName("image_name")
    String image_name;
    @SerializedName("vehicle_images")
    String vehicle_images;
    @SerializedName("id")
    String id;
    private Uri image = null;
    private String filename = "";
    private String imageurl = "";
    private Bitmap bitmap;

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_images() {
        return vehicle_images;
    }

    public void setVehicle_images(String vehicle_images) {
        this.vehicle_images = vehicle_images;
    }
}
