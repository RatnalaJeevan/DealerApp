package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoResponseDealerDetails {

    @SerializedName("phone_no")
    private String phone_no;
    @SerializedName("is_verified")
    private String is_verified;
    @SerializedName("dealer_id")
    private String dealer_id;
    @SerializedName("dealer_name")
    private String dealer_name;
    @SerializedName("dealer_logo")
    private String dealer_logo;
    @SerializedName("customer_support_phone_no")
    private String customer_support_phone_no;
    @SerializedName("city")
    private String city;
    @SerializedName("pincode")
    private String pincode;
    @SerializedName("state")
    private String state;
    @SerializedName("dealership_name")
    private String dealership_name;
    @SerializedName("full_address")
    private String full_address;
    @SerializedName("state_id")
    private String state_id;
    @SerializedName("dealer_email")
    private String dealer_email;
    @SerializedName("landmark")
    private String landmark;
    @SerializedName("location")
    private String location;
    @SerializedName("customer_support_email")
    private String customer_support_email;
    @SerializedName("city_id")
    private String city_id;


    public String getPhone_no() {
        return phone_no;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public String getDealer_logo() {
        return dealer_logo;
    }

    public String getCustomer_support_phone_no() {
        return customer_support_phone_no;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getState() {
        return state;
    }

    public String getDealership_name() {
        return dealership_name;
    }

    public String getFull_address() {
        return full_address;
    }

    public String getState_id() {
        return state_id;
    }

    public String getDealer_email() {
        return dealer_email;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getLocation() {
        return location;
    }

    public String getCustomer_support_email() {
        return customer_support_email;
    }

    public String getCity_id() {
        return city_id;
    }
}
