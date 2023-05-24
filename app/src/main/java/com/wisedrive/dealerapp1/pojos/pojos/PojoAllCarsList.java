package com.wisedrive.dealerapp1.pojos.pojos;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PojoAllCarsList
{
    private String vehicle_make;
    private String odometer;
    private String color;
    private String ownership;
    private String actual_price;
    private String insurance_provider;
    private String fuel_type;
    private String vehicle_no;
    private String vehicle_id;
    private String insurance_status;
    private String brand_icon;
    private String insurance_type;
    private String vehicle_model;
    private String manufacturing_year;
    private String is_insurance_update;
    private String warranty_status;
    private String transmission_type;
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
    private String cooling_period_kms;
    private String  is_with_cooling_period;
    private String  cooling_period_days;
    private String  inspection_comments;
    private String  insurance_policy;
    private String  customer_name;
    private String  inspection_date;
    private String  package_sold_on;
    private  String inspection_expires_on;
    private  String is_vehicle_present_in_portal;
    private String is_images_present_in_portal;
    private String is_features_present_in_portal;
    private String status_id;
    private String is_with_package;
    private JSONObject LeadCount;
    private JSONObject ViewCount;


    private String cooling_period_comments;
   // private ArrayList<PojoNewVehImgs>VehicleImages=new ArrayList<>();

    private JSONArray VehicleImages=new JSONArray();

    public PojoAllCarsList(JSONObject obj) throws JSONException
    {
        {
            try {
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
                customer_name=(obj.has("customer_name")? obj.getString("customer_name") : "");
                inspection_date=(obj.has("inspection_date")? obj.getString("inspection_date") : "");
                package_sold_on=(obj.has("package_sold_on")? obj.getString("package_sold_on") : "");
                inspection_expires_on=(obj.has("inspection_expires_on")? obj.getString("inspection_expires_on") : "");
                VehicleImages=(obj.has("VehicleImages")?(obj.getJSONArray("VehicleImages")):new JSONArray());
                cooling_period_comments=(obj.has("cooling_period_comments")? obj.getString("cooling_period_comments") : "");
                is_vehicle_present_in_portal=(obj.has("is_vehicle_present_in_portal")? obj.getString("is_vehicle_present_in_portal") : "");
                is_images_present_in_portal=(obj.has("is_images_present_in_portal")? obj.getString("is_images_present_in_portal") : "");
                is_features_present_in_portal=(obj.has("is_features_present_in_portal")? obj.getString("is_features_present_in_portal") : "");
                status_id=(obj.has("status_id")? obj.getString("status_id") : "");
                is_with_package=(obj.has("is_with_package")? obj.getString("is_with_package") : "");
                LeadCount=(obj.has("LeadCount")?(obj.getJSONObject("LeadCount")):new JSONObject());
                ViewCount=(obj.has("ViewCount")?(obj.getJSONObject("ViewCount")):new JSONObject());
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public String getIs_with_package() {
        return is_with_package;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getIs_images_present_in_portal() {
        return is_images_present_in_portal;
    }

    public String getIs_features_present_in_portal() {
        return is_features_present_in_portal;
    }

    public String getInspection_expires_on() {
        return inspection_expires_on;
    }

    public JSONObject getLeadCount() {
        return LeadCount;
    }

    public JSONObject getViewCount() {
        return ViewCount;
    }

    public String getIs_vehicle_present_in_portal() {
        return is_vehicle_present_in_portal;
    }

    public String getCooling_period_comments() {
        return cooling_period_comments;
    }


    public String getInsurance_validity() {
        return insurance_validity;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getPackage_sold_on() {
        return package_sold_on;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCooling_period_kms() {
        return cooling_period_kms;
    }

    public String getInsurance_policy() {
        return insurance_policy;
    }


    public String getCooling_period_days() {
        return cooling_period_days;
    }


    public String getInspection_comments() {
        return inspection_comments;
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

    public String getOdometer() {
        return odometer;
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

    public String getFuel_type() {
        return fuel_type;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }


    public String getBrand_icon() {
        return brand_icon;
    }

    public String getInsurance_type() {
        return insurance_type;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }


    public String getManufacturing_year() {
        return manufacturing_year;
    }

    public String getIs_with_cooling_period() {
        return is_with_cooling_period;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public String getIs_vehicle_public() {
        return is_vehicle_public;
    }



    public JSONArray getVehicleImages() {
        return VehicleImages;
    }

}
