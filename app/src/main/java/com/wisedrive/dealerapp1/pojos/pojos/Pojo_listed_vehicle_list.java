package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_listed_vehicle_list {
    String vehicle_make,is_feature_present,vehicle_no,vehicle_model,is_image_present,vehicle_id;
    PojoLeadCount LeadCount;

    public PojoLeadCount getLeadCount() {
        return LeadCount;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public String getIs_feature_present() {
        return is_feature_present;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getIs_image_present() {
        return is_image_present;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public class PojoLeadCount{
        String count;

        public String getCount() {
            return count;
        }
    }


}
