package com.wisedrive.dealerapp1.pojos.pojos;

public class PojoDeviceDetails {
    private String userId;
    private String phoneNum;
    private String appType;
    private String deviceImei;
    private String deviceModel;
    private String deviceVersion;
    private String deviceToken;
    private String deviceType;
    private String deviceAppVersion;

    public PojoDeviceDetails(String userId, String phoneNum, String appType, String deviceImei,
                             String deviceModel, String deviceVersion, String deviceToken, String deviceType, String deviceAppVersion) {
        this.userId = userId;
        this.phoneNum = phoneNum;
        this.appType = appType;
        this.deviceImei = deviceImei;
        this.deviceModel = deviceModel;
        this.deviceVersion = deviceVersion;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
        this.deviceAppVersion = deviceAppVersion;
    }
}
