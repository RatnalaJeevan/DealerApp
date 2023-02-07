package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoSubPackList {

    @SerializedName("sub_package_name")
    String sub_package_name;
    @SerializedName("quantity")
    String quantity;
    @SerializedName("sub_package_id")
    String sub_package_id;
    @SerializedName("main_package_id")
    String main_package_id;
    @SerializedName("final_price")
    String final_price;
    @SerializedName("percentage_amount_saved")
    String percentage_amount_saved;
    @SerializedName("package_id")
    String package_id;

    String isSelected="n";

    public String getPackage_id() {
        return package_id;
    }

    public String getMain_package_id() {
        return main_package_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPercentage_amount_saved() {
        return percentage_amount_saved;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public String getSub_package_id() {
        return sub_package_id;
    }

    public String getSub_package_name() {
        return sub_package_name;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setSub_package_name(String sub_package_name) {
        this.sub_package_name = sub_package_name;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
