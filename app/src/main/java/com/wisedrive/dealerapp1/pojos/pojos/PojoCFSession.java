package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoCFSession {
    String dealerEmail;
    String dealerNumber;
    String dealerName;
    String orderAmount;
    String dealerId;

    public PojoCFSession(String dealerEmail, String dealerNumber, String dealerName, String orderAmount, String dealerId) {
        this.dealerEmail = dealerEmail;
        this.dealerNumber = dealerNumber;
        this.dealerName = dealerName;
        this.orderAmount = orderAmount;
        this.dealerId = dealerId;
    }

    @SerializedName("order_status")
    String order_status;
    @SerializedName("cf_order_id")
    String cf_order_id;
    @SerializedName("payment_session_id")
    String payment_session_id;
    @SerializedName("order_id")
    String order_id;

    public String getDealerEmail() {
        return dealerEmail;
    }

    public String getDealerNumber() {
        return dealerNumber;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getCf_order_id() {
        return cf_order_id;
    }

    public String getPayment_session_id() {
        return payment_session_id;
    }

    public String getOrder_id() {
        return order_id;
    }
}
