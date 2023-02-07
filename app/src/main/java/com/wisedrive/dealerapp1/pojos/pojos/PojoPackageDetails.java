package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoPackageDetails {
    @SerializedName("percentage_amount_saved")
    String percentage_amount_saved;
    @SerializedName("bundle_name")
    String bundle_name;
    @SerializedName("quantity")
    String quantity;
    @SerializedName("main_package_id")
    String main_package_id;
    @SerializedName("main_package_name")
    String main_package_name;
    String isselected="n";
   


  

   

    public String getIsselected() {
        return isselected;
    }

    public void setIsselected(String isselected) {
        this.isselected = isselected;
    }
}
