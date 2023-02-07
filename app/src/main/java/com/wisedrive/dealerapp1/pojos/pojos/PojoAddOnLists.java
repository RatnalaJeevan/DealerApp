package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAddOnLists {


    @SerializedName("validity_days")
    String validity_days;
    @SerializedName("addon_name")
    String addon_name;
    @SerializedName("addon_logo")
    String addon_logo;
    @SerializedName("long_description")
    String long_description;
    @SerializedName("final_price")
    String final_price;
    @SerializedName("addon_id")
    String addon_id;

    public String getValidity_days() {
        return validity_days;
    }

    public String getAddon_name() {
        return addon_name;
    }

    public String getAddon_logo() {
        return addon_logo;
    }

    public String getLong_description() {
        return long_description;
    }

    public String getFinal_price() {
        return final_price;
    }

    public String getAddon_id() {
        return addon_id;
    }
}
