package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoExpVehList {


    @SerializedName("vehicle_count")
    String vehicle_count;
     @SerializedName("day_count")
    String day_count;
     @SerializedName("expiry_list_id")
    String expiry_list_id;

    public String getVehicle_count() {
        return vehicle_count;
    }

    public String getDay_count() {
        return day_count;
    }

    public String getExpiry_list_id() {
        return expiry_list_id;
    }
}
