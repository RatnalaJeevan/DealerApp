package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class PojoActVehlist {
    @SerializedName("brand_icon")
    String brand_icon;
    @SerializedName("activation_date")
    String activation_date;
    @SerializedName("vehicle_no")
    String vehicle_no;

//    public PojoActVehlist(JSONObject obj) throws JSONException {
//        {
//            try {
//                brand_icon=(obj.has("brand_icon")?obj.getString("brand_icon") : "");
//                activation_date=(obj.has("activation_date")?obj.getString("activation_date") : "");
//                vehicle_no=(obj.has("vehicle_no")?obj.getString("vehicle_no") : "");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getBrand_icon() {
        return brand_icon;
    }

    public String getActivation_date() {
        return activation_date;
    }
}
