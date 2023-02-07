package com.wisedrive.dealerapp1.pojos.pojos;

public class PojoBuyAddOn {

    String dealerId;
    String addonId;
    String mainpackId;
    String subpackId;
    String amount;
    String payment_status;
    String payment_type;
    String payment_mode;
    String reference_id;
    String order_id;
    String payment_id;
    String executive_verification_status;
    String collected_by;
    String discount;
    String gatewayId;
    String vehicleId;
    String customerId;
    String categoryId;

    public PojoBuyAddOn(String dealerId, String addonId, String mainpackId, String subpackId, String amount, String payment_status,
                        String payment_type, String payment_mode, String reference_id, String order_id, String payment_id,
                        String executive_verification_status, String collected_by, String discount, String gatewayId, String vehicleId,
                        String customerId, String categoryId) {
        this.dealerId = dealerId;
        this.addonId = addonId;
        this.mainpackId = mainpackId;
        this.subpackId = subpackId;
        this.amount = amount;
        this.payment_status = payment_status;
        this.payment_type = payment_type;
        this.payment_mode = payment_mode;
        this.reference_id = reference_id;
        this.order_id = order_id;
        this.payment_id = payment_id;
        this.executive_verification_status = executive_verification_status;
        this.collected_by = collected_by;
        this.discount = discount;
        this.gatewayId = gatewayId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.categoryId = categoryId;
    }
}

