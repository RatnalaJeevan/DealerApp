package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoDealerWarrantyInfo {
    @SerializedName("no_of_warranties_left_from")
    String no_of_warranties_left_from;
    @SerializedName("no_of_warranties_left")
    private String no_of_warranties_left;
    @SerializedName("count")
    private String count;
    @SerializedName("msg")
    private String msg;
    @SerializedName("total_live_cars")
    private String total_live_cars;
    @SerializedName("new_lead_count")
    private String new_lead_count;
    @SerializedName("from_car_count")
    private String from_car_count;

    public String getMsg() {
        return msg;
    }

    public String getNo_of_warranties_left_from() {
        return no_of_warranties_left_from;
    }

    public String getTotal_live_cars() {
        return total_live_cars;
    }

    public String getNew_lead_count() {
        return new_lead_count;
    }

    public String getFrom_car_count() {
        return from_car_count;
    }

    public String getCount() {
        return count;
    }

    public String getNo_of_warranties_left() {
        return no_of_warranties_left;
    }


}
