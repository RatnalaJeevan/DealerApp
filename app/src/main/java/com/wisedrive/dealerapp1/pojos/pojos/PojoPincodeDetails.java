package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPincodeDetails {
    @SerializedName("pincode")
    String pincode;
    @SerializedName("state_name")
    String state_name;
    @SerializedName("city_name")
    String city_name;
    @SerializedName("city")
    String city;
    @SerializedName("state")
    String state;
    @SerializedName("city_id")
    String city_id;
    @SerializedName("state_id")
    String state_id;
    @SerializedName("is_dealer_city")
    String is_dealer_city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getIs_dealer_city() {
        return is_dealer_city;
    }

    public void setIs_dealer_city(String is_dealer_city) {
        this.is_dealer_city = is_dealer_city;
    }

    public String getPincode()
    {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
