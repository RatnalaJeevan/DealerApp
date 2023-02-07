package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoInspectionDetails {

    @SerializedName("id")
    String id;
    @SerializedName("status")
    String status;
    @SerializedName("estimated_cost")
    String estimated_cost;
    @SerializedName("inspection_report_image")
    String inspection_report_image;
    @SerializedName("inspection_warranty_status")
    String inspection_warranty_status;

    public String getInspection_warranty_status() {
        return inspection_warranty_status;
    }

    public void setInspection_warranty_status(String inspection_warranty_status) {
        this.inspection_warranty_status = inspection_warranty_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimated_cost() {
        return estimated_cost;
    }

    public void setEstimated_cost(String estimated_cost) {
        this.estimated_cost = estimated_cost;
    }

    public String getInspection_report_image() {
        return inspection_report_image;
    }

    public void setInspection_report_image(String inspection_report_image) {
        this.inspection_report_image = inspection_report_image;
    }
}
