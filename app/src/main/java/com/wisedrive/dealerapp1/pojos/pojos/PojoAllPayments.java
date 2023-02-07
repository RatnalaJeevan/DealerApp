package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoAllPayments {


    @SerializedName("sub_package_name")
    String sub_package_name;
    @SerializedName("service_name")
    String service_name;
    @SerializedName("payment_mode")
    String payment_mode;
    @SerializedName("display_name")
    String display_name;
    @SerializedName("package_type")
    String package_type;
    @SerializedName("payment_date")
    String payment_date;
    @SerializedName("ActivateVehList")
    ArrayList<PojoActVehlist> ActivateVehList;

    public ArrayList<PojoActVehlist> getActivateVehList() {
        return ActivateVehList;
    }

    public String getSub_package_name() {
        return sub_package_name;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public String getService_name() {
        return service_name;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getPackage_type() {
        return package_type;
    }
}
