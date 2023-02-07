package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojOurServices {

    @SerializedName("package_discription")
    String package_discription;
    @SerializedName("gateway_name")
    String gateway_name;
    @SerializedName("payment_gateway_id")
    String payment_gateway_id;

    public String getGateway_name() {
        return gateway_name;
    }

    public String getPayment_gateway_id() {
        return payment_gateway_id;
    }

    public String getPackage_discription() {
        return package_discription;
    }
}
