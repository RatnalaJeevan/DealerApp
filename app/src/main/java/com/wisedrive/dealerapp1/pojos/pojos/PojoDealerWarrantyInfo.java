package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoDealerWarrantyInfo {
    @SerializedName("no_of_warranties_left")
    private String no_of_warranties_left;
    @SerializedName("count")
    private String count;

    public String getCount() {
        return count;
    }

    public String getNo_of_warranties_left() {
        return no_of_warranties_left;
    }


}
