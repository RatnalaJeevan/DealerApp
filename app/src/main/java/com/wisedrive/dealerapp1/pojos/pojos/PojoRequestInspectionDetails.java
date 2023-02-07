package com.wisedrive.dealerapp1.pojos.pojos;

public class PojoRequestInspectionDetails {

    String dealerId;
    String vehicleId;
    String vehicleNo;
    String inspectionType;
    String locationType;
    String carCount;
    String date;
    String customerName;
    String phoneNum;
    String address;
    String location;

    public PojoRequestInspectionDetails(String dealerId, String vehicleId, String vehicleNo,
                                        String inspectionType, String locationType, String carCount,
                                        String date, String customerName, String phoneNum, String address,
                                        String location) {
        this.dealerId = dealerId;
        this.vehicleId = vehicleId;
        this.vehicleNo = vehicleNo;
        this.inspectionType = inspectionType;
        this.locationType = locationType;
        this.carCount = carCount;
        this.date = date;
        this.customerName = customerName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.location=location;
    }
}

