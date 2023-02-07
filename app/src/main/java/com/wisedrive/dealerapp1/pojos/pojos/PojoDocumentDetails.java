package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoDocumentDetails
{
    @SerializedName("aadhaar_front")
    private String aadhaar_front;
    @SerializedName("rc_rear")
    private String rc_rear;
    @SerializedName("rc_front")
    private String rc_front;
    @SerializedName("aadhaar_rear")
    private String aadhaar_rear;
    @SerializedName("insurance_copy")
    private String insurance_copy;

    public String getAadhaar_front() {
        return aadhaar_front;
    }

    public void setAadhaar_front(String aadhaar_front) {
        this.aadhaar_front = aadhaar_front;
    }

    public String getRc_rear() {
        return rc_rear;
    }

    public void setRc_rear(String rc_rear) {
        this.rc_rear = rc_rear;
    }

    public String getRc_front() {
        return rc_front;
    }

    public void setRc_front(String rc_front) {
        this.rc_front = rc_front;
    }

    public String getAadhaar_rear() {
        return aadhaar_rear;
    }

    public void setAadhaar_rear(String aadhaar_rear) {
        this.aadhaar_rear = aadhaar_rear;
    }

    public String getInsurance_copy() {
        return insurance_copy;
    }

    public void setInsurance_copy(String insurance_copy) {
        this.insurance_copy = insurance_copy;
    }
}
