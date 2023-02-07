package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoSelectPack {

    @SerializedName("valid_to_date")
    String valid_to_date;
    @SerializedName("no_of_warranties_left")
    String no_of_warranties_left;
    @SerializedName("payment_date")
    String payment_date;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("display_name")
    String display_name;
    @SerializedName("dpp_id")
    String dpp_id;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("valid_days")
    String valid_days;
    @SerializedName("sub_package_name")
    String sub_package_name;
    String isSelected="n";

    public String getValid_days() {
        return valid_days;
    }

    public String getSub_package_name() {
        return sub_package_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getDpp_id() {
        return dpp_id;
    }

    public String getValid_to_date() {
        return valid_to_date;
    }

    public String getNo_of_warranties_left() {
        return no_of_warranties_left;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
