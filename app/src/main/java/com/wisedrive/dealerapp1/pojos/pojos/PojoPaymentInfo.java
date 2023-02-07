package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPaymentInfo {
    double amount;
    String payment_status;
    String payment_type;
    String payment_mode;
    String reference_id;
    String order_id;
    String customer_id;
    String payment_id;
    String executive_verification_status;
    String collected_by;
    double discount;
    @SerializedName("content_1")
    String content_1;
    @SerializedName("content_2")
    String content_2;

    public String getContent_1() {
        return content_1;
    }

    public void setContent_1(String content_1) {
        this.content_1 = content_1;
    }

    public String getContent_2() {
        return content_2;
    }

    public void setContent_2(String content_2) {
        this.content_2 = content_2;
    }

    public PojoPaymentInfo(double amount, String payment_status, String payment_type, String payment_mode,
                           String reference_id, String order_id, String customer_id, String payment_id,
                           String executive_verification_status, String collected_by, double discount) {
        this.amount = amount;
        this.payment_status = payment_status;
        this.payment_type = payment_type;
        this.payment_mode = payment_mode;
        this.reference_id = reference_id;
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.payment_id = payment_id;
        this.executive_verification_status = executive_verification_status;
        this.collected_by = collected_by;
        this.discount = discount;
    }
}
