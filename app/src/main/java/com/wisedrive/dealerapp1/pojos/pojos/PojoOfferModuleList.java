package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoOfferModuleList {

    @SerializedName("offer_title")
    String offer_title;
    @SerializedName("fastag_validity")
    String fastag_validity;
    @SerializedName("amount")
    String amount;
    @SerializedName("offer_id")
    String offer_id;
    @SerializedName("offer_category_id")
    String offer_category_id;
    @SerializedName("offer_payment_id")
    String offer_payment_id;
    @SerializedName("is_offer_taken")
    String is_offer_taken;

    @SerializedName("offer_details")
    String offer_details;

    String isSelected = "n";

    public String getOffer_details() {
        return offer_details;
    }

    public String getIs_offer_taken() {
        return is_offer_taken;
    }

    public String getOffer_category_id() {
        return offer_category_id;
    }

    public String getOffer_payment_id() {
        return offer_payment_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getFastag_validity() {
        return fastag_validity;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getOffer_title() {
        return offer_title;
    }
}
