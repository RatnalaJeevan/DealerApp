package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoOfferDetails {

    @SerializedName("offer_title")
    String offer_title ;
    @SerializedName("offer_details")
    String offer_details ;
    @SerializedName("offer_validity")
    String offer_validity ;
    @SerializedName("offer_availability")
    String offer_availability ;
    @SerializedName("offer_id")
    String offer_id ;

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getOffer_details() {
        return offer_details;
    }

    public void setOffer_details(String offer_details) {
        this.offer_details = offer_details;
    }

    public String getOffer_validity() {
        return offer_validity;
    }

    public void setOffer_validity(String offer_validity) {
        this.offer_validity = offer_validity;
    }

    public String getOffer_availability() {
        return offer_availability;
    }

    public void setOffer_availability(String offer_availability) {
        this.offer_availability = offer_availability;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }
}
