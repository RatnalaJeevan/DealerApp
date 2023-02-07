package com.wisedrive.dealerapp1.pojos.pojos;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

public class PojoAllCarBrands {
    @SerializedName("brand_icon")
    private String brand_icon;
    @SerializedName("id")
    private String id;
    @SerializedName("count")
    private String count;
    @SerializedName("status_id")
    private String status_id;
    @SerializedName("car_brand")
    private String car_brand;

    @SerializedName("brand_id")
    private String brand_id;
    private String is_Selected="n";
    @SerializedName("min_odometer")
    private String min_odometer;
    @SerializedName("max_sold_price")
    private double max_sold_price;
    @SerializedName("max_odometer")
    private String max_odometer;
    @SerializedName("min_sold_price")
    private String min_sold_price;

    public String getStatus_id() {
        return status_id;
    }

    public String getMin_odometer() {
        return min_odometer;
    }

    public double getMax_sold_price() {
        return max_sold_price;
    }

    public String getMax_odometer() {
        return max_odometer;
    }

    public String getMin_sold_price() {
        return min_sold_price;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getIs_Selected() {
        return is_Selected;
    }

    public void setIs_Selected(String is_Selected) {
        this.is_Selected = is_Selected;
    }

    public String getBrand_icon() {
        return brand_icon;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public String getCount() {
        return count;
    }

}
