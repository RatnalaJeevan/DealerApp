package com.wisedrive.dealerapp1.pojos.pojos;

import org.json.JSONArray;

import java.util.ArrayList;

public class PojoAddNewCar {
    private String dealerId;
    private String modelId;
    private String fuelType;
    private String transmissionType;
    private String year;
    private String vehicleNo;
    private String sellingPrice;
    private String km;
    private String noOfOwner;
    private String color;
    private String isPublic;
    private String status;
    private String bankName;
    private String insuranceType;
    private String expDate;
    private ArrayList imagesArr;

    public PojoAddNewCar(String dealerId, String modelId, String fuelType, String transmissionType, String year,
                         String vehicleNo, String sellingPrice, String km, String noOfOwner, String color, String isPublic, String status, String bankName, String insuranceType, String expDate, ArrayList imagesArr) {
        this.dealerId = dealerId;
        this.modelId = modelId;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.year = year;
        this.vehicleNo = vehicleNo;
        this.sellingPrice = sellingPrice;
        this.km = km;
        this.noOfOwner = noOfOwner;
        this.color = color;
        this.isPublic = isPublic;
        this.status = status;
        this.bankName = bankName;
        this.insuranceType = insuranceType;
        this.expDate = expDate;
        this.imagesArr = imagesArr;
    }
}
