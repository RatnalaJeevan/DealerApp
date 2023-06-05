package com.wisedrive.dealerapp1.pojos;

public class PojoSwapVeh {

    String oldvehicleId;
    String newvehicleId;
    String listingPrice;

    String vehicleId;
    String description;

    public PojoSwapVeh(String oldvehicleId, String newvehicleId, String listingPrice) {
        this.oldvehicleId = oldvehicleId;
        this.newvehicleId = newvehicleId;
        this.listingPrice = listingPrice;
    }


    public PojoSwapVeh(String vehicleId, String description) {
        this.vehicleId = vehicleId;
        this.description = description;
    }
}
