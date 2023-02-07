package com.wisedrive.dealerapp1.pojos.pojos;

import org.json.JSONException;
import org.json.JSONObject;

public class PojoNewVehImgs {

    String vehicle_images;
    String id;
    String image_name;

    public String getVehicle_images() {
        return vehicle_images;
    }

    public String getId() {
        return id;
    }

    public String getImage_name() {
        return image_name;
    }

    public PojoNewVehImgs(JSONObject obj) throws JSONException {
        {
            try {
                vehicle_images=(obj.has("vehicle_images")?obj.getString("vehicle_images") : "");
                id=(obj.has("id")?obj.getString("id") : "");
                image_name=(obj.has("image_name")?obj.getString("image_name") : "");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
