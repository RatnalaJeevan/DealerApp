package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoSample {


    String isSelected="n";

    @SerializedName("warranty_status_id")
    String warranty_status_id;
    @SerializedName("warranty_status")
    String warranty_status;

    public String getWarranty_status_id() {
        return warranty_status_id;
    }

    public String getWarranty_status() {
        return warranty_status;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }


}
