package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAddOnComboList {


    @SerializedName("sub_package_id")
    String sub_package_id;
    @SerializedName("bundle_name")
    String bundle_name;
    @SerializedName("original_price")
    String original_price;
    @SerializedName("amount_saved")
    String amount_saved;
    @SerializedName("quantity")
    String quantity;
    @SerializedName("percentage_amount_saved")
    String percentage_amount_saved;
    @SerializedName("final_price")
    String final_price;
    @SerializedName("main_package_id")
    String main_package_id;
    @SerializedName("sub_package_name")
    String sub_package_name;
    @SerializedName("main_package_name")
    String main_package_name;

    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("category_id")
    String category_id;


    String isSelected="n";

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getSub_package_id() {
        return sub_package_id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getBundle_name() {
        return bundle_name;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public String getAmount_saved() {
        return amount_saved;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPercentage_amount_saved() {
        return percentage_amount_saved;
    }

    public String getFinal_price() {
        return final_price;
    }

    public String getMain_package_id() {
        return main_package_id;
    }

    public String getSub_package_name() {
        return sub_package_name;
    }

    public String getMain_package_name() {
        return main_package_name;
    }
}
