package com.wisedrive.dealerapp1.pojos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PojoAbtExpVehList {

    String vehicle_no;
    String vehicle_model;
    String dealer_name;
    String brand_icon;
    String dealer_phone_no;
    String vehicle_make;
    String inspection_status;
    String fuel_type;
    String manufacturing_year;
    String transmission_type;
    String odometer;
    String expiry_date;

    private String color;
    private String ownership;
    private String actual_price;
    private String insurance_provider;
    private String vehicle_id;
    private String insurance_status;
    private String insurance_type;
    private String is_insurance_update;
    private String warranty_status;
    private  String  inspection_date;
    private String is_vehicle_public;
    private String odometer_from;
    private String insurance_validity;
    private String odometer_to;
    private String manufacturing_year_from;
    private String manufacturing_year_to;
    private String price_from;
    private String price_to;
    private String dealer_id;
    private String sold_price;
    private String  inspection_warranty_status;
    private String  inspection_status_by_mechanic;
    private String  cooling_period_kms;
    private String  is_with_cooling_period;
    private String  cooling_period_days;
    private String  inspection_comments;
    private String  insurance_policy;
    private JSONArray VehicleImages=new JSONArray();
    public PojoAbtExpVehList() {
    }
    public PojoAbtExpVehList(JSONObject obj) throws JSONException
    {
        {
            try {
                dealer_name=(obj.has("dealer_name")?obj.getString("dealer_name") : "");
                brand_icon=(obj.has("brand_icon")?obj.getString("brand_icon") : "");
                vehicle_model=(obj.has("vehicle_model")?obj.getString("vehicle_model") : "");
                vehicle_no=(obj.has("vehicle_no")?obj.getString("vehicle_no") : "");
                dealer_phone_no=(obj.has("dealer_phone_no")?obj.getString("dealer_phone_no") : "");
                vehicle_make=(obj.has("vehicle_make")?obj.getString("vehicle_make") : "");
                inspection_status=(obj.has("inspection_status")?obj.getString("inspection_status") : "");
                fuel_type=(obj.has("fuel_type")?obj.getString("fuel_type") : "");
                manufacturing_year=(obj.has("manufacturing_year")?obj.getString("manufacturing_year") : "");
                transmission_type=(obj.has("transmission_type")?obj.getString("transmission_type") : "");
                odometer=(obj.has("odometer")?obj.getString("odometer") : "");
                expiry_date=(obj.has("expiry_date")?obj.getString("expiry_date") : "");
                inspection_warranty_status=(obj.has("inspection_warranty_status")?obj.getString("inspection_warranty_status") : "");
                inspection_status_by_mechanic=(obj.has("inspection_status_by_mechanic")?obj.getString("inspection_status_by_mechanic") : "");
                vehicle_make=(obj.has("vehicle_make")?obj.getString("vehicle_make") : "");
                odometer=(obj.has("odometer")?obj.getString("odometer") : "");
                color=(obj.has("color")? obj.getString("color") : "");
                ownership = (obj.has("ownership") ? obj.getString("ownership") : "");
                vehicle_id = (obj.has("vehicle_id") ? obj.getString("vehicle_id") : "");
                actual_price = (obj.has("actual_price") ? obj.getString("actual_price") : "");
                insurance_provider = (obj.has("insurance_provider") ? obj.getString("insurance_provider") : "");
                fuel_type=(obj.has("fuel_type")?obj.getString("fuel_type") : "");
                vehicle_no=(obj.has("vehicle_no")?obj.getString("vehicle_no") : "");
                insurance_status=(obj.has("insurance_status")? obj.getString("insurance_status") : "");
                brand_icon = (obj.has("brand_icon") ? obj.getString("brand_icon") : "");
                insurance_type = (obj.has("insurance_type") ? obj.getString("insurance_type") : "");
                actual_price = (obj.has("actual_price") ? obj.getString("actual_price") : "");
                warranty_status = (obj.has("warranty_status") ? obj.getString("warranty_status") : "");
                insurance_validity = (obj.has("insurance_validity") ? obj.getString("insurance_validity") : "");
                insurance_provider = (obj.has("insurance_provider") ? obj.getString("insurance_provider") : "");
                vehicle_model=(obj.has("vehicle_model")?obj.getString("vehicle_model") : "");
                manufacturing_year=(obj.has("manufacturing_year")?obj.getString("manufacturing_year") : "");
                is_insurance_update=(obj.has("is_insurance_update")? obj.getString("is_insurance_update") : "");
                transmission_type = (obj.has("transmission_type") ? obj.getString("transmission_type") : "");
                is_vehicle_public = (obj.has("is_vehicle_public") ? obj.getString("is_vehicle_public") : "");
                odometer_from = (obj.has("odometer_from") ? obj.getString("odometer_from") : "");
                odometer_to = (obj.has("odometer_to") ? obj.getString("odometer_to") : "");
                manufacturing_year_from=(obj.has("manufacturing_year_from")?obj.getString("manufacturing_year_from") : "");
                manufacturing_year_to=(obj.has("manufacturing_year_to")?obj.getString("manufacturing_year_to") : "");
                price_from=(obj.has("price_from")? obj.getString("price_from") : "");
                price_to=(obj.has("price_to")? obj.getString("price_to") : "");
                dealer_id=(obj.has("dealer_id")? obj.getString("dealer_id") : "");
                sold_price=(obj.has("sold_price")? obj.getString("sold_price") : "");
                cooling_period_days=(obj.has("cooling_period_days")? obj.getString("cooling_period_days") : "");
                is_with_cooling_period=(obj.has("is_with_cooling_period")? obj.getString("is_with_cooling_period") : "");
                cooling_period_kms=(obj.has("cooling_period_kms")? obj.getString("cooling_period_kms") : "");
                inspection_comments=(obj.has("inspection_comments")? obj.getString("inspection_comments") : "");
                insurance_policy=(obj.has("insurance_policy")? obj.getString("insurance_policy") : "");
                inspection_date=(obj.has("inspection_date")? obj.getString("inspection_date") : "");
                VehicleImages=(obj.has("VehicleImages")?(obj.getJSONArray("VehicleImages")):new JSONArray());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JSONArray getVehicleImages() {
        return VehicleImages;
    }
    public String getInspection_date() {
        return inspection_date;
    }

    public void setInspection_date(String inspection_date) {
        this.inspection_date = inspection_date;
    }

    public String getBrand_icon() {
        return brand_icon;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public String getManufacturing_year() {
        return manufacturing_year;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public String getOdometer() {
        return odometer;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public String getDealer_logo() {
        return brand_icon;
    }

    public String getDealer_phone_no() {
        return dealer_phone_no;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public String getInspection_status() {
        return inspection_status;
    }


    public String getCooling_period_kms() {
        return cooling_period_kms;
    }

    public String getInsurance_policy() {
        return insurance_policy;
    }

    public String getIs_with_cooling_period() {
        return is_with_cooling_period;
    }


    public String getCooling_period_days() {
        return cooling_period_days;
    }


    public String getInspection_comments() {
        return inspection_comments;
    }

    public String getInsurance_validity() {
        return insurance_validity;
    }

    public String getInspection_warranty_status() {
        return inspection_warranty_status;
    }


    public String getInspection_status_by_mechanic() {
        return inspection_status_by_mechanic;
    }


    public String getSold_price() {
        return sold_price;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwnership() {
        return ownership;
    }

    public String getActual_price() {
        return actual_price;
    }


    public String getInsurance_provider() {
        return insurance_provider;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getInsurance_status() {
        return insurance_status;
    }

    public String getInsurance_type() {
        return insurance_type;
    }

    public void setInsurance_type(String insurance_type) {
        this.insurance_type = insurance_type;
    }

    public String getIs_insurance_update() {
        return is_insurance_update;
    }


    public String getIs_vehicle_public() {
        return is_vehicle_public;
    }

    public String getOdometer_from() {
        return odometer_from;
    }

    public void setOdometer_from(String odometer_from) {
        this.odometer_from = odometer_from;
    }

    public String getOdometer_to() {
        return odometer_to;
    }

    public void setOdometer_to(String odometer_to) {
        this.odometer_to = odometer_to;
    }

    public String getManufacturing_year_from() {
        return manufacturing_year_from;
    }


    public String getManufacturing_year_to() {
        return manufacturing_year_to;
    }

    public String getPrice_from() {
        return price_from;

    }

    public void setPrice_from(String price_from) {
        this.price_from = price_from;
    }

    public String getPrice_to() {
        return price_to;
    }

    public void setPrice_to(String price_to) {
        this.price_to = price_to;
    }
}
