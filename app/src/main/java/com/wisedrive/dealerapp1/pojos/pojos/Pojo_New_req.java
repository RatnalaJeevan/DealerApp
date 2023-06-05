package com.wisedrive.dealerapp1.pojos.pojos;

import java.util.ArrayList;

public class Pojo_New_req {

    String no_of_owners_to;
    String color,
            year_to,
            transmission_type,
            req_id, no_of_owners_from,
            year_from,
            posted_on_date,
            count_1,
            brand_icon,
            car_brand,
            model,
            fuel_type;
    double price_range_from,price_range_to;
    int kms_to,kms_from;
            ArrayList<Pojo_vehicle_status_list> MatchingVehList;

    public Pojo_New_req() {

    }

    public String getNo_of_owners_to() {
        return no_of_owners_to;
    }

    public String getColor() {
        return color;
    }

    public int getKms_to() {
        return kms_to;
    }

    public String getYear_to() {
        return year_to;
    }

    public double getPrice_range_from() {
        return price_range_from;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public String getReq_id() {
        return req_id;
    }

    public String getNo_of_owners_from() {
        return no_of_owners_from;
    }

    public String getYear_from() {
        return year_from;
    }

    public String getPosted_on_date() {
        return posted_on_date;
    }

    public double getPrice_range_to() {
        return price_range_to;
    }

    public int getKms_from() {
        return kms_from;
    }

    public String getCount_1() {
        return count_1;
    }

    public String getBrand_icon() {
        return brand_icon;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public String getModel() {
        return model;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public ArrayList<Pojo_vehicle_status_list> getMatchingVehList() {
        return MatchingVehList;
    }
}
