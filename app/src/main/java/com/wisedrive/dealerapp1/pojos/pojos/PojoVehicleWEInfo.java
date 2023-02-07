package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoVehicleWEInfo {

    @SerializedName("vehicle_model")
    String vehicle_model;
    @SerializedName("fuel_type")
    String fuel_type;
    @SerializedName("transmission_type")
    String transmission_type;
    @SerializedName("manufacturing_year")
    String manufacturing_year;
    @SerializedName("vehicle_make")
    String vehicle_make;
    @SerializedName("is_vehicle_sold")
    String is_vehicle_sold;
    @SerializedName("brand_icon")
    String brand_icon;
    @SerializedName("uci_category_id")
    String uci_category_id;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("actual_price")
    String actual_price;
    @SerializedName("customer_name")
    String customer_name;
    @SerializedName("customer_phone_no")
    String customer_phone_no;
    @SerializedName("location")
    String location;
    @SerializedName("state")
    String state;
    @SerializedName("pincode")
    String pincode;
    @SerializedName("city")
    String city;
    @SerializedName("Inspection_status")
    String Inspection_status;
    @SerializedName("cooling_period_days")
    String cooling_period_days;
    @SerializedName("cooling_period_kms")
    String cooling_period_kms;
    @SerializedName("customer_aadhaar_rear")
    String customer_aadhaar_rear;
    @SerializedName("rc_front")
    String rc_front;
    @SerializedName("rc_rear")
    String rc_rear;
    @SerializedName("customer_aadhaar_front")
    String customer_aadhaar_front;
    @SerializedName("insurance_copy")
    String insurance_copy;
    @SerializedName("vehicleId")
    String vehicleId;
    @SerializedName("insurance_status")
    String insurance_status;
    @SerializedName("insurance_provider")
    String insurance_provider;
    @SerializedName("insurance_policy")
    String insurance_policy;
    @SerializedName("insurance_type")
    String insurance_type;
    @SerializedName("vehicle_exterior_front_image")
    String vehicle_exterior_front_image;

    @SerializedName("sales_receipt")
    String sales_receipt;
    @SerializedName("delivery_note")
    String delivery_note;
    @SerializedName("customer_full_address")
    String customer_full_address;
    @SerializedName("purchased_from")
    String purchased_from;


    public String getSales_receipt() {
        return sales_receipt;
    }

    public String getDelivery_note() {
        return delivery_note;
    }

    public String getCustomer_full_address() {
        return customer_full_address;
    }

    public String getPurchased_from() {
        return purchased_from;
    }

    public String getCooling_period_days() {
        return cooling_period_days;
    }

    public String getCooling_period_kms() {
        return cooling_period_kms;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getInsurance_policy() {
        return insurance_policy;
    }

    public String getInsurance_type() {
        return insurance_type;
    }

    public String getVehicle_exterior_front_image() {
        return vehicle_exterior_front_image;
    }

    public String getInsurance_provider() {
        return insurance_provider;
    }

    public String getInsurance_status() {
        return insurance_status;
    }

    public String getIs_vehicle_sold() {
        return is_vehicle_sold;
    }

    public String getCustomer_aadhaar_rear() {
        return customer_aadhaar_rear;
    }

    public String getActual_price() {
        return actual_price;
    }

    public String getRc_front() {
        return rc_front;
    }

    public void setRc_front(String rc_front) {
        this.rc_front = rc_front;
    }

    public String getRc_rear() {
        return rc_rear;
    }

    public String getCustomer_aadhaar_front() {
        return customer_aadhaar_front;
    }

    public String getInsurance_copy() {
        return insurance_copy;
    }

    public String getInspection_status() {
        return Inspection_status;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone_no() {
        return customer_phone_no;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getUci_category_id() {
        return uci_category_id;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public String getManufacturing_year() {
        return manufacturing_year;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public String getBrand_icon() {
        return brand_icon;
    }

}
