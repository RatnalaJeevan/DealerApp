package com.wisedrive.dealerapp1.pojos.pojos;

public class PojoEditDealer {

    private String dealerId;
    private String vendorName;
    private String ownerName;
    private String emailId;
    private String location;
    private String landmark;
    private String addressLine1;
    private String addressLine2;
    private String cityId;
    private String stateId;
    private String latitude;
    private String longitude;
    private String updatedBy;
    private String mobileNo;
    private String otp;
    private String logo;

    public PojoEditDealer(String dealerId, String vendorName, String ownerName, String emailId,
                          String location, String landmark, String addressLine1, String addressLine2,
                          String cityId, String stateId, String latitude, String longitude, String updatedBy, String mobileNo,
                          String otp,String logo
    ) {
        this.dealerId = dealerId;
        this.vendorName = vendorName;
        this.ownerName = ownerName;
        this.emailId = emailId;
        this.location = location;
        this.landmark = landmark;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.cityId = cityId;
        this.stateId = stateId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedBy = updatedBy;
        this.mobileNo = mobileNo;
        this.otp=otp;
        this.logo=logo;
    }
}
