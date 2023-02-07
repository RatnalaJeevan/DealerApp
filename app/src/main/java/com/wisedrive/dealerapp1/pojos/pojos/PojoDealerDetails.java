package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoDealerDetails {

    private String dealershipName;
    private String dealerName;
    private String mobileNo;
    private String dealerLogo;
    private String address;
    private String cityId;
    private String pincode;
    private String otp;
    private String location;
    private String emailId;
    private String latitude;
    private String longitude;



    public PojoDealerDetails(String dealershipName, String dealerName, String mobileNo, String dealerLogo,
                             String address, String cityId, String pincode, String otp,String location,
                             String emailId,String latitude,String longitude) {
        this.dealershipName = dealershipName;
        this.dealerName = dealerName;
        this.mobileNo = mobileNo;
        this.dealerLogo = dealerLogo;
        this.address = address;
        this.cityId = cityId;
        this.pincode = pincode;
        this.otp = otp;
        this.location=location;
        this.emailId=emailId;
        this.latitude=latitude;
        this.longitude=longitude;
    }


}
