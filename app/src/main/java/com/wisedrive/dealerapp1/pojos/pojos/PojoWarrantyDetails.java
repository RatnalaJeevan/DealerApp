package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoWarrantyDetails {


    @SerializedName("package_activated_on")
    String package_activated_on;
    @SerializedName("package_activation_code")
    String package_activation_code;
    @SerializedName("warranty_type")
    String warranty_type;
    @SerializedName("sold_date")
    String sold_date;
    @SerializedName("name")
    String name;
    @SerializedName("number")
    String number;
    @SerializedName("customer_id")
    String customer_id;

    @SerializedName("fastag_validity")
    String fastag_validity;
    @SerializedName("fastag_availability")
    String fastag_availability;
    @SerializedName("fastag_status")
    String fastag_status;
    @SerializedName("fastag_number")
    String fastag_number;
    @SerializedName("pancard_image")
    String pancard_image;
    @SerializedName("fastag_iamge")
    String fastag_iamge;
    @SerializedName("city")
    String city;
    @SerializedName("state")
    String state;
    @SerializedName("pincode")
    String pincode;
    @SerializedName("vehicle_fastag_id")
    String vehicle_fastag_id;
    @SerializedName("location")
    String location;
    @SerializedName("customer_full_address")
    String customer_full_address;
    @SerializedName("landmark")
    String landmark;
    @SerializedName("delivery_note")
    String delivery_note;
    @SerializedName("package_sold_on")
    String package_sold_on;
    @SerializedName("purchased_from")
    String purchased_from;
    @SerializedName("sales_receipt")
    String sales_receipt;


    public String getPackage_activated_on() {
        return package_activated_on;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getVehicle_fastag_id() {
        return vehicle_fastag_id;
    }

    public String getLocation() {
        return location;
    }

    public String getCustomer_full_address() {
        return customer_full_address;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getDelivery_note() {
        return delivery_note;
    }

    public String getPackage_sold_on() {
        return package_sold_on;
    }

    public String getPurchased_from() {
        return purchased_from;
    }

    public String getSales_receipt() {
        return sales_receipt;
    }

    public String getFastag_status() {
        return fastag_status;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getFastag_validity() {
        return fastag_validity;
    }

    public String getFastag_availability() {
        return fastag_availability;
    }


    public String getFastag_number() {
        return fastag_number;
    }

    public String getPancard_image() {
        return pancard_image;
    }

    public String getFastag_iamge() {
        return fastag_iamge;
    }


    public String getPackage_activation_code() {
        return package_activation_code;
    }

    public String getWarranty_type() {
        return warranty_type;
    }

    public String getSold_date() {
        return sold_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
