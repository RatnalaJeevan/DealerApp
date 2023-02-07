package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoCredentials {
    @SerializedName("aws_secret")
    private String aws_secret;
    @SerializedName("aws_key")
    private String aws_key;
    @SerializedName("comet_auth_key")
    private String comet_auth_key;
    @SerializedName("comet_region")
    private String comet_region;
    @SerializedName("comet_app_id")
    private String comet_app_id;

    public String getAws_secret() {
        return aws_secret;
    }


    public String getAws_key() {
        return aws_key;
    }

    public String getComet_auth_key() {
        return comet_auth_key;
    }

    public String getComet_region() {
        return comet_region;
    }

    public String getComet_app_id() {
        return comet_app_id;
    }
}
