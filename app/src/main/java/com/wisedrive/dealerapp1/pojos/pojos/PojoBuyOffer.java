package com.wisedrive.dealerapp1.pojos.pojos;

public class PojoBuyOffer {

    String vehicleId;
    String dealerId;
    String paymentType;
    String refId;
    String orderId;
    String paymentMode;
    String gatewayId;
    String amount;
    String offerId;
    String paymentStatus;
    String collected_by;

    public PojoBuyOffer(String vehicleId, String dealerId, String paymentType, String refId, String orderId,
                        String paymentMode, String gatewayId, String amount, String offerId, String paymentStatus,
                        String collected_by) {
        this.vehicleId = vehicleId;
        this.dealerId = dealerId;
        this.paymentType = paymentType;
        this.refId = refId;
        this.orderId = orderId;
        this.paymentMode = paymentMode;
        this.gatewayId = gatewayId;
        this.amount = amount;
        this.offerId = offerId;
        this.paymentStatus = paymentStatus;
        this.collected_by = collected_by;
    }
}
