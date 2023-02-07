package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoInspectionEligibleStatus {
    @SerializedName("error_msg")
    String error_msg;
    @SerializedName("can_request_for_inspection")
    String can_request_for_inspection;
    @SerializedName("is_vehicle_exists")
    String is_vehicle_exists;

    public String getError_msg() {
        return error_msg;
    }

    public String getCan_request_for_inspection() {
        return can_request_for_inspection;
    }

    public String getIs_vehicle_exists() {
        return is_vehicle_exists;
    }

}
