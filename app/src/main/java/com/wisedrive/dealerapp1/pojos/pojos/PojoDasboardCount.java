package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoDasboardCount
{

    @SerializedName("sold_cars_count")
    private String sold_cars_count;
    @SerializedName("total_requested_count")
    private String total_requested_count;
    @SerializedName("requested_vehicles")
    private String requested_vehicles;
    @SerializedName("not_sold_cars_count")
    private String not_sold_cars_count;
    @SerializedName("all_cars")
    private String all_cars;
    @SerializedName("cars_for_sale")
    private String cars_for_sale;
    @SerializedName("count")
    private String count;


    public String getCount() {
        return count;
    }

    public String getSold_cars_count() {
        return sold_cars_count;
    }


    public String getTotal_requested_count() {
        return total_requested_count;
    }


    public String getRequested_vehicles() {
        return requested_vehicles;
    }


    public String getNot_sold_cars_count() {
        return not_sold_cars_count;
    }


    public String getAll_cars() {
        return all_cars;
    }


    public String getCars_for_sale() {
        return cars_for_sale;
    }

}
