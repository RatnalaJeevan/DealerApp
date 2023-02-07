package com.wisedrive.dealerapp1.pojos.pojos;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class PojoCarImageList {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    private Uri image = null;
    private  String filename = "";
    private  String imageurl = "";
    private Bitmap bitmap;


    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
