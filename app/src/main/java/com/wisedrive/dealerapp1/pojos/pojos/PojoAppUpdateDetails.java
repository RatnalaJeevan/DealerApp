package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAppUpdateDetails {
    @SerializedName("can_skip")
    private String can_skip;
    @SerializedName("appversion")
    private String appversion;
    @SerializedName("app_url")
    private String app_url;
    @SerializedName("is_current")
    private String is_current;
    @SerializedName("ostype_id")
    private String ostype_id;
    @SerializedName("app_name")
    private String app_name;
    @SerializedName("application_id")
    private String application_id;

    public String getCan_skip() {
        return can_skip;
    }
    public String getAppversion() {
        return appversion;
    }

    public String getApp_url() {
        return app_url;
    }

    public String getIs_current() {
        return is_current;
    }


}
