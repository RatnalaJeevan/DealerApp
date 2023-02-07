package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoBankList {
    @SerializedName("id")
    private String id;
    @SerializedName("insurance_provider")
    private String insurance_provider;
    @SerializedName("bank_name")
    private String bank_name;

    public String getInsurance_provider() {
        return insurance_provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
